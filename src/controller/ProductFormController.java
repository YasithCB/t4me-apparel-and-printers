package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import util.CrudUtil;
import util.InitializeCmbs;
import util.RegexPatterns;
import util.Util;
import view.tm.ProductTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProductFormController {
    public TableView<ProductTM> tblProduct;
    public TableColumn colProductId;
    public TableColumn colSize;
    public TableColumn colColor;
    public TableColumn colQtyOnHand;
    public TableColumn colDateCreated;
    public JFXTextField txtProductId;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnAction;
    public Label lblRegex;
    public ImageView imgRefresh;

    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        loadProductTable();

        imgRefresh.setOnMouseClicked(event -> {
            loadProductTable();
        });
    }

    public void loadProductTable() {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));

        try {
            tblProduct.setItems(ProductCrudController.loadAllProduct());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            CrudUtil.execute("DELETE FROM product WHERE productId = ?",
                    tblProduct.getSelectionModel().selectedItemProperty().getValue().getProductId()
                    );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadProductTable();
    }

    private void setDataToFields() {
        btnAction.setText("Update Product");

        ProductTM tm = tblProduct.getSelectionModel().selectedItemProperty().get();
        txtProductId.setText(tm.getProductId());
        txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
    }

    public void btnOnAction(ActionEvent actionEvent) {

        if (!txtProductId.getText().equals("") && !txtQtyOnHand.getText().equals("")){
            // substring the color ans size from product ID
            String s = txtProductId.getText();
            String color = s.substring(0,s.lastIndexOf("-"));
            String size = s.substring(s.length() - 1);

            if (btnAction.getText().equals("Add Product")){
                try {
                    if (CrudUtil.execute("INSERT INTO product(productId, color, size, qtyOnHand, dateCreated) VALUES (?,?,?,?,?)",
                            txtProductId.getText(),
                            color,
                            size,
                            Integer.parseInt(txtQtyOnHand.getText()),
                            LocalDate.now()
                    )){
                        new Alert(Alert.AlertType.INFORMATION,"Product Added!").show();
                        Util.clearFields(txtProductId,txtQtyOnHand);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                btnAction.setText("Add Product");
                try {
                    if (CrudUtil.execute("UPDATE product SET productId = ?, color = ?, size = ?, qtyOnHand = ?, dateCreated = NOW() WHERE productId = ? ",
                            txtProductId.getText(),
                            color,
                            size,
                            Integer.parseInt(txtQtyOnHand.getText()),
                            txtProductId.getText()
                    )){
                        new Alert(Alert.AlertType.INFORMATION,"Product Updated!").show();
                        Util.clearFields(txtProductId,txtQtyOnHand);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            loadProductTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Fill all required fields!").show();
        }

    }

    public void btnEditOnAction(ActionEvent actionEvent) {
        setDataToFields();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        Util.clearFields(txtProductId,txtQtyOnHand);
        btnAction.setText("Add Product");
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
        // product id
        if (!RegexPatterns.productId.matcher(txtProductId.getText()).matches() && !txtProductId.getText().equals("")) {
            btnAction.setDisable(true);
            lblRegex.setText("Product ID invalid");
        }else {
            btnAction.setDisable(false);
            lblRegex.setText("");
            //  qty
            if (!RegexPatterns.customerId.matcher(txtQtyOnHand.getText()).matches() && !txtQtyOnHand.getText().equals("")) {
                btnAction.setDisable(true);
                lblRegex.setText("Quantity invalid");
            }else {
                btnAction.setDisable(false);
                lblRegex.setText("");
            }
        }
    }
}
