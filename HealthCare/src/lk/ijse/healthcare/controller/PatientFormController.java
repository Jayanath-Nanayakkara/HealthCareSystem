package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.healthcare.dao.DataAccessCode;
import lk.ijse.healthcare.dto.PatientDto;
import lk.ijse.healthcare.view.tm.DoctorTm;
import lk.ijse.healthcare.view.tm.PatientTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class PatientFormController {
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colOption;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public AnchorPane patinetAncherPalne;
    public TableView tblPatient;
    public TextField txtsearch;
    public JFXButton btnSave;
    private String sText="";

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        searchPatient("");
        tblPatient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData((PatientTm) newValue);
            };
        });
        txtsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                sText=newValue;
                searchPatient(sText);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    private void setData(PatientTm tm) {
        btnSave.setText("Update Patient");
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContact.setText(tm.getContact());


    }
    public void newPatientOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save Patient");
        Clear();
    }
    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) patinetAncherPalne.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));

    }
    public void savePatinetOnAction(ActionEvent actionEvent) {
        PatientDto dto = new PatientDto(txtId.getText(),txtName.getText(),txtAddress.getText(),txtContact.getText());
        if (btnSave.getText().equals("Save Patient")){
            try{
                boolean isSaved = new DataAccessCode().savePatient(dto);
                if (isSaved){
                    searchPatient(sText);
                    Clear();
                    new Alert(Alert.AlertType.CONFIRMATION,"Patient Saved").show();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try Again").show();
                }

            }catch(ClassNotFoundException e){
                new Alert(Alert.AlertType.WARNING,"Error").show();
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }else {
            try {
                boolean isUpdate = new DataAccessCode().updatePatient(dto);
                if (isUpdate) {
                    searchPatient(sText);
                    Clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient Updated").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "Error").show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    private void searchPatient(String text) throws SQLException, ClassNotFoundException {
        ObservableList<PatientTm> tmList= FXCollections.observableArrayList();
        for (PatientDto dto:new DataAccessCode().searchPatient(sText)
             ) {
            Button btn = new Button("Delete");
            tmList.add(new PatientTm(dto.getId(),dto.getName(),dto.getAddress(),dto.getContact(),btn));
            btn.setOnAction(event -> {
                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Are you sure",
                        ButtonType.YES,
                        ButtonType.NO
                );
                Optional<ButtonType> buttonType=alert.showAndWait();
                if (buttonType.get()==ButtonType.YES){
                    try{
                        if(new DataAccessCode().deletPatient(dto.getId())){
                            searchPatient("");
                            new Alert(Alert.AlertType.CONFIRMATION,"patient Deleted").show();
                        }
                    }catch (SQLException e){
                        new Alert(Alert.AlertType.WARNING,"Error").show();
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }else{
                    new Alert(Alert.AlertType.WARNING,"Ty Again").show();
                }
            });
        }
        tblPatient.setItems(tmList);
    }
        private void Clear() {
            txtId.clear();
            txtName.clear();
            txtContact.clear();
            txtAddress.clear();
        }
}
