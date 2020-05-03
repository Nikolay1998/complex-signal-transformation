package math;

public abstract class RiemannSolver1D {
    private Double a;
    private Double b;
    private Double p;
    private Double q;
    private Double alpha = 1.0;
    private Integer m = 1000;
    public Integer n = 1000;
    private Dot[] resultAmplitude;
    private Dot[] resultPhase;

    public RiemannSolver1D() {

    }

    public RiemannSolver1D(Double a, Double b, Double p, Double q) {
        this.a = a;
        this.b = b;
        this.p = p;
        this.q = q;
    }

    public void solve() {
        Double ksi = p;
        Double hx = (b - a) / n;
        Double ksih = (q - p) / m;
        resultAmplitude = new Dot[m];
        resultPhase = new Dot[m];
        for (int j = 0; j < m; j++) {
            Complex localResult = new Complex(0.0, 0.0);
            Double x = a;
            for (int i = 0; i < n; i++) {
                localResult = localResult.add(function(x).mult(new Complex(hx, 0.0)).mult(furie(ksi * x)));
                x += hx;
            }
            resultAmplitude[j] = new Dot(ksi, localResult.abs());
            resultPhase[j] = new Dot(ksi, localResult.phase());
            ksi += ksih;
        }
    }

    public Dot[] getResultAmplitude() {
        return resultAmplitude;
    }

    public Dot[] getResultPhase() {
        return resultPhase;
    }

    private Double bessel(double arg) {
        Double result = 0.0;
        int b = 100;
        Double step = Math.PI / b;
        Double t = 0.0;
        for (int i = 0; i < b; i++) {
            result += (Math.cos(2 * t - arg * Math.sin(t))) * step;
            t += step;
        }
        return result / Math.PI;
    }

    private Complex furie(double arg) {
        return new Complex(Math.cos(Math.PI*2*arg), -Math.sin(Math.PI*2*arg));
    }
/*
    private Complex signal(Double x) {
        return new Complex(Math.exp(-x * x), 0.0);
        //return new Complex(Math.acos(x/10), Math.sin(x/10));
    }

 */
    public abstract Complex function(Double x);
}
