import java.text.DecimalFormat;

public class Blob {
    private int mass;
    private double centerX;
    private double centerY;

    public Blob() {
        mass = 0;
        centerX = 0.0;
        centerY = 0.0;
    }

    public void add(int x, int y) {
        mass++;
        centerX = ((centerX * (mass - 1)) + x) / mass;
        centerY = ((centerY * (mass - 1)) + y) / mass;
    }

    public int mass() {
        return mass;
    }

    public double distanceTo(Blob that) {
        double dx = this.centerX - that.centerX;
        double dy = this.centerY - that.centerY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.####");
        return mass + " (" + df.format(centerX) + ", " + df.format(centerY) + ")";
    }

    public static void main(String[] args) {
        Blob blob1 = new Blob();
        blob1.add(10, 20);
        blob1.add(15, 25);

        Blob blob2 = new Blob();
        blob2.add(5, 10);

        System.out.println("Blob 1: " + blob1);
        System.out.println("Blob 2: " + blob2);
        System.out.println("Distance between blob1 and blob2: " + blob1.distanceTo(blob2));
        System.out.println("Mass of blob1: " + blob1.mass());
        System.out.println("Mass of blob2: " + blob2.mass());
    }
}
