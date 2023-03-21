package com.nhom9_cp18013.appqlkh.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom9_cp18013.appqlkh.database.dbHelper;
import com.nhom9_cp18013.appqlkh.model.Hang;

import java.util.ArrayList;
import java.util.List;

public class HangDao {
    SQLiteDatabase database;
    dbHelper DbHelper;

    public HangDao(Context context) {
        DbHelper = new dbHelper(context);
    }

    public void open() {
        database = DbHelper.getWritableDatabase();
    }

    public void close() {
        DbHelper.close();
    }

    public long insertHH(Hang hang) {
        ContentValues values = new ContentValues();

        values.put(Hang.TB_COL_ID, hang.getMaHang());
        values.put(Hang.TB_COL_NAME, hang.getTenHang());
        values.put(Hang.TB_COL_IMAGE, hang.getHinhAnh());

        return database.insert(Hang.TB_NAME, null, values);
    }

    public int deleteHH(String maHang) {
        return database.delete(Hang.TB_NAME, "maHang = ?", new String[]{maHang});
    }

    public int updateHH(Hang hang, String maHangOld) {
        ContentValues values = new ContentValues();

        values.put(Hang.TB_COL_ID, hang.getMaHang());
        values.put(Hang.TB_COL_NAME, hang.getTenHang());
        values.put(Hang.TB_COL_IMAGE, hang.getHinhAnh());

        return database.update(Hang.TB_NAME, values, "maHang = ?", new String[]{maHangOld});
    }

    public Hang getMaHang(String maHang) {
        String sql = "SELECT * FROM Hang WHERE maHang = ?";
        List<Hang> list = getData(sql, maHang);
        return list.get(0);
    }

    public int checkMaHang(String maHang) {
        String sql = "SELECT * FROM Hang WHERE maHang = ?";
        List<Hang> list = getData(sql, maHang);
        return list.size() == 0 ? -1 : 1;
    }

    public List<Hang> getAll() {
        String sql = "SELECT * FROM Hang";
        List<Hang> list = getData(sql);
        return list;
    }

    public List<Hang> getAllSXTen() {
        String sql = "SELECT * FROM Hang ORDER BY tenHang ASC";
        List<Hang> list = getData(sql);
        return list;
    }

    public List<Hang> getAllSXMa() {
        String sql = "SELECT * FROM Hang ORDER BY maHang ASC";
        List<Hang> list = getData(sql);
        return list;
    }

    @SuppressLint("Range")
    public List<Hang> getData(String sql, String... args) {
        List<Hang> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            Hang hang = new Hang();

            hang.setMaHang(cursor.getString(cursor.getColumnIndex(Hang.TB_COL_ID)));
            hang.setTenHang(cursor.getString(cursor.getColumnIndex(Hang.TB_COL_NAME)));
            hang.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(Hang.TB_COL_IMAGE)));

            list.add(hang);
        }
        return list;
    }
}
