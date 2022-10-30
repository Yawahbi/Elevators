package io.yawahbi.elevators;

import io.yawahbi.elevators.states.GoingDownState;
import io.yawahbi.elevators.states.GoingUpState;
import io.yawahbi.elevators.states.RestingState;
import io.yawahbi.elevators.states.StopState;

import java.util.HashMap;
import java.util.Map;


public class Dispatcher {
    private Map<String, IElevator> elevators;
    private int numberOfFloors = 0;
    private static Dispatcher INSTANCE = null;

    private Dispatcher() {

    }

    public static Dispatcher getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Dispatcher();
        }
        return INSTANCE;
    }

    public void init(Map<String, IElevator> elevatorsMap, int buildingFloors) {
        this.elevators = elevatorsMap;
        this.numberOfFloors = buildingFloors;
    }

    
    public IElevator getClosestElevator() {
        return getClosestElevator(numberOfFloors);
    }

    
    public IElevator getClosestElevator(final int floor) {
        Map<String, Integer> priority = new HashMap<>();
        int minimum = numberOfFloors * 2;
        boolean downExist = false;
        boolean restExist = false;
        boolean topExist = false;
        String idTopElevator = "";
        String idMinElevator = "";
        String idRestElevator = "";
        for (Map.Entry<String, IElevator> elevator:elevators.entrySet()) {
            int stepsToServe = 0;
            int currentFloor = elevator.getValue().getCurrentFloor();
            if (elevator.getValue().getElevatorState() instanceof GoingUpState) {
                if (currentFloor > floor) {
                    stepsToServe = Math.abs(currentFloor - numberOfFloors);
                    stepsToServe += Math.abs(floor - numberOfFloors);
                }
                if (currentFloor < floor) {
                    stepsToServe = Math.abs(currentFloor - floor);
                }
            }
            if (elevator.getValue().getElevatorState() instanceof GoingDownState) {
                if (currentFloor > floor) {
                    stepsToServe = Math.abs(currentFloor - floor);
                }
                if (elevator.getValue().getCurrentFloor() < floor) {
                    stepsToServe = Math.abs(currentFloor - 1);
                    stepsToServe += floor;
                }
            }
            if (elevator.getValue().getElevatorState() instanceof RestingState) {
                if (elevator.getValue().getCurrentFloor() > floor) {
                    stepsToServe = Math.abs(currentFloor - floor);
                }
                if (elevator.getValue().getCurrentFloor() < floor) {
                    stepsToServe = Math.abs(currentFloor - floor);
                }
            }
            priority.put(elevator.getKey(), stepsToServe);
        }
        for (Map.Entry<String, Integer> elevator:priority.entrySet()) {
            if (elevator.getValue() <= minimum && elevators.get(elevator.getKey()).getElevatorState() instanceof GoingDownState &&  ! (elevators.get(elevator.getKey()).getElevatorState() instanceof StopState)) {
                minimum = elevator.getValue();
                idMinElevator = elevator.getKey();
                downExist = true;
            }
            if (elevator.getValue() <= minimum && elevators.get(elevator.getKey()).getElevatorState() instanceof RestingState && ! (elevators.get(elevator.getKey()).getElevatorState() instanceof StopState)) {
                minimum = elevator.getValue();
                idRestElevator = elevator.getKey();
                restExist = true;
            }
            if (elevator.getValue() <= minimum && elevators.get(elevator.getKey()).getElevatorState() instanceof GoingUpState && ! (elevators.get(elevator.getKey()).getElevatorState() instanceof StopState)) {
                minimum = elevator.getValue();
                idTopElevator = elevator.getKey();
                topExist = true;
            }
        }
        if (restExist) {
            return elevators.get(idRestElevator);
        }
        if (topExist) {
            return elevators.get(idTopElevator);
        }
        if (downExist) {
            return elevators.get(idMinElevator);
        }
        return null;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }
}
