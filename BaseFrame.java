import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;


class BaseFrame extends JFrame {
    public JPanel up;
    public JPanel upLeft;
    public JPanel middle;
    public JPanel upRight;
    public JPanel down;
    public JTextArea outputArea;
    BaseFrame(String title) {
        super(title);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        up = new JPanel();
        upLeft = new JPanel();
        upRight = new JPanel();
        down = new JPanel();
        middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setText("Output:\n");
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        down.setLayout(new BoxLayout(down, BoxLayout.Y_AXIS));
        down.add(scroll);
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        upLeft.setLayout(new BoxLayout(upLeft, BoxLayout.Y_AXIS));
        upRight.setLayout(new BoxLayout(upRight, BoxLayout.Y_AXIS));
        up.add(upLeft);
        up.add(Box.createRigidArea(new Dimension(20,0)));
        up.add(upRight);

        add(up);
        add(middle);
        add(down);
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}