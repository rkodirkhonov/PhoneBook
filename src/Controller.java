package src;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import src.Contact;
import src.Data;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ArrayList<Contact> arrayList;

    @FXML
    private TableView<Contact> contactTable;

    @FXML
    private TableColumn<Contact, String> nameCol,  phoneCol, emailCol, noteCol;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField searchField;


    @Override
    public void initialize(URL location, ResourceBundle resources) { // O(n)
        try {
            arrayList = Data.readData();
        } catch (IOException e) {
            System.out.println(e);
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("about"));
        noteCol.setCellFactory(TextFieldTableCell.forTableColumn());
        noteCol.setOnEditCommit(e-> { // O(n)
            Contact contact = e.getTableView().getItems().get(e.getTablePosition().getRow());
            int index = arrayList.indexOf(contact);
            contact.setAbout(e.getNewValue());
            arrayList.set(index,contact);
            try {
                Data.writeData(arrayList);
            } catch (IOException e1) {

            }
        });

        ObservableList<Contact> data = FXCollections.observableArrayList(arrayList);
        contactTable.setItems(data);
        FilteredList<Contact> filteredList = new FilteredList<>(data);
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {//o(n)
            filteredList.setPredicate(contact -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (contact.getName().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (contact.getPhone_number().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (contact.getEmail().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (contact.getAbout().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        }));

        SortedList<Contact> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(contactTable.comparatorProperty());
        contactTable.setItems(sortedList);

        addBtn.setOnAction(this::addFunction);
        emailField.setOnKeyPressed(this::addFunction);
        deleteBtn.setOnAction(this::deleteFunction);
    }
    void addFunction(KeyEvent e) { //o(n)
        if (e.getCode().equals(KeyCode.ENTER)) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (name.equals("") | phone.equals("") | email.equals("")) {
                JOptionPane.showMessageDialog(null, "Some Input fields are empty");
            } else if (email.matches(".+@gmail.com")) {
                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
                arrayList.add(new Contact(name, phone, email, " ")); // O(1)
                try {
                    Data.writeData(arrayList);
                } catch (IOException exception) {
                    System.out.println("save fail");
                }
                update();
            } else {
                JOptionPane.showMessageDialog(null, "Enter correct email format (example@gmail.com");
            }
        }
    }
    void addFunction(ActionEvent e) { //o(n)
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.equals("") | phone.equals("") | email.equals("")) {
            JOptionPane.showMessageDialog(null, "Some Input fields are empty");
        } else if (email.matches(".+@gmail.com")) {
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            arrayList.add(new Contact(name, phone, email, " ")); // O(1)
            try {
                Data.writeData(arrayList);
            } catch (IOException exception) {
                System.out.println("save fail");
            }
            update();
        } else {
            JOptionPane.showMessageDialog(null, "Enter correct email format (example@gmail.com");
        }
    }
    void deleteFunction(ActionEvent e){//o(1)
//        contactTable.getItems().removeAll(contactTable.getSelectionModel().getSelectedItem()); // we can remove only from the table using this line
        arrayList.remove(contactTable.getSelectionModel().getFocusedIndex()); // it will remove the selected item from the list in case the delete btn pressed after selecting a row
        try {
            Data.writeData(arrayList);
            update();
        } catch (IOException exception) {
            System.out.println("Fail");
        }

    }

    void update() {
        ObservableList<Contact> data = FXCollections.observableArrayList(arrayList);
        contactTable.setItems(data);
        FilteredList<Contact> filteredList = new FilteredList<>(data);
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {//o(n)
            filteredList.setPredicate(contact -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (contact.getName().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (contact.getPhone_number().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (contact.getEmail().toLowerCase().contains(searchKey)) {
                    return true;
                }
                if (contact.getAbout().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        }));

        SortedList<Contact> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(contactTable.comparatorProperty());
        contactTable.setItems(sortedList);
    }
}
