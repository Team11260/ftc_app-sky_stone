package org.firstinspires.ftc.teamcode.test;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractTeleop;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;

@TeleOp(name = "DrawingTest", group = "Test")
//@Disabled

public class DrawingTest extends AbstractTeleop {

    private ImageProcessor imageProcessor;

    @Override
    public void RegisterEvents() {

    }

    @Override
    public void UpdateEvents() {

    }

    @Override
    public void Init() {
        imageProcessor = new ImageProcessor(true);
    }

    @Override
    public void InitLoop() {
        Bitmap bitmap = imageProcessor.getImage();

        ImageProcessor.drawBox(bitmap, 250, 300, 600, 105, 10, Color.rgb(0, 0, 225));

        imageProcessor.setImage(bitmap);
    }

    @Override
    public void Loop() {

    }

    @Override
    public void Stop() {
        imageProcessor.shutdown();
    }
}
