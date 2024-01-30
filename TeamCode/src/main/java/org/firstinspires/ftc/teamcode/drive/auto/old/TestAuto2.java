package org.firstinspires.ftc.teamcode.drive.auto.old;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;

@Autonomous(group = "red")
public class TestAuto2 extends LinearOpMode {//far auto

    private SampleMecanumDrive drive;
    private int teamProp;
    private Pose2d startingPose = new Pose2d(12.5, -63.5, Math.toRadians(90)); // Set your starting position

    @Override
    public void runOpMode() {
        //get team prop location
        teamProp = 1;
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPose);

        // Define your trajectories here
        Trajectory toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                .lineToLinearHeading(new Pose2d(50, -28.5, Math.toRadians(0)))
                //.splineTo(new Vector2d(50, -28.5), Math.toRadians(0))
                .build();


        Trajectory toSecondWaypoint = drive.trajectoryBuilder(toFirstWaypoint.end())
                .lineToLinearHeading(new Pose2d(-12.5, -63.5, Math.toRadians(0)))
                //.splineTo(new Vector2d(-36, -63.5), Math.toRadians(0))
                .build();


        Trajectory toThirdWaypoint = drive.trajectoryBuilder(toSecondWaypoint.end())
                .lineToLinearHeading(new Pose2d(50, -43.5, Math.toRadians(0)))
                //.splineTo(new Vector2d(50, -43.5), Math.toRadians(0))
                .build();

        waitForStart();

        if (isStopRequested()) return;

        // Follow the defined trajectories
        drive.followTrajectory(toFirstWaypoint);
        drive.followTrajectory(toSecondWaypoint);
        drive.followTrajectory(toThirdWaypoint);

        // Add additional autonomous actions after reaching waypoints
        // Example: scoring a game element, activating a mechanism, etc.
    }
}
