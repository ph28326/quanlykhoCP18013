package com.nhom9_cp18013.appqlkh.ui.ThongKe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhom9_cp18013.appqlkh.R;
import com.nhom9_cp18013.appqlkh.adapter.Top10Adapter;
import com.nhom9_cp18013.appqlkh.dao.DaoSanPham;
import com.nhom9_cp18013.appqlkh.model.Top10SanPham;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TopSanPhamFragment extends Fragment {

    DaoSanPham dao;
    List<Top10SanPham> list;
    Top10Adapter adapter;
    RecyclerView rvTK;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTK = view.findViewById(R.id.rvTop10);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvTK.setLayoutManager(layoutManager);

        dao = new DaoSanPham(getActivity());
        dao.open();

        list = new ArrayList<>();
        list = dao.getTop();
        adapter = new Top10Adapter(list);

        rvTK.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_top_san_pham, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}