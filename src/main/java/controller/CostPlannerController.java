package controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CostPlannerCalculator;
import model.InvalidDivisionException;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;

import static java.lang.Float.parseFloat;

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
        //handlePieChart();
    }

    @FXML
    private void handlePieChart() throws NumberFormatException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Számlák", Double.parseDouble(bills.getText())),
                            new PieChart.Data("Befizetések", Double.parseDouble(generalcost.getText())),
                            new PieChart.Data("Szórakozás", Double.parseDouble(funcost.getText())),
                            new PieChart.Data("Bevásárlás", Double.parseDouble(foodcost.getText())),
                            new PieChart.Data("Egészség", Double.parseDouble(healthcost.getText()))
                            );
        pieChartofCosts.setData(pieChartData);
        pieChartofCosts.setClockwise(true);
        pieChartofCosts.setLabelLineLength(50);
        pieChartofCosts.setLabelsVisible(true);
        pieChartofCosts.setStartAngle(180);


    }

    @FXML
    private void handleUpdatePieChart() {
        handlePieChart();

            pieChartofCosts.getData().get(0).setPieValue(Double.parseDouble(bills.getText()));
            pieChartofCosts.getData().get(1).setPieValue(Double.parseDouble(generalcost.getText()));
            pieChartofCosts.getData().get(2).setPieValue(Double.parseDouble(funcost.getText()));
            pieChartofCosts.getData().get(3).setPieValue(Double.parseDouble(foodcost.getText()));
            pieChartofCosts.getData().get(4).setPieValue(Double.parseDouble(healthcost.getText()));

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
            handleUpdatePieChart();
        } catch (InvalidDivisionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        ;
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
            handleUpdatePieChart();
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
            handleUpdatePieChart();
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
            handleUpdatePieChart();
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
            handleUpdatePieChart();
        } catch (InvalidDivisionException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


}
