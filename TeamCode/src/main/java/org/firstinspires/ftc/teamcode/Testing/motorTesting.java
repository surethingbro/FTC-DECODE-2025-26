package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class motorTesting extends LinearOpMode {

    public DcMotor motor;
    public DcMotor motor2;
    public DcMotor motor3;
    public DcMotor motor4;


    @Override
    public void runOpMode() throws InterruptedException {

        motor = hardwareMap.get(DcMotor.class, "motor");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor3= hardwareMap.get(DcMotor.class, "motor3");
        motor4= hardwareMap.get(DcMotor.class, "motor4");


        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double forwardPower = gamepad1.left_stick_y;


            motor.setPower(forwardPower);
            motor2.setPower(forwardPower);
            motor3.setPower(forwardPower);
            motor4.setPower(forwardPower);

        }
    }
}