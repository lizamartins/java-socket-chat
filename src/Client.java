import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client {
    static ObjectOutputStream output;

    static Chat chat = new Chat("Client");
    public static void main(String[] args){


        chat.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirma = JOptionPane.showOptionDialog(
                        chat.frame,
                        "Are you sure you want to exit?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);

                if (confirma == JOptionPane.YES_OPTION) {
                    chat.msg_text.setText("END");
                    chat.send_btn.doClick();
                    chat.frame.dispose();
                }
            }
        });

        chat.send_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeObject(chat.msg_text.getText());
                    output.flush();
                    chat.msg_area.append("\nClient>> "+chat.msg_text.getText());
                    chat.msg_text.setText("");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        try {
            Socket client=new Socket("127.0.0.1",8522);
            chat.msg_area.append("Client connected");
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            output = new ObjectOutputStream(client.getOutputStream());
            chat.msg_area.append("\nWhen ending the session, type 'end' in the terminal");

            String message;
            do {
                message = (String) input.readObject();
                chat.msg_area.append("\nServer>> " + message);
            } while (!message.equalsIgnoreCase("FIM"));
        } catch (Exception e) {
            // TODO: handle exception
            chat.msg_area.append("\nError client: "+e.getMessage());
        }
        chat.msg_area.append("\nClient disconnected");
    }

}
