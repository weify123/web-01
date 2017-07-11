package com.wfy.server.utils.security.algorithm;

/**
 * Created by fuck on 2017/7/11.
 */
public class CertificateCoderTest {

    private static String password = "123456";
    private static String alias = "www.zlex.org";
    private static String certificatePath = "G:/Certificate/zlex.cer";
    private static String keyStorePath = "G:/Certificate/zlex.keystore";

    public static void main(String[] args) throws Exception{

        System.err.println("公钥加密——私钥解密");
        String inputStr = "Ceritifcate";
        byte[] data = inputStr.getBytes();

        byte[] encrypt = CertificateCoder.encryptByPublicKey(data,
                certificatePath);

        byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt,
                keyStorePath, alias, password);
        String outputStr = new String(decrypt);

        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

        // 验证数据一致
        System.err.println(data);
        System.err.println(decrypt);

        // 验证证书有效
        System.err.println(CertificateCoder.verifyCertificate(certificatePath));



        System.err.println("私钥加密——公钥解密");

        String inputStr1 = "sign";
        byte[] data1 = inputStr1.getBytes();

        byte[] encodedData = CertificateCoder.encryptByPrivateKey(data1,
                keyStorePath, alias, password);

        byte[] decodedData = CertificateCoder.decryptByPublicKey(encodedData,
                certificatePath);

        String outputStr1 = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr1);

        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = CertificateCoder.sign(encodedData, keyStorePath, alias,
                password);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = CertificateCoder.verify(encodedData, sign,
                certificatePath);
        System.err.println("状态:\r" + status);

    }
}
