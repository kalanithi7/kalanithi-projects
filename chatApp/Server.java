import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server extends Frame implements Runnable,ActionListener{
    TextField textField;
    TextArea textArea;
    Button send;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;

    Server(){
        textField = new TextField();
        textArea = new TextArea();
        send = new Button("Send");

        send.addActionListener(this);

       try{
        serverSocket = new ServerSocket(12000);
        socket = serverSocket.accept();
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception E){

        }

        add(textField);
        add(textArea);
        add(send);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

      

        setSize(500,500);
        setTitle("Server");
        setLayout(new FlowLayout());
        setVisible(true);
       
    }
    @Override
    public void actionPerformed(ActionEvent e){
         String msg = textField.getText();
         textArea.append("Server:"+msg+"\n");
         textField.setText("");
       try {
        dataOutputStream.writeUTF(msg);
        dataOutputStream.flush();
       } 
       catch (Exception ex) {
      
       }
        
    }
    public static void main(String[] args) {
         new Server();
        
    }
    public void run(){
        while (true) {
            try{
                String msg = dataInputStream.readUTF();
                textArea.append("Client:"+msg+"\n");
            }
            catch(Exception e){

            }
            
        }
    }

}