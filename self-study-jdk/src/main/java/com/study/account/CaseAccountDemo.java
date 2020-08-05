package com.study.account;


import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class CaseAccountDemo {

    public static void main(String[] args) throws IOException {
        startAccount();
    }

    private static void startAccount()  throws IOException {
        double account = 0.00d;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = null;
        System.out.println("请输入起始金额（+|-）：");
        while((in = bufferedReader.readLine()) != null){
            StringBuilder outPrint = new StringBuilder();
            if(in.equals("ok")){
                System.out.println("总金额为："+account);
                break;
            }
            if(isIntType(in)){
                account += Integer.parseInt(in);
            }
            if(isDoubleType(in)){
                double nextValue = Double.parseDouble(in);
                account = addOpt(account,nextValue);
            }
            outPrint.append("当前总额："+account+" ，请输入金额");
            System.out.println(outPrint.toString());
        }
    }

    private static boolean isIntType(String read){
        if(chackIsNullRead(read)){
            return false;
        }
        if(!StringUtils.isNumeric(read)){
            return false;
        }
        return true;
    }

    private static boolean isDoubleType(String read){
        if(chackIsNullRead(read)){
            return false;
        }
        String[] split = read.split(".");
        if(split.length > 2){
            return false;
        }
        for(String str : split){
           if(!StringUtils.isNumeric(str)) {
               return false;
           }
        }
        return true;
    }

    private static double addOpt(double var1,int var2){
        return addOpt(var1, (double)var2);
    }

    private static double addOpt(double var1,double var2){
        BigDecimal bigDecimal = new BigDecimal(Double.valueOf(var1));
        BigDecimal nextValueDec = new BigDecimal(Double.valueOf(var2));
        // 精确两位
        return bigDecimal.add(nextValueDec).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }



    private static boolean chackIsNullRead(String read){
        return StringUtils.isBlank(read);
    }


}
