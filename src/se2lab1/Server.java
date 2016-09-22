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

    BufferedReader br;

    BufferedOutputStream bos;
    PrintStream ps;

    public Server() throws InterruptedException
    {
        this(PORT);
    }

    public Server(int port) throws InterruptedException
    {
        this.port = port;

    }

    public void sendFile() //throws InterruptedException
    {
        File file = new File("panel.ser");

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

                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    bos = new BufferedOutputStream(socket.getOutputStream()); //will be used to send files
                    ps = new PrintStream(bos); //will be used to send messages

                    FileInputStream fis = new FileInputStream(file);

                    byte[] b;
                    final int defBufferSize = 8192;
                    if (file.length() < defBufferSize) {
                        b = new byte[(int) file.length()];
                    } else {
                        b = new byte[defBufferSize]; //max of buffer
                    }

                    int r;
                    while ((r = fis.read(b)) > 0) {
                        bos.write(b, 0, r);
                    }
                    bos.flush();
                    b = null;

                    Thread.sleep(10);
                    if (shutdownServer) { //this variable changed from another thread not yet coded
                        break SERVER_CONN;
                    }

                } catch (IOException ex) {
                    System.out.println(ex);

                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            } //waiting new connection loop

            //shutdown server
            ps.close();
            bos.close();
            br.close();
            socket.close();
            ps = null;
            bos = null;
            br = null;
            socket = null;

        } catch (IOException e) {
            System.out.println("error");
            System.err.println(e);
        }

    }

    private void log(String msg)
    {
        System.out.println(msg);
    }


}
