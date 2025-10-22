package org.firstinspires.ftc.teamcode.Testing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.HashMap;

public class Pepe extends OpenCvPipeline {

    public int blurValue = 2;
    private Mat blurBoxMat = new Mat();

    public Scalar lowerHSV = new Scalar(41.0, 0.0, 72.0, 0.0);
    public Scalar upperHSV = new Scalar(116.0, 183.0, 176.0, 0.0);
    private Mat hsvBinaryMat = new Mat();

    private ArrayList<MatOfPoint> contours = new ArrayList<>();
    private Mat hierachy = new Mat();

    private ArrayList<Rect> contoursRects = new ArrayList<>();

    private Rect biggestRect = null;

    public Scalar lineColor = new Scalar(0.0, 255.0, 0.0, 0.0);
    public int lineThickness = 3;

    private Mat inputRects = new Mat();

    private HashMap<String, Rect> rectTargets = new HashMap<>();
    private HashMap<String, RotatedRect> rotRectTarget = new HashMap<>();

    @Override
    public Mat processFrame(Mat input) {

        // "Difuminado"
        Imgproc.blur(input, blurBoxMat, new Size((2 * blurValue) + 1, (2 * blurValue) + 1));

        //"Umbral de Color"
        Imgproc.cvtColor(blurBoxMat, hsvBinaryMat, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsvBinaryMat, lowerHSV, upperHSV, hsvBinaryMat);

        //"Deteccion de Contornos"
        contours.clear();
        hierachy.release();
        /* maybe change the uh CHAIN APROXX*/Imgproc.findContours(hsvBinaryMat, contours, hierachy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE );

        //"Rectangulos Delimitantes"
        contoursRects.clear();
        for(MatOfPoint points : contours) {
            contoursRects.add(Imgproc.boundingRect(points));
        }

        //"Encontrar Rectangulo Mas Grande"
        this.biggestRect = null;
        for(Rect rect : contoursRects) {
            if(rect != null) {
                if((biggestRect == null) || (rect.area() > biggestRect.area())) {
                    this.biggestRect = rect;
                }
            }
        }

        //"Dibujar Rectangulos"
        input.copyTo(inputRects);
        if(((biggestRect != null) && (inputRects != null) && (!inputRects.empty()))) {
            Imgproc.rectangle(inputRects, biggestRect, lineColor, lineThickness);
        }

        clearTargets();

        addRectTarget("pelota", biggestRect);

        return inputRects;
    }

    private synchronized void clearTargets() {
        rectTargets.clear();
        rotRectTarget.clear();
    }

    private synchronized void addRectTarget(String label, Rect rect) {
        rectTargets.put(label, rect);
    }
}
