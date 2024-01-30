package org.firstinspires.ftc.teamcode.drive.auto.old.red;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.vision.TeamElementSubsystem;

@Autonomous(name = "RedAutoTrash", group = "red")
public class RedAutoNew extends LinearOpMode {

    private SampleMecanumDrive drive;
    private int teamProp;
    private Pose2d startingPose = new Pose2d(0, 0, Math.toRadians(90)); // Set your starting position
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        //get team prop location
        TeamElementSubsystem visionSubsystem = new TeamElementSubsystem(hardwareMap);
        visionSubsystem.setAlliance("red");

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPose);

        timer.reset();
        while (opModeIsActive() && timer.seconds() < 5.0) { // 5 seconds for stabilization
            teamProp = visionSubsystem.elementDetection(telemetry);
            telemetry.addData("Detected Zone", teamProp);
            telemetry.addData("Distance 1", visionSubsystem.getDistance1());
            telemetry.addData("Distance 2", visionSubsystem.getDistance2());
            telemetry.update();
            sleep(100); // Small delay to prevent excessive processing
        }

        Trajectory toFirstWaypoint = null;
        Trajectory toSecondWaypoint = null;
        // Define your trajectories here
        switch(teamProp) {
            case 1:
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .lineToLinearHeading(new Pose2d(-9, -24, Math.toRadians(90)))
                        .build();

                break;
            case 2:
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .lineToLinearHeading(new Pose2d(0, 30, Math.toRadians(90)))
                        .build();
                        break;
            case 3:
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .lineToLinearHeading(new Pose2d(15, 24, Math.toRadians(90)))
                        .build();

                break;

            default:
                int a = 0;//remove problems
                toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                        .lineToLinearHeading(new Pose2d(0, 30, Math.toRadians(90)))
                        .build();


        }
        waitForStart();

        if (isStopRequested()) return;

        // Follow the defined trajectories
        drive.followTrajectory(toFirstWaypoint);
        //drive.followTrajectory(toSecondWaypoint);

        // Add additional autonomous actions after reaching waypoints
        // Example: scoring a game element, activating a mechanism, etc.
    }
}

