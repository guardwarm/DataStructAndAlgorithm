package com.guardwarm.alogorithm.sort.cmp;

import com.guardwarm.alogorithm.sort.Sort;

public class BubbleSort3<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) < 0) {
                    swap(begin, begin - 1);
                    sortedIndex = begin;
                }
            }
            // 正好到sortedIndex - 1为乱序，所以end--仍有用
            end = sortedIndex;
        }
    }
}
