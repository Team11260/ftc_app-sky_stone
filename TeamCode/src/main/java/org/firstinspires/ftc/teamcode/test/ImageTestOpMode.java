package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.upacreekrobotics.dashboard.Dashboard;

@Autonomous(name = "Test Image")

public class ImageTestOpMode extends AbstractAuton {

    private ImageProcessor processor;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        processor = new ImageProcessor();
    }

    @Override
    public void InitLoop() {
        long start = System.currentTimeMillis();
//        processor.getImage();
        Dashboard.getSmartDashboard().putImage("Cool Image", processor.getImage());
        Dashboard.getSmartDashboard().putValue("Frame get time", System.currentTimeMillis() - start);
    }

    @Override
    public void Run() {

    }
}
