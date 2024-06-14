import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Chat {
    JFrame frame;
    JTextArea msg_area;
    JTextField msg_text;
    JButton send_btn;

    private class KeyboardAdapter extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e){
            int codigo_key=e.getKeyCode();
            if(codigo_key==KeyEvent.VK_ENTER){
                send_btn.doClick();
            }
        }
    }

    public Chat(String name){
        frame = new JFrame("Chat - "+name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        msg_area = new JTextArea();
        msg_area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(msg_area);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        msg_text = new JTextField();
        inputPanel.add(msg_text, BorderLayout.CENTER);

        send_btn = new JButton("Send");
        inputPanel.add(send_btn, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        msg_text.addKeyListener(new KeyboardAdapter());
    }
}
