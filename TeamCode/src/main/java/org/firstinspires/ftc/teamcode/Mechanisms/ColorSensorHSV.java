package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensorHSV {

    public NormalizedColorSensor colorSensor;
    public float[] hsv = new float[3];


    public void initialize(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "sensor");
        colorSensor.setGain(8);
    }

    public enum DetectedColor {
        PURPLE,
        GREEN,
        UNKNOWN
    }

    public void getHSV() {

        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        Color.RGBToHSV(
                (int) (colors.red * 255),
                (int) (colors.green * 255),
                (int) (colors.blue * 255),
                hsv
        );

    }

    public float getHue() {
        return hsv[0];
    }

    public DetectedColor getDetectedColor(Telemetry telemetry) {
        float hue = hsv[0];

        telemetry.addData("H", hue);

        if (hue >= 79 && hue <= 155) {
            return DetectedColor.GREEN;
        } else if (hue >= 120 && hue <= 240) {
            return DetectedColor.PURPLE;
        } else {
            return DetectedColor.UNKNOWN;
        }
    }
}
