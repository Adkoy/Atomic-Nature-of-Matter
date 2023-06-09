import java.util.ArrayList;
import java.util.List;

public class BeadFinder {
    private Picture picture;
    private double tau;

    public BeadFinder(Picture picture, double tau) {
        this.picture = picture;
        this.tau = tau;
    }

    public Blob[] getBeads(int min) {
        List<Blob> beads = new ArrayList<>();

        boolean[][] visited = new boolean[picture.width()][picture.height()];

        for (int col = 0; col < picture.width(); col++) {
            for (int row = 0; row < picture.height(); row++) {
                if (!visited[col][row]) {
                    Blob blob = new Blob();
                    int size = floodFill(col, row, blob, min, visited);
                    if (size >= min) {
                        beads.add(blob);
                    }
                }
            }
        }

        return beads.toArray(new Blob[0]);
    }

    private int floodFill(int col, int row, Blob blob, int min, boolean[][] visited) {
        int size = 0;

        if (col < 0 || row < 0 || col >= picture.width() || row >= picture.height()) {
            return size;
        }

        Color color = picture.get(col, row);
        double luminance = getLuminance(color);

        if (luminance <= tau || visited[col][row]) {
            return size;
        }

        blob.add(col, row);
        visited[col][row] = true;
        size++;

        size += floodFill(col + 1, row, blob, min, visited);
        size += floodFill(col - 1, row, blob, min, visited);
        size += floodFill(col, row + 1, blob, min, visited);
        size += floodFill(col, row - 1, blob, min, visited);

        return size;
    }

    private double getLuminance(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    public static void main(String[] args) {
        Picture picture = new Picture("link");
        double tau = 180.0;
        int min = 10;

        BeadFinder beadFinder = new BeadFinder(picture, tau);
        Blob[] beads = beadFinder.getBeads(min);

        System.out.println("Found " + beads.length + " beads:");
        for (int i = 0; i < beads.length; i++) {
            System.out.println("Bead " + (i + 1) + ": " + beads[i]);
        }
    }
}
