import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Newton extends BaseFrame implements ActionListener {
	HashMap<String, JTextField> map;
    JButton calc;
    
    Newton(){
		super("Metodo de Newton");
		map = SetLabelsFields(new String[] { "Função", "Limite inferior", "Limite superior", "Incerteza", "Precisão" });
        calc = new JButton("Calcular");
        calc.addActionListener(this);
        middle.add(calc);
        setVisible(true);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		StringBuilder out = new StringBuilder();
		double a, b, eps, x, prox;
		double der_pri, der_seg, maior=1;
		int k, p=5;
		Expression f = new Expression(map.get("Função").getText());
        try {
            a = Double.parseDouble(map.get("Limite inferior").getText());
            b = Double.parseDouble(map.get("Limite superior").getText());
            eps = Double.parseDouble(map.get("Incerteza").getText());
            p = Integer.parseInt(map.get("Precisão").getText());
            f.with("x", new BigDecimal(a)).eval();
            if(b < a){
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
        k=1;
		x = a; //x0 = a;
		prox = x - (Derivada.Primeira(f,x,p)/Derivada.Segunda(f,x,p)); 
		if(prox>1){ //verificacao de max{x(k+1),1} para condicao de parada
			maior = prox;
		}
		out.append("K= "+k+"\n\tx="+x+"\n\tx+1="+prox+"\n\tder_pri="+Derivada.Primeira(f,x,p)+"\n\tder_seg="+Derivada.Segunda(f,x,p)+"\n"); //verificacao
		while(Math.abs(Derivada.Primeira(f,x,p))> eps && (Math.abs(prox-x)/ maior)> eps){ //condicoes de parada
			k++;
			x = prox;
			prox = x - (Derivada.Primeira(f,x,p)/Derivada.Segunda(f,x,p));
			out.append("K= "+k+"\n\tx="+x+"\n\tx+1="+prox+"\n\tder_pri="+Derivada.Primeira(f,x,p)+"\n\tder_seg="+Derivada.Segunda(f,x,p)+"\n"); //verificacao
			if(prox>1){ 
				maior = prox;
			}
		}
		out.append("\n\n\tCondicao de Parada 1= " + Math.abs(Derivada.Primeira(f,x,p)));
		out.append("\n\tCondicao de Parada 2= " + (Math.abs(prox-x)/ maior));		
		out.append("\n\tValor ótimo = " + prox);
		outputArea.setText(out.toString());
	}
}
