package Calc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OutputStream {
    private static Socket socket;

    public static void setSocket( Socket socket ) {
        OutputStream.socket = socket;
    }

    public void otpravitDannuu( Double value){
        try {
            if(!socket.isOutputShutdown()){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Я отправляю данные");
                out.writeUTF(String.valueOf(value));
                out.flush();
                //out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
