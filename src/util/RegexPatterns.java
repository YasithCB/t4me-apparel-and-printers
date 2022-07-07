package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Pattern;

public class RegexPatterns {

    public static Pattern uName = Pattern.compile("^[A-z ]{3,30}$");
    public static Pattern contact = Pattern.compile("^[0-9]{10}$");
    public static Pattern address = Pattern.compile("^[A-z0-9 ,/]{4,40}$");
    public static Pattern email = Pattern.compile("^[a-z._]*(@[a-z]{2,10}[.a-z]{1,5})$");
    public static Pattern pw = Pattern.compile("^[A-z0-9_@#*^!]{8,15}$");
    public static Pattern orderId = Pattern.compile("^[0-9]*$");
    public static Pattern trackingNo = Pattern.compile("^[A-Z0-9-]*$");
    public static Pattern price = Pattern.compile("^[0-9]*(.[0-9]{2})?$");
    public static Pattern customerId = Pattern.compile("^[1-9][0-9]*$");
    public static Pattern productId = Pattern.compile("^[A-Z][a-z]*(-[A-Z])$");
    public static Pattern materialName = Pattern.compile("^[A-Z](-[A-z]*)(-[A-z]*[0-9]{3})?$");
    public static Pattern supplierName = Pattern.compile("^[A-z0-9 /-]*$");

    public static void removeError(JFXTextField txt, JFXButton btn) {

        btn.setDisable(false);
    }

    public static void removeError(JFXPasswordField pw, JFXButton btn) {
        pw.setFocusColor(Paint.valueOf("blue"));
        btn.setDisable(false);
    }

    public static void addError(JFXTextField txt, JFXButton btn) {
        txt.setFocusColor(Paint.valueOf("red"));
        btn.setDisable(true);
    }

    public static void addError(JFXPasswordField pw, JFXButton btn) {
        pw.setFocusColor(Paint.valueOf("red"));
        btn.setDisable(true);
    }

}
