package com.ivantha.ts.util;

import com.ivantha.ts.constant.Direction;
import com.ivantha.ts.constant.LaneType;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import com.ivantha.ts.model.RoadMap;
import com.ivantha.ts.model.Vehicle;

import java.util.Collection;
import java.util.Iterator;

import static com.ivantha.ts.constant.Direction.*;
import static com.ivantha.ts.constant.LaneType.IN_LANE;
import static com.ivantha.ts.constant.LaneType.OUT_LANE;
import static java.lang.Math.PI;
import static com.ivantha.ts.common.Global.*;

public class Draw {

    public static void drawMap(RoadMap roadMap, AnchorPane canvas){
        Group g = new Group();

        Rectangle vRect = new Rectangle(ROAD_LENGTH, 0, ROAD_RADIUS * 2, CANVAS_RADIUS * 2);
        vRect.getStyleClass().add("roadSurface");
        g.getChildren().add(vRect);

        Rectangle hRect = new Rectangle(0, ROAD_LENGTH, CANVAS_RADIUS * 2, ROAD_RADIUS * 2);
        hRect.getStyleClass().add("roadSurface");
        g.getChildren().add(hRect);

        Polyline p = new Polyline();
        p.getPoints().addAll(CANVAS_RADIUS + ROAD_RADIUS, 0.0,
                CANVAS_RADIUS + ROAD_RADIUS, ROAD_LENGTH,
                2 * CANVAS_RADIUS, ROAD_LENGTH,
                2 * CANVAS_RADIUS, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, 2 * CANVAS_RADIUS,
                ROAD_LENGTH, 2 * CANVAS_RADIUS,
                ROAD_LENGTH, CANVAS_RADIUS + ROAD_RADIUS,
                0.0, CANVAS_RADIUS + ROAD_RADIUS,
                0.0, ROAD_LENGTH,
                ROAD_LENGTH, ROAD_LENGTH,
                ROAD_LENGTH, 0.0);
        g.getChildren().add(p);

        Line lVerticle = new Line(CANVAS_RADIUS, 0, CANVAS_RADIUS, 2 * CANVAS_RADIUS);
        lVerticle.getStyleClass().add("midLine");
        g.getChildren().add(lVerticle);

        Line lHorizontal = new Line(0, CANVAS_RADIUS, 2 * CANVAS_RADIUS, CANVAS_RADIUS);
        lHorizontal.getStyleClass().add("midLine");
        g.getChildren().add(lHorizontal);

        Line markLeft = new Line(ROAD_LENGTH, ROAD_LENGTH,
                ROAD_LENGTH, CANVAS_RADIUS + ROAD_RADIUS);
        markLeft.getStyleClass().add("markLine");
        g.getChildren().add(markLeft);

        Line markRight = new Line(CANVAS_RADIUS + ROAD_RADIUS, ROAD_LENGTH,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS);
        markRight.getStyleClass().add("markLine");
        g.getChildren().add(markRight);

        Line markTop = new Line(ROAD_LENGTH, ROAD_LENGTH,
                CANVAS_RADIUS + ROAD_RADIUS, ROAD_LENGTH);
        markTop.getStyleClass().add("markLine");
        g.getChildren().add(markTop);

        Line markBottom = new Line(ROAD_LENGTH, CANVAS_RADIUS + ROAD_RADIUS,
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + ROAD_RADIUS);
        markBottom.getStyleClass().add("markLine");
        g.getChildren().add(markBottom);

        Line lMidVerticle1 = new Line(CANVAS_RADIUS - ROAD_RADIUS / 3, 0, CANVAS_RADIUS - ROAD_RADIUS / 3, 2 * CANVAS_RADIUS);
        lMidVerticle1.getStyleClass().add("markLine");
        g.getChildren().add(lMidVerticle1);

        Line lMidVerticle2 = new Line(CANVAS_RADIUS - ROAD_RADIUS / 3 * 2, 0, CANVAS_RADIUS - ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS);
        lMidVerticle2.getStyleClass().add("markLine");
        g.getChildren().add(lMidVerticle2);

        Line rMidVerticle1 = new Line(CANVAS_RADIUS + ROAD_RADIUS / 3, 0, CANVAS_RADIUS + ROAD_RADIUS / 3, 2 * CANVAS_RADIUS);
        rMidVerticle1.getStyleClass().add("markLine");
        g.getChildren().add(rMidVerticle1);

        Line rMidVerticle2 = new Line(CANVAS_RADIUS + ROAD_RADIUS / 3 * 2, 0, CANVAS_RADIUS + ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS);
        rMidVerticle2.getStyleClass().add("markLine");
        g.getChildren().add(rMidVerticle2);

        Line topMidHorizontal1 = new Line(0, CANVAS_RADIUS - ROAD_RADIUS / 3, 2 * CANVAS_RADIUS, CANVAS_RADIUS - ROAD_RADIUS / 3);
        topMidHorizontal1.getStyleClass().add("markLine");
        g.getChildren().add(topMidHorizontal1);

        Line topMidHorizontal2 = new Line(0, CANVAS_RADIUS - ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS, CANVAS_RADIUS - ROAD_RADIUS / 3 * 2);
        topMidHorizontal2.getStyleClass().add("markLine");
        g.getChildren().add(topMidHorizontal2);

        Line downMidHorizontal1 = new Line(0, CANVAS_RADIUS + ROAD_RADIUS / 3, 2 * CANVAS_RADIUS, CANVAS_RADIUS + ROAD_RADIUS / 3);
        downMidHorizontal1.getStyleClass().add("markLine");
        g.getChildren().add(downMidHorizontal1);

        Line downMidHorizontal2 = new Line(0, CANVAS_RADIUS + ROAD_RADIUS / 3 * 2, 2 * CANVAS_RADIUS, CANVAS_RADIUS + ROAD_RADIUS / 3 * 2);
        downMidHorizontal2.getStyleClass().add("markLine");
        g.getChildren().add(downMidHorizontal2);

        canvas.getChildren().add(g);
    }

    public static void refreshMap(RoadMap roadMap, AnchorPane canvas){
        canvas.getChildren().clear();

        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(1).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(2).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(3).getVehicles(), NORTH, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(1).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(2).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(3).getVehicles(), EAST, IN_LANE, ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(1).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(2).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(3).getVehicles(), SOUTH, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(1).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(2).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(3).getVehicles(), WEST, IN_LANE, (-1) * ROAD_RADIUS / 6 * 1);

        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(4).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(5).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(1).getLane(6).getVehicles(), NORTH, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(4).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(5).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(2).getLane(6).getVehicles(), EAST, OUT_LANE, (-1) * ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(4).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(5).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(3).getLane(6).getVehicles(), SOUTH, OUT_LANE,  ROAD_RADIUS / 6 * 5);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(4).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 1);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(5).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 3);
        drawVehiclesOnLane(canvas, roadMap.getJunction().getRoad(4).getLane(6).getVehicles(), WEST, OUT_LANE, ROAD_RADIUS / 6 * 5);

        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getNorthIntRoad().get(7).getVehicles(),
                CANVAS_RADIUS + (ROAD_RADIUS / 6 * 5), CANVAS_RADIUS - ROAD_RADIUS,
                ROAD_RADIUS / 6, 1,7);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getNorthIntRoad().get(8).getVehicles(),
                CANVAS_RADIUS + (ROAD_RADIUS / 6 * 3), CANVAS_RADIUS - ROAD_RADIUS,
                0, 1, 8);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getNorthIntRoad().get(9).getVehicles(),
                CANVAS_RADIUS + (ROAD_RADIUS / 6 * 1), CANVAS_RADIUS - ROAD_RADIUS,
                ROAD_RADIUS + ROAD_RADIUS / 6, 1, 9);

        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getEastIntRoad().get(7).getVehicles(),
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + (ROAD_RADIUS / 6 * 5),
                ROAD_RADIUS / 6,2, 7);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getEastIntRoad().get(8).getVehicles(),
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + (ROAD_RADIUS / 6 * 3),
                0, 2, 8);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getEastIntRoad().get(9).getVehicles(),
                CANVAS_RADIUS + ROAD_RADIUS, CANVAS_RADIUS + (ROAD_RADIUS / 6 * 1),
                ROAD_RADIUS + ROAD_RADIUS / 6, 2, 9);

        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getSouthIntRoad().get(7).getVehicles(),
                CANVAS_RADIUS - (ROAD_RADIUS / 6 * 5), CANVAS_RADIUS + ROAD_RADIUS,
                ROAD_RADIUS / 6, 3,7);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getSouthIntRoad().get(8).getVehicles(),
                CANVAS_RADIUS - (ROAD_RADIUS / 6 * 3), CANVAS_RADIUS + ROAD_RADIUS,
                0, 3, 8);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getSouthIntRoad().get(9).getVehicles(),
                CANVAS_RADIUS - (ROAD_RADIUS / 6 * 1), CANVAS_RADIUS + ROAD_RADIUS,
                ROAD_RADIUS + ROAD_RADIUS / 6, 3, 9);

        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getWestIntRoad().get(7).getVehicles(),
                ROAD_LENGTH, CANVAS_RADIUS - (ROAD_RADIUS / 6 * 5),
                ROAD_RADIUS / 6,4, 7);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getWestIntRoad().get(8).getVehicles(),
                ROAD_LENGTH, CANVAS_RADIUS - (ROAD_RADIUS / 6 * 3),
                0, 4, 8);
        drawVehiclesOnIntersection(canvas, roadMap.getJunction().getIntersection().getWestIntRoad().get(9).getVehicles(),
                ROAD_LENGTH, CANVAS_RADIUS - (ROAD_RADIUS / 6 * 1),
                ROAD_RADIUS + ROAD_RADIUS / 6, 4, 9);
    }

    private static void drawVehiclesOnLane(AnchorPane canvas, Collection<Vehicle> vehicles, Direction direction, LaneType laneType, double offset) {
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();
        switch (direction) {
            case NORTH:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.length);
                    rect.setWidth(vehicle.width);

                    rect.setX(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setY(vehicle.trajectory.getLocation() - vehicle.length);
                    }else {
                        rect.setY(ROAD_LENGTH - vehicle.trajectory.getLocation());
                    }

                    canvas.getChildren().add(rect);
                }
                break;
            case EAST:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.width);
                    rect.setWidth(vehicle.length);

                    rect.setY(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setX((2 * CANVAS_RADIUS) - vehicle.trajectory.getLocation());
                    }else {
                        rect.setX(CANVAS_RADIUS + ROAD_RADIUS + vehicle.trajectory.getLocation() - vehicle.length);
                    }

                    canvas.getChildren().add(rect);
                }
                break;
            case SOUTH:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.length);
                    rect.setWidth(vehicle.width);

                    rect.setX(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setY((2 * CANVAS_RADIUS) - vehicle.trajectory.getLocation());
                    }else {
                        rect.setY(CANVAS_RADIUS + ROAD_RADIUS + vehicle.trajectory.getLocation() - vehicle.length);
                    }

                    canvas.getChildren().add(rect);
                }
                break;
            case WEST:
                while (vehicleIterator.hasNext()) {
                    Vehicle vehicle = vehicleIterator.next();

                    Rectangle rect = new Rectangle();
                    rect.setFill(vehicle.getColor());

                    double adjustedOffset = offset - vehicle.width / 2;

                    rect.setHeight(vehicle.width);
                    rect.setWidth(vehicle.length);

                    rect.setY(CANVAS_RADIUS + adjustedOffset);
                    if(laneType == IN_LANE){
                        rect.setX(vehicle.trajectory.getLocation() - vehicle.length);
                    }else {
                        rect.setX(ROAD_LENGTH - vehicle.trajectory.getLocation());
                    }

                    canvas.getChildren().add(rect);
                }
                break;
        }
    }

    public static void drawVehiclesOnIntersection(AnchorPane canvas, Collection<Vehicle> vehicles, double startX, double startY, double radius, int originRoadId, int originLaneId){
        Iterator<Vehicle> vehicleIterator = vehicles.iterator();

        while (vehicleIterator.hasNext()) {
            Vehicle vehicle = vehicleIterator.next();

            Rectangle rect = new Rectangle();
            rect.setFill(vehicle.getColor());

            double thetaRad = vehicle.trajectory.getLocation() / (radius + vehicle.length / 2);

            switch (originRoadId){
                case 1:
                    rect.setHeight(vehicle.length);
                    rect.setWidth(vehicle.width);

                    switch (originLaneId){
                        case 7:
                            rect.setX(startX - vehicle.width / 2);
                            rect.setY(startY - vehicle.length);

                            rect.getTransforms().add(new Rotate((-1) * thetaRad * 180 / PI, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2, ROAD_LENGTH - vehicle.length / 2));
                            break;
                        case 8:
                            rect.setHeight(vehicle.length);
                            rect.setWidth(vehicle.width);

                            rect.setX(startX - vehicle.width / 2);
                            rect.setY(ROAD_LENGTH + vehicle.trajectory.getLocation() - vehicle.length);
                            break;
                        case 9:
                            rect.setX(startX - vehicle.width / 2);
                            rect.setY(startY - vehicle.length);

                            rect.getTransforms().add(new Rotate(thetaRad * 180 / PI, ROAD_LENGTH - vehicle.length / 2, ROAD_LENGTH - vehicle.length / 2));
                            break;
                    }
                    break;
                case 2:
                    rect.setHeight(vehicle.width);
                    rect.setWidth(vehicle.length);

                    switch (originLaneId){
                        case 7:
                            rect.setX(startX);
                            rect.setY(startY - vehicle.width / 2);

                            rect.getTransforms().add(new Rotate((-1) * thetaRad * 180 / PI, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2));
                            break;
                        case 8:
                            rect.setHeight(vehicle.width);
                            rect.setWidth(vehicle.length);

                            rect.setX(CANVAS_RADIUS + ROAD_RADIUS - vehicle.trajectory.getLocation());
                            rect.setY(startY - vehicle.width / 2);
                            break;
                        case 9:
                            rect.setX(startX);
                            rect.setY(startY - vehicle.width / 2);

                            rect.getTransforms().add(new Rotate(thetaRad * 180 / PI, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2, ROAD_LENGTH - vehicle.length / 2));
                            break;
                    }
                    break;
                case 3:
                    rect.setHeight(vehicle.length);
                    rect.setWidth(vehicle.width);

                    switch (originLaneId){
                        case 7:
                            rect.setX(startX - vehicle.width / 2);
                            rect.setY(startY);

                            rect.getTransforms().add(new Rotate((-1) * thetaRad * 180 / PI, ROAD_LENGTH - vehicle.length / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2));
                            break;
                        case 8:
                            rect.setHeight(vehicle.length);
                            rect.setWidth(vehicle.width);

                            rect.setX(startX - vehicle.width / 2);
                            rect.setY(CANVAS_RADIUS + ROAD_RADIUS - vehicle.trajectory.getLocation());
                            break;
                        case 9:
                            rect.setX(startX - vehicle.width / 2);
                            rect.setY(startY);

                            rect.getTransforms().add(new Rotate(thetaRad * 180 / PI, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2));
                            break;
                    }
                    break;
                case 4:
                    rect.setHeight(vehicle.width);
                    rect.setWidth(vehicle.length);

                    switch (originLaneId){
                        case 7:
                            rect.setX(startX - vehicle.length);
                            rect.setY(startY - vehicle.width / 2);

                            rect.getTransforms().add(new Rotate((-1) * thetaRad * 180 / PI, ROAD_LENGTH - vehicle.length / 2, ROAD_LENGTH - vehicle.length / 2));
                            break;
                        case 8:
                            rect.setHeight(vehicle.width);
                            rect.setWidth(vehicle.length);

                            rect.setX(ROAD_LENGTH + vehicle.trajectory.getLocation() - vehicle.length);
                            rect.setY(startY - vehicle.width / 2);
                            break;
                        case 9:
                            rect.setX(startX - vehicle.length);
                            rect.setY(startY - vehicle.width / 2);

                            rect.getTransforms().add(new Rotate(thetaRad * 180 / PI, ROAD_LENGTH - vehicle.length / 2, CANVAS_RADIUS + ROAD_RADIUS + vehicle.length / 2));
                            break;
                    }
                    break;
            }

            canvas.getChildren().add(rect);
        }
    }
}