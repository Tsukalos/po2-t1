import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.*;

import com.udojava.evalex.*;
import com.udojava.evalex.Expression.ExpressionException;

public class Info extends BaseFrame {
    JButton fechar;
    
    Info(){
		super("Informações");
        setVisible(true);
        outputArea.setText("\tT1 - PESQUISA OPERACIONAL II\n\tPROF. MARCIA A. ZANOLI MEIRA E SILVA\n\tDesenvolvido pelos aluno:\n\tPedro Lamkowski \t RA: \n\tBruna Lika Tamake \t RA: 171024427\n");
	}
	
}
