package com.example.meepmeeptesting;


import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
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

        Pose2d initialPose = new Pose2d(-49, -50   ,(Math.PI / 4)); //45°

        //TODO: VERIFY THAT WE REALLY TAKE 5 SECONDS TO SHOOT THE BALLS (I HOPE WE DO NOT...) IF WE DON'T, CHANGE THE WAITSECONDS!!!!
        myBot.runAction(
                myBot.getDrive().actionBuilder(initialPose)
                        .splineToLinearHeading(new Pose2d(-11.5,-12.4, Math.PI/4), Math.PI/4) //MOVE FORWARD TO SHOOTING SPOT, IN A HEADING OF 45° TO RADIANS
                        .waitSeconds(5) //SHOOT FIRST THREE ARTIFACTS

                        .turn((Math.PI * 5) / 4) // TURN 225° (IN RADIANS OFC), TO ALIGN WITH THE THREE ARTIFACTS
                        .lineToYLinearHeading(-30, (Math.PI * 3) / 2)  //GET CLOSE TO THEM (HEADING 270°)

                        .splineToLinearHeading(new Pose2d(-11.5,-55,(Math.PI * 3) / 2),(Math.PI * 3) / 2 ,
                                slowVel,
                                slowAccel) //GO TO TAKE THE ARTIFACTS, BUT SLOW A BIT SO THEY ROBOT DOESN'T PUSH THEM ACCIDENTALLY, SAME HEADING


                        .lineToYLinearHeading(-40,(Math.PI * 3) / 2 ) //THIS IS JUST SO THE SPLINE DOESN'T MAKE THE ROBOT COLLIDE WITH THE PATH
                        .splineToLinearHeading(new Pose2d(-11.5,-12.4, Math.PI/4), Math.PI/4)

                        //SPLINE BACK TO OUR SHOOTING POSITIONS,
                        // THIS TIME WITH A HEADING OF 45°

                        .waitSeconds(5) //SHOOT BALLS

                        .splineToSplineHeading(new Pose2d(12,-23, (Math.PI * 3) / 2), (Math.PI* 3 )/ 2) //ALIGN WITH THE OTHER 3 ARTIFACTS
                        .splineToSplineHeading(new Pose2d(12,-53,(Math.PI * 3) / 2),(Math.PI * 3) / 2,
                                slowVel,
                                slowAccel)
                        .lineToYLinearHeading(-40,(Math.PI * 3) / 2 ) //PREPARE FOR TELEOP AND MANUAL SHOOTING!
                        .endTrajectory()

                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}