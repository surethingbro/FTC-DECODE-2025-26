package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import android.graphics.Color;

public class ColorSensorHSV {

    public NormalizedColorSensor colorSensor;
    public float[] hsv = new float[3];

    public ColorSensorHSV(NormalizedColorSensor sensor) {
        this.colorSensor = sensor;
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

    public String getColorName() {
        float hue = hsv[0];

        if (hue >= 79 && hue <= 155) {
            return "GREEN";
        } else if (hue >= 273 && hue <= 321) {
            return "PURPLE";
        } else {
            return "UNKNOWN";
        }
    }


}
