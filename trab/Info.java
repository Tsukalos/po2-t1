import javax.swing.*;

public class Info extends BaseFrame {
  JButton fechar;

  Info() {
    super("Informações");
    setVisible(true);
    outputArea.setText(
        "T1 - PESQUISA OPERACIONAL II\nPROF. MARCIA A. ZANOLI MEIRA E SILVA\nDesenvolvido pelos alunos:\nBruna Lika Tamake\tRA:171024427\nPedro Lamkowski dos Santos\tRA:171021266\n\nEsse trabalho usa o avaliador de funções EvalEx:\nhttps://github.com/uklimaschewski/EvalEx\nLicença de uso disponível em trab/com/udojava/evalex/LICENSE\n");
  }

}
