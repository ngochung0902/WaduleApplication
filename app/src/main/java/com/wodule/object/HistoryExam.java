package com.wodule.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 29/06/2017.
 */
public class HistoryExam {
    @SerializedName("id")
    int id;
    @SerializedName("reference_id")
    String reference_id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("examiner_id")
    String examiner_id;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("scored_at")
    String scored_at;
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

    @SerializedName("error")
    String error;

    public String getError() {
        return error;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getExaminer_id() {
        return examiner_id;
    }

    public String getScored_at() {
        return scored_at;
    }

    public String getPart_1() {
        return part_1;
    }

    public String getPart_1_score() {
        return part_1_score;
    }

    public String getPart_1_comment() {
        return part_1_comment;
    }

    public String getPart_2() {
        return part_2;
    }

    public String getPart_2_score() {
        return part_2_score;
    }

    public String getPart_2_comment() {
        return part_2_comment;
    }

    public String getPart_3() {
        return part_3;
    }

    public String getPart_3_score() {
        return part_3_score;
    }

    public String getPart_3_comment() {
        return part_3_comment;
    }

    public String getPart_4() {
        return part_4;
    }

    public String getPart_4_score() {
        return part_4_score;
    }

    public String getPart_4_comment() {
        return part_4_comment;
    }

    public String getReference_id() {
        return reference_id;
    }
}
