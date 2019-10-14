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
    ImageProcessor imageProcessor;
    Robot robot;

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
        robot = new Robot();

        // imageProcessor = new ImageProcessor(false);

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
    }

    @Override
    public void Run() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "run started" + opModeIsActive());
        telemetry.update();
        while (opModeIsActive()) {

            /*telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.green(image.getPixel(XORIGIN + 40, 355)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.blue(image.getPixel(XORIGIN + 40, 355)));
            */

            telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones());
            telemetry.update();
        }
    }

}
