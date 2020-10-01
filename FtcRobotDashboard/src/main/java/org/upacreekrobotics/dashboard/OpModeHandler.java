package org.upacreekrobotics.dashboard;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpModeHandler implements OpModeManagerImpl.Notifications {

    private final List<String> opModeNames;

    private OpModeManagerImpl opModeManager;
    private OpModeState activeOpModeState;
    private long opModeInitTime;
    private long opModeStartTime;

    public OpModeHandler() {
        opModeManager = OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity());
        opModeNames = Collections.synchronizedList(new ArrayList<>());

        activeOpModeState = OpModeState.STOPPED;
        opModeInitTime = -1;
        opModeStartTime = -1;

        Dashboard.getExecutor().execute(() -> {
            RegisteredOpModes.getInstance().waitOpModesRegistered();
            synchronized (opModeNames) {
                for (OpModeMeta opModeMeta : RegisteredOpModes.getInstance().getOpModes()) {
                    opModeNames.add(opModeMeta.name);
                }
            }
        });
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {
        if (isOpModeActive()) {
            activeOpModeState = OpModeState.INIT;
            opModeInitTime = System.currentTimeMillis();
            Dashboard.getConnectionHandler().write(new Message(MessageType.SELECT_OP_MODE, getActiveOpModeName()));
            Dashboard.getConnectionHandler().write(new Message(MessageType.INIT_OP_MODE));
        }
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
        if (isOpModeActive()) {
            activeOpModeState = OpModeState.RUNNING;
            opModeStartTime = System.currentTimeMillis();
            Dashboard.getConnectionHandler().write(new Message(MessageType.RUN_OP_MODE));
        }
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        if (isOpModeActive()) {
            activeOpModeState = OpModeState.STOPPED;
            Dashboard.getConnectionHandler().write(new Message(MessageType.STOP_OP_MODE));
        }
    }

    public OpModeManagerImpl getOpModeManager() {
        if(opModeManager == null) {
            opModeManager = OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity());
        }
        return opModeManager;
    }

    public String getActiveOpModeName() {
        return opModeManager.getActiveOpModeName();
    }

    public OpModeState getActiveOpModeState() {
        return activeOpModeState;
    }

    public long getOpModeInitTime() {
        return opModeInitTime;
    }

    public long getOpModeStartTime() {
        return opModeStartTime;
    }

    public long getTimeSinceInit() {
        return getOpModeInitTime() - System.currentTimeMillis();
    }

    public long getTimeSinceStart() {
        return getOpModeStartTime() - System.currentTimeMillis();
    }

    public void initializeActiveOpMode(String name) {
        opModeManager.initActiveOpMode(name);
    }

    public void startActiveOpMode() {
        if(isOpModeActive()) {
            opModeManager.startActiveOpMode();
        }
    }

    public void stopActiveOpMode() {
        if(isOpModeActive()) {
            opModeManager.stopActiveOpMode();
        }
    }

    public void setGamepad(int gamepad, String update) {

    }

    public boolean isOpModeActive() {
        return !getOpModeManager().getActiveOpModeName().equals("$Stop$Robot$");
    }

    public boolean isOpModeInInit() {
        return isOpModeActive() && activeOpModeState == OpModeState.INIT;
    }

    public boolean isOpModeRunning() {
        return isOpModeActive() && activeOpModeState == OpModeState.RUNNING;
    }

    public boolean isOpModeStopped() {
        return !isOpModeActive() || activeOpModeState == OpModeState.STOPPED;
    }

    public List<String> getOpModeNames() {
        synchronized (opModeNames) {
            return opModeNames;
        }
    }

    public void startOpMode(String name) {
        new Thread(() -> {
            while (!("$Stop$Robot$").equals(getOpModeManager().getActiveOpModeName()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            getOpModeManager().initActiveOpMode(name);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            getOpModeManager().startActiveOpMode();
        }).start();
    }

    public enum OpModeState {
        INIT,
        RUNNING,
        STOPPED
    }
}
