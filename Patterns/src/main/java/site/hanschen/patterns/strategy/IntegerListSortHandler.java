package site.hanschen.patterns.strategy;

import java.util.List;

/**
 * @author HansChen
 */
public class IntegerListSortHandler implements SortHandler<List<Integer>> {

    private List<Integer> list;

    @Override
    public void setArray(List<Integer> list) {
        this.list = list;
    }

    @Override
    public int getLength() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean needSwap(int index) {
        return list != null && (list.get(index) > list.get(index + 1));
    }

    @Override
    public void swap(int index) {
        int temp = list.get(index);
        list.set(index, list.get(index + 1));
        list.set(index + 1, temp);
    }
}
