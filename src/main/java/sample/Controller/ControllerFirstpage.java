package sample.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.*;
import sample.DB.SubjectDB;
import sample.DB.UserDB;
import sample.UserData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ControllerFirstpage {
    SubjectDB subjectDB = new SubjectDB();

    UserDB userDB = new UserDB();
    ObservableList<UserData> users = userDB.showDB();

    Map<String, String> sub = subjectDB.getAllPreReq();
    ArrayList<String> predictSubject = new ArrayList<>(sub.keySet());
    @FXML
    TextField searchbox;
    @FXML
    Button search, show, edit, adduser;
    @FXML
    Label anssearch, id, name;


    @FXML
    void initialize() {
        TextFields.bindAutoCompletion(searchbox, predictSubject);
        if (users.isEmpty()) {
            name.setText("GUEST");
            show.setDisable(true);
        } else {
            id.setText("ID: " + userDB.getId().toString());
            name.setText("NAME: " + userDB.getName());
            show.setDisable(false);
        }
    }

    @FXML
    public void pressButtonEdit(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editpage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("EDIT");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pressButtonShow(ActionEvent event) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/showpage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("SHOW");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pressButtonSearch(ActionEvent event) {
        anssearch.setText(subjectDB.getData(searchbox.getText()));
        if (anssearch.getText().equals("")) {
            anssearch.setText("No data");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong CourseID");
            alert.setHeaderText(null);
            alert.setContentText("No CourseID,If you want to add click EDIT TABLE>ADD");
            alert.showAndWait();

        }
        searchbox.clear();
    }

    @FXML
    public void pressButtonUser(ActionEvent event) throws Exception {
        Button click = (Button) event.getSource();

        Stage bg = (Stage) click.getScene().getWindow();

        FXMLLoader p2 = new FXMLLoader(getClass().getResource("/userpage.fxml"));
        try {
            bg.setScene(new Scene((Parent) p2.load(), 900, 550));
            bg.setTitle("USER PAGE");
            bg.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
