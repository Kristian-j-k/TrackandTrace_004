module dk.kk.trackandtrace_004 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens dk.kk.trackandtrace_004 to javafx.fxml;
    exports dk.kk.trackandtrace_004;
}