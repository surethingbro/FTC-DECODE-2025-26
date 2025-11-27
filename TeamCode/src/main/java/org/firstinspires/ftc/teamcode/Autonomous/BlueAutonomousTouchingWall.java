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

@Config
@Autonomous
@SuppressWarnings("unused")
public class BlueAutonomousTouchingWall extends LinearOpMode {
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
                    intake.setPower(0.8)    ;
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

                ((DcMotorEx) launcher).setVelocity(maxVelocity);
                hopper.setPower(1);
                intake.setPower(0.8);

                if (((DcMotorEx) launcher).getVelocity() >= maxVelocity - 300 ) {
                    coreHex.setPower(1);
                } else {
                    coreHex.setPower(0);
                }

                telemetryPacket.put("Launcher Countdown", timer.seconds());


                return timer.milliseconds() < 10000;
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

        Pose2d initialPose = new Pose2d(61,-22, 0);

        VelConstraint slowVel = new TranslationalVelConstraint(15);
        AccelConstraint slowAccel = new ProfileAccelConstraint(-20,20);


        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Intake intake = new Intake(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);

        TrajectoryActionBuilder doAuto = drive.actionBuilder(initialPose)
                .setReversed(true)
                .splineTo(new Vector2d(55,-10), Math.toRadians(25))
                .stopAndAdd(launcher.launch())
                .stopAndAdd(launcher.notlaunch())
                .splineToSplineHeading(new Pose2d(46,-23,Math.toRadians(0)), Math.toRadians(0));


        waitForStart();

        if (isStopRequested()) {return;}

        Actions.runBlocking(
                new SequentialAction(
                        doAuto.build()
                )
        );



    }
}
