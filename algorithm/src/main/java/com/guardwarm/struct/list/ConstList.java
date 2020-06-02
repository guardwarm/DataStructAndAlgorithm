package com.guardwarm.struct.list;

public enum ConstList {
    /**
     * 各种数字
     */
    ELE(1),
    AAA(2) {
        @Override
        public void setNum(int num) {
            super.setNum(++num);
        }
    };
    /**
     * leixshuzu
     */
    private int num;

    ConstList(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
