import java.util.ArrayList;
import java.util.List;

public class BeadTracker {
    private Picture[] frames;
    private double tau;
    private int minPixels;
    private double timeSpan;

    public BeadTracker(Picture[] frames, double tau, int minPixels, double timeSpan) {
        this.frames = frames;
        this.tau = tau;
        this.minPixels = minPixels;
        this.timeSpan = timeSpan;
    }

    public Blob[] trackBeads() {
        List<Blob> beads = new ArrayList<>();
        BeadFinder beadFinder = new BeadFinder(frames[0], tau);

        for (int frameIndex = 0; frameIndex < frames.length; frameIndex++) {
            Blob[] currentBeads = beadFinder.getBeads(minPixels);
            updateBeads(beads, currentBeads, frameIndex);
            beadFinder = new BeadFinder(frames[frameIndex], tau);
        }

        return beads.toArray(new Blob[0]);
    }

    private void updateBeads(List<Blob> beads, Blob[] currentBeads, int frameIndex) {
        for (Blob currentBead : currentBeads) {
            boolean isMerged = false;

            for (Blob bead : beads) {
                if (bead.distanceTo(currentBead) <= timeSpan) {
                    bead.merge(currentBead);
                    isMerged = true;
                    break;
                }
            }

            if (!isMerged) {
                beads.add(currentBead);
            }
        }
    }

    public static void main(String[] args) {
        Picture[] frames = new Picture[10];
        double tau = 180.0;
        int minPixels = 10;
        double timeSpan = 0.5;

        BeadTracker beadTracker = new BeadTracker(frames, tau, minPixels, timeSpan);
        Blob[] beads = beadTracker.trackBeads();

        System.out.println("Found " + beads.length + " beads:");
        for (int i = 0; i < beads.length; i++) {
            System.out.println("Bead " + (i + 1) + ": " + beads[i]);
        }
    }
}
