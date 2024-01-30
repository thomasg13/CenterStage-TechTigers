package org.firstinspires.ftc.teamcode.drive.auto.old.blue;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.rr.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.vision.TeamElementSubsystem;

@Autonomous(group = "red")
public class SimpleBlueFarAuto extends LinearOpMode {

    private SampleMecanumDrive drive;
    private int teamProp;
    private Servo IntakeServo = null;
    private DcMotor IntakeMotor = null;
    private DcMotor LiftLeft = null;
    private DcMotor LiftRight = null;
    private Servo ClawServoLeft = null;
    private Servo ClawServoRight = null;
    private Servo BoxServo = null;
    private Pose2d startingPose = new Pose2d(-36, 63.5, Math.toRadians(270)); // Set your starting position

    private ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() {
        //get team prop location
        TeamElementSubsystem visionSubsystem = new TeamElementSubsystem(hardwareMap);
        visionSubsystem.setAlliance("blue");

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startingPose);

        IntakeServo = hardwareMap.get(Servo.class, "IntakeServo");
        IntakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        LiftLeft = hardwareMap.get(DcMotor.class, "ClawLeft");
        LiftRight = hardwareMap.get(DcMotor.class, "ClawRight");
        ClawServoLeft = hardwareMap.get(Servo.class, "ClawServoLeft");
        ClawServoRight = hardwareMap.get(Servo.class, "ClawServoRight");
        BoxServo = hardwareMap.get(Servo.class, "BoxServo");

        waitForStart();

        while (opModeIsActive() && timer.seconds() < 5.0) { // 5 seconds for stabilization
            teamProp = visionSubsystem.elementDetection(telemetry);
            telemetry.addData("Detected Zone", teamProp);
            telemetry.addData("Distance 1", visionSubsystem.getDistance1());
            telemetry.addData("Distance 2", visionSubsystem.getDistance2());
            telemetry.update();
            sleep(100); // Small delay to prevent excessive processing
        }



        Trajectory toDropPixelPosition = null;
        Trajectory pathingToStage = null;
        //Trajectory toStagePosition = null;
        Trajectory toParkPosition = null;
        // Define your trajectories here




        switch (teamProp) {//place pixel on ground then go to wall
            case 1:
                toDropPixelPosition = drive.trajectoryBuilder(startingPose)
                        .forward(24)
                        .strafeLeft(9)
                        .build();
                break;
            case 2:
                toDropPixelPosition = drive.trajectoryBuilder(startingPose)
                        .forward(30)
                        .build();
                break;
            case 3:
                toDropPixelPosition = drive.trajectoryBuilder(startingPose)
                        .forward(24)
                        .strafeRight(15)
                        .build();
                break;
            default:
                int a = 0;//remove problems
                toDropPixelPosition = drive.trajectoryBuilder(startingPose)
                        .forward(30)
                        .build();
                break;

        }
        pathingToStage = drive.trajectoryBuilder(toDropPixelPosition.end())
                .strafeLeft(12)
                .lineToLinearHeading(new Pose2d(-48, 12, Math.toRadians(270)))
                .build();

        toParkPosition = drive.trajectoryBuilder(pathingToStage.end())
                .splineTo(new Vector2d(60, 12), Math.toRadians(180))
                .build();




        if (isStopRequested()) return;

        // Follow the defined trajectories
        IntakeServo.setPosition(0.41);
        sleep(500);
        drive.followTrajectory(toDropPixelPosition);
        sleep(500);
        IntakeServo.setPosition(1);
        sleep(500);
        drive.followTrajectory(pathingToStage);
        //drive.followTrajectory(toStagePosition);
        drive.followTrajectory(toParkPosition);
        //maybe go forward after this


        //placing pixel on stage
        /*
        timer.reset();
        while(timer.seconds() < 1){
            LiftLeft.setPower(-0.5);
            LiftRight.setPower(0.5);
        }
        LiftLeft.setPower(0);
        LiftRight.setPower(0);

        ClawServoLeft.setPosition(0.75);//change later
        ClawServoRight.setPosition(0.75);

        //open the other servo, need to initialize it as well
        BoxServo.setPosition(0.8);//random at this moment
        sleep(500);
        BoxServo.setPosition(1);//may need to tweak
        */
        //parking

        // Add additional autonomous actions after reaching waypoints
    }
}

