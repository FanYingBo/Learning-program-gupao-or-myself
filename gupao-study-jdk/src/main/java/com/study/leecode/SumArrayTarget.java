package com.study.leecode;

import java.util.Arrays;

public class SumArrayTarget {

    public static void main(String[] args) {
        int[] ints = new int[]{2,5,5,11};
        System.out.println(Arrays.toString(twoSum(ints,10)));
    }

    /**
     * n*(n-1)+(n-1)*(n-2)+.....
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] indexs = new int[2];
        for(int i = 0;i < nums.length;i++){
            for(int j = i+1; j < nums.length;j++){
                if((nums[i] + nums[j]) == target){
                    indexs[0] = i;
                    indexs[1] = j;
                    return indexs;
                }
            }
        }
        return indexs;
    }

}
