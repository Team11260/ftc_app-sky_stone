package org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision;

import android.graphics.Bitmap;

import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;

public class ImageProcessor {

    private VuforiaImpl vuforia;
    private ImageViewer viewer;

    public ImageProcessor() {
        this(false);
    }

    public ImageProcessor(boolean led) {
        vuforia = new VuforiaImpl(false, false);
        viewer = new ImageViewer(vuforia.getRotation());
    }

    public Bitmap getImage() {
        return vuforia.getImage();
    }

    public void setImage(Bitmap image) {
        viewer.setImage(image);
    }

    public void shutDown() {
        viewer.clearImage();
    }

    public static void drawHorizontalLine(Bitmap bitmap, int position, int start, int end, int width, int color) {
        for(int p = start; p < end; p++) {
            for(int w = 0; w < width; w++) {
                bitmap.setPixel(p, position + w, color);
            }
        }
    }

    public static void drawHorizontalLine(Bitmap bitmap, int position, int width, int color) {
        drawHorizontalLine(bitmap, position, 0, bitmap.getWidth(), width, color);
    }

    public static void drawHorizontalLine(Bitmap bitmap, int position, int color) {
        drawHorizontalLine(bitmap, position, 1, color);
    }

    public static void drawVerticalLine(Bitmap bitmap, int position, int start, int end, int width, int color) {
        for(int p = start; p < end; p++) {
            for(int w = 0; w < width; w++) {
                bitmap.setPixel(position + w, p, color);
            }
        }
    }

    public static void drawVerticalLine(Bitmap bitmap, int position, int width, int color) {
        drawVerticalLine(bitmap, position, 0, bitmap.getHeight(), width, color);
    }

    public static void drawVerticalLine(Bitmap bitmap, int position, int color) {
        drawVerticalLine(bitmap, position, 1, color);
    }

    public static void drawBox(Bitmap bitmap, int x, int y, int b, int h, int width, int color) {
        drawHorizontalLine(bitmap, y, x, x + b, width, color);
        drawHorizontalLine(bitmap, y + h, x, x + b + width, width, color);
        drawVerticalLine(bitmap, x, y, y + h, width, color);
        drawVerticalLine(bitmap, x + b, y, y + h + width, width, color);
    }
}
