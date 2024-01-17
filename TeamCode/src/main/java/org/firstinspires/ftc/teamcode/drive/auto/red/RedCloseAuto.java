package org.firstinspires.ftc.teamcode.drive.auto.red;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "red")
public class RedCloseAuto extends LinearOpMode {

    private SampleMecanumDrive drive;
    private int teamProp;
    private Pose2d startingPose = new Pose2d(-39, -62, Math.toRadians(90)); // Set your starting position

    @Override
    public void runOpMode() {
        //get team prop location
        teamProp = 1;
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPose);

        // Define your trajectories here
        Trajectory toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                .splineTo(new Vector2d(-46, -41), Math.toRadians(0)) // Example waypoint
                .build();

        Trajectory toSecondWaypoint = drive.trajectoryBuilder(toFirstWaypoint.end())
                .splineTo(new Vector2d(51.75, -29.5), Math.toRadians(0))  // Another example waypoint
                .build();

        waitForStart();

        if (isStopRequested()) return;

        // Follow the defined trajectories
        drive.followTrajectory(toFirstWaypoint);
        drive.followTrajectory(toSecondWaypoint);

        // Add additional autonomous actions after reaching waypoints
        // Example: scoring a game element, activating a mechanism, etc.
    }
}

