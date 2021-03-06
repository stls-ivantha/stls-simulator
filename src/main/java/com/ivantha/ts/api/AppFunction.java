package com.ivantha.ts.api;

import com.ivantha.ts.service.AppServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppFunction {
    private Stage stage;

    // Start the app
    public void start(Stage stage) {
        try {
            this.stage = stage;

            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Scene scene = new Scene(root);
            scene.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.exit(0);
                }
            });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Traffic simulator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Stop the simulator
    public void stop() {
        stage.close();
    }

    // Start traffic
    public static void startTraffic(){
        AppServices.startTraffic();
    }

    // Stop traffic
    public static void stopTraffic(){
        AppServices.stopTraffic();
    }

    // Reset traffic
    public static void resetTraffic(){
        AppServices.resetTraffic();
    }
}
