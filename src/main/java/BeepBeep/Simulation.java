package BeepBeep;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.*;
import java.io.IOException;

public class Simulation {
    private final JFrame frame;
    private final FieldPanel field;

    /**
     * A window that displays your robot and
     * @param ft The field to render.
     * @param size Size of the field in pixels
     * @param opmodes Autonomous opmodes given from reflection calls
     */
    public Simulation(FieldType ft, int size, Class<?>[] opmodes) throws IOException {
        Drive drive = new Drive();
        frame = new JFrame("BeepBeep");
        //frame.setLayout(null); // CAN BE REMOVED?
        frame.setSize(size + size / 2, size); // 2:1 ratio
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        field = new FieldPanel(ft, drive);
        field.setBackground(Color.RED);
        field.setPreferredSize(new Dimension(size, size));
        frame.add(field, BorderLayout.WEST);

        DriverHub hub = new DriverHub(opmodes, drive);
        hub.setSize(size / 2, size);
        frame.add(hub, BorderLayout.EAST);
    }

    public void start() throws InterruptedException {
        frame.setVisible(true);
        while (true) {
            field.repaint();
            Thread.sleep(1000 / Cfg.targetFps); // 60 frames per second
        }
    }
}
