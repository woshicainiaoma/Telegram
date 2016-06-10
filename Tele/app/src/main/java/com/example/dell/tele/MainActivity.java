package com.example.dell.tele;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.tele.activity.Search;
import com.example.dell.tele.activity.ShowLinkman;
import com.example.dell.tele.db.ContractManager;
import com.example.dell.tele.model.ContractBean;
import com.example.dell.tele.util.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_view;
    private List<ContractBean> contacts = new ArrayList<ContractBean>();
    private ContactAdapter adapter;
    private Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_view = (ListView) findViewById(R.id.poeple);
        addBtn = (Button) findViewById(R.id.add);
        adapter = new ContactAdapter(this, contacts);
        lv_view.setAdapter(adapter);
        setContactsData();

        lv_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowLinkman.class);

                ContractBean contact = (ContractBean) adapter.getItem(position);

                intent.putExtra("message", String.valueOf(contact.getName()));

                startActivity(intent);

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });


        lv_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog show = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认删除联系人吗！！！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ContractBean contact = (ContractBean) adapter.getItem(position);
                                ContractManager.deleteContact(MainActivity.this, contact);
                                setContactsData();
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();


                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();


                return true;
            }
        });
    }

    private void setContactsData() {
        List<ContractBean> contactData = ContractManager.getContacts(this);
        contacts.clear();
        contacts.addAll(contactData);
        adapter.notifyDataSetChanged();
    }

}
