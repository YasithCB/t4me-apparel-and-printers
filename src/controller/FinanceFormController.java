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
import view.tm.TransactionTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class FinanceFormController {
    public TableView<TransactionTM> tblTransaction;
    public TableColumn colId;
    public TableColumn colDate;
    public TableColumn colCost;
    public TableColumn colIncome;
    public TableColumn colBalance;
    public TableColumn colDesc;
    public JFXTextField txtTransactionId;
    public JFXDatePicker dateTransaction;
    public JFXTextField txtIncome;
    public Label lblBalance;
    public JFXTextField txtCost;
    public JFXTextArea txtDesc;
    public JFXButton btnAction;

    public JFXComboBox<String> cmbMonth;
    public JFXComboBox<Integer> cmbYear;
    public Label lblCost;
    public Label lblIncome;
    public Label lblProfit;
    public Label lblRegex;

    TransactionTM selectedTM = null;

    public void initialize(){
        // initialize cmbs
        InitializeCmbs.cmbMonth(cmbMonth);
        InitializeCmbs.cmbYear(cmbYear);
        // select current month & year
        cmbMonth.setValue(LocalDate.now().getMonth().toString());
        cmbYear.setValue(LocalDate.now().getYear());

        loadTransactionsTable();

        // refresh
        cmbMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadTransactionsTable();
        });
        cmbYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadTransactionsTable();
        });
    }

    private void loadTransactionsTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        tblTransaction.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));

        ObservableList<TransactionTM> ol = FXCollections.observableArrayList();
        try {
            // get transaction from db to table acording to month & year
            ResultSet rs = CrudUtil.execute("SELECT * FROM finance WHERE MONTHNAME(date) = ? AND YEAR(date) = ?", cmbMonth.getValue(), cmbYear.getValue());
            while (rs.next()){
                ol.add(new TransactionTM(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getString(6)
                ));
            }
            ResultSet bal = CrudUtil.execute("SELECT balance FROM finance ORDER BY transactionId DESC LIMIT 1");
            if (bal.next()){
                lblBalance.setText(bal.getInt(1) + "");
            }else {
                lblBalance.setText("0.00");
            }

            tblTransaction.setItems(ol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TransactionUtil.genarateTransactionId(txtTransactionId);

        // genarate cost / income / profit
        double income = 0;
        double cost = 0;
        for (TransactionTM tm : tblTransaction.getItems()) {
            income += tm.getIncome();
            cost += tm.getCost();
        }
        lblIncome.setText(income+"");
        lblCost.setText(cost+"");
        lblProfit.setText(income-cost+"");
    }

    public void btnConfirmOnAction(ActionEvent actionEvent) {
        if (dateTransaction.getValue()!=null){
            if (btnAction.getText().equals("Confirm Transaction")){
                // add transaction
                TransactionUtil.addTransaction(new TransactionTM(
                        Integer.parseInt(txtTransactionId.getText()),
                        dateTransaction.getValue(),
                        Double.parseDouble(txtCost.getText()),
                        Double.parseDouble(txtIncome.getText()),
                        TransactionUtil.getBalance() + Double.parseDouble(txtIncome.getText()) - Double.parseDouble(txtCost.getText()),
                        txtDesc.getText()
                ));
            }else {
                TransactionUtil.updateTransaction(selectedTM,lblBalance,txtTransactionId,dateTransaction,txtCost,txtIncome,txtDesc);
                btnAction.setText("Confirm Transaction");
            }
            loadTransactionsTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Fill all required fields!").show();
        }
    }

    public void contextBtnEditOnAction(ActionEvent actionEvent) {
        btnAction.setText("Update Transaction");
        selectedTM = tblTransaction.getSelectionModel().selectedItemProperty().get();
        // set Data
        txtTransactionId.setText(String.valueOf(selectedTM.getTransactionId()));
        dateTransaction.setValue(selectedTM.getDate());
        txtCost.setText(String.valueOf(selectedTM.getCost()));
        txtIncome.setText(String.valueOf(selectedTM.getIncome()));
        txtDesc.setText(selectedTM.getDesc());
    }

    public void contextBtnDeleteOnAction(ActionEvent actionEvent) {
        selectedTM = tblTransaction.getSelectionModel().selectedItemProperty().get();
        // update balance
        lblBalance.setText(String.valueOf(Double.parseDouble(lblBalance.getText()) + selectedTM.getCost() - selectedTM.getIncome()));
        try {
            CrudUtil.execute("DELETE FROM finance WHERE transactionId = ?",
                    selectedTM.getTransactionId()
                    );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadTransactionsTable();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        Util.clearCmb(cmbYear,cmbMonth);
    }

    public void btnGetReportOnAction(ActionEvent actionEvent) {

        HashMap paraMap = new HashMap();
        paraMap.put("totalCost",lblCost.getText());
        paraMap.put("totalIncome",lblIncome.getText());
        paraMap.put("profit",lblProfit.getText());
        paraMap.put("bal",lblBalance.getText());
        paraMap.put("monthYear",cmbMonth.getValue() +" "+ cmbYear.getValue());

        ReportUtil.viewReportFromTM("finance",tblTransaction,paraMap);
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
            // cost
            if (!RegexPatterns.price.matcher(txtCost.getText()).matches() && !txtCost.getText().equals("")) {
                btnAction.setDisable(true);
                lblRegex.setText("Price invalid (eg: 2000 Or 2000.00)");
            }else {
                btnAction.setDisable(false);
                lblRegex.setText("");
                // income
                if (!RegexPatterns.price.matcher(txtIncome.getText()).matches() && !txtIncome.getText().equals("")) {
                    btnAction.setDisable(true);
                    lblRegex.setText("Price invalid (eg: 2000 Or 2000.00)");
                }else {
                    btnAction.setDisable(false);
                    lblRegex.setText("");
                }
            }
    }
}
