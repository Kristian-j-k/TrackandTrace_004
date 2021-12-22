package dk.kk.trackandtrace_004;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CostumerController {
    DBsql d=new DBsql();

    @FXML
    private TextField name;
    @FXML
    private TextField adress;
    @FXML
    private TextField postnr;
    @FXML
    private TextField city;
    @FXML
    private TextField mobile;
    @FXML
    private TextField mail;

    @FXML
    private Button opret;

    @FXML
    void tryk(ActionEvent event) {
        d.updateCostumer(name.getText(),adress.getText(),postnr.getText(),city.getText(),mobile.getText(),mail.getText()); //
        System.out.println(name.getText()+" er gemt");

    }

}