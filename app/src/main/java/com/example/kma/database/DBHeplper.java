package com.example.kma.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHeplper extends SQLiteOpenHelper {
    public DBHeplper(@Nullable Context context) {
        super(context, "KMA.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //tạo table lớp
        String sql = " CREATE TABLE LOP(maLop TEXT PRIMARY KEY, tenLop TEXT)";
        db.execSQL(sql);
        sql = " INSERT INTO LOP VALUES ('L03','QLXD&ATT')";
        db.execSQL(sql);
        sql = " INSERT INTO LOP VALUES ('L02','LTPMUD')";
        db.execSQL(sql);
        sql = " INSERT INTO LOP VALUES ('L05','THỂ CHẤT')";
        db.execSQL(sql);
        //tao table calendar event
        sql = "CREATE TABLE EventCalendar(Date TEXT, Event TEXT)";
        db.execSQL(sql);
        //tạo table sinh viên
        sql = " CREATE TABLE SINHVIEN(maSv TEXT PRIMARY KEY, tenSV TEXT ," + " email TEXT ,hinh TEXT, maLop TEXT REFERENCES LOP(maLop))";
        db.execSQL(sql);
        sql = " INSERT INTO SINHVIEN VALUES ('AT150647','Hoang Duc Thai','at150647@actvn.edu.vn','avatasinhvien','L03')";
        db.execSQL(sql);
        sql = " INSERT INTO SINHVIEN VALUES ('AT150248','Pham Co Thach','at150248@actvn.edu.vn','avatasinhvien','L02')";
        db.execSQL(sql);
        sql = " INSERT INTO SINHVIEN VALUES ('AT150208','Chau Dinh Doanh','at150208@actvn.edu.vn','avatamacdinh','L05')";
        db.execSQL(sql);
        //tạo table taikhoan
        sql = "CREATE TABLE taiKhoan(tenTaiKhoan text primary key, matKhau text)";
        db.execSQL(sql);
        //tai khoan mac dinh admin
        sql = "INSERT INTO taiKhoan VALUES('admin','admin')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
