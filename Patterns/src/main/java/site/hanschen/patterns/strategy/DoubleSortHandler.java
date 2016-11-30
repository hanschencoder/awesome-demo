package site.hanschen.patterns.strategy;

/**
 * @author HansChen
 */
public class DoubleSortHandler implements SortHandler<double[]> {

    private double[] array;

    @Override
    public void setArray(double[] array) {
        this.array = array;
    }

    @Override
    public int getLength() {
        return array == null ? 0 : array.length;
    }

    @Override
    public boolean needSwap(int index) {
        return array != null && (array[index] > array[index + 1]);
    }

    @Override
    public void swap(int index) {
        double temp = array[index];
        array[index] = array[index + 1];
        array[index + 1] = temp;
    }
}
