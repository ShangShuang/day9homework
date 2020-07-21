package com.example.day9homework.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    public P presenter;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLyout(), null);
        bind = ButterKnife.bind(this, view);
        initPresenter();
        if(presenter != null)
            presenter.bindView(this);
        initView();
        initData();
        initListener();
        return view;

    }

    protected abstract int getLyout();

    protected abstract void initData();

    protected abstract void initPresenter();

    protected abstract void initView();

    protected abstract void initListener();

    //解绑

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();//解绑
        presenter.destroy();
        presenter = null;
    }
}
