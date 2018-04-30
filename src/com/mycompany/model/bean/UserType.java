package com.mycompany.model.bean;

public class UserType {
    private int type_id;
    private String type_name;
    private Short quota;

    public UserType(int type_id, String type_name, Short quota) {
        this.type_id = type_id;
        this.type_name = type_name;
        this.quota = quota;
    }

    public UserType() {
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Short getQuota() {
        return quota;
    }

    public void setQuota(Short quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "type_id=" + type_id +
                ", type_name='" + type_name + '\'' +
                ", quota=" + quota +
                '}';
    }
}
