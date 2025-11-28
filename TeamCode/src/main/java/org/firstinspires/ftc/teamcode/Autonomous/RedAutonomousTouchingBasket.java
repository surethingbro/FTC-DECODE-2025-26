package org.firstinspires.ftc.teamcode.Autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@SuppressWarnings("unused")
@Config
@Autonomous
public class RedAutonomousTouchingBasket extends LinearOpMode {

    private static final int bankVelocity = 1300;
    private static final int farVelocity = 1900;
    private static final int maxVelocity = 2200;

    public class Intake {
        /** @noinspection FieldMayBeFinal*/
        private DcMotorEx intake;

        public Intake(HardwareMap hardwareMap) {
            intake = hardwareMap.get(DcMotorEx.class, "intake");
            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            intake.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        public class IntakeOn implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    intake.setPower(1);
                    initialized = true;
                }
                return false;
            }
        }

        public Action intakeOn() {
            return new Intake.IntakeOn();
        }

        public class IntakeOff implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    intake.setPower(0);
                    initialized = true;
                }
                return false;
            }
        }
        public Action intakeOff() {
            return new Intake.IntakeOff();
        }
    }

    /** @noinspection FieldMayBeFinal*/
    public class Launcher {

        private DcMotor launcher;
        private DcMotor coreHex;
        private CRServo hopper;
        private DcMotorEx intake;

        public Launcher(HardwareMap hardwareMap) {
            launcher = hardwareMap.get(DcMotorEx.class, "launcher");
            coreHex = hardwareMap.get(DcMotorEx.class, "coreHex");
            hopper = hardwareMap.get(CRServo.class, "hopper");
            intake = hardwareMap.get(DcMotorEx.class, "intake");

            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            intake.setDirection(DcMotorSimple.Direction.REVERSE);

            launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            launcher.setDirection(DcMotor.Direction.REVERSE);

            coreHex.setDirection(DcMotor.Direction.REVERSE);
        }

        public class Launch implements Action {
            ElapsedTime timer;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (timer == null) {
                    timer = new ElapsedTime();
                }

                ((DcMotorEx) launcher).setVelocity(bankVelocity);
                hopper.setPower(1);
                intake.setPower(1);

                if (((DcMotorEx) launcher).getVelocity() >= bankVelocity - 100) {
                    coreHex.setPower(1);
                } else {
                    coreHex.setPower(0);
                }

                telemetryPacket.put("Launcher Countdown", timer.seconds());


                return timer.milliseconds() < 5500;
            }
        }
        public Action launch() {
            return new Launcher.Launch();
        }

        public class notLaunch implements Action {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                ((DcMotorEx) launcher).setVelocity(0);
                coreHex.setPower(0);
                hopper.setPower(0);
                intake.setPower(0);

                return false;
            }
        }

        public Action notlaunch() {
            return new Launcher.notLaunch();
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d initialPose = new Pose2d(-49,50, (Math.PI * 7 )/ 4); //315°

        VelConstraint slowVel = new TranslationalVelConstraint(15);
        AccelConstraint slowAccel = new ProfileAccelConstraint(-20,20);

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Intake intake = new Intake(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);

        TrajectoryActionBuilder doAuto = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-11.5,12.4, Math.toRadians(130)), Math.toRadians(130))
                .stopAndAdd(launcher.launch())
                .stopAndAdd(launcher.notlaunch())

                .turnTo(Math.toRadians(270))

                .splineToLinearHeading(new Pose2d(-1,20, Math.toRadians(270)), Math.toRadians(270))
                .stopAndAdd(intake.intakeOn())

                .lineToYLinearHeading(55 ,Math.toRadians(270), slowVel, slowAccel)
                .afterTime(3, intake.intakeOff())

                .lineToY(30)
                .splineToLinearHeading(new Pose2d(-11.5,12.4, Math.toRadians(130)), Math.toRadians(130))
                .stopAndAdd(launcher.launch())
                .stopAndAdd(launcher.notlaunch())

                .strafeTo(new Vector2d(10,20));

        waitForStart();

        if (isStopRequested()) { return; }

        Actions.runBlocking(
                new SequentialAction(
                        doAuto.build()
                )
        );

    }
}
