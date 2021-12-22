package dk.kk.trackandtrace_004;

import javafx.application.Application;

public class Main {

    public static void main(String[] args) {

        DBsql d = new DBsql();
        d.InstallDatabase(); // installing datebase if it does not exist
        d.CreateTebles();
        Application.launch(MainMenuApplication.class, args);

        //todo hovedmenu lav overgang fra opret costumer til opret package og tilbage igen
        //todo vindue med pakke søgning med mulighed for at opdatere pakke status og afslutte levering
        //todo visible confirm at costumer/package er gemt

    }
}
