package com.wodule.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 20/06/2017.
 */
public class ExamObject {
    @SerializedName("id")
    int id;
    @SerializedName("part1")
    String part1;
    @SerializedName("part2_text")
    String part2_text;
    @SerializedName("part2_url")
    String part2_url;
    @SerializedName("part3_text")
    String part3_text;
    @SerializedName("part4_text")
    String part4_text;
    @SerializedName("part4_url")
    String part4_url;
    @SerializedName("part3_url")
    String part3_url;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPart1() {
        return part1;
    }

    public String getPart2_text() {
        return part2_text;
    }

    public String getPart2_url() {
        return part2_url;
    }

    public String getPart3_text() {
        return part3_text;
    }

    public String getPart3_url() {
        return part3_url;
    }

    public String getPart4_text() {
        return part4_text;
    }

    public String getPart4_url() {
        return part4_url;
    }

    public void setPart1(String part1) {
        this.part1 = part1;
    }

    public void setPart2_text(String part2_text) {
        this.part2_text = part2_text;
    }

    public void setPart2_url(String part2_url) {
        this.part2_url = part2_url;
    }

    public void setPart3_text(String part3_text) {
        this.part3_text = part3_text;
    }

    public void setPart3_url(String part3_url) {
        this.part3_url = part3_url;
    }

    public void setPart4_text(String part4_text) {
        this.part4_text = part4_text;
    }

    public void setPart4_url(String part4_url) {
        this.part4_url = part4_url;
    }
}
