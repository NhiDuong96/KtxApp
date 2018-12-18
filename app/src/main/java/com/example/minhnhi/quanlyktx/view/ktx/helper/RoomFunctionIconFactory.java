package com.example.minhnhi.quanlyktx.view.ktx.helper;

import com.example.minhnhi.quanlyktx.R;
import com.example.minhnhi.quanlyktx.beans.RoomFunction;

public class RoomFunctionIconFactory {

    public static int getIcon(RoomFunction roomFunction){
        switch (roomFunction){
            case LIVE_FUNCTION:
                return -1;
            case STUDY_FUNCTION:
                return R.mipmap.school_icon_96;
            case NON_USED:
                return R.mipmap.delete_icon_96;
        }
        return -1;
    }
}
