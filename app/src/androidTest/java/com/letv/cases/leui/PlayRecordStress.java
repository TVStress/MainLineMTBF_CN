package com.letv.cases.leui;

import android.content.Intent;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.After;
import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;




public class PlayRecordStress extends LetvTestCase{

    int count=0;


    BySelector storageDeviceS = By.clazz("android.widget.TextView").text("存储设备");
    BySelector intStorageDeviceS = By.clazz("android.widget.TextView").text("本机存储");
    BySelector menuBartext = By.clazz("android.widget.TextView").text("按菜单键更多操作");
    BySelector extStorageDeviceS = By.text("USB");

    @Test
    @CaseName("打开播放记录测试")
    public void testPlayRecordOpen() throws UiObjectNotFoundException, RemoteException {
        for(int Loop=0;Loop<getIntParams("Loop");Loop++){
        addStep("进入播放记录");
        gotoHomeScreen("应用");
        press_down(5);
//        UiObject2 Tool = waitForObj(By.res("com.stv.plugin.app:id/app_folder_item_label").text("工具"));
//        if(Tool!=null) {
//            Tool.click();
//            Tool.click();
//            sleepInt(1);
//            press_down(4);
//            sleepInt(1);
//            for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
//                addStep("进入播放记录");
//                UiObject2 playhoist = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("播放记录"));
//                check("未进入播放记录", playhoist != null);
//                playhoist.click();
//                sleepInt(1);
//                press_down(1);
//                addStep("退出播放记录");
//                sleepInt(1);
//                press_back(1);
//                sleepInt(1);
//            }
//            press_back(3);
//        }
//        else{
        addStep("进入播放记录");
        launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);
        addStep("退出播放记录");
        press_down(1);
        press_back(1);
        }
    }

    @Test
    @CaseName("播放记录中标签切换")
    public void testPlayRecordSwitch() throws UiObjectNotFoundException, RemoteException {
        addStep("进入播放记录");
        launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);
        for (int Loop = 0; Loop < getIntParams("Loop");Loop++) {
            addStep(".............Looper " + Loop);
            UiObject2 supertv=phone.findObject(By.text("超级影视"));
            check("未进入超级影视",supertv!=null);
            press_right(1);
            press_left(1);
            press_down(1);
            addStep("标签切换");
            UiObject2 local=phone.findObject(By.text("本地"));
            check("未进入本地",local!=null);
            press_right(1);
            press_left(1);
            press_up(1);
        }
        press_back(3);
    }

    @Test
    @CaseName("播放记录中单个删除")
    public void testDeleteOne()throws UiObjectNotFoundException, RemoteException {
        addStep("进入播放记录");
        fileManageFirst();
        launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);{
            try {
                DeleteOne();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入播放记录");
                    launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);
                    DeleteOne();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void DeleteOne()throws UiObjectNotFoundException,RemoteException{
        UiObject2 supertv=phone.findObject(By.text("超级影视"));
        check("未进入超级影视",supertv!=null);
        press_down(1);
        UiObject2 local=phone.findObject(By.text("本地"));
        check("未进入本地",local!=null);
        press_right(1);
        for(int i=0;i<10;i++) {
            press_menu(1);
            UiObject2 oneDelete = phone.findObject(By.clazz("android.widget.TextView").text(Pattern.compile("单个删除.* ")));
           if(oneDelete!=null){
                oneDelete.click();
                oneDelete.click();
               sleepInt(1);
            }
            else{
               press_center(1);
               sleepInt(1);
           }
            press_center(1);
            sleepInt(2);
        }
        press_back(3);
    }

    @Test
    @CaseName("播放记录中全部取消")
    public void testDeleteAllcalloff()throws UiObjectNotFoundException, RemoteException {
        addStep("进入播放记录");
//        fileManageFirst();
        launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);{
            try {
                DeleteAllcalloff();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入播放记录");
                    fileManageFirst();
                    launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);
                    DeleteAllcalloff();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void DeleteAllcalloff()throws UiObjectNotFoundException,RemoteException{
        UiObject2 supertv=phone.findObject(By.text("超级影视"));
        check("未进入超级影视",supertv!=null);
        press_down(1);
        addStep("标签切换");
        UiObject2 local=phone.findObject(By.text("本地"));
        check("未进入本地",local!=null);
        press_right(1);
        for(int i=0;i<10;i++) {
            sleepInt(4);
            press_right(1);
            press_menu(1);
            sleepInt(1);
            press_down(1);
            sleepInt(1);
            UiObject2 allDelete = phone.findObject(By.clazz("android.widget.TextView").text(Pattern.compile("全部删除.* ")));
            if(allDelete!=null){
                allDelete.click();
                sleepInt(1);
            }
            else{
                press_center(1);
                sleepInt(1);
            }
            press_down(1);
            press_right(1);
            sleepInt(1);
            UiObject2 deleted=waitForObj(By.text(Pattern.compile("全部删除本地播放记录.*")));
            check("未弹出全部删除本地播放记录？",deleted!=null);
//            UiObject2 calloff =waitForObj(By.text(Pattern.compile("取消.*")));
//            if(calloff!=null){
//                calloff.click();
//                calloff.click();
//                sleepInt(2);
//            }
//            else{
                press_center(1);
                sleepInt(2);
//            }
        }
        press_back(3);
    }

    @Test
    @CaseName("播放记录中续播")
    public void testPlaycontion()throws UiObjectNotFoundException, RemoteException {
        addStep("进入播放记录");
        fileManageFirst();
        launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);{
            try {
                Playcontion();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入播放记录");
                    fileManageFirst();
                    launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);
                    Playcontion();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void Playcontion()throws UiObjectNotFoundException,RemoteException{
        UiObject2 supertv=phone.findObject(By.text("超级影视"));
        check("未进入超级影视",supertv!=null);
        press_down(1);
        addStep("标签切换");
        UiObject2 local=phone.findObject(By.text("本地"));
        check("未进入本地",local!=null);
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            press_right(1);
            press_center(1);
            sleepInt(1);
            UiObject2 contion=waitForObj(By.clazz("android.widget.TextView").text("继续播放"));
            check("未进入继续播放",contion!=null);
            for (int i = 0; i < 5; i++) {
                press_center(1);
                sleepInt(4);
                press_center(1);
                sleepInt(1);
            }
            press_back(1);
            sleepInt(2);
        }

    }

    @Test
    @CaseName("播放记录中重头播放")
    public void testPlayStart()throws UiObjectNotFoundException, RemoteException {
        addStep("进入播放记录");
        fileManageFirst();
        launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);{
            try {
                PlayStart();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入播放记录");
                    fileManageFirst();
                    launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);
                    PlayStart();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void PlayStart()throws UiObjectNotFoundException,RemoteException{
        UiObject2 supertv=phone.findObject(By.text("超级影视"));
        check("未进入超级影视",supertv!=null);
        press_down(1);
        addStep("标签切换");
        UiObject2 local=phone.findObject(By.text("本地"));
        check("未进入本地",local!=null);
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            press_right(1);
            press_center(1);
            sleepInt(1);
            press_left(1);
            UiObject2 startPaly=waitForObj(By.clazz("android.widget.TextView").text("从头播放"));
            if(startPaly!=null){
                startPaly.click();
                sleepInt(1);
            }
            else{
                press_center(1);
                sleepInt(1);
            }
            for (int i = 0; i < 5; i++) {
                press_center(1);
                sleepInt(5);
                press_center(1);
                sleepInt(1);
            }
            press_back(1);

            sleepInt(2);
        }

    }


    @Test
    @CaseName("播放记录中全部删除")
    public void testDeleteAll()throws UiObjectNotFoundException, RemoteException {
        addStep("进入播放记录");
        fileManageFirst();
        launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);{
            try {
                DeleteAll();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入播放记录");
                    fileManageFirst();
                    launchApp(AppName.PlayHistory,IntentConstants.PlayHistory);
                    DeleteAll();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void DeleteAll()throws UiObjectNotFoundException,RemoteException{
        UiObject2 supertv=phone.findObject(By.text("超级影视"));
        check("未进入超级影视",supertv!=null);
        press_down(1);
        addStep("标签切换");
        UiObject2 local=phone.findObject(By.text("本地"));
        check("未进入本地",local!=null);
        press_right(1);
        for(int i=0;i<10;i++) {
            press_menu(1);
            press_down(1);
            UiObject2 allDelete = phone.findObject(By.clazz("android.widget.TextView").text(Pattern.compile("全部删除.* ")));
            if(allDelete!=null){
                allDelete.click();
                allDelete.click();
                sleepInt(1);
            }
            else{
                press_center(1);
                sleepInt(1);
            }
            press_down(1);
            sleepInt(2);
            press_left(1);
            UiObject2 calloff = waitForObj(By.text(Pattern.compile("删除.*")));
           if(calloff!=null){
               check("未进入删除",calloff!=null);
               calloff.click();
               calloff.click();
               sleepInt(2);
           }
            else{
               press_center(1);
               sleepInt(2);
           }
            press_back(4);
            fileManageFirst();
        }
        press_back(3);
    }












    @Test
    @CaseName("Delete all Record for one time")
    public void testDeleteAll1(){

        for (int Loop = 0;Loop<getIntParams("Loop");Loop++)
//        for (int Loop = 0;Loop<10;Loop++)
        {
            addStep(".....................Looper:" + Loop);
            try{
                //播放文件以获取播放记录
                playVideoforRecord();
                deleteAllRecord();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    //播放文件以获取播放记录
                    playVideoforRecord();
                    deleteAllRecord();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }



    public void playVideoforRecord(){
        addStep("进入到文件管理");
        gotoHomeScreen("应用");
        press_down(4);
        UiObject2 FileManager = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("文件管理"));
        if (FileManager == null) {
            launchApp(AppName.Filemanager, IntentConstants.Filemanager);
        }else {
            verify("没有通过RES ID 找到文件管理",FileManager != null);
            FileManager.click();
            press_center(1);
        }
        sleepInt(3);
        UiObject2 video = waitForObj(By.clazz("android.widget.TextView").text("视频"));
        clickAndWaitForNewWindow(video);
        sleepInt(1);
        addStep("播放视频文件");
        press_up(1);
        press_left(2);
        press_right(1);
        for(int i = 0 ; i < 5; i++)
        {
            press_center(1);
            sleepInt(5);
            press_back(1);
            press_right(1);
        }
        sleepInt(1);
        press_down(1);
        for(int i = 0 ; i < 5; i++)
        {
            press_center(1);
            sleepInt(5);
            press_back(1);
            press_right(1);
        }
        press_back(4);

    }

    public void deleteAllRecord(){

        addStep("进入播放记录");

        sleepInt(1);
        press_down(1);

        /*UiObject2 local = waitForObj(By.text(Pattern.compile("本地")));
        verify("Local Record not selected",local != null);
        local.click();*/
        press_center(1);

        UiObject2 noRecord = waitForObj(By.text(Pattern.compile("暂无记录")));
        if(noRecord == null)
        {
            press_right(1);
            press_menu(1);
            sleepInt(2);
            press_down(1);
            sleepInt(1);
            /*UiObject2 deleteAll = waitForObj(By.text(Pattern.compile("全部删除")));
            verify("Delete All not selected",deleteAll != null);
            press_down(1);
            press_up(1);
            deleteAll.click();
            deleteAll.click();*/
            press_center(1);
            /*确认全部删除
            UiObject2 deleteOK = waitForObj(By.res("eui.tv:id/button_horizontal_first").text("删除"));
            verify("没有找到确认删除按钮",deleteOK != null);*/
            press_center(1);
            sleepInt(3);
            /*press_left(1);
            UiObject2 delete = waitForObj(By.text(Pattern.compile("删除")));
            verify(" Delete not selected",delete != null);
            sleepInt(1);
            delete.click();
            // delete.click();

            press_center(1);*/
            noRecord = waitForObj(By.text(Pattern.compile("暂无记录")));
            verify("全部删除没有成功",noRecord != null);
        }
        press_back(2);
    }


    @Test
    @CaseName("Delete all Record then cancel")
    public void testDeleteAll_Cancel(){

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++)
//        for (int Loop = 0; Loop < 10;Loop++)
        {
            addStep(".....................Looper:" + Loop);

            try {
                //播放文件以获取播放记录
                playVideoforRecord();
                deleteAllRecord_Cancel();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    //播放文件以获取播放记录
                    playVideoforRecord();
                    deleteAllRecord_Cancel();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void deleteAllRecord_Cancel(){

        addStep("进入播放记录");

        sleepInt(1);
        press_down(1);

        /*UiObject2 local = waitForObj(By.text(Pattern.compile("本地")));
        verify("Local Record not selected",local != null);
        local.click();*/
        press_center(1);

        UiObject2 noRecord = waitForObj(By.text(Pattern.compile("暂无记录")));
        if (noRecord == null)
        {
            press_right(1);
            press_menu(1);
            sleepInt(2);
            press_down(1);
            sleepInt(1);
            press_center(1);

            /*UiObject2 deleteAll = waitForObj(By.text(Pattern.compile("全部删除")));
            verify("Delete All not selected",deleteAll != null);
            press_down(1);
            press_up(1);
            deleteAll.click();
            deleteAll.click();*/
            sleepInt(3);
            /*press_left(1);
            UiObject2 delete = waitForObj(By.text(Pattern.compile("删除")));
            verify(" Delete not selected",delete != null);
            sleepInt(1);*/
            //delete.click();
            // delete.click();
            addStep("选择取消操作");
            press_right(1);
            press_center(1);
            noRecord = waitForObj(By.text(Pattern.compile("暂无记录")));
            verify(" 全部删除没有成功",noRecord == null);
        }
        press_back(2);
    }

    @Test
    @CaseName("test play Record continue")
    public void testPlayContinue(){

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++)
//        for (int Loop = 0; Loop < 10;Loop++)
        {
            addStep(".....................Looper:" + Loop);

            try {
                //播放文件以获取播放记录
                playVideoforRecord();
                testSubPlayContinue();

            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    //播放文件以获取播放记录
                    playVideoforRecord();
                    testSubPlayContinue();

                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }


    public void testSubPlayContinue(){

        addStep("进入播放记录");


        sleepInt(1);
        press_down(1);

        sleepInt(1);
        /*UiObject2 local = waitForObj(By.text(Pattern.compile("本地")));
        verify("Local Record not selected",local != null);
        local.click();*/
        press_center(1);
        press_right(1);

        for(int i = 0;i<5;i++)
        {
            press_center(1);
            sleepInt(2);
            /*UiObject2 playContinue = waitForObj(By.text(Pattern.compile("继续播放")));
            verify("Play Continue not selected",playContinue != null);
            playContinue.click();*/
            press_center(1);
            sleepInt(6);
            press_back(1);
            sleepInt(2);
            /*local = waitForObj(By.text(Pattern.compile("本地")));
            verify("Local Record not selected",local != null);
            local.click();
            press_center(1);*/
            press_right(1);
        }
        press_back(3);
        sleepInt(1);
    }

    @Test
    @CaseName("test play from begin")
    public void testPlayFromBegin(){

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++)
//        for (int Loop = 0; Loop < 5;Loop++)
        {
            addStep(".....................Looper:" + Loop);

            try {
                //播放文件以获取播放记录
                playVideoforRecord();
                testSubPlayfromBegin();

            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    //播放文件以获取播放记录
                    playVideoforRecord();
                    testSubPlayfromBegin();

                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }


    public void testSubPlayfromBegin(){

        addStep("进入播放记录");
        sleepInt(1);
        press_down(1);
        sleepInt(1);

        /*UiObject2 local = waitForObj(By.text(Pattern.compile("本地")));
        verify("Local Record not selected",local != null);
        local.click();*/
       // press_center(1);
        press_right(1);
        sleepInt(1);
        for(int i = 0;i < 5;i++)
        {
            press_center(1);
            sleepInt(2);
            /*UiObject2 playfrombegin = waitForObj(By.text(Pattern.compile("从头播放")));
            verify("Play from begin not selected",playfrombegin != null);
            playfrombegin.click();*/
            press_left(1);
            press_center(1);
           // press_center(1);
            sleepInt(6);
            press_back(1);
            sleepInt(1);
            /*local = waitForObj(By.text(Pattern.compile("本地")));
            verify("Local Record not selected",local != null);
            local.click();
            press_center(1);*/
            press_right(1);
        }
        press_back(3);
        sleepInt(1);
    }

    public void fileManageFirst() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager,IntentConstants.Filemanager);
        addStep("进入文件管理");
        press_right(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        addStep("copy 外接USB存储中文件到本地文件夹");
        UiObject2 extStorageDevice1 = waitForObj(extStorageDeviceS);
        verify("没有找本机外部存储设备，请检查", extStorageDevice1 != null);
        extStorageDevice1.click();
        check("未进入外接存储文件", menuBartext != null);
        sleepInt(1);
        addStep("文件删除");
        sleepInt(1);
        press_right(2);
        UiObject2 copeAAAfile = phone.findObject(By.text("AAA"));
        check("未进入AAA文件夹", copeAAAfile != null);
        copeAAAfile.click();
        copeAAAfile.click();
        sleepInt(1);
        check("未进入外接存储文件", menuBartext != null);
        UiObject2 video= waitForObj(By.text("video"));
        video.click();
        video.click();
        press_menu(1);
        UiObject2 screen= waitForObj(By.text("筛选"));
        check("未进入筛选栏",screen!=null);
        screen.click();
        screen.click();
        UiObject2 all= waitForObj(By.text("全部"));
        check("未进入全部栏",screen!=null);
        all.click();
        all.click();
        press_left(2);
        for(int i=0;i<10;i++){
            press_center(1);
            sleepInt(8);
            press_back(1);
            sleepInt(1);
            press_right(1);
            sleepInt(1);
        }

    }
}
