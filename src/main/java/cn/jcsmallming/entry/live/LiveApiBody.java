package cn.jcsmallming.entry.live;

public class LiveApiBody {
    private String code;
    private String message;
    private LiveApiData data;

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

    public LiveApiData getData() {
        return data;
    }

    public void setData(LiveApiData data) {
        this.data = data;
    }
}
