package stream.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.PrintStream;

public class Window  extends JFrame {
    private JButton sendButton;
    private JTextArea textArea;
    private JTextField textInput;
    private JScrollPane scrollPane;
    public Window(PrintStream socOut, String username) throws HeadlessException {
        super(username);

        Container pane = this.getContentPane();
        this.setSize(500,500);
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            socOut.println(username + ": " + textInput.getText());
            textInput.setText("");
        });

        textInput = new JTextField(30);
        textInput.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socOut.println(username + ": " + textInput.getText());
                textInput.setText("");
            }
        });

        textArea = new JTextArea("");
        textArea.append("Server text: ");
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(50,400));
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));


        JPanel panel = new JPanel();


        panel.add(textInput,BorderLayout.WEST);
        panel.add(sendButton, BorderLayout.EAST);


        pane.add(scrollPane,BorderLayout.NORTH);
        pane.add(panel,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void writeText(String txt){
        textArea.append("\n" + txt);
    }

}
