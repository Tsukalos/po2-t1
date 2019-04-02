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
        StringBuilder out = new StringBuilder();
        double a,b,d = 0,xk;
        double fx = 0;
        double fxk = 0;
        double x = 0;
        int precision = 5;
        int k;
        Expression f = new Expression(txFunc.getText());
        try{
            a = Double.parseDouble(txA.getText());
            b = Double.parseDouble(txB.getText());
            d = Double.parseDouble(txD.getText());
            precision = Integer.parseInt(txP.getText());
            f.with("x", new BigDecimal(a));
            f.eval();
        }catch(NumberFormatException err){
            outputArea.setText("Digite valores válidos (numéricos) nos campos apropriados!");
            return;
        }catch(ExpressionException err){
            outputArea.setText("Digite uma função válida!!");
            return;
        }
        x = a;
        f.with("x", new BigDecimal(x));
        fx = f.eval().doubleValue();
        k = 0;
        double xr = 0;
        double fxr = 0;
        boolean fflag = true;
        // i = 0 -> normal
        // i = 1 -> refinamento
        for(int i = 0; i < 2; i++){
            fflag = true;
            do{
                k += 1;
                xk = x + d;
                fxk = f.with("x", new BigDecimal(xk)).setPrecision(7).eval().doubleValue();
                out.append("K = "+k+"\n\tx = "+BigDecimal.valueOf(x).setScale(precision, RoundingMode.HALF_UP)+"\n\txk = "+BigDecimal.valueOf(xk).setScale(precision, RoundingMode.HALF_UP)+"\n\tf(xk) = "+BigDecimal.valueOf(fxk).setScale(precision, RoundingMode.HALF_UP)+"\n");
                if(xk > b){
                    out.append("(xk > b) Intervalo ultrapassado\n");
                }
                else if(fxk < fx) {
                    out.append("x <- xk\n- - -\n\n");
                    xr = x;
                    fxr = fx;
                    x = xk;
                    fx = fxk;
                }else fflag = false;
            }while(xk < b && fflag);
            if(i == 0){
                d = d/10;
                x = xr;
                fx = fxr;
            }
        }
        out.append("Valor ótimo = "+BigDecimal.valueOf(x).setScale(precision, RoundingMode.HALF_UP)+"\n");
        
        outputArea.setText(out.toString());
    }
}

