package com.news.util;

import java.util.*;

import org.junit.Test;

public class UserTokenUtil {
    private static HashMap<String, String> userMap = new HashMap<>();
    private static String lifeCylce = "1800";//半个钟

    public static void setUserSession(String userid) {
        //生成过期时间
        long expireTime = System.currentTimeMillis() + Integer.parseInt(lifeCylce) * 1000;
        userMap.put(userid, expireTime + "");
        //清除已过期user
        long nowTime = System.currentTimeMillis();
        if (System.currentTimeMillis() % 1800000 == 0) {
            cleanExpireSession();
        }
    }

    public static void delUserSession(String userid) {
        userMap.remove(userid);
    }

    public static boolean getUserSession(String userid) {
        boolean flag = false;
        //获取过期时间
        String userExpireTime = userMap.get(userid);
        if (userExpireTime != null) {
            long expireTime = Long.parseLong(userExpireTime);
            //判断当前用户是否过期
            if (System.currentTimeMillis() > expireTime) {
                flag = false;
            } else {
                flag = true;
            }
        }
        return flag;
    }

    @Test
    public void testget() {
        String userid = "test";
        long nowtime = System.currentTimeMillis();
        setUserSession(userid);
        System.out.println(getUserSession(userid));
    }

    public static void cleanExpireSession() {
        for (Iterator<Map.Entry<String, String>> it = userMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> item = it.next();
            long expireTime = Long.parseLong(userMap.get(item.getKey()));
            if (System.currentTimeMillis() > expireTime) {
                it.remove();
            }
        }
    }
}
