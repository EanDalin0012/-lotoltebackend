package com.loto.lte.core.dto;

public class ResponseData <B>{
    private  Header result;
    private  B body;

    public ResponseData(Header result, B body) {
        this.result = result;
        this.body = body;
    }

    public ResponseData() {
    }

    public Header getResult() {
        return result;
    }

    public void setResult(Header result) {
        this.result = result;
    }

    public B getBody() {
        return body;
    }

    public void setBody(B body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "result=" + result +
                ", body=" + body +
                '}';
    }
}
