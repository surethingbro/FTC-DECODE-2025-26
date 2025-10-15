package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorSensor;
import org.firstinspires.ftc.teamcode.Mechanisms.TestBranchServo;

@TeleOp
public class ColorServo extends LinearOpMode {

    ColorSensor sensor = new ColorSensor();
    ColorSensor.detectedColor detectedColor;
    TestBranchServo servo = new TestBranchServo();

    @Override
    public void runOpMode() throws InterruptedException {

        sensor.initialize(hardwareMap);
        servo.init(hardwareMap);

        servo.setRot(0);

        if (isStopRequested()) return;

        waitForStart();

        while (opModeIsActive()) {

            detectedColor = sensor.getDetectedColor(telemetry);
            telemetry.addData("Detected Color", detectedColor);

            if (detectedColor == ColorSensor.detectedColor.PURPLE) {
                servo.setRot(1);
                telemetry.addLine("COLOR == PURPLE, MOVING TO SPEED 1");
            } else if (detectedColor == ColorSensor.detectedColor.GREEN) {
                servo.setRot(0);
                telemetry.addLine("COLOR == GREEN, MOVING TO SPEED 0");
            } else if (detectedColor == ColorSensor.detectedColor.UNKNOWN){
                telemetry.addLine("COLOR NOT DETECTED");
            }

            telemetry.update();


        }
    }
}
