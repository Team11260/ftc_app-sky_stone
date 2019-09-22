package org.upacreekrobotics.GUI;

import java.io.*;
import java.net.Socket;

public class Data {

    private Socket socket;

    PrintWriter writer;
    BufferedReader reader;

    private static boolean connected = false;

    public Data() {
        connect();
        connected = true;
    }

    public void write(Message message){
        write(message.getMessage());
    }

    public void write(String text){
        try {
            writer.println(text);
        } catch (NullPointerException e){
            connect();
        }
    }

    public Message read() {
        String line = null;
        try{
            line = reader.readLine();
        } catch (IOException e){
            connect();
        } catch (NullPointerException e){
            connect();
        }
        if(line!=null) {
            String[] text=line.split("~",2);
            return new Message(MessageType.valueOf(text[0]),text[1]);
        }
        return null;
    }

    public boolean isConnected(){
        try {
            writer.println(new Message(MessageType.HAND_SHAKE,"HI").getMessage());
            return !writer.checkError();
        } catch (NullPointerException e){
            connect();
        }
        return false;
    }

    public boolean isReady(){
        try {
            if(reader!=null) return reader.ready();
        } catch (IOException e) {
        }
        return false;
    }

    private void connect() {
        connected = false;
        while (!connected) {
            try {
                connected = false;
                socket = new Socket("192.168.49.1", 19231);
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                connected = true;
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }
        }
    }
}
