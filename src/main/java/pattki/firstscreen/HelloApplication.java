package pattki.firstscreen;

import pattki.firstscreen.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the Hello Application.
 */
public class HelloApplication extends Application {

    /**
     * Entry point of the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Part(int id, String name, double price, int stock, int min, int max);
        InHouse inHouse1 = new InHouse(PartIdGenerator.generateNextId(),"Wheel",20.00, 50, 20, 100, 1);
        InHouse inHouse2 = new InHouse(PartIdGenerator.generateNextId(),"Frame",100.00, 30, 20, 30, 2);
        Outsourced outsourced1 = new Outsourced(PartIdGenerator.generateNextId(),"Bell", 10.00, 20, 10, 60, "BellsRUs");
        Outsourced outsourced2 = new Outsourced(PartIdGenerator.generateNextId(),"Seat", 15.00, 20, 10, 100, "Seats");

        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);

        // Product(int id, String name, double price, int stock, int min, int max)
        Product product1 = new Product(ProductIdGenerator.generateNextId(), "Huffy", 150.00, 10, 2, 20);
        Product product2 = new Product(ProductIdGenerator.generateNextId(), "Omega", 200.00, 5, 1, 10);
        product1.addAssociatedPart(inHouse1);
        product1.addAssociatedPart(inHouse2);
        product2.addAssociatedPart(inHouse1);
        product2.addAssociatedPart(inHouse2);
        product2.addAssociatedPart(outsourced1);
        product2.addAssociatedPart(outsourced2);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch();
    }

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage
     * @throws IOException if an error occurs during loading of FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 410);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}
