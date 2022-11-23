package BeepBeep;

import java.util.ArrayList;

public class TrajectorySequence {
    protected final ArrayList<Trajectory> trajectorys = new ArrayList<>();
    protected final Pose2d origin;


    public TrajectorySequence(Pose2d origin) {
        this.origin = origin;
    }

    public TrajectorySequence forward(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new Trajectory(trajOrigin).forward(distance));
        return this;
    }

    public TrajectorySequence back(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new Trajectory(trajOrigin).back(distance));
        return this;
    }

    public TrajectorySequence strafeLeft(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new Trajectory(trajOrigin).strafeLeft(distance));
        return this;
    }

    public TrajectorySequence strafeRight(double distance) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new Trajectory(trajOrigin).strafeRight(distance));
        return this;
    }

    public TrajectorySequence strafeTo(Vector2d endPos) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new Trajectory(trajOrigin).strafeTo(endPos));
        return this;
    }

    public TrajectorySequence lineTo(Vector2d endPos) {
        return strafeTo(endPos);
    }

    public TrajectorySequence lineToConstantHeading(Vector2d endPos) {
        return strafeTo(endPos);
    }

    public TrajectorySequence lineToLinearHeading(Pose2d endPose) {
        Pose2d trajOrigin = origin;
        if (trajectorys.size() > 0) {
            trajOrigin = trajectorys.get(trajectorys.size() - 1).end();
        }
        trajectorys.add(new Trajectory(trajOrigin).lineToLinearHeading(endPose));
        return this;
    }
}
