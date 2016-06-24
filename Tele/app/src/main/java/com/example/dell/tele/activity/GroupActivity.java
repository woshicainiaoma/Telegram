package com.example.dell.tele.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dell.tele.R;
import com.example.dell.tele.db.GroupManager;
import com.example.dell.tele.model.ContractBean;
import com.example.dell.tele.util.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/22.
 */
public class GroupActivity extends Activity {

    private ListView showGroup;
    private GroupAdapter adapter;
    private List<ContractBean> contacts = new ArrayList<ContractBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupname);

        showGroup = (ListView) findViewById(R.id.groupname_show);
        adapter = new GroupAdapter(this,contacts);
        showGroup.setAdapter(adapter);
        setContactsData();

        showGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(GroupActivity.this,GroupShow.class);

                ContractBean contact = (ContractBean) adapter.getItem(position);

                intent.putExtra("id",String.valueOf(contact.getGroupId()));
                startActivity(intent);

            }
        });
    }

    private void setContactsData() {

        List<ContractBean> contactData = GroupManager.getAllGroupInfo(this);
        contacts.clear();
        contacts.addAll(contactData);
        adapter.notifyDataSetChanged();

    }
}
