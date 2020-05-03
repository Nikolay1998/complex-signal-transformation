package math;

import org.jtransforms.fft.DoubleFFT_1D;

public abstract class FFTSolver1D {
    double[] resultPhase;
    double[] resultAmpl;
    double[] funcAmpl;
    double[] funcPhase;



    public FFTSolver1D() {
    }

    public void fft(Integer N, Integer M, Double a) {
        Double hx = 2 * a / N;
        double[] functionValues = calcfunction(hx, N, a);

        functionValues = zeroAdding(M, functionValues, N);
        functionValues = swapParts(functionValues);
        DoubleFFT_1D fft_1D = new DoubleFFT_1D(M/2);
        fft_1D.complexForward(functionValues);

        for (int i = 0; i < M; i++) {
            functionValues[i] = functionValues[i] * hx * Math.sqrt(2);
        }
        functionValues = swapParts(functionValues);
        Complex[] result = transformToComplex(functionValues);
        result = getCentralValues(result, N/2);
        //functionValues = getCentralValues(functionValues, N);

        resultPhase = new double[N/2];
        resultAmpl = new double[N/2];
        for (int i = 0; i < N/2; i++) {
            //System.out.println(result[i]);
            resultAmpl[i] = result[i].abs();
            resultPhase[i] = result[i].phase();
        }
        //return functionValues;
    }

    private Complex[] transformToComplex(double[] functionValues) {
        Complex[] result = new Complex[functionValues.length/2];
        for(int i = 0; i < functionValues.length/2; i++){
            result[i] = new Complex(functionValues[2*i], functionValues[2*i+1]);
        }
        return result;
    }

    private Complex[] getCentralValues(Complex[] functionValues, Integer N) {
        Integer M = functionValues.length;
        Complex[] result = new Complex[N];
        int j = 0;
        for (int i = 0; i < (M - N) / 2; i++) {
            j++;
        }
        for (int i = 0; i < N; i++) {
            result[i] = functionValues[j];
            j++;
        }
        return result;
    }

    private double[] swapParts(double[] functionValues) {
        double[] result = new double[functionValues.length];
        for (int i = 0; i < functionValues.length / 2; i++) {
            result[i] = functionValues[functionValues.length / 2 + i];
        }
        for (int i = 0; i < functionValues.length / 2; i++) {
            result[i + functionValues.length / 2] = functionValues[i];
        }
        return result;
    }

    private double[] zeroAdding(Integer M, double[] functionValues, Integer N) {
        double[] result = new double[M];
        int j = 0;
        for (int i = 0; i < (M - N) / 2; i++) {
            result[j] = 0.0;
            j++;
        }
        for (int i = 0; i < N; i++) {
            result[j] = functionValues[i];
            j++;
        }
        for (int i = 0; i < (M - N) / 2; i++) {
            result[j] = 0.0;
            j++;
        }
        return result;
    }

    private double[] calcfunction(Double hx, Integer N, double a) {
        double[] result = new double[N];
        double x;
        for (int i = 0; i < N; i++) {
            x = -a + i * hx;
            result[i] = function(x);
        }
        funcAmpl = result;
        //funcPhase = ;
        return result;
    }
/*
    private Double rect(Double x) {
        if (x < 1.0 || x > 3.0) {
            return 0.0;
        } else if (x == 1.0 || x == 3.0) {
            return 0.5;
        } else return 1.0;
    }

    private Double gauss(Double x){
        return Math.exp(-x*x);
    }


 */
    public abstract double function(Double x);

    public double[] getResultPhase() {
        return resultPhase;
    }

    public double[] getResultAmpl() {
        return resultAmpl;
    }

    public double[] getFuncAmpl() {
        return funcAmpl;
    }

    public double[] getFuncPhase() {
        return funcPhase;
    }
}
