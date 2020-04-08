package com.study.jdk8.util.concurrent.countedcompleter;


import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @see java.util.concurrent.CountedCompleter
 */
public class CountedCompleterDemo {

    public static void main(String[] args) {
        Searcher searcher = new Searcher(new AtomicReference<String>(), new String[]{"jsac", "tom", "hhu", "xml","ddd"}, 0, 4);
        String invoke = searcher.putThings("jsac").invoke();
        System.out.println(invoke);
    }
}