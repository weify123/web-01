package com.wfy.server.utils.security.algorithm;

import java.util.Map;

/**
 * Created by fuck on 2017/7/11.
 */
public class RSACoderTest {

    private static String publicKey;
    private static String privateKey;

    public static void main(String[] args) throws Exception{

        Map<String, Object> keyMap = RSACoder.initKey();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);


        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
                privateKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);




        System.err.println("私钥加密——公钥解密");
        String inputStr1 = "sign";
        byte[] data1 = inputStr.getBytes();

        byte[] encodedData1 = RSACoder.encryptByPrivateKey(data1, privateKey);

        byte[] decodedData1 = RSACoder
                .decryptByPublicKey(encodedData1, publicKey);

        String outputStr1 = new String(decodedData1);
        System.err.println("加密前: " + inputStr1 + "\n\r" + "解密后: " + outputStr1);





        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:\r" + status);


    }
}
