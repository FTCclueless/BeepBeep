package BeepBeep;

// Encapsulation so it looks like you must call .build() (yeah its pretty scuffed)
public class Trajectory {
    protected final TrajectoryBuilder builder;
    private Pose2d end;

    public Trajectory(TrajectoryBuilder builder) {
        this.builder = builder;
        this.end = builder.origin;
        for (Path path : builder.paths) {
            this.end = path.getEnd(this.end);
        }
    }

    public Pose2d end() {
        return this.end;
    }
}
