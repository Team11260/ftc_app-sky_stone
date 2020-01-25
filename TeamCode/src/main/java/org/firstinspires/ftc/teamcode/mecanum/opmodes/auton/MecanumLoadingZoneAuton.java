package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;
import org.upacreekrobotics.dashboard.Config;

//@Autonomous(name = "Mecanum LoadingZone Autonomus", group = "New")

@Config

public class MecanumLoadingZoneAuton extends AbstractAuton {
    /*public int time1 = 0;
    public double power1 = 0.0;
    public static double ARM_DOWN_POSITION = 0.855;
    public static double ARM_AUTON_POSITION = 0.5;
    public static double GRIPPER_GRIP_POSITION = 0.35;
    public static double GRIPPER_RELEASE_POSITION = 0.8;

    private Drive drive;
    private Servo arm;
    private Servo gripper;*/

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private VuforiaImpl vuforia;

    private TFObjectDetector tfod;
    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO,"init");
        telemetry.update();
        vuforia = new VuforiaImpl("BACK", false, false);
       // vuforia.startTracking("Calc_OT");
        /*arm = hardwareMap.servo.get("arm_servo");
        arm.setDirection(Servo.Direction.FORWARD);
        arm.setPosition(ARM_DOWN_POSITION);

        gripper = hardwareMap.servo.get("gripper_servo");
        gripper.setDirection(Servo.Direction.FORWARD);
        gripper.setPosition(GRIPPER_RELEASE_POSITION);

        drive = new Drive(hardwareMap, telemetry);*/

        //initVuforia();
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"initVu");
        telemetry.update();

       /* if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData(DoubleTelemetry.LogMode.INFO,"Sorry!", "This device is not compatible with TFOD");
        }*/

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        /*if (tfod != null) {
            tfod.activate();
        }*/

        telemetry.addData(DoubleTelemetry.LogMode.INFO,"Bitmap");
        telemetry.update();

            //VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();


            //int[][][] pixels = new int[image.getWidth()][image.getHeight()][4];







    }

    @Override
    public void Run() {
        Bitmap image = null;
        ImageView userImage=null;
       /* boolean program = true;
        //drive.follow(new PursuitPath(new Point(0, 0), new Point(18, 0)));
        //drive.updateLoop();
        //drive.follow(new PursuitPath(new Point(18, -2), new Point(23, -2)));
        //drive.updateLoop();
        //drive.setDrivePowerAll(0, 0, 0, 0);
        //telemetry.addData(DoubleTelemetry.LogMode.INFO, "X: " + drive.getCurrentPosition().getX() + " Y: " + drive.getCurrentPosition().getY());
        //telemetry.addData(DoubleTelemetry.LogMode.INFO);
        time1 = 850;
        power1 = 0.4;
        goForward(time1, power1);
        gripper.setPosition(GRIPPER_GRIP_POSITION);
        delay(500);
        arm.setPosition(ARM_AUTON_POSITION);
        delay(500);
        turnBack(0.8);
        time1 = 1700;
        power1 = 0.4;
        goForward(time1, power1);
        turnRight(0.5);
        arm.setPosition(ARM_DOWN_POSITION);
        delay(300);
        gripper.setPosition(GRIPPER_RELEASE_POSITION);
        delay(300);
        arm.setPosition(ARM_AUTON_POSITION);
        while(program){
        }*/
        telemetry.addData(DoubleTelemetry.LogMode.INFO,"run started"+opModeIsActive());
        telemetry.update();
        while (opModeIsActive()) {
            image = vuforia.getImage();
            userImage.setImageBitmap(image);
            image.setPixel(100,100,250);

            //telemetry.addData(DoubleTelemetry.LogMode.INFO,"take");
            //telemetry.update();
           // telemetry.addData(DoubleTelemetry.LogMode.INFO,image.getWidth());
          //  telemetry.addData(DoubleTelemetry.LogMode.INFO,image.getHeight());

            //if (Color.red(image.getPixel(100,400))<100)
            //telemetry.addData(DoubleTelemetry.LogMode.INFO,"Left");
            //else
            telemetry.addData(DoubleTelemetry.LogMode.INFO,Color.red(image.getPixel(100,400)));
            telemetry.addData(DoubleTelemetry.LogMode.INFO,Color.red(image.getPixel(700,400)));

            //telemetry.addData(DoubleTelemetry.LogMode.INFO,"Right");

            telemetry.update();
           /* if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData(DoubleTelemetry.LogMode.INFO,"# Object Detected", updatedRecognitions.size());
                    telemetry.update();
                    // step through the list of recognitions and display boundary info.
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(DoubleTelemetry.LogMode.INFO,recognition.getLabel());
                        telemetry.addData(DoubleTelemetry.LogMode.INFO,recognition.getTop());
                        telemetry.addData(DoubleTelemetry.LogMode.INFO,recognition.getRight());
                        telemetry.addData(DoubleTelemetry.LogMode.INFO,recognition.getBottom());
                        telemetry.update();

                    }

                }
                else {telemetry.addData(DoubleTelemetry.LogMode.INFO,"No Recognitions detected");}
                telemetry.update();
            }
            else {telemetry.addData(DoubleTelemetry.LogMode.INFO,"No TensorFlow object detected");}
            telemetry.update();*/
        }
    }



    /*public void goForward(int time, double power){
        drive.setDrivePowerAll(power, power, power, power);
        delay(time);
        drive.setDrivePowerAll(0,0,0,0);
    }
    public void turnBack(double power){
        drive.setDrivePowerAll(-power,0,-power,0);
        delay(600);
        drive.setDrivePowerAll(0,0,0,0);
    }
    public void turnRight(double power){
        drive.setDrivePowerAll(power,0,power,0);
        delay(1650);
        drive.setDrivePowerAll(0,0,0,0);
    }*/

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AbuxcJX/////AAABmXadAYnA80uwmb4Rhy4YmvIh7qg/f2yrRu1Nd8O7sSufbUWHSv1jDhunwDBItvFchrvkc8EjTzjh97m2kAPy8YOjBclQbEBtuR8qcIfrGofASCZh2M6vQ0/Au+YbhYh0MLLdNrond+3YjkLswv6+Se3eVGw9y9fPGamiABzIrosjUdanAOWemf8BtuQUW7EqXa4mNPtQ+2jpZQO2sqtqxGu1anHQCD0S/PvdZdB7dRkyWaH6XTZCat5gZ0fpFH/aLWMFP4yiknlgYbjT7gklUAqyDX81pNrQhWWY4dOFnz2WiWhkCt+MNZMLKH5SdsyC7gwKI/r3h51pTwgXZfyYymB60eYAFqEUpeTrL+4LmltN";
        //parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        //  Instantiate the Vuforia engine
        //vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }



    /**
     * Initialize the TensorFlow Object Detection engine.
     */
   /* private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.5;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }*/

}