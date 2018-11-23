package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DB.SubjectDB;
import sample.DB.UserDB;
import sample.UserData;

import java.io.IOException;
import java.util.Calendar;

public class ControllerUserpage {
    UserDB userDB = new UserDB();
    ObservableList<UserData> users = userDB.showDB();
    SubjectDB subjectDB = new SubjectDB();
    ObservableList<Integer> semInput = FXCollections.observableArrayList(1, 2);

    @FXML
    Button clear, save;
    @FXML
    TextField id, name;
    @FXML
    ComboBox semBox;

    @FXML
    void initialize() {
        if (users.isEmpty()) {
            semBox.setValue(1);
            semBox.setItems(semInput);
            id.setDisable(false);
            name.setDisable(false);
            clear.setDisable(true);
        } else {
            semBox.setValue(userDB.getSem());
            semBox.setItems(semInput);
            id.setDisable(true);
            name.setDisable(true);
            name.setText(userDB.getName());
            id.setText(userDB.getId());
            clear.setDisable(false);
        }

    }

    @FXML
    public void cleanClear(ActionEvent event) {
        userDB.deleteData();
        semBox.setValue(1);
        semBox.setItems(semInput);
        save.setDisable(false);
        id.setDisable(false);
        name.setDisable(false);
        semBox.setDisable(false);
        clear.setDisable(true);
        subjectDB.updateAllPasstoZero();
        id.clear();
        name.clear();
    }

    @FXML
    public void pressButtonSave(ActionEvent event) {
        int year = Calendar.getInstance().get(Calendar.YEAR) - 1957;
        try {
            if (Long.parseLong(id.getText()) >= 0 && Integer.parseInt(id.getText().substring(0, 2)) <= year && id.getText().length() == 10) {
                userDB.addData(id.getText(), name.getText(), Integer.parseInt(semBox.getSelectionModel().getSelectedItem().toString()));
            } else {
                throw new NumberFormatException();
            }
            Button click = (Button) event.getSource();

            Stage bg = (Stage) click.getScene().getWindow();

            FXMLLoader p2 = new FXMLLoader(getClass().getResource("/firstpage.fxml"));
            try {
                bg.setScene(new Scene((Parent) p2.load(), 900, 550));
                bg.setTitle("Regis CS KU");
                bg.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Not in format");
            alert.setContentText("ID should numeric an ID less than 10 number");
            alert.showAndWait();
        }

    }

    @FXML
    public void pressBack(ActionEvent event) {
        Button click = (Button) event.getSource();

        Stage bg = (Stage) click.getScene().getWindow();

        FXMLLoader p2 = new FXMLLoader(getClass().getResource("/firstpage.fxml"));
        try {
            bg.setScene(new Scene((Parent) p2.load(), 900, 550));
            bg.setTitle("Regis CS KU");
            bg.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
