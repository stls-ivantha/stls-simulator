package model;

import constant.LaneType;

import java.util.ArrayList;

public class Lane {
    public final int laneId;
    public final LaneType laneType;

    private final ArrayList<Vehicle> vehicles = new ArrayList<>();

    public Lane(int laneId) {
        this.laneId = laneId;

        switch (laneId){
            case 1:
            case 2:
            case 3:
                laneType = LaneType.IN_LANE;
                break;
            case 4:
            case 5:
            case 6:
                laneType = LaneType.OUT_LANE;
                break;
            case 7:
            case 8:
            case 9:
                laneType = LaneType.INTERSECTION_LANE;
                break;
            default:
                laneType = null;
                break;
        }
    }

    public int getLaneId() {
        return laneId;
    }
    public LaneType getLaneType() {
        return laneType;
    }
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicleToQueue(Vehicle vehicle){
        vehicles.add(vehicle);
        vehicle.trajectory.setLane(this);
        vehicle.trajectory.setLaneIndex(vehicles.size() - 1);
    }

}
