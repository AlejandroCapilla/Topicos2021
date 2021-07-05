package sample.views;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClienteSocket {
    private Socket cliente;
    private InetAddress host;

    public void connectToServer() {
        try {
            host = InetAddress.getLocalHost();
            cliente = new Socket(host, 5000);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            System.out.println(entrada.readLine());

            PrintStream salida = new PrintStream(cliente.getOutputStream());
            salida.println("Cliente Alex conectado");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
