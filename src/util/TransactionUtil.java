package util;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.MaterialFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import view.tm.TransactionTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionUtil {

    public static void genarateTransactionId(JFXTextField txtTransactionId) {
        try {
            ResultSet rs = CrudUtil.execute("SELECT MAX(transactionId) FROM finance");
            if (rs.next()){
                txtTransactionId.setText(rs.getInt(1) + 1 + "");
                return;
            }
            txtTransactionId.setText("1");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadTable(Label lblBalance, TableView tblTransaction){

        ObservableList<TransactionTM> ol = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM finance");
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
    }

    public static void updateTransaction(TransactionTM tm,Label lblBalance, JFXTextField txtTransactionId,JFXDatePicker dateTransaction, JFXTextField txtCost, JFXTextField txtIncome, JFXTextArea txtDesc){
        // subtract previous values and add new values to balance
        lblBalance.setText(String.valueOf(Double.parseDouble(lblBalance.getText()) - tm.getIncome() + tm.getCost() - Double.parseDouble(txtCost.getText()) + Double.parseDouble(txtIncome.getText())));
        // update db
        try {
            if (CrudUtil.execute("UPDATE finance SET date = ?, cost = ?, income = ?, description = ? WHERE transactionId = ?",
                    dateTransaction.getValue(),
                    Double.parseDouble(txtCost.getText()),
                    Double.parseDouble(txtIncome.getText()),
                    txtDesc.getText(),
                    txtTransactionId.getText()
            )){
                new Alert(Alert.AlertType.INFORMATION,"Transaction Updated!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //clear fields
        Util.clearFields(txtCost,txtIncome);
        dateTransaction.setValue(null);
        txtDesc.setText("");
    }

    public static void addTransaction(TransactionTM tm) {
        try {
            CrudUtil.execute("INSERT INTO finance(transactionId, date, cost, income, balance, description) VALUES (?,?,?,?,?,?)",
                    tm.getTransactionId(),
                    tm.getDate(),
                    tm.getCost(),
                    tm.getIncome(),
                    tm.getBalance(),
                    tm.getDesc()
                    );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static double getBalance(){
        ResultSet rs = null;
        try {
            rs = CrudUtil.execute("SELECT balance FROM finance ORDER BY transactionId LIMIT 1");
            rs.next();
            return rs.getDouble(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
