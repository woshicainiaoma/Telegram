package com.example.dell.tele.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.tele.R;
import com.example.dell.tele.db.ContractManager;
import com.example.dell.tele.db.GroupManager;
import com.example.dell.tele.model.ContractBean;
import com.example.dell.tele.util.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/7.
 */
public class AddLinkman extends Activity {

    private EditText add_name;
    private EditText add_phone;
    private EditText add_email;
    private EditText add_adress;
    private EditText add_homePhone;
    private EditText add_workPhone;
    private Button add_link;
   // private List<ContractBean> contacts = new ArrayList<ContractBean>();
    //private ContactAdapter adapter;
    private Spinner sn_group;
    private List<ContractBean> contacts = new ArrayList<ContractBean>();
    private GroupAdapter adapter;
    private ListView lv_name;
    private static long nameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.back_layout);
        setContentView(R.layout.linkman_layout);
        add_link = (Button) findViewById(R.id.add_link);
        add_name = (EditText) findViewById(R.id.edit_name);
        add_phone = (EditText) findViewById(R.id.edit_phone);
        add_adress = (EditText)findViewById(R.id.edit_adress);
        add_email = (EditText) findViewById(R.id.edit_email);
        add_homePhone = (EditText) findViewById(R.id.edit_homephone);
        add_workPhone = (EditText) findViewById(R.id.edit_workphone);
        sn_group = (Spinner) findViewById(R.id.show_groupname);


        adapter = new GroupAdapter(this,contacts);
       sn_group.setAdapter(adapter);
        //lv_name = (ListView) findViewById(R.id.lv_groupname);
       // lv_name.setAdapter(adapter);
        setContactsData();
        sn_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ContractBean contact = (ContractBean) adapter.getItem(position);
                long groupId = Integer.valueOf((int) contact.getGroupId());
                nameId = groupId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContractBean contact = new ContractBean();
                contact.setName(add_name.getText() + "");
                contact.setPhone(add_phone.getText() + "");
                contact.setEmail(add_email.getText() + "");
                contact.setAdress(add_adress.getText() + "");
                contact.setHomePhone(add_homePhone.getText() + "");
                contact.setAdress(add_adress.getText() + "");
                contact.setWorkPhone(add_workPhone.getText() + "");
                contact.setGroupId(nameId);
                ContractManager.addContact(AddLinkman.this, contact);
               // setContactsData();
               // Intent intent = new Intent(AddLinkman.this, MainActivity.class);
               // startActivity(intent);
                AddLinkman.this.finish();
                Toast.makeText(AddLinkman.this, "添加成功", Toast.LENGTH_SHORT).show();

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
