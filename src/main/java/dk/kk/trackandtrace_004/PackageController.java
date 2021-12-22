package dk.kk.trackandtrace_004;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import java.net.URL;
import java.util.ResourceBundle;

public class PackageController implements Initializable {

    @FXML
    private ChoiceBox<String> deliverychoice;

    @FXML
    private Button opret;

    @FXML
    private ChoiceBox<String> recipient;

    @FXML
    private ChoiceBox<String> senderchoice;

    DBsql d = new DBsql(); //initiate database connection

    private String[] deliverychoises = {"GLS","PostNord","Afhent på lager"};
    private String[] senderchoises = d.costumerList();
    private String[] recipientchoises = d.costumerList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        deliverychoice.getItems().addAll(deliverychoises);
        senderchoice.getItems().addAll(senderchoises);
        recipient.getItems().addAll(recipientchoises);
    }


    @FXML
    void createPackage(ActionEvent event) {
        DBsql d = new DBsql();
        d.createPackage(senderchoice.getValue(), recipient.getValue(),deliverychoice.getValue());
        System.out.println(
                senderchoice.getValue()+
                " sender til "+
                recipient.getValue()+
                "\ndu har valgt "+deliverychoice.getValue()+
                "pakke ID "+d.lastPackageID());
    }


    }
