package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import org.junit.validator.PublicClassValidator;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
/**
 * Created by wangyaxiu on 2016/12/15.
 */

public class FileManageStress extends LetvTestCase {
    int count = 0;

    BySelector storageDeviceS = By.clazz("android.widget.TextView").text("存储设备");
    BySelector shareDeviceS = By.clazz("android.widget.TextView").text("共享设备");
    BySelector fileMangetext = By.clazz("android.widget.TextView").text("文件管理");
    BySelector menuBartext = By.clazz("android.widget.TextView").text("按菜单键更多操作");
    BySelector extStorageDeviceS = By.text("USB");
    BySelector intStorageDeviceS = By.clazz("android.widget.TextView").text("本机存储");
    BySelector AAAS = By.clazz("android.widget.TextView").text(Pattern.compile("AAA.*|letvdownload"));
    BySelector delS = By.text("删除");
    BySelector confirmDelS = By.text("确定");
    BySelector fileTitleS = By.res("com.stv.filemanager:id/text_title");
    BySelector bigToSmallS = By.text(Pattern.compile(".*Z到A"));
    BySelector contentS = By.res("android:id/content");

    @Test
    @CaseName("压测本地存储aaa复制到外接存储文件")
    public void testLocalCopyaaaFolder() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        addStep("进入文件管理");
        press_right(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        addStep("copy 外接USB存储中文件到本地文件夹");
        UiObject2 extStorageDevice1 = waitForObj(intStorageDeviceS);
        verify("没有找本机外部存储设备，请检查", extStorageDevice1 != null);
        clickAndWaitForNewWindow(extStorageDevice1);
        check("未进入外接存储文件", menuBartext != null);
        sleepInt(1);
        addStep("文件删除");
        press_menu(1);
        UiObject2 view = waitForObj(By.text(Pattern.compile("视图.*")));
        view.click();
        view.click();
        sleep(1);
        UiObject2 grid = waitForObj(By.text(Pattern.compile("网格.*")));
        grid.click();
        grid.click();
        sleepInt(1);
        press_right(2);
        UiObject2 copeAAAfile = phone.findObject(By.text("AAA"));
        check("未进入AAA文件夹", copeAAAfile != null);
        copeAAAfile.click();
        copeAAAfile.click();
        sleepInt(2);
        press_back(1);
        sleepInt(1);
        check("未进入外接存储文件", menuBartext != null);
        try {
            localcopyAAAFolder();
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                addStep("进入文件管理器，打开存储设备");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                fileManageFirst1();
                localcopyAAAFolder();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
        press_back(3);
    }

    public void localcopyaaaFolder() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < 1; Loop++) {
            press_menu(1);
            UiObject2 content = waitForObj(contentS);
            check("menu菜单没有弹出", content != null);
            for (int a = 0; a < 5; a++) {
                UiObject2 ran1 = phone.findObject(By.text("复制"));
                if (ran1.isSelected()) {
                    break;
                } else {
                    press_down(1);
                }
            }
            press_center(1);
            sleepInt(1);
            press_right(1);
            UiObject2 confirm = waitForObj(extStorageDeviceS);
            check("未进入选择设备界面", confirm != null);
            confirm.click();
            sleepInt(1);
            UiObject2 samefile = waitForObj(By.text("复制并覆盖"));
            if (samefile != null) {
                check("未弹出复制并覆盖对话框", samefile != null);
                clickAndWaitForNewWindow(samefile);
            }
            UiObject2 waittime = waitForObj(By.clazz("android.widget.Button").text(" 隐藏窗口"));
            if (waittime != null) {
                clickAndWaitForNewWindow(waittime);
            }
            sleepInt(10);
            check("未进入外接存储文件", menuBartext != null);
        }
    }


    @Test
    @CaseName("压测本地存储复制到外接存储文件")
    public void testLocalCopyAAAFolder() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        fileManageFirst1();
        try {
            localcopyAAAFolder();
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                addStep("进入文件管理器，打开存储设备");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                fileManageFirst1();
                localcopyAAAFolder();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
        press_back(3);
    }

    public void localcopyAAAFolder() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            press_right(1);
            press_menu(1);
            UiObject2 content = waitForObj(contentS);
            check("menu菜单没有弹出", content != null);
            for (int a = 0; a < 5; a++) {
                UiObject2 ran1 = phone.findObject(By.text("复制"));
                if (ran1.isSelected()) {
                    break;
                } else {
                    press_down(1);
                }
            }
            press_center(1);
            sleepInt(1);
            press_right(1);
            UiObject2 confirm = waitForObj(extStorageDeviceS);
            check("未进入选择设备界面", confirm != null);
            confirm.click();
            confirm.click();
            sleepInt(1);
            UiObject2 samefile = waitForObj(By.text("复制不覆盖"));
            if (samefile != null) {
                check("未弹出复制并覆盖对话框", samefile != null);
                clickAndWaitForNewWindow(samefile);
            }
            UiObject2 waittime = waitForObj(By.clazz("android.widget.Button").text(" 隐藏窗口"));
            if (waittime!=null) {
                clickAndWaitForNewWindow(waittime);
            }
            check("未进入外接存储文件", menuBartext != null);
        }
    }

    public void fileManageFirst1() throws UiObjectNotFoundException, RemoteException {
        addStep("进入文件管理");
        press_right(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        addStep("copy 外接USB存储中文件到本地文件夹");
        UiObject2 extStorageDevice1 = waitForObj(intStorageDeviceS);
        verify("没有找本机外部存储设备，请检查", extStorageDevice1 != null);
        extStorageDevice1.click();
        check("未进入外接存储文件", menuBartext != null);
        sleepInt(1);
        addStep("文件删除");
        press_menu(1);
        UiObject2 view = waitForObj(By.text(Pattern.compile("视图.*")));
        view.click();
        view.click();
        sleep(1);
        UiObject2 grid = waitForObj(By.text(Pattern.compile("网格.*")));
        grid.click();
        grid.click();
        sleepInt(1);
        press_right(2);
        UiObject2 copeAAAfile = phone.findObject(By.text("AAA"));
        check("未进入AAA文件夹", copeAAAfile != null);
        copeAAAfile.click();
        sleepInt(2);
        press_right(2);
        UiObject2 copeFolder = phone.findObject(By.text("folder"));
        check("未进入folder文件夹", copeFolder!= null);
        copeFolder.click();
        sleepInt(2);
        press_right(2);
        UiObject2 cope1 = phone.findObject(By.text(Pattern.compile("aa*")));
        check("未进入aa文件夹", cope1!= null);
        cope1.click();
        sleepInt(2);
        press_back(1);
        sleepInt(1);
        check("未进入外接存储文件", menuBartext != null);
        press_right(1);
    }


    @Test
    @CaseName("压测外接存储文件复制到本地")
    public void testCopyAAAFolder() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        fileManageFirst();
        addStep("文件删除");
        sleepInt(1);
        press_right(2);

        UiObject2 copeAAAfile = phone.findObject(By.text("AAA"));
        check("未进入AAA文件夹", copeAAAfile != null);
        copeAAAfile.click();
        copeAAAfile.click();
        press_right(2);
        UiObject2 copeApkfile = phone.findObject(By.text("apk"));
        check("未进入AAA文件夹", copeApkfile != null);
        copeApkfile.click();
        copeApkfile.click();
        press_back(1);
        try {
            copyAAAFolder();
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                addStep("进入文件管理器，打开存储设备");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                fileManageFirst();
                copyAAAFolder();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
        press_back(3);
    }

    public void copyAAAFolder() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            press_right(1);
            press_menu(1);
            UiObject2 content = waitForObj(contentS);
            check("menu菜单没有弹出", content != null);
            for (int a = 0; a < 5; a++) {
                UiObject2 ran1 = phone.findObject(By.text("复制"));
                if (ran1.isSelected()) {
                    break;
                } else {
                    press_down(1);
                }
            }
            press_center(1);
            sleepInt(1);
            press_right(1);
            UiObject2 confirm = waitForObj(By.res("com.stv.filemanager:id/text_name1").text("本机存储"));
            check("未进入选择设备界面", confirm != null);
            confirm.click();
            confirm.click();
            sleepInt(1);
            UiObject2 samefile = waitForObj(By.clazz("android.widget.Button").text("复制并覆盖"));
            if (samefile != null) {
                check("未弹出复制并覆盖对话框", samefile != null);
                samefile.click();
            }
            sleepInt(6);
        }

    }


    @Test
    @CaseName("压测外接存储文件删除")
    public void testDelAAAFolder() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        fileManageFirst();
        try {
            delAAAFolder();
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                addStep("进入文件管理器，打开存储设备");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                fileManageFirst();
                delAAAFolder();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
        press_back(3);
    }

    public void delAAAFolder() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("文件删除");
            sleepInt(1);
            press_right(1);
            UiObject2 aafile=waitForObj(By.text(Pattern.compile("aa.*")));
            check("未找到aa文件",aafile!=null);
            aafile.click();
            aafile.click();
            press_back(1);
            sleepInt(1);
            press_right(1);
            press_menu(1);
            UiObject2 content = waitForObj(contentS);
            check("menu菜单没有弹出", content != null);
            for (int a = 0; a < 5; a++) {
                UiObject2 ran1 = phone.findObject(By.text("删除"));
                if (ran1.isSelected()) {
                    break;
                } else {
                    press_down(1);
                }
            }
            sleepInt(2);
            press_center(1);
            sleepInt(1);
            UiObject2 confirmDel2 = waitForObj(By.clazz("android.widget.Button").text("确定"));
            check("未弹出删除框", confirmDel2 != null);
            confirmDel2.click();
            sleepInt(1);
            check("没有找到按菜单键更多操作", menuBartext != null);
        }
    }


    @Test
    @CaseName("test switch filter in  external storage")
    public void testSwitchAAAFilter() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            addStep("进入文件管理");

        /*gotoHomeScreen("应用");
        press_down(4);
        UiObject2 filemanager = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("文件管理"));
        if(filemanager !=null){
            filemanager.click();
            press_center(1);
            sleepInt(3);
        }else {
            launchApp(AppName.Filemanager, PkgName.Filemanager);
            sleepInt(2);
        }*/
            UiObject2 storageDevice = waitForObj(storageDeviceS);
            clickAndWaitForNewWindow(storageDevice);
            sleepInt(1);
            addStep("copy 外接USB存储中文件到本地文件夹");
            UiObject2 extStorageDevice = waitForObj(extStorageDeviceS);
            verify("没有找到usb外部存储设备，请检查", extStorageDevice != null);
            extStorageDevice.click();
            sleepInt(1);
            for (int i = 0; i < 3; i++) {
                UiObject2 AAA2 = phone.findObject(AAAS);
                if (AAA2 == null) {
                    press_down(2);
                } else break;
            }
            sleepInt(1);

            addStep("进入到菜单选项");
            press_menu(1);
            UiObject2 filter = waitForObj(By.text("筛选"));
            check("filter item is not exist", filter != null);
            //  filter.click();


            press_center(1);
            UiObject2 total = waitForObj(By.text("全部"));
            check("total item is not exist", total != null);
            press_down(2);
            press_up(1);
            sleepInt(1);
            int m = GenerateRandom(5);
            int n = GenerateRandom(5);
            if (Loop / 2 == 0) {
                press_up(m);
                press_down(n);
            } else {
                press_down(n);
                press_up(m);
            }
            press_center(1);
        }

        press_back(3);
    }


    @Test
    @CaseName("压测外接存储文件筛选")
    public void testFiltrate() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        fileManageFirst();
        try {
            Filtrate();
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                addStep("进入相册");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                Filtrate();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }

        }
        press_back(3);

    }

    public void Filtrate() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            String[] galleryt = {"全部", "视频", "图片", "音频", "安装包"};
            for (int i = 0; i < galleryt.length; i++) {
                addStep("文件按大小、时间近远、名称字母排序");
                sleepInt(1);
                press_menu(1);
                UiObject2 content = waitForObj(contentS);
                verify("menu菜单没有弹出", content != null);
                for (int a = 0; a < 5; a++) {
                    UiObject2 ran1 = phone.findObject(By.text("筛选"));
                    if (ran1.isSelected()) {
                        break;
                    } else {
                        press_down(1);
                    }
                }
                press_center(1);
                sleepInt(1);
                press_down(i);
                UiObject2 table = waitForObj(By.text(Pattern.compile(galleryt[i])));
                check("未进入文件筛选循环", table != null);
                table.click();
                table.click();
                sleepInt(1);
            }
        }
    }


    @Test
    @CaseName("压测外接存储文件排序")
    public void testSorting() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        fileManageFirst();
        try {
            Sorting();
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                addStep("进入相册");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                Sorting();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }

        }
        press_back(3);

    }

    public void Sorting() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            String[] galleryt = {"时间近到远", "时间远到近", "小到大", "大到小", "名称A到Z", "名称Z到A"};
            for (int i = 0; i < galleryt.length; i++) {
                addStep("文件按大小、时间近远、名称字母排序");
                sleepInt(1);
                press_menu(1);
                UiObject2 content = waitForObj(contentS);
                verify("menu菜单没有弹出", content != null);
                for (int a = 0; a < 5; a++) {
                    UiObject2 ran1 = phone.findObject(By.text("排序"));
                    if (ran1.isSelected()) {
                        break;
                    } else {
                        press_down(1);
                    }
                }
                press_center(1);
                sleepInt(1);
                press_down(i);
                UiObject2 table = waitForObj(By.text(Pattern.compile(galleryt[i])));
                check("未进入相册排序循环", table != null);
                table.click();
                table.click();
                sleepInt(1);
            }
        }
    }

    @Test
    @CaseName("压测外接存储文件视图")
    public void testSwitchView() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        fileManageFirst();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            addStep("进入到菜单选项");
            press_menu(1);
            press_down(4);
            UiObject2 view = waitForObj(By.res("eui.tv:id/list_item_1_title").text(Pattern.compile("视图")));
            verify("没有找到视图选项", view != null);
            view.click();
            sleepInt(1);
            if (Loop % 2 == 0) {
                UiObject2 list1 = waitForObj(By.text("列表"));
                check("没有找到列表选项", list1 != null);
                list1.click();
                list1.click();
                sleepInt(2);
            } else {
                UiObject2 grid1 = waitForObj(By.text("网格"));
                check("没有找到网格选项", grid1 != null);
                grid1.click();
                grid1.click();
                sleepInt(2);
            }
        }
        press_back(3);
    }

    @Test
    @CaseName("压测外接存储文件隐藏")
    public void testSwitchHidden() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        fileManageFirst();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            addStep("进入到菜单选项");
            press_menu(1);
            press_down(4);
            UiObject2 hidden = waitForObj(By.res("eui.tv:id/list_item_1_title").text(Pattern.compile("隐藏")));
            check("没有找到隐藏选项", hidden != null);
            hidden.click();
            sleepInt(1);

            if (Loop % 2 == 0) {
                UiObject2 hid = waitForObj(By.res("eui.tv:id/list_item_2_title").text("隐藏"));
                check("hid item is not exist", hid != null);
                hid.click();
                hid.click();
                sleepInt(2);
            } else {
                UiObject2 appear = waitForObj(By.text("显示"));
                check("grid item is not exist", appear != null);
                press_down(1);
                appear.click();
                appear.click();
                sleepInt(2);
            }
        }
        press_back(3);
    }

    public void fileManageFirst() throws UiObjectNotFoundException, RemoteException {
        addStep("进入文件管理");
        press_right(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        addStep("copy 外接USB存储中文件到本地文件夹");
        UiObject2 extStorageDevice = waitForObj(extStorageDeviceS);
        verify("没有找到usb外部存储设备，请检查", extStorageDevice != null);
        extStorageDevice.click();
        check("未进入外接存储文件", menuBartext != null);
        sleepInt(1);
        press_menu(1);
        UiObject2 view = waitForObj(By.text(Pattern.compile("视图.*")));
        view.click();
        view.click();
        sleep(1);
        UiObject2 grid = waitForObj(By.text(Pattern.compile("网格.*")));
        grid.click();
        grid.click();
        sleepInt(1);
    }


    @Test
    @CaseName("压测反复打开外接存储设备")
    public void testEntryExternalDevice() throws UiObjectNotFoundException, RemoteException {
        addStep("进入到应用桌面");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("..........................Looper " + Loop);
            addStep("进入文件管理");
            UiObject2 storageDevice = waitForObj(storageDeviceS);
            check("未进入存储设备", storageDevice != null);
            clickAndWaitForNewWindow(storageDevice);
            sleepInt(1);
            UiObject2 extStorageDevice = waitForObj(extStorageDeviceS);
            verify("没有找到usb外部存储设备，请检查", extStorageDevice != null);
            extStorageDevice.click();
            press_right(3);
            addStep("退出外部设备，文件管理");
            press_back(1);
            check("未退出到存储设备界面", storageDeviceS != null);
            press_back(1);
            check("未退出到存储设备界面", fileMangetext != null);
        }
    }


    @Test
    @CaseName("压测反复打开文件管理")
    public void testOpenFileManager() throws UiObjectNotFoundException, RemoteException {
        addStep("进入到应用桌面");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("......Loop" + Loop);
            try{
                OpenFileManager();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    launchApp(AppName.Filemanager, PkgName.Filemanager);
                    OpenFileManager();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }

        }
    }
    public void OpenFileManager()throws UiObjectNotFoundException,RemoteException{
        addStep("进入到文件管理");
        check("未进入文件管理", fileMangetext != null);
        press_right(5);
        addStep("退出文件管理");
        press_back(3);
        UiObject2 filemanage = phone.findObject(By.text(Pattern.compile("应用")));
        check("未退出文件管理", filemanage != null);
        addStep("进入到应用桌面");
        gotoHomeScreen("应用");
        press_down(3);
        press_right(3);
        UiObject2 allappfile = waitForObj(By.text("全部应用"));
        if(allappfile!=null){
            allappfile.click();
            press_down(4);
            UiObject2 filemanager1 = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text(Pattern.compile("文件管理")));
            check("未进入文件管理", filemanager1 != null);
            filemanager1.click();
            filemanager1.click();
        }
        else {
            launchApp(AppName.Filemanager, PkgName.Filemanager);
        }

    }


    @Test
    @CaseName("文件管理中反复打开SMB远程存储")
    public void testOpenSMB() throws UiObjectNotFoundException, RemoteException {
        addStep("进入文件管理");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        press_right(5);
        addStep("进入共享设备");
        UiObject2 shareDevice = waitForObj(shareDeviceS);
        clickAndWaitForNewWindow(shareDevice);
        sleepInt(2);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("-----------------Looper:" + Loop);
            // 检查是否SMB   10.57.183.215
            UiObject2 SMB = phone.findObject(By.res("com.stv.filemanager:id/text_title").text("SMB"));
            if (SMB != null) {
                UiObject2 testCenterServer = phone.findObject(By.res("com.stv.filemanager:id/text_name").text("10.57.183.215"));
                sleepInt(1);


                if (testCenterServer == null) {
                    //连接SMB
                    UiObject2 addDevice = phone.findObject(By.res("com.stv.filemanager:id/text_name").text("添加设备"));
                    verify("没有找到添加设备按钮", addDevice != null);
                    clickAndWaitForNewWindow(addDevice);
                    sleepInt(1);
                    UiObject2 findSMB = waitForObj(By.res("com.stv.filemanager:id/text_smb").text("SMB"));
                    verify("没有找到SMB选项", findSMB != null);
                    findSMB.click();
                    press_center(1);


                    connectSMB();


                    press_back(1);
                }
            } else {
                //连接SMB
                UiObject2 addDevice = phone.findObject(By.res("com.stv.filemanager:id/text_name").text("添加设备"));
                verify("没有找到添加设备按钮", addDevice != null);
                clickAndWaitForNewWindow(addDevice);
                press_center(1);
                connectSMB();
                press_back(1);
            }

            sleepInt(2);
            UiObject2 testCenterServer = phone.findObject(By.res("com.stv.filemanager:id/text_name").text("10.57.183.215"));
            verify("SMB没有连接上", testCenterServer != null);

            testCenterServer.click();
            testCenterServer.click();
            sleepInt(10);
            press_center(2);
            sleepInt(5);

            addStep("返回共享设备界面");
            press_back(2);
            sleepInt(5);
        }
    }

    private void connectSMB() {

        sleepInt(2);
        UiObject2 SMB = phone.findObject(By.text("SMB"));
        verify("没有找到SMB", SMB != null);
        SMB.click();

        sleepInt(1);
        UiObject2 IP = phone.findObject(By.text(Pattern.compile("IP 地址"))).getParent().findObject(By.clazz("android.widget.EditText"));
        verify("can't find ip address.", IP != null);
        IP.setText(getStrParams("IP"));
//        IP.setText("10.58.81.227");

        UiObject2 newSMB = phone.findObject(By.text(Pattern.compile("确定")));
        verify("can't find ok button.", newSMB != null);
        newSMB.click();
        newSMB.click();
        sleepInt(2);
        UiObject2 userName = phone.findObject(By.text(Pattern.compile("用户名"))).getParent().findObject(By.clazz("android.widget.EditText"));
        verify("can't find ip address.", userName != null);
        userName.setText(getStrParams("SMBUSERNAME"));
        //userName.setText("test");
        sleepInt(2);
        press_down(1);
        UiObject2 passwd = phone.findObject(By.text(Pattern.compile("密  .*码"))).getParent().findObject(By.clazz("android.widget.EditText"));
        verify("can't find passwd.", passwd != null);
        passwd.setText(getStrParams("SMBPASSWORD"));
        // passwd.setText("123.com");
        sleepInt(1);
        UiObject2 loginNow1 = phone.findObject(By.clazz("android.widget.Button").text("确定"));
        verify("can't find loginNow.", loginNow1 != null);
        loginNow1.click();
        loginNow1.click();
        sleepInt(6);
    }


    @Test
    @CaseName("文件管理中反复打开FTP远程存储")
    public void testOpenFTP() throws UiObjectNotFoundException, RemoteException {
        addStep("进入到应用桌面");
        addStep("进入共享设备");
        UiObject2 shareDevice = waitForObj(shareDeviceS);
        clickAndWaitForNewWindow(shareDevice);
        sleepInt(2);

        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("-----------------Looper:" + Loop);

            // 检查是否已连接FTP
            UiObject2 ftp = phone.findObject(By.res("com.stv.filemanager:id/text_title").text("FTP"));
            if (ftp != null) {
                sleep(5);
                ftp.click();
                //  ftp.click();

                sleep(5);
                press_center(1);
                sleepInt(5);
                addStep("返回共享设备界面");
                press_back(1);
                sleepInt(30);
            } else {
                //连接FTP
                UiObject2 addDevice = phone.findObject(By.res("com.stv.filemanager:id/text_name").text("添加设备"));
                verify("没有找到添加设备按钮", addDevice != null);
                clickAndWaitForNewWindow(addDevice);
                ConnectFTP();
                press_back(1);

            }
        }

    }

    private void ConnectFTP() {
        sleepInt(2);
        UiObject2 FTP1 = phone.findObject(By.text("FTP"));
        verify("没有找到FTP", FTP1 != null);
        FTP1.click();

        sleepInt(1);
        UiObject2 IP = phone.findObject(By.text(Pattern.compile("ftp://"))).getParent().findObject(By.clazz("android.widget.EditText"));
        verify("can't find ip address.", IP != null);
        IP.setText(getStrParams("FTP"));
        //IP.setText("192.168.1.3:3721");
        UiObject2 newFTP = phone.findObject(By.text(Pattern.compile("确定")));
        verify("can't find ok button.", newFTP != null);
        newFTP.click();
        newFTP.click();
        sleepInt(10);
    }


    @Test
    @CaseName("文件管理中反复打开DLNA远程存储")
    public void testOpenDLNA() throws UiObjectNotFoundException, RemoteException {

        addStep("进入到应用桌面");
//        LaunchFileManager();
        /*gotoHomeScreen("应用");
        addStep("进入文件管理");
        UiObject2 fileManager = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("文件管理"));
        if (fileManager == null){
            for(int i = 0 ; i < 4; i++)
            {
                press_down(3);
                fileManager = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text(Pattern.compile("文件管理")));
                if (fileManager != null) { break;}
            }
        }
        verify("没有找到文件管理应用",fileManager != null);
        fileManager.click();
        fileManager.click();
        sleepInt(1);*/
        addStep("进入共享设备");
        UiObject2 shareDevice = waitForObj(shareDeviceS);
        clickAndWaitForNewWindow(shareDevice);
        sleepInt(3);

        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("..........................Looper " + Loop);

            //连接DLNA
            UiObject2 addDevice = phone.findObject(By.res("com.stv.filemanager:id/text_name").text("添加设备"));
            verify("没有找到添加设备按钮", addDevice != null);
            clickAndWaitForNewWindow(addDevice);

            sleepInt(2);
            UiObject2 DLNA = phone.findObject(By.text("DLNA"));
            verify("没有找到DLNA", DLNA != null);
            DLNA.click();
            sleepInt(1);

            UiObject2 device = phone.findObject(By.res("com.stv.filemanager:id/layout_1"));
            verify("没有连接任何的DLNA设备", device != null);

            device.click();
            press_center(1);

            addStep("退回到共享设备");
            press_back(3);
            sleepInt(10);

        }
    }


    @Test
    @CaseName("文件管理中共享设备SMB添加")
    public void addSMBset_login() throws UiObjectNotFoundException, RemoteException {
        for(int i=0;i<2;i++) {
            addStep("进入到应用桌面");
            launchApp(AppName.Filemanager, PkgName.Filemanager);
            press_right(5);
            addStep("进入共享设备");
            UiObject2 shareDevice = waitForObj(shareDeviceS);
            clickAndWaitForNewWindow(shareDevice);
            sleepInt(2);
            UiObject2 addshare = waitForObj(By.res("com.stv.filemanager:id/text_name").text("添加设备"));
            check("未进入添加设备", addshare != null);
            addshare.click();
            sleepInt(1);
            UiObject2 addSMB = waitForObj(By.res("com.stv.filemanager:id/text_smb").text("SMB"));
            check("未进入添加设备", addSMB != null);
            addSMB.click();
            sleepInt(1);
            UiObject2 addIP = waitForObj(By.text("IP 地址"));
            check("未找到iP界面", addIP != null);
            UiObject2 addip = waitForObj(By.clazz("android.widget.EditText"));
            addip.click();
            press_center(1);
            sleepInt(1);
            UiObject2 IP = waitForObj(By.text(Pattern.compile("IP 地址"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find ip address.", IP != null);
//        IP.setText(getStrParams("IP"));
            IP.setText("10.58.81.227");
            sleepInt(2);
//            弹出输入窗口需要返回
//            press_back(1);
            press_down(2);
            UiObject2 ok = waitForObj(By.text("确定"));
            ok.click();
            sleepInt(2);


            UiObject2 worldtext = waitForObj(By.text(Pattern.compile("请输入SMB设备.*")));
            if (worldtext != null) {
                UiObject2 userName = waitForObj(By.text(Pattern.compile("用户名.*"))).getParent().findObject(By.clazz("android.widget.EditText"));
                check("can't find userName.", userName != null);
//        userName.setText(getStrParams("USERNAME"));
                userName.setText("pc7");
                sleepInt(2);
                press_down(1);
                UiObject2 passwd1 = waitForObj(By.text("密    码")).getParent().findObject(By.clazz("android.widget.EditText"));
                check("can't find passwd.", passwd1 != null);
                sleepInt(2);
                passwd1.setText("123456");
                sleepInt(2);
                press_center(1);

                UiObject2 ok1 = waitForObj(By.text("确定"));
                if (ok1 != null) {
                    ok1.click();
                    sleepInt(2);
                } else {
                    press_down(2);
                    press_center(1);
                    sleepInt(2);
                }
            } else {
                press_center(1);
            }
        }


    }



    @Test
    @CaseName("文件管理中共享设备FTP添加")
    public void addftpset_login() throws UiObjectNotFoundException, RemoteException {
        for(int i=0;i<2;i++) {
            addStep("进入到应用桌面");
            launchApp(AppName.Filemanager, PkgName.Filemanager);
            press_right(2);
            addStep("进入共享设备");
            UiObject2 shareDevice = waitForObj(shareDeviceS);
            clickAndWaitForNewWindow(shareDevice);
            sleepInt(2);
            UiObject2 addshare = waitForObj(By.res("com.stv.filemanager:id/text_name").text("添加设备"));
            check("未进入添加设备", addshare != null);
            addshare.click();
            sleepInt(1);
            press_right(2);
            UiObject2 addSMB = waitForObj(By.res("com.stv.filemanager:id/text_ftp").text("FTP"));
            check("未进入添加设备", addSMB != null);
            addSMB.click();
            sleepInt(1);
            UiObject2 addIP = waitForObj(By.text("ftp://"));
            check("未找到iP界面", addIP != null);
            UiObject2 addip = waitForObj(By.clazz("android.widget.EditText"));
            addip.click();
            press_center(1);
            sleepInt(1);
            UiObject2 ftp = waitForObj(By.text(Pattern.compile("ftp://"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find ip address.", ftp != null);
//        IP.setText(getStrParams("IP"));
            ftp.setText("10.58.81.227");
            sleepInt(2);
            //弹出输入窗口需要返回
//            press_back(1);
            press_down(1);
            UiObject2 ok = waitForObj(By.text("确定"));
            ok.click();
            sleepInt(2);

            UiObject2 ftpuser = waitForObj(By.text(Pattern.compile("用户名.*")));
            check("未输入用户名", ftpuser != null);
            UiObject2 ftpuser_input = waitForObj(By.clazz("android.widget.EditText"));
            check("can't find userName.", ftpuser_input != null);
            ftpuser_input.setText("pc6");
            sleepInt(2);
            press_down(1);
            sleepInt(2);

            UiObject2 ftppassword_input = waitForObj(By.text("密    码")).getParent().findObject(By.clazz("android.widget.EditText"));
            check("can't find userName.", ftppassword_input != null);
            ftppassword_input.setText("admin");
            sleepInt(1);

            UiObject2 ok1 = waitForObj(By.text("确定"));
            if (ok1 != null) {
                ok1.click();
                ok1.click();
                sleepInt(2);
            } else {
                press_down(2);
                press_center(1);
                sleepInt(2);
            }
        }
    }


    @Test
    @CaseName("文件管理中反复进入SMB")
    public void testSMBenter() throws UiObjectNotFoundException, RemoteException {
        addStep("进入到应用桌面");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        press_right(2);
        addStep("进入共享设备");
        UiObject2 shareDevice = waitForObj(shareDeviceS);
        clickAndWaitForNewWindow(shareDevice);
        sleepInt(1);
        press_right(4);
        sleepInt(2);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {

            UiObject2 Smbenter1 = waitForObj(By.text(Pattern.compile("SMB")));
            UiObject2 Smbenter2 = waitForObj(By.text("10.58.81.227"));

            check("未进入SMB", Smbenter1 != null || Smbenter2 != null);
            {
                Smbenter1.click();
                Smbenter2.click();
                sleepInt(5);
                press_right(2);
                check("未进入SMB服务器", menuBartext != null);
            }
            press_back(1);
            sleepInt(2);
        }
    }


    @Test
    @CaseName("文件管理中反复进入FTP")
    public void testFTPenter() throws UiObjectNotFoundException, RemoteException {
        addStep("进入到应用桌面");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        press_right(2);
        addStep("进入共享设备");
        UiObject2 shareDevice = waitForObj(shareDeviceS);
        clickAndWaitForNewWindow(shareDevice);
        sleepInt(1);
        press_right(2);
        sleepInt(2);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            UiObject2 Ftpenter1 = waitForObj(By.text(Pattern.compile("FTP")));
            UiObject2 Ftpenter2 = waitForObj(By.text(Pattern.compile("10.58.81.227(pc6)")));
            check("未进入FTP", Ftpenter1 != null || Ftpenter2 != null);
            Ftpenter1.click();
            Ftpenter1.click();
            sleepInt(5);
            press_right(2);
            check("未进入FTP服务器", menuBartext != null);
            press_back(1);
            sleepInt(1);
        }
    }
}