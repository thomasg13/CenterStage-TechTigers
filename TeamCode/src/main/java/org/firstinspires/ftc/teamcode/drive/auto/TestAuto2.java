package org.firstinspires.ftc.teamcode.drive.auto;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;

@Autonomous(group = "red")
public class TestAuto2 extends LinearOpMode {//close auto

    private SampleMecanumDrive drive;
    private int teamProp;
    private Pose2d startingPose = new Pose2d(35, -62, Math.toRadians(90)); // Set your starting position

    @Override
    public void runOpMode() {
        //get team prop location
        teamProp = 1;
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPose);

        // Define your trajectories here
        Trajectory toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                .splineTo(new Vector2d(12, -34), Math.toRadians(0)) // Example waypoint
                .build();

        Trajectory toSecondWaypoint = drive.trajectoryBuilder(toFirstWaypoint.end())
                .splineTo(new Vector2d(51.75, -37.5), Math.toRadians(0))  // Another example waypoint
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
