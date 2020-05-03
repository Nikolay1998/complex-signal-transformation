package math;

public abstract class InputSignal {
    public Complex[][] getInput(Double a, Integer n) {
        Complex[][] result = new Complex[n][n];
        double x;
        double y;
        double h = 2 * a / (n - 1);
        for (int i = 0; i < n; i++) {
            x = -a + i * h;
            for (int j = 0; j < n; j++) {
                y = -a + j * h;
                result[i][j] = inputSignal(x, y);
            }
        }
        return result;
    }

    protected abstract Complex inputSignal(double x, double y);
}

