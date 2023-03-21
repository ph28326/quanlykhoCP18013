package com.nhom9_cp18013.appqlkh.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhom9_cp18013.appqlkh.database.dbHelper;
import com.nhom9_cp18013.appqlkh.model.SanPham;
import com.nhom9_cp18013.appqlkh.model.Top10SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDao {
    SQLiteDatabase database;
    dbHelper DbHelper;

    public SanPhamDao(Context context) {
        DbHelper = new dbHelper(context);
    }

    public void open() {
        database = DbHelper.getWritableDatabase();
    }

    public void close() {
        DbHelper.close();
    }

    public long insertSP(SanPham sanPham) {
        ContentValues values = new ContentValues();

        values.put(SanPham.TB_COL_ID_SP, sanPham.getMaSP());
        values.put(SanPham.TB_COL_ID_HANG, sanPham.getMaHang());
        values.put(SanPham.TB_COL_NAME, sanPham.getTenSP());
        values.put(SanPham.TB_COL_IMAGE, sanPham.getHinhAnh());
        values.put(SanPham.TB_COL_CLASSIFY, sanPham.getPhanLoai());
        values.put(SanPham.TB_COL_STATE, sanPham.getTinhTrang());
        values.put(SanPham.TB_COL_MONEY, sanPham.getGiaTien());
        values.put(SanPham.TB_COL_STATUS, sanPham.getTrangThai());
        values.put(SanPham.TB_COL_NOTE, sanPham.getMoTa());

        return database.insert(SanPham.TB_NAME, null, values);
    }

    public int deleteSP(String maSP) {
        return database.delete(SanPham.TB_NAME, "maSP = ?", new String[]{maSP});
    }

    public int updateSP(SanPham sanPham, String maSPOld) {
        ContentValues values = new ContentValues();

        values.put(SanPham.TB_COL_ID_SP, sanPham.getMaSP());
        values.put(SanPham.TB_COL_ID_HANG, sanPham.getMaHang());
        values.put(SanPham.TB_COL_NAME, sanPham.getTenSP());
        values.put(SanPham.TB_COL_IMAGE, sanPham.getHinhAnh());
        values.put(SanPham.TB_COL_CLASSIFY, sanPham.getPhanLoai());
        values.put(SanPham.TB_COL_STATE, sanPham.getTinhTrang());
        values.put(SanPham.TB_COL_MONEY, sanPham.getGiaTien());
        values.put(SanPham.TB_COL_STATUS, sanPham.getTrangThai());
        values.put(SanPham.TB_COL_NOTE, sanPham.getMoTa());

        return database.update(SanPham.TB_NAME, values, "maSP = ?", new String[]{maSPOld});
    }

    public List<SanPham> getAll() {
        String sql = "SELECT * FROM SanPham";
        List<SanPham> list = getData(sql);
        return list;
    }

    public List<SanPham> getAllSPBan() {
        String sql = "SELECT * FROM SanPham WHERE trangThai = 1";
        List<SanPham> list = getData(sql);
        return list;
    }

    public List<SanPham> getAllTen() {
        String sql = "SELECT * FROM SanPham ORDER BY tenSP ASC";
        List<SanPham> list = getData(sql);
        return list;
    }

    public List<SanPham> getAllMa() {
        String sql = "SELECT * FROM SanPham ORDER BY maSP ASC";
        List<SanPham> list = getData(sql);
        return list;
    }

    public SanPham getMaSP(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE maSP = ?";
        List<SanPham> list = getData(sql, maSP);
        return list.get(0);
    }

    public int checkMaSP(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE maSP = ?";
        List<SanPham> list = getData(sql, maSP);
        return list.size() == 0 ? -1 : 1;
    }

    @SuppressLint("Range")
    public List<SanPham> getData(String sql, String... args) {
        List<SanPham> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            SanPham sanPham = new SanPham();

            sanPham.setMaSP(cursor.getString(cursor.getColumnIndex(SanPham.TB_COL_ID_SP)));
            sanPham.setMaHang(cursor.getString(cursor.getColumnIndex(SanPham.TB_COL_ID_HANG)));
            sanPham.setTenSP(cursor.getString(cursor.getColumnIndex(SanPham.TB_COL_NAME)));
            sanPham.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(SanPham.TB_COL_IMAGE)));
            sanPham.setPhanLoai(cursor.getInt(cursor.getColumnIndex(SanPham.TB_COL_CLASSIFY)));
            sanPham.setTinhTrang(cursor.getInt(cursor.getColumnIndex(SanPham.TB_COL_STATE)));
            sanPham.setGiaTien(cursor.getDouble(cursor.getColumnIndex(SanPham.TB_COL_MONEY)));
            sanPham.setTrangThai(cursor.getInt(cursor.getColumnIndex(SanPham.TB_COL_STATUS)));
            sanPham.setMoTa(cursor.getString(cursor.getColumnIndex(SanPham.TB_COL_NOTE)));
            list.add(sanPham);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<Top10SanPham> getTop() {
        List<Top10SanPham> list = new ArrayList<>();
        String selectTop = "SELECT maSP, sum(soLuong) as tongSL FROM ChiTietHoaDon INNER JOIN HoaDon ON ChiTietHoaDon.maHD = HoaDon.maHD WHERE HoaDon.phanLoai=1 GROUP BY maSP HAVING count(maSP) ORDER BY tongSL DESC LIMIT 10";
        Cursor cursor = database.rawQuery(selectTop, null);
        while (cursor.moveToNext()) {
            Top10SanPham top = new Top10SanPham();
            SanPham sanPham = getMaSP(cursor.getString(cursor.getColumnIndex("maSP")));
            top.setTenSP(sanPham.getTenSP());
            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tongSL"))));
            list.add(top);
        }
        return list;
    }
}
