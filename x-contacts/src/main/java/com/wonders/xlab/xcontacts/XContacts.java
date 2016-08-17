package com.wonders.xlab.xcontacts;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

/**
 * Created by hua on 16/8/17.
 */
public class XContacts {
    private static Object object = new Object();
    private static XContacts xContacts;

    private XContacts() {
    }

    public static XContacts getInstance() {
        if (null == xContacts) {
            synchronized (object) {
                if (xContacts == null) {
                    xContacts = new XContacts();
                }
            }
        }
        return xContacts;
    }

    public void start(Activity activity) {
        activity.startActivity(new Intent(activity, XContactsActivity.class));
    }

    public void start(Fragment fragment) {
        fragment.startActivity(new Intent(fragment.getActivity(), XContactsActivity.class));
    }
}
