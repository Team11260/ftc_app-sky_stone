package org.firstinspires.ftc.teamcode.mecanum.hardware.devices;

import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.SamplePosition;
import org.firstinspires.ftc.teamcode.framework.userhardware.paths.Path;
import org.firstinspires.ftc.teamcode.mecanum.hardware.Constants;

public final class RobotState {

    //public static double currentFrameTopScalar = Constants.START_FRAME_TOP_SCALAR;
    //public static double currentFrameBottomScalar = Constants.START_FRAME_BOTTOM_SCALAR;

    public static MatchState currentMatchState = MatchState.UNKNOWN;

    public static MineralLiftState currentMineralLiftState = MineralLiftState.COLLECT_POSITION;

    public static MineralGatePosition currentMineralGatePosition = MineralGatePosition.CLOSED;

    public static RobotLiftState currentRobotLiftState = RobotLiftState.RAISED;

    public static SamplePosition currentSamplePosition = SamplePosition.UNKNOWN;

    public static Path currentPath = null;

    public enum MatchState {
        AUTONOMOUS,
        TELEOP,
        UNKNOWN
    }

    public enum MineralLiftState {
        COLLECT_POSITION,
        DUMP_POSITION,
        IN_MOTION
    }

    public enum MineralGatePosition {
        OPEN,
        CLOSED
    }

    public enum RobotLiftState {
        RAISED,
        LOWERED,
        IN_MOTION
    }
}
