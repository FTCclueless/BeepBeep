package BeepBeep;

public class LinearOpMode extends Thread {
    // Only set by main threads when button presses
    protected DriverHubState state = DriverHubState.IDLE;
    public final Drive drive;

    public LinearOpMode(Drive drive) {
        this.drive = drive;
    }

    protected void runOpMode() throws InterruptedException, PathContinuityException {}
    // Roadrunner likes to call this runOpMode. 🤷 Please don't run this by yourself. It's only for threads.
    public void run() {
        try {
            runOpMode();
        } catch (InterruptedException | PathContinuityException e) {
            throw new RuntimeException("Thread stopped.");
        }
    }
    protected void waitForStart() {
        // Hang until running
        while (state != DriverHubState.RUNNING && !interrupted()) {}
    }

    protected boolean isStopRequested() {
        return state == DriverHubState.IDLE;
    }

    protected boolean opModeInInit() {
        return state == DriverHubState.INIT;
    }
}
