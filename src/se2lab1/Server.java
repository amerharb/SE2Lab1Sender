package se2lab1;

import java.io.*;
import java.net.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{

    private static final int PORT = 8000; //Deafult port
    private final int port;
    private ServerSocket serverSocket = null;
    private boolean shutdownServer = false; // this variable desigen to be changed from diffreant thread 

    Socket socket;

    public Server() throws InterruptedException
    {
        this(PORT);
    }

    public Server(int port) throws InterruptedException
    {
        this.port = port;

    }

    public void waitThenSendThePanelObject(ThePanel thePanel) //throws InterruptedException
    {
        // create a server socket
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e);
        }

        log("Server is Started on port : " + port);

        try {
            SERVER_CONN:
            while (true) { //wait for connection 
                socket = null;
                try {
                    // waiting for a connection, only one connection at the time
                    log("Server is waiting for connection");
                    socket = serverSocket.accept(); //code block here until connection come

                    log("Got request from " + socket.getInetAddress());

                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(thePanel);

                    //shutdown server
                    out.close();
                    socket.close();
                    out = null;
                    socket = null;

                    if (shutdownServer) { //this variable changed from another thread not yet coded
                        break SERVER_CONN;
                    }

                } catch (IOException ex) {
                    System.out.println(ex);
                }
            } //waiting new connection loop

        } catch (Exception e) {
            System.out.println("error");
            System.err.println(e);
        }
    }

    private void log(String msg)
    {
        System.out.println(msg);
    }

}
