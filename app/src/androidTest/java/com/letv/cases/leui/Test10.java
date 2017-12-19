package com.letv.cases.leui;

import android.app.Instrumentation;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Instrumentation;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.PkgName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class Test10 extends LetvTestCase{
    Instrumentation instrument;
    UiDevice device;

    @Before
    public void setUp(){
        instrument= InstrumentationRegistry.getInstrumentation();
        device=UiDevice.getInstance(instrument);
        device.pressBack();
        device.pressBack();
        device.pressHome();
        device.pressEnter();
    }
    @After
    public void tearDown(){
        device.pressBack();
        device.pressBack();
        device.pressHome();
        device.pressEnter();
    }
    @Test
    @CaseName("App天气")
    public void testUiDeviceNewApi()throws UiObjectNotFoundException,RemoteException {
        launchApp(AppName.Tianqi, IntentConstants.Tianqi);
        UiObject2 calendar= device.findObject(By.clazz("android.widget.ImageView").res("com.miui.home:id/icon_icon"));
        calendar.click();
        device.pressEnter();
    }
}

