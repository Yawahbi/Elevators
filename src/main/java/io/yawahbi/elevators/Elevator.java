package io.yawahbi.elevators;

import io.yawahbi.elevators.states.ElevatorState;
import io.yawahbi.elevators.states.RestingState;


public final class Elevator implements IElevator {
    private String id;
    private int numberOfFloors = 0;
    private int currentFloor;
    private ElevatorState elevatorState;

    
    public Elevator(final String elevatorId,
                    final int elevatorCurrentFloor,
                    final int buildingFloors) {
        this.id = elevatorId;
        this.currentFloor = elevatorCurrentFloor;
        elevatorState = new RestingState();
        numberOfFloors = buildingFloors;
    }

    @Override
    public ElevatorState getElevatorState() {
        return elevatorState;
    }

    @Override
    public void setElevatorState(final ElevatorState elevatorNewState) {
        this.elevatorState = elevatorNewState;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String elevatorId) {
        this.id = elevatorId;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public void setCurrentFloor(final int elevatorCurrentFloor) {
        this.currentFloor = elevatorCurrentFloor;
    }

    @Override
    public void serve(final int floor) {
        if (floor > currentFloor) {
            try {
                elevatorState.up(this, floor);
            } catch (IllegalStateException e) {
                elevatorState.down(this, 1);
                elevatorState.up(this, floor);
            }
        } else if (floor < currentFloor) {
            try {
                elevatorState.down(this, floor);
            } catch (IllegalStateException e) {
                elevatorState.up(this, numberOfFloors);
                elevatorState.down(this, floor);
            }
        }
    }

    @Override
    public void stop(final int floor) {
        serve(floor);
        elevatorState.stop(this);
    }

    @Override
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }
}
