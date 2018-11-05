package com.xuantie.futures;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.xuantie.futures.base.BaseActivity;
import com.xuantie.futures.utils.GsonUtil;
import com.xuantie.futures.utils.Utils;
import com.xuantie.futures.widget.dialog.SelectBanksDialog;
import com.xuantie.futures.widget.picker.JsonBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 开户申请
 * Created by Administrator on 2018/9/21.
 */

public class ApplyAccountActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_select_province_city)
    TextView mTvSelectProvinceCity;
    @BindView(R.id.tv_select_bank)
    TextView mTvSelectBank;
    @BindView(R.id.ll_apply_account)
    LinearLayout mLlApplyAccount;
    private Unbinder mUnbinder;
    private InputMethodManager mManagermanager;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private SelectBanksDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_account);
        mUnbinder = ButterKnife.bind(this);
        mTvTitle.setText("开户申请");
        mManagermanager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        mLlApplyAccount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //强制隐藏键盘
                if (mManagermanager.isActive()) {
                    mManagermanager.hideSoftInputFromWindow(mLlApplyAccount.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
    }

    @OnClick({R.id.rl_title_back, R.id.tv_select_province_city, R.id.tv_select_bank})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_title_back:
                finish();
                break;
            case R.id.tv_select_province_city:
                if (mManagermanager.isActive()) {
                    mManagermanager.hideSoftInputFromWindow(mLlApplyAccount.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                showPickerView();
                break;
            case R.id.tv_select_bank:
                if(dialog == null){
                    dialog = new SelectBanksDialog(this, R.style.CustomDialog, new SelectBanksDialog.SelectBanksDialogListener() {
                        @Override
                        public void onClickView(View view) {

                        }
                    });
                }
                dialog.show();
                break;
        }
    }

    //获取省市区数据
    private void getProvinceCity() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = Utils.getJson(this, "province.json");//获取assets目录下的json文件数据

        List<JsonBean> jsonBean = GsonUtil.GsonToList(JsonData,JsonBean.class);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

    }

    //弹出选择器
    private void showPickerView() {
        getProvinceCity();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                Toast.makeText(ApplyAccountActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("请选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
