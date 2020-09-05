package com.study.self.util.encrypt;

public enum  AlgorithmEnum {

    SHA_256("SHA-256"),
    SHA_512("SHA-512"),
    SHA_1("SHA-1"),
    MD5("MD5")

    ;
    private String algorithm;

    public String getAlgorithm() {
        return algorithm;
    }

    AlgorithmEnum(String algorithm){
        this.algorithm = algorithm;
    }

}
