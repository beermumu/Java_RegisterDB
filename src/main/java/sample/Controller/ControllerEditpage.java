package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Subject;
import sample.DB.SubjectDB;

import java.io.IOException;
import java.util.*;


public class ControllerEditpage {
    SubjectDB subjectDB = new SubjectDB();
    ObservableList<Subject> subjects = subjectDB.showDB();

    ObservableList<Integer> yearInput = FXCollections.observableArrayList(1, 2, 3, 4);
    ObservableList<Integer> semInput = FXCollections.observableArrayList(1, 2);
    ObservableList<String> colorInput = FXCollections.observableArrayList("GREEN", "BLUE", "RED");

    @FXML
    private TableView<Subject> pageSubject;
    @FXML
    private TextField fieldcourseid, fieldname, fieldprereq, fieldpass;
    @FXML
    private Button update, delete;
    @FXML
    ComboBox yearBox, semBox, colorBox;

    public void initialize() {
        fieldcourseid.setDisable(true);
        fieldname.setDisable(true);
        fieldprereq.setDisable(true);
        fieldpass.setDisable(true);

        setTableView();
        update.setDisable(true);
        delete.setDisable(true);

        pageSubject.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (pageSubject.getSelectionModel().getSelectedItem() != null) {
                    fieldcourseid.setText(pageSubject.getSelectionModel().getSelectedItem().getCOURSEID());
                    fieldname.setText(pageSubject.getSelectionModel().getSelectedItem().getNAME());
                    fieldpass.setText(pageSubject.getSelectionModel().getSelectedItem().getPASS());
                    fieldprereq.setText(pageSubject.getSelectionModel().getSelectedItem().getPREREQ());

                    fieldpass.setDisable(true);
                    update.setDisable(false);
                    delete.setDisable(false);

                    fieldname.setDisable(false);
                    fieldprereq.setDisable(false);

                    colorBox.setValue(pageSubject.getSelectionModel().getSelectedItem().getCOLOR());
                    colorBox.setItems(colorInput);

                    yearBox.setValue(pageSubject.getSelectionModel().getSelectedItem().getYEAR());
                    yearBox.setItems(yearInput);

                    semBox.setValue(pageSubject.getSelectionModel().getSelectedItem().getSEM());
                    semBox.setItems(semInput);
                }
            }
        });

    }

    public void setTableView() {
        subjects = subjectDB.showDB();
        Collections.sort(subjects, new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                if (o1.getYEAR() > o2.getYEAR()) return 1;
                if (o1.getYEAR() < o2.getYEAR()) return -1;
                else {
                    if (o1.getSEM() > o2.getSEM()) return 1;
                    if (o1.getSEM() < o2.getSEM()) return -1;
                    return 0;
                }
            }
        });

        pageSubject.setItems(subjects);
    }

    @FXML
    public void pressUpdate(ActionEvent event) {

        subjectDB.updateData(fieldcourseid.getText(), fieldname.getText(), fieldprereq.getText(), fieldpass.getText(), colorBox.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(yearBox.getSelectionModel().getSelectedItem().toString()), Integer.parseInt(semBox.getSelectionModel().getSelectedItem().toString()));
        fieldcourseid.clear();
        fieldname.clear();
        fieldprereq.clear();
        fieldpass.clear();
        setTableView();

        delete.setDisable(true);
        update.setDisable(true);

        fieldname.setDisable(true);
        fieldprereq.setDisable(true);

    }

    @FXML
    public void pressDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("This Subject will Gone");
        alert.setHeaderText("If you click OK this subject will be gone");
        alert.setContentText("Are you Ok with this");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Map<String, String> h = subjectDB.getAllPreReq();
            ArrayList<String> s = new ArrayList<>(h.values());
            int report = 0;
            for (int i = 0; i < s.size(); i++) {
                if (s.get(i).contains(pageSubject.getSelectionModel().getSelectedItem().getCOURSEID())) {
                    report = 1;
                }
            }
            if (report == 0) {
                changeNotPass();
                subjectDB.deleteData(fieldcourseid.getText());
                fieldcourseid.clear();
                fieldname.clear();
                fieldprereq.clear();
                fieldpass.clear();
                setTableView();

                delete.setDisable(true);
                update.setDisable(true);

                fieldname.setDisable(true);
                fieldprereq.setDisable(true);
            } else {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning");
                alert2.setHeaderText("Wrong Delete Subject");
                alert2.setContentText("This Subject is prereq for other subject");

                alert2.showAndWait();
            }
        }
    }

    @FXML
    public void pressButtonAdd(ActionEvent event) {

        Button click = (Button) event.getSource();

        Stage bg = (Stage) click.getScene().getWindow();

        FXMLLoader p2 = new FXMLLoader(getClass().getResource("/addpage.fxml"));
        try {
            bg.setScene(new Scene((Parent) p2.load(), 900, 550));
            bg.setTitle("ADD SUBJECT");
            bg.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public void changeNotPass() {
        subjectDB.updatePass(pageSubject.getSelectionModel().getSelectedItem().getCOURSEID(), "NotPass");
        if (pageSubject.getSelectionModel().getSelectedItem() != null) {
            Map<String, String> h = subjectDB.getAllPreReq();
            List<String> val = new ArrayList<String>(h.values());
            List<String> key = new ArrayList<String>(h.keySet());
            List<String> courseidCollect = new ArrayList<String>();
            courseidCollect.add(pageSubject.getSelectionModel().getSelectedItem().getCOURSEID());
            int c = 0;
            while (c < courseidCollect.size()) {
                for (int i = 0; i < val.size(); i++) {
                    if (val.get(i).contains("/")) {
                        String[] mid = val.get(i).split("/");
                        String[] hold1 = mid[0].split(",");
                        for (int j = 0; j < hold1.length; j++) {
                            if (hold1[j].contains(courseidCollect.get(c))) {
                                courseidCollect.add(key.get(i));
                                subjectDB.updatePass(key.get(i), "NotPass");
                            }
                        }

                        int count = 0;
                        String[] hold2 = mid[1].split(",");
                        if (val.get(i).contains(courseidCollect.get(c))) {
                            for (int j = 0; j < hold2.length; j++) {
                                if (subjectDB.getPass(hold2[j]).equals("Pass")) {
                                    count++;
                                }
                                if (count == 1 && j == hold2.length - 1) {
                                    courseidCollect.add(key.get(i));
                                    subjectDB.updatePass(key.get(i), "NotPass");
                                }
                            }
                        }


                    } else if (val.get(i).contains("-")) {
                        String[] hold1 = val.get(i).split("-");
                        for (int j = 0; j < hold1.length; j++) {
                            if (hold1[j].contains(courseidCollect.get(c))) {
                                courseidCollect.add(key.get(i));
                                subjectDB.updatePass(key.get(i), "NotPass");
                            }
                        }
                    } else if (val.get(i).contains(",")) {
                        int count = 1;
                        String[] hold1 = val.get(i).split(",");
                        if (val.get(i).contains(courseidCollect.get(c))) {
                            for (int j = 0; j < hold1.length; j++) {
                                if (subjectDB.getPass(hold1[j]).equals("Pass")) {
                                    count++;
                                }
                                if (count == 1 && j == hold1.length - 1) {
                                    courseidCollect.add(key.get(i));
                                    subjectDB.updatePass(key.get(i), "NotPass");
                                }
                            }
                        }
                    } else {
                        if (val.get(i).contains(courseidCollect.get(c))) {
                            subjectDB.updatePass(key.get(i), "NotPass");
                            courseidCollect.add(key.get(i));
                        }
                    }
                }
                c++;
            }
            setTableView();
        }
    }
}