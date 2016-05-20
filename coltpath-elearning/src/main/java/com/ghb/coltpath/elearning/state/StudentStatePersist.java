package com.ghb.coltpath.elearning.state;

import com.ghb.coltpath.core.model.Student;
import com.ghb.coltpath.elearning.repository.StudentRepository;
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
public class StudentStatePersist {
    @Autowired
    StudentRepository studentRepository;

    private final PersistStateMachineHandler handler;

    private final PersistStateMachineHandler.PersistStateChangeListener listener = new LocalPersistStateChangeListener();

    public StudentStatePersist(PersistStateMachineHandler handler) {
        this.handler = handler;
        this.handler.addPersistStateChangeListener(listener);
    }

    public void change(long user, String event) {
        Student student = studentRepository.findOne(user);
        handler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("user", user).build(), student.getState());
    }

    private class LocalPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {

        @Override
        public void onPersist(State<String, String> state, Message<String> message,
                              Transition<String, String> transition, StateMachine<String, String> stateMachine) {
            if (message != null && message.getHeaders().containsKey("user")) {
                Long userId = message.getHeaders().get("user", Long.class);
                Student student = studentRepository.findOne(userId);
                student.setState(state.getId());
                studentRepository.save(student);
            }
        }
    }
}
