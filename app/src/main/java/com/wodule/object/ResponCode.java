package com.wodule.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 20/06/2017.
 */
public class ResponCode {
    @SerializedName("code")
    String code;
    @SerializedName("organization")
    String organization;
    @SerializedName("class")
    String class_school;
    @SerializedName("adviser")
    String adviser;
    @SerializedName("type")
    String _type;

    public String getAdviser() {
        return adviser;
    }

    public String getClass_school() {
        return class_school;
    }

    public String getCode() {
        return code;
    }

    public String getOrganization() {
        return organization;
    }

    public String get_type() {
        return _type;
    }
    public void set_type(String _type) {
        this._type = _type;
    }

    public void setAdviser(String adviser) {
        this.adviser = adviser;
    }

    public void setClass_school(String class_school) {
        this.class_school = class_school;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

}
