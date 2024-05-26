package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.CostPlannerCalculator;
import org.tinylog.Logger;

import java.io.IOException;

public class CostPlannerController {
    public TextField income;
    public TextField bills;
    public TextField generalcost;
    public TextField funcost;
    public TextField foodcost;
    public TextField healthcost;
    public Label result;
    public Label billsP;
    public Label healthcostP;
    public Label generalCostP;
    public Label funCostP;
    public Label foodCostP;
    public Button saveAs;
    public Button save;
    public PieChart pieChartofCosts;


private final CostPlannerCalculator costPlanner = new CostPlannerCalculator();

@FXML
public void initialize(){};

    public void onQuit(ActionEvent actionEvent) {
        Logger.info("Terminating");
        Platform.exit();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (costPlanner.getFilePath() != null) {
            Logger.debug("Saving file");
            try {
                costPlanner.save();
            } catch (IOException e) {
                Logger.error(e, "Failed to save file");
            }
        } else {
            performSaveAs();
        }
    }
    @FXML
     public void saveAs(ActionEvent actionEvent) {
        performSaveAs();
    }
    private void performSaveAs() {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        var file = fileChooser.showSaveDialog(null);
        if (file != null) {
            Logger.debug("Saving file as {}", file);
            try {
                costPlanner.saveAs(file.getPath());
            } catch (IOException e) {
                Logger.error(e, "Failed to save file");
            }
        }
    }

    public void incomeChanged(ActionEvent actionEvent) {
    }

    public void billChanged(ActionEvent actionEvent) {
    }

    public void generalCostChanged(ActionEvent actionEvent) {
    }

    public void funCostChanged(ActionEvent actionEvent) {
    }

    public void foodCostChanged(ActionEvent actionEvent) {
    }

    public void healthCostChanged(ActionEvent actionEvent) {
    }
}
