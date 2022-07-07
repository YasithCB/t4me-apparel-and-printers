package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.CrudUtil;
import view.tm.ProductTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCrudController {
    public static ObservableList<ProductTM> loadAllProduct() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM product");

        ObservableList<ProductTM> ObList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            ObList.add(new ProductTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDate(5).toLocalDate()
            ));
        }
        return ObList;
    }
}
