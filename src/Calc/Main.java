package Calc;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.function.Function;

public class Main {

    public static Socket socket = null;

    public static void main( String[] args ) {

        Queue<String> queue = new LinkedList<>();

        Function<Double[] , Double> plus        = MathCalc::plus;
        Function<Double[] , Double> minus       = MathCalc::minus;
        Function<Double[] , Double> multiplying = MathCalc::multiplying;
        Function<Double[] , Double> division    = MathCalc::divisionTrue;

        HashMap<String, Function<Double[] , Double>> mathOperators = new HashMap<>();
        mathOperators.put("+", plus);
        mathOperators.put("-", minus);
        mathOperators.put("*", multiplying);
        mathOperators.put("/", division);

        Thread jthread = new JThread(queue, mathOperators);

        while (true) {

            DataInputStream in = null;

            try (ServerSocket serverSocket = new ServerSocket(32123)) {
                System.out.println("Wating for incoming connection...");
                socket = serverSocket.accept();

                Out.setSocket(socket);

                System.out.println("New incoming connecion from " + socket.getInetAddress() + " port " + socket.getPort());

                in = new DataInputStream(socket.getInputStream());
                //добавить in.close
            } catch (IOException e) {
                e.printStackTrace();
            }

            jthread.start();

            prinaitDannuu(in,
                    queue,
                    socket);

            jthread.interrupt();
        }

    }

    private static void prinaitDannuu(DataInputStream in, Queue<String> queue, Socket socket ){
        while (true) {
            try {
                assert in != null;
                String value = in.readUTF();
                queue.add(value);
                //System.out.println(queue.size());
            } catch (EOFException e) {
                System.out.println("Socket is connect? = " + socket.isConnected());
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
