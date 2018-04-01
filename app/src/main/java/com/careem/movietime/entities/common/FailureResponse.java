package com.careem.movietime.entities.common;

import java.io.Serializable;



public class FailureResponse implements Serializable {

    /**
     * status_message : Invalid API key: You must be granted a valid key.
     * success : false
     * status_code : 7
     */

    private String status_message;
    private boolean success;
    private int status_code;

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
