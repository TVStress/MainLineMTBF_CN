package com.letv.common;

import org.junit.Test;

import java.io.File;

/**
 * Created by zhoujine on 2016/5/10.
 */
public class LetvTestCase_test extends LetvTestCase{
    @Test
    public void testGoToHomeScreen(){
        gotoHomeScreen("应用");
        gotoHomeScreen("LIVE");
        gotoHomeScreen("信号源");
    }

    @Test
    public void testLaunchApp(){
        launchApp(AppName.Browser,PkgName.Browser);
        launchApp(AppName.Filemanager,PkgName.Filemanager);
        launchApp(AppName.LeAccount,PkgName.LeAccount);
        launchApp(AppName.Calendar, PkgName.Calendar);
        launchApp(AppName.LeStore, PkgName.LeStore);
        launchApp(AppName.Gallery, PkgName.Gallery);
        launchApp(AppName.LeTv, PkgName.LeTv);
        launchApp(AppName.Music,PkgName.Music);
        launchApp(AppName.Weather, PkgName.Weather);
        launchApp(AppName.LeSo, PkgName.LeSo);
        launchApp(AppName.Cinemas, PkgName.Cinemas);

    }

    @Test
    public void test(){
//        gotoHomeScreen("应用");
////        UiObject2 o = phone.findObject(By.depth(12).clazz("android.widget.RelativeLayout"));
////        verify("sss",o!=null);
////        Log.i(TAG, "test: "+o.getVisibleBounds());
////        if (o != null){
////            o.click();
////            phone.pressDPadCenter();
////            press_center(1);
////        }
//        UiObject2 o = phone.findObject(By.text("乐视体育"));
//        o.click();

    }

    @Test
    public void testCallShell(){
        File file = new File("/sdcard/mengfei.png");
        phone.takeScreenshot(file);
//        callShellAuto("screencap /sdcard/mengfei.png");
    }

    @Test
    public void testCallShellAuto(){
        callShellAuto("su -c am start -S com.android.browser/.LoadingActivity");
    }

    @Test
    public void testScreencap(){
        screenShot();
    }
}
