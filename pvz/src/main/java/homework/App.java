package homework;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    static Timeline tempTimeline = null;

    @Override
    public void start(Stage stage) throws IOException {
        if (Constants.GameModeSingle) {
            // Initialize game in single-player mode
            GlobalControl.initializeEverything();

            // Add initial plants (Gravestones)
            for (int i = 0; i < Constants.MaxRow; i++)
                GlobalControl.addPlants(new Gravestone(i, Constants.MaxColumn - 1));

            // Set and show main scene
            Scene mainScene = new Scene(GlobalControl.rootPane, Constants.WindowWidth, Constants.WindowHeight);
            stage.setScene(mainScene);
            stage.setTitle(Constants.MainStageTitle);
            stage.show();
            return;
        }

        // Initialize root layout
        VBox root = new VBox();
        root.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        HBox row1 = new HBox(15);
        row1.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        HBox row2 = new HBox(20);
        row2.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        // Role selection options
        ObservableList<String> options = FXCollections.observableArrayList("Run as Server", "Connect to Server");
        ComboBox<String> roleField = new ComboBox<>(options);

        // Add role selection to row1
        row1.getChildren().addAll(new Label("Select Your Role:"), roleField);

        // Add listener for role selection
        roleField.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Selected: " + newValue);
                row2.getChildren().clear();
                VBox series = new VBox(10);
                row2.getChildren().add(series);
                series.setAlignment(javafx.geometry.Pos.TOP_CENTER);
                series.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid;");

                if ("Run as Server".equals(newValue)) {
                    // Server setup
                    BroadcastSender.init();
                    BroadcastSender.start();
                    System.err.println("Selected: Run as Server");

                    TextField usernameField = new TextField();
                    Label promptLabel = new Label("");
                    series.getChildren().addAll(
                            new Label("Enter Your Username for other player to find you:"),
                            usernameField,
                            promptLabel
                    );

                    // Update prompt label with username
                    usernameField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            Constants.setUsername(newValue);
                            promptLabel.setText("Other player can find you with name: " + newValue);
                        }
                    });

                    // Setup timeline to check for connection acceptance
                    tempTimeline = new Timeline();
                    tempTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), e -> {
                        if (SocketServer.receivedAccept) {
                            GlobalControl.initializeEverything();
                            for (int i = 0; i < Constants.MaxRow; i++)
                                GlobalControl.addPlants(new Gravestone(i, Constants.MaxColumn - 1));
                            Scene mainScene = new Scene(GlobalControl.rootPane, Constants.WindowWidth, Constants.WindowHeight);
                            stage.setScene(mainScene);
                            tempTimeline.stop();
                        }
                    }));
                    tempTimeline.setCycleCount(Timeline.INDEFINITE);
                    tempTimeline.play();

                    // Initialize server
                    SocketServer.initialize();
                    Constants.isServerNPlants = true;
                } else if ("Connect to Server".equals(newValue)) {
                    // Client setup
                    BroadcastReceiver.init();
                    BroadcastReceiver.start();
                    System.err.println("Selected: Connect to Server");

                    // Table to show available servers
                    TableView<Constants.Player> table = new TableView<>();
                    TableColumn<Constants.Player, String> userNameCol = new TableColumn<>("Server Name");
                    userNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
                    TableColumn<Constants.Player, String> ipColumn = new TableColumn<>("Address");
                    ipColumn.setCellValueFactory(new PropertyValueFactory<>("ip"));
                    table.getColumns().addAll(userNameCol, ipColumn);

                    table.setFixedCellSize(25);
                    DoubleBinding tableHeight = Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(70);
                    table.prefHeightProperty().bind(tableHeight);
                    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    // Update table with available servers
                    Timeline tt = new Timeline();
                    tt.getKeyFrames().add(new KeyFrame(Duration.millis(1000), e -> table.setItems(Constants.player)));
                    tt.setCycleCount(Timeline.INDEFINITE);
                    tt.play();

                    TextField serverName = new TextField();
                    Button connectBtn = new Button("Connect");
                    series.getChildren().addAll(
                            new Label("Type the IP of server name to connect:"),
                            serverName,
                            table,
                            connectBtn
                    );

                    // Handle connect button click
                    connectBtn.setOnMouseClicked(e -> {
                        String ip = serverName.getText();
                        if (true || Constants.OnlineUser.containsKey(ip)) {
                            Constants.ServerIP = ip;
                            if (SocketClient.initialize()) {
                                GlobalControl.initializeEverything();
                                for (int i = 0; i < Constants.MaxRow; i++)
                                    GlobalControl.addPlants(new Gravestone(i, Constants.MaxColumn - 1));
                                Scene mainScene = new Scene(GlobalControl.rootPane, Constants.WindowWidth, Constants.WindowHeight);
                                stage.setScene(mainScene);
                            } else {
                                showAlert("Cannot connect to server!");
                            }
                        } else {
                            showAlert("Server not found!");
                        }
                    });
                    Constants.isServerNPlants = false;
                }
            }
        });

        // Add rows to root
        root.getChildren().addAll(row1, row2);

        // Set and show initial scene
        stage.setScene(new Scene(root, Constants.WindowWidth / 2, Constants.WindowHeight / 2));
        stage.setTitle(Constants.MainStageTitle);
        stage.show();
    }

    private void showAlert(String message) {
        // Show alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // Launch application
        launch();
        BroadcastReceiver.stop();
        BroadcastSender.stop();
    }
}
