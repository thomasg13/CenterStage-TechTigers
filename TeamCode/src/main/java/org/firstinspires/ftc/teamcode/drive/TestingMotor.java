package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp(name = "motor test")
public class TestingMotor extends LinearOpMode {
    private DcMotor lift = null;
    private DcMotor lift2 = null;
    public static int targetPosition = 0;
    public static double power = 0.1;
    private static double holdPower = 0.1;
    boolean rightBumperPressed = false;
    boolean leftBumperPressed = false;

    @Override
    public void runOpMode() {
        lift = hardwareMap.get(DcMotor.class, "RotateMotor");
        lift2 = hardwareMap.get(DcMotor.class, "RotateMotor2");

        // Set the motor to brake when power is zero to hold position
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //lift2.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reset the encoder and set the motor to use the encoder
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {
                // Monitor the motor's progress
                lift.setTargetPosition(targetPosition);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(power);
                //lift2.setTargetPosition(targetPosition);
                //lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //lift2.setPower(power);

                if (gamepad2.right_bumper) {
                    if (!rightBumperPressed) {
                        targetPosition += 50;
                        rightBumperPressed = true;
                    }
                } else {
                    rightBumperPressed = false;
                }

                if (gamepad2.left_bumper) {
                    if (!leftBumperPressed) {
                        targetPosition -= 50;
                        leftBumperPressed = true;
                    }
                } else {
                    leftBumperPressed = false;
                }

                telemetry.addData("Current Position 1", lift.getCurrentPosition());
                telemetry.addData("Target Position 1", lift.getTargetPosition());
                telemetry.addData("Current Position 2", lift2.getCurrentPosition());
                telemetry.addData("Target Position 2", lift2.getTargetPosition());
                telemetry.update();

                // Check if the motor has reached the target position
                /*
                if (Math.abs(lift.getCurrentPosition() - targetPosition) < 10) {
                    // Keep the motor powered to hold position
                    lift.setPower(holdPower); // You can adjust this power to a suitable holding power
                }

                 */
            }
        }
    }
}
