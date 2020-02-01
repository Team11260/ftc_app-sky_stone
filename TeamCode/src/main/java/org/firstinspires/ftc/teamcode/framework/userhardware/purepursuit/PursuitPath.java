package org.firstinspires.ftc.teamcode.framework.userhardware.purepursuit;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * PursuitPath is a class which stores points and calculates values to run pure pursuit
 *
 * @author Matthew Oates
 */

public class PursuitPath {

    /**PursuitPath specific creation and following data*/

    /**
     * Distance between each point (inches)
     */
    private double fPointSpacing = 2;

    /**
     * The amount of smoothing to be done on the path (larger number = more smoothing)
     */
    private double fPathSmoothing = 0.8;

    /**
     * Speed reduction through turns (larger number = faster turns)
     */
    private double fTurnSpeed = 0.07;

    /**
     * Scales following speed based on tracking error (smaller number = better tracking, larger number = faster tracking)
     */
    private double fTrackingErrorSpeed = 5.0;

    /**
     * Scales how much turn error is added into total error
     */
    private double fTurnErrorScalar = 0.0;

    /**
     * The max acceleration (total output/point)
     */
    private double fMaxAcceleration = 0.08;

    /**
     * The max deceleration (total output/point)
     */
    private double fMaxDeceleration = 0.02;

    /**
     * Minimum follow speed
     */
    private double fMinSpeed = 0.17;

    /**
     * Maximum follow speed
     */
    private double fMaxSpeed = 1.0;

    /**
     * Average look ahead distance
     */
    private double fLookAheadDistance = 5;

    /**
     * Look ahead distance for velocity calculations
     */
    private int fVelocityLookAheadPoints = 1;

    /**
     * Position error that it allows
     */
    private double fPositionError = 2.5;

    /**
     * Heading error that it allows
     */
    private double fHeadingError = 5.0;

    /**
     * Run specific data, gets reset with reset() method
     */

    private int fLastPointIndex = 0;
    private int fLastCurrentPointIndex = 0;
    private double fTargetAngle = 0;
    private double fDeltaAngle = 0;
    private double fCurvature = 0.000001;

    /**
     * Waypoints along path specified by behavior
     */
    private ArrayList<Point> fPoints;

    /**
     * All the points along the path, created from the waypoints (fPoints)
     */
    private ArrayList<PathPoint> fPath;

    /**
     * Pass in an ArrayList of waypoints
     */
    public PursuitPath(ArrayList<Point> points) {
        fPoints = points;
    }

    /**
     * Pass in a comma separated list or array of waypoints
     */
    public PursuitPath(Point... points) {
        this(new ArrayList<>(Arrays.asList(points)));
    }

    /**
     * Setters for path specific creation and following data
     */

    public void setPointSpacing(double pointSpacing) {
        fPointSpacing = pointSpacing;
    }

    public void setPathSmoothing(double pathSmoothing) {
        fPathSmoothing = pathSmoothing;
    }

    /*public void setTurnSpeed(double turnSpeed) {
        fTurnSpeed = turnSpeed;
    }*/

    public PursuitPath setTurnSpeed(double turnSpeed) {
        fTurnSpeed = turnSpeed;
        return this;
    }

    public PursuitPath setTurnSpeedReturn(double turnSpeed) {
        fTurnSpeed = turnSpeed;

        return this;
    }

    public void setTrackingErrorSpeed(double trackingErrorSpeed) {
        fTrackingErrorSpeed = trackingErrorSpeed;
    }

    public void setTurnErrorScalar(double turnErrorScalar) {
        fTurnErrorScalar = turnErrorScalar;
    }

    public PursuitPath setMaxAcceleration(double maxAcceleration) {
        fMaxAcceleration = maxAcceleration;
        return this;
    }

    public PursuitPath setMaxDeceleration(double maxDeceleration) {
        fMaxDeceleration = maxDeceleration;
        return this;
    }

    public PursuitPath setMinSpeed(double minSpeed) {
        fMinSpeed = minSpeed;
        return this;
    }

    public PursuitPath setMaxSpeed(double maxSpeed) {
        fMaxSpeed = maxSpeed;
        return this;
    }

    public void setLookAheadDistance(double lookAheadDistance) {
        fLookAheadDistance = lookAheadDistance;
    }

    public void setVelocityLookAheadPoints(int lookAheadPoints) {
        fVelocityLookAheadPoints = lookAheadPoints;
    }

    public PursuitPath setPositionError(double positionError) {
        fPositionError = positionError;
        return this;
    }

    public PursuitPath setHeadingError(double headingError) {
        fHeadingError = headingError;
        return this;
    }

    public double getHeadingError() {
        return fHeadingError;
    }
    /**
     * Getters for path specific creation and following data
     */

    public double getPointSpacing() {
        return fPointSpacing;
    }

    public double getPathSmoothing() {
        return fPathSmoothing;
    }

    public double getTurnSpeed() {
        return fTurnSpeed;
    }

    public double getTrackingErrorSpeed() {
        return fTrackingErrorSpeed;
    }

    public double getTurnErrorScalar(double turnErrorScalar) {
        return fTurnErrorScalar;
    }

    public double getMaxAcceleration() {
        return fMaxAcceleration;
    }

    public double getMaxDeceleration() {
        return fMaxDeceleration;
    }

    public double getMinSpeed() {
        return fMinSpeed;
    }

    public double getMaxSpeed() {
        return fMaxSpeed;
    }

    public double getLookAheadDistance() {
        return fLookAheadDistance;
    }

    public int getVelocityLookAheadPoints() {
        return fVelocityLookAheadPoints;
    }

    public double getPositionError(){
        return fPositionError;
    }
    /**Methods for path following*/

    /**
     * Returns a single PathPoint from fPath
     *
     * @param index the index of the PathPoint
     * @return a PathPoint
     */
    public PathPoint getPoint(int index) {
        return getPathPoint(index);
    }

    /**
     * Returns all points in path (fPath).
     *
     * @return a PathPoint ArrayList
     */
    public ArrayList<PathPoint> getPoints() {
        if (fPath != null) {
            return (ArrayList<PathPoint>) fPath.clone();
        }
        build();
        return getPoints();
    }

    /**
     * Calculates and returns the angle from the robots current pose to a specified point.
     *
     * @param index           the index of the point, usually the look ahead point
     * @param currentLocation the current Pose2d of the robot
     * @return the angle in degrees
     */
    public double getAngleFromPathPoint(int index, Pose currentLocation) {
        if (fPath == null || fPath.size() == 0) return 0.0;

        //Vector delta = new Vector(index < getPath().size() - 1 ? getPathPoint(index).subtract(currentLocation) : getPathPoint(getPath().size() - 1).add(new Vector(getPathPoint(getPath().size() - 1).subtract(getPathPoint(getPath().size() - 2))).normalize().scale(fLookAheadDistance - currentLocation.distance(getPathPoint(getPath().size() - 1)))).subtract(currentLocation));
        Vector delta = new Vector(getPathPoint(index).subtract(currentLocation));

//        double angle = Math.toDegrees(Math.atan2(delta.getY(), Math.abs(delta.getX()) > 0.3 ? delta.getX() : 0.3 * Math.signum(delta.getX())));
        double angle = Math.toDegrees(Math.atan2(delta.getY(), delta.getX()));

        fTargetAngle = angle;

        return angle;
    }


    /**
     * Calculates and returns the curvature from the robots current pose to a specified point,
     * used by the follower to steer the robot.
     *
     * @param index           the index of the point, usually the look ahead point
     * @param currentLocation the current Pose2d of the robot
     * @return the curvature represent as 1 / radius of the circle made by the amount of curvature
     */
    public double getCurvatureFromPathPoint(int index, Pose currentLocation) {
        if (fPath == null || fPath.size() == 0) return 0.0;

        Vector delta = new Vector(index < getPath().size() - 1 ? getPathPoint(index).subtract(currentLocation) : getPathPoint(getPath().size() - 1).add(new Vector(getPathPoint(getPath().size() - 1).subtract(getPathPoint(getPath().size() - 2))).normalize().scale(fLookAheadDistance - currentLocation.distance(getPathPoint(getPath().size() - 1)))).subtract(currentLocation));

        double angle = Math.toDegrees(Math.atan2(delta.getY(), Math.abs(delta.getX()) > 0.3 ? delta.getX() : 0.3 * Math.signum(delta.getX())));

        fTargetAngle = angle;

        fDeltaAngle = currentLocation.getHeading() - angle;

        if (Math.abs(fDeltaAngle) > 180)
            fDeltaAngle = -Math.signum(fDeltaAngle) * (360 - Math.abs(fDeltaAngle));

        double curvature = (Math.abs(fDeltaAngle) > 90 ? Math.signum(fDeltaAngle) : Math.sin(Math.toRadians(fDeltaAngle))) / (delta.magnitude() / 2);

        if (Double.isInfinite(curvature) || Double.isNaN(curvature)) return 0.0;

        fCurvature = curvature;

        return curvature;
    }

    /**
     * Returns the last target angle of the robot,
     * calculated by getCurvatureFromPathPoint.
     * Used mainly for debugging.
     *
     * @return the last target angle
     */
    public double getTargetAngle() {
        return fTargetAngle;
    }

    /**
     * Calculates and returns the optimal velocity of the robot at its current position,
     * used by the follower to drive the robot.
     * Calculates the speed of upcoming points using curvature and tracking error, then picks the slowest.
     * This ensures we slow down in advance for turns, preventing overshoot.
     *
     * @param index           the index of the point closest to the robot
     * @param currentLocation the current Pose2d of the robot
     * @return the velocity
     */
    public double getPathPointVelocity(int index, Pose currentLocation) {
        double speed = fMaxSpeed;
        for (int i = index; i < index + fVelocityLookAheadPoints && i < getPoints().size(); i++) {
            speed = Math.min(speed, range(getPathPoint(i).getVelocity() / range(getTrackingError(currentLocation) / fTrackingErrorSpeed, 1, 3), fMinSpeed, fMaxSpeed));
        }
        return speed;
    }

    /**
     * Calculates and returns the index of the point on the path (fPath) closest to the robots current position,
     * can then be passed into other methods to calculate following values.
     *
     * @param currentPosition the current Point of the robot
     * @return the index of the closest point
     */
    public int getClosestPointIndex(Point currentPosition) {
        if (fPath == null || fPath.size() == 0) return -1;

        double distance = 1000000;
        int index = -1;

        for (int i = fLastCurrentPointIndex; i < getPath().size(); i++) {
            if (currentPosition.distance(getPathPoint(i)) < distance) {
                index = i;
                distance = currentPosition.distance(getPathPoint(i));
            }
        }

        fLastCurrentPointIndex = index;

        if (fLastCurrentPointIndex < 0) {
            fLastCurrentPointIndex = 0;
        }

        return index;
    }

    /**
     * Calculates and returns the index of the pure pursuit look ahead point,
     * can then be passed into other methods to calculate following values.
     *
     * @param currentPosition the current Point of the robot
     * @return the index of the look ahead point
     */
    public int getLookAheadPointIndex(Pose currentPosition) {
        if (fPath == null || fPath.size() == 0) return -1;

        int closest = getClosestPointIndex(currentPosition);

        for (int i = getClosestPointIndex(currentPosition); i < getPath().size(); i++) {
            fCurvature = Math.abs(getCurvatureFromPathPoint(i, currentPosition));

            double correction = range(fCurvature, 1, 5);

            double curvature = 0;

            for (int p = closest; p <= i; p++) {
                curvature += getPointCurvature(closest);
            }

            if (getPointDistance(i) - getPointDistance(closest) > fLookAheadDistance / range(curvature / 3, 1, 2)) {
                fLastPointIndex = i;
                return i;
            }
        }

        if (closest != getPath().size() - 1) return getPath().size() - 1;

        return -1;
    }

    /**
     * Calculates and returns straight line distance between the robot and the closest point on the path (fPath),
     * used by getPathPointVelocity to slow the following speed if the robot is far off the path.
     *
     * @param currentPosition the current Point of the robot
     * @return the index of the look ahead point
     */
    public double getTrackingError(Point currentPosition) {
        return getPathPoint(getClosestPointIndex(currentPosition)).distance(currentPosition) + Math.abs(fDeltaAngle * fTurnErrorScalar);
    }

    /**Methods creating and clearing the path*/

    /**
     * Resets all the run specific data, so a single path can be run more than once.
     */
    public void reset() {
        fLastPointIndex = 0;
        fLastCurrentPointIndex = 0;
        fTargetAngle = 0;
        fDeltaAngle = 0;
        fCurvature = 0.000001;
    }

    /**
     * Returns a single PathPoint from fPath
     *
     * @param point the index of the PathPoint
     * @return a PathPoint
     */
    private PathPoint getPathPoint(int point) {
        if (fPath != null) {
            return fPath.get(point);
        }
        build();
        return getPathPoint(point);
    }

    /**
     * Returns all points in path (fPath).
     *
     * @return a PathPoint ArrayList
     */
    private ArrayList<PathPoint> getPath() {
        if (fPath != null) {
            return fPath;
        }
        build();
        return getPath();
    }

    /**
     * Turns all the waypoints (fPoints) into a path (fPath).
     */
    public void build() {

        if (fPath != null) {
            return;
        }

        if (fPoints.size() == 0) {
            fPath = new ArrayList<>();
            return;
        }

        fill();

        smooth();

        createPath();
    }

    /**
     * Fills the spaces between waypoints (fPoints) with a point fPointSpacing inches.
     */
    private void fill() {
        ArrayList<Point> newPoints = new ArrayList<>();

        for (int s = 1; s < fPoints.size(); s++) {
            Vector vector = new Vector(fPoints.get(s - 1), fPoints.get(s));

            int numPointsFit = (int) Math.ceil(vector.magnitude() / fPointSpacing);

            vector = vector.normalize().scale(fPointSpacing);

            for (int i = 0; i < numPointsFit; i++) {
                newPoints.add(fPoints.get(s - 1).add(vector.scale(i)));
            }
        }

        newPoints.add(fPoints.get(fPoints.size() - 1));

        fPoints = newPoints;
    }

    /**
     * Smooths the straight lines of points into a curved path.
     */
    private void smooth() {
        double change = 0.5;
        double changedPoints = 1;
        while (change / changedPoints >= 0.01) {
            change = 0;
            changedPoints = 0;

            ArrayList<Point> newPoints = (ArrayList<Point>) fPoints.clone();

            for (int i = 1; i < fPoints.size() - 1; i++) {
                Point point = fPoints.get(i);

                Vector middle = new Vector(fPoints.get(i + 1).subtract(fPoints.get(i - 1)));

                middle = new Vector(fPoints.get(i - 1).add(middle.normalize().scale(middle.magnitude() / 2)));

                Vector delta = new Vector(middle.subtract(point));

                Point newPoint = point.add(delta.normalize().scale(delta.magnitude() * fPathSmoothing));

                if (!Double.isNaN(newPoint.getX()) && !Double.isNaN(newPoint.getY())) {
                    newPoints.set(i, newPoint);
                    change += point.distance(newPoint);
                    changedPoints++;
                }
            }
            fPoints = newPoints;
        }
    }

    /**
     * Calculates a target velocity and curvature for every point on the path.
     */
    private void createPath() {

        fPath = new ArrayList<>();

        for (int p = 0; p < fPoints.size(); p++) {
            fPath.add(new PathPoint(fPoints.get(p), getPointDistance(p), getPointCurvature(p), getPointVelocity(p)));
        }

        for (int p = fPoints.size() - 2; p >= 0; p--) {
            getPathPoint(p).setVelocity(getPointNewVelocity(p));
        }
    }

    /**
     * Returns the distance a point is along the path.
     *
     * @param p the index of the point
     * @return the distance the point is along the path
     */
    private double getPointDistance(int p) {
        if (p == 0) return 0.0;
        return fPoints.get(p).distance(fPoints.get(p - 1)) + getPathPoint(p - 1).getDistance();
    }

    /**
     * Returns the curvature of the path at a point,
     * uses the getCurvature method.
     * Used by the getPointVelocity method.
     *
     * @param p the index of the point
     * @return the curvature of the path at the point, represent as 1 / radius of the circle made by the amount of curvature
     */
    private double getPointCurvature(int p) {
        if (p <= 0 || p >= fPoints.size() - 1) return 0.0;
        return getCurvature(fPoints.get(p), fPoints.get(p - 1), fPoints.get(p + 1));
    }

    /**
     * Returns the curvature between three points,
     * by fitting a circle to the points.
     *
     * @param p1 the index of the first point
     * @param p2 the index of the second point
     * @param p3 the index of the third point
     * @return the curvature represent as 1 / radius of the circle made by the amount of curvature
     */
    private double getCurvature(Point p1, Point p2, Point p3) {
        double x1 = p1.getX(), y1 = p1.getY();
        double x2 = p2.getX(), y2 = p2.getY();
        double x3 = p3.getX(), y3 = p3.getY();
        if (x1 == x2) x1 += 0.0001;
        double k1 = 0.5 * (Math.pow(x1, 2) + Math.pow(y1, 2) - Math.pow(x2, 2) - Math.pow(y2, 2)) / (x1 - x2);
        double k2 = (y1 - y2) / (x1 - x2);
        double b = 0.5 * (Math.pow(x2, 2) - 2 * x2 * k1 + Math.pow(y2, 2) - Math.pow(x3, 2) + 2 * x3 * k1 - Math.pow(y3, 2)) / (x3 * k2 - y3 + y2 - x2 * k2);
        double a = k1 - k2 * b;
        double r = Math.sqrt(Math.pow(x1 - a, 2) + Math.pow(y1 - b, 2));
        double c = 1 / r;
        if (Double.isNaN(c)) {
            return 0.0;
        }
        return c;
    }

    /**
     * Returns the first calculation velocity of the path at a point,
     * calculated using the amount of curvature at the point.
     * Uses the getPointCurvature method.
     *
     * @param p the index of the point
     * @return the first calculation of velocity
     */
    private double getPointVelocity(int p) {
        if (p >= fPoints.size() - 2) return fMinSpeed;

        double d = fPoints.get(p).distance(fPoints.get(p + 1));

        if (p <= 0)
            return Math.max(Math.min(2 * fMaxAcceleration * d, fTurnSpeed / getPointCurvature(p)), fMinSpeed);

        return Math.max(Math.min(getPathPoint(p - 1).getVelocity() + 2 * fMaxAcceleration * d, Math.min(fTurnSpeed / getPointCurvature(p), fMaxSpeed)), fMinSpeed);
    }

    /**
     * Returns the second/final calculation of the velocity of the path at a point,
     * calculated using fMaxAcceleration, and the speed at nearby points,
     * to make smooth and consistent acceleration and deceleration.
     *
     * @param p the index of the point
     * @return the second/final calculation of velocity
     */
    private double getPointNewVelocity(int p) {
        if (p >= fPoints.size() - 2) return fMinSpeed;

        double d = fPoints.get(p).distance(fPoints.get(p + 1));

        return Math.min(getPathPoint(p).getVelocity(), Math.min(getPathPoint(p + 1).getVelocity() + 2 * fMaxDeceleration * d, fMaxSpeed));
    }

    /**
     * Returns number in the bounds of min and max
     *
     * @param number the initial number
     * @param min    the lower bound of the return value
     * @param max    the upper bound of the return value
     * @return the bounded number
     */
    private double range(double number, double min, double max) {
        if (number < min) {
            return min;
        }
        if (number > max) {
            return max;
        }
        return number;
    }
}