package com.wfy.server.utils.security.algorithm;

import java.math.BigInteger;

/**
 * Created by fuck on 2017/7/11.
 */
public class CoderTest {

    public static void main(String[] args) throws Exception{
        String inputStr = "简单加密";
        System.err.println("原文:\n" + inputStr);

        byte[] inputData = inputStr.getBytes();
        String code = Coder.encryptBASE64(inputData);

        System.err.println("BASE64加密后:\n" + code);

        byte[] output = Coder.decryptBASE64(code);

        String outputStr = new String(output);

        System.err.println("BASE64解密后:\n" + outputStr);


        // 验证MD5对于同一内容加密是否一致
        System.err.println(Coder.encryptMD5(inputData));
        System.err.println(Coder.encryptMD5(inputData));

        // 验证SHA对于同一内容加密是否一致
        System.err.println(Coder.encryptSHA(inputData));
        System.err.println(Coder.encryptSHA(inputData));

        String key = Coder.initMacKey();
        System.err.println("Mac密钥:\n" + key);

        // 验证HMAC对于同一内容，同一密钥加密是否一致
        System.err.println(Coder.encryptHMAC(inputData, key));
        System.err.println(Coder.encryptHMAC(inputData, key));

        BigInteger md5 = new BigInteger(Coder.encryptMD5(inputData));
        System.err.println("MD5:\n" + md5.toString(16));

        BigInteger sha = new BigInteger(Coder.encryptSHA(inputData));
        System.err.println("SHA:\n" + sha.toString(32));

        BigInteger mac = new BigInteger(Coder.encryptHMAC(inputData, inputStr));
        System.err.println("HMAC:\n" + mac.toString(16));
    }
}
