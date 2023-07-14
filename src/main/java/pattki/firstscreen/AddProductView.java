package pattki.firstscreen;

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
import pattki.firstscreen.Model.InHouse;
import pattki.firstscreen.Model.Inventory;
import pattki.firstscreen.Model.Part;
import pattki.firstscreen.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductView implements Initializable {

    @FXML
    private TextField partsSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the TableView Columns
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

        assoPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

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

    // Part TableView
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

    // Populate the table view with the parts list on the Main Menu
    public void setPartsData(ObservableList<Part> parts) {
        mainPartsTable.setItems(parts);
    }

    // Associated Parts (for the Product) Table View
    @FXML
    private TableView<Part> assoPartsTable;

    @FXML
    private TableColumn<Part, Integer> assoPartIDColumn;

    @FXML
    private TableColumn<Part, String> assoPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> assoPartInvColumn;

    @FXML
    private TableColumn<Part, Double> assoPartPriceColumn;

    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part selectedPart = assoPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            // Create a confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Associated Parts");
            alert.setHeaderText("Remove");
            alert.setContentText("Do you want to remove this associated part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed, remove the associated part
                assoPartsTable.getItems().remove(selectedPart);
            }
        }
    }

    @FXML
    void onActionAddPart(ActionEvent event) {
        Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            // Update the assoPartsTable with the selected part
            assoPartsTable.getItems().add(selectedPart);
        }

        // FUTURE ENHANCEMENT
        // Add a pop up dialog box that gives the user the option to add multiple copies of the part
    }

    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        // Clear previous exception message
        exceptionText.setText("");

        // Check for errors
        StringBuilder exceptionMessage = new StringBuilder();
        if (nameText.getText().isEmpty()) {
            exceptionMessage.append("No data in name field\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inputting an invalid
            double price = Double.parseDouble(priceText.getText());
        } catch (NumberFormatException e) {
            exceptionMessage.append("Price is not a double\n");
        }

        try {
            // RUNTIME ERROR
            // Prevents the user from inputting an invalid inventory
            // Prevents the user from inputting an inventory not between the min/max
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
            int id = ProductIdGenerator.generateNextId();
            String name = nameText.getText();
            Double price = Double.valueOf(priceText.getText());
            int stock = Integer.parseInt(invText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());

            Product newProduct = new Product(id, name, price, stock, min, max);

            // Add the associated parts to the product
            ObservableList<Part> associatedParts = assoPartsTable.getItems();
            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("main-menu-view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }




}
