package com.example.shuaxin;

import android.widget.HeaderViewListAdapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class StudentApaptert extends BaseQuickAdapter<Student, BaseViewHolder>{
    public StudentApaptert(@Nullable List<Student> data) {
        super(R.layout.item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        helper.setText(R.id.txname,item.getName());
    }
}
