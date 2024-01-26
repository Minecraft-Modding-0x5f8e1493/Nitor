package mod.utils;

public class ColorUtil {
    public static int tint(int col, int tint) {
        int r = (getRed(col) + getRed(tint)) / 2;
        int g = (getGreen(col) + getGreen(tint)) / 2;
        int b = (getBlue(col) + getBlue(tint)) / 2;
        int a = getAlpha(col);
        return combine(r, g, b, a);
    }

    private static int getRed(int col) {
        return (col >> 16) & 0xFF;
    }

    private static int getGreen(int col) {
        return (col >> 8) & 0xFF;
    }

    private static int getBlue(int col) {
        return (col) & 0xFF;
    }

    private static int getAlpha(int col) {
        return (col >> 24) & 0xff;
    }

    private static int combine(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
