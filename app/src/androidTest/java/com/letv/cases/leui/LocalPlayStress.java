package com.letv.cases.leui;

import android.content.pm.LauncherApps;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import junit.framework.Assert;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Created by wangyaxiu on 2017/1/13.
 */

public class LocalPlayStress extends LetvTestCase {

    int count = 0;

    BySelector storageDeviceS = By.clazz("android.widget.TextView").text("存储设备");
    BySelector extStorageDeviceS = By.text("USB");

    @Test
    @CaseName("音乐播放器反复进退")
    public void testMusicPlayer() throws UiObjectNotFoundException, RemoteException {
        LaunchFileManager();
        sleepInt(1);
        try {
            MusicPlayer();
        } catch (Exception e) {
            try {
                failCount(count++, getIntParams("Loop"), e.getMessage());
                MusicPlayer();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

        press_back(4);
    }

    public void MusicPlayer() throws UiObjectNotFoundException, RemoteException {
        addStep("反复进入和退出音乐播放");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("........Loop" + Loop);
            UiObject2 audio = waitForObj(By.text(Pattern.compile("music6")));
            check("未找到了audio文件播放", audio != null);
            addStep("进入音乐播放");
            audio.click();
            sleepInt(10);
            press_back(1);
            addStep("退出音乐播放");
            sleepInt(5);
            if (audio == null) {
                addStep("没有找到相关音乐");
                press_down(4);
            }
        }

    }


    @Test
    @CaseName("音乐播放器暂停播放切换")
    public void testMusicPausePlayer() throws UiObjectNotFoundException, RemoteException {
        LaunchFileManager();
        sleepInt(2);
        UiObject2 audio = waitForObj(By.text(Pattern.compile("music9")));
        check("未找到了audio文件播放", audio != null);
        audio.click();
        try {
            MusicPausePlayer();
        } catch (Exception e) {
            try {
                failCount(count++, getIntParams("Loop"), e.getMessage());
                MusicPausePlayer();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
        press_back(4);
    }

    public void MusicPausePlayer() throws UiObjectNotFoundException, RemoteException {
        addStep("反复暂停播放切换");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("..........Loop" + (Loop + 1));
            sleepInt(10);
            addStep("播发暂停");
            press_center(1);
            sleepInt(10);
            addStep("继续播放");
            press_center(1);
            sleepInt(10);
        }
    }


    @Test
    @CaseName("音乐播放器左右键切换")
    public void testMusicLeftRightPlayer() throws UiObjectNotFoundException, RemoteException {
        LaunchFileManager();
        addStep("反复左右键的切换");
        UiObject2 audio = waitForObj(By.text(Pattern.compile("music4")));
        check("找到了audio文件播放", audio != null);
        audio.click();
        sleepInt(10);
        try {
            LeftRightPlayer();
        } catch (Exception e) {
            try {
                failCount(count++, getIntParams("Loop"), e.getMessage());
                LeftRightPlayer();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
        press_back(4);
    }

    public void LeftRightPlayer() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("..........Looper " + Loop);
            addStep("选择音频进入播放");
            press_left(1);
            addStep("向左切换播放键");
            sleepInt(10);
            press_right(1);
            addStep("向右切换播放键");
            sleepInt(10);
        }
    }


    @Test
    @CaseName("音乐播放器列表进行播放切换")
    public void testMusicListPlayer() throws UiObjectNotFoundException, RemoteException {
        LaunchFileManager();
        addStep("播放器通过列表播发");
        UiObject2 audio= waitForObj(By.text(Pattern.compile("music5")));
        check("找到了audio文件播放", audio != null);
        audio.click();
        sleepInt(2);
        if (audio == null) {
            addStep("没有找到相关音乐");
            press_down(4);
        }
        try {
            MusicListPlayer();
        } catch (Exception e) {
            try {
                failCount(count++, getIntParams("Loop"), e.getMessage());
                MusicListPlayer();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());

            }
        }

        press_back(4);
    }

    public void MusicListPlayer() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("..........Looper " + (Loop + 1));
            press_down(1);
            sleepInt(1);
            press_left(1);
            addStep("播放器通过列表播发");
            UiObject2 list = waitForObj(By.res("com.stv.music:id/play_list").text("播放列表"));
            check("未进入播放列表", list != null);
            list.click();
            press_center(1);
            press_left(1);
            addStep("向下换播放键");
            press_down(1);
            sleepInt(10);
            press_center(1);
            sleepInt(10);
            addStep("向上换播放键");
            press_up(1);
            sleepInt(10);
            press_back(1);
        }
    }


    @Test
    @CaseName("本地音乐与live交互")
    public void testLocalAndLiveInteraction() throws UiObjectNotFoundException, RemoteException {
        try {
            for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
                LaunchFileManager();
                addStep("本地音乐播发");
                UiObject2 audio = waitForObj(By.text(Pattern.compile("music1")));
                check("找到了audio文件播放", audio != null);
                audio.click();
                sleepInt(20);
                addStep("进入live桌面");
                press_home(2);

            }
        }
        catch (Exception e){
            try{
                failCount(count++,getIntParams("Loop"),e.getMessage());
                for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
                    LaunchFileManager();
                    addStep("本地音乐播发");
                    UiObject2 audio = waitForObj(By.text(Pattern.compile("music1")));
                    check("找到了audio文件播放", audio != null);
                    audio.click();
                    sleepInt(20);
                    addStep("进入live桌面");
                    press_home(2);
                }
            }
            catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
    }

    public void LaunchFileManager() throws UiObjectNotFoundException {
        addStep("进入文件管理");
        launchApp(AppName.Filemanager, IntentConstants.Filemanager);
        sleepInt(1);
        addStep("进入外部存储");
        press_right(4);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        UiObject2 extStorageDevice = waitForObj(extStorageDeviceS);
        UiObject2 localStoragefile = waitForObj(By.res("com.stv.filemanager:id/disk_name").text("本机存储"));
        if(extStorageDevice != null) {
            verify("没有找到usb外部存储设备，请检查", extStorageDevice != null);
            extStorageDevice.click();
            extStorageDevice.click();
            sleepInt(2);
        }
        else if (localStoragefile != null) {
                verify("未进入本机储存", localStoragefile != null);
                localStoragefile.click();
                localStoragefile.click();
                sleepInt(2);
        }

        press_menu(1);
        UiObject2 view = waitForObj(By.text(Pattern.compile("视图.*")));
        view.click();
        view.click();
        sleep(1);
        UiObject2 grid = waitForObj(By.text(Pattern.compile("网格.*")));
        grid.click();
        grid.click();
        sleep(1);
        press_menu(1);
        UiObject2 list = waitForObj(By.text(Pattern.compile("排序.*")));
        list.click();
        list.click();
        sleep(1);
        UiObject2 atoz = waitForObj(By.text(Pattern.compile("名称A到Z.*")));
        atoz.click();
        atoz.click();
        sleep(1);

        press_right(4);
        UiObject2 AA = waitForObj(By.text(Pattern.compile("AAA")));
        check("未找到到AAA文件夹", AA != null);
        AA.click();
        sleepInt(1);
        UiObject2 music = waitForObj(By.text(Pattern.compile("music")));
        check("未找到到音乐文件夹", music != null);
        press_right(4);
        music.click();
        sleepInt(1);
        press_menu(1);
        UiObject2 screen=phone.findObject(By.text(Pattern.compile("筛选.*")));
        screen.click();
        screen.click();
        sleep(10);
        UiObject2 all=waitForObj(By.text(Pattern.compile("全部.*")));
        if(all!=null) {
            press_up(2);
            all.click();
            all.click();
            sleepInt(2);
        }
        else {
            press_up(2);
            press_center(1);
            sleepInt(2);
        }
    }
}