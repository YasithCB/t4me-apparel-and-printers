package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;
import util.Util;
import view.tm.CartTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewCartFormController {
    public TableView<CartTM> tblCart;
    public JFXButton btnClose;
    public AnchorPane apnCart;

    public void initialize(){
        loadCartTable();

        btnClose.setOnMouseClicked(event -> {
            Util.closeApn(apnCart);
        });
    }

    private void loadCartTable() {
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("designSku"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        try {
            ObservableList<CartTM> ol = FXCollections.observableArrayList();
            ResultSet rs = CrudUtil.execute("SELECT * FROM orderDetails WHERE orderId = ?", OrderFormController.selectedOrderId);
            while (rs.next()){
                ol.add(new CartTM(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6)
                ));
            }
            tblCart.setItems(ol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
