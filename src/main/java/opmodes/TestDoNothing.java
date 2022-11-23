package opmodes;

import BeepBeep.Autonomous;
import BeepBeep.Drive;
import BeepBeep.LinearOpMode;

@Autonomous
public class TestDoNothing extends LinearOpMode {
    public TestDoNothing(Drive drive) {
        super(drive);
    }

    @Override
    public void runOpMode() {
        waitForStart();

        if (!isStopRequested()) {
            System.out.println("Hey BeepBeep!");
        }
    }
}