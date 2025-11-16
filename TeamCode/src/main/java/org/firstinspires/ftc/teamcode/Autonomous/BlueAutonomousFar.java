package org.firstinspires.ftc.teamcode.Autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
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
public class BlueAutonomousFar extends LinearOpMode {

    private static final int maxVelocity = 2200;
    public class Intake {
        private DcMotorEx intake;

        public Intake(HardwareMap hardwareMap) {
            intake = hardwareMap.get(DcMotorEx.class, "intake");
            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            intake.setDirection(DcMotorSimple.Direction.FORWARD);
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
            return new IntakeOn();
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
            return new IntakeOff();
        }
    }

    public class Launcher {
        private DcMotorEx launcher;
        private DcMotorEx coreHex;
        private CRServo hopper;

        public Launcher(HardwareMap hardwareMap) {
            launcher = hardwareMap.get(DcMotorEx.class, "launcher");
            coreHex = hardwareMap.get(DcMotorEx.class, "corehex");
            hopper = hardwareMap.get(CRServo.class, "hopper");
        }

        public class Launch implements Action {
            ElapsedTime timer;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (timer == null) {
                    timer = new ElapsedTime();

                    launcher.setVelocity(maxVelocity);
                    hopper.setPower(-1);
                }

                if (launcher.getVelocity() >= maxVelocity - 100) {
                    coreHex.setPower(1);
                } else {
                    coreHex.setPower(0);
                }

                return timer.seconds() < 5;
            }
        }
        public Action launch() {
            return new Launch();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(63, -14, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Intake intake = new Intake(hardwareMap);
        Launcher launcher = new Launcher(hardwareMap);


        waitForStart();

        if (isStopRequested()) { return; }

        Actions.runBlocking(
                new SequentialAction(drive.actionBuilder(initialPose)
                        .splineTo(new Vector2d(53,-10), Math.toRadians(210))
                        .afterTime(0.5, launcher.launch())
                        .build())

        );


    }
}