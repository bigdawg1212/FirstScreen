package pattki.firstscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pattki.firstscreen.Model.InHouse;
import pattki.firstscreen.Model.Inventory;
import pattki.firstscreen.Model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The `AddPartView` class is a controller for the "Add Part" view.
 * It allows users to add a new part to the inventory.
 */
public class AddPartView implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label exceptionText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField invText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField specialText;

    @FXML
    private Label specialLabel;

    /**
     * Handles the event when the "Cancel" button is clicked.
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        // Navigation to the Main Menu screen
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the event when the Radio buttons are toggled.
     */
    @FXML
    private void handleRadioButtonSelection(ActionEvent event) {
        if (inHouseRadioButton.isSelected()) {
            specialLabel.setText("Machine ID");
            specialText.setPromptText("Insert Machine ID");
        } else if (outsourcedRadioButton.isSelected()) {
            specialLabel.setText("Company ID");
            specialText.setPromptText("Insert Company Name");
        }

        // FUTURE ENHANCEMENT
        // Add a label, near the title, that labels the part as either InHouse or Outsourced.
    }

    /**
     * Handles the event when the 'Save' button is clicked.
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        // Clear previous exception message
        exceptionText.setText("");

        // Check for errors
        StringBuilder exceptionMessage = new StringBuilder();
        if (nameText.getText().isEmpty()) {
            exceptionMessage.append("No data in name field\n");
        }

        try {
            int stock = Integer.parseInt(invText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            // RUNTIME ERROR
            // Prevents the user from inserting a stock value outside the min/max bounds.
            if (stock < min || stock > max) {
                exceptionMessage.append("Stock should be between Min and Max\n");
            }
        } catch (NumberFormatException e) {
            exceptionMessage.append("Inventory is not an integer\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inserting an invalidly formatted price.
            double price = Double.parseDouble(priceText.getText());
        } catch (NumberFormatException e) {
            exceptionMessage.append("Price is not a double\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inputting an invalid Max
            int max = Integer.parseInt(maxText.getText());
        } catch (NumberFormatException e) {
            exceptionMessage.append("Max is not an integer\n");
        }

        try {
            int min = Integer.parseInt(minText.getText());
            // RUNTIME ERROR
            // Prevents the user from inputting a min greater than the max
            // Prevents the user from inputting an invalid min
            if (min >= Integer.parseInt(maxText.getText())) {
                exceptionMessage.append("Min should be less than Max\n");
            }
        } catch (NumberFormatException e) {
            exceptionMessage.append("Min is not an integer\n");
        }

        if (inHouseRadioButton.isSelected()) {
            try {
                // RUNTIME ERROR
                // Prevents the user from inputting an invalid integer
                int machineId = Integer.parseInt(specialText.getText());
            } catch (NumberFormatException e) {
                exceptionMessage.append("Machine ID is not an integer\n");
            }
        }

        // Display the error message if any errors occurred
        if (exceptionMessage.length() > 0) {
            exceptionText.setText("Exception: " + exceptionMessage.toString());
        } else {
            int id = PartIdGenerator.generateNextId();
            String name = nameText.getText();
            Double price = Double.valueOf(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());

            if (inHouseRadioButton.isSelected()) {
                int machineId = Integer.parseInt(specialText.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            } else if (outsourcedRadioButton.isSelected()) {
                String companyName = specialText.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            // Navigate back to the Main Menu screen
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Initializes the AddPartView controller's screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // System.out.println("Add Part Menu Viewed");
    }
}
