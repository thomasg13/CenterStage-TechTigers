package org.firstinspires.ftc.teamcode.drive.rr.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;

@Autonomous(group = "drive")
public class MyOpMode extends LinearOpMode {
    @Override
    public void runOpMode()  throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());


        Trajectory trajectoryForward = drive.trajectoryBuilder(new Pose2d())
                .forward(10)
                .build();
        Trajectory trajectoryStrafeRight = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(10)
                .build();

        Trajectory trajectoryStrafeLeft = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(10)
                .build();
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            drive.followTrajectory(trajectoryForward);
            drive.followTrajectory(trajectoryStrafeLeft);
            drive.followTrajectory((trajectoryForward));
            drive.followTrajectory(trajectoryStrafeRight);
            drive.followTrajectory(trajectoryForward);
            
        }
    }
}
