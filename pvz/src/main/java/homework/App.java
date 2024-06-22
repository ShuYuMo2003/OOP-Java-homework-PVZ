package homework;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.binding.Bindings;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.io.IOException;

import javax.swing.ButtonModel;

/**
 * JavaFX App
 */
public class App extends Application {

    static Timeline tempTimeline = null;

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();   root.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        HBox row1 = new HBox(15); row1.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        HBox row2 = new HBox(20); row2.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        ObservableList<String> options =
            FXCollections.observableArrayList(
                "Run as Server",
                "Connect to Server"
            );
        ComboBox<String> roleField = new ComboBox<String>(options);
        row1.getChildren().addAll(
            new Label("Select Your Role:"),
            roleField
        );

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
                    BroadcastSender.init();
                    BroadcastSender.start();
                    System.err.println("Selected: Run as Server");

                    TextField usernameField = new TextField();
                    Label promptLabel = new Label("");
                    series.getChildren().addAll(
                        new Label("Enter You Username for other player to find you:"),
                        usernameField,
                        promptLabel
                    );
                    usernameField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            Constants.setUsername(newValue);
                            promptLabel.setText("Other player can find you with name: " + newValue);
                        }
                    });

                    tempTimeline = new Timeline();
                    tempTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), e->{
                        if(SocketServer.receivedAccept){
                            GlobalControl.initializeEverything();
                            Scene mainScene = new Scene(GlobalControl.rootPane,
                                Constants.WindowWidth,
                                Constants.WindowHeight);
                            stage.setScene(mainScene);
                            tempTimeline.stop();
                        }
                    }));
                    tempTimeline.setCycleCount(Timeline.INDEFINITE);
                    tempTimeline.play();

                    SocketServer.initialize();
                    Constants.isServerNPlants = true;
                } else if ("Connect to Server".equals(newValue)) {
                    BroadcastReceiver.init();
                    BroadcastReceiver.start();
                    System.err.println("Selected: Connect to Server");

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
                    Timeline tt = new Timeline();
                    tt.getKeyFrames().add(new javafx.animation.KeyFrame(javafx.util.Duration.millis(1000), e->{
                        table.setItems(Constants.player);
                    }));
                    tt.setCycleCount(Timeline.INDEFINITE);
                    tt.play();

                    TextField serverName = new TextField();
                    Button connectBtn = new Button("Connect");
                    series.getChildren().addAll(
                        new Label("Type the ip of server name to connect:"),
                        serverName,
                        table,
                        connectBtn
                    );
                    connectBtn.setOnMouseClicked(e -> {
                        String ip = serverName.getText();
                        if(Constants.OnlineUser.containsKey(ip)){
                            Constants.ServerIP = ip;
                            if(SocketClient.initialize()){
                                GlobalControl.initializeEverything();
                                Scene mainScene = new Scene(GlobalControl.rootPane,
                                    Constants.WindowWidth,
                                    Constants.WindowHeight);
                                stage.setScene(mainScene);
                            } else {
                                showAlert("Can not connect to server!");
                            }
                        } else {
                            showAlert("Server not found!");
                        }
                    });
                    Constants.isServerNPlants = false;
                }
            }
        });

        root.getChildren().addAll(row1, row2);

        stage.setScene(new Scene(root, Constants.WindowWidth / 2, Constants.WindowHeight / 2));

        // btn.setOnMouseClicked(e -> {
        //     GlobalControl.initializeEverything();
        //     Scene mainScene = new Scene(GlobalControl.rootPane,
        //             Constants.WindowWidth,
        //             Constants.WindowHeight);
        //     stage.setScene(mainScene);
        // });

        stage.show();
        stage.setTitle(Constants.MainStageTitle);
        // BroadcastReceiver.stop();
        // BroadcastSender.stop();
        // stage.setResizable(false);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
        BroadcastReceiver.stop();
        BroadcastSender.stop();
    }
}

