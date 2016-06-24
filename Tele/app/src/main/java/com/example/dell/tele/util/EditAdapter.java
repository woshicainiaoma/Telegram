package com.example.dell.tele.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.dell.tele.R;
import com.example.dell.tele.model.ContractBean;

import java.util.List;

/**
 * Created by dell on 2016/6/15.
 */
public class EditAdapter extends BaseAdapter {



    private Context context;
    private List<ContractBean> contacts;


    public EditAdapter(Context context, List<ContractBean> contacts) {
        this.context = context;
        this.contacts = contacts;

    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ContractBean mContent = contacts.get(position);
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_edit, null);
            //holder.tvLetter = (TextView)  convertView.findViewById(R.id.catalog);
          //  holder.txl_name = (TextView) convertView.findViewById(R.id.txl_name);
           // holder.txl_phone = (TextView) convertView.findViewById(R.id.txl_phone);
            holder.edit_name = (EditText) convertView.findViewById(R.id.edit_person);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ContractBean contact = (ContractBean) getItem(position);
       // holder.txl_name.setText(contact.getName());
      //  holder.txl_phone.setText(contact.getPhone());
        holder.edit_name.setText(contact.getName());
        return convertView;
    }

    static class ViewHolder {

        EditText edit_name;
        // TextView tvLetter;
    }
}
