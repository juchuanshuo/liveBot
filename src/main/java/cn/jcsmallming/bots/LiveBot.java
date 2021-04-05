package cn.jcsmallming.bots;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.jcsmallming.UserInfo;
import cn.jcsmallming.entry.live.LiveApiBody;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.message.data.AtAll;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LiveBot extends BreadthCrawler {
    String liveInfoApi = "https://api.live.bilibili.com/xlive/web-room/v1/index/getInfoByRoom?room_id=%d";
    int LIVE_ON = 1;
    QQBot qqBot;
    //    static int LIVE_OFF = 0; 未开播状态有0和2
    Boolean IS_ON_LIVE = false;
    int count = 1;
    CloseableHttpClient httpclient = HttpClients.createDefault();
//    HttpGet httpget;
    String content = "";
    int liveStatus=0;
    LiveApiBody liveApiBody;
    String title="";
//    CloseableHttpResponse response;

    Date currentTime;
    SimpleDateFormat formatter;
    String dateString;
    MessageChain chain;

    public LiveBot(String crawlPath, boolean autoParse,int roomId,QQBot qqBot) {
        super(crawlPath, autoParse);
        this.addSeed(String.format(liveInfoApi,roomId));
        this.qqBot = qqBot;
        setThreads(1);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        content = page.html();
        liveApiBody = JSON.parseObject(content, LiveApiBody.class);
        if("412".equals(liveApiBody.getCode())){
            System.out.println("看來访问过于频繁被ban了，休息一会吧");
            try {
                Thread.sleep(3601000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        liveStatus = liveApiBody.getData().getRoom_info().getLive_status();
        title = liveApiBody.getData().getRoom_info().getTitle();
        if(count%1000==0){
            qqBot.getBot().getFriend(UserInfo.friend).sendMessage("定时发消息保持在线");
            try {
                dbManager.clear();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.gc();
        }
        synchronized(IS_ON_LIVE) {
            System.out.println("第" + count + "次监测");
            count ++;
            if(liveStatus==LIVE_ON&&!IS_ON_LIVE){
                IS_ON_LIVE=true;
                System.out.println("监测到开播");
                currentTime = new Date();
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateString = formatter.format(currentTime);
                chain = new MessageChainBuilder()
                        .append(AtAll.INSTANCE)
                        .append(new PlainText("\n快醒醒，你老婆【绯赤艾莉欧Official】开播啦"))
                        .append(new PlainText("\n\n开播时间：" + dateString))
                        .append("\n\n直播标题："+title) // 会被构造成 PlainText 再添加, 相当于上一行
                        .append("\n直播地址：https://live.bilibili.com/21396545")
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
                IS_ON_LIVE = true;
            }else if(liveStatus!=LIVE_ON&&!IS_ON_LIVE){
                System.out.println("尚未开播，继续监测中");
            }
            else if(liveStatus!=LIVE_ON&&IS_ON_LIVE){
                System.out.println("你老婆下播了");
                IS_ON_LIVE = false;
            }else{
                System.out.println("正在直播，继续监测");
            }
        }
    }
}
