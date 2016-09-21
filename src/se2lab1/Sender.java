
package se2lab1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sender {

    public void send() {
        ThePanel thePanel = new ThePanel();
        thePanel.setInfo("Hello");
        String filename = "panel.ser";
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(thePanel);
            out.close();
            System.out.println(thePanel.getInfo());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
