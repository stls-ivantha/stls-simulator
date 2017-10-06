package controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import constant.LaneType;
import javafx.scene.shape.Rectangle;
import model.*;
import util.Draw;
import main.Global;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

import static constant.LaneType.INTERSECTION_LANE;
import static java.lang.Math.PI;
import static main.Global.REFRESH_INTERVAL;
import static constant.LaneType.IN_LANE;
import static constant.LaneType.OUT_LANE;
import static main.Global.ROAD_RADIUS;

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

    private Timeline uiUpdater;
    private Timer mainTimer;
    private TimerTask mainTimerTask;

    private RoadMap roadMap;
    private Road nRoad;
    private Road eRoad;
    private Road sRoad;
    private Road wRoad;
    private Intersection intersection;
    private HashMap<Integer, Lane> nIntRoad;
    private HashMap<Integer, Lane> eIntRoad;
    private HashMap<Integer, Lane> sIntRoad;
    private HashMap<Integer, Lane> wIntRoad;

    private boolean isStarted = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Draw.drawMap(roadMap, backgroundCanvasAnchorPane);

        vehicleDensitySlider.valueProperty().bindBidirectional(Global.VEHICLE_DENSITY);
        averageGapSlider.valueProperty().bindBidirectional(Global.AVERAGE_GAP);
        averageSpeedSlider.valueProperty().bindBidirectional(Global.AVERAGE_SPEED);

        reset();

        uiUpdater = new Timeline(new KeyFrame(Duration.millis(REFRESH_INTERVAL), event1 -> {
            Platform.runLater(() -> Draw.refreshMap(roadMap, canvasAnchorPane));
        }));
        uiUpdater.setCycleCount(Timeline.INDEFINITE);

        startStopButton.setOnAction(event -> {
            if(!isStarted){
                stop();

                mainTimer = new Timer();
                mainTimerTask = new CustomerTimerTask();
                mainTimer.schedule(mainTimerTask, 0, REFRESH_INTERVAL);
                uiUpdater.play();

                isStarted = true;
                startStopButton.setText("Stop");
            }else{
                stop();

                isStarted = false;
                startStopButton.setText("Start");
            }
        });

        resetButton.setOnAction(event -> reset());

        northToggleButton.setOnAction(event -> roadMap.setNorthEnabled(northToggleButton.isSelected()));
        eastToggleButton.setOnAction(event -> roadMap.setEastEnabled(eastToggleButton.isSelected()));
        southToggleButton.setOnAction(event -> roadMap.setSouthEnabled(southToggleButton.isSelected()));
        westToggleButton.setOnAction(event -> roadMap.setWestEnabled(westToggleButton.isSelected()));
    }

    private void stop() {
        if (null != mainTimer) {
            mainTimer.cancel();
            mainTimer.purge();
        }
        uiUpdater.stop();
    }

    private void reset() {
        roadMap = new RoadMap();

        nRoad = roadMap.getJunction().getRoad(1);
        eRoad = roadMap.getJunction().getRoad(2);
        sRoad = roadMap.getJunction().getRoad(3);
        wRoad = roadMap.getJunction().getRoad(4);

        intersection = roadMap.getJunction().getIntersection();

        nIntRoad = intersection.getNorthIntRoad();
        eIntRoad = intersection.getEastIntRoad();
        sIntRoad = intersection.getSouthIntRoad();
        wIntRoad = intersection.getWestIntRoad();
    }

    class CustomerTimerTask extends TimerTask {
        @Override
        public void run() {
            roadMap.populateRoadMap();

            moveVehicles(nRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(nRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(nRoad.getLane(3).getVehicles(), IN_LANE);
            moveVehicles(eRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(eRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(eRoad.getLane(3).getVehicles(), IN_LANE);
            moveVehicles(sRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(sRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(sRoad.getLane(3).getVehicles(), IN_LANE);
            moveVehicles(wRoad.getLane(1).getVehicles(), IN_LANE);
            moveVehicles(wRoad.getLane(2).getVehicles(), IN_LANE);
            moveVehicles(wRoad.getLane(3).getVehicles(), IN_LANE);

            moveVehicles(nRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(nRoad.getLane(4).getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(eRoad.getLane(4).getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(sRoad.getLane(4).getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getLane(6).getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getLane(5).getVehicles(), OUT_LANE);
            moveVehicles(wRoad.getLane(4).getVehicles(), OUT_LANE);

            moveVehicles(nIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(nIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(nIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
            moveVehicles(eIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(eIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(eIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
            moveVehicles(sIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(sIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(sIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
            moveVehicles(wIntRoad.get(7).getVehicles(), INTERSECTION_LANE);
            moveVehicles(wIntRoad.get(8).getVehicles(), INTERSECTION_LANE);
            moveVehicles(wIntRoad.get(9).getVehicles(), INTERSECTION_LANE);
        }

        public void moveVehicles(ArrayList<Vehicle> vehicles, LaneType laneType) {
            Iterator<Vehicle> vehicleIterator = vehicles.iterator();

            while (vehicleIterator.hasNext()) {
                Vehicle v = vehicleIterator.next();
//                v.move();

                switch(laneType){
                    case IN_LANE:
                        if(v.trajectory.getLocation() >= Global.ROAD_LENGTH){
                            if(intersection.getIntLane(v.trajectory.origin, 6 + v.trajectory.destinationDiff).isSapceAvailable(v)){
                                vehicleIterator.remove();
                                intersection.appendVehicleToIntLane(v, v.trajectory.origin, 6 + v.trajectory.destinationDiff);

                                for (int i = 0; i < vehicles.size(); i++) {
                                    vehicles.get(i).trajectory.setLaneIndex(i);
                                }
                            }
                        }else{
                            v.move();
                        }
                        break;
                    case OUT_LANE:
                        if(v.trajectory.getLocation() >= Global.ROAD_LENGTH){
                            vehicleIterator.remove();

                            for (int i = 0; i < vehicles.size(); i++) {
                                vehicles.get(i).trajectory.setLaneIndex(i);
                            }
                        }else{
                            v.move();
                        }
                        break;
                    case INTERSECTION_LANE:
                        double laneWidth = ROAD_RADIUS / 6;
                        switch (v.trajectory.destinationDiff) {
                            case 1:
                                double thetaRadSmall = v.trajectory.getLocation() / (laneWidth + v.length / 2);
                                if (thetaRadSmall >= PI / 2) {
                                    if(roadMap.getJunction().getRoad(v.trajectory.destination).getLane(6).isSapceAvailable(v)){
                                        vehicleIterator.remove();
                                        roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 6);

                                        for (int i = 0; i < vehicles.size(); i++) {
                                            vehicles.get(i).trajectory.setLaneIndex(i);
                                        }
                                    }
                                } else {
                                    v.move();
                                }
                                break;
                            case 2:
                                if (v.trajectory.getLocation() >= ROAD_RADIUS * 2 + v.length) {
                                    if(roadMap.getJunction().getRoad(v.trajectory.destination).getLane(5).isSapceAvailable(v)){
                                        vehicleIterator.remove();
                                        roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 5);

                                        for (int i = 0; i < vehicles.size(); i++) {
                                            vehicles.get(i).trajectory.setLaneIndex(i);
                                        }
                                    }
                                } else {
                                    v.move();
                                }
                                break;
                            case 3:
                                double thetaRadLarge = v.trajectory.getLocation() / (ROAD_RADIUS + laneWidth + v.length / 2);
                                if (thetaRadLarge >= PI / 2) {
                                    if(roadMap.getJunction().getRoad(v.trajectory.destination).getLane(4).isSapceAvailable(v)){
                                        vehicleIterator.remove();
                                        roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 4);

                                        for (int i = 0; i < vehicles.size(); i++) {
                                            vehicles.get(i).trajectory.setLaneIndex(i);
                                        }
                                    }
                                } else {
                                    v.move();
                                }
                                break;
                        }
                        break;
                }


//                if(laneType == INTERSECTION_LANE){
//                    double laneWidth = ROAD_RADIUS / 6;
//
//                    switch (v.trajectory.destinationDiff){
//                        case 1:
//                            double thetaRadSmall = v.trajectory.getLocation() / (laneWidth + v.length / 2);
//                            if(thetaRadSmall >= PI / 2){
//                                vehicleIterator.remove();
//                                roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 6);
//
//                                for (int i = 0; i < vehicles.size(); i++) {
//                                    vehicles.get(i).trajectory.setLaneIndex(i);
//                                }
//                            }
//                            break;
//                        case 2:
//                            if(v.trajectory.getLocation() >= ROAD_RADIUS * 2 + v.length){
//                                vehicleIterator.remove();
//                                roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 5);
//
//                                for (int i = 0; i < vehicles.size(); i++) {
//                                    vehicles.get(i).trajectory.setLaneIndex(i);
//                                }
//                            }
//                            break;
//                        case 3:
//                            double thetaRadLarge = v.trajectory.getLocation() / (ROAD_RADIUS + laneWidth + v.length / 2);
//                            if(thetaRadLarge >= PI / 2){
//                                vehicleIterator.remove();
//                                roadMap.getJunction().getRoad(v.trajectory.destination).appendVehicleToOutLane(v, 4);
//
//                                for (int i = 0; i < vehicles.size(); i++) {
//                                    vehicles.get(i).trajectory.setLaneIndex(i);
//                                }
//                            }
//                            break;
//                    }
//                }else{
//                    if(v.trajectory.getLocation() >= Global.ROAD_LENGTH){
//                        vehicleIterator.remove();
//
//                        if(laneType == IN_LANE){
//                            intersection.appendVehicleToIntLane(v, v.trajectory.origin, 6 + v.trajectory.destinationDiff);
//                        }
//
//                        for (int i = 0; i < vehicles.size(); i++) {
//                            vehicles.get(i).trajectory.setLaneIndex(i);
//                        }
//                    }
//                }
            }
        }
    }
}
