package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class motorTesting extends LinearOpMode {

    public DcMotor motor;
    public DcMotor motor2;

    @Override
    public void runOpMode() throws InterruptedException {

        motor = hardwareMap.get(DcMotor.class, "motor");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {



            motor.setPower(1);
            motor2.setPower(1);

        }
    }
}