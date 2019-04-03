import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Uniforme extends BaseFrame implements ActionListener {

    JTextField txFunc;
    JTextField txA;
    JTextField txB;
    JTextField txD;
    JTextField txP;
    JButton calc;

    Uniforme() {
        super("Busca Uniforme");

        upLeft.add(new JLabel("Função"));
        upLeft.add(new JLabel("Limite inferior"));
        upLeft.add(new JLabel("Limite Superior"));
        upLeft.add(new JLabel("Delta"));
        upLeft.add(new JLabel("Precisão (int)"));

        txFunc = new JTextField(10);
        txA = new JTextField();
        txB = new JTextField();
        txD = new JTextField();
        txP = new JTextField();

        calc = new JButton("Calcular");

        upRight.add(txFunc);
        upRight.add(txA);
        upRight.add(txB);
        upRight.add(txD);
        upRight.add(txP);
        middle.add(calc);

        calc.addActionListener(this);

        txFunc.setMaximumSize(txFunc.getPreferredSize());
        txA.setMaximumSize(txFunc.getPreferredSize());
        txB.setMaximumSize(txFunc.getPreferredSize());
        txD.setMaximumSize(txFunc.getPreferredSize());
        txP.setMaximumSize(txFunc.getPreferredSize());

        setSize(400, 500);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // a, b -> intervalo que a função vai atuar
        // d -> delta a ser somado
        // fx -> valor da função em x
        // fxk -> valor da função quando xk = x + d
        
        StringBuilder out = new StringBuilder();
        double a,b,d = 0,xk;
        double fx = 0;
        double fxk = 0;
        double x = 0;
        int precision = 5;
        int k;
        Expression f = new Expression(txFunc.getText());
        // Bloco try valida se os inputs estão corretos para o cálculo
        try{
            // valores das caixas de texto
            a = Double.parseDouble(txA.getText());
            b = Double.parseDouble(txB.getText());
            d = Double.parseDouble(txD.getText());
            precision = Integer.parseInt(txP.getText());

            // Testa com um valor do intervalo se a função e válida
            f.with("x", new BigDecimal(a));
            f.eval();

            if(b < a){
                throw new Exception();
            }
        }catch(NumberFormatException err){
            outputArea.setText("Digite valores válidos (numéricos) nos campos apropriados!");
            return;
        }catch(ExpressionException err){
            // Função não validadada pelo evalex
            outputArea.setText("Digite uma função válida!!");
            return;
        }catch(Exception err){
            outputArea.setText("Digite um intervalo [a b] correto!");
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
        for(int i = 0; i < 2; i++){
            fflag = true;
            do{
                k += 1;
                xk = x + d;
                //  Aqui pegamos o valor em double ao invés do BigDecimal padrão.
                //  Isso facilita operações entre as variáveis, mas
                // não permite que tenhamos precisão de casas decimais. ->
                fxk = f.with("x", new BigDecimal(xk)).eval().doubleValue();
                //  Por isso na saída convertemos para BigDecimal onde podemos escolher
                // a precisão e o método de arredondadmento.
                out.append("K = "+k+"\n\tx = "+BigDecimal.valueOf(x).setScale(precision, RoundingMode.HALF_UP)+"\n\txk = "+BigDecimal.valueOf(xk).setScale(precision, RoundingMode.HALF_UP)+"\n\tf(xk) = "+BigDecimal.valueOf(fxk).setScale(precision, RoundingMode.HALF_UP)+"\n");
                if(xk > b){
                    out.append("(xk > b) Intervalo ultrapassado\n");
                }
                else if(fxk < fx) {
                    out.append("x <- xk\n- - -\n\n");
                    // Salvando a interação k para eventual refinamento
                    xr = x; 
                    fxr = fx;
                    // Colocando os valores para a próxima interação (k+1)
                    x = xk;
                    fx = fxk;
                }else fflag = false; // Não botei o teste no while porque o valor de fx já foi sobreescrito
            }while(xk < b && fflag);
            if(i == 0){
                // Se for a primeira interação, fazer refinamento:
                d = d/10;
                x = xr;
                fx = fxr;
            }
        }
        out.append("Valor ótimo = "+BigDecimal.valueOf(x).setScale(precision, RoundingMode.HALF_UP)+"\n");
        
        outputArea.setText(out.toString());
    }
}

