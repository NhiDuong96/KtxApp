package com.example.minhnhi.quanlyktx.cmd;

import com.example.minhnhi.quanlyktx.beans.Area;
import com.example.minhnhi.quanlyktx.beans.Bill;
import com.example.minhnhi.quanlyktx.beans.Floor;
import com.example.minhnhi.quanlyktx.beans.Notification;
import com.example.minhnhi.quanlyktx.beans.RegisterResult;
import com.example.minhnhi.quanlyktx.beans.RegisterRoom;
import com.example.minhnhi.quanlyktx.beans.Room;
import com.example.minhnhi.quanlyktx.beans.TimeRegister;
import com.example.minhnhi.quanlyktx.beans.UserProfile;

import java.util.List;

public class ApiResponseClass{
    public static class AreaResponse extends ApiResponse<List<Area>>{}
    public static class FloorResponse extends ApiResponse<List<Floor>>{}
    public static class RoomResponse extends ApiResponse<List<Room>>{}

    public static class BillResponse extends ApiResponse<List<Bill>>{}

    public static class StudentResponse extends ApiResponse<List<UserProfile>>{}

    public static class NotificationResponse extends ApiResponse<List<Notification>>{}

    public static class RegisterRoomResponse extends ApiResponse<RegisterRoom>{}

    public static class RegisterResultResponse extends ApiResponse<List<RegisterResult>>{}

    public static class TimeRegisterResponse extends ApiResponse<List<TimeRegister>>{}
}
