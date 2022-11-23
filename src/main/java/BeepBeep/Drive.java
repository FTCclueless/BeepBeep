package BeepBeep;

public class Drive {
    protected double x;
    protected double y;
    protected double heading;
    protected final double turningRate;
    protected final double maxAcc;
    protected final double width;
    protected final double height;
    // Relative values
    protected double accx = 0;
    protected double accy = 0;
    protected double velx = 0;
    protected double vely = 0;

    /**
     * Omnidirectional drive
     * @param x X position in inches
     * @param y Y position in inches
     * @param heading Robot heading in radians
     * @param maxAcc Max acceleration in inches
     * @param width Robot width in inches
     * @param height Robot height in inches
     */
    public Drive(double x, double y, double heading, double maxAcc, double turningRate, double width, double height) {
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.maxAcc = maxAcc;
        this.turningRate = turningRate;
        this.width = width;
        this.height = height;
    }

    public Drive() {
        this(0, 0, 0, 0.05, 0.01, 18, 18);
    }

    /**
     * Sets the robot position to be at the pose
     * @param pose Target pose for the robot to be at
     */
    public void setPoseEstimate(Pose2d pose) {
        x = pose.getX();
        y = pose.getY();
        heading = pose.getHeading();
    }

    public Trajectory trajectoryBuilder(Pose2d origin) {
        return new Trajectory(origin);
    }

    public TrajectorySequence trajectorySequenceBuilder(Pose2d origin) {
        return new TrajectorySequence(origin);
    }

    private boolean canSnapTo(Pose2d pose) {
        // Check if the distance between the two points is able to be snapped to with our current velocity
        double abs = Util.absAngle(pose.getHeading());
        return
            Math.pow(pose.getX() - x, 2) + Math.pow(pose.getY() - y, 2) <= Math.pow(velx + vely, 2) &&
            // Check if between turningRates
            (abs + Cfg.turningRate >= heading && abs - Cfg.turningRate <= heading);
    }

    private Pose2d toPose() {
        return new Pose2d(x, y, heading);
    }

    public void followTrajectory(Trajectory trajectory) throws InterruptedException, PathContinuityException {
        velx = vely = accx = accy = 0;
        x = trajectory.origin.getX();
        y = trajectory.origin.getY();
        heading = trajectory.origin.getHeading();
        for (Path path : trajectory.paths) {
            Pose2d endPose = path.getEnd(toPose());
            if (path.checkContinuity() && (velx != 0 || vely != 0)) {
                throw new PathContinuityException("Incontinuous path.");
            }
            while (!canSnapTo(endPose)) {
                Pose2d p = path.f(this);
                accx = p.getX();
                accy = p.getY();

                velx += accx;
                vely += accy;
                velx *= Cfg.fieldFriction;
                vely *= Cfg.fieldFriction;

                heading = p.getHeading();
                x += velx;
                y += vely;

                Thread.sleep(1000 / Cfg.targetFps);
            }
        }
    }

    public void followTrajectorySequence(TrajectorySequence seq) throws PathContinuityException, InterruptedException {
        for (Trajectory trajectory : seq.trajectorys) {
            followTrajectory(trajectory);
        }
    }
}
