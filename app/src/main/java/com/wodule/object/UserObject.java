package com.wodule.object;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 15/06/2017.
 */
public class UserObject {
    @SerializedName("id")
    int id;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("middle_name")
    String middle_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("date_of_birth")
    String date_of_birth;
    @SerializedName("country_of_birth")
    String country_of_birth;
    @SerializedName("city")
    String city;
    @SerializedName("country")
    String country;
    @SerializedName("telephone")
    String telephone;
    @SerializedName("nationality")
    String nationality;
    @SerializedName("status")
    String  status;
    @SerializedName("gender")
    String gender;
    @SerializedName("code")
    String  code;
    @SerializedName("user_name")
    String user_name;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String  password;
    @SerializedName("native_name")
    String native_name;
    @SerializedName("suffix")
    String Suffx;
    @SerializedName("In_first")
    String In_first;
    @SerializedName("address")
    String  address;
    @SerializedName("ethnicity")
    String ethnicity;
    @SerializedName("religion")
    String religion;
    @SerializedName("picture")
    String picture;
    @SerializedName("token")
    String token;
    @SerializedName("organization")
    String organization;
    @SerializedName("student_class")
    String student_class;
    @SerializedName("adviser")
    String adviser;
    @SerializedName("provider")
    String provider;
    @SerializedName("type")
    String Type;

    public String getType() {
        return Type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAdviser(String adviser) {
        this.adviser = adviser;
    }

    public String getAdviser() {
        return adviser;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getStudent_class() {
        return student_class;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry_of_birth(String country_of_birth) {
        this.country_of_birth = country_of_birth;
    }

    public String getCountry_of_birth() {
        return country_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setIn_first(String ln_first) {
        this.In_first = ln_first;
    }

    public String getIn_first() {
        return In_first;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNative_name(String native_name) {
        this.native_name = native_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNative_name() {
        return native_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getReligion() {
        return religion;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSuffx(String suffx) {
        Suffx = suffx;
    }

    public String getSuffx() {
        return Suffx;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }
}
