package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CostPlannerCalculator {

   //1. mentési folyamat a save és save as gombokhoz:
   private final StringProperty filePath = new SimpleStringProperty(null);

   private final StringProperty content = new SimpleStringProperty("");

   private final BooleanProperty modified = new SimpleBooleanProperty(false);

   public CostPlannerCalculator(){
      content.addListener((observable, oldValue, newValue) -> {
         modified.set(true);
         Logger.debug("Content changed");
      });
   }
   public StringProperty filePathProperty() {
      return filePath;
   }

   public final String getFilePath() {
      return filePath.get();
   }

   public StringProperty contentProperty() {
      return content;
   }

   public final String getContent() {
      return content.get();
   }

   public BooleanProperty modifiedProperty() {
      return modified;
   }

   public final boolean getModified() {
      return modified.get();
   }


   public void save() throws IOException {
      if (filePath.get() == null) {
         throw new IllegalStateException();
      }
      saveAs(filePath.get());
   }

   public void saveAs(String filePath) throws IOException {
      Files.writeString(Path.of(filePath), content.get());
      this.filePath.set(filePath);
      modified.set(false);
   }


   public void addAllCost(String bills){
       //TODO
   }
   public void calculateSaving(String income, String bills){
       //TODO

   }


}
