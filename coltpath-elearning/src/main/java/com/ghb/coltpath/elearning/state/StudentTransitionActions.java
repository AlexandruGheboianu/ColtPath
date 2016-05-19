package com.ghb.coltpath.elearning.state;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * Created by alexg on 5/16/2016.
 */

@WithStateMachine
public class StudentTransitionActions {


    @OnTransition(source = "CREATED", target = "STARTED")
    public void method1() {
        System.out.println("Transition: CREATED -> STARTED");
        System.out.println("Current State: STARTED");

        System.out.println("*********************Current State: STARTED*********************");
    }

    @OnTransition(source = "STARTED", target = "APPRENTICE")
    public void method2() {
        System.out.println("Transition: STARTED -> APPRENTICE");
        System.out.println("Current State: APPRENTICE");

        System.out.println("*********************Current State: APPRENTICE*********************");
    }
}