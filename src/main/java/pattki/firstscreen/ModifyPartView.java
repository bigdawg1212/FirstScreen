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
import pattki.firstscreen.Model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The `ModifyPartView` class represents the controller for the modify part view.
 * It allows the user to modify an existing part in the inventory.
 */
public class ModifyPartView implements Initializable {

    Stage stage;
    Parent scene;
    //Declare the part instance variable
    private Part part;
    // To store selection state of Radio Button
    private boolean isPartInHouse;
    // To store selection state of Radio Button
    private boolean isPartOutsourced;


    @FXML
    private TextField idText;

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
    private TextField specialText;

    @FXML
    private Label specialLabel;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private Label exceptionText;

    /**
     * Handles the event when the "Display Main Menu" button is clicked.
     *
     * @param event the action event
     * @throws IOException if an error occurs while loading the main menu view
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Handles the event when a radio button is selected.
     *
     * @param event the action event
     */
    @FXML
    private void handleRadioButtonSelection(ActionEvent event) {
        if (inHouseRadioButton.isSelected()) {
            specialLabel.setText("Machine ID");
            specialText.setPromptText("Insert Machine ID");
            isPartInHouse = true;
            isPartOutsourced = false;
        } else if (outsourcedRadioButton.isSelected()) {
            specialLabel.setText("Company ID");
            specialText.setPromptText("Insert Company Name");
            isPartInHouse = false;
            isPartOutsourced = true;
        }

        // FUTURE ENHANCEMENT
        // Add a label, near the title, that labels the part as either InHouse or Outsourced.
    }

    public static int partIndex;

    /**
     * Sends the selected part to the modify part view.
     *
     * @param part the part to be modified
     */
    public void sendPart(Part part)
    {
        this.part = part;

        idText.setText(String.valueOf(part.getId()));
        nameText.setText(part.getName());
        invText.setText(String.valueOf(part.getStock()));
        priceText.setText(String.valueOf(part.getPrice()));
        maxText.setText(String.valueOf(part.getMax()));
        minText.setText(String.valueOf(part.getMin()));

        partIndex = Inventory.getAllParts().indexOf(part);


        // Here I send the proper subclass to the Modify Part page,
        // which is either 'Inhouse' or 'Outsourced'
        // and send the existing Radio Button selections

        if (part instanceof InHouse) {
            int machineId = ((InHouse) part).getMachineId();
            inHouseRadioButton.setSelected(true);
            specialText.setText(String.valueOf(machineId));
        } else if (part instanceof Outsourced) {
            String companyName = ((Outsourced) part).getCompanyName();
            specialLabel.setText("Company ID");
            outsourcedRadioButton.setSelected(true);
            specialText.clear();
            specialText.setText(companyName);
        }
    }

    /**
     * Handles the event when the "Save" button is clicked to save the modified part.
     *
     * @param event the action event
     * @throws IOException if an error occurs while loading the main menu view
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        // Clear previous exception message
        exceptionText.setText("");

        // Check for errors
        StringBuilder exceptionMessage = new StringBuilder();

        // RUNTIME ERROR
        // Prevents the user from inputting no name
        if (nameText.getText().isEmpty()) {
            exceptionMessage.append("No data in name field\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inputting an invalid price
            double price = Double.parseDouble(priceText.getText());
        } catch (NumberFormatException e) {
            exceptionMessage.append("Price is not a double\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inputting an invalid stock value
            // Prevents the user from inputting a stock not between the min/max
            int stock = Integer.parseInt(invText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            if (stock < min || stock > max) {
                exceptionMessage.append("Stock should be between Min and Max\n");
            }
        } catch (NumberFormatException e) {
            exceptionMessage.append("Inventory is not an integer\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inputting an invalid max
            int max = Integer.parseInt(maxText.getText());
        } catch (NumberFormatException e) {
            exceptionMessage.append("Max is not an integer\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inputting an invalid min
            // Prevents the user from inputting a min >= max
            int min = Integer.parseInt(minText.getText());
            if (min >= Integer.parseInt(maxText.getText())) {
                exceptionMessage.append("Min should be less than Max\n");
            }
        } catch (NumberFormatException e) {
            exceptionMessage.append("Min is not an integer\n");
        }

        if (isPartInHouse) {
            try {
                // RUNTIME ERROR
                // Prevents the user from inputting an invalid Machine ID
                int machineId = Integer.parseInt(specialText.getText());
            } catch (NumberFormatException e) {
                exceptionMessage.append("Machine ID is not an integer\n");
            }
        }

        // Display the error message if any errors occurred
        if (exceptionMessage.length() > 0) {
            exceptionText.setText("Exception: " + exceptionMessage.toString());
        } else {
            // Retrieve the values from the TextFields
            String name = nameText.getText();
            Double price = Double.valueOf(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            int id = Integer.parseInt(idText.getText());

            // Create a new instance of the appropriate subclass
            Part updatedPart;
            if (isPartInHouse) {
                int machineId = Integer.parseInt(specialText.getText());
                updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
            } else {
                String companyName = specialText.getText();
                updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
            }

            updatedPart.setId(part.getId()); // Preserve existing ID
            // Update common attributes of the part from Text Fields
            updatedPart.setName(name);
            updatedPart.setPrice(price);
            updatedPart.setStock(stock);
            updatedPart.setMax(max);
            updatedPart.setMin(min);

            // Update the part in the Inventory
            Inventory.updatePart(partIndex, updatedPart);

            // Navigate back to the Main Menu screen
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Initializes the modify part view.
     *
     * @param url            the URL to the FXML file
     * @param resourceBundle the resource bundle associated with the FXML file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // System.out.println("Modify Part Screen Viewed");
    }
}
