package Calc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Out {
    private static Socket socket;

    public static void setSocket( Socket socket ) {
        Out.socket = socket;
    }

    public static void otpravitDannuu( Double value){
        try {
            if(!socket.isOutputShutdown()){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(String.valueOf(value));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
