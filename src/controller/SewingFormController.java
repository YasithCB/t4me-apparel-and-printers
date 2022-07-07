package controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import util.*;
import view.tm.SewingTM;
import view.tm.TransactionTM;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SewingFormController {
    public TableView tblSewingDetails;
    public TableColumn colMaterialId;
    public TableColumn colQty;
    public TableColumn colProductId;
    public TableColumn colTransactionId;
    public TableColumn colDate;
    public TableColumn colNoOfTshirts;
    public TableColumn colCostPerTshirt;
    public TableColumn colCost;
    public TableColumn colDesc;
    public JFXTextField txtNoOfTshirts;
    public JFXComboBox cmbMaterialId;
    public JFXComboBox cmbProductId;
    public JFXTextField txtTransactionId;
    public JFXTextField txtQty;
    public JFXDatePicker dateSewing;
    public JFXTextField txtCostPerTshirt;
    public JFXTextField txtSewingCost;
    public JFXTextArea txtDescription;
    public JFXButton btnConfirmSewing;
    public Label lblRegex;

    public void initialize(){
        loadSewingTable();
        initializeCmb();
        TransactionUtil.genarateTransactionId(txtTransactionId);
    }

    private void initializeCmb() {
        InitializeCmbs.cmbMaterialId(cmbMaterialId);
        InitializeCmbs.cmbProductId(cmbProductId);
    }

    private void loadSewingTable() {

        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("matId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("matQty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNoOfTshirts.setCellValueFactory(new PropertyValueFactory<>("noOfTshirts"));
        colCostPerTshirt.setCellValueFactory(new PropertyValueFactory<>("costPerTshirt"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("sewCost"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));

        ObservableList<SewingTM> ol = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM sewingDetails");
            while (rs.next()){
                ol.add(new SewingTM(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDate(5).toLocalDate(),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getString(9)
                ));
            }
            tblSewingDetails.setItems(ol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnConfirmSewing(ActionEvent actionEvent) {
        if (cmbMaterialId.getValue()!=null && cmbProductId.getValue()!=null && !txtQty.getText().equals("") && dateSewing.getValue()!=null && !txtNoOfTshirts.getText().equals("")
         && !txtCostPerTshirt.getText().equals("") && !txtSewingCost.getText().equals("")){
            try {
                // add transaction
                TransactionUtil.addTransaction(new TransactionTM(
                        Integer.parseInt(txtTransactionId.getText()),
                        dateSewing.getValue(),
                        Double.parseDouble(txtSewingCost.getText()),
                        0.0,
                        TransactionUtil.getBalance() - Double.parseDouble(txtSewingCost.getText()),
                        txtDescription.getText()
                ));

                // update product stock
                CrudUtil.execute("UPDATE product SET qtyOnHand = qtyOnHand + ? WHERE productId = ?",
                        Integer.parseInt(txtNoOfTshirts.getText()),
                        Integer.parseInt(cmbProductId.getValue().toString())
                );

                // add sewing details
                if (CrudUtil.execute("INSERT INTO sewingDetails(matId, productId, transactionId, matQty, date, noOfTshirts, costPerTshirt, sewCost, description) VALUES (?,?,?,?,?,?,?,?,?)",
                        cmbMaterialId.getValue(),
                        cmbProductId.getValue(),
                        Integer.parseInt(txtTransactionId.getText()),
                        Integer.parseInt(txtQty.getText()),
                        dateSewing.getValue(),
                        Integer.parseInt(txtNoOfTshirts.getText()),
                        Double.parseDouble(txtCostPerTshirt.getText()),
                        Double.parseDouble(txtSewingCost.getText()),
                        txtDescription.getText()
                )){
                    loadSewingTable();
                    new Alert(Alert.AlertType.INFORMATION,"Data Added!").show();
                    return;
                }
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Fill all required fields!").show();
        }
    }

    public void btnPrintReportOnAction(ActionEvent actionEvent) {
        ReportUtil.viewReport("sewingDetails");
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
        // transac id
        if (!RegexPatterns.customerId.matcher(txtTransactionId.getText()).matches() && !txtTransactionId.getText().equals("")) {
            btnConfirmSewing.setDisable(true);
            lblRegex.setText("Transaction ID invalid");
        }else {
            btnConfirmSewing.setDisable(false);
            lblRegex.setText("");
            // mat qty
            if (!RegexPatterns.customerId.matcher(txtQty.getText()).matches() && !txtQty.getText().equals("")) {
                btnConfirmSewing.setDisable(true);
                lblRegex.setText("Quantity invalid");
            }else {
                btnConfirmSewing.setDisable(false);
                lblRegex.setText("");
                // no of t
                if (!RegexPatterns.customerId.matcher(txtNoOfTshirts.getText()).matches() && !txtNoOfTshirts.getText().equals("")) {
                    btnConfirmSewing.setDisable(true);
                    lblRegex.setText("T shirt number invalid");
                }else {
                    btnConfirmSewing.setDisable(false);
                    lblRegex.setText("");
                    // cost per t
                    if (!RegexPatterns.price.matcher(txtCostPerTshirt.getText()).matches() && !txtCostPerTshirt.getText().equals("")) {
                        btnConfirmSewing.setDisable(true);
                        lblRegex.setText("Price invalid");
                    }else {
                        btnConfirmSewing.setDisable(false);
                        lblRegex.setText("");
                        // sew cost
                        if (!RegexPatterns.price.matcher(txtSewingCost.getText()).matches() && !txtSewingCost.getText().equals("")) {
                            btnConfirmSewing.setDisable(true);
                            lblRegex.setText("Price invalid");
                        }else {
                            btnConfirmSewing.setDisable(false);
                            lblRegex.setText("");
                        }
                    }
                }
            }
        }
    }
}
