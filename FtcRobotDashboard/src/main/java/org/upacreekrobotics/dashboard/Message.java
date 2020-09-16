package org.upacreekrobotics.dashboard;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {

    private final MessageType type;
    private final String text;

    public Message(MessageType type, String message) {
        this.type = type;
        text = message;
    }

    public Message(MessageType type) {
        this.type = type;
        text = "";
    }

    public Message(MessageType type, Object message) {
        this(type, message.toString());
    }

    public Message(JSONObject jsonObject) {
        this(MessageType.valueOf(jsonObject.optString("type")), jsonObject.optString("data"));
    }

    public MessageType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public JSONObject getMessage() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("type", type);
            jsonObject.put("data", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}