package controller;

import com.jfoenix.controls.*;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Material;
import util.*;
import view.tm.MaterialBuyingTM;
import view.tm.TransactionTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MaterialFormController {
    public TableView tblMaterialBuying;
    public TableColumn colMaterialId;
    public TableColumn colMaterialName;
    public TableColumn colSupplierName;
    public TableColumn colTransactionId;
    public TableColumn colDate;
    public TableColumn colTransportCost;
    public TableColumn colMaterialCost;
    public TableColumn colTotalCost;

    public JFXTextField txtTransactionId;
    public JFXTextField txtMaterialCost;
    public JFXComboBox<String> cmbSupplierName;
    public JFXDatePicker dateMaterialBuying;
    public JFXTextArea txtDescription;
    public JFXTextField txtTransportCost;
    public JFXTextField txtTotalCost;
    public JFXTextField txtMaterialName;
    public JFXComboBox<String> cmbMaterialId;


    public TableView tblMaterialOnStock;
    public TableColumn colMaterialIdOnStock;
    public TableColumn colMaterialNameOnStock;
    public TableColumn colColorOnStock;
    public TableColumn colGsmOnStock;
    public TableColumn colQtyOnStock;
    public TableColumn colDateCreatedOnStock;

    public AnchorPane apnMaterialOnStock;
    public Label lblRegexOnMaterialBuying;
    public JFXButton btnConfirmBuying;

    public void initialize() throws SQLException, ClassNotFoundException {
        initializeCmb();
        TransactionUtil.genarateTransactionId(txtTransactionId);
        loadTable();
        loadMaterialOnStockTable();
        calcTotal();
        autoSetMaterialName();
    }

    private void loadMaterialOnStockTable() {

        colMaterialIdOnStock.setCellValueFactory(new PropertyValueFactory<>("matId"));
        colMaterialNameOnStock.setCellValueFactory(new PropertyValueFactory<>("matName"));
        colColorOnStock.setCellValueFactory(new PropertyValueFactory<>("color"));
        colGsmOnStock.setCellValueFactory(new PropertyValueFactory<>("gsm"));
        colQtyOnStock.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        ObservableList<Material> ol = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM material");
            while (rs.next()){
                ol.add(new Material(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate()
                ));
            }
            tblMaterialOnStock.setItems(ol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void autoSetMaterialName() {
        // auto set matName
        cmbMaterialId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ResultSet rs = CrudUtil.execute("SELECT matName FROM material WHERE matId = ?", newValue);
                rs.next();
                if (cmbMaterialId.getValue() != null){
                    txtMaterialName.setText(rs.getString(1));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void calcTotal() {
        txtMaterialCost.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                txtTotalCost.setText(Double.parseDouble(txtMaterialCost.getText()) + Double.parseDouble(txtTransportCost.getText()) + "");
            }catch (Exception e){
                // empty string
            }
        });
        txtTransportCost.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                txtTotalCost.setText(Double.parseDouble(txtMaterialCost.getText()) + Double.parseDouble(txtTransportCost.getText()) + "");
            }catch (Exception e){
                // empty string
            }
        });
    }

    private void loadTable() {

        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTransportCost.setCellValueFactory(new PropertyValueFactory<>("transportCost"));
        colMaterialCost.setCellValueFactory(new PropertyValueFactory<>("materialCost"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        ObservableList<MaterialBuyingTM> ol = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM materialBuying");


            while (rs.next()){

                ResultSet rs2 = CrudUtil.execute("SELECT matName FROM material WHERE matId = ?", rs.getString(1));
                rs2.next();
                ol.add(new MaterialBuyingTM(
                        rs.getString(1),
                        rs2.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toString(),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7)
                ));
            }

            tblMaterialBuying.setItems(ol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeCmb() {
        InitializeCmbs.cmbMaterialId(cmbMaterialId);
        InitializeCmbs.cmbSupplierName(cmbSupplierName);
    }

    public void btnConfirmBuyingOnAction(ActionEvent actionEvent) {
        if (cmbMaterialId.getValue() != null && dateMaterialBuying.getValue() != null && cmbSupplierName.getValue() != null
         && !txtTransportCost.getText().equals("") && !txtMaterialCost.getText().equals("")){
            try {
                // add material buy
                CrudUtil.execute("INSERT INTO materialBuying(matId, supName, transactionId, date, transportCost, matCost, totalCost, description) VALUES (?,?,?,?,?,?,?,?)",
                        cmbMaterialId.getValue(),
                        cmbSupplierName.getValue(),
                        txtTransactionId.getText(),
                        dateMaterialBuying.getValue(),
                        Double.parseDouble(txtTransportCost.getText()),
                        Double.parseDouble(txtMaterialCost.getText()),
                        Double.parseDouble(txtTotalCost.getText()),
                        txtDescription.getText()
                );
                // add transaction
                TransactionUtil.addTransaction(new TransactionTM(
                        Integer.parseInt(txtTransactionId.getText()),
                        dateMaterialBuying.getValue(),
                        Double.parseDouble(txtTotalCost.getText()),
                        0.0,
                        TransactionUtil.getBalance() - Double.parseDouble(txtTotalCost.getText()),
                        txtDescription.getText()
                ));


                loadTable();
                Util.clearFields(txtMaterialCost,txtMaterialName,txtTotalCost,txtTransportCost,txtTransactionId);
                txtDescription.setText("");
                dateMaterialBuying.setValue(null);
                Util.clearCmb(cmbMaterialId,cmbSupplierName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Fill all required fields!").show();
        }
    }

    public void viewAllMaterialsOnMouseClicked(MouseEvent mouseEvent) {
        TranslateTransition slideAllOrder = new TranslateTransition();
        slideAllOrder.setDuration(Duration.seconds(0.2));
        slideAllOrder.setNode(apnMaterialOnStock);
        slideAllOrder.setToX(0);
        slideAllOrder.play();
    }

    public void buyMaterialOnMouseClicked(MouseEvent mouseEvent) {
        TranslateTransition slideAllOrder = new TranslateTransition();
        slideAllOrder.setDuration(Duration.seconds(0.2));
        slideAllOrder.setNode(apnMaterialOnStock);
        slideAllOrder.setToX(1281);
        slideAllOrder.play();
    }

    public void btnPrintReportOnAction(ActionEvent actionEvent) {
        ReportUtil.viewReport("materialBuying");
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
            //  transport cost
            if (!RegexPatterns.price.matcher(txtTransportCost.getText()).matches() && !txtTransportCost.getText().equals("")) {
                btnConfirmBuying.setDisable(true);
                lblRegexOnMaterialBuying.setText("Price invalid");
            }else {
                btnConfirmBuying.setDisable(false);
                lblRegexOnMaterialBuying.setText("");
                // material cost
                if (!RegexPatterns.price.matcher(txtMaterialCost.getText()).matches() && !txtMaterialCost.getText().equals("")) {
                    btnConfirmBuying.setDisable(true);
                    lblRegexOnMaterialBuying.setText("Price invalid");
                }else {
                    btnConfirmBuying.setDisable(false);
                    lblRegexOnMaterialBuying.setText("");
                }
            }
    }
}
