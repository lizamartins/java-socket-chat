import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server {
    static ObjectOutputStream output;

    static Chat chat = new Chat("Server");
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        chat.send_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.writeObject(chat.msg_text.getText());
                    output.flush();
                    chat.msg_area.append("\nServer>>"+chat.msg_text.getText());
                    chat.msg_text.setText("");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

        try {
            ServerSocket server = new ServerSocket(8522);

            chat.msg_area.append("Started");
            while (true) {
                Socket client = server.accept();
                chat.msg_area.append("\nConnected " + client.getInetAddress());
                output = new ObjectOutputStream(client.getOutputStream());

                output.writeObject("Welcome! How can I help you?");

                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                String message = "";
                do {
                    message = (String) input.readObject();
                    chat.msg_area.append("\nClient>> " + message);
                } while (!message.equalsIgnoreCase("END"));
                input.close();
                client.close();
                output.close();
                chat.msg_area.append("\nConnection closed");
            }

        } catch (Exception e) {
            // TODO: handle exception
            chat.msg_area.append("\nError: " + e.getMessage());

        }
        chat.msg_area.append("\nServer has closed the connection");
    }
}
