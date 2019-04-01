import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Uniforme extends BaseFrame implements ActionListener {

    JTextField txFunc;
    JTextField txA;
    JTextField txB;
    JTextField txD;
    JButton calc;

    Uniforme() {
        super("Busca Uniforme");

        upLeft.add(new JLabel("Função"));
        upLeft.add(new JLabel("Limite inferior"));
        upLeft.add(new JLabel("Limite Superior"));
        upLeft.add(new JLabel("Delta"));

        txFunc = new JTextField(10);
        txA = new JTextField();
        txB = new JTextField();
        txD = new JTextField();

        calc = new JButton("Calcular");

        upRight.add(txFunc);
        upRight.add(txA);
        upRight.add(txB);
        upRight.add(txD);
        middle.add(calc);

        calc.addActionListener(this);

        txFunc.setMaximumSize(txFunc.getPreferredSize());
        txA.setMaximumSize(txFunc.getPreferredSize());
        txB.setMaximumSize(txFunc.getPreferredSize());
        txD.setMaximumSize(txFunc.getPreferredSize());

        setSize(400, 500);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder out = new StringBuilder();
        double a,b,d = 0,xk;
        double x = 0;
        Expression f = new Expression(txFunc.getText());

        try{
            x = Double.parseDouble(txA.getText());
            b = Double.parseDouble(txB.getText());
            d = Double.parseDouble(txD.getText());
            f.eval();
        }catch(NumberFormatException err){
            outputArea.setText("Digite valores válidos (numéricos) nos campos apropriados!");
            return;
        }catch(ExpressionException err){
            outputArea.setText("Digite uma função válida!!");
            return;
        }
        xk = x + d;
        f.with("x", new BigDecimal(xk));
        out.append(f.eval());

    }


}

