package cn.jcsmallming.entry.dynamiccontent;

public class DynamicDetailCard {
    private String card;
    public void setCard(String card) {
        this.card = card;
    }
    public String getCard() {
        return card;
    }
    public Desc getDesc() {
        return desc;
    }

    public void setDesc(Desc desc) {
        this.desc = desc;
    }

    private Desc desc;
}