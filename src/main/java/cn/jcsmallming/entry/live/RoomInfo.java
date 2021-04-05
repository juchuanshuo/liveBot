package cn.jcsmallming.entry.live;

public class RoomInfo {
    private long uid;
    private long room_id;
    private int short_id;
    private String title;
    private String cover;
    private String tags;
    private String background;
    private String description;
    private int live_status;
    private int live_start_time;
    private int live_screen_type;
    private int lock_status;
    private int lock_time;
    private int hidden_status;
    private int hidden_time;
    private int area_id;
    private int online;
    public void setUid(long uid) {
        this.uid = uid;
    }
    public long getUid() {
         return uid;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }
    public long getRoom_id() {
        return room_id;
    }

    public void setShort_id(int short_id) {
        this.short_id = short_id;
    }
    public int getShort_id() {
        return short_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getCover() {
        return cover;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getTags() {
        return tags;
    }

    public void setBackground(String background) {
        this.background = background;
    }
    public String getBackground() {
        return background;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setLive_status(int live_status) {
        this.live_status = live_status;
    }
    public int getLive_status() {
        return live_status;
    }

    public void setLive_start_time(int live_start_time) {
        this.live_start_time = live_start_time;
    }
    public int getLive_start_time() {
        return live_start_time;
    }

    public void setLive_screen_type(int live_screen_type) {
        this.live_screen_type = live_screen_type;
    }
    public int getLive_screen_type() {
        return live_screen_type;
    }

    public void setLock_status(int lock_status) {
        this.lock_status = lock_status;
    }
    public int getLock_status() {
        return lock_status;
    }

    public void setLock_time(int lock_time) {
        this.lock_time = lock_time;
    }
    public int getLock_time() {
        return lock_time;
    }

    public void setHidden_status(int hidden_status) {
        this.hidden_status = hidden_status;
    }
    public int getHidden_status() {
        return hidden_status;
    }

    public void setHidden_time(int hidden_time) {
        this.hidden_time = hidden_time;
    }
    public int getHidden_time() {
        return hidden_time;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }
    public int getArea_id() {
        return area_id;
    }

    public void setOnline(int online) {
        this.online = online;
    }
    public int getOnline() {
        return online;
    }
}