package BeepBeep;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class FieldPanel extends JPanel {
    private final BufferedImage fieldImg;
    private final Drive drive;
    private final AffineTransform driveTransform = new AffineTransform();
    // Pixels per inch
    private double ppi = 0.0;

    /**
     * Canvas that renders the field as the robot moves.
     * @param ft FTC field
     * @param drive Pointer to drive variable
     * @throws IOException From finding the field picture
     */
    public FieldPanel(FieldType ft, Drive drive) throws IOException {
        super(true);
        this.drive = drive;
        String filePath = "src/fields/";
        switch (ft) {
            case POWERPLAY:
                filePath += "powerplay.jpg";
                break;
        }
        fieldImg = ImageIO.read(new File(filePath));
    }

    @Override
    public void setPreferredSize(Dimension d) {
        super.setPreferredSize(d);
        ppi = (d.width / 6.0) /* <- size of one tile */ / 24.0; /* 24 inches per tile */
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Dimension res = getSize();
        g.drawImage(fieldImg, 0, 0, res.width, res.height, (img, infoflags, x, y, width, height) -> false);
        int driveWidth = toPx(drive.width);
        int driveHeight = toPx(drive.height);

        // TODO This is a pretty bad way to do this. Fix this later.
        driveTransform.setToTranslation(
            toPx(-drive.y) + toPx(24 * 3),
            toPx(-drive.x) + toPx(24 * 3)
        );
        driveTransform.rotate(drive.heading);
        AffineTransform old = g2d.getTransform();
        g2d.setTransform(driveTransform);
        // Draw robot
        g.setColor(new Color(66, 135, 245));
        g.fillRect(-(driveWidth / 2), -(driveHeight / 2), driveWidth, driveHeight);
        // Draw line to show which direction its facing
        g.setColor(new Color(24, 109, 245));
        g.fillRect(0, -driveHeight / 16, driveWidth / 2, driveHeight / 8);
        //g.fillRect(0, -(driveSize / 8), driveSize - (driveSize / 5), driveSize / 5);
        g2d.setTransform(old);
    }

    private int toPx(double in) {
        return (int)(ppi * in);
    }
}