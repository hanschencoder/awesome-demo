package site.hanschen.patterns.template;

import java.util.Arrays;
import java.util.List;

/**
 * @author HansChen
 */
public class Test {

    public static void main(String[] args) {
        int[] intArray = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int operations = new IntBubbleSorter().sort(intArray);
        System.out.println("[Template Method] operations:" + operations + ", array:" + Arrays.toString(intArray));

        double[] doubleArray = {9.9, 8.8, 7.7, 6.6, 5.5, 4.4, 3.3, 2.2, 1.1, 0.0};
        operations = new DoubleBubbleSorter().sort(doubleArray);
        System.out.println("[Template Method] operations:" + operations + ", array:" + Arrays.toString(doubleArray));

        List<Integer> list = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        operations = new IntegerListBubbleSorter().sort(list);
        System.out.println("[Template Method] operations:" + operations + ", list:" + list.toString());
    }
}
