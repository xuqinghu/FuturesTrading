package com.xuantie.futures.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuantie.futures.FastLoginActivity;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.ui.mine.announcement.AnnouncementActivity;
import com.xuantie.futures.ui.mine.money.MoneyActivity;
import com.xuantie.futures.ui.mine.personalSetting.PersonalSettingActivity;
import com.xuantie.futures.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/9/26.
 */

public class MineFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.iv_user_icon)
    CircleImageView mIvUserIcon;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_personage_setting, R.id.rl_golden, R.id.rl_withdraw, R.id.rl_bank_card, R.id.rl_faq,
            R.id.rl_trade_setting,R.id.ll_go_login,R.id.tv_announcement,R.id.tv_online_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_personage_setting:
                startActivity(new Intent(mActivity, PersonalSettingActivity.class));
                break;
            case R.id.rl_golden:
                Intent intent = new Intent();
                intent.putExtra("type",0);
                intent.setClass(mActivity,MoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_withdraw:
                Intent intent1 = new Intent();
                intent1.putExtra("type",1);
                intent1.setClass(mActivity,MoneyActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_bank_card:
                break;
            case R.id.rl_faq:
                break;
            case R.id.rl_trade_setting:
                break;
            case R.id.ll_go_login:
                startIntentLogin(new Intent(mActivity, FastLoginActivity.class));
                break;
            case R.id.tv_announcement:
                startActivity(new Intent(mActivity, AnnouncementActivity.class));
                break;
            case R.id.tv_online_service:
                break;
        }
    }
}
