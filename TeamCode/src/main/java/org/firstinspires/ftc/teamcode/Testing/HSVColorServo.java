package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorSensorHSV;
import org.firstinspires.ftc.teamcode.Mechanisms.TestBranchServo;

//@TeleOp

@Disabled
public class HSVColorServo extends LinearOpMode {

    ColorSensorHSV sensor = new ColorSensorHSV();
    ColorSensorHSV.DetectedColor DetectedColor;
    TestBranchServo servo = new TestBranchServo();

    @Override
    public void runOpMode() throws InterruptedException {
        sensor.initialize(hardwareMap);
        servo.init(hardwareMap);

        servo.setRot(0);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            sensor.getHSV();

            telemetry.addData("Hue", sensor.getHue());
            DetectedColor = sensor.getDetectedColor(telemetry);
            telemetry.addData("DETECTED COLOR", DetectedColor);

            if (DetectedColor == ColorSensorHSV.DetectedColor.PURPLE) {
                servo.setRot(1);
                telemetry.addLine("COLOR == PURPLE, MOVING TO SPEED 1");
            } else if (DetectedColor == ColorSensorHSV.DetectedColor.GREEN) {
                servo.setRot(0);
                telemetry.addLine("COLOR == GREEN, MOVING TO SPEED 0");
            } else if (DetectedColor == ColorSensorHSV.DetectedColor.UNKNOWN){
                telemetry.addLine("COLOR NOT DETECTED");
            }


            telemetry.update();


        }


    }
}
