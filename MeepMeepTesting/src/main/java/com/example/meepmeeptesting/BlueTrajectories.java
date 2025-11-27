package com.example.meepmeeptesting;


import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueTrajectories {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(
                        60,
                        60,
                        Math.toRadians(180), //maxAngVel
                        Math.toRadians(180), //maxAngAccel
                        15
                )
                .build();

        //SLOW SPEEDS
        VelConstraint slowVel = new TranslationalVelConstraint(15);
        AccelConstraint slowAccel = new ProfileAccelConstraint(-20,20);


         //BLUE AUTONOMOUS TOUCHING THE BLUE BASKET


        Pose2d initialPose = new Pose2d(-49, -50, (Math.PI / 4)); //45°

        myBot.runAction(
                myBot.getDrive().actionBuilder(initialPose)
                        .splineToLinearHeading(new Pose2d(-49,-49, Math.toRadians(45)), Math.toRadians(45))
                        .splineToLinearHeading(new Pose2d(-11.5,-12.4, Math.toRadians(230)), Math.toRadians(230))
                        .turn(Math.toRadians(-142))

                        .setReversed(true)
                        .splineToLinearHeading(new Pose2d(2,-25, Math.toRadians(90)), Math.toRadians(90))
                        .lineToYLinearHeading(-45,Math.toRadians(90), slowVel, slowAccel)

                        .lineToY(-30)
                        .splineToLinearHeading(new Pose2d(-11.5,-12.4, Math.toRadians(230)), Math.toRadians(230))
                        .waitSeconds(2)

                        .strafeTo(new Vector2d(0,-27))
                        .endTrajectory().build());





        //BLUE AUTONOMOUS TOUCHING WALL!

/*
        Pose2d initialPose = new Pose2d(61,-22, 0);

        myBot.runAction(
                myBot.getDrive().actionBuilder(initialPose)

                        .setReversed(true)
                        .splineTo(new Vector2d(60,-10), Math.toRadians(25))
                        .splineToSplineHeading(new Pose2d(46,-23,Math.toRadians(0)), Math.toRadians(0))
                         //GO TO TAKE THE ARTIFACTS, BUT SLOW A BIT SO THEY ROBOT DOESN'T PUSH THEM ACCIDENTALLY, SAME HEADING//PREPARE FOR TELEOP AND MANUAL SHOOTING!
                                .endTrajectory().build());

*/


        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}