package questionsix;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class QuestionSix extends Application {

    private Map<String, String> CSVData;
    private CSVReaderHeaderAware CSVReader;
    private TableView table = new TableView();
    final VBox vbox = new VBox();

    public void start(Stage stage) {
        Group root = new Group(); 
        Scene scene = new Scene(root, 1200, 600); // Scene of application

        Button openbutton = new Button("Open File");
        openbutton.setLayoutX(10);
        openbutton.setLayoutY(5);
        
        openbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Select CSV");
                File defaultDirectory = new File("c:/");
                chooser.setInitialDirectory(defaultDirectory);
                Window primaryStage = null;
                File file = chooser.showOpenDialog(primaryStage);
                List<String[]> tempCSVData = null;
                try {
                    CSVReader = new CSVReaderHeaderAware(new FileReader(file.getPath()));
                } catch (Exception ex) {
                    Logger.getLogger(QuestionSix.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    table.setItems(generateData());
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Error loading File");
                    alert.setContentText(ex.toString());
                    alert.setTitle("ERROR");
                    Optional<ButtonType> result = alert.showAndWait();
                }

                table.setLayoutX(0);
                table.setLayoutX(50);

                vbox.setSpacing(5);
                vbox.setPadding(new Insets(10, 0, 0, 10));
                vbox.getChildren().addAll(table);

            }
        });
        
        vbox.getChildren().add(openbutton);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setTitle("CSV Viewer");
        stage.setScene(scene); // scene onto the Stage
        stage.show(); // show the stage

    }

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args); // launch a standalone application
    }

    public ObservableList<Map> generateData() throws IOException, CsvValidationException {
        ObservableList<Map> allData = FXCollections.observableArrayList();

        for (int i = 0; ; i++) {
            Map<String, String> dataRow = CSVReader.readMap();
            if (i == 0) {
                for (String key : dataRow.keySet()) {
                    TableColumn<Map, String> dc = new TableColumn<>(key);
                    dc.setCellValueFactory(new MapValueFactory(key));
                    table.getColumns().add(dc);
                }
            }
            if (dataRow != null)
                allData.add(dataRow);
            else 
                break;
        }
        return allData;
    }
}
