package org.upacreekrobotics.dashboard;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean connected = false;

    public ConnectionHandler(MessageHandler messageHandler) {
        Dashboard.getExecutor().execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if(!isConnected()) {
                    connect();
                }
                Message message = read();
                if (message != null) {
                    messageHandler.newMessage(message);
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void connect() {
        socket = null;
        connected = false;
        while (!connected) {
            try {
                connected = false;
                ServerSocket serv = new ServerSocket(19231);
                socket = serv.accept();
                socket.setReuseAddress(true);
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                connected = true;
            } catch (IOException | NullPointerException e) {
            }
        }
    }

    public void write(Message message) {
        write(message.getMessage());
    }

    public void write(JSONObject json) {
        write(json.toString());
    }

    public void write(String text) {
        if(isConnected()) {
            try {
                writer.println(text);
            } catch (NullPointerException e) {
                connected = false;
            }
        }
    }

    public Message read() {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException | NullPointerException e) {
            connected = false;
        }
        try {
            if (line != null) {
                return new Message(new JSONObject(line));
            }
        } catch (Exception e) {
            System.err.println("Invalid Dashboard Message");
        }
        return null;
    }

    public boolean isConnected() {
        try {
            writer.println(new Message(MessageType.HAND_SHAKE, "keepalive").getMessage());
            return !writer.checkError();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public interface MessageHandler {

        void newMessage(Message message);
    }
}
