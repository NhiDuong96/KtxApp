package com.example.minhnhi.quanlyktx.view.ktx.pager;

import com.example.minhnhi.quanlyktx.beans.Room;

public class RoomPagerFactory {

    public RoomPager createPage(int pagePosition, Room room){
        switch (pagePosition){
            case 0:
                RoomInfoPager pager = new RoomInfoPager();
                pager.setRoom(room);
                return pager;
            case 1:
                RoomSVListPager svPager = new RoomSVListPager();
                svPager.setRoom(room);
                return svPager;
            case 2:
                RoomBillPager billPager = new RoomBillPager();
                billPager.setRoom(room);
                return billPager;
        }
        return new RoomInfoPager();
    }
}
