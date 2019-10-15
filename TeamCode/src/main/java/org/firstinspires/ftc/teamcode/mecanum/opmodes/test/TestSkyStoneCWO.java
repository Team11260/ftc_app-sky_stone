package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.upacreekrobotics.dashboard.Config;

//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;

@Autonomous(name = "BlockFind CWO Auton", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class TestSkyStoneCWO extends AbstractAuton {


    private ImageProcessor imageProcessor;
    int XORIGIN = 205;
    int YORIGIN = 330;
    int BLOCKWIDTH = 295;
    int BLOCKHEIGHT = 148;
    int LINEWIDTH = 10;
    int SWATHXOFFSET = 30;
    int SWATHYOFFSET = 10;
    int SWATHYHEIGHT = 98;
    int THRESHOLD = 100;


    int threshold = 100;
    // int height = YORIGIN + (BLOCKHEIGHT/2);
    int height = YORIGIN+10;
    int x_left = XORIGIN + 50;
    int x_center = x_left + BLOCKWIDTH;
    int x_right = x_center + BLOCKWIDTH;
    String skystoneLocation;
    Bitmap image = null;

    @Override
    public void RegisterStates() {

    }

     public void InitLoop() {

         skystoneLocation = getSkyStonePositionThreeStones();

         telemetry.addData(DoubleTelemetry.LogMode.INFO, skystoneLocation);
         telemetry.update();
     }

    @Override
    public void Init() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
        imageProcessor = new ImageProcessor(false);
    }

    @Override
    public void Run() {
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "run started" + opModeIsActive());
        telemetry.update();

        while (opModeIsActive()) {

        }
    }

    public String getSkyStonePositionThreeStones() {

        int threshold = THRESHOLD;
        int height = YORIGIN+SWATHYOFFSET;
        int x_left = XORIGIN + SWATHXOFFSET;
        int x_center = x_left + BLOCKWIDTH;
        int x_right = x_center + BLOCKWIDTH;

        image = imageProcessor.getImage();
        ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
        ImageProcessor.drawBox(image, XORIGIN + SWATHXOFFSET, YORIGIN + SWATHYOFFSET, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        ImageProcessor.drawBox(image, XORIGIN + SWATHXOFFSET+BLOCKWIDTH, YORIGIN + SWATHYOFFSET, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        ImageProcessor.drawBox(image, XORIGIN + SWATHYOFFSET+BLOCKWIDTH+BLOCKWIDTH, YORIGIN + SWATHYOFFSET, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
        imageProcessor.setImage(image);

        if (getLineAverage(image, x_left, height) < threshold)
            return "Left";
        else if (getLineAverage(image, x_center, height) < threshold)
            return "Center";
        else if (getLineAverage(image, x_right, height) < threshold)
            return "Right";
        else
            return "No Sky Stone Found";
    }

    public int getLineAverage(Bitmap image, int x, int y) {
        int sum = 0;
        for (int i = 0; i < SWATHYHEIGHT; i++)
            sum += Color.red(image.getPixel(x, y + i));
        sum /=  SWATHYHEIGHT;
        telemetry.addData(DoubleTelemetry.LogMode.INFO,sum);
        telemetry.update();
        return sum;
    }



    /*public int getPixelNineAve(Bitmap image, int x, int y) {

        int sum = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum = sum + (Color.red(image.getPixel(x - 1 + i, y - 1 + j)));
            }
        }
        return sum / 9;
        //return Color.red(image.getPixel(x , y ));
    }

    public int getFour(Bitmap image, int x, int y) {

        int sum = 0;
        int yoffset = 20;
        int xoffset = 130;

        sum = getPixelNineAve(image, x, y - yoffset);
        sum = sum + getPixelNineAve(image, x, y + yoffset);
        sum = sum + getPixelNineAve(image, x + xoffset, y - yoffset);
        sum = sum + getPixelNineAve(image, x + xoffset, y + yoffset);

        return sum / 4;
        //return Color.red(image.getPixel(x , y ));
    }*/

}
