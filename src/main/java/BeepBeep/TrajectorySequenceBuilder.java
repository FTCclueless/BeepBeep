package BeepBeep;

import java.util.ArrayList;

// TODO end caching

public class TrajectorySequenceBuilder {
    protected final ArrayList<TrajectoryBuilder> trajectorys = new ArrayList<>();
    protected final Pose2d origin;

    public TrajectorySequenceBuilder(Pose2d origin) {
        this.origin = origin;
    }

    public TrajectorySequenceBuilder forward(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).forward(distance));
        return this;
    }

    public TrajectorySequenceBuilder back(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).back(distance));
        return this;
    }

    public TrajectorySequenceBuilder strafeLeft(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).strafeLeft(distance));
        return this;
    }

    public TrajectorySequenceBuilder strafeRight(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).strafeRight(distance));
        return this;
    }

    public TrajectorySequenceBuilder strafeTo(Vector2d endPos) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).strafeTo(endPos));
        return this;
    }

    public TrajectorySequenceBuilder strafeTo(Pose2d endPos) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).strafeTo(endPos));
        return this;
    }

    public TrajectorySequenceBuilder lineTo(Vector2d endPos) {
        return strafeTo(endPos);
    }

    public TrajectorySequenceBuilder lineToConstantHeading(Vector2d endPos) {
        return strafeTo(endPos);
    }

    public TrajectorySequenceBuilder lineToLinearHeading(Pose2d endPose) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).lineToLinearHeading(endPose));
        return this;
    }

    public TrajectorySequenceBuilder turn(double theta) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new TrajectoryBuilder(trajOrigin).turn(theta));
        return this;
    }

    protected Pose2d end() {
        if (trajectorys.size() > 0) {
            return trajectorys.get(trajectorys.size() - 1).end();
        }
        return origin;
    }

    public TrajectorySequence build() {
        return new TrajectorySequence(this);
    }
}
