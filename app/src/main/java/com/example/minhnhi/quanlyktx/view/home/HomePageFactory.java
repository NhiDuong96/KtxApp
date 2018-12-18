package com.example.minhnhi.quanlyktx.view.home;

import com.example.minhnhi.quanlyktx.view.home.page.NotificationPage;
import com.example.minhnhi.quanlyktx.view.home.page.RecruitmentPage;

class HomePageFactory {
    public static final int MAX_PAGE = 2;

    public static final int NOTIFICATION = 0;
    public static final int RECRUITMENT = 1;
    public static final int DOCUMENT = 2;
    public static final int FORM = 3;

    private int currentPageId = NOTIFICATION;

    HomePage createPage(int pageId){
        switch (pageId){
            case NOTIFICATION:
                return new NotificationPage();
            case RECRUITMENT:
                return new RecruitmentPage();
            case DOCUMENT:
                return new NotificationPage();
            case FORM:
                return new NotificationPage();

        }
        return new NotificationPage();
    }

    int nextPage(){
        currentPageId = (currentPageId >= (MAX_PAGE - 1))? 0 : currentPageId+1;
        return currentPageId;
    }


    int previousPage(){
        currentPageId = (currentPageId <= 0)? MAX_PAGE - 1 : currentPageId-1;
        return currentPageId;
    }

}
