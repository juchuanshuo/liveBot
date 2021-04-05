package cn.jcsmallming;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserInfo {
    public static String f;
    public static int roomId;
    public static int userId;
    public static int qqNum = 1;
    public static String password;
    public static int friend;
    public static int group;
    public static Properties props = new Properties();
    static {
        f = "src/main/resources/userinfo.properties";
        try {
            props.load(new FileInputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        roomId = Integer.valueOf(props.getProperty("roomId"));
        userId = Integer.valueOf(props.getProperty("userId"));
        qqNum = Integer.valueOf(props.getProperty("qqNum"));
        group = Integer.valueOf(props.getProperty("group"));
        friend = Integer.valueOf(props.getProperty("friend"));
        password = props.getProperty("password");
    }

    public static void main(String[] args) {
        System.out.println(UserInfo.roomId);
        System.out.println(UserInfo.userId);
        System.out.println(UserInfo.qqNum);
        System.out.println(UserInfo.password);
    }
}
