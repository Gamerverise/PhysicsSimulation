package gui.widgets;

import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BatteryVoltageGraphWidget extends LineChart<Number, Number> {

    /****************** Methods for Convenient Type Casting *******************/

    ObservableList<Series<Number, Number>> get_data() {
        return (ObservableList<Series<Number, Number>>) getData();
    }

    Series<Number, Number> get_series() {
        // We have only one series for this graph

        return (Series<Number, Number>) get_data().get(0);
    }

    ObservableList<Data<Number, Number>> get_series_data() {
        return (ObservableList<Data<Number, Number>>) get_series().getData();
    }

    Series<Number, Number> new_series() {
        return new Series<Number, Number>();
    }

    Data<Number, Number> new_data_point(double elapsed_time, double voltage) {
        return new Data<Number, Number>(elapsed_time, voltage);
    }

    /***************************************************************/

    BatteryVoltageGraphWidget() {
        super(new NumberAxis(0, 180, 10), new NumberAxis(0, 15, 1));

        getXAxis().setLabel("Elapsed Time");
        getYAxis().setLabel("Voltage");

        get_data().add(new_series());

        for (int i = 0; i < 100; i++)
            add_data_point(i, 15 - Math.log(i));
    }

    void add_data_point(double elapsed_time, double voltage) {
        get_series_data().add(new_data_point(elapsed_time, voltage));
    }
}
