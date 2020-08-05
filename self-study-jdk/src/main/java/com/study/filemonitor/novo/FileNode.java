package com.study.filemonitor.novo;

import java.util.List;

public class FileNode {

    private FileNode parentNode;

    private List<FileNode> childNodeList;

    private String currentPath;

    private boolean isFile;

    public FileNode(String currentPath,boolean isFile){
        this.currentPath = currentPath;
        this.isFile = isFile;
    }

    public FileNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(FileNode parentNode) {
        this.parentNode = parentNode;
    }

    public List<FileNode> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<FileNode> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public boolean isFile() {
        return isFile;
    }
}
