package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DB.SubjectDB;

import java.io.IOException;

public class ControllerAddpage {
    SubjectDB subjectDB = new SubjectDB();
    ObservableList<Integer> yearInput = FXCollections.observableArrayList(1,2,3,4);
    ObservableList<Integer> semInput = FXCollections.observableArrayList(1,2);
    ObservableList<String> colorInput = FXCollections.observableArrayList("GREEN","BLUE","RED");

    @FXML
    Button clear, save, back;
    @FXML
    TextField courseid, name, prereq;
    @FXML
    ComboBox yearBox , semBox , colorBox;


    @FXML
    void initialize(){

        yearBox.setValue(1);
        yearBox.setItems(yearInput);

        semBox.setValue(1);
        semBox.setItems(semInput);

        colorBox.setValue("GREEN");
        colorBox.setItems(colorInput);
    }

    @FXML
    public void cleanClear(ActionEvent event) {
        courseid.clear();
        name.clear();
        prereq.clear();
    }

    @FXML
    public void pressButtonSave(ActionEvent event) {
        try {
            if (Integer.parseInt(courseid.getText())>=0){
                subjectDB.addData(courseid.getText(), name.getText(), prereq.getText(), "NotPass", colorBox.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(yearBox.getSelectionModel().getSelectedItem().toString()), Integer.parseInt(semBox.getSelectionModel().getSelectedItem().toString()));
            }else {
                throw new NumberFormatException();
            }
            Button click = (Button) event.getSource();

            Stage bg = (Stage) click.getScene().getWindow();

            FXMLLoader p2 = new FXMLLoader(getClass().getResource("/editpage.fxml"));
            try {
                bg.setScene(new Scene((Parent) p2.load(), 900, 550));
                bg.setTitle("SHOW TABLE");
                bg.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Not in format");
            alert.setContentText("CourseID should numeric. Please edit to format.");
            alert.showAndWait();

        }

    }

    @FXML
    public void pressBack(ActionEvent event) {
        Button click = (Button) event.getSource();

        Stage bg = (Stage) click.getScene().getWindow();

        FXMLLoader p2 = new FXMLLoader(getClass().getResource("/editpage.fxml"));
        try {
            bg.setScene(new Scene((Parent) p2.load(), 900, 550));
            bg.setTitle("SHOW TABLE");
            bg.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
