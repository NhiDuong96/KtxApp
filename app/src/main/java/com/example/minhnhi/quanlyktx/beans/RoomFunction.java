package com.example.minhnhi.quanlyktx.beans;

public enum RoomFunction {
    STUDY_FUNCTION(2),

    LIVE_FUNCTION(1),

    NON_USED(3);

    public int code;

    RoomFunction(int code){
        this.code = code;
    }

    public static RoomFunction getFunctionById(int id){
        for(RoomFunction function: RoomFunction.values()){
            if(function.code == id){
                return function;
            }
        }
        return NON_USED;
    }
}
