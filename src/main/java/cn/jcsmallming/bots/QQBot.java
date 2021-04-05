package cn.jcsmallming.bots;

import cn.jcsmallming.UserInfo;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

public class QQBot {
    Bot bot = BotFactory.INSTANCE.newBot(UserInfo.qqNum, UserInfo.password, new BotConfiguration() {{
        fileBasedDeviceInfo(); // 使用 device.json 存储设备信息
        setProtocol(MiraiProtocol.ANDROID_PAD); // 切换协议
    }});;
//    Bot bot = BotFactory.INSTANCE.newBot(UserInfo.friend, "mima1996", new BotConfiguration() {{
//        fileBasedDeviceInfo(); // 使用 device.json 存储设备信息
//        setProtocol(MiraiProtocol.ANDROID_PAD); // 切换协议
//    }});;
    public QQBot() {
        // 使用自定义配置
        bot.login();
    }

    public Bot getBot() {
        return bot;
    }
}