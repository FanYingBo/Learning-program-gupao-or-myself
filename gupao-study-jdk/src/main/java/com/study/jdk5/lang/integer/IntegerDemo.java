package com.study.jdk5.lang.integer;


/**
 *@see java.lang.Integer
 * getChars()
 */
public class IntegerDemo {
    final static char [] DigitOnes = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    } ;
    final static char [] DigitTens = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
    } ;
    final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };


    final static int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE };


    public static void main(String[] args) {
        // 验证Integer 中的getChars(int i, int index, char[] buf) 方法
//        charsMethodVerify();

        System.out.println(Integer.toBinaryString(65535));// 1111 1111 1111 1111
        System.out.println(Integer.toBinaryString(52429));// 1100 1100 1100 1101
        int i = 2122;//
        System.out.println(Integer.toBinaryString(i));// 1000 0100 1010
        int q = (i * 52429) >>> (16+3);
        System.out.println(Integer.toBinaryString(q));// 1101 0100 2^7+2^6+2^4+2^2= 128+64+16+4=212
        int r = i - ((q << 3) + (q << 1));// 110 1010 0000 + 1 1010 1000 = 1000 0100 1000
        System.out.println(Integer.toBinaryString(r));// 10 2

        System.out.println(Integer.toBinaryString(3*5));//1100+11 2^2+2^0=5 --> 3<<2+3<<0 --> 3<<2 1111
        System.out.println(Integer.toBinaryString(16/8));// 除数是2的整数次幂的时候可以用二进制运算
    }

    private static void charsMethodVerify(){
        int num = 2122;
        int size = stringSize(num);
        System.out.println(size);
        String str = "sdaddss112122ddd";
        char[] chars = str.toCharArray();
        char[] chars_ = new char[chars.length+size];
        System.arraycopy(chars,0,chars_,0,Math.min(chars.length,chars.length+size));
        getChars(num,chars_.length,chars_);
        System.out.println(new String(chars_));
    }

    private static void getChars(int i, int index, char[] buf){
        int q, r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Generate two digits per iteration
        while (i >= 65536) {
            q = i / 100;
            // really: r = i - (q * 100);
            r = i - ((q << 6) + (q << 5) + (q << 2));
            i = q;
            buf [--charPos] = DigitOnes[r];
            buf [--charPos] = DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i <= 65536, i);
        for (;;) {
            q = (i * 52429) >>> (16+3);
            r = i - ((q << 3) + (q << 1));  // r = i-(q*10) ...
            buf [--charPos] = digits [r];
            i = q;
            if (i == 0) break;
        }
        if (sign != 0) {
            buf [--charPos] = sign;
        }
    }


    // Requires positive x
    private static int stringSize(int x) {
        for (int i=0; ; i++)
            if (x <= sizeTable[i])
                return i+1;
    }
}
