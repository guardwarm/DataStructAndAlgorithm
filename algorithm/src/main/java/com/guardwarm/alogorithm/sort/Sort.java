package com.guardwarm.alogorithm.sort;

import com.guardwarm.model.Student;

import java.text.DecimalFormat;

@SuppressWarnings("unused")
public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>>{
    protected T[] array;
    private int cmpCount;
    private int swapCount;
    private long time;
    private final DecimalFormat dmf = new DecimalFormat("#.00");

    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        this.array = array;
        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    @Override
    public int compareTo(Sort<T> o) {
        int result = (int) (time - o.time);
        if (result != 0) {
            return result;
        }
        result = cmpCount - o.cmpCount;
        if (result != 0) {
            return result;
        }
        return swapCount - o.swapCount;
    }

    protected abstract void sort();

    /**
     * 根据索引取值，进行比较
     * @param i1    索引i1
     * @param i2    索引i2
     * @return  array[i1] 和 array[i2]的差值
     */
    protected int cmp(int i1, int i2) {
        ++cmpCount;
        return array[i1].compareTo(array[i2]);
    }

    protected void swap(int i1, int i2) {
        ++swapCount;
        T temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    private String numberFormat(int number) {
        if (number < 1_0000) {
            return "" + number;
        }
        if (number < 1_0000_0000) {
            return dmf.format(number / 1_0000) + "万";
        }
        return dmf.format(number / 1_0000_0000) + "亿";
    }

    private boolean isStable() {
//        if (this instanceof RadixSort) return true;
//        if (this instanceof CountingSort) return true;
//        if (this instanceof ShellSort) return false;
//        if (this instanceof SelectionSort) return false;
        Student[] students = new Student[20];
        for (int i = 0; i < 20; i++) {
            students[i] = new Student(i *10, 10);
        }
        for (int i = 1; i < students.length; i++) {
            if (students[i].score - students[i-1].score != 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("【").append(getClass().getSimpleName()).append("】").append("\n")
                .append("稳定性：").append(isStable()).append("\t")
                .append("耗时：").append(time/1000.0).append("s(").append(time).append("ms)").append("\t")
                .append("比较：").append(numberFormat(cmpCount)).append("\t")
                .append("交换：").append(numberFormat(swapCount)).append("\n")
                .append("--------------------------------------");
        return sb.toString();
    }
}
