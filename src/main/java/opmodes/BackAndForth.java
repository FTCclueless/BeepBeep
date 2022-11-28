// Package really does not matter.
package opmodes;

import BeepBeep.*;

// Necessary. Marks as an opmode.
@Autonomous
public class BackAndForth extends LinearOpMode {
    // This is only needed for BeepBeep. When porting your code to the robot don't include this.
    public BackAndForth(Drive drive) {
        super(drive);
    }

    @Override
    public void runOpMode() throws InterruptedException, PathContinuityException {
        Pose2d origin = new Pose2d(0, 0, 0);
        drive.setPoseEstimate(origin);
        // Like RoadRunner, construct a trajectory sequence that does the following things.
        TrajectorySequence seq = drive.trajectorySequenceBuilder(origin)
            .back(48)
            .forward(48)
            .build();

        waitForStart();

        if (!isStopRequested()) {
            while (true) {
                drive.followTrajectorySequence(seq);
            }
        }
    }
}
