package com.hdh.smartlock.util;

/**
 * @ClassName ResultMsg
 * @Description TODO 接口返回数据封装类
 * @Author Kikityer
 * @Date 2019/5/6 20:48
 * @Version 1.0.0
 **/
public class ResultMsg {

    private static final String OK = "ok";
    private static final String ERROR = "error";
    private ResultMsg.Meta meta;
    private Object data;

    public ResultMsg() {}

    public ResultMsg success() {
        this.meta = new ResultMsg.Meta(true,"ok");
        return this;
    }

    public ResultMsg success(Object data) {
        this.meta = new ResultMsg.Meta(true,"ok");
        this.data = data;
        return this;
    }

    public ResultMsg failure() {
        this.meta = new ResultMsg.Meta(false, "error");
        return this;
    }

    public ResultMsg failure(String message) {
        this.meta = new ResultMsg.Meta(false, message);
        return this;
    }

    public ResultMsg failure(String message, Object data) {
        this.meta = new ResultMsg.Meta(false, message);
        this.data = data;
        return this;
    }

    public ResultMsg.Meta getMeta() {
        return meta;
    }

    public void setMeta(ResultMsg.Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class Meta {
        private boolean success;
        private String message;

        public Meta() {}

        public Meta(boolean success) {
            this.success = success;
        }

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public boolean getSuccess() {
            return this.success;
        }
    }
}
