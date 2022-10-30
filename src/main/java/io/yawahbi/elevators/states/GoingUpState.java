package io.yawahbi.elevators.states;

import io.yawahbi.elevators.IElevator;

import java.util.logging.Logger;


public final class GoingUpState implements ElevatorState {
    private static final Logger LOGGER = Logger.getLogger(GoingUpState.class.getName());

    @Override
    public void up(final IElevator elevator, final int where) {
        if (elevator.getCurrentFloor() != where) {
            LOGGER.info("Going Up to Floor : " + (elevator.getCurrentFloor() + 1));
            elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
            up(elevator, where);
        } else {
            this.rest(elevator);
        }
    }

    @Override
    public void down(final IElevator elevator, final int where) {
        throw new IllegalStateException("Elevator is ascending, Wait until it reach top floor.");
    }

    @Override
    public void rest(final IElevator elevator) {
        LOGGER.info("Open Elevator, Resting ...");
        elevator.setElevatorState(new RestingState());
        if (elevator.getCurrentFloor() != elevator.getNumberOfFloors()) {
            LOGGER.info("Close Elevator, Continue ...");
            elevator.setElevatorState(this);
        } else {
            LOGGER.info("Elevator reaches the top floor, Switch direction.");
            elevator.setElevatorState(new GoingDownState());
        }
    }


    @Override
    public void stop(IElevator elevator) {
        elevator.setElevatorState(new StopState());
    }
}
