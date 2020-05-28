package org.mensajes.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;
//BE------------------------------------------------------------------------------------------------------------------------------------------------------------------------
public class Cliente {

    // Class variables
    private static Socket sockCliente;

    private static PrintWriter out;
    private static BufferedReader in;

    // Starts the client, which has the ability to send messages
    public Cliente(String IP, int puerto) {
        try {
            sockCliente = new Socket(IP, puerto);
            out = new PrintWriter(sockCliente.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sockCliente.getInputStream()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the other user \n Are you sure you are connected?", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Method to send messages
    public static void enviarMensaje(String msg, SentCallback call) {
        System.out.println("enviado a "+sockCliente.getLocalAddress()+"\nmsg: "+msg);

        out.println(msg);
        Thread get = new Thread(new Runnable() {

            @Override
            public void run() {
                String ret;
                try {
                    ret = in.readLine();
                    System.out.println(ret);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error in verifying the reception of the message by the other user", "ERROR", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
        get.start();
    }
}
