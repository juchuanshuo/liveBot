package cn.jcsmallming.entry.dynamiccontent;

public class Oringin {
    private long aid;
    private int attribute;
    private long cid;
    private int copyright;
    private long ctime;
    //视频描述
    private String desc;
    private int duration;
    private String dynamic;
    private String jump_url;
    private String pic;
    private String player_info;
    private long pubdate;
    // 视频地址
    private String short_link;
    private int state;
    private int tid;
    private String title;
    private String tname;
    private ContentItem item;

    public ContentItem getItem() {
        return item;
    }

    public void setItem(ContentItem item) {
        this.item = item;
    }

    public String getSlide_link() {
        return slide_link;
    }

    public void setSlide_link(String slide_link) {
        this.slide_link = slide_link;
    }

    private int videos;
    private String slide_link;
    public void setAid(long aid) {
        this.aid = aid;
    }
    public long getAid() {
        return aid;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }
    public int getAttribute() {
        return attribute;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }
    public long getCid() {
        return cid;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }
    public int getCopyright() {
        return copyright;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }
    public long getCtime() {
        return ctime;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }
    public String getDynamic() {
        return dynamic;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }
    public String getJump_url() {
        return jump_url;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getPic() {
        return pic;
    }

    public void setPlayer_info(String player_info) {
        this.player_info = player_info;
    }
    public String getPlayer_info() {
        return player_info;
    }

    public void setPubdate(long pubdate) {
        this.pubdate = pubdate;
    }
    public long getPubdate() {
        return pubdate;
    }

    public void setShort_link(String short_link) {
        this.short_link = short_link;
    }
    public String getShort_link() {
        return short_link;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
    public int getTid() {
        return tid;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
    public String getTname() {
        return tname;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }
    public int getVideos() {
        return videos;
    }

}
