package controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashBoardFormController {
    public LineChart chartSales;
    public PieChart pieStock;
    public PieChart pieMaterial;
    public Label lblNotify;

    public void initialize(){
        lineChart();
        pieCharts();
        setNotifications();
    }

    private void setNotifications() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT productId,qtyOnHand FROM product");
            while (rs.next()){
                if (rs.getInt(2) < 5){
                    lblNotify.setText(lblNotify.getText() + "Please note "+rs.getString(1)+" product stock is lower than 5. Ready to update stock.\n");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void pieCharts() {
        // pie chart data create
        ObservableList<PieChart.Data> productDate = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT productId,qtyOnHand FROM product");
            while (rs.next()){
                productDate.add(new PieChart.Data(rs.getString(1),rs.getInt(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        productDate.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName()," ",data.pieValueProperty())
                )
        );
        // add data to the chart
        pieStock.setData(productDate);
        pieStock.setTitle("TShirts Stock");



        // material pie
        ObservableList<PieChart.Data> materialData = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT matName,qtyOnHand FROM material");
            while (rs.next()){
                if (rs.getString(1).equals("Light") || rs.getString(1).equals("Dark")){
                    materialData.add(new PieChart.Data(rs.getString(1),rs.getInt(2)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        materialData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName()," ",data.pieValueProperty())
                )
        );
        //add data to material pie
        pieMaterial.setData(materialData);
        pieMaterial.setTitle("Transfer Papers Stock");
    }

    private void lineChart() {
        XYChart.Series series = new XYChart.Series();
        series.setName("Design SKU");

        ResultSet rs = null;
        try {
            rs = CrudUtil.execute("SELECT designSku,Sales FROM design");
            while (rs.next()){
                series.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*series.getData().add(new XYChart.Data("January", 1));
        series.getData().add(new XYChart.Data("February", 4));
        series.getData().add(new XYChart.Data("March", 3));
        series.getData().add(new XYChart.Data("April", 5));
        series.getData().add(new XYChart.Data("May", 4));
        series.getData().add(new XYChart.Data("June", 10));
        series.getData().add(new XYChart.Data("July", 12));
        series.getData().add(new XYChart.Data("August", 3));
        series.getData().add(new XYChart.Data("September", 4));
        series.getData().add(new XYChart.Data("October", 3));
        series.getData().add(new XYChart.Data("November", 5));
        series.getData().add(new XYChart.Data("December", 4));*/

        chartSales.getData().add(series);
        chartSales.setTitle("Sales of Designs");
    }

}
