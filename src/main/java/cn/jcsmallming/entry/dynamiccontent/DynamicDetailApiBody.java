package cn.jcsmallming.entry.dynamiccontent;

public class DynamicDetailApiBody {
    private String code;
    private String message;
    private DynamicDetailApiData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DynamicDetailApiData getData() {
        return data;
    }

    public void setData(DynamicDetailApiData data) {
        this.data = data;
    }
}
