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
import lk.ijse.healthcare.dto.DoctorDto;
import lk.ijse.healthcare.view.tm.DoctorTm;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Optional;

public class DoctorFormController {
    public AnchorPane doctorFormContext;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public TextField txtSearch;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colOption;
    public TableView tblDoctors;
    public TableColumn colId;
    public JFXButton btnSave;
    private String sText ="";

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        searchDoctors("");
        tblDoctors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData((DoctorTm) newValue);
            };
        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                sText=newValue;
                searchDoctors(sText);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setData(DoctorTm tm) {
        btnSave.setText("Update Doctor");
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContact.setText(tm.getContact());


    }

    private void searchDoctors(String text) throws SQLException, ClassNotFoundException {
       ObservableList<DoctorTm> tmList = FXCollections.observableArrayList();
            for(DoctorDto dto:new DataAccessCode().searchDoctors(sText)
            ){
                Button btn = new Button("Delete");
                tmList.add(new DoctorTm(dto.getdId(),dto.getName(),dto.getAddress(),dto.getContact(),btn));
                btn.setOnAction(event -> {
                    Alert alert =new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you sure?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if(buttonType.get()==ButtonType.YES){
                        try {
                            if(new DataAccessCode().deleteDoctor(dto.getdId())){
                                searchDoctors("");

                                new Alert(Alert.AlertType.CONFIRMATION,
                                        "doctor Deleted").show();
                            }
                        } catch (SQLException throwables) {
                            new  Alert(Alert.AlertType.CONFIRMATION,
                                    "Error").show();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else{
                        new Alert(Alert.AlertType.CONFIRMATION,
                                "Try Again").show();
                    }
                });

            }
            tblDoctors.setItems(tmList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
      DoctorDto dto = new DoctorDto(txtId.getText(),txtName.getText(),txtAddress.getText(),txtContact.getText());
      if(btnSave.getText().equals("Save Doctor")) {
          try {
              boolean isSaved = new DataAccessCode().saveDoctor(dto);
              if(isSaved) {
                  clear();
                  searchDoctors(sText);
                  new Alert(Alert.AlertType.CONFIRMATION, "Doctor Saved").show();
              }else{
                  new Alert(Alert.AlertType.WARNING,"Try again").show();
              }
          } catch (ClassNotFoundException e) {
              new Alert(Alert.AlertType.ERROR,"Error!!").show();
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }
      }else{
          try {
              boolean isUpdate = new DataAccessCode().updateDoctor(dto);
              if(isUpdate) {
                  clear();
                  searchDoctors("");
                  new Alert(Alert.AlertType.CONFIRMATION, "Doctor Updated").show();
              }else{
                  new Alert(Alert.AlertType.WARNING,"Try again").show();
              }
          } catch (ClassNotFoundException e) {
              new Alert(Alert.AlertType.ERROR,"Error!!").show();
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          }

      }

    }

    public void newDoctorOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save Doctor");
        clear();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) doctorFormContext.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource("../view/DashBoardForm.fxml")))
        );

    }


    private void clear() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }
}

