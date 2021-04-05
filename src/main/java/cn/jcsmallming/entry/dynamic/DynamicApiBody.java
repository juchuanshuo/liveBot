package cn.jcsmallming.entry.dynamic;

public class DynamicApiBody {
    private String code;
    private String message;
    private DynamicApiData data;

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

    public DynamicApiData getData() {
        return data;
    }

    public void setData(DynamicApiData data) {
        this.data = data;
    }
}
