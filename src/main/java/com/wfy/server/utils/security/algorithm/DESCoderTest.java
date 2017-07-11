package com.wfy.server.utils.security.algorithm;

/**
 * Created by fuck on 2017/7/11.
 */
public class DESCoderTest {

    public static void main(String[] args) throws Exception{
        String inputStr = "DES";
        String key = DESCoder.initKey();
        System.err.println("原文:\t" + inputStr);

        System.err.println("密钥:\t" + key);

        byte[] inputData = inputStr.getBytes();
        inputData = DESCoder.encrypt(inputData, key);

        System.err.println("加密后:\t" + DESCoder.encryptBASE64(inputData));

        byte[] outputData = DESCoder.decrypt(inputData, key);
        String outputStr = new String(outputData);

        System.err.println("解密后:\t" + outputStr);

    }

}
