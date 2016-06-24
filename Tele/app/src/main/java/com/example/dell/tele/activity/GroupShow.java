package com.example.dell.tele.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.dell.tele.R;
import com.example.dell.tele.db.GroupManager;
import com.example.dell.tele.model.ContractBean;
import com.example.dell.tele.util.ShowLinkmanAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/22.
 */
public class GroupShow extends Activity {

    private String dataId;
    private ListView lv_showLink;
    private List<ContractBean> contacts = new ArrayList<ContractBean>();
    private ShowLinkmanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupname);
        lv_showLink = (ListView) findViewById(R.id.groupname_show);

        adapter = new ShowLinkmanAdapter(this,contacts);
        lv_showLink.setAdapter(adapter);

        Intent intent = getIntent();
        dataId = intent.getStringExtra("id");

        int linkId = Integer.parseInt(dataId);

        setContactsData(linkId);

    }

    private void setContactsData(int i) {

        List<ContractBean> contact = GroupManager.getAllContactsByGroupId(i,this);
        contacts.clear();
        contacts.addAll(contact);
        adapter.notifyDataSetChanged();

    }
}
