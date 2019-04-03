import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Dicotomica extends BaseFrame implements ActionListener{
    JTextField txFunc;
    JTextField txA;
    JTextField txB;
    JTextField txD;
    JTextField txP;
    JTextField txE;
    JButton calc;

    Dicotomica() {
        super("Busca Dicotomica");

        upLeft.add(new JLabel("Função"));
        upLeft.add(new JLabel("Limite inferior"));
        upLeft.add(new JLabel("Limite Superior"));
        upLeft.add(new JLabel("Delta"));
        upLeft.add(new JLabel("Precisão (int)"));
        upLeft.add(new JLabel("Limite Conversão"));

        txFunc = new JTextField(10);
        txA = new JTextField();
        txB = new JTextField();
        txD = new JTextField();
        txP = new JTextField();
        txE = new JTextField();

        calc = new JButton("Calcular");

        upRight.add(txFunc);
        upRight.add(txA);
        upRight.add(txB);
        upRight.add(txD);
        upRight.add(txE);
        upRight.add(txP);
        middle.add(calc);

        calc.addActionListener(this);

        txFunc.setMaximumSize(txFunc.getPreferredSize());
        txA.setMaximumSize(txFunc.getPreferredSize());
        txB.setMaximumSize(txFunc.getPreferredSize());
        txD.setMaximumSize(txFunc.getPreferredSize());
        txP.setMaximumSize(txFunc.getPreferredSize());
        txE.setMaximumSize(txFunc.getPreferredSize());

        setSize(400, 500);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        // a, b -> intervalo que a função vai atuar
        // d -> delta a ser somado/subtraido
        // xk -> ponto medio do intervalo
        // fx -> valor da função em x (x-delta)
        // fz -> valor da funcao em z (x+delta)
        // eps -> limite de conversao

        StringBuilder out = new StringBuilder();
        double a,b,xk,z,w,eps;
        double d=0, fx=0, fz=0;
        int precision;
        Expression f = new Expression(txFunc.getText());

        try{
            a = Double.parseDouble(txA.getText());
            b = Double.parseDouble(txB.getText());
            d = Double.parseDouble(txD.getText());
            eps = Double.parseDouble(txE.getText());
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
        while((b-a)>=eps){
	    xk=(b-a)/2;
	    w = xk-d;
	    z = xk+d;
	    
	    
            outputArea.setText("Entrei");
        }
		
    }
}
