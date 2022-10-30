package lk.ijse.healthcare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {
    public AnchorPane dashboardContext;


    public void openPaymentFormOnAction(ActionEvent actionEvent) {
    }
    public void openPatientFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PatientForm");
    }
    public void openDocumentFormOnAction(ActionEvent actionEvent) {
    }
    public void openDoctorFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DoctorForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage)dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource("../view/"+location+".fxml")))
        );
    }
}
