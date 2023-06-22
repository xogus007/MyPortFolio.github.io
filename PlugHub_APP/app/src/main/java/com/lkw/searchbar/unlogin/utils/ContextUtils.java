package com.lkw.searchbar.unlogin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

// Context 확실하게 받는 방법
public class ContextUtils {
    public Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }

        if (!(context instanceof ContextWrapper)) {
            return null;
        }

        return context instanceof Activity
                ? (Activity) context
                : getActivity(((ContextWrapper) context).getBaseContext());
    }
}
