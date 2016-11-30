package site.hanschen.patterns.strategy;

/**
 * @author HansChen
 */
public class BubbleSorter<T> {

    /**
     * 抽象策略接口，可以有不同的实现
     */
    private SortHandler<T> sortHandler;

    public BubbleSorter(SortHandler<T> sortHandler) {
        this.sortHandler = sortHandler;
    }

    /**
     * 冒泡排序
     */
    public int sort(T array) {

        sortHandler.setArray(array);
        int length = sortHandler.getLength();

        int operations = 0;
        if (length <= 1) {
            return operations;
        }

        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                operations++;
                if (sortHandler.needSwap(j)) {
                    sortHandler.swap(j);
                }
            }
        }

        return operations;
    }
}
