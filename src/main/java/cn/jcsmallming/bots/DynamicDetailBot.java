package cn.jcsmallming.bots;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.jcsmallming.UserInfo;
import cn.jcsmallming.entry.dynamiccontent.*;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
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
    String filePath = "./images/";

    public DynamicDetailBot(String crawlPath, boolean autoParse, String dynamicId, QQBot qqBot) {
        super(crawlPath, autoParse);
        this.addSeed(String.format(getDynamicDetailApi, dynamicId));
        this.qqBot = qqBot;
        setThreads(1);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        content = page.html();
        dynamicDetailApiBody = JSON.parseObject(content, DynamicDetailApiBody.class);
        String card = "";
        try {
            card = dynamicDetailApiBody.getData().getCard().getCard();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            try {
                this.start(1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return;
        }
        DynamicDetailContentCard cardBody = JSON.parseObject(card, DynamicDetailContentCard.class);
        /*
        type
        4:????????????
        2??????????????????

        1???????????????
            orig_type?????????type
            8???????????????
            4200??????????????????
         */
        int type = dynamicDetailApiBody.getData().getCard().getDesc().getType();
        long timeStamp = dynamicDetailApiBody.getData().getCard().getDesc().getTimestamp();
        currentTime = new Date(timeStamp * 1000);

        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateString = formatter.format(currentTime);
        if (type == 1) {
            Oringin oringin = JSON.parseObject(cardBody.getOrigin(), Oringin.class);
            // ????????????
            int orig_type = cardBody.getItem().getOrig_type();
            if (orig_type == 8) {
                //????????????
                chain = new MessageChainBuilder()
                        .append(new PlainText("???????????????????????????Official????????????????????????\n?????????"))
                        .append(new PlainText(cardBody.getItem().getContent()))
                        .append(new PlainText("\n???????????????" + oringin.getDesc()))
                        .append(new PlainText("\n???????????????" + oringin.getShort_link()))
                        .append(new PlainText("\n\n???????????????" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            } else if (orig_type == 2) {
                //?????????????????????
                ContentItem item = oringin.getItem();
                String dyDesc = item.getDescription();
                List<Image> images = new ArrayList<>();
                List<Pictures> pictures = item.getPictures();
                for (Pictures pic : pictures) {
                    BufferedImage image = null;
                    String fileName = "";
                    File file;
                    try {
                        URL url = new URL(pic.getImg_src());
                        image = ImageIO.read(url);
                        String[] temp = pic.getImg_src().split("/");
                        fileName = temp[temp.length - 1];
                        file = new File(filePath + fileName);
                        String[] format = fileName.split("\\.");
                        String formatName = format.length > 1 ? format[format.length - 1] : "jpg";
                        ImageIO.write(image, formatName, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            this.start(1);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        return;
                    }
                    images.add(qqBot.getBot().getFriend(UserInfo.friend).uploadImage(ExternalResource.create(file)));
                }
                MessageChainBuilder builder = new MessageChainBuilder()
                        .append(new PlainText("???????????????????????????Official????????????????????????????????????"))
                        .append("\n?????????")
                        .append(new PlainText(cardBody.getItem().getContent()))
                        .append(new PlainText("\n?????????" + oringin.getItem().getDescription()))
                        .append("\n?????????\n");
                for (Image image : images) {
                    builder.append(Image.fromId(image.getImageId()));
                }
                builder.append(new PlainText("\n\n???????????????" + dateString));
                chain = builder.build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            } else if (orig_type == 4200) {
                //???????????????
                chain = new MessageChainBuilder()
                        .append(new PlainText("???????????????????????????Official?????????????????????????????????\n?????????"))
                        .append(new PlainText(cardBody.getItem().getContent()))
                        .append(new PlainText("\n??????????????????"))
                        .append(new PlainText(oringin.getSlide_link()))
                        .append(new PlainText("\n\n???????????????" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            } else if (orig_type == 4) {
                //??????????????????
                chain = new MessageChainBuilder()
                        .append(new PlainText("???????????????????????????Official????????????????????????????????????\n??????????????????????????????????????????????????????????????????"))
                        .append(new PlainText("\n\n???????????????" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            } else {
                chain = new MessageChainBuilder()
                        .append(new PlainText("???????????????????????????Official???????????????????????????????????????????????????"))
                        .append(new PlainText("\n\n???????????????" + dateString))
                        .build();
                qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
            }
        } else if (type == 2) {
            // ??????????????????
            String dyDesc = cardBody.getItem().getDescription();
            List<Image> images = new ArrayList<>();
            List<Pictures> pictures = cardBody.getItem().getPictures();
            for (Pictures pic : pictures) {
                BufferedImage image = null;
                String fileName = "";
                File file;
                try {
                    URL url = new URL(pic.getImg_src());
                    image = ImageIO.read(url);
                    String[] temp = pic.getImg_src().split("/");
                    fileName = temp[temp.length - 1];
                    file = new File(filePath + fileName);
                    String[] format = fileName.split("\\.");
                    String formatName = format.length > 1 ? format[format.length - 1] : "jpg";
                    ImageIO.write(image, formatName, file);
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        this.start(1);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return;
                }
                images.add(qqBot.getBot().getFriend(UserInfo.friend).uploadImage(ExternalResource.create(file)));
            }
            MessageChainBuilder builder = new MessageChainBuilder()
                    .append(new PlainText("???????????????????????????Official??????????????????????????????\n?????????"))
                    .append(new PlainText(dyDesc))
                    .append(new PlainText("\n?????????\n"));
            for (Image image : images) {
                builder.append(Image.fromId(image.getImageId()));
            }
            builder.append(new PlainText("\n\n???????????????" + dateString));
            chain = builder.build();
            qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
        } else if (type == 4) {
            // ????????????
            chain = new MessageChainBuilder()
                    .append(new PlainText("???????????????????????????Official???????????????????????????\n"))
                    .append(new PlainText(cardBody.getItem().getContent()))
                    .append(new PlainText("\n\n???????????????" + dateString))
                    .build();
            qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
        } else if (type == 8) {
            String shortLink = cardBody.getShort_link();
            chain = new MessageChainBuilder()
                    .append(new PlainText("???????????????????????????Official???????????????????????????????????????\n?????????"))
                    .append(new PlainText(shortLink))
                    .append(new PlainText("\n\n???????????????" + dateString))
                    .build();
            qqBot.getBot().getGroup(UserInfo.group).sendMessage(chain);
        }
    }

//    public static void main(String[] args) throws Exception {
//        new UserInfo().init();
//        QQBot qqBot = new QQBot();
//        DynamicDetailBot dynamicBot = new DynamicDetailBot("dynamicList",true,"512440871737997080",qqBot);
////        DynamicDetailBot dynamicBot = new DynamicDetailBot("dynamicList",true,"510448887377493156",qqBot);
//        dynamicBot.getConf().setReadTimeout(1000 * 5);
//        dynamicBot.start(1);
//    }
}
