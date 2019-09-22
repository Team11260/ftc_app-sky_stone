package org.upacreekrobotics.GUI;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.IOException;
import java.io.OutputStream;

public class DocumentOutputStream extends OutputStream {
    private Document doc;

    public DocumentOutputStream(Document doc) {
        this.doc = doc;
    }


    public void write(int b) {
        //write(new Byte[] {b}, 0, 1);
    }

    public void write(byte b[], int off, int len) throws IOException {
        try {
            doc.insertString(doc.getLength(),
                    new String(b, off, len), null);
        }
        catch (BadLocationException ble) {
            throw new IOException(ble.getMessage());
        }
    }
}