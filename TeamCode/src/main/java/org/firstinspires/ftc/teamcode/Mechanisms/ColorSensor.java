package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

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
        colorSensor.setGain(8);
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
        > mayor que
        < menor que

         */
        if (normRed > normBlue && normRed > normGreen) {
            return detectedColor.RED;
        } else if (normBlue > normRed && normBlue > normGreen) {
            return detectedColor.BLUE;
        } else if (normRed > normBlue && normGreen > normBlue) {
            return detectedColor.YELLOW;
        } else {
            return detectedColor.UNKNOWN;
        }
    }

}

