package com.wfy.server.utils.security.md5;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fuck on 2017/7/11.
 */
public class MD5 {

    /**
     * 签名字符串
     *
     * @param text
     *            需要签名的字符串
     * @param key
     *            密钥
     * @param charset
     *            编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String charset) throws Exception {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    /**
     * 签名字符串
     *
     * @param text
     *            需要签名的字符串
     * @param sign
     *            签名结果
     * @param key
     *            密钥
     * @param charset
     *            编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String charset) throws Exception {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    public static String calcMd5Sign(Map<String, String> p, String md5Key, String charset) {
        String plain = calculateSignPlain(p);
        String sign = null;
        try {
            sign = sign(plain, md5Key, charset);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return sign;
    }

    public static String calculateSignPlain(Map<String, String> params) {
        Map<String, String> sortedParams = new TreeMap<String, String>(params);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : sortedParams.entrySet()) {
            if (StringUtils.isEmpty(e.getValue())) {
                continue;
            }
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
