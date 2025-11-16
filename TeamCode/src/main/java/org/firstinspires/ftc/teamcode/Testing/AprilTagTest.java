package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class AprilTagTest extends LinearOpMode {

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void runOpMode() throws InterruptedException {

        aprilTagWebcam.init(hardwareMap, telemetry);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            aprilTagWebcam.update();

            telemetry.addLine("SELECT YOUR DESIRED TAG");
            telemetry.addLine("gamepad1 a == tag 21");
            telemetry.addLine("gamepad1 b == tag 22");
            telemetry.addLine("gamepad1 x == tag 23");

            if (gamepad1.a) {
                AprilTagDetection id21 = aprilTagWebcam.getTagBySpecificId(21);
                aprilTagWebcam.displayDetectionTelemetry(id21);

            } else if (gamepad1.b) {
                AprilTagDetection id22 = aprilTagWebcam.getTagBySpecificId(22);
                aprilTagWebcam.displayDetectionTelemetry(id22);

            } else if (gamepad1.x) {
                AprilTagDetection id23 = aprilTagWebcam.getTagBySpecificId(23);
                aprilTagWebcam.displayDetectionTelemetry(id23);

            } else if (gamepad1.y) {
                telemetry.addLine("== Detected April Tags ==");
                for (AprilTagDetection detection : aprilTagWebcam.getDetectedTags()) {
                    telemetry.addData("Tag ID", detection.id);
                    telemetry.addData("Centro", "(%.0f, %.0f)", detection.center.x, detection.center.y);
                }
            }

            telemetry.update();
        }

        aprilTagWebcam.stop();
    }
}
