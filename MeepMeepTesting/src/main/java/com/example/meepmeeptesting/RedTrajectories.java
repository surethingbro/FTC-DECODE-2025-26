package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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
        Pose2d initialPose = new Pose2d(-49,50, (Math.PI * 7 )/ 4); //315°

        myBot.runAction(
                myBot.getDrive().actionBuilder(initialPose)
                        .splineToLinearHeading(new Pose2d(-12,12, (Math.PI * 7 )/ 4), (Math.PI * 7 )/ 4)
                        .waitSeconds(5)
                        .turnTo(Math.PI / 2)

                        .lineToYLinearHeading(30, Math.PI / 2)
                        .splineToLinearHeading(new Pose2d(-12,50,Math.PI / 2),Math.PI / 2 ,
                                slowVel,
                                slowAccel)

                        .lineToYLinearHeading(40,Math.PI/ 2 )
                        .splineToLinearHeading(new Pose2d(-12,12.4, (Math.PI * 7 )/ 4), (Math.PI * 7 )/ 4)
                        .waitSeconds(5)

                        .splineToSplineHeading(new Pose2d(12,23, Math.PI / 2), Math.PI/ 2) //ALIGN WITH THE OTHER 3 ARTIFACTS
                        .splineToSplineHeading(new Pose2d(12,50,Math.PI  /2),Math.PI / 2,
                                slowVel,
                                slowAccel)
                        .lineToYLinearHeading(40,Math.PI / 2 )
                        .endTrajectory().build());


    //RED AUTONOMOUS TOUCHING WALL
/*
        Pose2d initialPose = new Pose2d(-61,23, 0);

        myBot.runAction(
                myBot.getDrive().actionBuilder(initialPose)
                        .lineToXLinearHeading(-30,(Math.PI * 7) / 4)
                        .splineToLinearHeading(new Pose2d(-12,12, (Math.PI * 7 )/ 4), (Math.PI * 7 )/ 4)
                        .waitSeconds(5)
                        .turnTo(Math.PI / 2)

                        .lineToYLinearHeading(30, Math.PI / 2)
                        .splineToLinearHeading(new Pose2d(-12,50,Math.PI / 2),Math.PI / 2 ,
                                slowVel,
                                slowAccel)

                        .lineToYLinearHeading(40,Math.PI/ 2 )
                        .splineToLinearHeading(new Pose2d(-12,12.4, (Math.PI * 7 )/ 4), (Math.PI * 7 )/ 4)
                        .waitSeconds(5)

                        .splineToSplineHeading(new Pose2d(12,23, Math.PI / 2), Math.PI/ 2) //ALIGN WITH THE OTHER 3 ARTIFACTS
                        .splineToSplineHeading(new Pose2d(12,50,Math.PI  /2),Math.PI / 2,
                                slowVel,
                                slowAccel)
                        .lineToYLinearHeading(40,Math.PI / 2 )

                .endTrajectory().build());

 */
            meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
}
}
