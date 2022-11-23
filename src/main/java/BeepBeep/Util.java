package BeepBeep;

public class Util {
    protected static double lerp(double a, double b, double c) {
        return a + (b - a) * c;
    }

    public static double absAngle(double angle) {
        double clipped = angle % (Math.PI * 2.0);
        if (clipped < 0) {
            return clipped + Math.PI * 2;
        }
        return clipped;
    }
}
