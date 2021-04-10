package cn.jcsmallming.bots;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.jcsmallming.UserInfo;
import cn.jcsmallming.entry.dynamiccontent.*;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicDetailBot extends BreadthCrawler {
    String getDynamicDetailApi = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/get_dynamic_detail?dynamic_id=%s";
    QQBot qqBot;
    String content = "";
    DynamicDetailApiBody dynamicDetailApiBody;
    Date currentTime;
    SimpleDateFormat formatter;
    String dateString;
    MessageChain chain;
    String filePath = "images" +File.separator;

    public DynamicDetailBot(String crawlPath, boolean autoParse, String dynamicId, QQBot qqBot) {
        super(crawlPath, autoParse);
        this.addSeed(String.format(getDynamicDetailApi,dynamicId));
        this.qqBot = qqBot;
        setThreads(1);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        content = page.html();
        dynamicDetailApiBody = JSON.parseObject(content, DynamicDetailApiBody.class);
        String card = dynamicDetailApiBody.getData().getCard().getCard();
        DynamicDetailContentCard cardBody = JSON.parseObject(card, DynamicDetailContentCard.class);
        /*
        type
        4:文字动态
        2：带图片动态

        1：转发动态
            orig_type及以上type
            8：分享视频
            4200：分享直播间
         */
        int type =dynamicDetailApiBody.getData().getCard().getDesc().getType();
        long timeStamp = dynamicDetailApiBody.getData().getCard().getDesc().getTimestamp();
        currentTime = new Date(timeStamp*1000);

        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateString = formatter.format(currentTime);
        if(type == 1){
            Oringin oringin = JSON.parseObject(cardBody.getOrigin(), Oringin.class);
            // 转发动态
            int orig_type = cardBody.getItem().getOrig_type();
            if(orig_type==8){
                //转发视频
                chain = new MessageChainBuilder()
                        .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚转发了视频\n配文："))
                        .append(new PlainText(cardBody.getItem().getContent()))
                        .append(new PlainText("\n视频简介：" + oringin.getDesc()))
                        .append(new PlainText("\n视频地址：" + oringin.getShort_link()))
                        .append(new PlainText("\n\n发布时间：" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            }else if(orig_type==2){
                //转发带图片动态
                ContentItem item = oringin.getItem();
                String dyDesc = item.getDescription();
                List<Image> images = new ArrayList<>();
                List<Pictures> pictures =  item.getPictures();
                for(Pictures pic :pictures){
                    BufferedImage image = null;
                    String fileName="";
                    File file = new File(fileName);
                    try {
                        URL url = new URL(pic.getImg_src());
                        image = ImageIO.read(url);
                        String[] temp = pic.getImg_src().split("/");
                        fileName = temp[temp.length-1];
                        file = new File(filePath+fileName);
                        ImageIO.write(image,fileName.split(".")[1] , file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    images.add(qqBot.getBot().getFriend(UserInfo.friend).uploadImage(ExternalResource.create(file)));
                }
                MessageChainBuilder builder =  new MessageChainBuilder()
                .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚转发了带图片的动态"))
                        .append("\n配文：")
                        .append(new PlainText(cardBody.getItem().getContent()))
                        .append(new PlainText("\n原文：" + oringin.getDesc()))
                        .append("\n图片：\n");
                for(Image image : images){
                    builder.append(Image.fromId(image.getImageId()));
                }
                builder.append(new PlainText("\n\n发布时间：" + dateString));
                chain = builder.build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            }else if(orig_type==4200){
                //分享直播间
                chain = new MessageChainBuilder()
                        .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚分享了一个直播间\n配文："))
                        .append(new PlainText(cardBody.getItem().getContent()))
                        .append(new PlainText("\n直播间链接："))
                        .append(new PlainText(oringin.getSlide_link()))
                        .append(new PlainText("\n\n发布时间：" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            }else if(orig_type==4){
                //转发文字动态
                chain = new MessageChainBuilder()
                        .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚转发了一个文字动态\n由于没翻到以前团长转发过的文字动态故没写功能"))
                        .append(new PlainText("\n\n发布时间：" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            } else{
                chain = new MessageChainBuilder()
                        .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚转发了一个未能识别的动态类型"))
                        .append(new PlainText("\n\n发布时间：" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            }
        }else if(type == 2){
            // 发布图片动态
            String dyDesc = cardBody.getItem().getDescription();
            List<Image> images = new ArrayList<>();
            List<Pictures> pictures =  cardBody.getItem().getPictures();
            for(Pictures pic :pictures){
                BufferedImage image = null;
                String fileName="";
                File file = new File(fileName);
                try {
                    URL url = new URL(pic.getImg_src());
                    image = ImageIO.read(url);
                    String[] temp = pic.getImg_src().split("/");
                    fileName = temp[temp.length-1];
                    file = new File(filePath+fileName);
                    ImageIO.write(image, "jpg", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                images.add(qqBot.getBot().getFriend(UserInfo.friend).uploadImage(ExternalResource.create(file)));
            }
            MessageChainBuilder builder =  new MessageChainBuilder()
                    .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚发布了图文动态\n配文："))
                    .append(new PlainText(dyDesc))
                    .append(new PlainText("\n图片：\n"));
            for(Image image : images){
                builder.append(Image.fromId(image.getImageId()));
            }
            builder.append(new PlainText("\n\n发布时间：" + dateString));
            chain = builder.build();
            qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
        } else if(type == 4){
            // 文字动态
            chain = new MessageChainBuilder()
                    .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚发布了新动态\n"))
                    .append(new PlainText(cardBody.getItem().getContent()))
                    .append(new PlainText("\n\n发布时间：" + dateString))
                    .build();
            qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
        }else if(type == 8){
            String shortLink = cardBody.getShort_link();
            chain = new MessageChainBuilder()
                    .append(new PlainText("你老婆【绯赤艾莉欧Official】刚刚发发布了一个投稿视频\n地址："))
                    .append(new PlainText(shortLink))
                    .append(new PlainText("\n\n发布时间：" + dateString))
                    .build();
            qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
        }
    }

//    public static void main(String[] args) throws Exception {
//        new UserInfo().init();
//        QQBot qqBot = new QQBot();
//        DynamicDetailBot dynamicBot = new DynamicDetailBot("dynamicList",true,"511280186893919042",qqBot);
////        DynamicDetailBot dynamicBot = new DynamicDetailBot("dynamicList",true,"510448887377493156",qqBot);
//        dynamicBot.getConf().setReadTimeout(1000 * 5);
//        while (true){
//            dynamicBot.start(1);
//            Thread.sleep(3000);
//        }
//    }
}
