package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.User;
import util.CrudUtil;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FogotPasswordFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXPasswordField pw;
    public JFXButton btnCancel;
    public JFXTextField txtPassword;
    public ImageView imageShowPassword;
    public Label lblRegex;
    public AnchorPane apnForgotPassword;
    public JFXButton btnShowPassword;

    String userPW = null;

    public void initialize(){
        txtPassword.setVisible(false);

        btnCancel.setOnMouseClicked(event -> {
            Util.closeApn(apnForgotPassword);
        });

        btnShowPassword.setOnMouseClicked(event -> {
            if (txtUserName.getText().equals("") || txtEmail.getText().equals("") || txtContact.getText().equals("")){
                btnShowPassword.setDisable(true);
                return;
            }
            // task
            // show pw
            txtPassword.setText(userPW);
            pw.setText(userPW);
        });

        imageShowPassword.setOnMouseClicked(event -> {
            if (txtPassword.isVisible()){
                txtPassword.setVisible(false);
                pw.setVisible(true);
            }else {
                txtPassword.setVisible(true);
                pw.setVisible(false);
            }
        });
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
        // validate uName
        // check and get user
        boolean isIn = false;
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM user");
            while (rs.next()){
                if (rs.getString(2).equals(txtUserName.getText()) || txtUserName.getText().equals("")){

                    isIn = true;
                    // get user
                    ResultSet rrs = CrudUtil.execute("SELECT password FROM `user` WHERE userId = ?",rs.getInt(1));
                    rrs.next();
                    userPW = rrs.getString(1);
                    // btn enable
                    btnShowPassword.setDisable(false);
                    lblRegex.setText("");

                    if (txtEmail.getText().equals(rs.getString(4)) || txtEmail.getText().equals("")){
                        // btn enable
                        btnShowPassword.setDisable(false);
                        lblRegex.setText("");
                        if (txtContact.getText().equals(rs.getString(5)) || txtContact.getText().equals("")){
                            btnShowPassword.setDisable(false);
                            lblRegex.setText("");
                        }else {
                            btnShowPassword.setDisable(true);
                            lblRegex.setText("Contact doesn't match");
                        }

                    }else {
                        // btn disable
                        btnShowPassword.setDisable(true);
                        lblRegex.setText("Email doesn't match");
                    }
                }
            }

            if (!isIn && !txtUserName.getText().equals("")){
                btnShowPassword.setDisable(true);
                lblRegex.setText("User not found");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
