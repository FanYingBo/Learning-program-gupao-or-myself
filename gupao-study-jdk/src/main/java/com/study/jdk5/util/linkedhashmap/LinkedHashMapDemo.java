package com.study.jdk5.util.linkedhashmap;


import java.util.Map;
import java.util.LinkedHashMap;

/**
 *
 *
 * @since 1.4
 * @see java.util.LinkedHashMap#removeEldestEntry(Map.Entry)
 * 通过重写removeEldestEntry方法可以实现lru：如果一个数据在最近一段时间没有被访问到，那么在将来它被访问的可能性也很小
 * 通过定义 构造方法中的accessOrder参数（false：插入顺序，true：读取顺序）实现
 */
public class LinkedHashMapDemo {

    public static void main(String[] args) {
        LruCache lruCache = new LruCache();
        lruCache.putObject(21,22);
        lruCache.putObject(22,29);
        lruCache.putObject(23,29);
        lruCache.putObject(2,29);
        lruCache.putObject(12,29);
        lruCache.putObject(29,29);
        lruCache.putObject(20,29);
        lruCache.putObject(26,29);
        lruCache.putObject(30,29);
        lruCache.putObject(32,29);
        lruCache.putObject(39,29);
        lruCache.printThis();
        System.out.println(lruCache.getEldestKey());

    }
}

class LruCache{

    private Map<Object,Object> linkedCache;

    private Object eldestKey;

    public LruCache() {
        setSize(10);
    }

    private void setSize(int size){
        this.linkedCache = new LinkedHashMap<Object, Object>(size,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                boolean isRemove = size() > size;
                if(isRemove){
                    eldestKey = eldest.getKey();
                }
                return isRemove;
            }
        };
    }
    public void putObject(Object key,Object value){
        linkedCache.put(key,value);
    }

    public Object get(Object key){
        return this.linkedCache.get(key);
    }

    public void remove(Object key){
        this.linkedCache.remove(key);
    }

    public Object getEldestKey(){
        return eldestKey;
    }


    public void printThis(){
        System.out.println(this.linkedCache);
    }
}
