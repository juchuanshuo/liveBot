package cn.jcsmallming.bots;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.jcsmallming.entry.dynamic.DynamicApiBody;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicBot extends BreadthCrawler {
    String getGetDynamicsApi = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?visitor_uid=0&host_uid=%d&offset_dynamic_id=0&need_top=0&platform=web";
    QQBot qqBot;
    static int count = 0;
    List<String> old = new ArrayList<>();;
    static String last = "";
    String content = "";
    DynamicApiBody dynamicApiBody;
    DynamicDetailBot contentBot;
    Date currentTime;

    public DynamicBot(String crawlPath, boolean autoParse, int userId,QQBot qqBot) {
        super(crawlPath, autoParse);
        this.addSeed(String.format(getGetDynamicsApi,userId));
        this.qqBot = qqBot;
        setThreads(1);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        content = page.html();
        dynamicApiBody = JSON.parseObject(content, DynamicApiBody.class);
        long timestamp = dynamicApiBody.getData().getCards().get(0).getDesc().getTimestamp();
        String dynamicId = dynamicApiBody.getData().getCards().get(0).getDesc().getDynamic_id();
        currentTime = new Date();
        count++;
        if(count == 1){
            last = dynamicId;
            old.add(dynamicId);
        }
        if(count%1000==0){
            try {
                dbManager.clear();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.gc();
        }
        if(!last.equals(dynamicId)&&!old.contains(dynamicId)){
            System.out.println("监测到新动态，即将进行爬取");
            last = dynamicId;
//            long time = currentTime.getTime()/1000;
//            if((time-timestamp)>30){
//                System.out.println("监测到有新动态，但与当前时间间隔超过30秒，判断为爬取错误，跳过");
//                return;
//            }
            contentBot = new DynamicDetailBot("detailContent",true,dynamicId,qqBot);
            contentBot.getConf().setReadTimeout(1000 * 5);
            old.add(dynamicId);
            try {
                contentBot.start(1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }else{
            System.out.println(String.format("第%d次爬取，没有新动态",count));
        }
    }

//    public static void main(String[] args) throws Exception {
//        QQBot qqBot = new QQBot();
//        int roomId = 21396545;// 团长直播间id
//        int userId = 407106379;//团长uid
//        DynamicBot dynamicBot = new DynamicBot("dynamicList",true,userId,qqBot);
//        dynamicBot.getConf().setReadTimeout(1000 * 5);
//        dynamicBot.start(1);
//    }
}
