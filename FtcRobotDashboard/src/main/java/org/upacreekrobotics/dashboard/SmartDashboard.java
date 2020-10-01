package org.upacreekrobotics.dashboard;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmartDashboard {

    private final List<String> responses;

    private String oldMessages;
    private int requestID;
    private Object requestIDLock;

    public SmartDashboard() {
        responses = Collections.synchronizedList(new ArrayList<>());

        oldMessages = "";
        requestID = 0;
        requestIDLock = new Object();
    }

    void reconnected() {
        String[] parts = oldMessages.split("&#%#&");
        for (String part : parts) {
            if (!part.equals(" "))
                put(part);
        }
        oldMessages = "";
    }

    void onResponse(Message message) {
        synchronized (responses) {
            responses.add(message.getText());
        }
    }

    private void put(String text) {
        if (Dashboard.getConnectionHandler().isConnected()) {
            Dashboard.getConnectionHandler().write(new Message(MessageType.SMARTDASHBOARD_PUT, text));
        } else {
            oldMessages += text + "#&%&#";
        }
    }

    public void putValue(Object key, Object value) {
        put("VALUE<&#%#&>" + key + "<&#%#&>" + value);
    }

    public void putBoolean(Object key, boolean value) {
        put("BOOLEAN<&#%#&>" + key + "<&#%#&>" + value);
    }

    public void putButton(Object key) {
        put("BUTTON<&#%#&>" + key);
    }

    public void putInput(Object key) {
        put("INPUT<&#%#&>" + key);
    }

    public void putSlider(Object key, int low, int high) {
        put("SLIDER<&#%#&>" + key + "<&#%#&>" + low + "<&#%#&>" + high);
    }

    public void putGraph(Object key, String set, double x, double y) {
        put("GRAPH<&#%#&>" + key + "<&#%#&>" + set + "<&#%#&>" + x + "<&#%#&>" + y);
    }

    public void putGraphPoint(Object key, String set, double x, double y) {
        put("GRAPH_POINT<&#%#&>" + key + "<&#%#&>" + set + "<&#%#&>" + x + "<&#%#&>" + y);
    }

    public void putGraphCircle(Object key, String set, double r, double x, double y) {
        put("GRAPH_CIRCLE<&#%#&>" + key + "<&#%#&>" + set + "<&#%#&>" + r + "<&#%#&>" + x + "<&#%#&>" + y);
    }
    
    public void clearGraph(Object key, String set) {
        put("GRAPH_CLEAR<&#%#&>" + key + "<&#%#&>" + set);
    }

    public void putImage(Object key, Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        put("IMAGE<&#%#&>" + key + "<&#%#&>" + Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT));
    }

    public Object get(String type, Object key) {
        int requestID = -1;

        synchronized (requestIDLock) {
            if (Dashboard.getConnectionHandler().isConnected()) {
                Dashboard.getConnectionHandler().write(new Message(MessageType.SMARTDASHBOARD_GET,
                        this.requestID + "<&#%#&>" + type + "<&#%#&>" + key));
            }
            requestID = this.requestID;
            this.requestID++;
        }

        Object response = null;

        loop:
        while (Dashboard.getConnectionHandler().isConnected()) {
            synchronized (responses) {
                for (String message : responses) {
                    String[] parts = message.split("<&#%#&>");
                    if (Integer.valueOf(parts[0]) == requestID) {
                        responses.remove(message);

                        message = message.split("<&#%#&>")[1];

                        return message;
                    }
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getValue(Object key) {
        Object response = get("VALUE", key);

        if(response != null) {
            return response.toString();
        }

        return null;
    }

    public Boolean getBoolean(Object key) {
        Object response = get("BOOLEAN", key);

        if(response != null) {
            return Boolean.valueOf(response.toString());
        }

        return null;
    }

    public Boolean getButton(Object key) {
        Object response = get("BUTTON", key);

        if(response != null) {
            return Boolean.valueOf(response.toString());
        }

        return null;
    }

    public String getInput(Object key) {
        Object response = get("INPUT", key);

        if(response != null) {
            return response.toString();
        }

        return null;
    }

    public Integer getSlider(Object key) {
        Object response = get("SLIDER", key);

        if(response != null) {
            return Integer.valueOf(response.toString());
        }

        return null;
    }
}