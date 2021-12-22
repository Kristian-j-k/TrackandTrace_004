package dk.kk.trackandtrace_004;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

     //   DBsql d=new DBsql(); //initiate database connection


//adress
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
        private Button opretAdresse;

        @FXML
        void tryk(ActionEvent event) {
            DBsql d=new DBsql(); //initiate database connection
            d.updateCostumer(name.getText(),adress.getText(),postnr.getText(),city.getText(),mobile.getText(),mail.getText()); //
            System.out.println(name.getText()+" er gemt");

            name.clear();
            adress.clear();
            postnr.clear();
            city.clear();
            mobile.clear();
            mail.clear();

            updatePackageView();

        }


    @FXML
    private Tab faneOpretPakke;

    @FXML
    private ChoiceBox<String> deliverychoice;

    @FXML
    private Button opretPakke;

    @FXML
    private ChoiceBox<String> recipient;

    @FXML
    private ChoiceBox<String> senderchoice;


    private String[] deliverychoises = {"GLS","PostNord","Afhent på lager"};
    private String[] senderchoises; // = d.costumerList();
    private String[] recipientchoises; // = d.costumerList();


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        DBsql d=new DBsql(); //initiate database connection
        senderchoises =d.costumerList();
        recipientchoises = d.costumerList();

        deliverychoice.getItems().addAll(deliverychoises);
        senderchoice.getItems().addAll(senderchoises);
        recipient.getItems().addAll(recipientchoises);
    }

    @FXML
    public void updatePackageView(){
        DBsql d=new DBsql(); //initiate database connection
        senderchoises =d.costumerList();
        recipientchoises = d.costumerList();
        senderchoice.getItems().removeAll(senderchoises);
        recipient.getItems().removeAll(recipientchoises);
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

    //trackAndTrace

    @FXML
    private TextField recipiantsSearchText;

    @FXML
    private Button RecipientSearchButton;

    @FXML
    private ChoiceBox<String> RecipiantsPackages;

    private String[] packageOnCostumer;

    @FXML
    void costumerSearchPackages(ActionEvent event){
        DBsql d =new DBsql();
        packageOnCostumer = d.costumerSearchPackages(recipiantsSearchText.getText());
        RecipiantsPackages.getItems().removeAll(packageOnCostumer);
        RecipiantsPackages.getItems().addAll(packageOnCostumer);
    }

    @FXML
    private TextArea TrackAndTrace;

    @FXML
    void packageSearch(ActionEvent event){
        DBsql d = new DBsql();
        String s="";
        String[] s2 = d.trackAndTraceSearch(RecipiantsPackages.getValue());

        for (int i = 0; i < s2.length; i++) {
            s =s+"\n"+ s2[i];
        }
        TrackAndTrace.clear();
        TrackAndTrace.appendText(s);
    }

    @FXML
    private TextField addTextField;

    @FXML
    void addTrackAndTrace(ActionEvent event){
        DBsql d = new DBsql();
        d.trackAndTraceUpdate(Integer.valueOf(RecipiantsPackages.getValue()),addTextField.getText());

    }

    @FXML
    private Button deliveretToRecipientButton;

    @FXML
    void delivered (ActionEvent event){
        DBsql d=new DBsql();
        d.delivered(Integer.valueOf(RecipiantsPackages.getValue()),"Pakken er leveret");
    }

    @FXML
    private ListView<String> PackageView;

    @FXML
    void packageListView(){
        DBsql d = new DBsql();
        PackageView.getItems().clear();
        PackageView.getItems().addAll(d.packages());

    }


    }
