package controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CostPlannerCalculator;
import model.InvalidDivisionException;
import org.tinylog.Logger;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

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
    public AnchorPane allPlatform;


    @FXML
public void initialize(){
        //ha a result modosul, akkor nincs mentve a fajl:
        Bindings.bindBidirectional(result.textProperty(), costPlanner.contentProperty());
        Platform.runLater(() -> ((Stage) allPlatform.getScene().getWindow()).titleProperty().bind(
                Bindings.when(costPlanner.filePathProperty().isNotNull())
                        .then(costPlanner.filePathProperty())
                        .otherwise("Unnamed")
                        .concat(Bindings.when(costPlanner.modifiedProperty())
                                .then("*")
                                .otherwise("")))
        );

    }

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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF",".pdf"),
                new FileChooser.ExtensionFilter("PNG", ".png"));

        File file = fileChooser.showSaveDialog(new Stage());
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
        result.setText(costPlanner.calculateSaving(income.getText(),bills.getText(),generalcost.getText(),funcost.getText(),
                foodcost.getText(),healthcost.getText()));
    }

    public void billChanged(ActionEvent actionEvent) {
        result.setText(costPlanner.calculateSaving(income.getText(),bills.getText(),generalcost.getText(),funcost.getText(),
                foodcost.getText(),healthcost.getText()));
        try {
            billsP.setText(costPlanner.percentPickedCost(bills.getText(), generalcost.getText(),funcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            generalCostP.setText(costPlanner.percentPickedCost(generalcost.getText(),healthcost.getText(),bills.getText(),
                    funcost.getText(),foodcost.getText()));
            healthcostP.setText(costPlanner.percentPickedCost(healthcost.getText(),bills.getText(), generalcost.getText(),
                    funcost.getText(),foodcost.getText()));
            funCostP.setText(costPlanner.percentPickedCost(funcost.getText(),bills.getText(), generalcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            foodCostP.setText(costPlanner.percentPickedCost(foodcost.getText(),funcost.getText(),bills.getText(),
                    generalcost.getText(),healthcost.getText()));
        } catch (InvalidDivisionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void generalCostChanged(ActionEvent actionEvent) {
        result.setText(costPlanner.calculateSaving(income.getText(),bills.getText(),generalcost.getText(),funcost.getText(),
                foodcost.getText(),healthcost.getText()));
        try {
            billsP.setText(costPlanner.percentPickedCost(bills.getText(), generalcost.getText(),funcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            generalCostP.setText(costPlanner.percentPickedCost(generalcost.getText(),healthcost.getText(),bills.getText(),
                    funcost.getText(),foodcost.getText()));
            healthcostP.setText(costPlanner.percentPickedCost(healthcost.getText(),bills.getText(), generalcost.getText(),
                    funcost.getText(),foodcost.getText()));
            funCostP.setText(costPlanner.percentPickedCost(funcost.getText(),bills.getText(), generalcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            foodCostP.setText(costPlanner.percentPickedCost(foodcost.getText(),funcost.getText(),bills.getText(),
                    generalcost.getText(),healthcost.getText()));
        } catch (InvalidDivisionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void funCostChanged(ActionEvent actionEvent) {
        result.setText(costPlanner.calculateSaving(income.getText(),bills.getText(),generalcost.getText(),funcost.getText(),
                foodcost.getText(),healthcost.getText()));
        try {
            billsP.setText(costPlanner.percentPickedCost(bills.getText(), generalcost.getText(),funcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            generalCostP.setText(costPlanner.percentPickedCost(generalcost.getText(),healthcost.getText(),bills.getText(),
                    funcost.getText(),foodcost.getText()));
            healthcostP.setText(costPlanner.percentPickedCost(healthcost.getText(),bills.getText(), generalcost.getText(),
                    funcost.getText(),foodcost.getText()));
            funCostP.setText(costPlanner.percentPickedCost(funcost.getText(),bills.getText(), generalcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            foodCostP.setText(costPlanner.percentPickedCost(foodcost.getText(),funcost.getText(),bills.getText(),
                    generalcost.getText(),healthcost.getText()));
        } catch (InvalidDivisionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void foodCostChanged(ActionEvent actionEvent) {
        result.setText(costPlanner.calculateSaving(income.getText(),bills.getText(),generalcost.getText(),funcost.getText(),
                foodcost.getText(),healthcost.getText()));
        try {
            billsP.setText(costPlanner.percentPickedCost(bills.getText(), generalcost.getText(),funcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            generalCostP.setText(costPlanner.percentPickedCost(generalcost.getText(),healthcost.getText(),bills.getText(),
                    funcost.getText(),foodcost.getText()));
            healthcostP.setText(costPlanner.percentPickedCost(healthcost.getText(),bills.getText(), generalcost.getText(),
                    funcost.getText(),foodcost.getText()));
            funCostP.setText(costPlanner.percentPickedCost(funcost.getText(),bills.getText(), generalcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            foodCostP.setText(costPlanner.percentPickedCost(foodcost.getText(),funcost.getText(),bills.getText(),
                    generalcost.getText(),healthcost.getText()));
        } catch (InvalidDivisionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void healthCostChanged(ActionEvent actionEvent) {
        result.setText(costPlanner.calculateSaving(income.getText(),bills.getText(),generalcost.getText(),funcost.getText(),
                foodcost.getText(),healthcost.getText()));
        try {
            billsP.setText(costPlanner.percentPickedCost(bills.getText(), generalcost.getText(),funcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            generalCostP.setText(costPlanner.percentPickedCost(generalcost.getText(),healthcost.getText(),bills.getText(),
                    funcost.getText(),foodcost.getText()));
            healthcostP.setText(costPlanner.percentPickedCost(healthcost.getText(),bills.getText(), generalcost.getText(),
                    funcost.getText(),foodcost.getText()));
            funCostP.setText(costPlanner.percentPickedCost(funcost.getText(),bills.getText(), generalcost.getText(),
                    foodcost.getText(),healthcost.getText()));
            foodCostP.setText(costPlanner.percentPickedCost(foodcost.getText(),funcost.getText(),bills.getText(),
                    generalcost.getText(),healthcost.getText()));
        } catch (InvalidDivisionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


}
