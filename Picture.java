import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Picture extends JPanel implements ActionListener {
    private BufferedImage image;
    private String imagePath;

    public Picture(String imagePath) {
        this.imagePath = imagePath;
        loadImage();
    }

    public Picture(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    private void loadImage() {
        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int width() {
        return image.getWidth();
    }

    public int height() {
        return image.getHeight();
    }

    public Color get(int col, int row) {
        int rgb = image.getRGB(col, row);
        int alpha = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        return new Color(red, green, blue, alpha);
    }

    public void set(int col, int row, Color color) {
        int alpha = color.getAlpha();
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
        image.setRGB(col, row, rgb);
    }

    public void setOriginLowerLeft() {
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.scale(1, -1);
        g2d.translate(0, -height());
        g2d.dispose();
    }

    public void setOriginUpperLeft() {
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.scale(1, -1);
        g2d.translate(0, -height());
        g2d.dispose();
    }

    public void save(String filename) {
        File file = new File(filename);
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(File file) {
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        JFrame frame = new JFrame("Picture");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.getContentPane().add(this);
        frame.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
    public Picture thresholding(int tau) {
        Picture result = new Picture(width(), height());
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                Color color = get(col, row);
                int brightness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                if (brightness > tau) {
                    result.set(col, row, Color.WHITE);  // Background
                } else {
                    result.set(col, row, Color.BLACK);  // Foreground
                }
            }
        }
        return result;
    }


}
