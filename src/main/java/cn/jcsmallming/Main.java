package cn.jcsmallming;

import cn.jcsmallming.bots.DynamicBot;
import cn.jcsmallming.bots.LiveBot;
import cn.jcsmallming.bots.QQBot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        QQBot qqBot = new QQBot();
        LiveBot bot = new LiveBot("crawl",true,UserInfo.roomId,qqBot);
        bot.getConf().setReadTimeout(1000 * 5);
        DynamicBot dynamicBot = new DynamicBot("dynamicList",true,UserInfo.userId,qqBot);
        dynamicBot.getConf().setReadTimeout(1000 * 5);
        while (true){
            try{
                bot.start(1);
                dynamicBot.start(1);
                if(isBelong()){
                    Thread.sleep(10000);
                }else{
                    Thread.sleep(3000);
                }

            }catch (Exception e){
                System.out.println("看来被ban了呢,休息一会吧");
                Thread.sleep(601000);
            }
        }
    }
    public static boolean isBelong(){

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("00:00");
            endTime = df.parse("6:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return belongCalendar(now, beginTime, endTime);
    }


    /**
     * 判断时间是否在时间段内
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

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
