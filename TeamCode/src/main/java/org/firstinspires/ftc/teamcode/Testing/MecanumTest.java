package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
 
//@TeleOp

@Disabled
public class MecanumTest extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor launcher;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotorEx.class, "left");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "right");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        launcher = hardwareMap.get(DcMotorEx.class, "launcher");

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double forwardPower = -gamepad1.left_stick_y;
            double lateralPower = gamepad1.left_stick_x;
            double turnPower = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(forwardPower) + Math.abs(lateralPower) + Math.abs(turnPower), 1);
            double frontLeftPower = (forwardPower + turnPower + lateralPower) / denominator;
            double frontRightPower = (forwardPower - turnPower - lateralPower) / denominator;
            double backLeftPower = (forwardPower + turnPower - lateralPower) / denominator;
            double backRightPower = (forwardPower - turnPower + lateralPower) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            if (gamepad1.a) {
                launcher.setPower(1);
            } else if (gamepad1.b) {
                launcher.setPower(0);
            }
        }
    }
}
