package com.study.selfs.test;

import java.util.Arrays;

/**
 * 01矩阵岛屿问题，0非岛屿 1岛屿 相邻的1算一个岛屿   上下左右算相邻
 */
public class IslandDemo {

    public static void main(String[] args) {
        boolean[][] grid = {{false,false,true,false,false},
                            {false,false,true,true,false},
                            {false,true,false,false,false},
                            {false,false,false,true,true},
                            {false,true,false,false,false}};
        System.out.println(traverse(grid));
        System.out.println(Arrays.toString(grid));
    }
    private static int traverse(boolean[][] grid){
        int num = 0;
        if(grid == null){
            return num;
        }
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
               if(grid[i][j] == true){
                   num++;
                   control(i,j,grid);
               }
            }
        }

        return num;
    }

    private static void control(int i,int j,boolean[][] grid){
        grid[i][j] = false;
        if(i>0 && grid[i-1][j]){
            // 判断上面
            control(i-1,j,grid);
        }
        if(i<grid.length-1 && grid[i+1][j]){
            // 判断下面
            control(i+1,j,grid);
        }
        if(j >0 && grid[i][j-1]){
            // 判断左面
            control(i,j-1,grid);
        }
        if(j < grid[i].length-1 && grid[i][j+1]){
            // 判断右面
            control(i,j+1,grid);
        }
    }

}
