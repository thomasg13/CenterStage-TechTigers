package org.firstinspires.ftc.teamcode.drive.vision;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Vision Test", group="Test")
public class VisionTestOpMode extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        TeamElementSubsystem visionSubsystem = new TeamElementSubsystem(hardwareMap);
        telemetry.addLine("Are you blue or red?");
        telemetry.addLine("Press (Y/Δ) for blue, (B/O) for red");
        telemetry.update();

        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.y) {
                visionSubsystem.setAlliance("blue");
                telemetry.clearAll();
                telemetry.addLine("Blue Team Detection");
                telemetry.update();
                break; // Exit the loop after selection
            } else if (gamepad1.b) {
                visionSubsystem.setAlliance("red");
                telemetry.clear();
                telemetry.addLine("Red Team Detection");
                telemetry.update();
                break; // Exit the loop after selection
            }
        }

        waitForStart();

        while (opModeIsActive()) {
            int detectedZone = visionSubsystem.elementDetection(telemetry);
            telemetry.addData("Detected Zone", detectedZone);
            telemetry.addData("Distance 1", visionSubsystem.getDistance1());
            telemetry.addData("Distance 2", visionSubsystem.getDistance2());
            telemetry.update();
        }
    }
}
