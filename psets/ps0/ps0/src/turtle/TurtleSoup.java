/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 4; i++){
            turtle.forward(sideLength);
            turtle.turn(90);
        }
        //throw new RuntimeException("implement me!");
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double degrees = 180 - (360.0/sides);
        return degrees;
        //throw new RuntimeException("implement me!");
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int side = (int)Math.round(360.0/(180 - angle));
        return side;
        //throw new RuntimeException("implement me!");
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = calculateRegularPolygonAngle(sides);
        //System.out.println(angle);
        for (int i = 0; i < sides; i++){
            turtle.forward(sideLength);
            turtle.turn(180 - angle);
            
        }
        
        //throw new RuntimeException("implement me!");
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double slope;
        if ((targetX - currentX) < 0.001){
            slope = 0;
        }
        else if ((targetY - currentY) < 0.001){
            slope = 90;
        }
        else{
            slope = (double)(targetY - currentY)/(targetX - currentX);
            //System.out.println(slope+" "+Math.atan(slope));
            slope = Math.atan(slope) * (180.0/Math.PI);
            //System.out.println(slope);
        }
        double turn = (slope - currentHeading); 
        turn = turn < 0 ? 360+turn : turn;
        return turn;
        //throw new RuntimeException("implement me!");
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> solList = new ArrayList<>();
        double heading = 0.0;
        for (int i = 0, size = xCoords.size(); i < size-1; i++){
            double newAngle = calculateHeadingToPoint(heading, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1));
            //System.out.println(newAngle+" "+xCoords.get(i)+ " "+yCoords.get(i));
            solList.add(newAngle); 
            heading = newAngle;
        }
        return solList;
        //throw new RuntimeException("implement me!");
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        PenColor[] color = {PenColor.BLACK, PenColor.GRAY, PenColor.RED,
                            PenColor.PINK, PenColor.ORANGE, PenColor.YELLOW,
                            PenColor.GREEN, PenColor.CYAN, PenColor.BLUE,
                            PenColor.MAGENTA};
        for (int i = 0; i < 50; i++){
            turtle.color(color[i%10]);
            turtle.forward(i * 10);
            turtle.turn(144);
        }
    }
    /*
    private static void helpDraw(Turtle turtle, int length, int depth){
        if (depth == 0){
            turtle.forward(length);
        }
        else{
            helpDraw(turtle, length/3, depth-1);
            turtle.turn(60);
            helpDraw(turtle, length/3, depth-1);
            turtle.turn(240);
            helpDraw(turtle, length/3, depth-1);
            turtle.turn(60);
            helpDraw(turtle, length/3, depth-1);
        }
    }
    */
    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        //DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);

        // draw the window
        //turtle.draw();
        
        DrawableTurtle turtle = new DrawableTurtle();
        //drawRegularPolygon(turtle, 1000, 1);
        drawPersonalArt(turtle);
        turtle.draw();
    }

}
