package com.wodule.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 21/06/2017.
 */
public class ResponResult {
    @SerializedName("id")
    int id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("examiner_id")
    String examiner_id;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("part_1")
    String part_1;
    @SerializedName("part_1_score")
    String part_1_score;
    @SerializedName("part_1_comment")
    String part_1_comment;
    @SerializedName("part_2")
    String part_2;
    @SerializedName("part_2_score")
    String part_2_score;
    @SerializedName("part_2_comment")
    String part_2_comment;
    @SerializedName("part_3")
    String part_3;
    @SerializedName("part_3_score")
    String part_3_score;
    @SerializedName("part_3_comment")
    String part_3_comment;
    @SerializedName("part_4")
    String part_4;
    @SerializedName("part_4_score")
    String part_4_score;
    @SerializedName("part_4_comment")
    String part_4_comment;

    @SerializedName("overall_score")
    String overall_score;
    @SerializedName("taken")
    String taken;
    @SerializedName("exam_id")
    String exam_id;

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
    @SerializedName("error")
    String error;

    public int getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getExaminer_id() {
        return examiner_id;
    }

    public String getPart_1() {
        return part_1;
    }

    public String getPart_1_comment() {
        return part_1_comment;
    }

    public String getPart_1_score() {
        return part_1_score;
    }

    public String getPart_2() {
        return part_2;
    }

    public String getPart_2_comment() {
        return part_2_comment;
    }

    public String getPart_2_score() {
        return part_2_score;
    }

    public String getPart_3() {
        return part_3;
    }

    public String getPart_3_comment() {
        return part_3_comment;
    }

    public String getPart_3_score() {
        return part_3_score;
    }

    public String getPart_4() {
        return part_4;
    }

    public String getPart_4_comment() {
        return part_4_comment;
    }

    public String getPart_4_score() {
        return part_4_score;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getExam_id() {
        return exam_id;
    }

    public String getOverall_score() {
        return overall_score;
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

    public String getTaken() {
        return taken;
    }

    public String getError() {
        return error;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setExaminer_id(String examiner_id) {
        this.examiner_id = examiner_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOverall_score(String overall_score) {
        this.overall_score = overall_score;
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

    public void setPart_1(String part_1) {
        this.part_1 = part_1;
    }

    public void setPart_1_comment(String part_1_comment) {
        this.part_1_comment = part_1_comment;
    }

    public void setPart_1_score(String part_1_score) {
        this.part_1_score = part_1_score;
    }

    public void setPart_2(String part_2) {
        this.part_2 = part_2;
    }

    public void setPart_2_comment(String part_2_comment) {
        this.part_2_comment = part_2_comment;
    }

    public void setPart_2_score(String part_2_score) {
        this.part_2_score = part_2_score;
    }

    public void setPart_3(String part_3) {
        this.part_3 = part_3;
    }

    public void setPart_3_comment(String part_3_comment) {
        this.part_3_comment = part_3_comment;
    }

    public void setPart_3_score(String part_3_score) {
        this.part_3_score = part_3_score;
    }

    public void setPart_4(String part_4) {
        this.part_4 = part_4;
    }

    public void setPart_4_comment(String part_4_comment) {
        this.part_4_comment = part_4_comment;
    }

    public void setPart_4_score(String part_4_score) {
        this.part_4_score = part_4_score;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
