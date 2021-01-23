package ru.itmo.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


enum Architecture {BLOCKING, NONBLOCKING, ASYNCH};
enum Parameter {N, M, DELTA};

public class Main extends Application {

    public static class ExperimentParameters {
        Architecture architecture;
        Parameter parameter;
        long from;
        long to;
        long step;
        int X;
        int N;
        int M;
        int delta;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final ExperimentParameters experimentParameters = new ExperimentParameters();

        stage.setTitle("Set testing parameters");
        Scene scene = new Scene(new Group(), 650, 150);

        final ComboBox<String> archComboBox = new ComboBox<>();
        archComboBox.getItems().addAll(
                "BLOCKING",
                "NONBLOCKING",
                "ASYNCH"
        );

        archComboBox.setValue("BLOCKING");
        archComboBox.setEditable(true);

        final ComboBox<String> estimationComboBox = new ComboBox<>();
        estimationComboBox.getItems().addAll(
                "N",
                "M",
                "DELTA"
        );

        estimationComboBox.setValue("N");
        estimationComboBox.setEditable(true);

        final TextField X = new TextField(""); X.setText("10");
        X.setPrefWidth(80);
        X.setMaxWidth(40);

        final TextField from = new TextField(""); from.setText("1");
        from.setPrefWidth(80);
        from.setMaxWidth(80);

        final TextField to = new TextField(""); to.setText("20");
        to.setPrefWidth(80);
        to.setMaxWidth(80);

        final TextField step = new TextField(""); step.setText("5");
        step.setPrefWidth(80);
        step.setMaxWidth(80);


        final TextField N = new TextField(""); N.setText("10");
        N.setPrefWidth(80);
        N.setMaxWidth(80);

        final TextField M = new TextField(""); M.setText("10");
        M.setPrefWidth(80);
        M.setMaxWidth(80);

        final TextField Delta = new TextField(""); Delta.setText("10");
        Delta.setPrefWidth(80);
        Delta.setMaxWidth(80);

        // --------------- on button --------------------

        final Button button = new Button ("start");
        button.setOnAction((ActionEvent e) -> {
            experimentParameters.architecture = Architecture.valueOf(archComboBox.getValue());
            experimentParameters.parameter = Parameter.valueOf(estimationComboBox.getValue());
            experimentParameters.X = Integer.parseInt(X.getText());
            experimentParameters.N = Integer.parseInt(N.getText());
            experimentParameters.M = Integer.parseInt(M.getText());
            experimentParameters.delta = Integer.parseInt(Delta.getText());
            experimentParameters.from = Long.parseLong(from.getText());
            experimentParameters.to = Long.parseLong(to.getText());
            experimentParameters.step = Long.parseLong(step.getText());

            Tester tester = new Tester(experimentParameters);
            tester.start();

            NumberAxis x = new NumberAxis();
            NumberAxis y = new NumberAxis();

            LineChart<Number, Number> numberLineChart = new LineChart<Number, Number>(x,y);
            numberLineChart.setTitle("Estimation of " + experimentParameters.parameter);
            XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
            XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
            XYChart.Series<Number, Number> series3 = new XYChart.Series<>();


            series1.setName("Sorting");
            series2.setName("Client on server");
            series3.setName("Client on client");

            ObservableList<XYChart.Data<Number, Number>> g1 = FXCollections.observableArrayList(tester.getSorting());
            ObservableList<XYChart.Data<Number, Number>> g2 = FXCollections.observableArrayList(tester.getClientOnServer());
            ObservableList<XYChart.Data<Number, Number>> g3 = FXCollections.observableArrayList(tester.getClientFullTime());

            series1.setData(g1);
            series2.setData(g2);
            series3.setData(g3);


            Scene mainScene = new Scene(numberLineChart, 600,600);
            numberLineChart.getData().add(series1);
            numberLineChart.getData().add(series2);
            Stage primaryStage = new Stage();
            primaryStage.setScene(mainScene);
            stage.hide();
            primaryStage.show();

        });

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

        // --------------------------- forth row -------------------------------

        grid.add(button, 0, 3);

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
