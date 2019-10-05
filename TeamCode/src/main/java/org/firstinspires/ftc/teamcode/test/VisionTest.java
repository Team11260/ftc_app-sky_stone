package org.firstinspires.ftc.teamcode.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.framework.abstractopmodes.AbstractAuton;
import org.firstinspires.ftc.teamcode.framework.userhardware.DoubleTelemetry;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.ImageViewer;
import org.firstinspires.ftc.teamcode.framework.userhardware.inputs.sensors.vision.vuforia.VuforiaImpl;

import java.util.ArrayList;
import java.util.Collections;

@TeleOp(name = "VisionTest", group = "Test")
//@Disabled

public class VisionTest extends AbstractAuton {

    ImageViewer imageViewer;
    VuforiaImpl vuforia;
    int currentRow = -1;

    @Override
    public void RegisterStates() {

    }

    @Override
    public void Init() {
        vuforia = new VuforiaImpl(false, true);
        imageViewer = new ImageViewer(vuforia.getRotation());
    }

    @Override
    public void InitLoop(int loop) {
        Bitmap bitmap = vuforia.getImage();

        ArrayList<Integer> rows = new ArrayList<>();

        for(int r = 0; r < bitmap.getHeight(); r+=16) {
            int y = 0;
            for(int c = 0; c < bitmap.getWidth(); c+=16) {
                int pixel = bitmap.getPixel(c, r);
                y += (Color.red(pixel) + Color.green(pixel) / 2) - Color.blue(pixel);
            }
            rows.add(y);
        }

        //telemetry.addDataDB(DoubleTelemetry.LogMode.INFO, rows);

        rows = filterArray(rows, 5);

        //telemetry.addDataDB(DoubleTelemetry.LogMode.INFO, rows);

        if(currentRow == -1) currentRow = bitmap.getHeight() / 2;

        ArrayList<Integer> pixels = new ArrayList<>();

        for(int p = 0; p < bitmap.getWidth(); p++) {
            int pixel = bitmap.getPixel(p, currentRow);
            bitmap.setPixel(p, currentRow, Color.rgb(0, 0, 0));
            pixels.add(Color.red(pixel) + Color.green(pixel) + Color.blue(pixel));
        }

        positionFromPixels(pixels);

        for(int p = 0; p < bitmap.getHeight(); p++) {
            bitmap.setPixel(bitmap.getWidth() / 4, p, Color.rgb(254, 254, 254));
            bitmap.setPixel((bitmap.getWidth() / 4) * 3, p, Color.rgb(254, 254, 254));
            bitmap.setPixel((bitmap.getWidth() / 5) * 2, p, Color.rgb(254, 254, 254));
            bitmap.setPixel((bitmap.getWidth() / 5) * 3, p, Color.rgb(254, 254, 254));
        }

        /*int above = 0, below = 0;

        for(int r = 0; r < rows.size(); r++) {
            if(rows.get(r) == -1) continue;
            for(int c = 0; c < bitmap.getWidth(); c++) {
                bitmap.setPixel(c, r * 8, Color.rgb(254, 254, 254));
            }
            if(r * 8 < currentRow) {
                above++;
            } else {
                below++;
            }
        }*/

        //currentRow += (below - above) * 5;

        imageViewer.setImage(bitmap);
    }

    @Override
    public void Run() {

    }

    @Override
    public void Stop() {
        imageViewer.clearImage();
    }

    public void positionFromPixels(ArrayList<Integer> pixels) {
        int numPixels = pixels.size();

        telemetry.addDataDB(DoubleTelemetry.LogMode.INFO, "Unfiltered: " + pixels);

        pixels = filterArray(pixels, 5);

        telemetry.addDataDB(DoubleTelemetry.LogMode.INFO, "Filtered: " + pixels);

        int left = 0, center = 0, right = 0;
        for(int p = 0; p < numPixels; p++) {
            if(pixels.get(p) == -1) pixels.set(p, 255);
            pixels.set(p, 255 - pixels.get(p));
            if(pixels.get(p) == 0) continue;
            if(p > (numPixels / 4.0) && p < (numPixels / 5.0) * 2) {
                left++;
            } else if(p > (numPixels / 5.0) * 2 && p < (numPixels / 5) * 3) {
                center++;
            } else if(p > (numPixels / 5) * 3 && p < (numPixels / 4) * 3){
                right++;
            }
        }

        telemetry.addData(DoubleTelemetry.LogMode.INFO, "Left: " + left + " Center: " + center + " Right: " + right);

        if(left > center && left > right) {
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Left");
        } else if(center > right) {
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Center");
        } else {
            telemetry.addData(DoubleTelemetry.LogMode.INFO, "Right");
        }
        telemetry.update();
    }

    public ArrayList<Integer> filterArray(ArrayList<Integer> pixels, int level) {
        int numPixels = pixels.size();

        ArrayList<Integer> sortedPixels = (ArrayList<Integer>) pixels.clone();

        Collections.sort(sortedPixels);

        for(int p = 0; p < numPixels; p++) {
            int total = 0;
            total += pixels.get(p);
            if(p != 0) total += pixels.get(p - 1);
            if(p != pixels.size() - 1) total += pixels.get(p + 1);
            pixels.set(p, total / 3);
        }

        int cutoff = sortedPixels.get(numPixels / level);

        for(int p = 0; p < numPixels; p++) {
            if(pixels.get(p) > cutoff) {
                pixels.set(p, -1);
            }
        }

        return pixels;
    }
}
