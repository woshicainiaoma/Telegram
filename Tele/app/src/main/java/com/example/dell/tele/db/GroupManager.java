package com.example.dell.tele.db;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.dell.tele.model.ContractBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/22.
 */
public class GroupManager {

    public static List<ContractBean> getAllGroupInfo(Context context) {

        List<ContractBean> groupList = new ArrayList<ContractBean>();

        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(ContactsContract.Groups.CONTENT_URI,
                    null, null, null, null);

            while (cursor.moveToNext()) {

               // GroupEntity ge = new GroupEntity();
                ContractBean contact = new ContractBean();
                int groupId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Groups._ID)); // 组id
                String groupName = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Groups.TITLE)); // 组名

                contact.setGroupId(groupId);
                contact.setGroupName(groupName);

               // Log.i("MainActivity", "group id:" + groupId + ">>groupName:"
                      //  + groupName);

                groupList.add(contact);
                contact = null;
            }

            return groupList;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static List<ContractBean> getAllContactsByGroupId(int groupId, Context context) {

        String[] RAW_PROJECTION = new String[] { ContactsContract.Data.RAW_CONTACT_ID, };

        String RAW_CONTACTS_WHERE = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
                + "=?"
                + " and "
                + ContactsContract.Data.MIMETYPE
                + "="
                + "'"
                + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
                + "'";

        // 通过分组的id 查询得到RAW_CONTACT_ID
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI, RAW_PROJECTION,
                RAW_CONTACTS_WHERE, new String[] { groupId + "" }, "data1 asc");

        List<ContractBean> contactList = new ArrayList<ContractBean>();

        while (cursor.moveToNext()) {
            // RAW_CONTACT_ID
            int col = cursor.getColumnIndex("raw_contact_id");
            int raw_contact_id = cursor.getInt(col);

            // Log.i("getAllContactsByGroupId", "raw_contact_id:" +
            // raw_contact_id);

            ContractBean ce = new ContractBean();

           // ce.setContactId(raw_contact_id);
            ce.setGroupId(raw_contact_id);

            Uri dataUri = Uri.parse("content://com.android.contacts/data");
            Cursor dataCursor = context.getContentResolver().query(dataUri,
                    null, "raw_contact_id=?",
                    new String[] { raw_contact_id + "" }, null);

            while (dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor
                        .getColumnIndex("data1"));
                String mime = dataCursor.getString(dataCursor
                        .getColumnIndex("mimetype"));

                if ("vnd.android.cursor.item/phone_v2".equals(mime)) {
                    ce.setPhone(data1);
                } else if ("vnd.android.cursor.item/name".equals(mime)) {
                    ce.setName(data1);
                }
            }

            dataCursor.close();
            contactList.add(ce);
            ce = null;
        }

        cursor.close();

        return contactList;
    }

}
