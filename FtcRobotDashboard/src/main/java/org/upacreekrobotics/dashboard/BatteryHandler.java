package org.upacreekrobotics.dashboard;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.BatteryChecker;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.List;

public class BatteryHandler implements BatteryChecker.BatteryWatcher {

    private BatteryChecker batteryChecker;
    private double currentPhoneBatteryPercent = 0.0;
    private List<VoltageSensor> voltageSensors = null;
    private OpModeManagerImpl opModeManager = null;

    public BatteryHandler() {
        batteryChecker = new BatteryChecker(this, 2000);
        batteryChecker.pollBatteryLevel(this);

        Dashboard.getExecutor().execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Dashboard.getConnectionHandler().write(new Message(MessageType.PHONE_BATTERY, getPhoneBatteryPercent()));
                Dashboard.getConnectionHandler().write(new Message(MessageType.BATTERY, getRobotBatteryVoltage()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public double getPhoneBatteryPercent() {
        return currentPhoneBatteryPercent;
    }

    public double getRobotBatteryVoltage() {
        double sensors = 0;
        double voltage = 0;
        if (voltageSensors == null) {
            if (opModeManager == null) {
                opModeManager = OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity());
            }
            if (opModeManager == null) {
                return 0.0;
            }
            voltageSensors = opModeManager.getHardwareMap().getAll(VoltageSensor.class);
        }
        for (VoltageSensor voltageSensor : voltageSensors) {
            try {
                if (voltageSensor.getVoltage() != 0) {
                    sensors++;
                    voltage += voltageSensor.getVoltage();
                }
            } catch (Exception e) {
                //TODO  log error
                //if (DashboardTelemtry != null) DashboardTelemtry.write("Robot Battery Voltage Read Error");
            }
        }
        if (sensors < 1) {
            return 0.0;
        }
        return voltage / sensors;
    }

    @Override
    public void updateBatteryStatus(BatteryChecker.BatteryStatus status) {
        if (status != null) {
            currentPhoneBatteryPercent = status.percent;
        }
    }
}
