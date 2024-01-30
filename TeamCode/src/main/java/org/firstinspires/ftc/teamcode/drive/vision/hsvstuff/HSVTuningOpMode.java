package org.firstinspires.ftc.teamcode.drive.vision.hsvstuff;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@TeleOp(name = "HSV Tuning OpMode", group = "Tuning")
public class HSVTuningOpMode extends LinearOpMode {
    OpenCvCamera webcam;
    TuningPipeline tuningPipeline;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        tuningPipeline = new TuningPipeline();
        webcam.setPipeline(tuningPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Average HSV Zone 1", tuningPipeline.avgHSV1);
            telemetry.addData("Average HSV Zone 2", tuningPipeline.avgHSV2);
            telemetry.update();
        }
    }

    static class TuningPipeline extends OpenCvPipeline {
        Scalar avgHSV1 = new Scalar(0,0,0);
        Scalar avgHSV2 = new Scalar(0,0,0);

        @Override
        public Mat processFrame(Mat input) {
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            Rect zone1 = new Rect(300, 75, 150, 150);
            Rect zone2 = new Rect(900, 100, 150, 150);

            avgHSV1 = Core.mean(hsv.submat(zone1));
            avgHSV2 = Core.mean(hsv.submat(zone2));

            Imgproc.rectangle(input, zone1, new Scalar(0, 255, 0), 2);
            Imgproc.rectangle(input, zone2, new Scalar(0, 255, 0), 2);

            return input;
        }
    }
}
