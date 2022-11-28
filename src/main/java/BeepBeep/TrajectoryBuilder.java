package BeepBeep;

import java.util.ArrayList;
// TODO Ignoring of headings in things like strafeTo

public class TrajectoryBuilder {
    protected final ArrayList<Path> paths = new ArrayList<>();
    protected final Pose2d origin;

    public TrajectoryBuilder(Pose2d origin) {
        this.origin = origin;
    }

    /**
     * Move robot relative to a theta2 value
     * @param v Robots vector currently
     * @param theta2 Rotation
     * @param distance Distance it travels
     * @return Output position
     */
    private Vector2d movRel(Vector2d v, double theta2, double distance) {
        double theta = Math.atan2(v.getY(), v.getX());
        return new Vector2d(Math.cos(theta + theta2) * distance, Math.sin(theta + theta2) * distance);
    }
    
    public TrajectoryBuilder forward(double distance) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                Vector2d v = movRel(new Vector2d(0, -drive.maxAcc), drive.heading, drive.maxAcc);
                return new Pose2d(v, drive.heading);
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                Vector2d v = movRel(new Vector2d(0, -distance), pose.getHeading(), distance);
                return new Pose2d(
                        v.getX() + pose.getX(),
                        v.getY() + pose.getY(),
                        pose.getHeading()
                );
            }

            @Override
            public boolean checkContinuity() {return true;}
        });
        return this;
    }

    public TrajectoryBuilder back(double distance) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                Vector2d v = movRel(new Vector2d(0, drive.maxAcc), drive.heading, drive.maxAcc);
                return new Pose2d(v, drive.heading);
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                Vector2d v = movRel(new Vector2d(0, distance), pose.getHeading(), distance);
                return new Pose2d(
                        v.getX() + pose.getX(),
                        v.getY() + pose.getY(),
                        pose.getHeading()
                );
            }

            @Override
            public boolean checkContinuity() {return true;}
        });
        return this;
    }

    public TrajectoryBuilder strafeLeft(double distance) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                Vector2d v = movRel(new Vector2d(-drive.maxAcc, 0), drive.heading, drive.maxAcc);
                return new Pose2d(v, drive.heading);
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                Vector2d v = movRel(new Vector2d(-distance, 0), pose.getHeading(), distance);
                return new Pose2d(
                        v.getX() + pose.getX(),
                        v.getY() + pose.getY(),
                        pose.getHeading()
                );
            }

            @Override
            public boolean checkContinuity() {return true;}
        });
        return this;
    }

    public TrajectoryBuilder strafeRight(double distance) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                Vector2d v = movRel(new Vector2d(drive.maxAcc, 0), drive.heading, drive.maxAcc);
                return new Pose2d(v, drive.heading);
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                Vector2d v = movRel(new Vector2d(distance, 0), pose.getHeading(), distance);
                return new Pose2d(
                        v.getX() + pose.getX(),
                        v.getY() + pose.getY(),
                        pose.getHeading()
                );
            }

            @Override
            public boolean checkContinuity() {return true;}
        });
        return this;
    }

    public TrajectoryBuilder strafeTo(Vector2d endPos) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                double theta = Math.atan2(endPos.getY() - drive.y, endPos.getX() - drive.x);
                return new Pose2d(Math.cos(theta) * drive.maxAcc, Math.sin(theta) * drive.maxAcc, drive.heading);
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                return new Pose2d(endPos, pose.getHeading());
            }

            @Override
            public boolean checkContinuity() {
                return true;
            }
        });
        return this;
    }

    public TrajectoryBuilder strafeTo(Pose2d endPos) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                double theta = Math.atan2(endPos.getY() - drive.y, endPos.getX() - drive.x);
                return new Pose2d(Math.cos(theta) * drive.maxAcc, Math.sin(theta) * drive.maxAcc, drive.heading);
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                return endPos;
            }

            @Override
            public boolean checkContinuity() {
                return true;
            }
        });
        return this;
    }


    public TrajectoryBuilder lineTo(Vector2d endPos) {
        return strafeTo(endPos);
    }

    public TrajectoryBuilder lineToConstantHeading(Vector2d endPos) {
        return strafeTo(endPos);
    }

    // FIXME kinda doesn't work sometimes
    public TrajectoryBuilder lineToLinearHeading(Pose2d endPose) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                double theta = Math.atan2(endPose.getY() - drive.y, endPose.getX() - drive.x);
                return new Pose2d(
                    Math.cos(theta) * drive.maxAcc,
                    Math.sin(theta) * drive.maxAcc,
                    Util.lerp(drive.heading, endPose.getHeading(), Cfg.turningRate)
                );
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                return endPose;
            }

            @Override
            public boolean checkContinuity() {
                return true;
            }
        });
        return this;
    }

    public TrajectoryBuilder turn(double theta) {
        paths.add(new Path() {
            @Override
            public Pose2d f(Drive drive) {
                return new Pose2d(0, 0, Util.absAngle(drive.heading + Cfg.turningRate * Math.signum(theta)));
            }

            @Override
            public Pose2d getEnd(Pose2d pose) {
                return new Pose2d(pose.getX(), pose.getY(), Util.absAngle(pose.getHeading() + theta));
            }

            @Override
            public boolean checkContinuity() {
                return true;
            }
        });
        return this;
    }

    public Trajectory build() {
        return new Trajectory(this);
    }

    protected Pose2d end() {
        // FIXME unoptimized
        Pose2d ret = origin;
        for (Path path : paths) {
            ret = path.getEnd(ret);
        }
        return ret;
    }

    // TODO CAUSE SPLINE
    /*public Trajectory lineToSplineHeading(Pose2d endPose) {
    }

    public Trajectory splineTo(Vector2d endPos, double endTangent) {
    }

    public Trajectory splineToConstantHeading(Vector2d endPos, double endTangent) {
    }*/

    // Two other functions not implemented at bottom
}
