import javax.swing.*;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;

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
        up.setLayout(new FlowLayout(FlowLayout.CENTER));
        // up.add(upLeft);
        // up.add(Box.createRigidArea(new Dimension(20,0)));
        // up.add(upRight);

        add(up);
        add(middle);
        add(down);
        setSize(400, 500);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    HashMap<String, JTextField> SetLabelsFields(String[] labels) {
        HashMap<String, JTextField> map = new HashMap<String, JTextField>();
        for (String s : labels) {
            JPanel a = new JPanel();
            a.setLayout(new GridLayout(1,2,5,0));
            JTextField b = new JTextField(10);
            b.setMaximumSize(b.getPreferredSize());
            JLabel l = new JLabel(s);
            
            a.add(l);
            a.add(b);
            up.add(a);
            map.put(s, b);
        }
        return map;
    }
}