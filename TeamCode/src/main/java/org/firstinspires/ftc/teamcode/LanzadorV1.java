package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class LanzadorV1 extends LinearOpMode {

    public DcMotor leftSide;
    public DcMotor rightSide;
    
    @Override
    public void runOpMode() throws InterruptedException {

        leftSide = hardwareMap.get(DcMotor.class,"left");
        rightSide = hardwareMap.get(DcMotor.class, "right");

        leftSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftSide.setDirection(DcMotorSimple.Direction.FORWARD);
        rightSide.setDirection(DcMotorSimple.Direction.REVERSE);

        leftSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {

            if (gamepad1.a) {
                leftSide.setPower(1);
                rightSide.setPower(1);
            } else if (gamepad1.b) {
                leftSide.setPower(-1);
                rightSide.setPower(-1);
            } else if (gamepad1.x) {
                leftSide.setPower(0);
                rightSide.setPower(0);
            }
        }

    }
}
