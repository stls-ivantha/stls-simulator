package com.ivantha.ts.controller;

import com.ivantha.ts.common.Global;
import com.ivantha.ts.common.Session;
import com.ivantha.ts.model.Lane;
import com.ivantha.ts.model.RoadMap;
import com.ivantha.ts.model.Vehicle;
import com.ivantha.ts.service.MapServices;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

import static com.ivantha.ts.common.Global.REFRESH_INTERVAL;
import static com.ivantha.ts.common.Global.ROAD_RADIUS;
import static com.ivantha.ts.model.TrafficLight.State.*;
import static java.lang.Math.PI;

public class DashboardController implements Initializable {
    @FXML
    private JFXToggleButton northToggleButton;
    @FXML
    private JFXToggleButton eastToggleButton;
    @FXML
    private JFXToggleButton southToggleButton;
    @FXML
    private JFXToggleButton westToggleButton;

    @FXML
    private JFXSlider vehicleDensitySlider;
    @FXML
    private JFXSlider averageSpeedSlider;
    @FXML
    private JFXSlider averageGapSlider;

    @FXML
    private AnchorPane canvasAnchorPane;
    @FXML
    private AnchorPane backgroundCanvasAnchorPane;

    @FXML
    private Button startStopButton;
    @FXML
    private Button resetButton;

    @FXML
    private RadioButton n1RedRadioButton;
    @FXML
    private RadioButton n1OrangeRadioButton;
    @FXML
    private RadioButton n1GreenRadioButton;
    @FXML
    private RadioButton n2RedRadioButton;
    @FXML
    private RadioButton n2OrangeRadioButton;
    @FXML
    private RadioButton n2GreenRadioButton;
    @FXML
    private RadioButton n3RedRadioButton;
    @FXML
    private RadioButton n3OrangeRadioButton;
    @FXML
    private RadioButton n3GreenRadioButton;
    @FXML
    private RadioButton e1RedRadioButton;
    @FXML
    private RadioButton e1OrangeRadioButton;
    @FXML
    private RadioButton e1GreenRadioButton;
    @FXML
    private RadioButton e2RedRadioButton;
    @FXML
    private RadioButton e2OrangeRadioButton;
    @FXML
    private RadioButton e2GreenRadioButton;
    @FXML
    private RadioButton e3RedRadioButton;
    @FXML
    private RadioButton e3OrangeRadioButton;
    @FXML
    private RadioButton e3GreenRadioButton;
    @FXML
    private RadioButton s1RedRadioButton;
    @FXML
    private RadioButton s1OrangeRadioButton;
    @FXML
    private RadioButton s1GreenRadioButton;
    @FXML
    private RadioButton s2RedRadioButton;
    @FXML
    private RadioButton s2OrangeRadioButton;
    @FXML
    private RadioButton s2GreenRadioButton;
    @FXML
    private RadioButton s3RedRadioButton;
    @FXML
    private RadioButton s3OrangeRadioButton;
    @FXML
    private RadioButton s3GreenRadioButton;
    @FXML
    private RadioButton w1RedRadioButton;
    @FXML
    private RadioButton w1OrangeRadioButton;
    @FXML
    private RadioButton w1GreenRadioButton;
    @FXML
    private RadioButton w2RedRadioButton;
    @FXML
    private RadioButton w2OrangeRadioButton;
    @FXML
    private RadioButton w2GreenRadioButton;
    @FXML
    private RadioButton w3RedRadioButton;
    @FXML
    private RadioButton w3OrangeRadioButton;
    @FXML
    private RadioButton w3GreenRadioButton;

    private Timeline uiUpdater;
    private Timer mainTimer;
    private TimerTask mainTimerTask;

    private boolean isStarted = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MapServices.drawMap(Session.getRoadMap(), backgroundCanvasAnchorPane);

        vehicleDensitySlider.valueProperty().bindBidirectional(Session.vehicleDensityProperty());
        averageGapSlider.valueProperty().bindBidirectional(Session.averageGapProperty());
        averageSpeedSlider.valueProperty().bindBidirectional(Session.averageSpeedProperty());

        reset();

        uiUpdater = new Timeline(new KeyFrame(Duration.millis(REFRESH_INTERVAL), event1 -> {
            Platform.runLater(() -> MapServices.refreshMap(Session.getRoadMap(), canvasAnchorPane));
        }));
        uiUpdater.setCycleCount(Timeline.INDEFINITE);

        startStopButton.setOnAction(event -> {
            if (!isStarted) {
                stop();

                mainTimer = new Timer();
                mainTimerTask = new CustomerTimerTask();
                mainTimer.schedule(mainTimerTask, 0, REFRESH_INTERVAL);
                uiUpdater.play();

                isStarted = true;
                startStopButton.setText("Stop");
            } else {
                stop();

                isStarted = false;
                startStopButton.setText("Start");
            }
        });

        resetButton.setOnAction(event -> reset());

        northToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().northEnabledProperty());
        eastToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().eastEnabledProperty());
        southToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().southEnabledProperty());
        westToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().westEnabledProperty());

        n1RedRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane1TrafficLight().redProperty());
        n1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane1TrafficLight().orangeProperty());
        n1GreenRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane1TrafficLight().greenProperty());

        n2RedRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane2TrafficLight().redProperty());
        n2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane2TrafficLight().orangeProperty());
        n2GreenRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane2TrafficLight().greenProperty());

        n3RedRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane3TrafficLight().redProperty());
        n3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane3TrafficLight().orangeProperty());
        n3GreenRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane3TrafficLight().greenProperty());

        e1RedRadioButton.selectedProperty().bindBidirectional(Session.getEastLane1TrafficLight().redProperty());
        e1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getEastLane1TrafficLight().orangeProperty());
        e1GreenRadioButton.selectedProperty().bindBidirectional(Session.getEastLane1TrafficLight().greenProperty());

        e2RedRadioButton.selectedProperty().bindBidirectional(Session.getEastLane2TrafficLight().redProperty());
        e2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getEastLane2TrafficLight().orangeProperty());
        e2GreenRadioButton.selectedProperty().bindBidirectional(Session.getEastLane2TrafficLight().greenProperty());

        e3RedRadioButton.selectedProperty().bindBidirectional(Session.getEastLane3TrafficLight().redProperty());
        e3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getEastLane3TrafficLight().orangeProperty());
        e3GreenRadioButton.selectedProperty().bindBidirectional(Session.getEastLane3TrafficLight().greenProperty());

        s1RedRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane1TrafficLight().redProperty());
        s1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane1TrafficLight().orangeProperty());
        s1GreenRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane1TrafficLight().greenProperty());

        s2RedRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane2TrafficLight().redProperty());
        s2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane2TrafficLight().orangeProperty());
        s2GreenRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane2TrafficLight().greenProperty());

        s3RedRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane3TrafficLight().redProperty());
        s3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane3TrafficLight().orangeProperty());
        s3GreenRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane3TrafficLight().greenProperty());

        w1RedRadioButton.selectedProperty().bindBidirectional(Session.getWestLane1TrafficLight().redProperty());
        w1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getWestLane1TrafficLight().orangeProperty());
        w1GreenRadioButton.selectedProperty().bindBidirectional(Session.getWestLane1TrafficLight().greenProperty());

        w2RedRadioButton.selectedProperty().bindBidirectional(Session.getWestLane2TrafficLight().redProperty());
        w2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getWestLane2TrafficLight().orangeProperty());
        w2GreenRadioButton.selectedProperty().bindBidirectional(Session.getWestLane2TrafficLight().greenProperty());

        w3RedRadioButton.selectedProperty().bindBidirectional(Session.getWestLane3TrafficLight().redProperty());
        w3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getWestLane3TrafficLight().orangeProperty());
        w3GreenRadioButton.selectedProperty().bindBidirectional(Session.getWestLane3TrafficLight().greenProperty());
    }

    private void stop() {
        if (null != mainTimer) {
            mainTimer.cancel();
            mainTimer.purge();
        }
        uiUpdater.stop();
    }

    private void reset() {
        Session.setRoadMap(new RoadMap());

        northToggleButton.setSelected(false);
        eastToggleButton.setSelected(false);
        southToggleButton.setSelected(false);
        westToggleButton.setSelected(false);
    }

    class CustomerTimerTask extends TimerTask {
        @Override
        public void run() {
            Session.getRoadMap().populateRoadMap();

            moveInLaneVehicles(Session.getnRoad().getLane(1));
            moveInLaneVehicles(Session.getnRoad().getLane(2));
            moveInLaneVehicles(Session.getnRoad().getLane(3));
            moveInLaneVehicles(Session.geteRoad().getLane(1));
            moveInLaneVehicles(Session.geteRoad().getLane(2));
            moveInLaneVehicles(Session.geteRoad().getLane(3));
            moveInLaneVehicles(Session.getsRoad().getLane(1));
            moveInLaneVehicles(Session.getsRoad().getLane(2));
            moveInLaneVehicles(Session.getsRoad().getLane(3));
            moveInLaneVehicles(Session.getwRoad().getLane(1));
            moveInLaneVehicles(Session.getwRoad().getLane(2));
            moveInLaneVehicles(Session.getwRoad().getLane(3));

            moveOutLaneVehicles(Session.getnRoad().getLane(6));
            moveOutLaneVehicles(Session.getnRoad().getLane(5));
            moveOutLaneVehicles(Session.getnRoad().getLane(4));
            moveOutLaneVehicles(Session.geteRoad().getLane(6));
            moveOutLaneVehicles(Session.geteRoad().getLane(5));
            moveOutLaneVehicles(Session.geteRoad().getLane(4));
            moveOutLaneVehicles(Session.getsRoad().getLane(6));
            moveOutLaneVehicles(Session.getsRoad().getLane(5));
            moveOutLaneVehicles(Session.getsRoad().getLane(4));
            moveOutLaneVehicles(Session.getwRoad().getLane(6));
            moveOutLaneVehicles(Session.getwRoad().getLane(5));
            moveOutLaneVehicles(Session.getwRoad().getLane(4));

            moveIntersectionVehiclesSmallArc(Session.getnIntRoad().get(7));
            moveIntersectionVehiclesStraight(Session.getnIntRoad().get(8));
            moveIntersectionVehiclesLargeArc(Session.getnIntRoad().get(9));
            moveIntersectionVehiclesSmallArc(Session.geteIntRoad().get(7));
            moveIntersectionVehiclesStraight(Session.geteIntRoad().get(8));
            moveIntersectionVehiclesLargeArc(Session.geteIntRoad().get(9));
            moveIntersectionVehiclesSmallArc(Session.getsIntRoad().get(7));
            moveIntersectionVehiclesStraight(Session.getsIntRoad().get(8));
            moveIntersectionVehiclesLargeArc(Session.getsIntRoad().get(9));
            moveIntersectionVehiclesSmallArc(Session.getwIntRoad().get(7));
            moveIntersectionVehiclesStraight(Session.getwIntRoad().get(8));
            moveIntersectionVehiclesLargeArc(Session.getwIntRoad().get(9));
        }

        public void moveInLaneVehicles(Lane lane) {
            lane.resetSensorArray();
            ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    if (v.getTrajectory().getLocation() >= Global.ROAD_LENGTH) {
                        boolean trafficLightGreen = Session.getJunction().getRoad(v.getTrajectory().origin).getLane(v.getTrajectory().startLaneId).getTrafficLight().getState() == GREEN;
                        boolean spaceAvalable = Session.getIntersection().getIntLane(v.getTrajectory().origin, 6 + v.getTrajectory().destinationDiff).isSapceAvailable(v);

                        if (trafficLightGreen && spaceAvalable) {
                            vehicleIterator.remove();
                            Session.getIntersection().appendVehicleToIntLane(v, v.getTrajectory().origin, 6 + v.getTrajectory().destinationDiff);

                            // Reset lane positions
                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();

                        // Check sensor positions
                        if(v.getTrajectory().getLocation() % Global.DPS <= v.getLength()){
                            lane.getSensorArray()[9 - ((int) (v.getTrajectory().getLocation() / Global.DPS) % 10)] = true;
                        }
                    }
                }
            }
        }

        public void moveOutLaneVehicles(Lane lane) {
            lane.resetSensorArray();
            ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    if (v.getTrajectory().getLocation() >= Global.ROAD_LENGTH) {
                        vehicleIterator.remove();

                        // Reset lane positions
                        for (int i = 0; i < vehicles.size(); i++) {
                            vehicles.get(i).getTrajectory().setLaneIndex(i);
                        }
                    } else {
                        v.move();

                        // Check sensor positions
                        if(v.getTrajectory().getLocation() % Global.DPS <= v.getLength()){
                            lane.getSensorArray()[(int) (v.getTrajectory().getLocation() / Global.DPS) % 10] = true;
                        }
                    }
                }
            }
        }

        public void moveIntersectionVehiclesSmallArc(Lane lane) {
            ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    double laneWidth = ROAD_RADIUS / 6;
                    double thetaRadSmall = v.getTrajectory().getLocation() / (laneWidth + v.getLength() / 2);
                    if (thetaRadSmall >= PI / 2) {
                        if (Session.getJunction().getRoad(v.getTrajectory().destination).getLane(3 + v.getTrajectory().destinationDiff).isSapceAvailable(v)) {
                            vehicleIterator.remove();
                            Session.getJunction().getRoad(v.getTrajectory().destination).appendVehicleToOutLane(v, 6);

                            // Reset lane positions
                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }

        public void moveIntersectionVehiclesStraight(Lane lane) {
            ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    if (v.getTrajectory().getLocation() >= ROAD_RADIUS * 2 + v.getLength()) {
                        if (Session.getJunction().getRoad(v.getTrajectory().destination).getLane(3 + v.getTrajectory().destinationDiff).isSapceAvailable(v)) {
                            vehicleIterator.remove();
                            Session.getJunction().getRoad(v.getTrajectory().destination).appendVehicleToOutLane(v, 5);

                            // Reset lane positions
                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }

        public void moveIntersectionVehiclesLargeArc(Lane lane) {
            ArrayList<Vehicle> vehicles = lane.getVehicleArrayList();
            synchronized (vehicles){
                Iterator<Vehicle> vehicleIterator = vehicles.iterator();

                while (vehicleIterator.hasNext()) {
                    Vehicle v = vehicleIterator.next();

                    double laneWidth = ROAD_RADIUS / 6;
                    double thetaRadLarge = v.getTrajectory().getLocation() / (ROAD_RADIUS + laneWidth + v.getLength() / 2);
                    if (thetaRadLarge >= PI / 2) {
                        if (Session.getJunction().getRoad(v.getTrajectory().destination).getLane(3 + v.getTrajectory().destinationDiff).isSapceAvailable(v)) {
                            vehicleIterator.remove();
                            Session.getJunction().getRoad(v.getTrajectory().destination).appendVehicleToOutLane(v, 4);

                            // Reset lane positions
                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).getTrajectory().setLaneIndex(i);
                            }
                        }
                    } else {
                        v.move();
                    }
                }
            }
        }
    }
}
