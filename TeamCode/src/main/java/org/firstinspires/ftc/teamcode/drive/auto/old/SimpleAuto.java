package org.firstinspires.ftc.teamcode.drive.auto.old;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;

@Autonomous(group = "drive")
public class SimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        // Spline to a point
        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(30, 0), Math.toRadians(220))
                .build();

        // Forward movement
        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .forward(35)
                .build();

        // Backward movement
        Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                .back(15)
                .build();

        // Strafe left
        Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                .strafeLeft(40)
                .build();

        // Strafe right
        Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                .strafeRight(35)
                .build();

        // Spline back to start and rotate 180 degrees
        Trajectory traj6 = drive.trajectoryBuilder(traj5.end(), true)
                .splineTo(new Vector2d(0, 0), Math.toRadians(180))
                .build();

        // Execute trajectories
        drive.followTrajectory(traj1);
        sleep(1000); // Wait for 1 second
        drive.followTrajectory(traj2);
        sleep(1000); // Wait for 1 second
        drive.followTrajectory(traj3);
        sleep(1000); // Wait for 1 second
        //drive.followTrajectory(traj4);
        //sleep(1000); // Wait for 1 second
        drive.followTrajectory(traj5);
        sleep(1000); // Wait for 1 second
        //drive.followTrajectory(traj6);
    }
}
