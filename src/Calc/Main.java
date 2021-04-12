package Calc;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.function.Function;

public class Main {

    public static void main( String[] args ) {

        Queue queue = new LinkedList();

        Function<Double[][] , Double> plus = MathCalcMoi::plus;

        HashMap<String, Function<Double[][] , Double>> mathOperators = new HashMap<>();
        mathOperators.put("+", plus);

        Stack stackMathOp = new Stack();
        Stack stackNumb = new Stack();

        Double out = 0.0;

        Thread jthread = new JThread("first", queue, mathOperators, stackMathOp, stackNumb, out);
        //jthread.start();

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

            jthread.start();

            while (true) {
                try {
                    String value = in.readUTF();
                    queue.add(value);
                    System.out.println(queue.size());
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
