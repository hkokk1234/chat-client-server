import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    
    private JFrame frame;
    private JTextField textField;
    private JTextArea textArea;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient() {
        frame = new JFrame("Chat Client");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(textField, BorderLayout.SOUTH);
        
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (out != null) {
                    String message = "Me: " + textField.getText();
                    out.println(textField.getText());
                    textArea.append(message + "\n");
                    textField.setText("");
                }
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void connect() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String message;
                        while ((message = in.readLine()) != null) {
                            textArea.append(message + "\n");
                        }
                    } catch (IOException e) {
                        textArea.append("Connection lost\n");
                    }
                }
            }).start();
            
        } catch (IOException e) {
            textArea.append("Connection error: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.connect();
    }
}
