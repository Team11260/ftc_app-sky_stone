
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TestMain {
    public static void main(String[] args) {
        ArrayList<Integer> pixels = new ArrayList<>(Arrays.asList(8,10,4,1,2,1,0,1,4,6,8,6,7,8,6,9,7,8,0,7));
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

        int cutoff = sortedPixels.get(numPixels / 5);

        for(int p = 0; p < numPixels; p++) {
            if(pixels.get(p) > cutoff) {
                pixels.set(p, 10);
            }
            pixels.set(p, 10 - pixels.get(p));
        }

        System.out.println(pixels);

        int left = 0, center = 0, right = 0;
        for(int p = 0; p < numPixels; p++) {
            if(pixels.get(p) == 0) continue;
            if(p < numPixels / 3.0) {
                left++;
            } else if(p < (numPixels / 3.0) * 2) {
                center++;
            } else {
                right++;
            }
        }

        System.out.println("Left: " + left + " Center: " + center + " Right: " + right);

        if(left > center && left > right) {
            System.out.println("Left");
        } else if(center > right) {
            System.out.println("Center");
        } else {
            System.out.println("Right");
        }
    }
}
