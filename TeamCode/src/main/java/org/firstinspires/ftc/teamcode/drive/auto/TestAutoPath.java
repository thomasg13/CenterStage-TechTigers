package org.firstinspires.ftc.teamcode.drive.auto;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.vision.TeamElementSubsystem;

@Config
@Autonomous(group = "MAIN")
public class TestAutoPath extends LinearOpMode {

    private SampleMecanumDrive drive;
    private int teamProp;
    private Servo ClawTurn = null;//TO BE CHANGED
    private Servo ClawLeft = null;//TO BE CHANGED
    private Servo ClawRight = null;//TO BE CHANGED
    private DcMotor RotateMotor = null;
    private DcMotor SlideMotor = null;
    private Pose2d startingPose = new Pose2d(0, 0, Math.toRadians(90)); // Set your starting position

    private ElapsedTime timer = new ElapsedTime();

    public static double distance = 1;//should be 15
    public static double turnAngle = 0;
    @Override
    public void runOpMode() {
        //get team prop location
        TeamElementSubsystem visionSubsystem = new TeamElementSubsystem(hardwareMap);
        visionSubsystem.setAlliance("red");

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPose);

        ClawTurn = hardwareMap.get(Servo.class, "ClawTurn");
        ClawLeft = hardwareMap.get(Servo.class, "ClawLeft");
        ClawRight = hardwareMap.get(Servo.class, "ClawRight");
        RotateMotor = hardwareMap.get(DcMotor.class, "RotateMotor");
        SlideMotor = hardwareMap.get(DcMotor.class, "SlideMotor");

        waitForStart();

        ClawTurn.setPosition(0);
        ClawLeft.setPosition(0.75);
        ClawRight.setPosition(0.25);

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
        Trajectory toSecondWayPoint = null;

        switch(teamProp) {//distance 10.5 for forward
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;

            default:
        }

        Trajectory toDropPixelPosition = null;
        Trajectory pathingToStage = null;
        //Trajectory toStagePosition = null;
        Trajectory toParkPosition = null;

        toFirstWaypoint = drive.trajectoryBuilder(startingPose)
                .lineToLinearHeading(new Pose2d(0, distance, Math.toRadians(90)))
                .build();

        if (isStopRequested()) return;

        // Follow the defined trajectories
        ClawTurn.setPosition(0.6);
        sleep(500);
        drive.followTrajectory(toFirstWaypoint);
        sleep(500);

        drive.turn(Math.toRadians(turnAngle));

        sleep(500);

        ClawTurn.setPosition(0.7);
        sleep(500);

        ClawLeft.setPosition(1);
        sleep(500);
        ClawTurn.setPosition(0.65);
        sleep(100);
        ClawTurn.setPosition(0.55);
        sleep(100);
        ClawTurn.setPosition(0);

        sleep(500);
        //DRIVE BACK FOR HEADING PURPOSES

        while(opModeIsActive()){
            ClawTurn.setPosition(0);
            ClawLeft.setPosition(1);
            ClawRight.setPosition(0.25);
        }
    }
}

