package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;
import org.upacreekrobotics.dashboard.Config;

import java.util.HashMap;

@Autonomous(name = "BlockFind Auton", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class BlockFindAuton extends AbstractAuton {


    private ImageProcessor imageProcessor;
    int XORIGIN = 205;
    int YORIGIN = 330;
    int BLOCKWIDTH = 295;
    int BLOCKHEIGHT = 148;
    int LINEWIDTH = 10;


    int threshold = 100;
    // int height = YORIGIN + (BLOCKHEIGHT/2);
    int height = YORIGIN+10;
    int x_left = XORIGIN + 50;
    int x_center = x_left + BLOCKWIDTH;
    int x_right = x_center + BLOCKWIDTH;

    @Override
    public void RegisterStates() {

    }

    /* public void InitLoop() {
         ImageView userImage=null;

         Bitmap image = null;

         image = imageProcessor.getImage();

         telemetry.addData(DoubleTelemetry.LogMode.INFO, getSkyStonePositionThreeStones(image));

         telemetry.update();


         ImageProcessor.drawBox(image, XORIGIN, YORIGIN, 3 * BLOCKWIDTH, BLOCKHEIGHT, LINEWIDTH, Color.rgb(0, 0, 225));

         imageProcessor.setImage(image);
*/

    @Override
    public void Init() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
        imageProcessor = new ImageProcessor(false);

    }

    @Override
    public void Run() {

        ImageView userImage = null;
        Bitmap image = null;

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
            ImageProcessor.drawBox(image, XORIGIN + 30, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, XORIGIN + 30+BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
            ImageProcessor.drawBox(image, XORIGIN + 30+BLOCKWIDTH+BLOCKWIDTH, YORIGIN + 10, 1, BLOCKHEIGHT - 50, LINEWIDTH = 4, Color.rgb(225, 0, 0));
            /*ImageProcessor.drawBox(image, 295, 310, 20, 20, LINEWIDTH, Color.rgb(225, 0, 0));
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
        int height = YORIGIN+10;
        int x_left = XORIGIN + 30;
        int x_center = x_left + BLOCKWIDTH;
        int x_right = x_center + BLOCKWIDTH;

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
        for (int i = 0; i < BLOCKHEIGHT - 50; i++)
            sum += Color.red(image.getPixel(x, y + i));
        sum /= (19 + BLOCKHEIGHT);
        telemetry.addData(DoubleTelemetry.LogMode.INFO,sum);
        return sum;
    }

    public int getLineMode(Bitmap image, int x, int y) {
        int mode = 0;
        Integer current = 0;
        SparseIntArray hashmap = new SparseIntArray();
        for (int i = 0; i < BLOCKHEIGHT - 20; i++) {
            current = hashmap.get(Color.red(image.getPixel(x, y + i)));
            hashmap.put(Color.red(image.getPixel(x, y + i)), ++current);
        }
        int greatest = 0;
        int color = 0;
        for (int i = 0; i < hashmap.size(); i++) {
            if (hashmap.valueAt(i) > greatest) {
                greatest = hashmap.valueAt(i);
                color = hashmap.keyAt(i);
            }

        }
        return color;
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

        sum = getPixelNineAve(image, x, y - yoffset);
        sum = sum + getPixelNineAve(image, x, y + yoffset);
        sum = sum + getPixelNineAve(image, x + xoffset, y - yoffset);
        sum = sum + getPixelNineAve(image, x + xoffset, y + yoffset);

        return sum / 4;
        //return Color.red(image.getPixel(x , y ));
    }

}
