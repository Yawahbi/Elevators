package io.yawahbi.elevators.states;

import io.yawahbi.elevators.IElevator;

import java.util.logging.Logger;


public final class GoingDownState implements ElevatorState {
    private static final Logger LOGGER = Logger.getLogger(GoingDownState.class.getName());

    @Override
    public void up(final IElevator elevator, final int where) {
        throw new IllegalStateException("Elevator is descending, Wait until it reach bottom floor.");
    }

    @Override
    public void down(final IElevator elevator, final int where) {
        if (elevator.getCurrentFloor() != where) {
            LOGGER.info("Going Down to Floor : " + (elevator.getCurrentFloor() - 1));
            elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
            down(elevator, where);
        } else {
            this.rest(elevator);
        }
    }

    @Override
    public void rest(final IElevator elevator) {
        LOGGER.info("Resting");
        elevator.setElevatorState(new RestingState());
        if (elevator.getCurrentFloor() != 1) {
            LOGGER.info("Close Elevator, Continue ...");
            elevator.setElevatorState(this);
        } else {
            LOGGER.info("Elevator reaches the bottom floor, Switch direction.");
            elevator.setElevatorState(new GoingUpState());
        }
    }

    @Override
    public void stop(IElevator elevator) {
        elevator.setElevatorState(new StopState());
    }
}
