package com.example.minhnhi.quanlyktx.view.ktx.pager;

import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationEndListener;

public class RoomPagerFactory {

    public RoomPager createPage(int pagePosition, Room room, OnSlideAnimationEndListener listener){
        RoomPager pager = null;
        switch (pagePosition){
            case 0:
                pager = new RoomInfoPager();
                break;
            case 1:
                pager = new RoomSVListPager();
                break;
            case 2:
                pager = new RoomBillPager();
                break;
            default:
                pager = new RoomInfoPager();
                break;
        }
        pager.setRoom(room);
        pager.setOnSlideFragmentAnimationEnd(listener);
        return pager;
    }

    public int getNumOfPages() {
        return 3;
    }
}
