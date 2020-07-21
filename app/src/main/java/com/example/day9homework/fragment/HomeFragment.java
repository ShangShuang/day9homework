package com.example.day9homework.fragment;


import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day9homework.DownLoadActivity;
import com.example.day9homework.R;
import com.example.day9homework.adapter.HomeAdapter;
import com.example.day9homework.base.BaseFragment;
import com.example.day9homework.bean.HomeBean;
import com.example.day9homework.presenter.HomePresenter;
import com.example.day9homework.view.HomeView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {


    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private ArrayList<HomeBean.DataBean.DatasBean> list;
    private HomeAdapter adapter;

   /* public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }*/


    @Override
    protected void initPresenter() {
        presenter = new HomePresenter();
    }

    @Override
    protected void initView() {
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        list = new ArrayList<>();

        adapter = new HomeAdapter(getActivity(), list);
        rvHome.setAdapter(adapter);
    }

    @Override
    protected int getLyout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        presenter.getData();

    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(), DownLoadActivity.class));
            }
        });
    }

    @Override
    public void setData(HomeBean homeBean) {
        list.addAll(homeBean.getData().getDatas());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }
}
