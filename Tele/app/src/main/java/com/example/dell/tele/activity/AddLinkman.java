package com.example.dell.tele.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.tele.MainActivity;
import com.example.dell.tele.R;
import com.example.dell.tele.db.ContractManager;
import com.example.dell.tele.model.ContractBean;
import com.example.dell.tele.util.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/7.
 */
public class AddLinkman extends Activity {

    private EditText add_name;
    private EditText add_phone;
    private Button add_link;
    private List<ContractBean> contacts = new ArrayList<ContractBean>();
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.back_layout);
        setContentView(R.layout.linkman_layout);
        add_link = (Button) findViewById(R.id.add_link);
        add_name = (EditText) findViewById(R.id.edit_name);
        add_phone = (EditText) findViewById(R.id.edit_phone);

        add_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContractBean contact = new ContractBean();
                contact.setName(add_name.getText() + "");
                contact.setPhone(add_phone.getText() + "");
                ContractManager.addContact(AddLinkman.this, contact);
                setContactsData();
                Intent intent = new Intent(AddLinkman.this, MainActivity.class);
                startActivity(intent);
                AddLinkman.this.finish();
            }
        });
    }

    private void setContactsData() {
        List<ContractBean> contactData = ContractManager.getContacts(this);
        contacts.clear();
        contacts.addAll(contactData);
        // adapter.notifyDataSetChanged();
        Toast.makeText(AddLinkman.this, "添加成功", Toast.LENGTH_SHORT).show();

    }
}
