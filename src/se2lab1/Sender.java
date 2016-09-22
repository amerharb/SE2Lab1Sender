package se2lab1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender
{

    private final boolean SAVE_TO_FILE = false;

    public void send()
    {
        ThePanel thePanel = new ThePanel();
        thePanel.setInfo("Hello");
        
        String filename;
        if (SAVE_TO_FILE) {
            filename = "panel.ser";
        }        
        
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        
        try {
            if (SAVE_TO_FILE) {
                fos = new FileOutputStream(filename);
                out = new ObjectOutputStream(fos);
                out.writeObject(thePanel);
                out.close();
                System.out.println("the Object has been saved in" + filename);
            } else {
                try {
                    Server s = new Server(8000);
                    s.waitThenSendThePanelObject(thePanel);//its mean wait for connection to send the file 
                } catch (InterruptedException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            System.out.println("test info from object : " + thePanel.getInfo());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
