package BeepBeep;

public class Pose2d {
    private final double x;
    private final double y;
    private final double heading;

    public Pose2d(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public Pose2d(double x, double y) {
        this(x, y, 0);
    }

    public Pose2d(Vector2d pos, double heading) {
        this(pos.getX(), pos.getY(), heading);
    }

    public Pose2d() {
        this(0, 0, 0);
    }

    public Pose2d div(double scalar) {
        return new Pose2d(x / scalar, y / scalar, heading / scalar);
    }

    public boolean epsilonEquals(Pose2d other) {
        return (x + Cfg.epsilon == other.getX() &&
                y + Cfg.epsilon == other.getY() &&
                heading + Cfg.epsilon == other.getHeading());
    }

    public boolean equals(Pose2d other) {
        return (x == other.getX() &&
                y == other.getY() &&
                heading + Cfg.epsilon == other.getHeading());
    }

    public double getHeading() {return heading;}

    public double getX() {return x;}

    public double getY() {return y;}

    public Pose2d minus(Pose2d other) {
        return new Pose2d(x - other.getX(), y - other.getY(), heading - other.getHeading());
    }

    Pose2d	plus(Pose2d other) {
        return new Pose2d(x + other.getX(), y + other.getY(), heading + other.getHeading());
    }

    Pose2d	times(double scalar) {
        return new Pose2d(x * scalar, y * scalar, heading * scalar);
    }

    Pose2d	unaryMinus() {
        return new Pose2d(-x, -y, -heading);
    }

    Vector2d vec() {
        return new Vector2d(x, y);
    }
}
