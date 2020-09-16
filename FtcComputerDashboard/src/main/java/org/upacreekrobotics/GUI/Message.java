package org.upacreekrobotics.GUI;

import org.json.JSONObject;

public class Message{

    private MessageType type;
    private String text;

    public Message(MessageType type, String message){
        this.type = type;
        text = message;
    }

    public Message(JSONObject jsonObject) {
        this(MessageType.valueOf(jsonObject.optString("type")), jsonObject.optString("data"));
    }

    public MessageType getType(){
        return type;
    }

    public String getText(){
        return text;
    }

    public JSONObject getMessage(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", type);
        jsonObject.put("data", text);

        return jsonObject;
    }
}