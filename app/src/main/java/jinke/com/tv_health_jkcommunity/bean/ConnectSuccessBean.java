package jinke.com.tv_health_jkcommunity.bean;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ConnectSuccessBean {
    /**
     * code : 200
     * data : 5
     * result : 操作成功！
     */

    private int code;
    private String data;
    private String result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
