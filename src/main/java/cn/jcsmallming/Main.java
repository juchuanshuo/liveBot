package cn.jcsmallming;

import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BerkeleyDBManager;
import cn.jcsmallming.bots.DynamicBot;
import cn.jcsmallming.bots.LiveBot;
import cn.jcsmallming.bots.QQBot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new UserInfo().init();
        new Main().start();
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

    public void start() throws InterruptedException {
        QQBot qqBot = new QQBot();
        while (true){
            try{
                crawlOnce(qqBot);
//                if(isBelong()){
//                    Thread.sleep(10000);
//                }else{
//                    Thread.sleep(3000);
//                }
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("出现了些问题");
                Thread.sleep(601000);
            }
        }
    }
    public void crawlOnce(QQBot qqBot) throws Exception {
        LiveBot bot = new LiveBot("crawl",true,UserInfo.roomId,qqBot);
        bot.getConf().setReadTimeout(1000 * 5);
        DynamicBot dynamicBot = new DynamicBot("dynamicList",true,UserInfo.userId,qqBot);
        dynamicBot.getConf().setReadTimeout(1000 * 5);
        dynamicBot.setDBManager(new BerkeleyDBManager("dynamicList"));
        bot.start(1);
        dynamicBot.start(1);
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
