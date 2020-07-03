package com.study.selfs.xml;

import java.io.Serializable;

public class CustomConfig implements Serializable{

    private static final long serialVersionUID = 8628268054882147103L;

    private String tableName;

    private String entityPath;

    private String mapperPath;

    public String getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(String entityPath) {
        this.entityPath = entityPath;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
