package site.hanschen.patterns.strategy;

/**
 * @author HansChen
 */
public interface SortHandler<T> {

    /**
     * 初始化排序数组
     */
    void setArray(T array);

    /**
     * @return 返回数组长度
     */
    int getLength();

    /**
     * @return 是否需要交换数组中 index 和 index+1 元素
     */
    boolean needSwap(int index);

    /**
     * 交换array数组中的 index 和 index+1 元素
     */
    void swap(int index);
}
