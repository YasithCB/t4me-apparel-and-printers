package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.CrudUtil;
import util.RegexPatterns;
import util.Util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginFormController extends Util {
    public ImageView imgLogo;
    public AnchorPane apnLogin;
    public AnchorPane apnWelcome;
    public ImageView imgWelcome;
    public Label lblNewUser;
    public JFXButton btnSwap;
    public AnchorPane apnMain;
    // signup
    public JFXTextField txtUserName;
    public JFXPasswordField pw;
    // reg
    public JFXTextField txtUserNameOnReg;
    public JFXPasswordField txtPwOnReg;
    public JFXPasswordField txtReEnterPwOnReg;
    public JFXTextField txtContact;
    public JFXTextField txtUserId;
    public JFXTextField txtEmail;
    public JFXButton btnReg;
    public JFXButton btnSignUp;
    public Label lblRegexOnSign;
    public Label lblRegexOnReg;
    public Label lblForgotPassword;
    public JFXTextField txtPassword;
    public ImageView imageShowPassword;

    public void initialize() {
        txtPassword.setVisible(false);
        logoFadeOut();
        genarateUserId();

        lblForgotPassword.setOnMouseClicked(event -> {
            try {
                Util.newUi("FogotPasswordForm","Forgot Password");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        imageShowPassword.setOnMouseClicked(event -> {
            txtPassword.setText(pw.getText());
            pw.setText(txtPassword.getText());

            if (txtPassword.isVisible()){
                txtPassword.setVisible(false);
                pw.setVisible(true);
            }else {
                txtPassword.setVisible(true);
                pw.setVisible(false);
            }
        });
    }

    private void genarateUserId() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT MAX(userId) FROM user");
            rs.next();
            txtUserId.setText(String.valueOf(rs.getInt(1) + 1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void emergeLogin() {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.4),apnWelcome);
        slide.setToX(-485);

        slide.setOnFinished(event -> {
            imgWelcome.setVisible(true);
            apnLogin.setVisible(true);
            lblNewUser.setVisible(true);
            btnSwap.setVisible(true);
        });

        slide.play();
    }

    private void logoFadeOut() {
        imgWelcome.setVisible(false);
        apnLogin.setVisible(false);
        lblNewUser.setVisible(false);
        btnSwap.setVisible(false);

        apnLogin.setTranslateX(477.5);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5),imgLogo);
        fadeOut.setFromValue(1);
        fadeOut.setDelay(Duration.seconds(1));
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeOut.setOnFinished(event -> {
            emergeLogin();
        });

        fadeOut.play();
    }

    public void btnSwapTabOnAction(ActionEvent actionEvent) {
        swapTab();
    }

    private void swapTab() {
        if (btnSwap.getText().equals("Register Now")){
            TranslateTransition slide = new TranslateTransition(Duration.seconds(0.2),apnLogin);
            slide.setToX(0);
            apnLogin.toBack();
            slide.play();

            lblNewUser.setText("Already have an account?");
            btnSwap.setText("Sign Up");
        }else {
            TranslateTransition slide = new TranslateTransition(Duration.seconds(0.2),apnLogin);
            slide.setToX(477.5);
            slide.play();

            lblNewUser.setText("New User?");
            btnSwap.setText("Register Now");
        }
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        signUser();
    }

    private void signUser() {
        if (!txtUserName.getText().equals("") && !pw.getText().equals("")){
            try {
                setUi(apnMain,"MainForm","T 4 M E");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        btnSignUp.setDisable(true);
    }

    public void btnRegOnAction(ActionEvent actionEvent) {
        registerUser();
    }

    private void registerUser() {
        try {
            if (CrudUtil.execute("INSERT INTO user(userName, password, email, contact, regDate) VALUES (?,?,?,?,NOW())",
                    txtUserNameOnReg.getText(),
                    txtPwOnReg.getText(),
                    txtEmail.getText(),
                    txtContact.getText()
            )){
                new Alert(Alert.AlertType.INFORMATION,"User Registered!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        swapTab();
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) throws IOException {
        // validate reg
        validateReg();
        // validate sign
        validateSign();

        goNextTextField(keyEvent);
    }

    private void goNextTextField(KeyEvent keyEvent) {
        // if user on sign
        if (txtUserName.isFocused() && keyEvent.getCode()== KeyCode.ENTER){
            pw.requestFocus();
        }else if (pw.isFocused() && keyEvent.getCode()==KeyCode.ENTER && !btnSignUp.isDisable()){
            signUser();
        }

        // if user on reg
        if (txtUserNameOnReg.isFocused() && keyEvent.getCode()== KeyCode.ENTER){
            txtContact.requestFocus();
        }else if (txtContact.isFocused() && keyEvent.getCode()==KeyCode.ENTER){
            txtEmail.requestFocus();
        }else if (txtEmail.isFocused() && keyEvent.getCode()==KeyCode.ENTER){
            txtPwOnReg.requestFocus();
        }else if (txtPwOnReg.isFocused() && keyEvent.getCode()==KeyCode.ENTER){
            txtReEnterPwOnReg.requestFocus();
        }else if (txtReEnterPwOnReg.isFocused() && keyEvent.getCode()==KeyCode.ENTER && !btnReg.isDisable()){
            registerUser();
        }
    }

    private void validateSign() {
        // validate uName
        // check and get user
        boolean isIn = false;
        try {
            ResultSet rs = CrudUtil.execute("SELECT userName,password FROM user");
            while (rs.next()){
                if (rs.getString(1).equals(txtUserName.getText()) || txtUserName.getText().equals("")){

                    isIn = true;
                    // btn enable
                    btnSignUp.setDisable(false);
                    lblRegexOnSign.setText("");

                    if (pw.getText().equals(rs.getString(2)) || pw.getText().equals("")){
                        // set uname to DashB
                        MainFormController.userName = rs.getString(1);
                        // btn enable
                        btnSignUp.setDisable(false);
                        lblRegexOnSign.setText("");
                        break;
                    }else {
                        // btn disable
                        btnSignUp.setDisable(true);
                        lblRegexOnSign.setText("Password incorrect");
                    }
                }
            }

            if (!isIn && !txtUserName.getText().equals("")){
                btnSignUp.setDisable(true);
                lblRegexOnSign.setText("User not found");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void validateReg() {
        // validate uName
        if (!RegexPatterns.uName.matcher(txtUserNameOnReg.getText()).matches() && !txtUserNameOnReg.getText().equals("")){
            btnReg.setDisable(true);
            lblRegexOnReg.setText("User name Invalid");
        }else {
            btnReg.setDisable(false);
            lblRegexOnReg.setText("");
            // validate contact
            if (!RegexPatterns.contact.matcher(txtContact.getText()).matches() && !txtContact.getText().equals("")) {
                btnReg.setDisable(true);
                lblRegexOnReg.setText("User Contact Invalid");
            }else {
                btnReg.setDisable(false);
                lblRegexOnReg.setText("");
                // validate email
                if (!RegexPatterns.email.matcher(txtEmail.getText()).matches() && !txtEmail.getText().equals("")) {
                    btnReg.setDisable(true);
                    lblRegexOnReg.setText("E-mail Invalid");
                }else {
                    btnReg.setDisable(false);
                    lblRegexOnReg.setText("");
                    // validate password
                    if (!RegexPatterns.pw.matcher(txtPwOnReg.getText()).matches() && !txtPwOnReg.getText().equals("")) {
                        btnReg.setDisable(true);
                        lblRegexOnReg.setText("Password doesn't meet minimum requirements");
                    }else {
                        btnReg.setDisable(false);
                        lblRegexOnReg.setText("");
                        // validate pw re entertered
                        if (!txtReEnterPwOnReg.getText().equals(txtPwOnReg.getText()) && !txtReEnterPwOnReg.getText().equals("")) {
                            btnReg.setDisable(true);
                            lblRegexOnReg.setText("Passwords doesn't match");
                        }else {
                            btnReg.setDisable(false);
                            lblRegexOnReg.setText("");
                        }
                    }
                }
            }
        }
    }
}
