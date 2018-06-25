package jinke.com.tv_health_jkcommunity.bean;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ConnectBean {
    /**
     * signType : isRegister
     * data : {"openId":"5566","wsId":"0"}
     */

    private String signType;
    private DataBean data;

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * openId : 5566
         * wsId : 0
         */

        private String openId;
        private String wsId;

        public DataBean(String openId, String wsId) {
            this.openId = openId;
            this.wsId = wsId;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getWsId() {
            return wsId;
        }

        public void setWsId(String wsId) {
            this.wsId = wsId;
        }
    }
}
