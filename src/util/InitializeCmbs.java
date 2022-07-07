package util;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InitializeCmbs {

    public static void cmbMaterialId(JFXComboBox cmb){
        ObservableList<String> oblist = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute("SELECT matId FROM material");
            while (resultSet.next()){
                oblist.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmb.setItems(oblist);
    }

    public static void cmbSupplierName(JFXComboBox cmb){
        ObservableList<String> oblist2 = FXCollections.observableArrayList();
        ResultSet rs = null;
        try {
            rs = CrudUtil.execute("SELECT supName FROM supplier");
            while (rs.next()){
                oblist2.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmb.setItems(oblist2);
    }

    public static void cmbProductId(JFXComboBox cmb){
        ObservableList<String> ol = FXCollections.observableArrayList();
        ResultSet rs = null;
        try {
            rs = CrudUtil.execute("SELECT * FROM product");
            while (rs.next()){
                ol.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmb.setItems(ol);
    }

    public static void cmbDesignSku(JFXComboBox cmb){
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM design");
            ObservableList<Integer> obList = FXCollections.observableArrayList();
            while (result.next()){
                obList.add(result.getInt(1));
            }
            cmb.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void cmbColor(JFXComboBox cmb){
        cmb.setItems(FXCollections.observableArrayList(
                "White",
                "Off-White",
                "Black",
                "Gray",
                "Green",
                "Blue",
                "Red",
                "Yellow",
                "Pink"
        ));
    }

    public static void cmbSize(JFXComboBox cmb){
        cmb.setItems(FXCollections.observableArrayList(
                "ES","S","M","L","XL","XXL"
        ));

    }

    public static void cmbMonth(JFXComboBox cmb){
        cmb.setItems(FXCollections.observableArrayList(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        ));
    }

    public static void cmbYear(JFXComboBox cmb){
        cmb.setItems(FXCollections.observableArrayList(
                2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035
        ));
    }
}
