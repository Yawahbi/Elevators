package io.yawahbi.elevators;


import java.util.HashMap;
import java.util.Map;


public class Building {
    private Map<String, IElevator> elevators;
    private Dispatcher dispatcher;

    
    public Building(int numberOfFloors, String... elevators) {
        this.elevators = new HashMap<>();
        dispatcher = Dispatcher.getInstance();
        for (String elevator : elevators) {
            String id = elevator.split(":")[0];
            int currentFloor = Integer.valueOf(elevator.split(":")[1]);
            IElevator e = new Elevator(id, currentFloor,numberOfFloors);
            this.elevators.put(id, e);
        }
        dispatcher.init(this.elevators,numberOfFloors);
    }

    
    public String requestElevator() {
        IElevator elevator = dispatcher.getClosestElevator();
        elevator.serve(dispatcher.getNumberOfFloors());
        return elevator.getId();
    }

    
    public String requestElevator(int floor) {
        IElevator elevator = dispatcher.getClosestElevator(floor);
        elevator.serve(floor);
        return elevator.getId();
    }

    
    public void stopAt(String elevatorId, int floor) {
        elevators.get(elevatorId).stop(floor);
    }

    
    public void move(String elevatorId, String direction) {
        IElevator elevator = elevators.get(elevatorId);
        switch (direction) {
            case "UP":
                elevator.getElevatorState().up(elevator, elevator.getCurrentFloor() + 1);
                break;
            case  "DOWN":
                elevator.getElevatorState().down(elevator, elevator.getCurrentFloor() - 1);
                break;
            default:
                throw new IllegalArgumentException("An elevator can move only UP or Down.");
        }
    }

}
