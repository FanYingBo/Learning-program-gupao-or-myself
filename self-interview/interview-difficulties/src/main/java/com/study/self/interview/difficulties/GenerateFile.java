package com.study.self.interview.difficulties;

import java.io.*;
import java.util.Random;

public class GenerateFile {

    public static void main(String[] args) {
        File file = new File("D:\\batch\\");
        if(!file.exists()){
            file.mkdirs();
        }
        file = new File("D:\\batch\\address.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file,true);
            int count = 1000000;
            for(int i = 0; i< count;i++){
                Random random = new Random();

                StringBuilder sb = new StringBuilder();
                sb.append("www.").append((char)(97+random.nextInt(25)))
                        .append((char)(97+random.nextInt(25)))
                        .append((char)(97+random.nextInt(25)))
                        .append((char)(97+random.nextInt(25)))
                        .append((char)(97+random.nextInt(25)))
                        .append((char)(97+random.nextInt(25)))
                        .append((char)(97+random.nextInt(25)))
                        .append(".com")
                        .append("\n");
                fos.write(sb.toString().getBytes("UTF-8"));
            }
            fos.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
