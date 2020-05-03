package math;

public class AnalyticSolution {
    private Complex[] solution;
    private Dot[] ampl;
    private Dot[] phase;

    public AnalyticSolution(double a, double b, Integer n) {
        solution = new Complex[n + 1];
        ampl = new Dot[n + 1];
        phase = new Dot[n + 1];
        double ksi;
        double h = (b - a) / n;
        for (int i = 0; i < n + 1; i++) {
            ksi = a + h * i;
            if (ksi == 0) {
                solution[i] = solution[i-1];
            } else {
                solution[i] = getValue(ksi);
            }
            ampl[i] = new Dot(ksi, solution[i].abs());
            phase[i] = new Dot(ksi, solution[i].phase());
            //System.out.println(solution[i].abs());
        }
    }

    private Complex getValue(double ksi) {
        Double den = Math.PI * 2 * ksi;
        return new Complex(
                //(Math.PI * ksi * Math.cos(2 * Math.PI * ksi) + Math.sin(2 * Math.PI * ksi) + Math.PI * ksi * Math.cos(6 * Math.PI * ksi) + Math.sin(6 * Math.PI * ksi)) / den,
                //-(Math.cos(2 * Math.PI * ksi) - Math.PI * ksi * Math.sin(2 * Math.PI * ksi) - Math.cos(6 * Math.PI * ksi) + Math.PI * ksi * Math.sin(6 * Math.PI * ksi)) / den
                (Math.sin(2 * Math.PI * ksi) + Math.sin(6 * Math.PI * ksi))/den,
                -(Math.cos(2 * Math.PI * ksi) - Math.cos(6 * Math.PI * ksi))/den
        );
    }

    public Dot[] getAmpl() {
        return ampl;
    }

    public Dot[] getPhase() {
        return phase;
    }
}
