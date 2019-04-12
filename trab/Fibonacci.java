import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;
import java.math.*;
import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Fibonacci extends BaseFrame implements ActionListener {
    JButton calc;
    HashMap<String, JTextField> map;

    Fibonacci() {
        super("Busca de Fibonacci");
        map = SetLabelsFields(new String[] { "Função", "Limite inferior", "Limite superior", "Incerteza", "Precisão" });
        calc = new JButton("Calcular");
        calc.addActionListener(this);
        middle.add(calc);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder out = new StringBuilder();
        double a, b, eps;
        a = b = eps = 0;
        double mi, lambda;
        double fmi, flambda;
        int p = 5;
        Vector<Integer> fib = new Vector<Integer>();
        Expression f = new Expression(map.get("Função").getText());
        try {
            a = Double.parseDouble(map.get("Limite inferior").getText());
            b = Double.parseDouble(map.get("Limite superior").getText());
            eps = Double.parseDouble(map.get("Incerteza").getText());
            p = Integer.parseInt(map.get("Precisão").getText());
            f.with("x", new BigDecimal(a)).eval();
            if (b < a) {
                throw new Exception("Digite um intervalo [a b] correto!");
            }
        } catch (NumberFormatException err) {
            outputArea.setText("Digite valores válidos (numéricos) nos campos apropriados!");
            return;
        } catch (ExpressionException err) {
            outputArea.setText("Digite uma função válida!!");
            return;
        } catch (Exception err) {
            outputArea.setText(err.getMessage());
            return;
        }
        // Gerando sequencia de fibonacci
        double fiblim = (b - a) / eps;
        fib.add(1);
        fib.add(1);
        int n = 1;

        int current;
        do {
            n += 1;
            current = fib.get(n - 2) + fib.get(n - 1);
            fib.add(n, current);
        } while (current <= fiblim);

        int kmax = n - 1;
        int k = 0;
        System.out.println(fib.size());
        System.out.println(n);

        mi = a + (fib.get(n - k - 2) * (b - a) / fib.get(n - k));
        lambda = a + (fib.get(n - k - 1) * (b - a) / fib.get(n - k));
        fmi = f.with("x", new BigDecimal(mi)).eval().doubleValue();
        flambda = f.with("x", new BigDecimal(lambda)).eval().doubleValue();

        while (Math.abs(b - a) > eps && k < kmax) {
            out.append("K = " + k + "\n\ta = " + fout(a, p) + "\n\tb = " + fout(b, p) + "\n");
            out.append("\tmi = " + fout(mi, p) + "\n\tlambda = " + fout(lambda, p) + "\n");
            out.append("\tf(mi) = " + fout(fmi, p) + "\n\tf(lambda) = " + fout(flambda, p) + "\n-\n");
            if (fmi > flambda) {
                out.append("\n f(mi) > f(lambda) então:\n");
                a = mi;
                mi = lambda;
                fmi = flambda;
                lambda = a + (fib.get(n - k - 1) * (b - a) / fib.get(n - k));
                flambda = f.with("x", new BigDecimal(lambda)).eval().doubleValue();
                out.append("\ta <- mi\n");
                out.append("\tmi <- lambda\n");
                out.append("\tlambda <- a + (fibseq[n-k-1]*(b-a)/fibseq[n-k])\n");
            } else {
                out.append("\n f(mi) < f(lambda) então:\n");
                b = lambda;
                lambda = mi;
                flambda = fmi;
                mi = a + (fib.get(n - k - 2) * (b - a) / fib.get(n - k));
                fmi = f.with("x", new BigDecimal(mi)).eval().doubleValue();
                out.append("\tb <- lambda\n");
                out.append("\tlambda <- mi\n");
                out.append("\tmi <- a + (fibseq[n-k-2]*(b-a)/fibseq[n-k]\n");
            }
            k += 1;
            out.append("- - -\n");
        }
        double x = (a + b) / 2;
        out.append("Interações: " + k + "\nx* <- (a + b)/2\n");
        out.append("\tValor ótimo = " + fout(x, p));

        outputArea.setText(out.toString());
    }

}