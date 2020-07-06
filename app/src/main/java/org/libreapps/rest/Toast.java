package org.libreapps.rest;

import android.content.Context;

public class Toast {
    public void showToast(Context context, String text, int length) {
        android.widget.Toast.makeText(context, text, length).show();
    }
}
