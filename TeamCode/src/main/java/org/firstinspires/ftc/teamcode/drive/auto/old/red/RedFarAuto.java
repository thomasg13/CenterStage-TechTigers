package org.firstinspires.ftc.teamcode.drive.auto.old.red;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.vision.TeamElementSubsystem;

@Autonomous(group = "red")
public class RedFarAuto extends LinearOpMode {

    private SampleMecanumDrive drive;
    private int teamProp;
    private Pose2d startingPose = new Pose2d(-36, -63.5, Math.toRadians(90)); // Set your starting position

    @Override
    public void runOpMode() {
        //get team prop location
        TeamElementSubsystem visionSubsystem = new TeamElementSubsystem(hardwareMap);
        visionSubsystem.setAlliance("red");
        teamProp = visionSubsystem.elementDetection(telemetry);
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPose);

        Trajectory toFirstWaypoint = null;
        Trajectory toSecondWaypoint = null;
        // Define your trajectories here
        switch(teamProp) {
            case 1:
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .splineTo(new Vector2d(-46, -41), Math.toRadians(0))
                        .build();

                toSecondWaypoint = drive.trajectoryBuilder(toFirstWaypoint.end())
                        .splineTo(new Vector2d(51.75, -29.5), Math.toRadians(0))
                        .build();
                break;
            case 2:
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .splineTo(new Vector2d(-36, -34), Math.toRadians(0))
                        .build();

                toSecondWaypoint = drive.trajectoryBuilder(toFirstWaypoint.end())
                        .splineTo(new Vector2d(51.75, -37.5), Math.toRadians(0))
                        .build();
                break;
            case 3:
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .splineTo(new Vector2d(-28.5, -41), Math.toRadians(0))
                        .build();

                toSecondWaypoint = drive.trajectoryBuilder(toFirstWaypoint.end())
                        .splineTo(new Vector2d(51.75, -43.5), Math.toRadians(0))
                        .build();
                break;

            default:
                int a = 0;//remove problems
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .splineTo(new Vector2d(-36, -34), Math.toRadians(0))
                        .build();

                toSecondWaypoint = drive.trajectoryBuilder(toFirstWaypoint.end())
                        .splineTo(new Vector2d(51.75, -37.5), Math.toRadians(0))
                        .build();
        }
        waitForStart();

        if (isStopRequested()) return;

        // Follow the defined trajectories
        drive.followTrajectory(toFirstWaypoint);
        drive.followTrajectory(toSecondWaypoint);

        // Add additional autonomous actions after reaching waypoints
        // Example: scoring a game element, activating a mechanism, etc.
    }
}

