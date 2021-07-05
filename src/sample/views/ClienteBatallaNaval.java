package sample.views;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClienteBatallaNaval {
    private Socket cliente;
    private InetAddress host;
    PrintStream salida;
    BufferedReader entrada;

    public ClienteBatallaNaval(Socket cliente, InetAddress host) {
        this.cliente = cliente;
        this.host = host;
    }

    public void connectToServer() {
        try {
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            //System.out.println(entrada.readLine());

            salida = new PrintStream(cliente.getOutputStream());
            salida.println("Jugador Alex conectado");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mandarDatos(String num) {
        salida.println(num);
    }
}

