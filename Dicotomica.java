import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Dicotomica extends BaseFrame implements ActionListener {
    HashMap<String, JTextField> map;
    JButton calc;

    Dicotomica() {
        super("Busca Dicotomica");

        map = SetLabelsFields(
                new String[] { "Função", "Limite inferior", "Limite superior", "Delta", "Incerteza", "Precisão" });

        calc = new JButton("Calcular");

        middle.add(calc);

        calc.addActionListener(this);

        setSize(400, 500);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // a, b -> intervalo que a função vai atuar
        // d -> delta a ser somado/subtraido
        // xk -> ponto medio do intervalo
        // fx -> valor da função em x (x-delta)
        // fz -> valor da funcao em z (x+delta)
        // eps -> limite de conversao

        StringBuilder out = new StringBuilder();
        double a, b, xk, z, w, eps;
        double d = 0, fw = 0, fz = 0;
        int p;

        Expression f = new Expression(map.get("Função").getText());
        // Bloco try valida se os inputs estão corretos para o cálculo
        try {
            a = Double.parseDouble(map.get("Limite inferior").getText());
            b = Double.parseDouble(map.get("Limite superior").getText());
            d = Double.parseDouble(map.get("Delta").getText());
            eps = Double.parseDouble(map.get("Incerteza").getText());
            p = Integer.parseInt(map.get("Precisão").getText());

            // Testa com um valor do intervalo se a função e válida
            f.with("x", new BigDecimal(a));
            f.eval();
            if (b < a) {
                throw new Exception();
            }
        } catch (NumberFormatException err) {
            outputArea.setText("Digite valores válidos (numéricos) nos campos apropriados!");
            return;
        } catch (ExpressionException err) {
            // Função não validadada pelo evalex
            outputArea.setText("Digite uma função válida!!");
            return;
        } catch (Exception err) {
            outputArea.setText("Digite um intervalo [a b] correto!");
            return;
        }
        int k = 1;
        while (Math.abs(b - a) >= eps) { // enquanto intervalo for maior que o limite de conversao
            xk = (a + b) / 2; // define ponto medio para a iteracao
            w = xk - d; // subtrai delta do ponto medio
            z = xk + d; // soma delta do ponto medio
            fw = f.with("x", new BigDecimal(w)).eval().doubleValue(); // guarda valor de f(w)
            fz = f.with("x", new BigDecimal(z)).eval().doubleValue(); // guarda valor de f(z)
            out.append("K = " + fout(k, p) + "\n\ta = " + fout(a, p) + "\n\tb = " + fout(b, p) + "\n");
            out.append("\tw = " + fout(w, p) + "\n\tz = " + fout(z, p) + "\n");
            out.append("\tf(w) = " + fout(fw, p) + "\n\tf(z) = " + fout(fz, p) + "\n-\n");
            if (fw >= fz) { // f(w) maior ou igual que f(z)
                out.append("\n f(w) > f(z) então:\n");
                out.append("\n a <- w\n");
                a = w; // ajuste do limite de atuacao - diminui por baixo
            } else { // f(z) maior que f(w)
                out.append("\n f(w) < f(z) então:\n");
                out.append("\n b <- z\n");
                b = z; // ajuste do limite de atuacao - diminui por cima
            }
            k++;
        }
        out.append("Interações = " + k + "\nValor otimo = " + fout((a + b) / 2, p) + "\n");
        outputArea.setText(out.toString());
    }
}
