package controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import util.Util;

import java.io.IOException;
import java.sql.SQLException;

public class MainFormController {

    public AnchorPane apnMain;
    public static String userName = null;
    public Label lblUserName;
    public JFXTabPane tabPane;
    public Label lblDay;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        lblUserName.setText(userName);

        // set realtime clock
        Util.curDateTime(lblDate, lblDay, lblTime);

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        System.out.println("");
                    }
                }
        );

    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Util.setUi(apnMain,"LoginForm","L O G I N");
    }
}
