import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu extends JFrame {
    public static void main(String[] args) {
        new Menu();
    }

    Menu() {
        super("POII - T1");

        setLayout(new GridLayout(7, 1, 5, 5));

        setButtons();

        pack();

        setBounds(0, 0, 400, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void setButtons() {
        JButton bBuscaUniforme = new JButton("Busca Uniforme");
        JButton bBuscaDicotomica = new JButton("Busca Dicotômica");
        JButton bSecaoAurea = new JButton("Método da Seção Aurea");
        JButton bBuscaFibonacci = new JButton("Busca de Fibonacci");
        JButton bBissecao = new JButton("Método da Bisseção");
        JButton bNewton = new JButton("Método de Newton");
        JButton bInfo = new JButton("Informações do Projeto");
        bBuscaUniforme.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new Uniforme();
            }
        });
        bBuscaDicotomica.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new Dicotomica();
            }
        });
        bSecaoAurea.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Aurea();
            }
        });
        bBuscaFibonacci.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("a");
            }
        });
        bBissecao.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Bissecao();
            }
        });
        bNewton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Newton();
            }
        });
        bInfo.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.print("a");
            }
        });
        add(bBuscaUniforme);
        add(bBuscaDicotomica);
        add(bSecaoAurea);
        add(bBuscaFibonacci);
        add(bBissecao);
        add(bNewton);
        add(bInfo);
    }
}
