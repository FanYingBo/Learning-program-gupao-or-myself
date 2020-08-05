package com.study.selfs.leecode;

import java.util.TreeSet;

/**
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 *
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 */
public class MedianDigit {

    public static void main(String[] args) {
        int[] ints1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
        int[] ints2 = {0,6};
        System.out.println(findMedianSortedArrays2(ints1,ints2));
    }

    /**
     * 计数法计算中位数
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2){
        int totalLen = nums1.length + nums2.length;
        boolean isEven = (totalLen & 1) == 0;
        int mid = totalLen/ 2;
        int count = 0;
        int midNum = 0;
        int midCount = isEven ? 2 : 1;
        if (nums1.length > 0 && nums2.length > 0){
            int nums1Max = nums1[nums1.length-1];
            int nums2Max = nums2[nums2.length-1];
            int nums1Min = nums1[0];
            int nums2Min = nums2[0];
            int[] plusNum = null;
            int[] minusMin = null;
            if(nums1Max > 0 || nums2Max > 0){
                plusNum = new int[Math.max(nums1Max + 1,nums2Max + 1)];
            }
            if(nums1Min < 0 || nums2Min < 0){
                minusMin = new int[-Math.min(nums1Min - 1,nums2Min - 1)];
            }
            computePlusMinus(nums1,minusMin,plusNum);
            computePlusMinus(nums2,minusMin,plusNum);
            if(minusMin != null){
                for(int i = minusMin.length - 1; i >= 0; i--){
                    int tmpCount = count;
                    count += minusMin[i];
                    if(isEven && minusMin[i] != 0){
                        if(count == mid){
                            midNum = (-i);
                        }

                        if(count == mid + 1){
                            if(minusMin[i] >= 2){
                                midNum += (-i) * 2;
                            }else{
                                midNum += (-i);
                            }
                        }
                        if(tmpCount == mid && count > mid +1){
                            midNum += (-i);
                        } else if(tmpCount < mid && count > mid + 1 ){
                            midNum += -i * 2;
                        }
                    }else{
                        if( tmpCount <= mid && count >= mid + 1 && minusMin[i] != 0){
                            midNum = -i;
                        }
                    }
                }
            }
            if(plusNum != null){
                for(int i = 0; i < plusNum.length; i++){
                    int tmpCount = count;
                    count += plusNum[i];
                    if(isEven && plusNum[i] != 0){
                        if(count == mid){
                            midNum = i;
                        }
                        if(count == mid + 1){
                            if(plusNum[i] >= 2){
                                midNum += i * 2;
                            }else{
                                midNum += i;
                            }
                        }
                        if(tmpCount == mid && count > mid +1){
                            midNum += i;
                        } else if(tmpCount < mid && count > mid + 1 ){
                            midNum += i * 2;
                        }
                    }else{
                        if( tmpCount <= mid && count >= mid + 1 && plusNum[i] != 0){
                            midNum = i;
                        }
                    }
                }
            }
            return (midNum * 10d)/midCount/10;
        }else{
            if(nums1.length > 0){
                if(isEven){
                    midNum = (nums1[mid -1] + nums1[mid]);
                }else{
                    midNum = nums1[mid];
                }
            }
            if(nums2.length > 0){
                if(isEven){
                    midNum = (nums2[mid -1] + nums2[mid]);
                }else{
                    midNum = nums2[mid];
                }
            }
            return (midNum * 10d)/midCount/10;
        }
    }

    /**
     *
     * @param nums1
     * @param minusNum 小于0
     * @param plusNum 大于等于0
     */
    private static void  computePlusMinus(int[] nums1,int[] minusNum,int[] plusNum ){
        for(int i = 0; i < nums1.length ; i++) {
            if (nums1[i] >= 0) {
                if (plusNum != null) {
                    plusNum[nums1[i]] += 1;
                }
            }
            if (nums1[i] < 0) {
                if (minusNum != null) {
                    minusNum[-nums1[i]] += 1;
                }
            }
        }
    }

    /**
     * 二叉树实现( 这种实现不能存在重复，不合理)
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for(int i = 0;i < nums1.length;i++){
            treeSet.add(nums1[i]);
        }
        for(int i = 0;i < nums2.length;i++) {
            treeSet.add(nums2[i]);
        }
        System.out.println(treeSet);
        boolean isEven = (treeSet.size() & 1) == 0;
        int mid = treeSet.size() / 2;
        int midNum = 0;
        int midCount = isEven ? 2 : 1;
        for(int i = 0;i < nums1.length + nums2.length;i++){
            Integer integer = treeSet.pollFirst();
            if(isEven ){
                if(i == mid - 1){
                    midNum = integer;
                }
                if(i == mid){
                    midNum +=  integer;
                }
            }else if(i == mid){
                midNum = integer;
            }
        }
        System.out.println(midNum);
        return (midNum * 10d)/midCount/10;
    }
}
