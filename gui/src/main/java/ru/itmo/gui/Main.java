package ru.itmo.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;




public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Set testing parameters");
        Scene scene = new Scene(new Group(), 650, 100);
        final String[] parameters = {"", ""};

        final ComboBox<String> archComboBox = new ComboBox<>();
        archComboBox.getItems().addAll(
                "blocking",
                "nonblocking",
                "asynch"
        );
        archComboBox.setPromptText("architectures");
        archComboBox.setValue("blocking");
        archComboBox.setEditable(true);
        archComboBox.valueProperty().addListener((ov, t, t1) -> parameters[0] = t1);


        final ComboBox<String> estimationComboBox = new ComboBox<>();
        estimationComboBox.getItems().addAll(
                "N",
                "M",
                "delta"
        );
        estimationComboBox.setPromptText("architectures");
        estimationComboBox.setValue("N");
        estimationComboBox.setEditable(true);
        estimationComboBox.valueProperty().addListener((ov, t, t1) -> parameters[1] = t1);
        final TextField X = new TextField("");
        X.setPrefWidth(80);
        X.setMaxWidth(40);

        final TextField from = new TextField("");
        from.setPrefWidth(80);
        from.setMaxWidth(40);

        final TextField to = new TextField("");
        to.setPrefWidth(80);
        to.setMaxWidth(40);

        final TextField step = new TextField("");
        step.setPrefWidth(80);
        step.setMaxWidth(40);


        final TextField N = new TextField("");
        N.setPrefWidth(80);
        N.setMaxWidth(40);

        final TextField M = new TextField("");
        M.setPrefWidth(80);
        M.setMaxWidth(40);

        final TextField Delta = new TextField("");
        Delta.setPrefWidth(80);
        Delta.setMaxWidth(40);

        // --------------- on button --------------------

        final Button button = new Button ("start");




        GridPane grid = new GridPane();

        grid.setVgap(6);
        grid.setHgap(4);
        // --------------------------- fst row -------------------------------
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("  Server type: "), 0, 0);
        grid.add(archComboBox, 1, 0);
        grid.add(new Label("  Parameter: "), 2, 0);
        grid.add(estimationComboBox, 3, 0);
        grid.add(new Label("  X: "), 4, 0);
        grid.add(X, 5, 0);

        // --------------------------- snd row -------------------------------

        grid.add(new Label("  from: "), 0, 1);
        grid.add(from, 1, 1);
        grid.add(new Label("  to: "), 2, 1);
        grid.add(to, 3, 1);
        grid.add(new Label("  step: "), 4, 1);
        grid.add(step, 5, 1);

        // --------------------------- third row -------------------------------

        grid.add(new Label("  N: "), 0, 2);
        grid.add(N, 1, 2);
        grid.add(new Label("  M: "), 2, 2);
        grid.add(M, 3, 2);
        grid.add(new Label("  delta: "), 4, 2);
        grid.add(Delta, 5, 2);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
        /*
        primaryStage.setTitle("JavaFX Chart (Series)");

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<Number, Number>(x,y);
        numberLineChart.setTitle("Series");
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("cos(x)");
        series1.setName("sin(x)");
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas2 = FXCollections.observableArrayList();
        for(int i=0; i<20; i++){
            datas.add(new XYChart.Data(i,Math.sin(i)));
            datas2.add(new XYChart.Data(i,Math.cos(i)));
        }

        series1.setData(datas);
        series2.setData(datas2);

        Scene scene = new Scene(numberLineChart, 600,600);
        numberLineChart.getData().add(series1);
        numberLineChart.getData().add(series2);
        primaryStage.setScene(scene);

        primaryStage.show();
        */
    }

}
