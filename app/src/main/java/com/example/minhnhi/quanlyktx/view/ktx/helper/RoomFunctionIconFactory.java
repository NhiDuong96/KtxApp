package com.example.minhnhi.quanlyktx.view.ktx.helper;

import com.example.minhnhi.quanlyktx.R;

public class RoomFunctionIconFactory {

    public static int getIcon(int roomFunctionId){
        switch (roomFunctionId){
            case 0:
                return -1;
            case 1:
                return R.mipmap.school_icon_96;
            case 2:
                return R.mipmap.delete_icon_96;
        }
        return -1;
    }
}
