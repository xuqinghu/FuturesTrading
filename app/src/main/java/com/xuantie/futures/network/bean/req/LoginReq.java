package com.xuantie.futures.network.bean.req;

/**
 * Created by Administrator on 2018/9/25.
 */

public class LoginReq {

    /**
     * channel : app
     * content : {"userAccountNo":"admin","userPassword":"45678","deviceId":null,"code":"ASMB"}
     */

    private String channel;
    private ContentBean content;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * userAccountNo : admin
         * userPassword : 45678
         * deviceId : null
         * code : ASMB
         */

        private String userAccountNo;
        private String userPassword;
        private Object deviceId;
        private String code;

        public String getUserAccountNo() {
            return userAccountNo;
        }

        public void setUserAccountNo(String userAccountNo) {
            this.userAccountNo = userAccountNo;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
