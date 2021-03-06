package com.example.administrator.gc.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.presenter.fragment.MinePresenter;
import com.example.administrator.gc.ui.activity.AboutActivity;
import com.example.administrator.gc.ui.activity.LoginActivity;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MineFragment extends BaseFragment {
    private MinePresenter presenter;
    private LinearLayout aboutLinearLayout;
    private Button loginButton;
    private boolean isLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mine, container, false);
        isLogin = cache.readBooleanValue("isLogin", false);
        return v;
    }

    @Override
    protected void initView(View v) {
        aboutLinearLayout = (LinearLayout) v.findViewById(R.id.aboutLinearLayout);
        loginButton = (Button) v.findViewById(R.id.loginButton);
        if (isLogin) {
            loginButton.setText("退出登录");
        } else {
            loginButton.setText("登录");
        }
    }

    @Override
    protected void bind() {
        presenter = new MinePresenter();
        presenter.bind(this);
        presenter.getData();
    }

    public void show(String s) {

    }

    @Override
    protected void setListener() {
        aboutLinearLayout.setOnClickListener(listener);
        loginButton.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == aboutLinearLayout.getId()) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
            if (id == loginButton.getId()) {
                if (isLogin) {
                    logOut();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        }
    };

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity())
                .setTitle("是否退出")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cache.saveBooleanValue("isLogin", false);
                        reset();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        reset();
    }

    private void reset() {
        isLogin = cache.readBooleanValue("isLogin", false);
        if (isLogin) {
            loginButton.setText("退出登录");
        } else {
            loginButton.setText("登录");
        }
    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }
}
