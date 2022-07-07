package util;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.TransactionTM;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class ReportUtil {

    public static void viewReport(String reportName){
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(ReportUtil.class.getResource("/view/report/"+reportName+".jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void viewReportFromTM(String reportName, TableView<TransactionTM> tbl, HashMap hashMap){

        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(ReportUtil.class.getResource("/view/report/"+reportName+".jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, hashMap, new JRBeanArrayDataSource(tbl.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

}
