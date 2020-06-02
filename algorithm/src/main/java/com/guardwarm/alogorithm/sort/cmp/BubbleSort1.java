package com.guardwarm.alogorithm.sort.cmp;

import com.guardwarm.alogorithm.sort.Sort;

public class BubbleSort1<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; --end) {
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) < 0) {
                    swap(begin, begin - 1);
                }
            }
        }
    }
}
