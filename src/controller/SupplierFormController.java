package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Supplier;
import util.CrudUtil;
import util.RegexPatterns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SupplierFormController {
    public TableView<Supplier> tblSuppliers;
    public TableColumn colSupplierName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDesc;
    public TableColumn colDateCreated;
    
    public JFXTextArea txtDesc;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtSupplierName;
    public JFXButton btnOnAction;
    public Label lblRegex;

    public void initialize(){
        loadSupplierTable();
    }

    private void loadSupplierTable() {
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colDateCreated.setCellValueFactory(new PropertyValueFactory<>("date"));

        ObservableList<Supplier> ol = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM supplier");
            while (rs.next()){
                ol.add(new Supplier(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate()
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblSuppliers.setItems(ol);
    }

    public void btnOnAction(ActionEvent actionEvent) {
        if (!txtSupplierName.getText().equals("") && !txtAddress.getText().equals("") && !txtContact.getText().equals("")){
            if (btnOnAction.getText().equals("Add Supplier")){
                try {
                    if (CrudUtil.execute("INSERT INTO supplier(supName, address, contact, description, dateCreated) VALUES (?,?,?,?,?)",
                            txtSupplierName.getText(),
                            txtAddress.getText(),
                            txtContact.getText(),
                            txtDesc.getText(),
                            LocalDate.now()
                    )){
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
                try {
                    btnOnAction.setText("Add Supplier");
                    if (CrudUtil.execute("UPDATE supplier SET address = ?, contact = ?, description = ? WHERE supName = ?",
                            txtAddress.getText(),
                            txtContact.getText(),
                            txtDesc.getText(),
                            txtSupplierName.getText()
                    )){
                        new Alert(Alert.AlertType.INFORMATION,"Supplier Updated!").show();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            loadSupplierTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Fill all required fields!").show();
        }
    }

    public void contextBtnEditOnAction(ActionEvent actionEvent) {
        btnOnAction.setText("Update Supplier");

        Supplier sp = tblSuppliers.getSelectionModel().selectedItemProperty().get();
        // set data
        txtSupplierName.setText(sp.getSupName());
        txtAddress.setText(sp.getAddress());
        txtContact.setText(sp.getContact());
        txtDesc.setText(sp.getDesc());
    }

    public void contextBtnDeleteOnAction(ActionEvent actionEvent) {
        try {
            CrudUtil.execute("DELETE FROM supplier WHERE supName = ?",
                    tblSuppliers.getSelectionModel().selectedItemProperty().get().getSupName()
                    );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadSupplierTable();
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
        // sup name
        if (!RegexPatterns.supplierName.matcher(txtSupplierName.getText()).matches() && !txtSupplierName.getText().equals("")) {
            btnOnAction.setDisable(true);
            lblRegex.setText("Supplier Name invalid");
        }else {
            btnOnAction.setDisable(false);
            lblRegex.setText("");
            // address
            if (!RegexPatterns.address.matcher(txtAddress.getText()).matches() && !txtAddress.getText().equals("")) {
                btnOnAction.setDisable(true);
                lblRegex.setText("Supplier Address invalid (eg: Galle rd, Colombo6)");
            }else {
                btnOnAction.setDisable(false);
                lblRegex.setText("");
                // contact
                if (!RegexPatterns.contact.matcher(txtContact.getText()).matches() && !txtContact.getText().equals("")) {
                    btnOnAction.setDisable(true);
                    lblRegex.setText("Supplier Contact invalid (eg: 076XX45XX5)");
                }else {
                    btnOnAction.setDisable(false);
                    lblRegex.setText("");
                }
            }
        }
    }
}
