package com.study.selfs.jdk5.lang.iterable;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
/**
 *
 * Iterable JDK 1.5
 *
 */
public class IterableChild implements Iterable{
    @Override
    public void forEach(Consumer action) {


    }

    @Override
    public Spliterator spliterator() {
        return null;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
