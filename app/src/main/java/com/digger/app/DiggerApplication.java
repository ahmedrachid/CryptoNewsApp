package com.digger.app;

import android.app.Application;

import com.onesignal.OneSignal;

public class DiggerApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
