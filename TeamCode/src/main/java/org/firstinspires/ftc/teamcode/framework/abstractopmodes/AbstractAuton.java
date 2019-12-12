package org.firstinspires.ftc.teamcode.framework.abstractopmodes;

import org.firstinspires.ftc.teamcode.framework.util.State;
import org.firstinspires.ftc.teamcode.framework.util.StateConfigurationException;
import org.firstinspires.ftc.teamcode.framework.util.StateMachine;
import org.firstinspires.ftc.teamcode.mecanum.hardware.RobotState;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class AbstractAuton extends AbstractOpMode {

    private StateMachine stateMachine = new StateMachine();

    private static AbstractAuton abstractAuton;

    private int initLoops = 0;

    public AbstractAuton() {
        abstractAuton = this;
        RobotState.currentMatchState = RobotState.MatchState.AUTONOMOUS;
    }

    @Override
    public void runOpmode() {

        gamepad1.reset();
        gamepad2.reset();

        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<Boolean> InitThread = () -> {
            try {
                Init();
            } catch (Exception e) {
                throwException(e);
            }
            return true;
        };
        Callable<Boolean> InitLoopThread = () -> {
            try {
                InitLoop();
                InitLoop(initLoops);
            } catch (Exception e) {
                throwException(e);
            }
            return true;
        };

        Future<Boolean> CurrentFuture;

        //calls user init
        CurrentFuture = service.submit(InitThread);

        while (!isStopRequested() && !isStarted()) {
            checkException();

            if (CurrentFuture.isDone()) {
                initLoops++;
                CurrentFuture = service.submit(InitLoopThread);
            }
        }

        while (!isStopRequested() && !CurrentFuture.isDone()) checkException();

        RegisterStates();

        try {
            stateMachine.prepare();
        } catch (StateConfigurationException e) {
            throwException(e);
        }

        addState(new State("run", "start", () -> {
            Run();
        }));

        boolean stateMachineActive = true;

        while (opModeIsActive() && stateMachineActive) {
            checkException();

            stateMachineActive = stateMachine.update();
        }

        AbstractOpMode.stopRequested();

        //TODO remake our shutdown procedure
        CurrentFuture.cancel(true);
        stateMachine.shutdown();

        while (!service.isTerminated()) {
            service.shutdownNow();
            checkException();
        }

        Stop();
        telemetry.stop();
    }

    public abstract void RegisterStates();

    public abstract void Init();

    public void InitLoop() {

    }

    public void InitLoop(int loops) {

    }

    public abstract void Run();

    public void Stop() {
    }

    public void addState(State state) {
        stateMachine.addState(state);
    }

    public static void addFinishedState(String state) {
        abstractAuton.stateMachine.addFinishedState(state);
    }
}