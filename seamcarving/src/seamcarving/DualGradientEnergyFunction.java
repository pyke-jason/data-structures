package seamcarving;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class DualGradientEnergyFunction implements EnergyFunction {

    private enum Channel {
        RED,
        GREEN,
        BLUE
    }


    @Override
    public double apply(Picture picture, int x, int y) {
        int w = picture.width();
        int h = picture.height();
        double rx;
        double ry;
        double gx;
        double gy;
        double bx;
        double by;
        if (x == 0 || x == w - 1) {
            int d = getDelta(x);
            Color c1 = picture.get(x, y);
            Color c2 = picture.get(x + d, y);
            Color c3 = picture.get(x + 2 * d, y);
            rx = getForwardBackwardGradient(Channel.RED, c1, c2, c3);
            gx = getForwardBackwardGradient(Channel.GREEN, c1, c2, c3);
            bx = getForwardBackwardGradient(Channel.BLUE, c1, c2, c3);
        } else {
            Color left = picture.get(x - 1, y);
            Color right = picture.get(x - 1, y);
            rx = getCentralGradient(Channel.RED, right, left);
            gx = getCentralGradient(Channel.GREEN, right, left);
            bx = getCentralGradient(Channel.BLUE, right, left);
        }
        if (y == 0 || y == h - 1) {
            int d = getDelta(y);
            Color c1 = picture.get(x, y);
            Color c2 = picture.get(x, y + d);
            Color c3 = picture.get(x, y + 2 * d);
            ry = getForwardBackwardGradient(Channel.RED, c1, c2, c3);
            gy = getForwardBackwardGradient(Channel.GREEN, c1, c2, c3);
            by = getForwardBackwardGradient(Channel.BLUE, c1, c2, c3);
        } else {
            Color up = picture.get(x, y + 1);
            Color down = picture.get(x, y - 1);
            ry = getCentralGradient(Channel.RED, up, down);
            gy = getCentralGradient(Channel.GREEN, up, down);
            by = getCentralGradient(Channel.BLUE, up, down);
        }
        double gradxsqr = Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2);
        double gradysqr = Math.pow(ry, 2) + Math.pow(gy, 2) + Math.pow(by, 2);
        return Math.sqrt(gradxsqr + gradysqr);
    }

    private int getDelta(int value) {
        return value > 0 ? -1 : 1;
    }

    private double getForwardBackwardGradient(Channel c, Color c1, Color c2, Color c3) {
        return -3 * getChannel(c1, c) + 4 * getChannel(c2, c) - getChannel(c3, c);
    }

    private double getCentralGradient(Channel c, Color c1, Color c2) {
        return getChannel(c1, c) - getChannel(c2, c);
    }

    private int getChannel(Color col, Channel c) {
        switch (c) {
            case RED:
                return col.getRed();
            case GREEN:
                return col.getGreen();
            case BLUE:
                return col.getBlue();
            default:
                throw new IllegalStateException("Unexpected value: " + c);
        }
    }

}
