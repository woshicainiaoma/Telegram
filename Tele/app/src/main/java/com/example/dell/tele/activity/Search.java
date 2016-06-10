package com.example.dell.tele.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dell.tele.R;
import com.example.dell.tele.db.ContractManager;
import com.example.dell.tele.model.ContractBean;
import com.example.dell.tele.ui.SearchView;
import com.example.dell.tele.util.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/10.
 */
public class Search extends Activity implements SearchView.SearchViewListener {


    /**
     * 搜索结果列表view
     */
    private ListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
   // private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
   // private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private ContactAdapter resultAdapter;

    private List<ContractBean> dbData;

    /**
     * 热搜版数据
     */
   // private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    //private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */

    private List<ContractBean> resultData;


    /**
     * 默认提示框显示项的个数
     */
   // private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
   // private static int hintSize = DEFAULT_HINT_SIZE;


//    public static void setHintSize(int hintSize) {
//        MainActivity.hintSize = hintSize;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        initData();
        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        lvResults = (ListView) findViewById(R.id.show_link);
        searchView = (SearchView) findViewById(R.id.search_link);
        //设置监听
        searchView.setSearchViewListenter(this);
        //设置adapter
        //searchView.setTipsHintAdapter(hintAdapter);
       // searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
      //  getDbData();
        //初始化热搜版数据
       // getHintData();
        //初始化自动补全数据
       // getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
//    private void getDbData() {
//        int size = 100;
//        dbData = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            dbData.add(new Bean(R.drawable.icon, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
//        }
//    }

    /**
     * 获取热搜版data 和adapter
     */
//    private void getHintData() {
//        hintData = new ArrayList<>(hintSize);
//        for (int i = 1; i <= hintSize; i++) {
//            hintData.add("热搜版" + i + "：Android自定义View");
//        }
//        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
//    }

    /**
     * 获取自动补全data 和adapter
     */
//    private void getAutoCompleteData(String text) {
//        if (autoCompleteData == null) {
//            //初始化
//            autoCompleteData = new ArrayList<>(hintSize);
//        } else {
//            // 根据text 获取auto data
//            autoCompleteData.clear();
//            for (int i = 0, count = 0; i < dbData.size()
//                    && count < hintSize; i++) {
//                if (dbData.get(i).getTitle().contains(text.trim())) {
//                    autoCompleteData.add(dbData.get(i).getTitle());
//                    count++;
//                }
//            }
//        }
//        if (autoCompleteAdapter == null) {
//            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
//        } else {
//            autoCompleteAdapter.notifyDataSetChanged();
//        }
//    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<ContractBean>();
        } else {
            resultData.clear();
          //  List<ContractBean> contactData = ContractManager.getShow(this,text);
            List<ContractBean> contractData = ContractManager.getShow(this,text);
            resultData.addAll(contractData);
        }

        if (resultAdapter == null) {
            resultAdapter = new  ContactAdapter(this, resultData);;
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
//    @Override
//    public void onRefreshAutoComplete(String text) {
//        //更新数据
//        getAutoCompleteData(text);
//    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
//        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();


    }



}
