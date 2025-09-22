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
        colorSensor.setGain(4);
    }

    public detectedColor getDetectedColor(Telemetry telemetry) {

        NormalizedRGBA colors = colorSensor.getNormalizedColors(); //this will return 4 values, red, green, blue, and alpha

        float normRed, normGreen, normBlue;
        normRed = colors.red/ colors.alpha;
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        telemetry.addData("Red", normRed);
        telemetry.addData("Green", normGreen);
        telemetry.addData("Blue", normBlue);

        //TODO add if statements for specific colors added


        /*
        red,green,blue
        RED =
        YELLOW = <0.47, <.12, <.13
        BLUE =
         */

        if (normRed > 0.35 && normGreen < 0.3 && normBlue < 0.3) {
            return detectedColor.RED;
        } else if (normRed < 0.47 && normGreen < 0.12 && normBlue < 0.13) {
            return detectedColor.YELLOW;
        } else if (normRed < 0.2 && normGreen < 0.5 && normBlue > 0.5) {
            return detectedColor.BLUE;
        }
        return detectedColor.UNKNOWN;
    }

}

