package com.ivantha.ts.controller;

import com.ivantha.ts.common.Global;
import com.ivantha.ts.common.Session;
import com.ivantha.ts.service.AppServices;
import com.ivantha.ts.service.UIServices;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UIServices.drawMap(Session.getRoadMap(), backgroundCanvasAnchorPane);

        // Bind global environment values with sliders
        vehicleDensitySlider.valueProperty().bindBidirectional(Session.vehicleDensityProperty());
        averageGapSlider.valueProperty().bindBidirectional(Session.averageGapProperty());
        averageSpeedSlider.valueProperty().bindBidirectional(Session.averageSpeedProperty());

        AppServices.resetTraffic();

        // Update canvas
        Session.setUiUpdater(new Timeline(new KeyFrame(Duration.millis(Global.CANVAS_REFRESH_INTERVAL),event1 -> {
            Platform.runLater(() -> UIServices.refreshMap(Session.getRoadMap(), canvasAnchorPane));
        })));
        Session.getUiUpdater().setCycleCount(Timeline.INDEFINITE);

        startStopButton.setOnAction(event -> {
            if (!Session.isStarted()) {
                AppServices.startTraffic();
                startStopButton.setText("Stop");
            } else {
                AppServices.stopTraffic();
                startStopButton.setText("Start");
            }
        });

        resetButton.setOnAction(event -> AppServices.resetTraffic());

        // Bind road traffic toggle buttons
        northToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().northEnabledProperty());
        eastToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().eastEnabledProperty());
        southToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().southEnabledProperty());
        westToggleButton.selectedProperty().bindBidirectional(Session.getRoadMap().westEnabledProperty());

        // North Lane 1
        n1RedRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane1TrafficLight().redProperty());
        n1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane1TrafficLight().orangeProperty());
        n1GreenRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane1TrafficLight().greenProperty());

        // North Lane 2
        n2RedRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane2TrafficLight().redProperty());
        n2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane2TrafficLight().orangeProperty());
        n2GreenRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane2TrafficLight().greenProperty());

        // North Lane 3
        n3RedRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane3TrafficLight().redProperty());
        n3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane3TrafficLight().orangeProperty());
        n3GreenRadioButton.selectedProperty().bindBidirectional(Session.getNorthLane3TrafficLight().greenProperty());

        // East Lane 1
        e1RedRadioButton.selectedProperty().bindBidirectional(Session.getEastLane1TrafficLight().redProperty());
        e1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getEastLane1TrafficLight().orangeProperty());
        e1GreenRadioButton.selectedProperty().bindBidirectional(Session.getEastLane1TrafficLight().greenProperty());

        // East Lane 1
        e2RedRadioButton.selectedProperty().bindBidirectional(Session.getEastLane2TrafficLight().redProperty());
        e2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getEastLane2TrafficLight().orangeProperty());
        e2GreenRadioButton.selectedProperty().bindBidirectional(Session.getEastLane2TrafficLight().greenProperty());

        // East Lane 3
        e3RedRadioButton.selectedProperty().bindBidirectional(Session.getEastLane3TrafficLight().redProperty());
        e3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getEastLane3TrafficLight().orangeProperty());
        e3GreenRadioButton.selectedProperty().bindBidirectional(Session.getEastLane3TrafficLight().greenProperty());

        // South Lane 1
        s1RedRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane1TrafficLight().redProperty());
        s1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane1TrafficLight().orangeProperty());
        s1GreenRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane1TrafficLight().greenProperty());

        // South Lane 2
        s2RedRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane2TrafficLight().redProperty());
        s2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane2TrafficLight().orangeProperty());
        s2GreenRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane2TrafficLight().greenProperty());

        // South Lane 3
        s3RedRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane3TrafficLight().redProperty());
        s3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane3TrafficLight().orangeProperty());
        s3GreenRadioButton.selectedProperty().bindBidirectional(Session.getSouthLane3TrafficLight().greenProperty());

        // West Lane 1
        w1RedRadioButton.selectedProperty().bindBidirectional(Session.getWestLane1TrafficLight().redProperty());
        w1OrangeRadioButton.selectedProperty().bindBidirectional(Session.getWestLane1TrafficLight().orangeProperty());
        w1GreenRadioButton.selectedProperty().bindBidirectional(Session.getWestLane1TrafficLight().greenProperty());

        // West Lane 2
        w2RedRadioButton.selectedProperty().bindBidirectional(Session.getWestLane2TrafficLight().redProperty());
        w2OrangeRadioButton.selectedProperty().bindBidirectional(Session.getWestLane2TrafficLight().orangeProperty());
        w2GreenRadioButton.selectedProperty().bindBidirectional(Session.getWestLane2TrafficLight().greenProperty());

        // West Lane 3
        w3RedRadioButton.selectedProperty().bindBidirectional(Session.getWestLane3TrafficLight().redProperty());
        w3OrangeRadioButton.selectedProperty().bindBidirectional(Session.getWestLane3TrafficLight().orangeProperty());
        w3GreenRadioButton.selectedProperty().bindBidirectional(Session.getWestLane3TrafficLight().greenProperty());
    }
}
