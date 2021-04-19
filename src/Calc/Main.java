package Calc;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.*;

public class Main {
    public static void main( String[] args ) throws IOException {
        while (true) {
            Queue<String> queue = new LinkedList<>();

            Thread jthread = new JThread(queue);

            try (ServerSocket serverSocket = new ServerSocket(32123)) {
                System.out.println("Wating for incoming connection...");
                Socket socket = serverSocket.accept();

                OutputStream.setSocket(socket);

                System.out.println("New incoming connecion from " + socket.getInetAddress() + " port " + socket.getPort());

                DataInputStream in = new DataInputStream(socket.getInputStream());

                jthread.start();
                inputStream(in, queue, socket, jthread);

                in.close();
                jthread.interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void inputStream(DataInputStream in, Queue<String> queue, Socket socket, Thread jthread){

        while (true) {
            try {
                assert in != null;
                String value = in.readUTF();
                if (value.equals("Reset")){
                    jthread.interrupt();
                    queue = new LinkedList<>();
                    jthread = new JThread(queue);
                    jthread.start();
                }
                else{
                    queue.add(value);
                }
            } catch (EOFException e) {
                System.out.println("Socket is connect? = " + socket.isConnected());
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
