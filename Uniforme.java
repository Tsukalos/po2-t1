import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Uniforme extends BaseFrame implements ActionListener {

    JButton calc;
    HashMap<String, JTextField> map;

    Uniforme() {
        super("Busca Uniforme");
        map = SetLabelsFields(new String[] { "Função", "Limite inferior", "Limite superior", "Delta", "Precisão" });
        calc = new JButton("Calcular");

        middle.add(calc);

        calc.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // a, b -> intervalo que a função vai atuar
        // d -> delta a ser somado
        // fx -> valor da função em x
        // fxk -> valor da função quando xk = x + d

        StringBuilder out = new StringBuilder();
        double a, b, d = 0, xk;
        double fx = 0;
        double fxk = 0;
        double x = 0;
        int precision = 5;
        int k;
        Expression f = new Expression(map.get("Função").getText());
        // Bloco try valida se os inputs estão corretos para o cálculo
        try {
            // valores das caixas de texto
            a = Double.parseDouble(map.get("Limite inferior").getText());
            b = Double.parseDouble(map.get("Limite superior").getText());
            d = Double.parseDouble(map.get("Delta").getText());
            precision = Integer.parseInt(map.get("Precisão").getText());

            // Testa com um valor do intervalo se a função e válida
            f.with("x", new BigDecimal(a));
            f.eval();

            if (b < a) {
                // Realiza um throw genérico com uma mensagem apropriada.
                throw new Exception("Digite um intervalo [a b] correto!");
            }
        } catch (NumberFormatException err) {
            outputArea.setText("Digite valores válidos (numéricos) nos campos apropriados!");
            return;
        } catch (ExpressionException err) {
            // Função não validadada pelo evalex
            outputArea.setText("Digite uma função válida!!");
            return;
        } catch (Exception err) {
            outputArea.setText(err.getMessage());
            return;
        }
        x = a;
        f.with("x", new BigDecimal(x));
        fx = f.eval().doubleValue();
        k = 0;
        double xr = 0; // Variável para o refinamento
        double fxr = 0; // Variável para o refinamento
        boolean fflag = true;
        // i = 0 -> normal
        // i = 1 -> refinamento
        for (int i = 0; i < 2; i++) {
            fflag = true;
            do {
                k += 1;
                xk = x + d;
                // Aqui pegamos o valor em double ao invés do BigDecimal padrão.
                // Isso facilita operações entre as variáveis, mas
                // não permite que tenhamos precisão de casas decimais. ->
                fxk = f.with("x", new BigDecimal(xk)).eval().doubleValue();
                // Por isso na saída convertemos para BigDecimal onde podemos escolher
                // a precisão e o método de arredondadmento.
                out.append("K = " + k + "\n\tx = " + BigDecimal.valueOf(x).setScale(precision, RoundingMode.HALF_UP)
                        + "\n\txk = " + BigDecimal.valueOf(xk).setScale(precision, RoundingMode.HALF_UP)
                        + "\n\tf(xk) = " + BigDecimal.valueOf(fxk).setScale(precision, RoundingMode.HALF_UP) + "\n");
                if (xk > b) {
                    out.append("(xk > b) Intervalo ultrapassado\n");
                } else if (fxk < fx) {
                    out.append("x <- xk\n- - -\n\n");
                    // Salvando a interação k para eventual refinamento
                    xr = x;
                    fxr = fx;
                    // Colocando os valores para a próxima interação (k+1)
                    x = xk;
                    fx = fxk;
                } else
                    fflag = false; // Não botei o teste no while porque o valor de fx já foi sobreescrito
            } while (xk < b && fflag);
            if (i == 0) {
                // Se for a primeira interação, fazer refinamento:
                d = d / 10;
                x = xr;
                fx = fxr;
            }
        }
        out.append("Valor ótimo = " + BigDecimal.valueOf(x).setScale(precision, RoundingMode.HALF_UP) + "\n");

        outputArea.setText(out.toString());
    }
}
