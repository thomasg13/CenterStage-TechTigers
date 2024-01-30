package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.teamcode.drive.rr.DriveConstants.MOTOR_VELO_PID;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;


import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp(name = "MotorTestWithPID")
public class MotorTestWithPid extends LinearOpMode {
    private DcMotorEx lift = null;
    public static int position = 350;
    public static double power = 0.5;
    public static double P = 0.1;
    public static double I = 0.01;
    public static double D = 0.001;
    public static double F = 0.0003;

    @Override
    public void runOpMode() {
        lift = hardwareMap.get(DcMotorEx.class, "ClawLeft");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set custom PID coefficients here
        PIDFCoefficients pidNew = new PIDFCoefficients(P, I, D, F);
        lift.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidNew);

        waitForStart();

        if (opModeIsActive()) {


            while (opModeIsActive()) {
                lift.setTargetPosition(position);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(power);
                telemetry.addData("Current Position", lift.getCurrentPosition());
                telemetry.addData("Target Position", lift.getTargetPosition());
                telemetry.update();
            }
        }
    }

}
