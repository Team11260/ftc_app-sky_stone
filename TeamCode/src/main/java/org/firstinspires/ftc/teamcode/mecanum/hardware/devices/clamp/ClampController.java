package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.clamp;

import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.CLAMP_CLOSE;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.CLAMP_OPEN;

public class ClampController extends SubsystemController {

    public Clamp clamp;

    boolean isClamped = false;

    public ClampController() {clamp = new Clamp(hardwareMap);
    }

    @Override
    public void update() throws Exception {

    }

    public void setClampOpen() {clamp.setClampPosition(CLAMP_OPEN);
    }

    public void setClampClose() {clamp.setClampPosition(CLAMP_CLOSE);
    }

    public void toggleClamp() {
        if (isClamped)
            setClampOpen();
        else
            setClampClose();
        isClamped = !isClamped;
    }

    @Override
    public void stop() {

    }

}
