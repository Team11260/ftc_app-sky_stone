package org.firstinspires.ftc.teamcode.mecanum.opmodes.auton.vector;

public class Vector {

    public double x;
    public double y;
    public double magnitude;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        shrinkToUnitVector();
    }

    public double getMagnitude() {
        magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return magnitude;
    }

    public double cos() {
        return Math.abs(x);
    }

    public double sin() {
        return Math.abs(y);
    }

    public void shrinkToUnitVector() {
        if (getMagnitude() > 1) {
            this.x = x / magnitude;
            this.y = y / magnitude;
        }
    }

}
