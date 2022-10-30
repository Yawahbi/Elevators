package io.yawahbi.elevators.states;

import io.yawahbi.elevators.IElevator;

public final class StopState implements ElevatorState {
    @Override
    public void up(IElevator elevator, int where) {
        elevator.setElevatorState(new GoingUpState());
        elevator.getElevatorState().up(elevator, where);
    }

    @Override
    public void down(IElevator elevator, int where) {
        elevator.setElevatorState(new GoingDownState());
        elevator.getElevatorState().down(elevator, where);
    }

    @Override
    public void rest(IElevator elevator) {
        elevator.setElevatorState(new RestingState());
    }

    @Override
    public void stop(IElevator elevator) {
        throw new IllegalStateException("Elevator already stopping.");
    }
}
