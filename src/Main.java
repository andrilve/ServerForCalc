import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
//    static class Message implements Serializable {
//        private int size;
//        private byte[] data;
//
//        public Message( int sz, byte[] _data){
//            size = sz;
//            data = _data;
//        }
//
//        @Override
//        public String toString(){
//            return "Message(size = " + size + ") ";
//        }
//    }

    public static void main( String[] args ) {

        while (true) {
            Socket socket = null;
            DataInputStream in = null;

            try (ServerSocket serverSocket = new ServerSocket(32123)) {
                System.out.println("Wating for incoming connection...");
                socket = serverSocket.accept();

                System.out.println("New incoming connecion from " + socket.getInetAddress() + " port " + socket.getPort());
                in = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    String value = in.readUTF();
                    System.out.println("это что надо " + value);
                    //byte[] data = in.readNBytes(size);
                    //Message msg = new Message();
                    // System.out.println("Data resieved " + msg.toString());
                } catch (EOFException e) {
                    System.out.println("Socket is connect? = " + socket.isConnected());
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
