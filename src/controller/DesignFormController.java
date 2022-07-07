package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Design;
import util.CrudUtil;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DesignFormController {
    public TableView<Design> tblDesign;
    public TableColumn colSku;
    public TableColumn colSales;
    public TableColumn colDateCreated;

    public JFXTextField txtDesignSku;
    public ImageView imgRefresh;

    public void initialize(){
        try {
            loadTable();

            imgRefresh.setOnMouseClicked(event -> {
                loadTable();
            });
        }catch (Exception e){}


    }

    private void loadTable() {
        colSku.setCellValueFactory(new PropertyValueFactory<>("designSku"));
        colSales.setCellValueFactory(new PropertyValueFactory<>("sales"));
        colDateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));

        ObservableList<Design> ol = FXCollections.observableArrayList();
        try {
            // find next ID
            ResultSet nextId = CrudUtil.execute("SELECT designSku FROM design ORDER BY designSku DESC LIMIT 1");
            nextId.next();
            txtDesignSku.setText(nextId.getInt(1)+1 + "");
            // load table
            ResultSet rs = CrudUtil.execute("SELECT * FROM design");
            while (rs.next()){
                ol.add(new Design(
                        rs.getInt(1),
                        rs.getInt(3),
                        rs.getString(2)
                ));
            }
            tblDesign.setItems(ol);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            CrudUtil.execute("INSERT INTO design(designSku, dateCreated, Sales) VALUES (?,?,?)",
                    Integer.parseInt(txtDesignSku.getText()),
                    LocalDate.now(),
                    0
                    );
            // give alert
            new Alert(Alert.AlertType.INFORMATION,"Design Added!").show();

            loadTable();
            Util.clearFields(txtDesignSku);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
