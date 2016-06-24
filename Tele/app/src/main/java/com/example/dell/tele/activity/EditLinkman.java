package com.example.dell.tele.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.tele.R;
import com.example.dell.tele.db.ContractManager;
import com.example.dell.tele.model.ContractBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/15.
 */
public class EditLinkman extends Activity {

    private List<ContractBean> contacts = new ArrayList<ContractBean>();
    //private EditAdapter adapter;
   // private ListView editList;
    private EditText editName;
    private EditText editNum;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_linkman);

       // editList = (ListView) findViewById(R.id.edit_list);
      //  adapter = new EditAdapter(this,contacts);
        editName = (EditText) findViewById(R.id.edit_name1);
        editNum = (EditText) findViewById(R.id.edit_phone1);
        saveBtn = (Button) findViewById(R.id.add_link1);
     //   editList.setAdapter(adapter);
        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        //editName.setGravity(Gravity.RIGHT);

      //  setContactsData(name);
       // editName.setText(contacts.);
        getShow(this, name);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = getContactID(name, EditLinkman.this);

                ContractBean contact = new ContractBean();

                contact.setRawContactId(Integer.valueOf(id).longValue());
                contact.setName(editName.getText() + "");
                contact.setPhone(editNum.getText() + "");

                ContractManager.updateContact(EditLinkman.this, contact);

                String result = (editName.getText() + "");
                Intent intent1 = new Intent();
                intent1.putExtra("result", result);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(1001, intent1);



                EditLinkman.this.finish();

                Toast.makeText(EditLinkman.this,"ok",Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setContactsData(String name) {

    }

    private static String getContactID(String name, Context context) {
        String id = "0";
        Cursor cursor = context.getContentResolver().query(
                android.provider.ContactsContract.Contacts.CONTENT_URI,
                new String[]{android.provider.ContactsContract.Contacts._ID},
                android.provider.ContactsContract.Contacts.DISPLAY_NAME +
                        "='" + name + "'", null, null);
        if (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex(
                    android.provider.ContactsContract.Contacts._ID));
        }
        return id;
    }


    public void getShow(Context context, String name) {

      //  List<ContractBean> contacts = new ArrayList<ContractBean>();
        String id = getContactID(name, context);
        Cursor cursor = null;
        try {
            // cursor =context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY);

            //   cursor =context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ "=" +name,null,null);

            cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, ContactsContract.Data.CONTACT_ID + "=" + id, null, null);


            ContractBean contact = null;
            while (cursor.moveToNext()) {

                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String displayNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
               // contact = new ContractBean();

               editName.setText(displayName);
                editName.setSelection(editName.getText().length());
                editNum.setText(displayNum);
                //contact.setPhone(displayName);
              //  contacts.add(contact);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();

            }
        }
       // return contacts;
    }

}
