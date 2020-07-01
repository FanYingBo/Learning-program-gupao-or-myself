package com.study.leecode;

import javax.swing.plaf.IconUIResource;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 *
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 */
public class MedianDigit {

    public static void main(String[] args) {
        int[] ints1 = {1,2};
        int[] ints2 = {-1,3};
        System.out.println(ints1.length);
        System.out.println(findMedianSortedArrays(ints1,ints2));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        return 0;
    }
}
