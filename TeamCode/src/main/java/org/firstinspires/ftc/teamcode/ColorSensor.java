package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensor {

    NormalizedColorSensor colorSensor;

    public enum detectedColor {
        RED,
        BLUE,
        YELLOW,
        UNKNOWN
    }

     public void initialize(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "sigma_detector");
    }

    public detectedColor getDetectedColor(Telemetry telemetry) {

        NormalizedRGBA colors = colorSensor.getNormalizedColors(); //this will return 4 values, red, green, blue, and alpha

        float normRed, normGreen, normBlue;
        normRed = colors.red/ colors.alpha;
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        telemetry.addData("Red", normRed);
        telemetry.addData("green", normGreen);
        telemetry.addData("blue", normBlue);

        //TODO add if statements for specific colors added

        /*
        red,green,blue

        RED =
        GREEN =
        BLUE =
         */

        return detectedColor.UNKNOWN;
    }

}

