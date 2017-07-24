package com.wodule.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 30/06/2017.
 */
public class ResponError {
    @SerializedName("error")
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
