package com.example.minhnhi.quanlyktx.view.activity.pager;

import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.utils.OnSlideAnimationEndListener;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomBillPager;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomInfoPager;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomPager;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomPagerFactory;
import com.example.minhnhi.quanlyktx.view.ktx.pager.RoomSVListPager;

public class RoomRegisterPagerFactory extends RoomPagerFactory {

    public RoomPager createPage(int pagePosition, Room room, OnSlideAnimationEndListener listener){
        switch (pagePosition){
            case 0:
                RoomRegisterPager pager = new RoomRegisterPager();
                pager.setRoom(room);
                pager.setOnSlideFragmentAnimationEnd(listener);
                return pager;
        }
        return new RoomRegisterPager();
    }

    public int getNumOfPages() {
        return 1;
    }
}
