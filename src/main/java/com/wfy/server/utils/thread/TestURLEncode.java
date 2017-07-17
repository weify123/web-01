package com.wfy.server.utils.thread;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fuck on 2017/7/17.
 */
public class TestURLEncode {

    private static final String char_set = "utf-8";

    private static final String SPECIAL_CHAR = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    public static void main(String[] args){

        try {

            Pattern p = Pattern.compile(SPECIAL_CHAR);
            Matcher m = p.matcher("1234qwer");
            System.out.println(m.find());

            String encode = URLEncoder.encode(URLEncoder.encode("魏飞宇@$%", char_set), char_set);
            System.out.println(encode);

            String decode = URLDecoder.decode(URLDecoder.decode(encode, char_set), char_set);
            System.out.println(decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
