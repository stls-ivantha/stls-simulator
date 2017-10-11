package com.ivantha.ts.model;

public class TrafficLight {
    public enum TrafficLightState{
        RED,
        ORANGE,
        GREEN
    }

    private TrafficLightState state = TrafficLightState.RED;

    public TrafficLight() {
    }

    public TrafficLightState getState() {
        return state;
    }
    public void setState(TrafficLightState state) {
        this.state = state;
    }
}