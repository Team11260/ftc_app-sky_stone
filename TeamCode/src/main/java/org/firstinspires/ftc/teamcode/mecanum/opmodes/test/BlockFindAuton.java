package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;
import org.upacreekrobotics.dashboard.Config;

@Autonomous(name = "BlockFind Auton", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class BlockFindAuton extends AbstractAuton {

    private ImageProcessor imageProcessor;
    int XORIGIN = 250;
    int YORIGIN = 240;
    int BLOCKWIDTH = 220;
    int BLOCKHEIGHT = 110;
    int LINEWIDTH = 10;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
        imageProcessor = new ImageProcessor(false);

    }

    @Override
    public void Run() {

        Bitmap image = null;
        //ImageView userImage=null;

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "run started" + opModeIsActive());
        telemetry.update();
        while (opModeIsActive()) {

            image = imageProcessor.getImage();

            /*telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.green(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.blue(image.getPixel(XORIGIN + 40, 355)));

            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(XORIGIN + 260, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.green(image.getPixel(XORIGIN + 260, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.blue(image.getPixel(XORIGIN + 260, 355)));

            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(XORIGIN + 480, 355)));*/
           // telemetry.addData(DoubleTelemetry.LogMode.INFO, getFour(image,305, 295));
            //telemetry.addData(DoubleTelemetry.LogMode.INFO, getFour(image,525, 295));
            //telemetry.addData(DoubleTelemetry.LogMode.INFO, getFour(image,745, 295));


           // telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(525, 295)));
           // telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(745, 295)));


           // telemetry.addData(DoubleTelemetry.LogMode.INFO, "New Round of Data");
            for (int i = 0; i < 3; i++) {
                // telemetry.addData(DoubleTelemetry.LogMode.INFO, "" +Color.red(image.getPixel(305+i*220, 295 )));
            }

            telemetry.addData(DoubleTelemetry.LogMode.INFO, getSkyStonePositionThreeStones(image));

            telemetry.update();

            ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));
           /* ImageProcessor.drawBox(image, 295, 260, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 295, 310, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 425, 260, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 425, 310, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 515, 260, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 515, 310, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 645, 260, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 645, 310, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 735, 260, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 735, 310, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 865, 260, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, 865, 310, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));*/
            imageProcessor.setImage(image);

        }
    }

    public String getSkyStonePositionThreeStones(Bitmap image) {

        int threshold = 100;
        // int height = YORIGIN + (BLOCKHEIGHT/2);
        int height = 295;
        int x_left = 305;
        int x_center = x_left + BLOCKWIDTH;
        int x_right = x_center + BLOCKWIDTH;

        if (getFour(image, x_left, height) < threshold)
            return "Left";
        else if (getFour(image, x_center, height) < threshold)
            return "Center";
        else if (getFour(image, x_right, height) < threshold)
            return "Right";
        else
            return "No Sky Stone Found";
    }


    public int getPixelNineAve(Bitmap image, int x, int y) {

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

        sum = getPixelNineAve(image, x , y-yoffset);
        sum = sum + getPixelNineAve(image, x , y+yoffset);
        sum = sum + getPixelNineAve(image, x+xoffset , y-yoffset);
        sum = sum + getPixelNineAve(image, x+xoffset , y+yoffset);

        return sum / 4;
        //return Color.red(image.getPixel(x , y ));
    }

}
