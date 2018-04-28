package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
public class LetvStoreStressApp extends LetvTestCase {
    int count=0;
    @Test
    @CaseName("LetvStore里下载应用")
    public void testDownloadApp() throws UiObjectNotFoundException, RemoteException {
        addStep("打开LetvStore");
        launchApp(AppName.LeStore, IntentConstants.LeStore);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("...........looper : " + Loop);
            try{
                DownloadApp();
            }
            catch (Exception e){
                try{
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    launchApp(AppName.LeStore, IntentConstants.LeStore);
                    DownloadApp();
                }
                catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }

        }
        press_back(1);
        sleepInt(2);
        UiObject2 exitok = waitForObj(By.text("退出"));
        exitok.click();
        press_back(4);
    }
    public void DownloadApp() throws UiObjectNotFoundException, RemoteException {
        BySelector upgradeBtnS = By.text(Pattern.compile("升 级|Upgrade"));
        UiObject2 upgradeBtn = phone.findObject(upgradeBtnS);
        if (upgradeBtn != null) {
            addStep("检测到升级按钮，升级乐视应用商店");
            upgradeBtn.click();
            sleepInt(120);
            press_right(4);
            sleepInt(10);
        }
        UiObject2 install = phone.findObject(By.text("一键安装"));
        if (install != null) {
            press_back(1);
            sleepInt(1);
        }
//        UiObject2 manager = phone.findObject(storeS);
//        check("未能进入LetvStore主界面", manager != null);
        press_up(1);
        String arr[] = {"精品", "应用", "游戏","分类"};
        for (int i = 0; i < arr.length; i++) {
            UiObject2 arrLine = phone.findObject(By.text(arr[i]));
            arrLine.click();
            press_right(1);
            sleepInt(2);
        }
        press_up(1);
        press_right(1);
        sleepInt(1);
        addStep("搜索App");
        UiObject2 search = phone.findObject(By.res("com.letv.tvos.appstore:id/tv_search").text("搜索"));
        check("未进入搜索", search != null);
        search.click();
        search.click();
        sleepInt(1);
        UiObject2 D = waitForObj(By.text("D"));
        UiObject2 S = waitForObj(By.text("S"));
//        UiObject2 Y = waitForObj(By.text("Y"));
        D.click();
        D.click();
        S.click();
        S.click();

        press_right(2);
        press_down(1);
        UiObject2 apply = waitForObj(By.res("com.letv.tvos.appstore:id/tv_app_name").text("电视应用管家"));
        check("未进入下载应用中",apply!=null);
        apply.click();
        apply.click();
        sleepInt(2);
        UiObject2 down = waitForObj(By.res("com.letv.tvos.appstore:id/downloadTV").text("下载"));
        BySelector open = By.res("com.letv.tvos.appstore:id/downloadTV").text("打开");


        if (down != null) {
            check("安装按钮不存在", down != null);
            clickAndWaitForNewWindow(down);
            UiObject2 check2 = waitForObj(open, 30000L);
            check("网络不稳定安装未成功", check2 != null);
            check2.click();
        } else {
            UiObject2 check2 = waitForObj(open);
//            check("打开按钮不存在", check2 != null);
            check2.click();
            sleepInt(2);
        }

        sleepInt(2);
        press_back(4);
        sleepInt(4);

        addStep("进入应用管理");
        UiObject2 manage = waitForObj(By.res("com.letv.tvos.appstore:id/tv_manager").text("管理"));
        check("未进入管理", manage != null);
        manage.click();
        manage.click();
        sleepInt(2);
        addStep("进入应用卸载");
        press_right(3);
        press_center(1);
        sleepInt(3);
        addStep("卸载APP应用");
        UiObject2 TVmanager=waitForObj(By.res("com.letv.tvos.appstore:id/tv_myapp_item_appname").text("电视应用管家"));
        check("未进入管理",TVmanager!=null);
        TVmanager.click();
        TVmanager.click();
        sleepInt(2);

        press_center(1);

        UiObject2 off=waitForObj(By.text("卸载"));
        check("未进入卸载界面",off!=null);
        off.click();
        off.click();
        sleepInt(2);

        UiObject2 offok=waitForObj(By.text("卸载"));
        check("未进入卸载按钮",off!=null);
        offok.click();

        sleepInt(5);
        press_back(2);
        sleepInt(1);
//        UiObject2 aaply1=waitForObj(By.text("应用商店"));
//        check("未退出应用商店",aaply1!=null);

    }


    @Test
    @CaseName("应用商店搜索应用")
    public void testSearchApps() throws UiObjectNotFoundException, RemoteException {
        BySelector upgradeBtnS = By.text("升 级");
        BySelector storeS = By.pkg("com.letv.tvos.appstore");
        addStep("打开应用商店，选择搜索图标");
        launchApp(AppName.LeStore, IntentConstants.LeStore);
            UiObject2 upgradeBtn = phone.findObject(upgradeBtnS);
            if (upgradeBtn != null) {
                addStep("检测到升级按钮，升级乐视应用商店");
                upgradeBtn.click();
                sleepInt(60);
                press_right(4);
                sleepInt(10);
                UiObject2 install = phone.findObject(By.text("一键安装"));
                if (install != null) {
                    press_back(1);
                    sleepInt(1);
                }
            }
            UiObject2 manager = phone.findObject(storeS);
        check("未能进入LetvStore主界面", manager != null);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                SearchApps();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开应用商店，选择搜索图标，按确定键输入");
                    launchApp(AppName.LeStore, IntentConstants.LeStore);
                    UiObject2 upgradeBtn1 = phone.findObject(upgradeBtnS);
                    if (upgradeBtn1 != null) {
                        addStep("检测到升级按钮，升级乐视应用商店");
                        upgradeBtn1.click();
                        sleepInt(60);
                        press_right(4);
                        sleepInt(10);
                        UiObject2 install = phone.findObject(By.text("一键安装"));
                        if (install != null) {
                            press_back(1);
                            sleepInt(1);
                        }
                    }
                    UiObject2 manager1 = phone.findObject(storeS);
                    check("未能进入LetvStore主界面", manager1 != null);
                    addStep("搜索App");
                    UiObject2 search1=phone.findObject(By.res("com.letv.tvos.appstore:id/search_icon"));
                    check("search icon not exits",search1!=null);
                    search1.click();
                    search1.click();
                    SearchApps();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        press_back(1);
        sleepInt(2);
        UiObject2 exitok1 = waitForObj(By.text("退出"));
        exitok1.click();
        press_back(4);
    }

    public void SearchApps() throws UiObjectNotFoundException, RemoteException {

        addStep("输入应用商店存在的应用首字母，进行搜索");
        seach();
        UiObject2 search=waitForObj(By.clazz("android.widget.EditText"));
        check("search don't exists",search!=null);
        search.setText("A");
        sleepInt(3);
        UiObject2 targetApp = waitForObj(By.res(Pattern.compile("com.letv.tvos.appstore:id/recyclerview_app_search_result"))).findObjects(By.clazz("android.widget.TextView")).get(0);
        check("根据输入的字母查找失败", targetApp != null);
        sleepInt(3);
        press_back(1);
        sleepInt(3);


        addStep("输入应用商店存在的应用名称拼音，进行搜索");
        seach();
        UiObject2 search1=waitForObj(By.res("com.letv.tvos.appstore:id/searchEtx"));
        check("search don't exists",search1!=null);
        search1.setText("duoledianshizhushou");
        UiObject2 duoMiYinYue = waitForObj(By.text(Pattern.compile("多乐电视.*")));
        check("多乐电视助手没有搜到", duoMiYinYue != null);
        sleepInt(3);
        press_back(1);
        sleepInt(3);


        addStep("输入应用商店不存在的应用名称，进行搜索");
        seach();
        UiObject2 search2=waitForObj(By.res("com.letv.tvos.appstore:id/searchEtx"));
        check("search don't exists",search2!=null);
        search2.setText("ddddd");
        UiObject2 recommend = waitForObj(By.text(Pattern.compile(".*得到0个结果")));
        check("没有找到对应的结果", recommend != null);
        sleepInt(1);
        press_back(1);
        sleepInt(5);
    }

    public void seach(){
        addStep("搜索App");
        UiObject2 search=phone.findObject(By.res("com.letv.tvos.appstore:id/tv_search"));
        check("search icon not exits",search!=null);
        search.click();
        search.click();
        sleepInt(1);
    }

    @Test
    @CaseName("LetvStoreUpdate")
    public void testLetvStoreUpdate() throws UiObjectNotFoundException, RemoteException {
        addStep("打开LetvStore");
        try{
            LetvStoreUpdate();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                LetvStoreUpdate();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
        press_back(3);
    }
    public void LetvStoreUpdate() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        press_down(1);
        press_back(3);
        launchApp(AppName.LeStore, IntentConstants.LeStore);
        UiObject2 newdata=waitForObj(By.text("立即更新"));
        if(newdata!=null){
            clickAndWaitForNewWindow(newdata);
            newdata.click();
            sleepInt(60);
        }
        BySelector upgradeBtnS = By.text(Pattern.compile("升 级|Upgrade"));
        UiObject2 upgradeBtn = phone.findObject(upgradeBtnS);
        if (upgradeBtn != null) {
            addStep("检测到升级按钮，升级乐视应用商店");
            clickAndWaitForNewWindow(upgradeBtn);
            sleepInt(60);
            press_right(4);
            sleepInt(10);
        }
        UiObject2 stopintall=waitForObj(By.res("com.letv.tvos.appstore:id/downloadTV").text("暂不安装"));
        if (stopintall!=null){
            stopintall.click();
            stopintall.click();
        }
        UiObject2 install = phone.findObject(By.text("一键安装"));
        if (install != null) {
            press_back(1);
            sleepInt(1);
        }
        press_up(2);
        UiObject2 appupdate=waitForObj(By.res("com.letv.tvos.appstore:id/mv_update"));
        if (appupdate!=null){
            appupdate.click();
            appupdate.click();
            press_down(1);
            UiObject2 one_appupdate=waitForObj(By.text("一键更新"));
            for (int i =0;i<6;i++){
                clickAndWaitForNewWindow(one_appupdate);
                sleepInt(60);
            }
        }
        exitApp();
        press_home(1);
    }


    @Test
    @CaseName("LetvStoreInstallCIBN")
    public void testLetvStoreInstallCIBN() throws UiObjectNotFoundException, RemoteException {
        addStep("打开LetvStore");
        try{
            LetvStoreInstallCIBN();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                LetvStoreInstallCIBN();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
        press_back(3);
    }
    public void LetvStoreInstallCIBN() throws UiObjectNotFoundException, RemoteException {
        //进入CIBN高清影视
        gotoHomeScreen("应用");
        press_back(3);
        press_down(1);
        UiObject2 cinbapp = waitForObj(By.res(Pattern.compile("com.stv.plugin.app:id/poster_cellview_label|com.stv.plugin.app:id/cellview_label")).text("CIBN高清影视"));
        check("未进入CIBN高清影视", cinbapp != null);
        cinbapp.click();
        cinbapp.click();

        UiObject2 cibndownapp = waitForObj(By.res(Pattern.compile("com.letv.tvos.appstore:id/downloadTV")).text("下载"));
        if (cibndownapp != null) {
            check("未进入下载", cibndownapp != null);
            cibndownapp.click();
            clickAndWaitForNewWindow(cibndownapp);
            sleepInt(30);
        }
        exitApp();
        sleepInt(30);
    }


    @Test
    @CaseName("LetvStoreInstallHuaShuoTV")
    public void testLetvStoreInstallHuaShuoTV() throws UiObjectNotFoundException, RemoteException {
        addStep("打开LetvStore");
        try{
            LetvStoreInstallHuaShuoTV();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                LetvStoreInstallHuaShuoTV();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
        press_back(3);
    }
    public void LetvStoreInstallHuaShuoTV() throws UiObjectNotFoundException, RemoteException {
        //进入华数TV
        gotoHomeScreen("应用");
        press_back(3);
        press_down(1);
        UiObject2 hushuoapp = waitForObj(By.res(Pattern.compile("com.stv.plugin.app:id/poster_cellview_label|com.stv.plugin.app:id/cellview_label")).text("华数TV"));
        check("未进入华数TV", hushuoapp != null);
        hushuoapp.click();
        hushuoapp.click();
        UiObject2 hushuodownapp = waitForObj(By.res(Pattern.compile("com.letv.tvos.appstore:id/downloadTV")).text("下载"));
        if (hushuodownapp != null) {
            check("未进入下载", hushuoapp != null);
            hushuodownapp.click();
            clickAndWaitForNewWindow(hushuodownapp);
            sleepInt(60);
        }
        UiObject2 seting = waitForObj(By.text("安装"));
        if (seting != null) {
            seting.click();
            seting.click();
        }
        UiObject2 openhushuoapp = waitForObj(By.text(Pattern.compile("打开")));
        if (hushuodownapp != null) {
            check("未进入下载打开", openhushuoapp != null);
            openhushuoapp.click();
            clickAndWaitForNewWindow(openhushuoapp);
            sleepInt(60);
        }

        press_down(1);
        UiObject2 agree = waitForObj(By.res("cn.com.wasu.main:id/btn_agree").text("同意"));
        if (agree != null) {
            agree.click();
            agree.click();
        } else {
            press_center(1);
        }
        press_back(1);
        press_home(2);
        sleepInt(2);
    }





    @Test
    @CaseName("应用桌面app逐个遍历")
    public void testLetvStoreAllapp938() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {

            addStep("进入应用");
            gotoHomeScreen("应用");
            press_back(3);
            press_down(1);
            try {
                LetvStoreAllapp938();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    LetvStoreAllapp938();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
            addStep("第"+ Loop+1 +"次遍历");
            press_back(3);
        }
    }
    public void LetvStoreAllapp938() throws UiObjectNotFoundException, RemoteException {
    addStep("遍历应用桌面app");
    for (int i=1;i<=7;i++) {
        press_back(4);
        gotoHomeScreen("应用");
        press_down(i);
        press_center(1);
        sleepInt(2);
        press_home(1);
        for (int p = 1; p < 5; p++) {
            press_right(1);
            sleepInt(1);
            press_center(1);
            sleepInt(2);
            press_home(1);
            sleepInt(1);
            }
        }
    }



    @Test
    @CaseName("应用桌面app逐个遍历")
    public void testLetvStoreAllapp648() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("进入应用");
            gotoHomeScreen("应用");
            press_back(3);
            press_down(1);
            try {
                LetvStoreAllapp648();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    LetvStoreAllapp648();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
            addStep("第"+ Loop+1 +"次遍历");
            press_back(3);
        }
    }
    public void LetvStoreAllapp648() throws UiObjectNotFoundException, RemoteException {
        addStep("遍历应用桌面app");
        for (int i=1;i<7;i++) {
            press_back(4);
            gotoHomeScreen("应用");
            press_down(i);
            press_center(1);
            sleepInt(2);
            press_home(1);
            for (int p = 1; p < 5; p++) {
                press_right(1);
                sleepInt(1);
                press_center(1);
                sleepInt(2);
                press_home(1);
                sleepInt(1);
            }

        }

    }
}
