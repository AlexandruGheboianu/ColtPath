package com.ghb.coltpath.state;

import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * Created by alexg on 5/17/2016.
 */
public class Persist {
    @Autowired
    UserRepository userRepository;

    private final PersistStateMachineHandler handler;

    private final PersistStateMachineHandler.PersistStateChangeListener listener = new LocalPersistStateChangeListener();

    public Persist(PersistStateMachineHandler handler) {
        this.handler = handler;
        this.handler.addPersistStateChangeListener(listener);
    }

    public void change(long user, String event) {
        User u = userRepository.findOne(user);
        handler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("user", user).build(), u.getState());
    }

    private class LocalPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {

        @Override
        public void onPersist(State<String, String> state, Message<String> message,
                              Transition<String, String> transition, StateMachine<String, String> stateMachine) {
            if (message != null && message.getHeaders().containsKey("user")) {
                Long userId = message.getHeaders().get("user", Long.class);
                User user = userRepository.findOne(userId);
                user.setState(state.getId());
                userRepository.save(user);
            }
        }
    }
}
