package org.firstinspires.ftc.teamcode.Testing;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "April Tag Detector")
public class ATVision extends LinearOpMode {

    public DcMotor leftSide;
    public DcMotor rightSide;

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {

        leftSide = hardwareMap.get(DcMotor.class,"left");
        rightSide = hardwareMap.get(DcMotor.class, "right");

        leftSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        initAprilTag();

        waitForStart();

        while (opModeIsActive()) {

            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            telemetry.addData("# AprilTags Detected", currentDetections.size());

            if ( !currentDetections.isEmpty()) {
                for (AprilTagDetection detection : currentDetections) {
                    if (detection.metadata != null) {
                        telemetry.addData("ID detected: ", detection.id);
                        telemetry.addData("NAME: ", detection.metadata.name);
                    } else {
                        telemetry.addLine(String.format("\n. (ID %d) Unknown", detection.id));
                    }

                    switch (detection.id) {
                        case 21:
                            leftSide.setPower(1);
                            break;

                        case 22:
                            rightSide.setPower(1);
                            break;

                        case 23:
                            leftSide.setPower(1);
                            rightSide.setPower(1);
                            break;


                        default:
                            leftSide.setPower(0);
                            rightSide.setPower(0);
                            break;
                    }
                }
            } else {
                leftSide.setPower(0);
                rightSide.setPower(0);
            }

            telemetry.update();
        }

        visionPortal.close();
    }
    private void initAprilTag() {

        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                .setDrawAxes(false)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setDrawTagID(true)
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())

                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size(640, 480));
        builder.enableLiveView(true);

        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

    }

}