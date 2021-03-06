package com.ivantha.ts.model;

import com.ivantha.ts.common.Session;

import java.util.HashMap;


public class Road {
    private final int roadId;
    private final HashMap<Integer, Lane> laneHashMap = new HashMap<>();

    public Road(int roadId) {
        this.roadId = roadId;

        laneHashMap.put(1, new Lane(1));            //In-lane 1
        laneHashMap.put(2, new Lane(2));            //In-lane 2
        laneHashMap.put(3, new Lane(3));            //In-lane 3

        laneHashMap.put(4, new Lane(4));            //Out-lane 1
        laneHashMap.put(5, new Lane(5));            //Out-lane 2
        laneHashMap.put(6, new Lane(6));            //Out-lane 3
    }

    public int getRoadId() {
        return roadId;
    }

    public Lane getLane(int laneId) {
        return laneHashMap.get(laneId);
    }

    public void populateRoad() {
        if (laneHashMap.get(1).getVehicleArrayList().size() + laneHashMap.get(2).getVehicleArrayList().size() +
                laneHashMap.get(3).getVehicleArrayList().size() < Session.getVehicleDensity()) {
            generateInVehicle();
        }
    }

    // Generate vehicle in In-lanes
    public void generateInVehicle() {
        int origin = roadId;
        int destinationDiff = 1 + (int) ((Math.random() + 0.4) * 2);
        int preDestination = origin + destinationDiff;

        int destination = preDestination;
        if (destination > 4) {
            destination = destination - 4;
        }

        Lane startLane = laneHashMap.get(destinationDiff);
        Vehicle vehicle = new Vehicle(origin, destination, destinationDiff, startLane.getLaneId());

        appendVehicleToInLane(vehicle, startLane);
    }

    // Add vehicle to lane
    private void appendVehicleToInLane(Vehicle vehicle, Lane lane) {
        if (lane.getVehicleArrayList().size() > 0) {
            Vehicle frontVehicle = lane.getVehicleArrayList().get(lane.getVehicleArrayList().size() - 1);
            if (vehicle.getTrajectory().getLocation() < frontVehicle.getTrajectory().getLocation() - frontVehicle.getLength() - Session.getAverageGap()) {
                vehicle.getTrajectory().setLocation(vehicle.getLength());
                vehicle.setVelocity(0.0);
                lane.addVehicleToQueue(vehicle);
            }
        } else {
            vehicle.getTrajectory().setLocation(vehicle.getLength());
            vehicle.setVelocity(0.0);
            lane.addVehicleToQueue(vehicle);
        }
    }

    // Add vehicles to remove queue
    public void appendVehicleToOutLane(Vehicle vehicle, int laneId) {
        vehicle.getTrajectory().setLocation(vehicle.getLength());
        laneHashMap.get(laneId).addVehicleToQueue(vehicle);
    }
}