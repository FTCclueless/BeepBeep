package BeepBeep;

interface Path {
    Pose2d f(Drive drive);
    Pose2d getEnd(Pose2d pose);
    boolean checkContinuity();
}
