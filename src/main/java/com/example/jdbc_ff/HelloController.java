package com.example.jdbc_ff;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController {
    @FXML
    private Label welcomeText;
    private TextField tfUsername;
    private TextField tfpassword;

    @FXML
    private Text actionTarget;

    @FXML
    protected void SignIn(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfpassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            actionTarget.setText("Username or password cannot be empty");
            actionTarget.setOpacity(1);
            return;
        }
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {
            String selectQuery = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(selectQuery);
            boolean found = false;
            while (result.next()) {
                String dbUsername = result.getString("username");
                String dbPassword = result.getString("password");
                if (username.equals(dbUsername) && password.equals(dbPassword)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                    try {
                        Scene scene = new Scene(loader.load());
                        Stage stage = (Stage) tfUsername.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            actionTarget.setText("Invalid username/password");
            actionTarget.setOpacity(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleRegister(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}