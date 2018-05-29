package com.letv.common;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 *
 * Created by letv on 2015/12/10.
 * Last edit by letv on 15:02 2015/12/10.
 */
@RunWith(LeRunner.class)
public class LetvTestCase{
    public static Instrumentation instrumentation;
    public static UiDevice phone;
    public static String folderName;
    public static final long WAIT_TIMEOUT = 5000L;
    // define static keyevent
    public static final int KEY_VOLUME_UP = 24;
    public static final int KEY_VOLUME_DOWN = 25;
    public static final int KEY_VOLUME_MUTE = 164;
    public static final int KEY_CHANNEL_UP = 166;
    public static final int KEY_CHANNEL_DOWN = 167;
    public static final int KEY_SETTING = 176;
    public static final int KEY_SHOW_DISPLAY = 165;
    public static final int KEY_SIGNAL_SOURCE = 4404;
    public static final int KEY_UP = 19;
    public static final int KEY_DOWN = 20;
    public static final int KEY_LEFT = 21;
    public static final int KEY_RIGHT = 22;
    public static final int KEY_CENTER = 23;
    public static final int KEY_BACK = 4;
    public static final int KEY_HOME = 3;
    public static final int KEY_MENU = 82;
    public static final int KEYCODE_DEL = 67;

    // MEDIA related
    public static final int KEY_MEDIA_PRE = 88;
    public static final int KEY_MEDIA_NEXT = 87;
    public static final int KEY_MEDIA_FAST_FORWARD = 90;
    public static final int KEY_MEDIA_REWIND = 89;
    public static final int KEY_MEDIA_PLAY_PAUSE = 85;
    public static final int KEY_MEDIA_STOP = 86;


    // DATA Source
    public static final int KEY_TV = 170;
    public static final int KEY_VGA = 4401;
    public static final int KEY_VOICE = 4409;
    // public static final int KEY_HDMI = 269;
    public static final int KEY_HDMI = 4435;
    public static final int KEY_CVBS = 4402;

    // Number 0-9
    public static final int KEY_NUM0 = 7;
    public static final int KEY_NUM1 = 8;
    public static final int KEY_NUM2 = 9;
    public static final int KEY_NUM3 = 10;
    public static final int KEY_NUM4 = 11;
    public static final int KEY_NUM5 = 12;
    public static final int KEY_NUM6 = 13;
    public static final int KEY_NUM7 = 14;
    public static final int KEY_NUM8 = 15;
    public static final int KEY_NUM9 = 16;

    public static final int KEY_REVIEW = 223;
    // public static final int KEY_LIST = 257;
    // 适用于X55
    public static final int KEY_LIST = 4436;
    public static final int KEY_TF_UP = 226;
    public static final int KEY_TF_DOWN = 227;
    public static final int KEY_TF_LEFT = 228;
    public static final int KEY_TF_RIGHT = 229;

    // This key is used to exempt online tracing
    public static final String KEY_TRACE = "trace";
    // Define status code
    public static final int STATUS_ANR = 10;
    public static final int STATUS_FC = 11;
    public static final int STATUS_TOMBSTONES = 12;
    public static final int STATUS_STEP = 15;
    public static final int STATUS_TITLE = 16;
    public static final int STATUS_SCREENSHOT = 17;
    public static final String TAG = LetvTestCase.class.getSimpleName();

    public Map<String,String> listApps = new HashMap<>();

    public static final String PACKAGE_HOME = "com.stv.launcher";

    // Define sdcard root directory
    public static final String ROOT_DIR = "/sdcard";
    private Bundle params;

    public static Long WAIT_TIME_OUT = 15000L;
    @Rule
    public final TestName name = new TestName();




    @Before
    public void setUp() throws Exception {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        phone = UiDevice.getInstance(instrumentation);
        params = InstrumentationRegistry.getArguments();
        Configurator.getInstance().setWaitForSelectorTimeout(20000);
        Log.i(TAG, "=====================case start time is :" + getCurrentTime());
        Method currentTestMethod = getClass().getDeclaredMethod(name.getMethodName()
        );
        if (currentTestMethod.isAnnotationPresent(CaseName.class)) {
            String cnName = currentTestMethod.getAnnotation(CaseName.class)
                    .value();
            send_status(STATUS_TITLE, "title", cnName);
        }
        folderName = getStrParams("caseFolder");
        createDir(ROOT_DIR + File.separator + "AutoSmoke_UI30" + File.separator
                + folderName);
        listApps = initMapApps();
        registerCommonWatcher();
    }

    @After
    public void tearDown() throws Exception {
        press_back(3);
        exitApp();
        System.out.println("The phone type is: " + Build.MODEL);
        unregisterCommonWatcher();
        Log.i(TAG, "==========================case end time is :" + getCurrentTime());
}






    public int GenerateRandom(int range){

        Random r = new Random();
        int n = r.nextInt(range);
        n = Math.abs(r.nextInt() % range);
        System.out.println("Random data : " + n);
        return n ;
    }

    public String getStrEngCh(int resourceId){
        return InstrumentationRegistry.getTargetContext().getString(resourceId);
    }

    public void exitApp(){
        for(int i=0;i<2;i++){
            press_back(1);
            sleepInt(1);
            UiObject2 exitApp =phone.findObject(By.text(Pattern.compile("退出|去意已决|狠心退出|直接退出|确定|确认|退出播放|残忍离去|确定")));
            if(exitApp!=null){
                exitApp.click();
                sleepInt(1);
                UiObject2 exitApp1 =phone.findObject(By.text(Pattern.compile("退出|去意已决|狠心退出|直接退出|确定|确认|退出播放|残忍离去|确定")));
                if(exitApp1!=null) {
                    exitApp1.click();
                    sleepInt(1);
                }
//                else {
//                    press_down(1);
//                    press_center(1);
//                }
            }
        }
    }

    public boolean gotoHomeScreen(String hs) {
        UiObject2 desktop1 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).selected(true));
        UiObject2 desktop2 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).focused(true));
        if (desktop1 != null || desktop2 != null) {
            press_back(1);
            return true;
        }
        UiObject2 launcher = phone.findObject(By.pkg(Pattern.compile("com.stv.launcher||com.stv.signalsourcemanager")));
        if (launcher != null) {
            press_back(1);
        } else {
            exitApp();
            exitApp();
        }
        if (!phone.getCurrentPackageName().equals(PACKAGE_HOME)) {
            press_home(1);
            sleepInt(2);
        }
        press_home(2);
        if (hs == "首页") {
            press_home(2);
            sleepInt(10);
            press_up(1);
            UiObject2 LIVEDesk = waitForObj(By.res("com.stv.launcher:id/launcher_tab_item_title").text("首页"));
            check("没有切换到首页桌面", LIVEDesk != null);
            return true;
        }
        sleepInt(6);
        press_left(4);
        press_home(1);
        UiObject2 singal = phone.findObject(By.pkg(Pattern.compile("com.stv.launcher|com.stv.signalsourcemanager")));

        if (hs == "信号源") {
            verify("没有切换到信号源桌面", singal != null);
            return true;
        }
        for (int i = 0; i < 30; i++) {
            UiObject2 tab = phone.findObject(By.text(Pattern.compile(hs)).pkg("com.stv.launcher"));
            if (tab != null) {
                tab.click();
                sleepInt(2);
                UiObject2 desktop3 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).selected(true));
                UiObject2 desktop4 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).focused(true));
                if (hs != "搜索") {
                    verify("没有切换到" + hs + "桌面", desktop3 != null || desktop4 != null);
                }
                return true;
            } else {
                press_right(1);
                if (i==3){
                    press_back(1);
                }
            }
        }
        UiObject2 desktop5 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).selected(true));
        UiObject2 desktop6 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).focused(true));
        verify("没有切换到" + hs + "桌面", desktop5 != null || desktop6 != null);
        return true;
    }


    //oneself write Eric
    public static void retry(){
        UiObject2 retry=phone.findObject(By.text("重试"));
        for(int l=0;l<5;l++) {
            if (retry != null) {
                retry.click();
                retry.click();
            }
            if (retry == null){
                break;
                }

            }
        }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
//    public boolean gotoHomeScreen(String hs) {
//        UiObject2 desktop1=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).selected(true));
//        UiObject2 desktop2=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).focused(true));
//        if(desktop1!=null||desktop2!=null){
//            press_back(1);
//            return true;
//        }
//        UiObject2 launcher=phone.findObject(By.pkg(Pattern.compile("com.stv.launcher|com.stv.signalsourcemanager")));
//        if(launcher!=null){
//            press_back(1);
//        }else {
//            exitApp();
//            exitApp();
//        }
//		 if(!phone.getCurrentPackageName().equals(PACKAGE_HOME)){
//		        	press_home(1);
//		        	sleepInt(2);
//		        }
//        press_home(2);
//        if(hs == "LIVE"){
//            press_home(1);
//            UiObject2 LIVEDesk=waitForObj(By.pkg("com.letv.android.tv.letvlive"));
//            verify("没有切换到Live桌面",LIVEDesk!=null);
//            return true;
//        }
//        sleepInt(6);
//        press_left(6);
//        UiObject2 singal=phone.findObject(By.pkg("com.stv.signalsourcemanager"));
//        if(hs == "信号源"){
//            verify("没有切换到信号源桌面",singal!=null);
//            return true;
//        }
//
//        for(int i=0;i<30;i++){
//            UiObject2 tab=phone.findObject(By.text(Pattern.compile(hs)).pkg("com.stv.launcher"));
//            if(tab!=null){
//                tab.click();
//                sleepInt(2);
//                UiObject2 desktop3=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).selected(true));
//                UiObject2 desktop4=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).focused(true));
//                UiObject2 desktop5=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).enabled(true));
//                if (hs != "搜索") {
//                    verify("没有切换到" + hs + "桌面", desktop3 != null || desktop4 != null || desktop5 != null );
//                }
//                return true;
//            }else press_right(1);
//        }
//        UiObject2 desktop5=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).selected(true));
//        UiObject2 desktop6=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(hs)).focused(true));
//        verify("没有切换到"+hs+"桌面",desktop5!=null||desktop6!=null);
//        return true;
//    }

    public void sleepInt(int sc) {
        if (sc <= 0) {
            return;
        }
        int tenNum = sc/10;
        int oneNum = sc%10;
        if (tenNum > 0) {
            for (int i = 0; i < tenNum; i++) {
                sleep(10 * 1000);
                phone.runWatchers();
            }
            sleep(oneNum * 1000);
        }else {
            sleep(oneNum * 1000);
        }
    }

    public void verify(boolean b) {
        if (!b) {
            screenShot();
            Assert.fail();
        }
    }

    public void verify(String msg,boolean b) {
        if(msg.equals("")) {
            verify(b);
        } else if (!b){
                screenShot();
            Assert.fail(msg);
        }
    }



    public void check(String msg, boolean b) {
        if (msg.equals("")) {
            check(b);
        } else if (!b) {
            throw new RuntimeException(msg);
        }
    }
    public void check(boolean b) {
        if (!b) {
            throw new RuntimeException();
        }
    }

    public void failCount (int count,int Loop,String msg) {
        if( Loop >4 && count > Loop/5+1) {
           // screenShot();
            Assert.fail(msg);
        } else if(Loop <=4 && count > Loop/2){
            screenShot();
            Assert.fail(msg);
        }
    }
    public static void screenShot() {
        String filename = System.currentTimeMillis() + ".png";
        String command = "screencap " + ROOT_DIR + File.separator
                + "AutoSmoke_UI30" + File.separator + folderName
                + File.separator + filename;
        String filepath = ROOT_DIR + File.separator
                + "AutoSmoke_UI30" + File.separator + folderName
                + File.separator + filename;
        String dirname = ROOT_DIR + File.separator
                + "AutoSmoke_UI30" + File.separator + folderName
                + File.separator;
        Log.i(TAG, "screenShot: " + filepath);
        Bundle b = new Bundle();
        b.putString("screencap", filename);
        send_status(STATUS_SCREENSHOT, "screencap", filename);
        createDir(dirname);
        Log.i(TAG, "screenShot: "+filepath);
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(Build.VERSION.SDK_INT >20){
            callShell("screencap -p /sdcard/tmp.png");
            callShell(command);
        }else  {
            File fileTmp=new File("/sdcard/tmp.png");
            phone.takeScreenshot(fileTmp);
            phone.takeScreenshot(file);
        }
    }





    public static String callShell(String shellString) {
        try {
            return phone.executeShellCommand(shellString);
        } catch (IOException e) {
            System.out.println("call shell failed!!");
            e.printStackTrace();
            return null;
        }
    }


    public static String callShellAuto(String shellString) {
        try {
            Process process = Runtime.getRuntime().exec(shellString);
            int exitValue = process.waitFor();
            System.out.println("exitvalue"+exitValue);
            if (0 != exitValue) {
                System.out.println("call shell failed. error code is :"
                        + exitValue);
            }
            InputStream in = process.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int count = -1;
            while ((count = in.read(data, 0, 1024)) != -1)
                outStream.write(data, 0, count);
            data = null;
            return new String(outStream.toByteArray(), "UTF-8");
        } catch (Throwable e) {
            System.out.println("call shell failed!!");
            e.printStackTrace();
            return null;
        }
    }


    public static String runShell(String cmd) {
        try {
            String cmds[] = new String[3];
            cmds[0] = "sh";
            cmds[1] = "-c";
            cmds[2] = cmd;
            Process pcs = Runtime.getRuntime().exec(cmds);

            String result = null;
            BufferedInputStream in = new BufferedInputStream(
                    pcs.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lineStr;

            while ((lineStr = br.readLine()) != null) {
                result = lineStr;
            }
            br.close();
            in.close();
            // System.out.println("==============================" + result);
            return result;
        } catch (Throwable e) {
            System.out.println("==============================null");
            e.printStackTrace();
            return null;
        }
    }

    public void sleep(int time){
        SystemClock.sleep(time);
    }

    public String getStrParams(String key){return params.getString(key);}

    // public int getIntParams(String key){return params.getInt(key);}
    public int getIntParams(String key){return Integer.parseInt(params.getString(key));}

    public static String getCurrentTime() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int day = ca.get(Calendar.DATE);
        int minute = ca.get(Calendar.MINUTE);
        int hour = ca.get(Calendar.HOUR);
        int second = ca.get(Calendar.SECOND);
        return (String.valueOf(year) + "-"
                + String.valueOf(month + 1) + "-" + String.valueOf(day) + "-"
                + String.valueOf(hour) + "-" + String.valueOf(minute) + "-" + String
                .valueOf(second));
    }

    public void addStep(String comment){
        Bundle b = new Bundle();
        b.putString("casestep",comment);
        send_status(STATUS_STEP,"caseStep", comment);
        Bundle c = new Bundle();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format( now );
        c.putString("current time",time);
        send_status(STATUS_STEP, "current time",time);
    }

    public int getRandom(int k){
        int i=1+(int)(Math.random()*(k-2));//获取1~(k-1)之间的随机整数
        return i;
    }



    // create case folder to save logs and screenshot
    public static void createDir(String dir) {
        File folder = new File(dir);
        if (folder!=null) {
            System.out.println("The folder " + dir + " has existed!");
            System.out.println("current_case_root_folder=" + folderName);
            // createLogFile(dir);
            return;
        }
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        // create folder
        if (folder.mkdirs()) {
            System.out.println("Create folder " + dir + " success!");
            System.out.println("current_case_root_folder=" + folderName);
            // createLogFile(dir);
        } else {
            System.out.println("Create folder " + dir + " fail!");
        }
    }

    public void press_keyevent(int times, int keycode) {
        if (times < 1)
            return;
        for (int i = times; i > 0; i--) {
            phone.pressKeyCode(keycode);
            sleep(500);
        }
    }


    public void press_back(int times) {
        press_keyevent(times, KEY_BACK);
    }

    public void press_menu(int times) {
        press_keyevent(times, KEY_MENU);
    }
    public void press_menu1(int times){
        if (times<=0){
            return;
        }
        for (int i=0;i<times;i++) {
            phone.pressMenu();
            sleep(5000);
        }
    }
    public void press_voice(int times) {
        press_keyevent(times, KEY_VOICE);
    }

    public void press_up(int times) {
        press_keyevent(times, KEY_UP);
    }

    public void press_down(int times) {
        press_keyevent(times, KEY_DOWN);
    }

    public void press_left(int times) {
        press_keyevent(times, KEY_LEFT);
    }

    public void press_right(int times) {
        press_keyevent(times, KEY_RIGHT);
    }

    public void press_center(int times) {
        press_keyevent(times, KEY_CENTER);
    }

    public void press_setting(int times) {
        press_keyevent(times, KEY_SETTING);
    }

    public void press_home(int times) {
        press_keyevent(times, KEY_HOME);
    }

    public void press_tf_up(int times) {
        press_keyevent(times, KEY_TF_UP);
    }

    public void press_tf_down(int times) {
        press_keyevent(times, KEY_TF_DOWN);
    }

    public void press_tf_left(int times) {
        press_keyevent(times, KEY_TF_LEFT);
    }

    public void press_tf_right(int times) {
        press_keyevent(times, KEY_TF_RIGHT);
    }

    public void press_vol_up(int times) {
        press_keyevent(times, KEY_VOLUME_UP);
    }

    public void press_vol_down(int times) {
        press_keyevent(times, KEY_VOLUME_DOWN);
    }

    public void press_ch_up(int times) {press_keyevent(times,KEY_CHANNEL_UP);}

    public void press_ch_down(int times) {press_keyevent(times,KEY_CHANNEL_DOWN);}

    public void pressDialPad(String number){
        for (int i = 0; i < number.length(); i++) {
            UiObject2 numBtn = phone.findObject(By.clazz("android.widget.TextView")
                    .res("com.android.dialer:id/dialpad_key_number")
                    .text(String.valueOf(number.charAt(i))));
            numBtn.click();
            sleep(500);
        }
    }

    public void swipe_screen(double startX, double startY, double endX, double endY,
                             int swipeSpeed) {
        phone.swipe((int) startX, (int) startY, (int) endX, (int) endY, swipeSpeed);
        sleep(500);
    }

    public static void send_status(int code, String key, String value) {
        Bundle b = new Bundle();
        b.putString(key, value);
        instrumentation.sendStatus(code, b);
    }

    public boolean waitForExist(BySelector selector){
        return phone.wait(Until.hasObject(selector),WAIT_TIME_OUT);
    }

    public boolean waitForExist(BySelector selector,long timeout){
        return phone.wait(Until.hasObject(selector),timeout);
    }

    public boolean waitUntilGone(BySelector selector){
        return phone.wait(Until.gone(selector), WAIT_TIME_OUT);
    }

    public boolean waitUntilGone(BySelector selector,long timeout){
        return phone.wait(Until.gone(selector),timeout);
    }

    public UiObject2 waitForObj(BySelector selector){
        return phone.wait(Until.findObject(selector),WAIT_TIME_OUT);
    }

    public UiObject2 waitForObj(BySelector selector,long timeout){
        return phone.wait(Until.findObject(selector),timeout);
    }

    public List<UiObject2> waitForObjs(BySelector selector){
        return phone.wait(Until.findObjects(selector), WAIT_TIME_OUT);
    }

    public List<UiObject2> waitForObjs(BySelector selector,long timeout){
        return phone.wait(Until.findObjects(selector), timeout);
    }

    public boolean clickAndWaitForNewWindow(UiObject2 obj){
        return obj.clickAndWait(Until.newWindow(), WAIT_TIME_OUT);
    }

    public boolean clickAndWaitForNewWindow(UiObject2 obj,long timeout){
        return obj.clickAndWait(Until.newWindow(), timeout);
    }

    public void launchAppByPackage(String pkg) throws RemoteException {
       gotoHomeScreen("应用");
        if (pkg.equals("") || pkg == null) {
            System.out.println("the App name can't be null!!!");
        } else {
            callShell("am start -S " + pkg);
        }
    }


    public boolean launchApp(String appName,String pkg) {
        return launchApp(appName,pkg,true);
    }

    public boolean launchApp(String app,String pkg, boolean flag) {
        // verify the app is not null
        if (app == "" || app == null) {
            System.out.println("the App name can't be null!!!");
            return false;
        }
        // verify if the app is a valid App
        if (!(listApps.containsKey(app))) {
            screenShot();
            if (flag) {
                Assert.fail("The app " + app + "is not a valid App, please check the App name.");
            }
            System.out.println("The app " + app + "is not a valid App, please check the App name.");
            return false;
        }
        // go to App desk
        gotoHomeScreen("应用");
        ArrayList<String> alrealyEnterFoler = new ArrayList<>();
        UiObject2 appItem;
        List<UiObject2> folders;
        for (int i = 0; i < 4; i++) {
            appItem = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(app)));
            if (appItem != null) { // find the app oncurrent window
                appItem.click();
                sleepInt(1);
                for(int k=0;k<3;k++) {
                    appItem = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(app)));
                    if (appItem != null) {
                        appItem.click();
                        sleepInt(1);
                    }
                }
                UiObject2 appItem2 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(app)));
                if (appItem2 != null) {
                    if (pkg.equals("") || pkg == null) {
                        System.out.println("the App name can't be null!!!");
                    } else {
                        startActivity(pkg);
                    }
                }
                verify("Can not found " + app, waitForExist(By.pkg(Pattern.compile(listApps.get(app)))));
                sleepInt(5);
                return true;
            }
            press_down(2);
        }

        // launchapp by package
        if (pkg.equals("") || pkg == null) {
            System.out.println("the App name can't be null!!!");
        } else {
            addStep("launch app by package");
            startActivity(pkg);
            verify("Can not found " + app, waitForExist(By.pkg(Pattern.compile(listApps.get(app)))));
            return true;
        }
        return false;
    }



    /* 这个方法已经不用了
    public boolean launchApp(String appName,String pkg) {
        return launchApp(appName,pkg,true);
    }

    public boolean launchApp(String app,String pkg, boolean flag) {
        // verify the app is not null
        if (app == "" || app == null) {
            System.out.println("the App name can't be null!!!");
            return false;
        }
        // verify if the app is a valid App
        if (!(listApps.containsKey(app))) {
            screenShot();
            if (flag) {
                Assert.fail("The app " + app + "is not a valid App, please check the App name.");
            }
            System.out.println("The app " + app + "is not a valid App, please check the App name.");
            return false;
        }
        // go to App desk
        gotoHomeScreen("应用");
        retry();
        ArrayList<String> alrealyEnterFoler = new ArrayList<>();
        UiObject2 appItem;
        List<UiObject2> folders;
        for (int i = 0; i < 3; i++) {
                    appItem = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(app)));
                    if (appItem != null) { // find the app oncurrent window
                        appItem.click();
                        sleepInt(1);
                        for(int k=0;k<3;k++) {
                            appItem = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(app)));
                            if (appItem != null) {
                                appItem.click();
                                sleepInt(1);
                            }
                        }
                UiObject2 appItem2 = phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile(app)));
                if (appItem2 != null) {
                    if (Build.VERSION.SDK_INT > 20) {
                        if (pkg.equals("") || pkg == null) {
                            System.out.println("the App name can't be null!!!");
                        } else {
                            callShell("am start -S " + pkg);
                            sleepInt(1);
                        }
                    }
                }
                verify("Can not found " + app, waitForExist(By.pkg(Pattern.compile(listApps.get(app)))));
                sleepInt(5);
                return true;
            }
            press_down(5);
        }
        sleepInt(5);

        UiObject2 deskno = phone.findObject(By.text("重试"));
        for (int i = 0; i < 3; i++) {
            if (deskno != null) {
                deskno.click();
                sleepInt(5);
            }
        }

        // launchapp by package
        if(Build.VERSION.SDK_INT >20) {
            if (pkg.equals("") || pkg == null) {
                System.out.println("the App name can't be null!!!");
            } else {
                addStep("launch app by package");
                callShell("am start -S " + pkg);
                sleepInt(5);
                verify("Can not found " + app, waitForExist(By.pkg(Pattern.compile(listApps.get(app)))));
                return true;
            }
        }
        return false;
    }

    */


    public boolean launchApp(String pkg) {
        return launchApp(pkg,true);
    }

    public boolean launchApp(String pkg, boolean flag){
        if (pkg.equals("") || pkg == null) {
            System.out.println("the pkg name can't be null!!!");
            return false;
        } else {
            addStep("launch app by package");
            startActivity(pkg);
//            verify("Can not found " + pkg, waitForExist(By.pkg(Pattern.compile(listApps.get(app)))));
            return true;
        }

//        return false;
    }

    public void startActivity(String pkg) {

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        //sets the intent to start your app
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //starts the app
        context.startActivity(intent);
        sleepInt(10);
    }








    public boolean verifyByImg(String target, int x, int y, int x1, int y1,
                               double percent) {
        int width = x1 - x;
        int height = y1 - y;
        String p1 = target;
        String p2 = "/sdcard/current.png";
        File f1 = new File(p1);
        File f2 = new File(p2);
        phone.takeScreenshot(f2);
        Bitmap sub1 = null;
        try {
            sub1 = Util.getSubImage(p1, x, y, width, height);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap sub2 = null;
        try {
            sub2 = Util.getSubImage(p2, x, y, width, height);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean same = Util.sameAs(sub1, sub2, percent);
//        if (!same) {
//            Assert.fail("图片对比失败,target:" + target + ", dst:" + p2);
//        }
        return same;
    }


    public static boolean LetvUI(double UI) {
        Class<?> c1 = Build.class;
        String  letvUI = null;
        Method m1 = null;
        boolean flag=false;
        try {
            m1 = c1.getDeclaredMethod("getString", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        m1.setAccessible(true);
        try {
            letvUI = (String) m1.invoke(c1,"ro.letv.ui");
            double letvui=Double.valueOf(letvUI).doubleValue();
            if (UI==letvui){
                flag=true;
            }else{
                flag=false;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "build ui:"+letvUI);
        return flag;
    }

    public void verifyByImg(String target, double percent) {
        String p1 = target;
        String p2 = "/sdcard/2.png";
        File f1 = new File(p1);
        File f2 = new File(p2);
        phone.takeScreenshot(f2);
        boolean same = false;
        try {
            same = Util.sameAs(p1, p2, 0.9);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!same) {
            Assert.fail("图片对比失败,target:" + target + ", dst:" + p2);
        }
    }


    private void registerCommonWatcher() {
        phone.registerWatcher("anrWatcher", LetvWatcher.anrWatcher);
        phone.registerWatcher("fcWatcher", LetvWatcher.fcWatcher);
        phone.registerWatcher("allowWatcher", LetvWatcher.allowWatcher);
    }

    private void unregisterCommonWatcher() {
        phone.removeWatcher("anrWatcher");
        phone.removeWatcher("fcWatcher");
        phone.removeWatcher("allowWatcher");
    }

    private Map<String ,String> initMapApps(){
        Map<String,String> map = new HashMap<>();
        map.put(AppName.GoogleSearch,PkgName.GoogleSearch);
        map.put(AppName.TVlive,PkgName.TVlive);
        map.put(AppName.Upgrade,PkgName.Upgrade);
        map.put(AppName.Channel,PkgName.Channel);
        map.put(AppName.LeAccount,PkgName.LeAccount);
        map.put(AppName.Browser,PkgName.Browser);
        map.put(AppName.Filemanager,PkgName.Filemanager);
        map.put(AppName.Camera,PkgName.Camera);
        map.put(AppName.Settings,PkgName.Settings);
        map.put(AppName.SystemUpdate,PkgName.SystemUpdate);
        map.put(AppName.LeTv,PkgName.LeTv);
        map.put(AppName.LeStore,PkgName.LeStore);
        map.put(AppName.GameCenter,PkgName.GameCenter);
        map.put(AppName.GunGame,PkgName.GunGame);
        map.put(AppName.Message,PkgName.Message);
        map.put(AppName.Calendar,PkgName.Calendar);
        map.put(AppName.Download,PkgName.Download);
        map.put(AppName.TvManager,PkgName.TvManager);
        map.put(AppName.LeSearch,PkgName.LeSearch);
        map.put(AppName.Music,PkgName.Music);
        map.put(AppName.Help,PkgName.Help);
        map.put(AppName.Weather ,PkgName.Weather);
        map.put(AppName.PlayHistory,PkgName.PlayHistory);
        map.put(AppName.LeCloud,PkgName.LeCloud);
        map.put(AppName.LeFans,PkgName.LeFans);
        map.put(AppName.DuoLe,PkgName.DuoLe);
        map.put(AppName.Inotice,PkgName.Inotice);
        map.put(AppName.LeSports,PkgName.LeSports);
        map.put(AppName.Shop ,PkgName.Shop);
        map.put(AppName.Gallery ,PkgName.Gallery);
        map.put(AppName.Voice ,PkgName.Voice);
        map.put(AppName.LeSo ,PkgName.LeSo);
        map.put(AppName.CIBN ,PkgName.CIBN);
        map.put(AppName.HomeTime ,PkgName.HomeTime);
        map.put(AppName.Cinemas ,PkgName.Cinemas);
        map.put(AppName.Feedback ,PkgName.Feedback);
        map.put(AppName.Tool ,PkgName.Tool);
        map.put(AppName.ChildrenTV ,PkgName.ChildrenTV);

        return map;
    }

    /*648平台桌面调整*/
    public void DesktopAdjustment(String args) throws RemoteException, UiObjectNotFoundException {
        int count=0;
        addStep("进入管理桌面");
        gotoHomeScreen("应用");
        UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
        check("桌面管理没有找到", deskManager != null);
        deskManager.click();
        deskManager.click();
        sleepInt(1);
        try {
//              if (LetvUI(6.5)){
            DeskSwitchScarchMyLefan(args);
//                }else {
//                DeskSwitch();
//                }
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                DeskSwitchScarchMyLefan(args);
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
            press_back(2);
            }
        }

    public void DeskSwitchScarchMyLefan(String args) throws UiObjectNotFoundException, RemoteException {
        for(int i =0;i<4;i++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("应用")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_right(3);
                press_center(1);
                press_left(1);
                press_center(1);
                press_right(3);
                press_down(1);
                press_center(1);
                break;
            }
            else {
                press_right(1);
            }
        }
        sleepInt(2);
        addStep("调整到"+args);
        for(int j=0;j<4;j++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text(Pattern.compile(args))).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_up(1);
                press_right(3);
                press_center(1);
                break;
            }
            else {
                press_left(1);
            }
        }

    }





}
