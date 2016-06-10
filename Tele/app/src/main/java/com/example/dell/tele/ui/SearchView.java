package com.example.dell.tele.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.tele.R;

/**
 * Created by dell on 2016/6/10.
 */
public class SearchView extends LinearLayout implements View.OnClickListener {

    private EditText searchEdit;

    private Context mContext;

   // private ListView show_result;

    private SearchViewListener mListener;

    public void setSearchViewListenter(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context,AttributeSet atts) {
        super(context,atts);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout,this);
        initViews();
    }

    private void initViews(){

        searchEdit = (EditText) findViewById(R.id.search_edit);
      //  show_result = (ListView) findViewById(R.id.show_result);

//        show_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String text = show_result.getAdapter().getItem(position).toString();
//                searchEdit.setText(text);
//                searchEdit.setSelection(text.length());
//                show_result.setVisibility(View.GONE);
//                notifyStartSearching(text);
//            }
//        });

        searchEdit.addTextChangedListener(new EditChangedListener());
        searchEdit.setOnClickListener(this);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                  //  show_result.setVisibility(View.GONE);
                    notifyStartSearching(searchEdit.getText().toString());
                }

                return true;
            }
        });
    }

    private void   notifyStartSearching(String text) {
        if (mListener != null) {
            mListener.onSearch(searchEdit.getText().toString());
        }

       // InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
       // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {

             notifyStartSearching(searchEdit.getText().toString());

            } else {

              //  show_result.setVisibility(GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
       // void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);

//        /**
//         * 提示列表项点击时回调方法 (提示/自动补全)
//         */
//        void onTipsItemClick(String text);
    }
}
