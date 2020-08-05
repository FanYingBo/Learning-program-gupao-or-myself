package com.study.selfs.jdk8.util.function.supplier;


import java.util.function.Supplier;

/**
 * @since 1.8
 * @see java.util.function.Supplier  可以当做是工厂
 */
public class SupplierDemo {


    public static void main(String[] args) {
//        Runnable aNew = Computer::new;
//        System.out.println(aNew);
        Supplier<Computer> computerSupplier = () -> new Computer("i7-HQ7700", "GTX-1060");
        System.out.println(computerSupplier.get().getCpu());

    }

}
class Computer{

    private String cpu;

    private String gpu;


    Computer(String cpu, String gpu) {
        this.cpu = cpu;
        this.gpu = gpu;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }
}
