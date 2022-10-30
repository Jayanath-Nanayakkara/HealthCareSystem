package lk.ijse.healthcare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginFormContext;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashBoardForm");
    }
    public void createNewAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SignUpForm");
    }
    private void setUI(String location) throws IOException {
        Stage stage = (Stage)loginFormContext.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource("../view/"+location+".fxml")))
        );
    }
}
