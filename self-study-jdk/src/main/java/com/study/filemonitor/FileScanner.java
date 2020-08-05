package com.study.filemonitor;

import com.study.filemonitor.novo.FileNode;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileScanner {

    private String path;

    private Map<String,List<FileNode>> parentFileNodeMap = new ConcurrentHashMap<>();

    private Map<String,FileNode> childFileNodeMap = new ConcurrentHashMap<>();

    public FileScanner(String path){
        this.path = path;
        setParentAndStart();
    }

    private void scannerFile(File path){
        File[] files = path.listFiles();
        for(int index=0;index < files.length;index++){
            if(files[index].isDirectory()){
                getParentAndSetChild(files[index],Boolean.FALSE);
                scannerFile(files[index]);
            }else{
                getParentAndSetChild(files[index],Boolean.TRUE);
            }
        }
    }

    private void getParentAndSetChild(File file,boolean isFile){
        String parentPath = file.getParent();
        FileNode fileNode = childFileNodeMap.get(parentPath);
        FileNode childFileNode = new FileNode(file.getAbsolutePath(),isFile);
        childFileNode.setParentNode(fileNode);
        if(fileNode != null){
            List<FileNode> childNodeList = fileNode.getChildNodeList();
            if(childNodeList == null) {
                childNodeList = new LinkedList<>();
            }
            childNodeList.add(childFileNode);
            fileNode.setChildNodeList(childNodeList);
        }
        childFileNodeMap.put(file.getAbsolutePath(),childFileNode);
    }


    private void setParentAndStart(){
        File monitorFilePath = new File(this.path);
        if(monitorFilePath.isFile()){
            return;
        }
        FileNode fileNode = new FileNode(path,true);
        childFileNodeMap.put(path,fileNode);
        scannerFile(monitorFilePath);
    }
}
