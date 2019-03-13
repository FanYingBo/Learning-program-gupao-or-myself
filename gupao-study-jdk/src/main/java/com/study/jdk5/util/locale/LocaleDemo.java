package com.study.jdk5.util.locale;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @since 1.1
 * @see java.util.Locale
 */
public class LocaleDemo {

    public static void main(String[] args) {
//        getAllISO2City();//国际标准化组织 提供的双字母

//        getAllAvaliableCountryLang(); // 获得所有国家的名称和语言

        testDiffDateLocales();

    }

    private static void getAllISO2City(){
        String[] isoCountries = Locale.getISOCountries();
        String[] isoLanguages = Locale.getISOLanguages();
        System.out.println(isoCountries.length + " " + isoLanguages.length);

        Locale aDefault = Locale.getDefault();
        System.out.println(aDefault);
    }

    private static void getAllAvaliableCountryLang(){
        Locale[] availableLocales = Locale.getAvailableLocales();
        for(int i = 0; i<availableLocales.length;i++){
            if(i % 8 != 0)
                System.out.printf(availableLocales[i].getCountry()+" "+availableLocales[i].getDisplayLanguage()+"|");
            else
                System.out.println();
        }
        System.out.println("\n"+availableLocales.length);
    }

    /**
     * 2种不同的Locale的格式化时间
     */
    private static void testDiffDateLocales() {
        // date为2013-09-19 14:22:30
        Date date = new Date();

        // 创建“简体中文”的Locale
        Locale localeCN = Locale.SIMPLIFIED_CHINESE;
        // 创建“英文/美国”的Locale
        Locale localeUS = Locale.US;

        // 获取“简体中文”对应的date字符串
        String cn = DateFormat.getDateInstance(DateFormat.MEDIUM, localeCN).format(date);
        // 获取“英文/美国”对应的date字符串
        String us = DateFormat.getDateInstance(DateFormat.MEDIUM, localeUS).format(date);

        System.out.printf("cn=%s\n us=%s\n", cn, us);


    }


}
