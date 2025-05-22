import biuoop.GUI;
import biuoop.DrawSurface;


public class Tests {

    static private void test1() {
        GUI gui = new GUI("Balls Test 1", 400, 400);
        DrawSurface d = gui.getDrawSurface();

        Ball b1 = new Ball(100,100,30, java.awt.Color.RED);
        Ball b2 = new Ball(100,150,10, java.awt.Color.BLUE);
        Ball b3 = new Ball(80,249,50, java.awt.Color.GREEN);

        b1.drawOn(d);
        b2.drawOn(d);
        b3.drawOn(d);

        gui.show(d);
    }

    static private void drawAnimation() {
        GUI gui = new GUI("title",200,200);
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        java.util.Random rand = new java.util.Random();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            Ball ball = new Ball(rand.nextInt(200), rand.nextInt(200), 30, java.awt.Color.BLACK);
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }



    public static void main(String[] args) {
//        test1();
//        drawAnimation();
    }
}