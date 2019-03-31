import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.udojava.evalex.*;

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
        out.append("AAAAAAA");


        outputArea.setText(out.toString());
    }


}

