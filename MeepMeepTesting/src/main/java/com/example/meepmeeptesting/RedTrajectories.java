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

public class RedTrajectories {
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


        //RED AUTONOMOUS TOUCHING BASKET
        /*
        Pose2d initialPose = new Pose2d(-49,50, (Math.PI * 7 )/ 4); //315°


        myBot.runAction(
                myBot.getDrive().actionBuilder(initialPose)

                        .splineToLinearHeading(new Pose2d(-11.5,12.4, Math.toRadians(130)), Math.toRadians(130))
                        .turnTo(Math.toRadians(270))

                        .splineToLinearHeading(new Pose2d(0,20, Math.toRadians(270)), Math.toRadians(270))
                        .lineToYLinearHeading(45,Math.toRadians(270), slowVel, slowAccel)

                        .lineToY(30)
                        .splineToLinearHeading(new Pose2d(-11.5,12.4, Math.toRadians(130)), Math.toRadians(130))

                        .strafeTo(new Vector2d(5,20))


                        .endTrajectory().build());
         */

    //RED AUTONOMOUS TOUCHING WALL

        Pose2d initialPose = new Pose2d(61,22, 0);

        myBot.runAction(
                myBot.getDrive().actionBuilder(initialPose)

                        .setReversed(true)
                        .splineTo(new Vector2d(55,10), Math.toRadians(-35))

                        .waitSeconds(2)
                        .strafeToConstantHeading(new Vector2d(57,35))

                .endTrajectory().build());


            meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
}
}
