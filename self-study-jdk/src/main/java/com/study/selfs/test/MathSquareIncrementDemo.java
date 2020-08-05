package com.study.selfs.test;


import java.util.HashMap;

/**
 * a*a..+b*b..+...... = c*c..
 */
public class MathSquareIncrementDemo {

    private static long mutliNum = 2;

    public static void main(String[] args) {
        // 2次方和
        operation(100);


    }

    private static void operation(long c){
        HashMap<Long,Long> maps = new HashMap<>();
        long num = 0;
        for (long n = c; n > 0; n--) {
            long l = cirulationMutliply(n);
            for (long i = 1; i < c; i++) {
                for (long j = 1; j < c; j++) {
                    long l1 = cirulationMutliply(i) + cirulationMutliply(j);
                    if (l1 == l &&  i != (maps.get(j) == null ? 0:maps.get(j).longValue())) {
                        StringBuilder sb = new StringBuilder("");
                        sb.append(i).append("的")
                                .append(mutliNum)
                                .append("次方与")
                                .append(j)
                                .append("的")
                                .append(mutliNum)
                                .append("次方和等于")
                                .append(n)
                                .append("的")
                                .append(mutliNum)
                                .append("次方");
                        System.out.println(sb.toString());
                        maps.put(i,j);
                        num++;
                    }
                }
            }
        }
        System.out.println("在"+c+"的整数中，满足一个整数平方等于两个整数的平方和的个数为"+num);
    }

    private static long cirulationMutliply(long  multiplicandNum){
        long nextNum = multiplicandNum;
        for(int i = 0;i < mutliNum-1;i++) {
            nextNum = nextNum * nextNum;
        }
        return nextNum;
    }



}
