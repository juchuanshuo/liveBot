package cn.jcsmallming.entry.dynamiccontent;

public class DynamicDetailContentCard {

    public ContentItem getItem() {
        return item;
    }

    public void setItem(ContentItem item) {
        this.item = item;
    }

    private ContentItem item;

    private String origin;

    public String getShort_link() {
        return short_link;
    }

    public void setShort_link(String short_link) {
        this.short_link = short_link;
    }

    private String short_link;
    public String getOrigin() {

        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}