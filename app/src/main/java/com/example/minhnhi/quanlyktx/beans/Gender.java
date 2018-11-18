package com.example.minhnhi.quanlyktx.beans;

public enum Gender {
    MALE(1,"Nam"),
    FEMALE(0,"Nữ");
    int code;
    String text;
    Gender(int code, String text){
        this.code = code;
        this.text = text;
    }

    public static Gender getGenderByCode(int code){
        for(Gender gender: Gender.values()){
            if(gender.code == code){
                return gender;
            }
        }
        return MALE;
    }

    public static int getGenderIndexByCode(int code){
        int i = 0;
        for(Gender gender: Gender.values()){
            if(gender.code == code){
                return i;
            }
            i++;
        }
        return i;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static String[] keys(){
        String[] keys = new String[Gender.values().length];
        int i = 0;
        for(Gender gender: Gender.values()){
            keys[i] = gender.text;
            i++;
        }
        return keys;
    }
}
