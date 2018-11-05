package com.xuantie.futures.ui.ranking;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xuantie.futures.R;
import com.xuantie.futures.base.BaseFragment;
import com.xuantie.futures.ui.ranking.myRanking.MyRankingFragment;
import com.xuantie.futures.ui.ranking.paihangbang.PaiHangFragment;
import com.xuantie.futures.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 跟单
 * Created by Administrator on 2018/9/26.
 */

public class RankingFragment extends BaseFragment {
    @BindView(R.id.tl)
    SegmentTabLayout mTl;
    Unbinder unbinder;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.iv_jiantou)
    ImageView mIvJiantou;
    @BindView(R.id.tv_gendan)
    TextView mTvGendan;
    @BindView(R.id.iv_select)
    ImageView mIvSelect;
    @BindView(R.id.iv_help)
    ImageView mIvHelp;
    @BindView(R.id.rl_root)
    RelativeLayout mRlRoot;
    private String[] mTitles = {"我的跟单", "排行榜"};
    private ArrayList<String> conditionList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private AlertDialog mHelpDialog;
    private RecyclerView mRvCondition;
    private PopupWindow mPopupWindow;
    private ConditionAdapter mConditionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initPopupWindow();
        return view;
    }

    private void initView() {
        conditionList.add("昨日排行");
        conditionList.add("前7日排行");
        conditionList.add("前30日排行");
        mFragments.add(MyRankingFragment.newInstance());
        mFragments.add(PaiHangFragment.newInstance());
        mTl.setTabData(mTitles, getActivity(), R.id.fl_ranking, mFragments);
        mTl.setCurrentTab(1);
        showElement(1);
        mTl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showElement(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_paihang, null);
        mRvCondition = view.findViewById(R.id.rv_condition);
        mPopupWindow = new PopupWindow(view, Utils.dipToPx(mActivity, 130), ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
        mRvCondition.setLayoutManager(new LinearLayoutManager(mActivity));
        mConditionAdapter = new ConditionAdapter(R.layout.item_condition, conditionList);
        mRvCondition.setAdapter(mConditionAdapter);
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(lp);
    }

    private void showElement(int position) {
        if (position == 0) {
            mIvHelp.setVisibility(View.VISIBLE);
            mTvGendan.setVisibility(View.VISIBLE);
            mTvType.setVisibility(View.GONE);
            mIvJiantou.setVisibility(View.GONE);
            mIvSelect.setVisibility(View.GONE);
        } else {
            mTvType.setVisibility(View.VISIBLE);
            mIvJiantou.setVisibility(View.VISIBLE);
            mIvSelect.setVisibility(View.VISIBLE);
            mTvGendan.setVisibility(View.GONE);
            mIvHelp.setVisibility(View.GONE);
        }
    }

    private void showHelpDialog() {
        if (mHelpDialog == null) {
            mHelpDialog = new AlertDialog.Builder(mActivity)
                    .setTitle("跟单提示")
                    .setMessage(R.string.gendan_help)
                    .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
            mHelpDialog.show();
        } else {
            mHelpDialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_help, R.id.tv_type, R.id.iv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_help:
                showHelpDialog();
                break;
            case R.id.tv_type:
                if ("盈利".equals(mTvType.getText().toString())) {
                    mTvType.setText("亏损");
                } else {
                    mTvType.setText("盈利");
                }
                break;
            case R.id.iv_select:
                mPopupWindow.showAsDropDown(mRlRoot, mRlRoot.getMeasuredWidth(), 0);
                setBackgroundAlpha(0.5f);
                break;
        }
    }
}
