package pattki.firstscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pattki.firstscreen.Model.Inventory;
import pattki.firstscreen.Model.Part;
import pattki.firstscreen.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The `ModifyProductView` class is a controller for modifying a product.
 * It allows users to modify the properties of a product and manage its associated parts.
 */
public class ModifyProductView implements Initializable {

    @FXML
    private TextField partsSearch;

    // Temporary list to hold the associated parts
    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    /**
     * Initializes the ModifyProductView controller's screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Main Parts TableView
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Part Search Functionality
        partsSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim().toLowerCase();

            // Filter the allParts list based on the search text
            ObservableList<Part> filteredList = Inventory.getAllParts().filtered(part -> {
                String partName = part.getName().toLowerCase();
                String partId = String.valueOf(part.getId()).toLowerCase();
                return partName.contains(searchText) || partId.contains(searchText);
            });

            mainPartsTable.setItems(filteredList);
        });

        tempAssoPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tempAssoPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tempAssoPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tempAssoPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    // Product Text Field information
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
    private Label exceptionText;

    Stage stage;
    Parent scene;

    // Main Parts TableView
    @FXML
    private TableView<Part> mainPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    // Associated Parts Table View
    @FXML
    private TableView<Part> tempAssociatedPartsTable;

    @FXML
    private TableColumn<Part, Integer> tempAssoPartIDColumn;

    @FXML
    private TableColumn<Part, String> tempAssoPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> tempAssoPartInvColumn;

    @FXML
    private TableColumn<Part, Double> tempAssoPartPriceColumn;

    // Populate the table view with the parts list on the Main Menu
    public void initializeWithParts(ObservableList<Part> parts) {
        mainPartsTable.setItems(parts);
    }
    // Method to initialize associated parts table with data
    public void initializeWithAssoParts(ObservableList<Part> parts) {
        tempAssociatedPartsTable.setItems(parts);
    }

    private Product initialProduct;
    public static int productIndex;

    /**
     * Sets the product data for modification.
     */
    public void setProductData(Product product) {
        initialProduct = new Product(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getMin(),
                product.getMax()
        );

        // Add associated parts to the initialProduct
        initialProduct.getAllAssociatedParts().addAll(product.getAllAssociatedParts());

        // Set the product data in the TextFields and associated parts table
        idText.setText(Integer.toString(product.getId()));
        nameText.setText(product.getName());
        priceText.setText(Double.toString(product.getPrice()));
        invText.setText(Integer.toString(product.getStock()));
        minText.setText(Integer.toString(product.getMin()));
        maxText.setText(Integer.toString(product.getMax()));

        // Prepopulate the associated parts table
        tempAssociatedPartsTable.setItems(product.getAllAssociatedParts());

        productIndex = Inventory.getAllProducts().indexOf(product);

    }

    /**
     * Handles the action when the user clicks the "Add" button to add a part to the product.
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            // Add the selected part to the temporary list
            tempAssociatedParts.add(selectedPart);

            // Update the tempAssociatedPartsTable by adding the selected part to the table
            tempAssociatedPartsTable.getItems().add(selectedPart);
        }

        // FUTURE ENHANCEMENT
        // Add a pop up dialog box that gives the user the option to add multiple copies of the part
    }

    /**
     * Handles the action when the user clicks the "Remove Associated Part" button to remove a part from the product.
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part selectedPart = tempAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            // Create a confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Associated Part");
            alert.setHeaderText("Remove");
            alert.setContentText("Do you want to remove the associated part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed, remove the associated part
                tempAssociatedParts.remove(selectedPart);

                // Update the tempAssociatedPartsTable by removing the selected part from the table
                tempAssociatedPartsTable.getItems().remove(selectedPart);
            }
        }
    }

    /**
     * Handles the action when the user clicks the "Save" button to save the modified product.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
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
            // Prevents the user from inputting a stock value not between the min/max
            // Prevents the user from inputting an invalid stock
            int stock = Integer.parseInt(invText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            if (stock < min || stock > max) {
                exceptionMessage.append("Inventory should be between Min and Max\n");
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
            // Prevents the user from entering a min >= max
            int min = Integer.parseInt(minText.getText());
            if (min >= Integer.parseInt(maxText.getText())) {
                exceptionMessage.append("Min should be less than Max\n");
            }
        } catch (NumberFormatException e) {
            exceptionMessage.append("Min is not an integer\n");
        }

        // Display the error message if any errors occurred
        if (exceptionMessage.length() > 0) {
            exceptionText.setText("Exception: " + exceptionMessage.toString());
        } else {
            // Update the associated parts of the initialProduct
            ObservableList<Part> associatedParts = tempAssociatedPartsTable.getItems();

            // Create a new Product object with the modified values
            Product updatedProduct = new Product(
                    Integer.parseInt(idText.getText()),
                    nameText.getText(),
                    Double.parseDouble(priceText.getText()),
                    Integer.parseInt(invText.getText()),
                    Integer.parseInt(minText.getText()),
                    Integer.parseInt(maxText.getText())
            );

            // Set the associated parts of the updated product
            updatedProduct.getAllAssociatedParts().setAll(associatedParts);

            // Update the product in Inventory
            Inventory.updateProduct(productIndex, updatedProduct);

            // Navigate back to the Main Menu screen
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Handles the action when the user clicks the "Cancel" button to go back to the main menu.
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        // Revert back to Main Menu
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}
