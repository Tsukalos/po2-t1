import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Bissecao extends BaseFrame implements ActionListener {
	HashMap<String, JTextField> map;
    JButton calc;
    
    Bissecao(){
		super("Método da Bisseção");
		map = SetLabelsFields(new String[] {"Função","Limite inferior", "Limite superior", "Incerteza", "Precisão"});
		calc = new JButton("Calcular");
        calc.addActionListener(this);
        middle.add(calc);
        setVisible(true);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		StringBuilder out = new StringBuilder();
		double a, b, x=0, k, eps;
		int p=5, cont_k=0;
		//k -> numero maximo de iteracoes
		//x -> ponto medio entre o intervalo de atuacao
		
		Expression f = new Expression(map.get("Função").getText());
		try{
            a = Double.parseDouble(map.get("Limite inferior").getText());
            b = Double.parseDouble(map.get("Limite superior").getText());
            eps = Double.parseDouble(map.get("Incerteza").getText());
            p = Integer.parseInt(map.get("Precisão").getText());
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
        k = Math.ceil(Log.log(eps/(b-a),0.5)); //quantidade maxima de iteracoes - com erro fica retornando 0
		out.append("Quantidade maxima de iteracoes="+k+"\n");
        x = (a+b)/2;
        cont_k=1;
        out.append("K="+cont_k+"\n\ta="+a+"\n\tb="+b+"\n\tx="+x+"\n\tder_pri="+Derivada.Primeira(f,x,eps)+"\n");
        while(cont_k<= k && Derivada.Primeira(f,x,eps)!=0){
			if(Derivada.Primeira(f,x,p) >0){
				out.append("\n f'(x) > 0 então:\n");
                out.append("\n b <- x\n");
				b = x;
			}
			else{
				out.append("\n f'(x) < 0 então:\n");
                out.append("\n a <- x\n");
				a = x;
			}
			x = (a+b)/2;
			cont_k++;
			out.append("\nK="+cont_k+"\n\ta="+a+"\n\tb="+b+"\n\tx="+x+"\n\tder_pri="+Derivada.Primeira(f,x,eps)+"\n");
		}
		out.append("\tValor ótimo = " + x);
		outputArea.setText(out.toString());
	}
}
