package org.upacreekrobotics.GUI;

public enum MessageType {
    ROBOT_STATUS,
    RESTART_ROBOT,
    HAND_SHAKE,
    BATTERY,
    PHONE_BATTERY,

    OP_MODES,
    GET_OP_MODES,
    SELECT_OP_MODE,

    INIT_OP_MODE,
    RUN_OP_MODE,
    STOP_OP_MODE,

    VARIABLE,

    GET_VALUE,
    RETURN_VALUE,
    INFO,
    TELEMETRY,
    LOG,

    SMARTDASHBOARD_PUT,
    SMARTDASHBOARD_GET,

    GAMEPAD_1_SET,
    GAMEPAD_2_SET;

    public String getMessage(MessageType type) {
        return type.toString();
    }
}