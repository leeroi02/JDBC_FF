module com.example.jdbc_ff {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.jdbc_ff to javafx.fxml;
    exports com.example.jdbc_ff;
}