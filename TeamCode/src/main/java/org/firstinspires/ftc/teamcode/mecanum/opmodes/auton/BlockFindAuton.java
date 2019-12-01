package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
//import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.Vuforia;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageProcessor;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.util.PathState;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Robot;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.Arm;
import org.firstinspires.ftc.teamcode.mecanum.hardware.devices.arm.ArmController;
import org.upacreekrobotics.dashboard.Config;

import java.nio.file.Paths;
import java.text.DecimalFormat;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RedPurePursuitRight;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectCenterSkyStone;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectLeftSkyStone;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.collectRightSkyStone;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.Constants.forwardDrive;

@Autonomous(name = "BlockFind Auton", group = "New")


//  At 3 feet a stone is approximately  180x90 pixels
@Config
public class BlockFindAuton extends AbstractAuton {
    //ImageProcessor imageProcessor;
    Robot robot;
    boolean dashBoardSwitch = true;
    DecimalFormat DF;
    private ElapsedTime runtime = new ElapsedTime();


    public static double power = 0.19;
    public static int loopTime = 20;
    public static double velThreshold = 0.5;

    public static double distance = 24;


    @Override
    public void RegisterStates() {
      //  addState(new PathState("Lower Arm", "strafe to sky stone", robot.setArmDownCallable()));
        addState(new PathState("Gripper Grip", "strafe to sky stone", robot.setGripperGripCallable()));

     //   addState(new PathState("Raise Arm", "strafe to foundation", robot.setGripperGripCallable()));
        addState(new PathState("Lower Arm at Foundation", "strafe to foundation", robot.setArmDownCallable()));
        //addState(new PathState("Release Gripper","strafe to foundation",robot.setGripperReleaseCallable()));
    }

    public void InitLoop() {

        // arm.setArmUpPosition();

        telemetry.addData(DoubleTelemetry.LogMode.INFO, robot.getSkyStonePositionThreeStones(0));

        telemetry.update();
        delay(1000);

    }

    @Override
    public void Init() {
        robot = new Robot();

        robot.setArmUp();
        robot.setGripperRelease();

        //drive = new Drive(hardwareMap, telemetry);
        DF = new DecimalFormat("#.##");
        // imageProcessor = new ImageProcessor(false);


        telemetry.addData(DoubleTelemetry.LogMode.INFO, "init");
        telemetry.update();
    }

    @Override
    public void Run() {

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "run started");
        telemetry.update();

        runtime.reset();
        robot.setArmDown();
        switch (robot.getSkyStonePositionThreeStones(0)) {
            case "Right":
                robot.runDrivePath(RedPurePursuitRight);
                break;

            case "Left":
               // robot.runDrivePath(collectLeftSkyStone);
                break;

            case "Center":
                //robot.runDrivePath(collectCenterSkyStone);
                break;

            default:
                //robot.runDrivePath(collectCenterSkyStone);
                break;


        }
        telemetry.addData(DoubleTelemetry.LogMode.INFO, "time elapsed  " + runtime.milliseconds());
        telemetry.update();
//        robot.arm.setArmDownPosition();
//        robot.arm.setGripperGripPosition();
//        delay(1000);
//        robot.runDrivePath(collectRightSkyStone);

        //robot.runTestPurePursuit();


        //robot.driver.approachFoundation(power, loopTime, velThreshold );

        //robot.driver.approachWall(-0.2,10);

        //robot.runDrivePath(collectLeftSkyStone);

        //robot.strafe(+0.14,5000);
        //robot.stop();
        /*robot.strafe(-0.25,500);
        robot.stop();
        robot.driver.realignHeading();
        robot.stop();
        robot.runDrivePath(collectLeftSkyStone);
        robot.driver.realignHeading();
        robot.stop();*/
        /*arm.setGripperGripPosition();
        delay(500);
        robot.arm.setArmUpPosition();
        robot.runDrivePath(strafeToTray);
        robot.driver.setPower(0.2,0.23);
        delay(500);
        robot.driver.setPower(0.0,0.0);
        robot.arm.setArmDownPosition();
        delay(200);
        arm.setGripperReleasePostion();
        delay(300);
        arm.setArmUpPosition();
        delay(300);*/

        //telemetry.addData(DoubleTelemetry.LogMode.INFO, "segment distance");
        //robot.runDrivePath(path);
        //arm.setArmDownPosition();
        //arm.setGripperReleasePostion();
        //robot.runDrivePath(collectCenterSkyStone);
        delay(9000);

        //robot.strafe(-0.4,400);

       /* robot.runDrivePath(collectBlock);
        arm.setGripperGripPosition();
        delay(500);
        arm.setArmUpPosition();
        robot.runDrivePath(backUp);
        robot.strafe(-0.4,2700);
        robot.runDrivePath(forwardDrive);
        arm.setArmDownPosition();
        arm.setGripperReleasePostion();
        arm.setArmBackPosition();
        robot.runDrivePath(dragFoundation);
        robot.strafe(0.3, 1200);
        robot.runDrivePath(avoidRobot);
        robot.strafe(0.3, 1200);*/
        // robot.driver.setPower(-0.3,-0.3);
        // delay(500);


        robot.stop();

    }


    @Override
    public void Stop() {
        robot.driver.stop();
    }


    public synchronized void findSecondBlock(int position, double power) {


        //while (robot.getPixelStripeAve() > 50) {
        //    telemetry.addData("Pixel average is   " + robot.getPixelStripeAve());
        //}

    }
}