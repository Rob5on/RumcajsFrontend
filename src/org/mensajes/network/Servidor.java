package org.mensajes.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
//BE------------------------------------------------------------------------------------------------------------------------------------------------------------------------
public class Servidor {

    private static ServerSocket sockServer;

    // Starts the server, which is capable of listening to messages
    public Servidor(int puerto) {
        try {
            sockServer = new ServerSocket(puerto);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error in creating the server for the other user to connect", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    //  Set the server listener with a Callback
    public static void aceptarMensajes(GetCallback call) {

        // This process is carried in a separate thread to avoid stopping the program
        Thread escucha = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket conexion = sockServer.accept();

                    PrintWriter out = new PrintWriter(conexion.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

                    while (true) {
                        String msg = in.readLine();

                        call.get(msg);
                    }


                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "The user has left the chat", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                    e.printStackTrace();
                }
            }
        });

        // The program starts the thread
        escucha.start();
    }

}
