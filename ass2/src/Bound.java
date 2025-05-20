public class Bound {
    private final int xLowerBound;
    private final int xUpperBound;
    private final int yLowerBound;
    private final int yUpperBound;

    /**
     * Constructs a Bound object with the specified lower and upper bounds for the X and Y axes.
     *
     * @param xLowerBound the lower bound of the X-axis
     * @param xUpperBound the upper bound of the X-axis
     * @param yLowerBound the lower bound of the Y-axis
     * @param yUpperBound the upper bound of the Y-axis
     */
    public Bound(int xLowerBound, int xUpperBound, int yLowerBound, int yUpperBound) {
        this.xLowerBound = xLowerBound;
        this.xUpperBound = xUpperBound;
        this.yLowerBound = yLowerBound;
        this.yUpperBound = yUpperBound;
    }

    /**
     * Gets the lower bound of the X-axis.
     *
     * @return the X-axis lower bound
     */
    public int getXLowerBound() {
        return this.xLowerBound;
    }

    /**
     * Gets the upper bound of the X-axis.
     *
     * @return the X-axis upper bound
     */
    public int getXUpperBound() {
        return this.xUpperBound;
    }

    /**
     * Gets the lower bound of the Y-axis.
     *
     * @return the Y-axis lower bound
     */
    public int getYLowerBound() {
        return this.yLowerBound;
    }

    /**
     * Gets the upper bound of the Y-axis.
     *
     * @return the Y-axis upper bound
     */
    public int getYUpperBound() {
        return this.yUpperBound;
    }
}
