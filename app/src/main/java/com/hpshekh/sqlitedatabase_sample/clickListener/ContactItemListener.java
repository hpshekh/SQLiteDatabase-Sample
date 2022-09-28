package com.hpshekh.sqlitedatabase_sample.clickListener;

import com.hpshekh.sqlitedatabase_sample.model.Contact;

public interface ContactItemListener {
    void onItemClick(Contact contact);
    void onItemLongClick(Contact contact);
}
