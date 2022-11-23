package opmodes;

import BeepBeep.*;

@Autonomous
public class BackAndForth extends LinearOpMode {
    public BackAndForth(Drive drive) {
        super(drive);
    }

    @Override
    public void runOpMode() throws InterruptedException, PathContinuityException {
        Pose2d origin = new Pose2d(0, 0, 0);
        drive.setPoseEstimate(origin);
        TrajectorySequence seq = drive.trajectorySequenceBuilder(origin)
            .back(48)
            .forward(48);

        waitForStart();

        if (!isStopRequested()) {
            while (true) {
                drive.followTrajectorySequence(seq);
            }
        }
    }
}
