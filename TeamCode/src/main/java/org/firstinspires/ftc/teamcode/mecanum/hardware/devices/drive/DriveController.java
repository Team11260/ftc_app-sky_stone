package org.firstinspires.ftc.teamcode.mecanum.hardware.devices.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.framework.userhardware.PIDController;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.DriveSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Segment;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.TurnSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.trajectories.TrapezoidTrajectory;
import org.firstinspires.ftc.teamcode.framework.util.SubsystemController;

import static org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry.LogMode.INFO;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState.currentPath;

public class DriveController extends SubsystemController {

    private Drive drive;

    private PIDController velocityPID;
    private PIDController straightPID;

    @Override
    public void init() {
        drive = new Drive(hardwareMap);

        velocityPID = new PIDController(1000, 0, 100);
        straightPID = new PIDController(25, 1, 35, 0.2);
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

    public void runPath(Path path) {
        boolean lastPathPaused = false;

        if (currentPath != null && currentPath.isPaused()) {
            lastPathPaused = true;
        }

        currentPath = path;
        currentPath.reset();

        if (lastPathPaused) currentPath.pause();

        telemetry.addData(INFO, "Starting path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());

        while (!path.isDone() && opModeIsActive()) {

            //Path is done
            if (path.getNextSegment() == null) break;

            telemetry.addData(INFO, "Starting segment: " + path.getCurrentSegment().getName() + " in path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());

            if (path.getCurrentSegment().getType() == Segment.SegmentType.TURN) {
                turnToSegment((TurnSegment) path.getCurrentSegment());
            } else if (path.getCurrentSegment().getType() == Segment.SegmentType.DRIVE) {
                driveToSegment((DriveSegment) path.getCurrentSegment());
            }

            telemetry.addData(INFO, "Finished segment: " + path.getCurrentSegment().getName() + " in path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());
        }

        telemetry.addData(INFO, "Finished path: " + currentPath.getName() + "  paused: " + currentPath.isPaused() + "  done: " + currentPath.isDone());

    }

    public void turnToSegment(TurnSegment segment) {

    }

    public void driveToSegment(DriveSegment segment) {
        double position;
        double velocity;
        double target;
        double velocityCorrection;
        double output;
        double heading;
        double headingCorrection;

        drive.resetHeading();
        drive.resetPosition();
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        TrapezoidTrajectory trajectory = new TrapezoidTrajectory(segment.getDistance(), 0.1, 0.05, 0.5, 0.05);

        while (!trajectory.isDone(position = drive.getStraightPosition()) && opModeIsActive()) {
            target = trajectory.velocity(drive.getStraightPosition());
            velocity = drive.getStraightVelocity();
            velocityCorrection = velocityPID.output(target, velocity);
            output = (target * 1.2) + velocityCorrection;

            heading = drive.getHeading();
            headingCorrection = straightPID.output(0, heading);

            drive.setPower(output - headingCorrection, output + headingCorrection);

            telemetry.getSmartdashboard().putGraph("velocity", "profile", position, target);
            telemetry.getSmartdashboard().putGraph("velocity", "output", position, output);
            telemetry.getSmartdashboard().putGraph("velocity", "velocity", position, velocity);
        }

        drive.setPower(0);

        for(int i = 0; i < 50; i++) {
            position = drive.getStraightPosition();
            telemetry.getSmartdashboard().putGraph("velocity", "profile", position, 0);
            telemetry.getSmartdashboard().putGraph("velocity", "velocity", position, drive.getStraightVelocity());
            delay(100);
        }

        drive.resetPosition();
    }
}
