package com.study.selfs.jdk5.lang.math;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Math  - >  StrictMath 中的native方法
 * jdk 1.0
 * java 中的运算
 * @see java.util
 */
public class MathDemo {
    public static void main(String[] args) {
//1.        /*****   数学运算     *****/
        System.out.println("绝对值 int："+Math.abs(-1));
        System.out.println("绝对值 double："+Math.abs(-0.1));
        System.out.println("反余弦函数 -1<>1 ："+Math.acos(0.2)*180/Math.PI);
        // jdk1.8新增
//        System.out.println(" 求和："+Math.addExact(-2,1));
//
        System.out.println("反正弦函数 -1<>1："+Math.asin(-1)*180/Math.PI);
        System.out.println("反正切函数 -∞<>+∞："+Math.atan(-1)*180/Math.PI);
        System.out.println("反正切函数 -∞<>+∞："+Math.atan2(1,Math.PI));
        //jdk 1.5
        System.out.println("开立方："+Math.cbrt(-125));// -5
//
        System.out.println("上舍入："+Math.ceil(1.01));// 2
        //jdk 1.6
        System.out.println("第一个参数取值，第二个参数去符号："+Math.copySign(1.03,-1.02));// -1.03
//
        System.out.println("余弦函数："+Math.cos(Math.PI/3));// 0.5000000000000001
        System.out.println("开根号："+Math.cbrt(8));
        System.out.println("反余弦函数 -1<>1："+Math.acos(-1));
        //jdk 1.5
//        System.out.println("双曲余弦 +∞："+Math.cosh(0));
        //jdk 1.8
//        System.out.println("减一 +∞："+Math.decrementExact(2));

//        System.out.println("e^x ："+Math.exp(Math.E));
        //jdk 1.5
//        System.out.println("实立方根 ："+Math.cbrt(2));
//        System.out.println("e^x-1 ："+Math.expm1(Math.E));

        System.out.println("下舍入 ："+Math.floor(1.56));// 1

        System.out.println("下舍入整除 ："+Math.floorDiv(8,5));// 1

        System.out.println("下舍入取模 ："+Math.floorMod(8,3));// 3
        System.out.println("无偏指数 ："+Math.getExponent(16.00));// 二的指数下舍入
//2.        /*****   位运算     *****/









//        DataWindow dw = new DataWindow("Dynamic Line Chart Demo");
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                dw.setVisible(true);
//            }
//        });
//        Thread thread = new Thread(() ->{
//            while (true) {
//                long t = System.currentTimeMillis();
//                int v1 = (int) (Math.sin(t / 1000 * Math.PI / 30) * 50) + 50;
//                int v2 = (int) (Math.cos(t / 1000 * Math.PI / 30) * 50) + 50;
//                dw.addData(t, v1, v2);
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//
//        });
//        thread.start();
//
    }
}
class DataPanel extends JPanel {

    private static final long serialVersionUID = -9039511286331044798L;

    private int index = 0;
    private ArrayList<Long> time = new ArrayList<>();
    private ArrayList<Integer> val = new ArrayList<>();
    DateFormat fmt = DateFormat.getDateTimeInstance();

    public DataPanel() {
    }

    public void addData(long t, int v) {
        time.add(t);
        val.add(v);
        index++;
        repaint();
    }

    // Graph the sensor values in the dataPanel JPanel
    public void paint(Graphics g) {
        super.paint(g);
        int left = getX() + 10;       // get size of pane
        int top = 10;
        int right = left + getWidth() - 20;
        int bottom = getHeight() - 20;

        int y0 = bottom - 20;                   // leave some room for margins
        int yn = top;
        int x0 = left + 33;
        int xn = right;
        double vscale = (yn - y0) / 120.0;      // light values range from 0 to 800
        double tscale = 1.0 / 2000.0;           // 1 pixel = 2 seconds = 2000 milliseconds

        // draw X axis = time
        g.setColor(Color.BLACK);
        g.drawLine(x0, yn, x0, y0);
        g.drawLine(x0, y0, xn, y0);
        int tickInt = 60 / 2;
        for (int xt = x0 + tickInt; xt < xn; xt += tickInt) {   // tick every 1 minute
            g.drawLine(xt, y0 + 5, xt, y0 - 5);
            int min = (xt - x0) / (60 / 2);
            g.drawString(Integer.toString(min), xt - (min < 10 ? 3 : 7) , y0 + 20);
        }

        // draw Y axis = sensor reading
        g.setColor(Color.BLUE);
        for (int vt = 120; vt > 0; vt -= 20) {         // tick every 200
            int v = y0 + (int)(vt * vscale);
            g.drawLine(x0 - 5, v, x0 + 5, v);
            g.drawString(Integer.toString(vt), x0 - 38 , v + 5);
        }

        // graph sensor values
        int xp = -1;
        int vp = -1;
        for (int i = 0; i < index; i++) {
            int x = x0 + (int)((time.get(i) - time.get(0)) * tscale);
            int v = y0 + (int)(val.get(i) * vscale);
            if (xp > 0) {
                g.drawLine(xp, vp, x, v);
            }
            xp = x;
            vp = v;
        }
    }

}

class DataWindow extends JFrame {

    private static final long serialVersionUID = -5628586708228044760L;

    DateFormat fmt = DateFormat.getDateTimeInstance();

    /** Creates new form DataWindow */
    public DataWindow() {
        initComponents();
    }

    public DataWindow(String ieee) {
        initComponents();
        setTitle(ieee);
    }

    public void addData(long t, int v1, int v2) {
        dataPanelTop.addData(t, v1);
        dataPanelBottom.addData(t, v2);
        dataTextArea.append(fmt.format(new Date(t)) + " v1, v2 = " + v1 + ", " + v2 + "\n");
        dataTextArea.setCaretPosition(dataTextArea.getText().length());
    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dataPanelTop = new DataPanel();
        dataPanelTop.setMinimumSize(new Dimension(600, 220));
        dataPanelTop.setPreferredSize(new Dimension(600, 220));
        getContentPane().add(dataPanelTop, BorderLayout.NORTH);

        dataPanelBottom = new DataPanel();
        dataPanelBottom.setMinimumSize(new Dimension(600, 220));
        dataPanelBottom.setPreferredSize(new Dimension(600, 220));
        getContentPane().add(dataPanelBottom, BorderLayout.SOUTH);

        jScrollPaneCenter = new javax.swing.JScrollPane();
        dataTextArea = new javax.swing.JTextArea();

        jScrollPaneCenter.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPaneCenter.setMinimumSize(new Dimension(600, 160));
        jScrollPaneCenter.setPreferredSize(new Dimension(600, 160));

        dataTextArea.setColumns(20);
        dataTextArea.setEditable(false);
        dataTextArea.setRows(4);
        dataTextArea.setBackground(Color.LIGHT_GRAY);
        jScrollPaneCenter.setViewportView(dataTextArea);

        getContentPane().add(jScrollPaneCenter, BorderLayout.CENTER);

        pack();
    }

    private DataPanel dataPanelTop;
    private DataPanel dataPanelBottom;
    private JTextArea dataTextArea;
    private JScrollPane jScrollPaneCenter;

}


