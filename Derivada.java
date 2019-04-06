import java.math.BigDecimal;

import com.udojava.evalex.*;

public class Derivada {
    public static double Primeira(Expression f, double x, double precision) {
        boolean flag = false;
        int k = 0;
        double fx = 0, fant = 0;
        double err = 0, errant = 0;
        double h = 2;
        while (!flag) {
            k++;
            h = h / 2;
            fx = (f.with("x", new BigDecimal(x + h)).eval().doubleValue()
                    - f.with("x", new BigDecimal(x - h)).eval().doubleValue()) / (2 * h);
            if (k > 1) {
                err = Math.abs(fx - fant) / maxErr(fx);
                if (err < precision)
                    flag = true;
            }
            if (k > 2) {
                if (err > errant) {
                    return fant;
                }
            }
            errant = err;
            fant = fx;
        }
        return fx;
    }

    public static double Segunda(Expression f, double x, double precision) {
        boolean flag = false;
        int k = 0;
        double fx = 0, fant = 0;
        double err = 0, errant = 0;
        double h = 2;
        double minus2fx = -2 * f.with("x", new BigDecimal(x)).eval().doubleValue();
        while (!flag) {
            k++;
            h = h / 2;
            fx = (f.with("x", new BigDecimal(x + (2 * h))).eval().doubleValue() + minus2fx
                    + f.with("x", new BigDecimal(x - (2 * h))).eval().doubleValue()) / (4 * h * h);
            if (k > 1) {
                err = Math.abs(fx - fant) / maxErr(fx);

                if (err < precision)
                    flag = true;
            }
            if (k > 2) {
                if (err > errant) {
                    return fant;
                }
            }
            errant = err;
            fant = fx;
        }
        return fx;
    }

    private static double maxErr(double a) {
        return (Math.abs(a) >= 1) ? Math.abs(a) : 1;
    }

    public static void main(String[] args) {
        System.out.println(Derivada.Segunda(new Expression("4*(x^3) + 2*(x^2)"), 2, 0.1));
    }
}