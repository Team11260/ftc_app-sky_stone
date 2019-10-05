package org.firstinspires.ftc.teamcode.mecanum.opmodes.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;
import org.upacreekrobotics.dashboard.Config;

@Autonomous(name = "Vision Test", group = "New")

@Config
//  At 3 feet a stone is approximately  180x90 pixels

public class BlockFindAuton extends AbstractAuton {

    private VuforiaImpl vuforia;
    //private VuforiaImpl vuforia;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"init");
        telemetry.update();

        vuforia = new VuforiaImpl("BACK", true, false);
        //vuforia = new Vuforia("BACK", true, false);


    }

    @Override
    public void Run() {

        Bitmap image = null;
        //ImageView userImage=null;

        telemetry.addData(DoubleTelemetry.LogMode.INFO,"run started"+opModeIsActive());
        telemetry.update();
        while (opModeIsActive()) {

            image = vuforia.getImage();

            //telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(550, 470)));
            //telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.green(image.getPixel(100, 400)));

            for(int i = 0; i <50; i++){
             //   telemetry.addData(DoubleTelemetry.LogMode.INFO, "red" +Color.red(image.getPixel(i*20, 400 )));
            }
            /*telemetry.addData(DoubleTelemetry.LogMode.INFO, "red" +Color.red(image.getPixel(300, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "green" +Color.green(image.getPixel(300, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "blue" +Color.blue(image.getPixel(300, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "red" +Color.red(image.getPixel(500, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "green" +Color.green(image.getPixel(500, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "blue" +Color.blue(image.getPixel(500, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "red" +Color.red(image.getPixel(800, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "green" +Color.green(image.getPixel(800, 375)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "blue" +Color.blue(image.getPixel(800, 375)));
            */




           // telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.red(image.getPixel(625, 375)));


            //telemetry.addData(DoubleTelemetry.LogMode.INFO, Color.green(image.getPixel(700, 400)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO, getSkyStonePositionThreeStones(image));
            telemetry.update();

           // image.setPixel(300,300,0xff000000);  //alpha, red, green, blue

            // userImage.setImageBitmap(image);
            // image.setPixel(100,100,250);
        }

    }

    public String getSkyStonePositionTwoStones(Bitmap image){

        int threshold = 100;
        int height = 375;
        int x_left = 350;
        int x_right = 625;

        if (Color.red(image.getPixel(x_left,height))<threshold)
            return "Left";
        else if  (Color.red(image.getPixel(x_right, height))<threshold)
            return "Right";
        else
            return "No Sky Stone Found";
    }

    public String getSkyStonePositionThreeStones(Bitmap image){

        int threshold = 100;
        int height = 400;
        int x_left = 420;
        int x_center = 600;
        int x_right = 780;

        if (Color.red(image.getPixel(x_left,height))<threshold)
            return "Left";
        else if (Color.red(image.getPixel(x_center,height))<threshold)
            return "Center";
        else if (Color.red(image.getPixel(x_right,height))<threshold)
            return "Right";
        else
            return "No Sky Stone Found";
    }
}
