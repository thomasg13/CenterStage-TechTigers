package org.firstinspires.ftc.teamcode.drive.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;

@Autonomous(group = "drive")
public class ImprovedSimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Define your initial pose here (change x, y, heading as needed)
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;

        // Define trajectories
        Trajectory traj1 = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(30, 0), Math.toRadians(220))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .forward(35)
                .build();

        Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                .back(15)
                .build();

        Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                .strafeLeft(40)
                .build();

        Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                .strafeRight(35)
                .build();

        // Spline back to start and rotate to face forward
        Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
                .splineTo(new Vector2d(0, 0), Math.toRadians(0))
                .build();

        // Execute trajectories
        drive.followTrajectory(traj1);
        drive.followTrajectory(traj2);
        drive.followTrajectory(traj3);
        drive.followTrajectory(traj4);
        drive.followTrajectory(traj5);
        drive.followTrajectory(traj6);
    }

}
