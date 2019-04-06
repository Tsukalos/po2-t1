import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Aurea extends BaseFrame implements ActionListener {
    HashMap<String, JTextField> map;
    JButton calc;
    final double alpha = (Math.sqrt(5) - 1) / 2;

    Aurea() {
        super("Seção Áurea");
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
        Expression f = new Expression(map.get("Função").getText());
        try {
            a = Double.parseDouble(map.get("Limite inferior").getText());
            b = Double.parseDouble(map.get("Limite superior").getText());
            eps = Double.parseDouble(map.get("Incerteza").getText());
            p = Integer.parseInt(map.get("Precisão").getText());
            f.with("x", new BigDecimal(a)).eval();
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

        mi = a + (1 - alpha) * (b - a);
        lambda = a + alpha * (b - a);
        fmi = f.with("x", new BigDecimal(mi)).eval().doubleValue();
        flambda = f.with("x", new BigDecimal(lambda)).eval().doubleValue();
        int k = 1;
        while (Math.abs(b - a) > eps) {
            out.append("K = "+fout(k,p)+"\n\ta = "+fout(a,p)+"\n\tb = "+fout(b,p)+"\n");
            out.append("\tmi = "+fout(mi,p)+"\n\tlambda = "+fout(lambda,p)+"\n");
            out.append("\tf(mi) = "+fout(fmi,p)+"\n\tf(lambda) = "+fout(flambda,p)+"\n-\n");
            if (fmi > flambda) {
                out.append("\n f(mi) > f(lambda) então:\n");
                a = mi;
                mi = lambda;
                fmi = flambda;
                lambda = a + alpha * (b - a);
                flambda = f.with("x", new BigDecimal(lambda)).eval().doubleValue();
                out.append("\ta <- mi\n");
                out.append("\tmi <- lambda\n");
                out.append("\tlambda <- a + alpha*(b-a)\n");
            } else {
                out.append("\n f(mi) < f(lambda) então:\n");
                b = lambda;
                lambda = mi;
                flambda = fmi;
                mi = a + (1 - alpha) * (b - a);
                fmi = f.with("x", new BigDecimal(mi)).eval().doubleValue();
                out.append("\tb <- lambda\n");
                out.append("\tlambda <- mi\n");
                out.append("\tmi <- a + (1- alpha)*(b-a)\n");
            }
            k += 1;
            out.append("- - -\n");
        }
        double x = (a + b) / 2;
        out.append("Interações: "+k+"\nx* <- (a + b)/2\n");
        out.append("\tValor ótimo = " + fout(x,p));

        outputArea.setText(out.toString());

    }

    BigDecimal fout(double v, int precision){
       return BigDecimal.valueOf(v).setScale(precision, RoundingMode.HALF_UP);
    }
}