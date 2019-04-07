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
        outputArea.setText("Demais infos");
	}
	
}
