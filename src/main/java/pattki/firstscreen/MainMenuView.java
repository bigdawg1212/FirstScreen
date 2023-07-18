package pattki.firstscreen;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import pattki.firstscreen.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller class for the Main Menu view.
 */
public class MainMenuView implements Initializable {

    @FXML
    private TextField partsSearch;

    @FXML
    private TextField productsSearch;

    @FXML
    private TableView<Part> mainPartsTable;

        // Part TableView
    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;


    @FXML
    private TableView<Product> mainProductsTable;
        // Product TableView
    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInvColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    Stage stage;
    Parent scene;

    /**
     * Event handler for the "Add" button in the Parts section.
     */
    @FXML
    void onActionPartsAdd(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("add-part-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Event handler for the "Modify" button in the Parts section.
     */
    @FXML
    void onActionPartsModify(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modify-part-view.fxml"));
        loader.load();

        ModifyPartView MPVController = loader.getController();
        MPVController.sendPart(mainPartsTable.getSelectionModel().getSelectedItem());


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

        // FUTURE ENHANCEMENT
        // Add an error pop up dialog box that notifies the user that no part is selected to modify

    }

    /**
     * Shows a confirmation dialog for deleting a part.
     *
     * @return true if the user confirms the deletion, false otherwise
     */
    private boolean deletePartConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Event handler for the "Delete" button in the Parts section.
     */
    @FXML
    public void onActionPartsDelete(ActionEvent event) throws IOException {

        Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            // No part selected, show an error message or handle accordingly
            return;
        }

        boolean confirmed = deletePartConfirmation();
        if (confirmed) {
            // Delete the selected part from the Inventory
            Inventory.removePart(selectedPart);
            // Update the table
            mainPartsTable.getItems().remove(selectedPart);
        }

    }

    /**
     * Event handler for the "Add" button in the Products section.
     */
    @FXML
    public void onActionProductsAdd(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-product-view.fxml"));
        Parent addProductView = loader.load();
        AddProductView addProductViewController = loader.getController();

        // Pass the parts data from the Inventory class to the AddProductView controller
        addProductViewController.setPartsData(Inventory.getAllParts());

        // Set the AddProductView as the scene
        Scene scene = new Scene(addProductView);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Event handler for the "Modify" button in the Products section.
     */
    @FXML
    public void onActionProductsModify(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-product-view.fxml"));
        Parent modifyProductView = loader.load();
        ModifyProductView modifyProductViewController = loader.getController();

        // Pass the parts data from the Inventory class to the ModifyProductView controller
        modifyProductViewController.initializeWithParts(Inventory.getAllParts());

        // Pass associated parts from selected product to ModifyProductView controller
        Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        modifyProductViewController.initializeWithAssoParts(selectedProduct.getAllAssociatedParts());

        // Prepopulate the fields with the selected product's data
        modifyProductViewController.setProductData(selectedProduct);

        // Set the ModifyProductView as the scene
        Scene scene = new Scene(modifyProductView);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        // FUTURE ENHANCEMENT
        // Add an error pop up dialog box that notifies the user that there is no Product selected to modify
    }

    /**
     * Event handler for the "Delete" button in the Products section.
     */
    @FXML
    public void onActionProductsDelete(ActionEvent event) {
        Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            // No product selected, show an error message or handle accordingly
            return;
        }

        if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            boolean confirmed = deleteProductConfirmation();
            if (confirmed) {
                // Delete the selected product from the Inventory
                Inventory.removeProduct(selectedProduct);
                // Update the table
                mainProductsTable.getItems().remove(selectedProduct);
            }
        } else {
            // RUNTIME ERROR
            // The product has associated parts, show a warning message
            showDeleteProductWithAssociatedPartsWarning();
        }

        // FUTURE ENHANCEMENT
        // Give the user an option to STILL delete the product, along with its associated parts
    }

    /**
     * Shows a confirmation dialog for deleting a product.
     *
     * @return true if the user confirms the deletion, false otherwise
     */
    private boolean deleteProductConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Shows a warning dialog for attempting to delete a product with associated parts.
     */
    private void showDeleteProductWithAssociatedPartsWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Cannot Delete Product");
        alert.setContentText("The product has associated parts. Remove the associated parts before deleting the product.");
        alert.showAndWait();
    }

    /**
     * Event handler for the "Exit" button.
     */
    @FXML
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Event handler for the initializing the screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // To Do

        mainPartsTable.setItems(Inventory.getAllParts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductsTable.setItems(Inventory.getAllProducts());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


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


        // Product Search Functionality
        productsSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim().toLowerCase();

            // Filter the allProducts list based on the search text
            ObservableList<Product> filteredList = Inventory.getAllProducts().filtered(product -> {
                String productName = product.getName().toLowerCase();
                String productId = String.valueOf(product.getId()).toLowerCase();
                return productName.contains(searchText) || productId.contains(searchText);
            });

            mainProductsTable.setItems(filteredList);
        });

        System.out.println("I am initialized");


    }


}
