package util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Util {
    static double x,y;

    public static void closeApn(AnchorPane apn){
        Stage stage = (Stage) apn.getScene().getWindow();
        stage.close();
    }

    public static void newUi(String location,String title) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Util.class.getResource("../view/" + location + ".fxml"));
        stage.setScene(new Scene(root));

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.setTitle(title);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void setUi(AnchorPane apn,String location,String title) throws IOException {
        Stage stage = (Stage) apn.getScene().getWindow();
        Parent root = FXMLLoader.load(Util.class.getResource("/view/" + location + ".fxml"));
        stage.setScene(new Scene(root));

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.setTitle(title);
        stage.centerOnScreen();
        stage.show();
    }

    public static void setChild(AnchorPane apn,String location) throws IOException {
        apn.getChildren().clear();
        Parent parent = FXMLLoader.load(Util.class.getResource("../view/"+location+".fxml"));
        apn.getChildren().add(parent);
    }

    /**
     * Get date and all relative details
     * @param date --> date label
     * @param day --> day label
     * @param time --> time label
     */
    public static void curDateTime(Label date, Label day, Label time) {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            date.setText(LocalDate.now().toString());
            time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            day.setText(LocalDate.now().getDayOfWeek().toString());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public static void clearFields(TextField...param){
        for (TextField txt : param) {
            txt.setText("");
        }
    }

    public static void clearCmb(ComboBox...param){
        for (ComboBox cmb : param) {
            cmb.getSelectionModel().select(-1);
        }
    }

    public static void clearLabels(Label...param){
        for (Label lbl : param) {
            lbl.setText("");
        }
    }
}
