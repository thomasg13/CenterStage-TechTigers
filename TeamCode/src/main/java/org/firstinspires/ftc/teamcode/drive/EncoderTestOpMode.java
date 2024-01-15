package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Encoder Test", group = "Test")
public class EncoderTestOpMode extends LinearOpMode {

    private DcMotor testMotor;

    @Override
    public void runOpMode() {
        // Initialize your motor. Replace "motorName" with your motor's configuration name.
        testMotor = hardwareMap.get(DcMotor.class, "backR");

        // Optionally, set the motor to run using its encoder
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for start command
        waitForStart();

        while (opModeIsActive()) {
            // Display encoder value
            telemetry.addData("Encoder Position", testMotor.getCurrentPosition());
            telemetry.update();

            // Optional: add simple motor controls to manually test motor movement
            if (gamepad1.a) {
                testMotor.setPower(0.5); // move motor at 50% power
            } else {
                testMotor.setPower(0); // stop motor
            }
        }
    }
}
