package org.firstinspires.ftc.teamcode.drive.vision.hsvstuff;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name="TestPipeline Test", group="Test")
public class TestPipelineOpMode extends LinearOpMode {

    private OpenCvWebcam webcam;
    private TestPipeline testPipeline;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        testPipeline = new TestPipeline(1);
        webcam.setPipeline(testPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", "Camera could not be opened");
                telemetry.update();
            }
        });

        waitForStart();

        while (opModeIsActive()) {
            String location = testPipeline.getLocation();
            Scalar avgHSV1 = testPipeline.getAvgHSV1();
            Scalar avgHSV2 = testPipeline.getAvgHSV2();

            telemetry.addData("Location Detected", location);
            telemetry.addData("Avg HSV1", avgHSV1.toString());
            telemetry.addData("Avg HSV2", avgHSV2.toString());
            telemetry.update();
        }

        webcam.stopStreaming();
    }
}
