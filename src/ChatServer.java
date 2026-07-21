// ChatServer.java (Βελτιωμένος με δυνατότητα αποστολής μηνυμάτων και βελτιωμένα γραφικά)
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static JTextArea textArea;
    private static JTextField textField;
    private static PrintWriter serverOut;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Server");
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
                String message = "Server: " + textField.getText();
                textArea.append(message + "\n");
                synchronized (clientWriters) {
                    for (PrintWriter writer : clientWriters) {
                        writer.println(message);
                    }
                }
                textField.setText("");
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            textArea.append("Server is running...\n");
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            textArea.append("Server error: " + e.getMessage() + "\n");
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }
                
                String message;
                while ((message = in.readLine()) != null) {
                    textArea.append(message + "\n");
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(message);
                        }
                    }
                }
            } catch (IOException e) {
                textArea.append("Client error: " + e.getMessage() + "\n");
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
    }
}