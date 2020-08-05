package com.study.selfs.test;


import java.util.Arrays;

/**
 *1.计数排序
 *@see ArraySortDemo#countSorting
 *2.快速排序
 *@see ArraySortDemo#quickSort(int[], int, int)
 *3.插入排序
 *@see ArraySortDemo#insertSort(int[])
 *4.归并排序
 * @see ArraySortDemo#mergerSort(int[], int, int)
 *
 * ——————————————————————————————————————————————————————————————————————
 *  排序算法  |  最差时间复杂度  |  平均时间复杂度  |  稳定度  |  空间复杂度  |
 * ———————————————————————————————————————————————————————————————————————
 *  计数排序  |     O（n+K）   |      O（n+K）   |   稳定  |    O（n+K） |
 * ————————————————————————————————————————————————————————————————————————
 *  快速排序  |     O（n^2）   |      O(nlogn)  |   不稳定 |    O（logn）|
 * ———————————————————————————————————————————————————————————————————————
 *  插入排序  |     O（n^2）   |      O（n^2）   |   稳定  |     O（1）  |
 * ————————————————————————————————————————————————————————————————————————
 *  归并排序  |     O(nlogn)  |     O(nlogn)    |  稳定   |     0（n）  |
 * ————————————————————————————————————————————————————————————————————————
 */
public class ArraySortDemo {
    public static void main(String[] args) {
//        int[] arr = {41,25,71,82,24,63,42,21,32,45,67,56,34,29,46,90,81,78,87,235,88,86,112,345,221,331,873,27,345,20,83,291};
        int[] arr = {0,21,44,23,13,19,15,25,63,9};
        long start = System.nanoTime();
//        countSorting();
//        quickSort(arr,0,arr.length-1);
//        insertSort(arr);
        mergerSort(arr,0,arr.length-1);
        System.out.println("耗时："+(System.nanoTime()-start));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 计数排序
     */
    private static void countSorting(){
        // 原数组扩充一位首位增加一位 只去下标为 1~a.length的数据
        int[] a = {0,2,5,3,0,2,3,0,3};
        int[] c = new int[6];
        int[] b = new int[a.length];
        for(int i = 0; i < c.length;i++){
            c[i] = 0;
        }
        // 1.计算a中数值等于c下标的元素个数，并存储在c中
        // [2, 0, 2, 3, 0, 1] 表示a有2个0,2个2,3个3,1个5
        for(int j = 1;j < a.length;j++){
            c[a[j]] = c[a[j]]+1;
        }
        // 2.加总 通过加总得到a中的元素有多少是小于等于c的下标的，并存储在c中
        System.out.println(Arrays.toString(c));
//        数组C：[2, 2, 2, 3, 0, 1]
//        数组C：[2, 2, 4, 3, 0, 1]
//        数组C：[2, 2, 4, 7, 0, 1]
//        数组C：[2, 2, 4, 7, 7, 1]
//        数组C：[2, 2, 4, 7, 7, 8] 0+2 1个 表示 1个0 1个2
//                                 0+2 1个 表示 1个0 1个2
//                                 2+2 1个 表示 1个2 1个2
//                                 4+3 1个 表示 1个3 1个4
//                                 4+3 1个 表示 1个3 1个4
//                                 3+5 1个 表示 1个3 1个5
        for(int i = 1;i<c.length;i++){
            c[i] = c[i]+c[i-1];
            System.out.println("数组C："+Arrays.toString(c));
        }
        // 3.把a中的元素放到对应的b中的位置
        for(int j = a.length-1;j >0;j--){
            b[c[a[j]]] = a[j];
            // 下一个位置
            c[a[j]] = c[a[j]]-1;
            System.out.println("J："+j+" 数组B："+Arrays.toString(b)+" 数组C："+Arrays.toString(c));
        }
    }

    /**
     * 快速排序
     * 快速排序的原理：选择一个关键值作为基准值。比基准值小的都在左边序列（一般是无序的），比基准值大的都在右边（一般是无序的）。
     * 一般选择序列的第一个元素。一次循环：从后往前比较，用基准值和最后一个值比较，如果比基准值小的交换位置，如果没有继续比较下一个，
     * 直到找到第一个比基准值小的值才交换。找到这个值之后，又从前往后开始比较，如果有比基准值大的，交换位置，如果没有继续比较下一个，
     * 直到找到第一个比基准值大的值才交换。直到从前往后的比较索引>从后往前比较的索引，结束第一次循环，此时，对于基准值来说，
     * 左右两边就是有序的了。接着分别比较左右两边的序列，重复上述的循环。
     */
    private static int partition(int []array,int lo,int hi){
        //固定的切分方式
        int key = array[lo];
        while(lo < hi){
            while(array[hi] >= key && hi> lo){//从后半部分向前扫描
                hi--;
            }
            array[lo] = array[hi];
            while(array[lo] <= key && hi > lo){// 从前半部分向后扫描
                lo++;
            }
            array[hi] = array[lo];
        }
        array[hi] = key;
        return hi;
    }

    private static void quickSort(int[] array,int lo ,int hi){
        if(lo >= hi){
            return ;
        }
        int index = partition(array,lo,hi);
        quickSort(array,lo,index-1);
        quickSort(array,index+1,hi);

    }

    /**
     * 插入排序
     * 1、将指针指向某个元素，假设该元素左侧的元素全部有序，将该元素抽取出来，然后按照从右往左的顺序分别与其左边的元素比较，
     *   遇到比其大的元素便将元素右移，直到找到比该元素小的元素或者找到最左面发现其左侧的元素都比它大，停止；
     * 2、此时会出现一个空位，将该元素放入到空位中，此时该元素左侧的元素都比它小，右侧的元素都比它大；
     * 3、指针向后移动一位，重复上述过程。每操作一轮，左侧有序元素都增加一个，右侧无序元素都减少一个。
     * @param arr
     */
    private static void insertSort(int[] arr){
        for(int index = 1; index<arr.length; index++){//外层向右的index，即作为比较对象的数据的index
            int temp = arr[index];//用作比较的数据
            int leftindex = index-1;
            while(leftindex>=0 && arr[leftindex]>temp){//当比到最左边或者遇到比temp小的数据时，结束循环
                arr[leftindex+1] = arr[leftindex];
                leftindex--;
            }
            arr[leftindex+1] = temp;//把temp放到空位上
        }
    }

    private static int[] merger(int[] arr ,int leftIndex,int midindex, int rightIndex){
        int leftLen = midindex - leftIndex + 1;
        int rightLen = rightIndex - midindex;
        int[] left = new int[leftLen];
        int[] right = new int[rightLen];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[leftIndex + i ];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[midindex + i +1];
        }
        System.out.println("left "+ Arrays.toString(left) +"\n"
                +"right "+ Arrays.toString(right) +"\n"
                +"arr "+ Arrays.toString(arr) );
        int leftindex = 0;
        int rightindex = 0;
        for (int index = leftIndex; index <= rightIndex; index++) {
            if(leftindex < left.length && rightindex < right.length) {
                int leftVal = left[leftindex];
                int rightVal = right[rightindex];
                if (leftVal <= rightVal) {
                    arr[index] = left[leftindex];
                    leftindex++;
                } else {
                    arr[index] = right[rightindex];
                    rightindex++;
                }
            }else if(leftindex == left.length && rightindex < right.length){
                arr[index] = right[rightindex];
                rightindex++;
            }else if(leftindex < left.length && rightindex == right.length){
                arr[index] = left[leftindex];
                leftindex++;
            }
        }
        System.out.println("arr sorted "+ Arrays.toString(arr));
        return arr;
    }


    private static void mergerSort(int[] arr,int leftIndex,int rightIndex){
        if(rightIndex > leftIndex){
            int midindex = (rightIndex+leftIndex) / 2;
            mergerSort(arr, leftIndex, midindex);
            mergerSort(arr,midindex+1,rightIndex);
            merger(arr,leftIndex,midindex,rightIndex);
        }

    }
}
