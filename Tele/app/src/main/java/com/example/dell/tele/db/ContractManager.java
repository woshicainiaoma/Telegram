package com.example.dell.tele.db;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;

import com.example.dell.tele.model.ContractBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/7.
 */
public class ContractManager {

    public static List<ContractBean> getContacts(Context context) {

        List<ContractBean> contacts = new ArrayList<ContractBean>();

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY);
            ContractBean contact = null;
            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contact = new ContractBean();
                // String displayNub = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //name = displayName;

                contact.setName(displayName);
                contacts.add(contact);
                //  Log.d(TAG,displayName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();

            }
        }
        return contacts;
    }

    public static void addContact(Context context, ContractBean contact) {

        ContentValues values = new ContentValues();

        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);


        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.getName());
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getPhone());
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Email.ADDRESS, contact.getEmail());
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getAdress());
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getHomePhone());
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getWorkPhone());
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

//        values.clear();
//        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
//        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
//        values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, contact.getPhone());
//        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
//        //ContentValues valuesData2 = new ContentValues();
        values.clear();
        values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, contact.getGroupId());
        values.put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
                ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
       context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);


    }

    public static void updateContact(Context context, ContractBean contact) {
        ContentResolver resolver = context.getContentResolver();

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation
                .newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(
                        ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
                        new String[]{
                                String.valueOf(contact.getRawContactId()),
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getName())
                .build());

        ops.add(ContentProviderOperation
                .newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(
                        ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
                        new String[]{
                                String.valueOf(contact.getRawContactId()),
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE})
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getPhone()).build());
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

    }

    public static void deleteContact(Context context, ContractBean contact) {

        ContentResolver resolver = context.getContentResolver();
//        resolver.delete(ContactsContract.Data.CONTENT_URI, ContactsContract.Data.RAW_CONTACT_ID + "=?",
//                new String[] { String.valueOf(contact.getRawContactId()) });


        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String id = getContactID(contact.getName(), context);

        //delete contact
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=" + id, null)
                .build());
        //delete contact information such as phone number,email
        ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.Data.CONTACT_ID + "=" + id, null)
                .build());
        //Log.d(TAG, "delete contact: " + contact.getName());

        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, ops);
            //Log.d(TAG, "delete contact success");
        } catch (Exception e) {
            // Log.d(TAG, "delete contact failed");
            //Log.e(TAG, e.getMessage());
        }
        //Log.w(TAG, "**delete end**");
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


    public static List<ContractBean> getShow(Context context, String name) {

        List<ContractBean> contacts = new ArrayList<ContractBean>();
        String id = getContactID(name, context);
        Cursor cursor = null;
        try {
            // cursor =context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY);

            //   cursor =context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ "=" +name,null,null);

            cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.Data.CONTACT_ID + "=" + id, null, null);


            ContractBean contact = null;
            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact = new ContractBean();


                contact.setPhone(displayName);
                contacts.add(contact);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();

            }
        }
        return contacts;
    }


    public static List<ContractBean> getFuzzyQueryByName(String key, Context context){

        List<ContractBean> contacts = new ArrayList<ContractBean>();
        ContentResolver cr = context.getContentResolver();
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.Contacts.DISPLAY_NAME + " like " + "'%" + key + "%'",
                null, null);
        ContractBean contact = null;
        while(cursor.moveToNext()){
            String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contact.setName(name);
            contact.setPhone(number);
            contacts.add(contact);
        }
        cursor.close();

        return contacts;

    }




}
