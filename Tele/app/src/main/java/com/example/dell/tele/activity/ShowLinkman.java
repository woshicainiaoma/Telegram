package com.example.dell.tele.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.back_layout);
        setContentView(R.layout.show_layout);
        // name_link = (TextView) findViewById(R.id.link_name);
        show = (ListView) findViewById(R.id.show_person);
        adapter = new ContactAdapter(this, contacts);
        show.setAdapter(adapter);
        Intent intent = getIntent();
        String data = intent.getStringExtra("message");
        //name_link.setText(data);
        setContactsData(data);
    }

    private void setContactsData(String name) {
        List<ContractBean> contactData = ContractManager.getShow(this, name);
        contacts.clear();
        contacts.addAll(contactData);
        adapter.notifyDataSetChanged();
    }

}