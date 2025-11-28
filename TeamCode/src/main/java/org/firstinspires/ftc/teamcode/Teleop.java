package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class Teleop extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;

    public DcMotor launcher;
    public DcMotor coreHex;

    public DcMotorEx intake;

    public CRServo hopper;

    private static final int bankVelocity = 1300;
    private static final int farVelocity = 1900;
    private static final int maxVelocity = 2200;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotorEx.class, "leftPar");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeftPerp");
        frontRight = hardwareMap.get(DcMotorEx.class, "right");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        launcher = hardwareMap.get(DcMotorEx.class,"launcher");
        coreHex = hardwareMap.get(DcMotorEx.class,"coreHex");

        intake = hardwareMap.get(DcMotorEx.class, "intake");

        hopper = hardwareMap.get(CRServo.class, "hopper");

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setDirection(DcMotor.Direction.REVERSE);

        coreHex.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        intake.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hopper.setPower(0);

        waitForStart();

        if (isStopRequested()) { return; }

        while ( opModeIsActive() ) {

            double  forwardPower = -gamepad1.left_stick_y;
            double lateralPower = gamepad1.left_stick_x;
            double turnPower = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(forwardPower) + Math.abs(lateralPower) + Math.abs(turnPower), 1);
            double frontLeftPower = (forwardPower + turnPower + lateralPower) / denominator;
            double frontRightPower = (forwardPower  - turnPower - lateralPower) / denominator;
            double backLeftPower = (forwardPower + turnPower - lateralPower) / denominator;
            double backRightPower = (forwardPower - turnPower + lateralPower) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            setFlywheelVelocity();

            //TODO: Change gamepad controls according to each driver's preferences
            // Manual control for the Intake
            if (gamepad1.x) {
                intake.setPower(1);

            } else if (gamepad1.b) {
                intake.setPower(0);
            } else if (gamepad1.y) {
                intake.setPower(-1);
            }

            //TODO: Change gamepad controls according to each driver's preferences
            // Manual control for the Core Hex
            if (gamepad2.a) {
                coreHex.setPower(0.5);
            } else if (gamepad2.y) {
                coreHex.setPower(-0.5);
            }

            //TODO: Change gamepad controls according to each driver's preferences
            // Manual control for the hopper's servo
            if (gamepad1.dpad_left) {
                hopper.setPower(1);
            } else if (gamepad1.dpad_right) {
                hopper.setPower(-1);
            }

            telemetry.addData("Flywheel Velocity", ((DcMotorEx) launcher).getVelocity());
            telemetry.addData("Flywheel Power", launcher.getPower());
            telemetry.update();
        }
    }

    /**
     * This if/else statement contains the controls for the flywheel, both manual and auto.
     * b and x will spin up ONLY the flywheel to the target velocity set.
     * The bumpers will activate the flywheel, Core Hex feeder, and servo to cycle a series of balls.
     */

    private void setFlywheelVelocity() {
        if (gamepad2.left_bumper) {
            farPowerAuto();
        } else if (gamepad2.right_bumper) {
            bankShotAuto();
        }  else {
            ((DcMotorEx) launcher).setVelocity(0);
            coreHex.setPower(0);
            // The check below is in place to prevent stuttering with the servo. It checks if the servo is under manual control!
            if (!gamepad1.dpad_right && !gamepad1.dpad_left) {
                hopper.setPower(0);
            }
        }
    }

    /**
     * The bank shot or near velocity is intended for launching balls touching or a few inches from the goal.
     * When running this function, the flywheel will spin up and the Core Hex will wait before balls can be fed.
     * The servo will spin until the bumper is released.
     */

    private void bankShotAuto() {
        ((DcMotorEx) launcher).setVelocity(bankVelocity);
        hopper.setPower(1);
        if (((DcMotorEx) launcher).getVelocity() >= bankVelocity - 50)  {
            coreHex.setPower(1);
        } else {
            coreHex.setPower(0);
        }
    }

    /**
     * The far power velocity is intended for launching balls a few feet from the goal. It may require adjusting the deflector.
     * When running this function, the flywheel will spin up and the Core Hex will wait before balls can be fed.
     * The servo will spin until the bumper is released.
     */
    private void farPowerAuto() {
        ((DcMotorEx) launcher).setVelocity(maxVelocity);
        hopper.setPower(1);
        if (((DcMotorEx) launcher).getVelocity() >= maxVelocity - 300) {
            coreHex.setPower(1);
        } else {
            coreHex.setPower(0);
        }
    }
}




