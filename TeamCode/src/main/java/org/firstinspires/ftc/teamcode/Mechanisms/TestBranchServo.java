package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class TestBranchServo {

    private CRServo servo;

    public void init(HardwareMap hwMap) {
        servo = hwMap.get(CRServo.class, "servo");
    }

    public void setRot(double speed) {
        servo.setPower(speed);
    }
}
