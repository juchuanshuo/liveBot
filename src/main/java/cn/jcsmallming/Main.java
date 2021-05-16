package cn.jcsmallming;

import cn.jcsmallming.bots.DynamicBot;
import cn.jcsmallming.bots.LiveBot;
import cn.jcsmallming.bots.QQBot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new UserInfo().init();
        QQBot qqBot = new QQBot();
        LiveBot bot = new LiveBot("liveStatusCache", true, UserInfo.roomId, qqBot);
        DynamicBot dynamicBot = new DynamicBot("dynamicListCache", true, UserInfo.userId, qqBot);
        while (true) {
            try {
                bot.getConf().setReadTimeout(1000 * 5);
                dynamicBot.getConf().setReadTimeout(1000 * 5);
                bot.start(1);
                dynamicBot.start(1);
                if (isBelong()) {
                    Thread.sleep(5000);
                } else {
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("出现了些问题");
                Thread.sleep(601000);
            }
        }
    }

    public static boolean isBelong() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("01:00");
            endTime = df.parse("6:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return belongCalendar(now, beginTime, endTime);
    }

    public void start() throws InterruptedException {

    }

    public void crawlOnce(QQBot qqBot) throws Exception {
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }
}
