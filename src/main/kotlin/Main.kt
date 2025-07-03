package org.example

import LogWriter
import MecanumDrive
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.ftc.FlightRecorder
import java.io.File
import java.lang.Math.toRadians

fun main() {
    FlightRecorder.onOpModePreInit()

    val xPos = 12.0
    val hpPose = Pose2d(xPos, -50.0, toRadians(-90.0))
    val startPose = Pose2d(30.0, -62.0, toRadians(90.0))
    val drive = MecanumDrive(startPose)

    val traj = drive.actionBuilderPath(startPose)
        .splineToSplineHeading(Pose2d(xPos, -24.0, toRadians(180.0)), toRadians(90.0))
        .splineToSplineHeading(Pose2d(xPos, -3.0, toRadians(180.0)), toRadians(90.0))
        // score
        // grab first preset
        .waitSeconds(0.25)
        .setTangent(toRadians(-90.0))
        .splineToSplineHeading(hpPose, toRadians(-90.0))
        // intake reverse +  grab hp
        .waitSeconds(0.25)
        /*

        .setTangent(toRadians(90.0))
        .splineToSplineHeading(Pose2d(xPos, -12.0, toRadians(180.0)), toRadians(90.0))
        // score
        // grab second preset
        .waitSeconds(0.25)
        .setTangent(toRadians(-90.0))
        .splineToSplineHeading(hpPose, toRadians(-90.0))
        // intake reverse + grab hp
        .waitSeconds(0.25)

        .setTangent(toRadians(90.0))
        .splineToSplineHeading(Pose2d(xPos, -12.0, toRadians(180.0)), toRadians(90.0))
        // score
        .waitSeconds(0.25)
        .setTangent(toRadians(-90.0))
        .splineToSplineHeading(Pose2d(xPos, -22.0, toRadians(180.0)), toRadians(-90.0))
        .waitSeconds(0.1)
        .setTangent(toRadians(-90.0))
        // third preset
        .waitSeconds(0.25)
        .splineToSplineHeading(hpPose, toRadians(-90.0))
        // intake reverse + grab hp
        .waitSeconds(0.25)

        .setTangent(toRadians(90.0))
        .splineToSplineHeading(Pose2d(xPos, -3.0, toRadians(180.0)), toRadians(90.0))
        // score
        .waitSeconds(0.25)
        .setTangent(toRadians(-90.0))
        .splineToSplineHeading(hpPose, toRadians(-90.0))
        // grab hp
        .waitSeconds(0.25)

        .setTangent(toRadians(90.0))
        .splineToSplineHeading(Pose2d(xPos, -3.0, toRadians(180.0)), toRadians(90.0))
        // score
        .waitSeconds(0.25)
        .setTangent(toRadians(-90.0))
        .splineToSplineHeading(hpPose, toRadians(-90.0))
        // grab hp
        .waitSeconds(0.25)


        .setTangent(toRadians(90.0))
        .splineToSplineHeading(Pose2d(xPos, -3.0, toRadians(180.0)), toRadians(90.0))
        // score
        .waitSeconds(0.25)
        .setTangent(toRadians(-90.0))
        .splineToSplineHeading(hpPose, toRadians(-90.0))
        // grab hp
        .waitSeconds(0.25)

        .setTangent(toRadians(90.0))
        .splineToSplineHeading(Pose2d(xPos, -3.0, toRadians(180.0)), toRadians(90.0))
        // score
        .waitSeconds(0.25)
        .setTangent(toRadians(-90.0))
        .splineToSplineHeading(hpPose, toRadians(-90.0))
        // grab hp
        .waitSeconds(0.25)

         */




        .build()

    FlightRecorder.onOpModePreStart()


    while (traj.run(TelemetryPacket())) {

    }



    FlightRecorder.onOpModePostStop()
}