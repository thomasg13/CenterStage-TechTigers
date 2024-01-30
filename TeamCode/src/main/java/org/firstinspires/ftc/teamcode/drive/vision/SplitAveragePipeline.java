package org.firstinspires.ftc.teamcode.drive.vision;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.Arrays;
import java.util.List;

@Config
public class SplitAveragePipeline extends OpenCvPipeline {

    List<Integer> ELEMENT_COLOR = Arrays.asList(255, 0, 0); //(red, green, blue)

    //Telemetry telemetry;

    static int color_zone = 1;

    int toggleShow = 1;

    Mat original;

    Mat zone1;
    Mat zone2;

    Scalar avgColor1;
    Scalar avgColor2;

    double distance1 = 1;
    double distance2 = 1;

    double max_distance = 0;
    public static int x1 = 200;
    public static int y1 = 100;
    public static int width1 = 250;
    public static int height1 = 150;
    public static int x2 = 800;
    public static int y2 = 75;
    public static int width2 = 200;
    public static int height2 = 200;

    @Override
    public Mat processFrame(Mat input) {

        //Creating duplicate of original frame with no edits
        original = input.clone();

        //input = input.submat(new Rect(0));

        //Defining Zones
        //Rect(top left x, top left y, bottom right x, bottom right y)
        zone2 = input.submat(new Rect(x1, y1, width1, height1));
        zone1 = input.submat(new Rect(x2, y2, width2, height2));

        Rect rectZone1 = new Rect(60, 170, 356, 285);
        Rect rectZone2 = new Rect(735, 170, 253, 230);

        // Draw rectangles on the frame
        //Scalar rectColor = new Scalar(0, 255, 0); // Green color for the rectangle
        //Imgproc.rectangle(input, rectZone1, rectColor, 2); // Draw rect for Zone 1
        //Imgproc.rectangle(input, rectZone2, rectColor, 2); // Draw rect for Zone 2



        //Averaging the colors in the zones
        avgColor1 = Core.mean(zone1);
        avgColor2 = Core.mean(zone2);

        //Putting averaged colors on zones (we can see on camera now)
        zone1.setTo(avgColor1);
        zone2.setTo(avgColor2);

        distance1 = color_distance(avgColor1, ELEMENT_COLOR);
        distance2 = color_distance(avgColor2, ELEMENT_COLOR);

        if ((distance1 > 195) && (distance2 > 190)){
            color_zone = 3;
            max_distance = -1;
        }else{
            max_distance = Math.min(distance1, distance2);

            if (max_distance == distance1) {
                //telemetry.addData("Zone 1 Has Element", distance1);
                color_zone = 1;

            }else{
                //telemetry.addData("Zone 2 Has Element", distance2);
                color_zone = 2;
            }
        }

        // Allowing for the showing of the averages on the stream
        if (toggleShow == 1){
            return input;
        }else{
            return original;
        }
    }

    public double getDistance1() {
        return distance1;
    }

    public double getDistance2() {
        return distance2;
    }

    public double color_distance(Scalar color1, List color2){
        double r1 = color1.val[0];
        double g1 = color1.val[1];
        double b1 = color1.val[2];

        int r2 = (int) color2.get(0);
        int g2 = (int) color2.get(1);
        int b2 = (int) color2.get(2);

        return Math.sqrt(Math.pow((r1 - r2), 2) + Math.pow((g1 - g2), 2) + Math.pow((b1 - b2), 2));
    }

    public void setAlliancePipe(String alliance){
        if (alliance.equals("red")){
            ELEMENT_COLOR = Arrays.asList(255, 0, 0);
        }else{
            ELEMENT_COLOR = Arrays.asList(0, 0, 255);
        }
    }

    public int get_element_zone(){
        return color_zone;
    }

    public double getMaxDistance(){
        return max_distance;
    }

    public void toggleAverageZonePipe(){
        toggleShow = toggleShow * -1;
    }

}