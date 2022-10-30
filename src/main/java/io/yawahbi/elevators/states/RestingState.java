package io.yawahbi.elevators.states;

import io.yawahbi.elevators.IElevator;


public final class RestingState implements ElevatorState {
    @Override
    public void up(final IElevator elevator, final int where) {
        elevator.setElevatorState(new GoingUpState());
        elevator.getElevatorState().up(elevator, where);
    }

    @Override
    public void down(final IElevator elevator, final int where) {
        elevator.setElevatorState(new GoingDownState());
        elevator.getElevatorState().down(elevator, where);
    }

    @Override
    public void rest(final IElevator elevator) {
        throw new IllegalStateException("Elevator already resting.");
    }

    @Override
    public void stop(IElevator elevator) {
        elevator.setElevatorState(new StopState());
    }
}
