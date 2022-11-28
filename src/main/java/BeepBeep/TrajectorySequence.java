package BeepBeep;

import java.util.ArrayList;

public class TrajectorySequence {
    protected final ArrayList<Trajectory> trajectorys = new ArrayList<>();
    protected final TrajectorySequenceBuilder builder;
    private Pose2d end;

    public TrajectorySequence(TrajectorySequenceBuilder builder) {
        for (TrajectoryBuilder trajectoryBuilder : builder.trajectorys) {
            trajectorys.add(trajectoryBuilder.build());
        }
        this.builder = builder;
        if (trajectorys.size() > 0) {
            this.end = trajectorys.get(trajectorys.size() - 1).end();
        } else {
            this.end = builder.origin;
        }
    }

    public Pose2d end() {
        return this.end;
    }
}
