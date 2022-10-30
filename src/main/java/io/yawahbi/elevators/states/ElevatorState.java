package io.yawahbi.elevators.states;

import io.yawahbi.elevators.IElevator;


public interface ElevatorState {
    
    void up(IElevator elevator, int where);
    
    void down(IElevator elevator, int where);
    
    void rest(IElevator elevator);

    void stop(IElevator elevator);
}
