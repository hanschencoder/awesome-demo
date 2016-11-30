package site.hanschen.patterns.strategy;


import java.util.Arrays;
import java.util.List;

/**
 * @author HansChen
 */
public class Test {

    public static void main(String[] args) {
        int[] intArray = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        BubbleSorter<int[]> intBubbleSorter = new BubbleSorter<>(new IntSortHandler());
        int operations = intBubbleSorter.sort(intArray);
        System.out.println("[Strategy] operations:" + operations + ", array:" + Arrays.toString(intArray));

        double[] doubleArray = {9.9, 8.8, 7.7, 6.6, 5.5, 4.4, 3.3, 2.2, 1.1, 0.0};
        BubbleSorter<double[]> doubleBubbleSorter = new BubbleSorter<>(new DoubleSortHandler());
        operations = doubleBubbleSorter.sort(doubleArray);
        System.out.println("[Strategy] operations:" + operations + ", array:" + Arrays.toString(doubleArray));

        List<Integer> list = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        BubbleSorter<List<Integer>> integerListBubbleSorter = new BubbleSorter<>(new IntegerListSortHandler());
        operations = integerListBubbleSorter.sort(list);
        System.out.println("[Strategy] operations:" + operations + ", list:" + list);
    }
}
