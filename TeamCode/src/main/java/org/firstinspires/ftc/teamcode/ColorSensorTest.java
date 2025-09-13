package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;



public class ColorSensorTest extends OpMode {

    ColorSensor sigmaD = new ColorSensor();


    @Override
    public void init() {

        sigmaD.initialize(hardwareMap);
    }

    @Override
    public void loop() {

        sigmaD.getDetectedColor(telemetry);
    }
}
