package view;

import javafx.event.ActionEvent;
import math.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    final NumberAxis xResultAmplAxis = new NumberAxis();
    final NumberAxis yResultAmplAxis = new NumberAxis();

    final NumberAxis xResultPhaseAxis = new NumberAxis();
    final NumberAxis yResultPhaseAxis = new NumberAxis();

    public NumberAxis xFuncPhaseAxis = new NumberAxis();
    public NumberAxis yFuncPhaseAxis = new NumberAxis();

    public NumberAxis xFuncAmplAxis = new NumberAxis();
    public NumberAxis yFuncAmplAxis = new NumberAxis();

    @FXML
    LineChart<Number, Number> resultAmplChart = new LineChart<Number, Number>(xResultAmplAxis, yResultAmplAxis);

    @FXML
    LineChart<Number, Number> resultPhaseChart = new LineChart<Number, Number>(xResultAmplAxis, yResultAmplAxis);

    @FXML
    LineChart<Number, Number> funcPhaseChart = new LineChart<Number, Number>(xFuncPhaseAxis, yFuncPhaseAxis);

    @FXML
    LineChart<Number, Number> funcAmplChart = new LineChart<Number, Number>(xFuncAmplAxis, yFuncAmplAxis);


    private XYChart.Series graphSeria = new XYChart.Series();
    private XYChart.Series graphSeria2 = new XYChart.Series();


    public void initialize(URL location, ResourceBundle resources) {

        Integer N = 1000;
        Double a = 5.0;
        Integer M = 32768;
        Double b = N * N / (8 * a * M);
        AnalyticSolution analytic = new AnalyticSolution(-b, b, 1000);

        RiemannSolver1D riemannSolver1D = new RiemannSolver1D(-a, a, -b, b) {
            public Complex function(Double x) {
                //return new Complex(Math.exp(-x*x), 0.0);
                if (x < -1.0 || x > 3.0) {
                    return new Complex(0.0, 0.0);
                } else if (x == -1.0 || x == 3.0) {
                    return new Complex(0.5, 0.0);
                } else return new Complex(1.0, 0.0);
            }
        };
        riemannSolver1D.solve();
        FFTSolver1D fftSolver = new FFTSolver1D() {
            @Override
            public double function(Double x) {
                //return Math.exp(-x*x);
                if (x < -1.0 || x > 3.0) {
                    return 0.0;
                } else if (x == -1.0 || x == 3.0) {
                    return 0.5;
                } else return 1.0;
            }
        };
        fftSolver.fft(N, M, a);
        draw(resultAmplChart, fftSolver.getResultAmpl(), b, "FFT");
        draw(resultPhaseChart, fftSolver.getResultPhase(), b, "FFT");

        draw(resultAmplChart, riemannSolver1D.getResultAmplitude(), "Riemann Summ");
        draw(resultPhaseChart, riemannSolver1D.getResultPhase(), "Riemann Summ");

        draw(funcAmplChart, fftSolver.getFuncAmpl(), a, "Amplitude");
        draw(funcPhaseChart, new double[]{0.0, 0.0, 0.0, 0.0}, a, "Phase");

        draw(resultAmplChart, analytic.getAmpl(), "Analytic solution");
        draw(resultPhaseChart, analytic.getPhase(), "Analytic solution");

    }

    public void calculateAction(ActionEvent actionEvent) {
        /*Integer N = 1000;
        Double a = 5.0;
        Integer M = 8192;
        Double b = N * N / (8 * a * M);
        FFTSolver1D.fft(N, M, a);
        draw(resultAmplChart, FFTSolver1D.getResultAmpl(), b);
        draw(resultPhaseChart, FFTSolver1D.getResultPhase(), b);
*/
    }

    public void draw(LineChart lineChart, double[] u, Double b, String legend) {
        //lineChart.getData().clear();
        Double x = -b;
        Double h = 2 * b / (u.length - 1);
        for (int i = 0; i < u.length; i++) {
            x = -b + i * h;
            graphSeria.getData().add(new XYChart.Data(x, u[i]));
        }
        graphSeria.setName(legend);
        lineChart.getData().add(graphSeria);
        lineChart.setCreateSymbols(false);
        //lineChart.setLegendVisible(false);
        graphSeria = new XYChart.Series();
    }

    public void draw(LineChart lineChart, Dot[] dots, String legend) {
        for (int i = 0; i < dots.length; i++) {
            graphSeria.getData().add(new XYChart.Data(dots[i].getX(), dots[i].getY()));
        }
        graphSeria.setName(legend);
        lineChart.getData().add(graphSeria);
        lineChart.setCreateSymbols(false);
        graphSeria = new XYChart.Series();
    }

}
