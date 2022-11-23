package BeepBeep;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

enum DriverHubState {
    IDLE,
    INIT,
    RUNNING
};

class DriverHub extends JPanel {
    private final JButton button;
    private DriverHubState state = DriverHubState.IDLE;
    private LinearOpMode opmode = null;

    /**
     * Simulated driver hub
     * @param opmodes Autonomous opmodes given from reflection calls
     */
    public DriverHub(Class<?>[] opmodes, Drive drive) {
        super(true);

        String[] opmodesNames = new String[opmodes.length];
        for (int i = 0; i < opmodes.length; i++) {
            opmodesNames[i] = opmodes[i].getSimpleName(); // Gets the name without the package path
        }
        JComboBox cb = new JComboBox<>(opmodesNames);
        cb.addActionListener((ActionEvent event) -> {
            if (opmode != null) { // We might have not run an opmode yet, so we cant just stop nothing
                opmode.interrupt();
            }
        });
        add(cb);

        button = new JButton("INIT");
        button.addActionListener((ActionEvent event) -> {
            switch (this.state) {
                case IDLE: // Start a new opmode (in init state)
                    this.button.setText("RUN");
                    try {
                        opmode = (LinearOpMode)(opmodes[cb.getSelectedIndex()].getConstructors()[0].newInstance(drive));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    opmode.start(); // Thread that edits the drivetrain's values
                    this.state = DriverHubState.INIT;
                    break;
                case INIT: // Run the opmode
                    this.button.setText("STOP");
                    this.state = DriverHubState.RUNNING;
                    break;
                case RUNNING: // Stop the current opmode
                    this.button.setText("INIT");
                    opmode.interrupt();
                    this.state = DriverHubState.IDLE;
                    break;
            }
            opmode.state = this.state;
        });
        add(button);
    }
}
