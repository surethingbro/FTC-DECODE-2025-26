package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class ColorSensorTest extends OpMode {

    ColorSensor sigmaD = new ColorSensor();

    ColorSensor.detectedColor detectedColor;


    @Override
    public void init() {

        sigmaD.initialize(hardwareMap);
    }

    @Override
    public void loop() {

        detectedColor = sigmaD.getDetectedColor(telemetry);
        telemetry.addData("Detected Color", detectedColor);
    }
}
