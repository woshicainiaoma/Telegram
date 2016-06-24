package com.example.dell.tele.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.tele.R;
import com.example.dell.tele.db.ContractManager;
import com.example.dell.tele.model.ContractBean;
import com.example.dell.tele.util.ContactAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 2016/6/8.
 */
public class ShowLinkman extends Activity {

    private TextView name_link;
    private ContactAdapter adapter;
    private List<ContractBean> contacts = new ArrayList<ContractBean>();
    private ListView show;
    private Button editLink;
    public String dataOne;
    public String newData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.back_layout);
        setContentView(R.layout.show_layout);
        // name_link = (TextView) findViewById(R.id.link_name);
        show = (ListView) findViewById(R.id.show_person);
        editLink = (Button) findViewById(R.id.edit_link);
        adapter = new ContactAdapter(this, contacts);
        show.setAdapter(adapter);
        Intent intent = getIntent();
         dataOne = intent.getStringExtra("message");
        //name_link.setText(data);

        editLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ShowLinkman.this, EditLinkman.class);
                intent1.putExtra("name", dataOne);
                //startActivity(intent1);
//                Intent intent = new Intent();
//                intent.putExtra("message", editText1.getText().toString().trim() + " + " + editText2.getText().toString().trim() + " = ?");
//                intent.setClass(MainActivity.this, SecondActivity.class);
                /*
                 * 如果希望启动另一个Activity，并且希望有返回值，则需要使用startActivityForResult这个方法，
                 * 第一个参数是Intent对象，第二个参数是一个requestCode值，如果有多个按钮都要启动Activity，则requestCode标志着每个按钮所启动的Activity
                 */
                startActivityForResult(intent1, 1000);

            }
        });


        setContactsData(dataOne);
        //adapter.notifyDataSetChanged();
    }

    private void setContactsData(String name) {
        List<ContractBean> contactData = ContractManager.getShow(this, name);
        contacts.clear();
        contacts.addAll(contactData);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //setContactsData(newData);


    }

    @Override
    protected void onRestart() {
        super.onRestart();

//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");



            setContactsData(newData);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == 1001)
        {
            newData = data.getStringExtra("result");

        }
    }
}