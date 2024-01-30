package org.firstinspires.ftc.teamcode.drive.vision.hsvstuff;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class TestPipeline extends OpenCvPipeline {
    private String location = "nothing"; // output
    public Scalar lower = new Scalar(70, 120, 125); // HSV threshold bounds
    public Scalar upper = new Scalar(110, 150, 180);

    private Scalar avgHSV1 = new Scalar(0, 0, 0);
    private Scalar avgHSV2 = new Scalar(0, 0, 0);

    private Mat hsvMat = new Mat(); // converted image
    private Mat binaryMat = new Mat(); // imamge analyzed after thresholding
    private Mat maskedInputMat = new Mat();

    // Rectangle regions to be scanned
    private Point topLeft1 = new Point(300, 75), bottomRight1 = new Point(450, 225);
    private Point topLeft2 = new Point(900, 100), bottomRight2 = new Point(1050, 250);

    public TestPipeline(int color) {//if 0 red, if 1 blue
        if(color == 0){
            lower = new Scalar(70, 0, 0);
            upper = new Scalar(110, 255, 255);
        }else if(color == 1){
            lower = new Scalar(110, 0, 0);
            upper = new Scalar(150, 255, 255);
        }else{//wrong, default is red
            lower = new Scalar(110, 0, 0);
            upper = new Scalar(150, 255, 255);
        }
    }

    @Override
    public Mat processFrame(Mat input) {
        // Convert from BGR to HSV
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsvMat, lower, upper, binaryMat);

        // Calculate average HSV values for the rectangles
        avgHSV1 = Core.mean(hsvMat.submat(new Rect(topLeft1, bottomRight1)));
        avgHSV2 = Core.mean(hsvMat.submat(new Rect(topLeft2, bottomRight2)));


        // Draw outlined rectangles on the input image for visual feedback
        Scalar rectangleColor = new Scalar(0, 255, 0); // Green color for the rectangle
        int thickness = 2; // Thickness of the rectangle outline

        Imgproc.rectangle(input, new Rect(topLeft1, bottomRight1), rectangleColor, thickness);
        Imgproc.rectangle(input, new Rect(topLeft2, bottomRight2), rectangleColor, thickness);

        // Return the original input frame with drawn rectangles
        return input;
    }



    public Scalar getAvgHSV1() {
        return avgHSV1;
    }

    public Scalar getAvgHSV2() {
        return avgHSV2;
    }

    public String getLocation(){
        // Determine the location based on the presence of the object in each zone
        double threshold = 0.9; // Example threshold value, adjust based on testing

        boolean objectInZone1 = (avgHSV1.val[0] >= lower.val[0] && avgHSV1.val[0] <= upper.val[0]) &&
                (avgHSV1.val[1] >= lower.val[1] && avgHSV1.val[1] <= upper.val[1]) &&
                (avgHSV1.val[2] >= lower.val[2] && avgHSV1.val[2] <= upper.val[2]);

        boolean objectInZone2 = (avgHSV2.val[0] >= lower.val[0] && avgHSV2.val[0] <= upper.val[0]) &&
                (avgHSV2.val[1] >= lower.val[1] && avgHSV2.val[1] <= upper.val[1]) &&
                (avgHSV2.val[2] >= lower.val[2] && avgHSV2.val[2] <= upper.val[2]);


        if (objectInZone1 && !objectInZone2) {
            location = "1"; // Object in first zone
        } else if (!objectInZone1 && objectInZone2) {
            location = "2"; // Object in second zone
        } else if(objectInZone1 && objectInZone2) {
            location = "both zones"; // Object not found in any zone or in both
        }else{
            location = "3"; // Object not found in either zone
        }

        return location;
    }
}