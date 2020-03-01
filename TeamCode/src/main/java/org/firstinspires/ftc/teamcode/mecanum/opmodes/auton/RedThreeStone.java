package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.PurePursuitSegment;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.Point;
import org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit.PursuitPath;

import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.OFF_SET;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_BLOCK2_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_BLOCK4_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_BLOCK5_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_BLOCK6_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_BLOCK_LOCATION_Y;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_FOUNDATION_MIDDLE_NEAR_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_FOUNDATION_MIDDLE_X;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_FOUNDATION_Y;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_FOUNDATION_Y_2ND;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_RUNWAY_Y;
import static org.firstinspires.ftc.teamcode.mecanum.hardware.AutonPursuitPaths.RED_RUNWAY_Y_2;

@Autonomous(group = "new", name = "Red Three Stone")

public class RedThreeStone extends RedTwoStone {




    @Override
    public void Init() {


        super.Init();


    }

    @Override
    public void Run() {

        super.Run();


    }



    @Override
    protected Path centerPath() {
        Path center = new Path("get center");

        center.addSegment(new PurePursuitSegment("drive to first sky stone",
                new PursuitPath(
                        new Point(0, 0),
                        new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y))
                        .setMaxAcceleration(0.01), 0));
        center.addSegment(new PurePursuitSegment("first trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK5_X, RED_BLOCK_LOCATION_Y),
                        new Point(RED_BLOCK5_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y)), 0));
        center.addSegment(new PurePursuitSegment("drive to second sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_MIDDLE_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK2_X, RED_RUNWAY_Y),
                        new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET)), 0));
        center.addSegment(new PurePursuitSegment("second trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK2_X, RED_BLOCK_LOCATION_Y + OFF_SET),
                        new Point(RED_BLOCK2_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y)), 50));
        center.addSegment(new PurePursuitSegment("drive to third sky stone",
                new PursuitPath(
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X, RED_RUNWAY_Y_2),
                        new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y))
                        .setMaxDeceleration(0.01), 100));
        center.addSegment(new PurePursuitSegment("last trip to foundation",
                new PursuitPath(
                        new Point(RED_BLOCK6_X, RED_BLOCK_LOCATION_Y),
                        new Point(RED_BLOCK6_X + 10, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2),
                        new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND)), 100));
        center.addSegment(new PurePursuitSegment("drive to fourth sky stone",
                new PursuitPath(new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK4_X, RED_RUNWAY_Y_2), new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y)), 100));
        center.addSegment(new PurePursuitSegment("fourth trip to foundation",
                new PursuitPath(new Point(RED_BLOCK4_X, RED_BLOCK_LOCATION_Y), new Point(RED_BLOCK4_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_RUNWAY_Y_2), new Point(RED_FOUNDATION_MIDDLE_NEAR_X, RED_FOUNDATION_Y_2ND)), 100));


        return center;

    }



    @Override
    protected Path dragPath() {
        return super.dragPath();
    }


}
