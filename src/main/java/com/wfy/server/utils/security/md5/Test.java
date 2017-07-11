package com.wfy.server.utils.security.md5;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuck on 2017/7/11.
 */
public class Test {

    public static void main(String[] args){

        String text = "aaa";
        String key = "weifeiyu";
        String charset = "utf-8";

        try {
            String s = MD5.sign(text, key, charset);
            System.out.println("----------->" + s);

            System.out.println(MD5.verify(text, "699b0aafa372d2726c36a3787b9793d1", key, charset));

            Map<String, String> map = new HashMap<String, String>();
            map.put("name", text);
            String sm = MD5.calcMd5Sign(map, key, charset);
            System.out.println("----------->>" + sm);

            System.out.println(MD5.verify(MD5.calculateSignPlain(map), "b23d4353ee7c04188beed3d984391cd1", key, charset));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
