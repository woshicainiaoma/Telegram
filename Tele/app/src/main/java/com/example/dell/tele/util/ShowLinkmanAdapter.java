package com.example.dell.tele.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.tele.R;
import com.example.dell.tele.model.ContractBean;

import java.util.List;

/**
 * Created by dell on 2016/6/22.
 */
public class ShowLinkmanAdapter extends BaseAdapter {

    private Context context;
    private List<ContractBean> contacts;


    public ShowLinkmanAdapter(Context context, List<ContractBean> contacts) {
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
            convertView = View.inflate(context, R.layout.showlinkman, null);
            //holder.tvLetter = (TextView)  convertView.findViewById(R.id.catalog);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ContractBean contact = (ContractBean) getItem(position);
        holder.tv_name.setText(contact.getName());
        holder.tv_phone.setText(contact.getPhone());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_phone;
        // TextView tvLetter;
    }
}
