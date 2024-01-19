package org.firstinspires.ftc.teamcode.drive.auto;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;

@Autonomous(name = "auto", group = "drive")
public class FullAuto extends LinearOpMode {

    private Boolean redAlliance = null;
    private Boolean closeStartingPosition = null;
    private Boolean goThroughStageDoor = null;
    private Boolean parkInCorner = null;
    private int waitSecondsAfterSpikeMark = 15;
    private Boolean finishedSelectingDelay = false;
    private int numCycles = 0;
    private Boolean finishedSelectingCycles = false;

    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeInInit() && !isStopRequested()) {


            if(redAlliance == null) {
                telemetry.clearAll();
                telemetry.addLine("Are you on red alliance?");
                telemetry.addLine("Press (Y/Δ) for yes, (B/O) for no");
                telemetry.update();

                if (gamepad1.y) {
                    redAlliance = true;
                } else if (gamepad1.b) {
                    redAlliance = false;
                }
            } else if (closeStartingPosition == null){
                telemetry.clearAll();
                telemetry.addLine("Are you starting close to the backdrop?");
                telemetry.addLine("Press (Y/Δ) for yes, (B/O) for no");
                telemetry.update();

                if (gamepad1.y) {
                    closeStartingPosition = true;
                } else if (gamepad1.b) {
                    closeStartingPosition = false;
                }
            } else if (goThroughStageDoor == null) {
                telemetry.clearAll();
                telemetry.addLine("Should autonomous mode go through the stage door?");
                telemetry.addLine("Press (Y/Δ) for yes, (B/O) for no");
                telemetry.update();

                if (gamepad1.y) {
                    goThroughStageDoor = true;
                } else if (gamepad1.b) {
                    goThroughStageDoor = false;
                }
            } else if (parkInCorner == null){
                telemetry.clearAll();
                telemetry.addLine("Should autonomous mode park in the corner or in the middle?");
                telemetry.addLine("Press (Y/Δ) for corner, (B/O) for middle");
                telemetry.update();

                if (gamepad1.y) {
                    parkInCorner = true;
                } else if (gamepad1.b) {
                    parkInCorner = false;
                }
            } else if (!finishedSelectingDelay){
                telemetry.clearAll();
                telemetry.addLine("How long should the autonomous mode wait before scoring on backdrop?");
                telemetry.addLine("Press (Y/Δ) to confirm");
                telemetry.addData("Delay (s): ", waitSecondsAfterSpikeMark);
                telemetry.update();

                if (gamepad1.dpad_up) {
                    waitSecondsAfterSpikeMark++;
                } else if (gamepad1.dpad_down) {
                    waitSecondsAfterSpikeMark--;
                }

                if (gamepad1.y) {
                    finishedSelectingDelay = true;
                }

                if (waitSecondsAfterSpikeMark > 15) {
                    waitSecondsAfterSpikeMark = 15;
                } else if (waitSecondsAfterSpikeMark < 0) {
                    waitSecondsAfterSpikeMark = 0;
                }

            } else if (!finishedSelectingCycles) {
                telemetry.clearAll();
                telemetry.addLine("How many cycles should the autonomous mode attempt?");
                telemetry.addLine("Press (Y/Δ) to confirm");
                telemetry.addData("Cycles (#): ", numCycles);
                telemetry.update();

                if (gamepad1.dpad_up) {
                    numCycles++;
                } else if (gamepad1.dpad_down) {
                    numCycles--;
                }

                if (gamepad1.y) {
                    finishedSelectingCycles = true;
                }

                if (numCycles > 2) {
                    numCycles = 2;
                } else if (numCycles < 0) {
                    numCycles = 0;
                }
            } else {
                break;
            }
        }

        AutonomousOpMode autonomousOpMode = null;
        if (autonomousOpMode == null) {
            if (redAlliance) {
                if (closeStartingPosition) {
                    //autonomousOpMode = new RedCloseAuto(hardwareMap, telemetry, numCycles, parkInCorner);
                } else {
                    //autonomousOpMode = new RedFarAuto(hardwareMap, telemetry, numCycles, parkInCorner, waitSecondsAfterSpikeMark);
                }
            } else {
                if (closeStartingPosition) {
                    //autonomousOpMode = new BlueCloseAuto(hardwareMap, telemetry, numCycles, parkInCorner);
                } else {
                    //autonomousOpMode = new BlueFarAuto(hardwareMap, telemetry, numCycles, parkInCorner, waitSecondsAfterSpikeMark);
                }
            }
        }

        autonomousOpMode.initialize();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            clearBulkCache();
            autonomousOpMode.runLoop();
        }
    }

    private void clearBulkCache() {
        LynxModule[] allHubs = new LynxModule[2];
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }



}
