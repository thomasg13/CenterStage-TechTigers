package org.firstinspires.ftc.teamcode.drive;

import android.transition.Slide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(group = "MAIN")
public class Main extends LinearOpMode {
    private Servo ClawTurn = null;//TO BE CHANGED
    private Servo ClawLeft = null;//TO BE CHANGED
    private Servo ClawRight = null;//TO BE CHANGED
    private DcMotor RotateMotor = null;
    private DcMotor SlideMotor = null;

    public static int targetPosition = 0;
    public static double power = 1;
    private static double holdPower = 0.1;
    private static double rotateposition = 0;
    boolean rightBumperPressed = false;
    boolean leftBumperPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontL");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backL");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontR");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backR");

        ClawTurn = hardwareMap.get(Servo.class, "ClawTurn");
        ClawLeft = hardwareMap.get(Servo.class, "ClawLeft");
        ClawRight = hardwareMap.get(Servo.class, "ClawRight");
        RotateMotor = hardwareMap.get(DcMotor.class, "RotateMotor");
        SlideMotor = hardwareMap.get(DcMotor.class, "SlideMotor");


        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.

        RotateMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset the encoder and set the motor to use the encoder
        RotateMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RotateMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        ClawTurn.setPosition(0.1);
        ClawLeft.setPosition(0.5);
        ClawRight.setPosition(0);
        boolean leftClosed = true;
        boolean rightClosed = true;

        boolean dpadLeftPressed = false;
        boolean dpadRightPressed = false;
        boolean locked = false;
        double currentPosition = 0;
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.triangle) {
                imu.resetYaw();
            }

            // Monitor the motor's progress
            RotateMotor.setTargetPosition(targetPosition);
            RotateMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RotateMotor.setPower(power);

            SlideMotor.setPower(gamepad2.right_stick_y);

            if (gamepad1.right_bumper) {
                if (!rightBumperPressed) {
                    if(rotateposition == 0) {
                        targetPosition = 100;
                        rotateposition += 1;
                    } else if(rotateposition == 1){
                        targetPosition = 150;
                    }
                    rightBumperPressed = true;
                }
            } else {
                rightBumperPressed = false;
            }

            if (gamepad1.left_bumper) {
                if(rotateposition == 1) {
                    targetPosition = 100;
                    rotateposition -= 1;
                } else if(rotateposition == 0){
                    targetPosition = 0;
                }
                leftBumperPressed = true;
            } else {
                leftBumperPressed = false;
            }

            if(gamepad2.triangle){
                ClawTurn.setPosition(0);
            }

            if(gamepad2.cross){
                ClawTurn.setPosition(0.68);
            }

            // Handle left claw toggle
            if (gamepad2.right_bumper) {
                if (!dpadLeftPressed) {
                    if (leftClosed) {
                        ClawLeft.setPosition(0.25);
                    } else {
                        ClawLeft.setPosition(0.5);
                    }
                    leftClosed = !leftClosed;
                    dpadLeftPressed = true;
                }
            } else {
                dpadLeftPressed = false;
            }

            if (gamepad2.square) {
                locked = true;
                currentPosition = ClawTurn.getPosition(); // Lock the current position
            }

            if (gamepad2.circle) {
                locked = false; // Unlock the servo to allow movement
            }

            if(locked){
                ClawTurn.setPosition(currentPosition);
            }else{
                currentPosition = (gamepad2.left_stick_x) * 0.7;
                ClawTurn.setPosition(currentPosition);
            }

            // Handle right claw toggle
            if (gamepad2.left_bumper) {
                if (!dpadRightPressed) {
                    if (rightClosed) {
                        ClawRight.setPosition(0.25);
                    } else {
                        ClawRight.setPosition(0);
                    }
                    rightClosed = !rightClosed;
                    dpadRightPressed = true;
                }
            } else {
                dpadRightPressed = false;
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            telemetry.addData("Current Position 1", RotateMotor.getCurrentPosition());
            telemetry.addData("Target Position 1", RotateMotor.getTargetPosition());
            telemetry.update();
        }
    }
}