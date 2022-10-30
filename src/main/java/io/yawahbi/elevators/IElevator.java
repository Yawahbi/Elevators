package io.yawahbi.elevators;

import io.yawahbi.elevators.states.ElevatorState;

public interface IElevator {

    String getId();
    
    int getCurrentFloor();

    void setCurrentFloor(int elevatorCurrentFloor);
    
    ElevatorState getElevatorState();
    
    void setElevatorState(ElevatorState elevatorNewState);
    
    int getNumberOfFloors();

    void setNumberOfFloors(int numberOfFloors);
    
    void serve(int floor);
    
    void stop(int floor);
}
