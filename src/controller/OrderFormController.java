package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;
import util.*;
import view.tm.CartTM;
import view.tm.ProductTM;
import view.tm.TransactionTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderFormController extends ProductFormController{
    public JFXTextField txtOrderId;
    public JFXDatePicker dateOrder;
    public JFXButton btnPlaceOrder;
    public JFXComboBox cmbPlatform;
    public JFXComboBox cmbOrderStatus;
    public JFXComboBox cmbPaymentStatus;
    public JFXComboBox cmbPaymentMethod;
    public JFXTextField txtCustomerId;
    public JFXTextField txtUserId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtQuantity;
    public JFXComboBox cmbProductId;
    public JFXTextField txtTrackingNumber;
    public JFXComboBox cmbDesignSku;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtSubTotal;
    public JFXTextField txtTransactionID;
    // table cart
    public TableView<CartTM> tblCart;
    public TableColumn colProductId;
    public TableColumn colDesignSku;
    public TableColumn colQtyOnCart;
    public TableColumn colUnitPrice;
    public TableColumn colSubTotal;

    public Label lblTotal;
    public AnchorPane apnAllOrders;
    public AnchorPane apnPlaceOrder;

    //table Orders
    public TableView<Order> tblOrders;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colTransactionId;
    public TableColumn colTrackingNo;
    public TableColumn colDate;
    public TableColumn colQtyOnOrder;
    public TableColumn colTotalPrice;
    public TableColumn colPlatform;
    public TableColumn colPaymentStatus;
    public TableColumn colDeliveryStatus;
    public TableColumn colPaymentMethod;

    public JFXTextField txtOrderIdOnAllOrder;
    public JFXTextField txtCustomerIdOnAllOrder;
    public JFXTextField txtTransactionIdOnAllOrder;
    public JFXTextField txtTrackingNoOnAllOrder;
    public JFXDatePicker dateOnAllOrder;
    public JFXTextField txtQtyOnAllOrder;
    public JFXTextField txtTotalPriceOnAllOrder;
    public JFXComboBox cmbPlatformOnAllOrder;
    public JFXComboBox cmbOrderStatusOnAllOrder;
    public JFXComboBox cmbPaymentStatusOnAllOrder;
    public JFXComboBox cmbPaymentMethodOnAllOrder;
    public Label lblAvailableQty;
    public JFXButton btnAddToCart;
    public Label lblRegex;
    public JFXButton btnUpdateOnAllOrder;
    public Label lblRegexOnAllOrder;
    public JFXComboBox cmbFilterDeliveryStatus;
    public JFXButton btnFilterResult;
    public MenuItem btnViewCartOnContext;

    ObservableList<CartTM> cartList = FXCollections.observableArrayList();
    CartTM cart;
    public static String selectedOrderId = "";

    public void initialize(){
        apnAllOrders.setTranslateX(1281);
        initializeCmb();
        cmbFilterDeliveryStatus.setValue("Delivered");
        genarateCustomerIdAndUserId();
        TransactionUtil.genarateTransactionId(txtTransactionID);
        makeTotal();
        loadOrderTable();
        genarateAvailableQty();

        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setDataToFields(newValue);
        });
        cmbFilterDeliveryStatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadOrderTable();
        });
        btnViewCartOnContext.setOnAction(event -> {
            selectedOrderId = tblOrders.getSelectionModel().getSelectedItem().getOrderId();
            try {
                Util.newUi("ViewCartForm","C A R T");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cmbPlatform.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.toString().equals("Daraz")){
                    txtTransactionID.setText("0");
                }else {
                    TransactionUtil.genarateTransactionId(txtTransactionID);
                }
            }catch (NullPointerException e){
                // null when cmb clear / refresh
            }
        });
    }

    private void genarateAvailableQty() {
        cmbProductId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ResultSet rs = CrudUtil.execute("SELECT productId,qtyOnHand FROM product");
                while (rs.next()){
                    if (rs.getString(1).equals(newValue)){
                        lblAvailableQty.setText(String.valueOf(rs.getInt(2)));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setDataToFields(Order o) {
        try {
            txtOrderIdOnAllOrder.setText(o.getOrderId());
            txtCustomerIdOnAllOrder.setText(String.valueOf(o.getCustomerId()));
            txtTransactionIdOnAllOrder.setText(String.valueOf(o.getTransactionId()));
            txtTrackingNoOnAllOrder.setText(o.getTrackingNo());
            dateOnAllOrder.setValue(o.getDate());
            txtQtyOnAllOrder.setText(String.valueOf(o.getQty()));
            txtTotalPriceOnAllOrder.setText(String.valueOf(o.getTotalPrice()));

            cmbPlatformOnAllOrder.setValue(o.getPlatform());
            cmbPaymentStatusOnAllOrder.setValue(o.getPaymentStatus());
            cmbPaymentMethodOnAllOrder.setValue(o.getPaymentMethod());
            cmbOrderStatusOnAllOrder.setValue(o.getDeliveryStatus());
        }catch (NullPointerException e){

        }
    }

    private void loadOrderTable() {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colTrackingNo.setCellValueFactory(new PropertyValueFactory<>("trackingNo"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQtyOnOrder.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colPlatform.setCellValueFactory(new PropertyValueFactory<>("platform"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));

        ObservableList<Order> ol = FXCollections.observableArrayList();
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM `order` WHERE deliveryStatus = ?", cmbFilterDeliveryStatus.getValue().toString());
            while (rs.next()){
                ol.add(new Order(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getDate(5).toLocalDate(),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                ));
            }
            tblOrders.setItems(ol);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void makeTotal() {
        txtQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                txtSubTotal.setText(String.valueOf(Double.parseDouble(txtUnitPrice.getText()) * Double.parseDouble(txtQuantity.getText())));
            }catch (NumberFormatException e){
                //cause by -- empty string
            }
        });

        txtUnitPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                txtSubTotal.setText(String.valueOf(Double.parseDouble(txtUnitPrice.getText()) * Double.parseDouble(txtQuantity.getText())));
            }catch (NumberFormatException e){
                //cause by -- empty string
            }
        });
    }

    private void genarateCustomerIdAndUserId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1");
            resultSet.next();
            txtCustomerId.setText(resultSet.getInt(1) + 1 +"");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // user ID
        try {
            ResultSet rs = CrudUtil.execute("SELECT userId,userName FROM `user`");
            while (rs.next()){
                if (rs.getString(2).equals(MainFormController.userName)){
                    txtUserId.setText(String.valueOf(rs.getInt(1)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeCmb() {
        cmbPlatform.setItems(FXCollections.observableArrayList(Platform.values()));
        cmbPlatformOnAllOrder.setItems(FXCollections.observableArrayList(Platform.values()));

        cmbOrderStatus.setItems(FXCollections.observableArrayList(OrderStatus.values()));
        cmbFilterDeliveryStatus.setItems(FXCollections.observableArrayList(OrderStatus.values()));
        cmbOrderStatusOnAllOrder.setItems(FXCollections.observableArrayList(OrderStatus.values()));

        cmbPaymentStatus.setItems(FXCollections.observableArrayList(PaymentStatus.values()));
        cmbPaymentStatusOnAllOrder.setItems(FXCollections.observableArrayList(PaymentStatus.values()));

        cmbPaymentMethod.setItems(FXCollections.observableArrayList(PaymentMethod.values()));
        cmbPaymentMethodOnAllOrder.setItems(FXCollections.observableArrayList(PaymentMethod.values()));

        InitializeCmbs.cmbProductId(cmbProductId);
        InitializeCmbs.cmbDesignSku(cmbDesignSku);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        if (!txtOrderId.getText().equals("") && dateOrder.getValue()!=null && cmbPaymentMethod.getValue()!=null && cmbPlatform.getValue()!=null
         && cmbOrderStatus.getValue()!=null && cmbPaymentStatus.getValue()!=null){

            // find if this is duplicate order id
            boolean duplicate = false;
            try {
                ResultSet rs = CrudUtil.execute("SELECT orderId FROM `order`");
                while (rs.next()){
                    if (txtOrderId.getText().equals(rs.getString(1))){
                        // mark as duplicate
                        duplicate = true;
                    }
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            // check if it is duplicate
            if (duplicate){
                new Alert(Alert.AlertType.ERROR,"Duplicate Order Id!").show();
            }else { // not a duplicate
                try {
                    // add customer
                    CrudUtil.execute("INSERT INTO customer( userId, name, address, contact) VALUES (?,?,?,?)",
                            Integer.parseInt(txtUserId.getText()),
                            txtCustomerName.getText(),
                            txtCustomerAddress.getText(),
                            txtCustomerContact.getText()
                    );
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                // find qty
                int qty = 0;
                ObservableList<CartTM> ol = tblCart.getItems();
                for (CartTM tm : ol) {
                    qty += tm.getQty();
                }

                // make order
                Order order = new Order(
                        txtOrderId.getText(),
                        Integer.parseInt(txtCustomerId.getText()),
                        Integer.parseInt(txtTransactionID.getText()),
                        txtTrackingNumber.getText(),
                        dateOrder.getValue(),
                        qty,
                        Double.parseDouble(lblTotal.getText()),
                        cmbPlatform.getValue().toString(),
                        cmbPaymentStatus.getValue().toString(),
                        cmbPaymentMethod.getValue().toString(),
                        cmbOrderStatus.getValue().toString()
                );

                // add transaction
                /**
                 * transaction part disabled for daraz because daraz orders not give credit at the time order placed
                 * transaction only enabled for fb orders
                 */
                if (cmbPlatform.getValue().equals("Facebook")){
                    TransactionUtil.addTransaction(new TransactionTM(
                            Integer.parseInt(txtTransactionID.getText()),
                            LocalDate.now(),
                            0.0,
                            Double.parseDouble(lblTotal.getText()),
                            TransactionUtil.getBalance() + Double.parseDouble(lblTotal.getText()),
                            txtCustomerName.getText() +" - "+ txtCustomerId.getText()
                    ));
                }

                // add order
                if (OrderCrudController.saveOrder(order)){
                    new Alert(Alert.AlertType.INFORMATION,"Order Placed!").show();

                    initialize();

                    // clear all
                    Util.clearCmb(cmbOrderStatus,cmbPaymentStatus,cmbPlatform,cmbPaymentMethod);
                    Util.clearFields(txtCustomerName,txtCustomerAddress,txtCustomerContact,txtOrderId,txtTrackingNumber);
                    dateOrder.setValue(null);
                    cartList.clear();

                    loadOrderTable();
                    return;
                }
                new Alert(Alert.AlertType.ERROR,"Something went Wrong!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Required fields couldn't be empty!").show();
        }


    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!txtOrderId.getText().equals("") && cmbProductId.getValue() != null && cmbDesignSku.getValue() != null && !txtQuantity.getText().equals("")
         && !txtUnitPrice.getText().equals("")){
            // update sales on Designs
            CrudUtil.execute("UPDATE design SET Sales = Sales + ? WHERE designSku = ?",
                    Integer.parseInt(txtQuantity.getText()),
                    Integer.parseInt(cmbDesignSku.getValue().toString()
                    ));

            // update stock
            CrudUtil.execute("UPDATE product SET qtyOnHand = qtyOnHand - ? WHERE productId = ?",
                    Integer.parseInt(txtQuantity.getText()),
                    cmbProductId.getValue().toString()
            );

            //make enable place order btn
            btnPlaceOrder.setDisable(false);

            colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            colDesignSku.setCellValueFactory(new PropertyValueFactory<>("designSku"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnCart.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

            cart = new CartTM(
                    txtOrderId.getText(),
                    cmbProductId.getValue().toString(),
                    cmbDesignSku.getValue().toString(),
                    Integer.parseInt(txtQuantity.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Double.parseDouble(txtSubTotal.getText())
            );
            cartList.add(cart);
            tblCart.setItems(cartList);

            //add to db
            OrderCrudController.saveCart(cart);

            Double total = 0.0;
            for (CartTM temp : cartList) {
                total += temp.getSubTotal();
            }
            lblTotal.setText(total.toString());

            // product table refresh
            //tblProduct.setItems(ProductCrudController.loadAllProduct());


            Util.clearCmb(cmbProductId,cmbDesignSku);
            Util.clearFields(txtUnitPrice,txtSubTotal,txtQuantity);
            Util.clearLabels(lblAvailableQty);
        }else {
            new Alert(Alert.AlertType.ERROR,"Required fields couldn't be empty!").show();
        }
    }

    public void viewAllOrdersOnMouseClicked(MouseEvent mouseEvent) {

        TranslateTransition slideAllOrder = new TranslateTransition();
        slideAllOrder.setDuration(Duration.seconds(0.2));
        slideAllOrder.setNode(apnAllOrders);
        slideAllOrder.setToX(0);
        slideAllOrder.play();

    }

    public void placeOrderOnMouseClicked(MouseEvent mouseEvent) {

        TranslateTransition slideAllOrder = new TranslateTransition();
        slideAllOrder.setDuration(Duration.seconds(0.2));
        slideAllOrder.setNode(apnAllOrders);
        slideAllOrder.setToX(1281);
        slideAllOrder.play();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        try {
            CrudUtil.execute("UPDATE `order` SET trackingNo = ?,date = ?,qty = ?,totalPrice = ?,platform = ?,paymentStatus = ?,paymentMethod = ?,deliveryStatus = ? WHERE orderId = ?",
                    txtTrackingNoOnAllOrder.getText(),
                    dateOnAllOrder.getValue(),
                    Integer.parseInt(txtQtyOnAllOrder.getText()),
                    Double.parseDouble(txtTotalPriceOnAllOrder.getText()),
                    cmbPlatformOnAllOrder.getValue().toString(),
                    cmbPaymentStatusOnAllOrder.getValue().toString(),
                    cmbPaymentMethodOnAllOrder.getValue().toString(),
                    cmbOrderStatusOnAllOrder.getValue().toString(),
                    txtOrderIdOnAllOrder.getText()
                    );

            loadOrderTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnCartOnAction(ActionEvent actionEvent) {
        try {
            // update sales on Designs
            CrudUtil.execute("UPDATE design SET Sales = Sales - 1 WHERE designSku = ?",
                    Integer.parseInt(cmbDesignSku.getValue().toString()
                    ));


            // update stock
            try {
                CrudUtil.execute("UPDATE product SET qtyOnHand = qtyOnHand + ? WHERE productId = ?",
                        tblCart.getSelectionModel().selectedItemProperty().getValue().getQty(),
                        tblCart.getSelectionModel().selectedItemProperty().getValue().getProductId()
                );
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // delete
            CrudUtil.execute("DELETE FROM orderDetails WHERE  = ?",
                    cmbProductId.getValue()
            );


            loadOrderTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnCartOnAction(ActionEvent actionEvent) {
        CartTM tm = tblCart.getSelectionModel().selectedItemProperty().get();

        txtQuantity.setText(String.valueOf(tm.getQty()));
        txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
        cmbProductId.setValue(tm.getProductId());
        cmbDesignSku.setValue(tm.getDesignSku());

    }

    public void btnDeleteOnAllOderOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure? This process can't undone.");

        try {
            CrudUtil.execute("DELETE FROM `order` WHERE orderId = ?",
                    txtOrderIdOnAllOrder.getText()
            );
            CrudUtil.execute("DELETE FROM orderDetails WHERE orderId = ?",
                    txtOrderIdOnAllOrder.getText()
            );

            loadOrderTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnPrintReportOnAction(ActionEvent actionEvent) {
        ReportUtil.viewReport("orders");
    }

    private void validateAllOrder() {
        // validation
        // order id
        if (!RegexPatterns.orderId.matcher(txtOrderIdOnAllOrder.getText()).matches() && !txtOrderIdOnAllOrder.getText().equals("")) {
            btnUpdateOnAllOrder.setDisable(true);
            lblRegexOnAllOrder.setText("Order ID invalid");
        }else {
            btnUpdateOnAllOrder.setDisable(false);
            lblRegexOnAllOrder.setText("");
            // cus id
            if (!RegexPatterns.customerId.matcher(txtCustomerIdOnAllOrder.getText()).matches() && !txtCustomerIdOnAllOrder.getText().equals("")) {
                btnUpdateOnAllOrder.setDisable(true);
                lblRegexOnAllOrder.setText("Customer ID invalid");
            }else {
                btnUpdateOnAllOrder.setDisable(false);
                lblRegexOnAllOrder.setText("");
                // transac id
                if (!RegexPatterns.customerId.matcher(txtTransactionIdOnAllOrder.getText()).matches() && !txtTransactionIdOnAllOrder.getText().equals("")) {
                    btnUpdateOnAllOrder.setDisable(true);
                    lblRegexOnAllOrder.setText("Transaction ID invalid");
                }else {
                    btnUpdateOnAllOrder.setDisable(false);
                    lblRegexOnAllOrder.setText("");
                    // track no
                    if (!RegexPatterns.trackingNo.matcher(txtTrackingNoOnAllOrder.getText()).matches() && !txtTrackingNoOnAllOrder.getText().equals("")) {
                        btnUpdateOnAllOrder.setDisable(true);
                        lblRegexOnAllOrder.setText("Tracking number invalid");
                    } else {
                        btnUpdateOnAllOrder.setDisable(false);
                        lblRegexOnAllOrder.setText("");
                        // qty
                        if (!RegexPatterns.customerId.matcher(txtQtyOnAllOrder.getText()).matches() && !txtQtyOnAllOrder.getText().equals("")) {
                            btnUpdateOnAllOrder.setDisable(true);
                            lblRegexOnAllOrder.setText("Quantity invalid");
                        } else {
                            btnUpdateOnAllOrder.setDisable(false);
                            lblRegexOnAllOrder.setText("");
                            // total price
                            if (!RegexPatterns.price.matcher(txtTotalPriceOnAllOrder.getText()).matches() && !txtTotalPriceOnAllOrder.getText().equals("")) {
                                btnUpdateOnAllOrder.setDisable(true);
                                lblRegexOnAllOrder.setText("Price invalid");
                            } else {
                                btnUpdateOnAllOrder.setDisable(false);
                                lblRegexOnAllOrder.setText("");
                            }
                        }
                    }
                }
            }
        }
    }

    private void validatePlaceOrder() {
        // validation
        // cus name
        if (!RegexPatterns.uName.matcher(txtCustomerName.getText()).matches() && !txtCustomerName.getText().equals("")) {
            btnPlaceOrder.setDisable(true);
            lblRegex.setText("Customer name invalid");
        }else {
            btnPlaceOrder.setDisable(false);
            lblRegex.setText("");
            //cus address
            if (!RegexPatterns.address.matcher(txtCustomerAddress.getText()).matches() && !txtCustomerAddress.getText().equals("")) {
                btnPlaceOrder.setDisable(true);
                lblRegex.setText("Customer address invalid");
            }else {
                btnPlaceOrder.setDisable(false);
                lblRegex.setText("");
                // cus contact
                if (!RegexPatterns.contact.matcher(txtCustomerContact.getText()).matches() && !txtCustomerContact.getText().equals("")) {
                    btnPlaceOrder.setDisable(true);
                    lblRegex.setText("Customer contact invalid");
                }else {
                    btnPlaceOrder.setDisable(false);
                    lblRegex.setText("");
                    // Order ID
                    if (!RegexPatterns.orderId.matcher(txtOrderId.getText()).matches() && !txtOrderId.getText().equals("")) {
                        btnPlaceOrder.setDisable(true);
                        lblRegex.setText("Order id invalid");
                    } else {
                        btnPlaceOrder.setDisable(false);
                        lblRegex.setText("");
                        // tracking no
                        if (!RegexPatterns.trackingNo.matcher(txtTrackingNumber.getText()).matches() && !txtTrackingNumber.getText().equals("")) {
                            btnPlaceOrder.setDisable(true);
                            lblRegex.setText("Tracking Number invalid");
                        } else {
                            btnPlaceOrder.setDisable(false);
                            lblRegex.setText("");
                            // qty
                            if (!txtQuantity.getText().equals("") && Integer.parseInt(txtQuantity.getText()) > Integer.parseInt(lblAvailableQty.getText())) {
                                btnAddToCart.setDisable(true);
                                lblRegex.setText("Quantity invalid");
                            } else {
                                btnAddToCart.setDisable(false);
                                lblRegex.setText("");
                                // unit price
                                if (!RegexPatterns.price.matcher(txtUnitPrice.getText()).matches() && !txtUnitPrice.getText().equals("")) {
                                    btnAddToCart.setDisable(true);
                                    lblRegex.setText("Price invalid");
                                } else {
                                    btnAddToCart.setDisable(false);
                                    lblRegex.setText("");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void txtOnKeyReleased(KeyEvent keyEvent) {
        validatePlaceOrder();
    }

    public void txtOnKeyReleased2(KeyEvent keyEvent) {
        validateAllOrder();
    }
}