package com.example.minhnhi.quanlyktx.home;

public class HomePageFactory {
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
                return new NotificationPage();
            case DOCUMENT:
                return new NotificationPage();
            case FORM:
                return new NotificationPage();

        }
        return new NotificationPage();
    }

    public int nextPage(){
        if(currentPageId == NOTIFICATION){
            currentPageId = RECRUITMENT;
        }else if(currentPageId == RECRUITMENT){
            currentPageId = DOCUMENT;
        }else if(currentPageId == DOCUMENT){
            currentPageId = FORM;
        }else if(currentPageId == FORM){
            currentPageId = NOTIFICATION;
        }
        return currentPageId;
    }


    public int previousPage(){
        if(currentPageId == NOTIFICATION){
            currentPageId = FORM;
        }else if(currentPageId == RECRUITMENT){
            currentPageId = NOTIFICATION;
        }else if(currentPageId == DOCUMENT){
            currentPageId = RECRUITMENT;
        }else if(currentPageId == FORM){
            currentPageId = DOCUMENT;
        }
        return currentPageId;
    }

}
