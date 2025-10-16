package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorSensorHSV;

public class HSVColorServo extends LinearOpMode {

    public NormalizedColorSensor sensor;
    public ColorSensorHSV sensorHSV;

    @Override
    public void runOpMode() throws InterruptedException {

        sensor = hardwareMap.get(NormalizedColorSensor.class, "sensor");
        sensorHSV = new ColorSensorHSV(sensor);

        waitForStart();

        while (opModeIsActive()) {
            sensorHSV.getHSV();

            telemetry.addData("Hue", sensorHSV.getHue());
            telemetry.addData("Color detectado", sensorHSV.getColorName());
            telemetry.update();
        }




    }
}
