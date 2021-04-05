package cn.jcsmallming.entry.dynamic;

import java.util.List;

public class DynamicApiData {
    private int has_more;
    private List<DynamicCard> cards;
    private long next_offset;
    private int _gt_;
    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }
    public int getHas_more() {
        return has_more;
    }

    public void setCards(List<DynamicCard> cards) {
        this.cards = cards;
    }
    public List<DynamicCard> getCards() {
        return cards;
    }

    public void setNext_offset(long next_offset) {
        this.next_offset = next_offset;
    }
    public long getNext_offset() {
        return next_offset;
    }

    public void set_gt_(int _gt_) {
        this._gt_ = _gt_;
    }
    public int get_gt_() {
        return _gt_;
    }
}