package org.cloud.activiti.security;

import org.cloud.activiti.util.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class PasswordHash implements InitializingBean {
    private String algorithmName;
    private int hashIterations;

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(algorithmName, "algorithmName mast be MD5、SHA-1、SHA-256、SHA-384、SHA-512");
    }

    public String toHex(Object source, Object salt) {
        return DigestUtils.hashByShiro(algorithmName, source, salt, hashIterations);
    }

    public static void main(String[] args) {
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.setAlgorithmName("md5");
        passwordHash.setHashIterations(1);
        String salt = "8888";
        String pwd = passwordHash.toHex("123456", salt);
        System.out.println(pwd);
    }

}
