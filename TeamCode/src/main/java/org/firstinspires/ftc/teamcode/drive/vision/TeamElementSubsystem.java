package org.firstinspires.ftc.teamcode.drive.vision;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import org.firstinspires.ftc.teamcode.drive.vision.SplitAveragePipeline;

public class TeamElementSubsystem {
    OpenCvCamera camera;
    SplitAveragePipeline splitAveragePipeline;
    int camW = 1280;
    int camH = 720;

    int zone = 1;

    public TeamElementSubsystem(HardwareMap hardwareMap){
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));
        splitAveragePipeline = new SplitAveragePipeline();

        camera.setPipeline(splitAveragePipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(camW, camH, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
    }

    public void setAlliance(String alliance){
        splitAveragePipeline.setAlliancePipe(alliance);
    }

    public double getDistance1(){
        return splitAveragePipeline.getDistance1();
    }

    public double getDistance2(){
        return splitAveragePipeline.getDistance2();
    }

    public int elementDetection(Telemetry telemetry) {
        zone = splitAveragePipeline.get_element_zone();
        telemetry.addData("Element Zone", zone);
        return zone;
    }

    public void toggleAverageZone(){
        splitAveragePipeline.toggleAverageZonePipe();
    }

    public double getMaxDistance(){
        return splitAveragePipeline.getMaxDistance();
    }
}
