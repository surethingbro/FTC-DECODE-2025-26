package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ColorSensor {

    NormalizedColorSensor colorSensor;



    public enum detectedColor {
        PURPLE,
        GREEN,
        UNKNOWN
    }

     public void initialize(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "sigma_detector");
        colorSensor.setGain(8);
    }
    public detectedColor getDetectedColor(Telemetry telemetry) {

        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        float normRed, normGreen, normBlue;
        normRed = colors.red/ colors.alpha;
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        telemetry.addData("Red", normRed);
        telemetry.addData("Green", normGreen);
        telemetry.addData("Blue", normBlue);

        //TODO add if statements for specific colors added

        /*
        > greater than
        < less than

         */
        if (normGreen > normRed  && normBlue > normGreen) {
            return detectedColor.PURPLE;
        } else if (normGreen > normRed && normGreen > normBlue) {
            return detectedColor.GREEN;
        } else {
            return detectedColor.UNKNOWN;
        }
    }

    /*private double getEntrySensorHue()
    {
        float[] hsvValues = {0.0f, 0.0f, 0.0f};
        NormalizedRGBA normalizedColors = entryAnalogSensor.getNormalizedColors();
        Color.RGBToHSV(
                (int) (normalizedColors.red255),
                (int) (normalizedColors.green255),
                (int) (normalizedColors.blue*255),
                hsvValues);
        return hsvValue[0];
    }   //getEntrySensorHue
*/
}

