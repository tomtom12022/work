package com.work.app.object;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Response<T> {

    private String responseCode;

    private String responseMessage;

    private T responseResult;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(T responseResult) {
        this.responseResult = responseResult;
    }

}
