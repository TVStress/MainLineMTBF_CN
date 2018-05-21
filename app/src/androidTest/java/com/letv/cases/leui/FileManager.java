package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiWatcher;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;

public class FileManager extends LetvTestCase {
    int count=0;

    BySelector storageDeviceS = By.clazz("android.widget.TextView").text("存储设备");
    BySelector extStorageDeviceS = By.text("USB");
    BySelector intStorageDeviceS = By.clazz("android.widget.TextView").text("本机存储");
    BySelector AAAS = By.clazz("android.widget.TextView").text(Pattern.compile("AAA.*|letvdownload"));
    BySelector delS = By.text("删除");
    BySelector confirmDelS = By.text("确定");
    BySelector fileTitleS = By.res("com.stv.filemanager:id/text_title");

    @Override
    public void setUp() throws Exception{
        super.setUp();
        phone.registerWatcher("allowWatcher", allowWatcher);
    }

    @Override
    public void tearDown() throws Exception{
        super.tearDown();
        phone.removeWatcher("allowWatcher");
        press_back(9);
    }

    //TV-17408:15.进入文件管理器打开、删除文件
    public void delAAAFolder() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        verify("没有找到存储设备",storageDevice!=null);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        addStep("打开外接存储设备");
        UiObject2 extStorageDevice = waitForObj(extStorageDeviceS);
        verify("没有找到外接设备",extStorageDevice!=null);
        extStorageDevice.click();
        extStorageDevice.click();
        sleepInt(1);
        addStep("删除外接存储中的AAA文件夹");
        sleepInt(1);
        for (int j=0;j<2;j++) {
            press_left(1);
            UiObject2 AAA = phone.findObject(AAAS);
            if (AAA != null) {
                for (int i = 0; i < 2; i++) {
                    if (AAA.isSelected()) {
                        break;
                    } else {
                        press_right(1);
                    }
                }
                press_right(1);
                press_left(1);
                press_menu(1);
                sleepInt(2);
                press_down(1);
                press_up(1);
                press_right(1);
                sleepInt(2);
                UiObject2 confirmDel = waitForObj(confirmDelS);
                if(confirmDel!=null){
                    verify("not del file", confirmDel != null);
                    press_center(1);
                    sleepInt(1);
                }
            }
            UiObject2 menu=waitForObj(By.text("按菜单键更多操作"));
            verify("没有找到按菜单键更多操作", menu != null);
        }
    }

    public void copyAAAFolder() throws RemoteException {
        launchApp(AppName.Filemanager,PkgName.Filemanager);
        sleepInt(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        addStep("copy 本机存储中的stability文件夹到外接USB存储中");
        UiObject2 intStorageDevice = waitForObj(intStorageDeviceS);
        intStorageDevice.click();
        sleepInt(1);
        for(int i=0;i<3;i++){
            UiObject2 AAA2 = phone.findObject(AAAS);
            if(AAA2==null){
                press_down(2);
            }else break;
        }
        for (int i = 0; i < 10; i++) {
            UiObject2 AAA1 = phone.findObject(AAAS);
            if (AAA1.isSelected()) {
                break;
            } else {
                press_right(1);
            }
        }
        press_menu(1);
        UiObject2 copy = waitForObj(By.text("复制"));
        check("copy item is not exist", copy != null);
        sleepInt(1);
        press_down(1);
        check("copy item is not focused", copy.isSelected());
        sleepInt(1);
        press_center(1);
        UiObject2 extStorageDevice = waitForObj(extStorageDeviceS);
        check("extStorage Device not exists", extStorageDevice != null);
        extStorageDevice.click();
        UiObject2 copyWindows = waitForObj(By.text("复制文件"));
        check("没有copy文件的弹框", copyWindows != null);
        sleepInt(150);
        UiObject2 hideWindows = phone.findObject(By.text("隐藏窗口"));
        if (hideWindows != null) {
            hideWindows.click();
            sleepInt(1);
        }
    }

    public void delfile(String filename) throws UiObjectNotFoundException {
        int k=1;
        if(filename.equals("zpicture")){
            k=5;
        }
        for (int i = 0; i < k; i++) {
            sleepInt(1);
            UiObject2 searchTarget = waitForObj(By.text(filename));
            check("can't find " + filename + " folder", searchTarget != null);
            searchTarget.click();
            sleepInt(2);
            UiObject2 fileTitle = waitForObj(fileTitleS);
            check("test folder not opened", fileTitle.getText().equals(filename));
            press_right(1);
            press_menu(1);
            sleepInt(2);
            press_center(1);
            UiObject2 del = phone.findObject(delS);
            if (del != null) del.click();
            sleepInt(3);
            UiObject2 confirmDel = waitForObj(confirmDelS);
            check("not del file", confirmDel != null);
            confirmDel.click();
            sleepInt(1);
        }
    }

    // TV-44260:文件管理中打开本地存储和外接存储中音视频图片文件
    public void testFileManager_playMedia() throws UiObjectNotFoundException, RemoteException {
        addStep("进入文件管理器，打开存储设备");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                FileManager_playMedia();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入文件管理器，打开存储设备");
                    launchApp(AppName.Filemanager, PkgName.Filemanager);
                    sleepInt(3);
                    FileManager_playMedia();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void FileManager_playMedia() throws UiObjectNotFoundException, RemoteException {
        UiObject2 classify = waitForObj(By.clazz("android.widget.TextView").text("分类"));
        classify.click();
        addStep("打开分类界面图片的本机存储设备，选择系统支持的任意图片打开");
        UiObject2 picture = waitForObj(By.clazz("android.widget.TextView").text("图片"));
        picture.click();
        UiObject2 localStorage1 = waitForObj(By.clazz("android.widget.TextView").text("本机存储"));
        localStorage1.click();
        press_center(3);// 第一次按键，变更鼠标为按键方式；打开相册，打开图片
        sleepInt(1);
        UiObject2 gallery3d = waitForObj(By.pkg("com.android.gallery3d"));
        check("图片未打开", gallery3d != null);

        addStep("返回到分类界面，打开音频分类本地存储系统支持的任意音频文件（播放1分钟）");
        press_back(4);
        UiObject2 audio = waitForObj(By.clazz("android.widget.TextView").text("音频"));
        audio.click();
        UiObject2 localStorage = waitForObj(By.clazz("android.widget.TextView").text("本机存储"));
        localStorage.click();
        press_center(2);
        sleepInt(60);
        UiObject2 player = waitForObj(By.pkg("com.stv.videoplayer"));
        check("音频没有播放", player != null);

        addStep("返回到分类界面，打开视频分类本地存储系统支持的任意视频文件（播放2分钟）");
        press_back(3);
        UiObject2 video = waitForObj(By.clazz("android.widget.TextView").text("视频"));
        video.click();
        localStorage.click();
        press_center(2);
        sleepInt(120);
        check("视频没有播放", player != null);

        addStep("返回到分类界面");
        press_back(3);
        classify.click();

        addStep("选择图片分类的外接存储设备中任意图片，打开");
        picture.click();
        UiObject2 extStorage = waitForObj(By.clazz("android.widget.TextView").text("TOSHIBA"));
        extStorage.click();
        sleepInt(15);
        press_center(3);// 打开相册，打开图片
        sleepInt(1);
        check("图片未打开", gallery3d != null);

        addStep("返回到分类界面，打开音频分类外接存储设备系统支持的任意音频文件（播放1分钟）");
        press_back(4);
        audio.click();
        extStorage.click();
        press_center(2);
        sleepInt(60);
        check("音频没有播放", player != null);

        addStep("返回到分类界面，打开视频分类外接存储系统支持的任意视频文件（播放2分钟）");
        press_back(3);
        video.click();
        extStorage.click();
        press_center(2);
        sleepInt(120);
        check("视频没有播放", player != null);
        addStep("按返回键");
        press_back(3);
        addStep("执行步骤2-步骤9，测试10次");
    }

    @Test
    @CaseName("播放本地存储中视频")
    public void testLocalVideo() throws UiObjectNotFoundException, RemoteException {
        addStep("进入文件管理器，打开存储设备");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        verify("存储设备不存在", storageDevice != null);
        storageDevice.click();
        sleepInt(1);
        UiObject2 intStorageDevice = waitForObj(intStorageDeviceS);
        verify("本机存储不存在", intStorageDevice != null);
        intStorageDevice.click();
        intStorageDevice.click();
        sleepInt(1);
        for(int i=0;i<3;i++){
            UiObject2 AAA2 = phone.findObject(AAAS);
            if(AAA2==null){
                press_down(2);
            }else break;
        }
        UiObject2 AAA = waitForObj(By.text("AAA"));
        verify("AAA文件夹不存在", AAA != null);
        AAA.click();
        AAA.click();
        sleepInt(1);
        addStep("打开video下的任意视频文件");
        UiObject2 video = waitForObj(By.text("video"));
        verify("视频文件不存在", video != null);
        clickAndWaitForNewWindow(video);
        press_right(1);
        press_center(1);
        sleepInt(10);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                LocalVideo();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入文件管理器，打开存储设备");
                    launchApp(AppName.Filemanager, PkgName.Filemanager);
                    sleepInt(3);
                    UiObject2 storageDevice1 = waitForObj(storageDeviceS);
                    check("存储设备不存在", storageDevice1 != null);
                    storageDevice1.click();
                    sleepInt(1);
                    UiObject2 intStorageDevice1 = waitForObj(intStorageDeviceS);
                    check("本机存储不存在", intStorageDevice1 != null);
                    intStorageDevice1.click();
                    intStorageDevice1.click();
                    sleepInt(1);
                    for(int i=0;i<3;i++){
                        UiObject2 AAA2 = phone.findObject(AAAS);
                        if(AAA2==null){
                            press_down(2);
                        }else break;
                    }
                    UiObject2 AAA1 = waitForObj(By.text("AAA"));
                    check("AAA文件夹不存在", AAA1 != null);
                    AAA1.click();
                    AAA1.click();
                    sleepInt(1);
                    addStep("打开video下的任意视频文件");
                    UiObject2 video1 = waitForObj(By.text("video"));
                    check("视频文件不存在", video1 != null);
                    clickAndWaitForNewWindow(video1);
                    press_right(1);
                    press_center(1);
                    sleepInt(10);
                    LocalVideo();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void LocalVideo() throws UiObjectNotFoundException, RemoteException {
        UiObject2 player = waitForObj(By.pkg("com.stv.videoplayer"));
        if(player==null){
            press_center(1);
        }
        UiObject2 pause2=phone.findObject(By.text("播放列表"));
        check("video not play", pause2 == null);
        phone.pressKeyCode(KEY_MEDIA_PLAY_PAUSE, 2);
        sleepInt(3);
        UiObject2 pause=waitForObj(By.res("com.stv.videoplayer:id/pause_iv"));
        check("video is not paused", pause != null);
        phone.pressKeyCode(KEY_MEDIA_PLAY_PAUSE, 2);
        sleepInt(8);
        UiObject2 pause1=phone.findObject(By.text("播放列表"));
        check("video is not playing", pause1 == null);
        sleepInt(3);
        addStep("按菜单键，切换视频画面比例");
        press_menu(1);
        String switchRatioButton[] = {"强制全屏|全屏", "原始比例"};
        for (int a = 0; a < switchRatioButton.length; a++) {
            switchRatio(switchRatioButton[a]);
        }
        addStep("开启/关闭字幕显示,进行字幕设置");
        String switchButton[] = {"开", "关"};
        for (int a = 0; a < switchButton.length; a++) {
            switchSubtitle(switchButton[a]);
        }
        addStep("设置播放模式");
        String playModelList[] = {"单个循环", "全部循环", "随机播放", "单个播放", "顺序播放"};
        for (int a = 0; a < playModelList.length; a++) {
            UiObject2 playSet = waitForObj(By.text("播放设置"));
            check("播放设置不存在", playSet != null);
            playSet.click();
            playSet.click();
            sleepInt(1);
            UiObject2 playModel = waitForObj(By.text("播放模式"));
            check("播放模式不存在", playModel != null);
            playModel.click();
            playModel.click();
            sleepInt(1);
            UiObject2 playModelName = waitForObj(By.text(playModelList[a]));
            check(playModelList[a] + "不存在", playModelName != null);
            playModelName.click();
            playModelName.click();
            sleepInt(1);
            press_back(1);
            UiObject2 playMode2 = waitForObj(By.text("播放模式"));
            check("播放模式不存在", playMode2 != null);
            press_back(1);
        }
        press_back(1);
        UiObject2 playSet1 = waitForObj(By.text("播放设置"));
        check("播放设置不存在", playSet1 == null);
        addStep("按下键，在播放列表中，选择任意视频");
        press_down(2);
        UiObject2 playList1 = waitForObj(By.text("播放列表"));
        check("playlist menu not exists", playList1 != null);
        press_center(1);
        sleepInt(1);
        UiObject2 videoSelect=waitForObj(By.text("video10.mp4"));
        if(videoSelect!=null&&videoSelect.isSelected()){
            press_up(10);
            press_center(1);
        }
        press_back(1);
        sleepInt(1);
        addStep("播放视频后退");
        press_right(1);
        phone.pressKeyCode(KEY_MEDIA_REWIND, 20);
        sleepInt(2);
        phone.pressKeyCode(KEY_MEDIA_REWIND,20);
        sleepInt(5);
    }
    

    public void switchRatio(String textName) throws UiObjectNotFoundException {
        UiObject2 pictureRatio = waitForObj(By.text("画面比例"));
        check("画面比例按钮不存在", pictureRatio != null);
        press_up(1);
        pictureRatio.click();
        sleepInt(1);
        UiObject2 force = waitForObj(By.text(Pattern.compile(textName)));
        check("未找到强制全频按钮", force != null);
        force.click();
        force.click();
        sleepInt(1);
        press_back(1);
    }

    public void switchSubtitle(String button) throws UiObjectNotFoundException {
        UiObject2 subtitle = waitForObj(By.text("字幕选择"));
        check("字幕选择不存在", subtitle != null);
        subtitle.click();
        subtitle.click();
        sleepInt(1);
        UiObject2 subtitleShow = waitForObj(By.text("字幕显示"));
        check("字幕显示不存在", subtitleShow != null);
        subtitleShow.click();
        sleepInt(1);
        UiObject2 closeOrOpen = waitForObj(By.text(button));
        check(button + "不存在", closeOrOpen != null);
        closeOrOpen.click();
        sleepInt(1);
        press_back(1);
        UiObject2 subtitleShow1 = waitForObj(By.text("字幕显示"));
        check("字幕显示不存在", subtitleShow1 != null);
        press_back(1);
    }


    @Test
    @CaseName("Copy AAA Folder from internal to external storage")
    public void testCopyAAAFolder() throws RemoteException {
       copyAAAFolder();
    }

    @Test
    @CaseName("Delete AAA folder in external storage")
    public void testDelAAAFolder() throws UiObjectNotFoundException, RemoteException {
        delAAAFolder();
    }


    @Test
    @CaseName("播放外接存储中视频")
    public void testExtVideo() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        UiObject2 storageDevice = phone.findObject(storageDeviceS);
        verify("存储设备没有找到",storageDevice!=null);
        clickAndWaitForNewWindow(storageDevice);
        addStep("打开外接存储设备");
        UiObject2 extStorageDevice = phone.findObject(extStorageDeviceS);
        verify("外接设备没有找到",extStorageDevice!=null);
        extStorageDevice.click();
        extStorageDevice.click();
        sleepInt(1);
        UiObject2 AAA = waitForObj(By.text("AAA"));
        verify("AAA文件夹不存在", AAA != null);
        AAA.click();
        AAA.click();
        sleepInt(1);
        addStep("打开video下的任意视频文件");
        UiObject2 video = waitForObj(By.text("video"));
        verify("视频文件不存在", video != null);
        clickAndWaitForNewWindow(video);
        press_right(1);
        press_center(1);
        sleepInt(10);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                LocalVideo();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    launchApp(AppName.Filemanager, PkgName.Filemanager);
                    sleepInt(3);
                    UiObject2 storageDevice1 = phone.findObject(storageDeviceS);
                    verify("存储设备没有找到",storageDevice1!=null);
                    clickAndWaitForNewWindow(storageDevice1);
                    addStep("打开外接存储设备");
                    UiObject2 extStorageDevice1 = phone.findObject(extStorageDeviceS);
                    verify("外接设备没有找到",extStorageDevice1!=null);
                    extStorageDevice1.click();
                    extStorageDevice1.click();
                    sleepInt(1);
                    UiObject2 AAA1 = waitForObj(By.text("AAA"));
                    verify("AAA文件夹不存在", AAA1 != null);
                    AAA1.click();
                    AAA1.click();
                    sleepInt(1);
                    addStep("打开video下的任意视频文件");
                    UiObject2 video1 = waitForObj(By.text("video"));
                    verify("视频文件不存在", video1 != null);
                    clickAndWaitForNewWindow(video1);
                    press_right(1);
                    press_center(1);
                    sleepInt(10);
                    LocalVideo();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        press_back(3);
    }

    @Test
    @CaseName("播放本地存储中音频，进行操作")
    public void testLocalMedia() throws UiObjectNotFoundException, RemoteException {
        addStep("进入文件管理器，打开存储设备");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        UiObject2 storageDevice = phone.findObject(storageDeviceS);
        verify("没有找到存储设备", storageDevice != null);
        storageDevice.click();
        sleepInt(1);
        UiObject2  intStorageDevice= phone.findObject(intStorageDeviceS);
        verify("没有找到本机存储", intStorageDevice != null);
        intStorageDevice.click();
        intStorageDevice.click();
        sleepInt(1);
        for(int i=0;i<3;i++){
            UiObject2 AAA2 = phone.findObject(AAAS);
            if(AAA2==null){
                press_down(2);
            }else break;
        }
        for (int i = 0; i < 10; i++) {
            UiObject2 AAA = phone.findObject(AAAS);
            if (AAA.isSelected()) {
                break;
            } else {
                press_right(1);
            }
        }
        UiObject2 AAA = phone.findObject(AAAS);
        AAA.click();
        sleepInt(1);
        addStep("打开music下的任意音频文件");
        sleepInt(2);
        UiObject2 musicFolder = phone.findObject(By.text("music"));
        verify("没有找到music文件夹", musicFolder != null);
        press_right(1);
        musicFolder.click();
        sleepInt(1);
        UiObject2 musicName = phone.findObject(By.textContains("music1"));
        verify("音频文件不存在", musicName != null);
        clickAndWaitForNewWindow(musicName);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                playMusic(Loop);
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入文件管理器，打开存储设备");
                    launchApp(AppName.Filemanager, PkgName.Filemanager);
                    sleepInt(3);
                    UiObject2 storageDevice1 = phone.findObject(storageDeviceS);
                    check("没有找到存储设备", storageDevice1 != null);
                    storageDevice1.click();
                    sleepInt(1);
                    UiObject2  intStorageDevice1= phone.findObject(intStorageDeviceS);
                    check("没有找到本机存储", intStorageDevice1 != null);
                    intStorageDevice1.click();
                    intStorageDevice1.click();
                    sleepInt(1);
                    sleepInt(1);
                    for (int i = 0; i < 10; i++) {
                        UiObject2 AAA1 = phone.findObject(AAAS);
                        if (AAA1.isSelected()) {
                            break;
                        } else {
                            press_right(1);
                        }
                    }
                    UiObject2 AAA2 = phone.findObject(AAAS);
                    AAA2.click();
                    sleepInt(1);
                    addStep("打开music下的任意音频文件");
                    sleepInt(2);
                    UiObject2 musicFolder1 = phone.findObject(By.text("music"));
                    check("没有找到music文件夹", musicFolder1 != null);
                    press_right(1);
                    musicFolder1.click();
                    sleepInt(1);
                    UiObject2 musicName1 = phone.findObject(By.textContains("music1"));
                    check("音频文件不存在", musicName1 != null);
                    clickAndWaitForNewWindow(musicName1);
                    playMusic(Loop);
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    @Test
    @CaseName("播放外接存储中音频，进行操作")
    public void testExtMedia() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        UiObject2 storageDevice = phone.findObject(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        addStep("打开外接存储设备");
        sleepInt(1);
        UiObject2 extStorageDevice = phone.findObject(extStorageDeviceS);
        verify("音频文件不存在", extStorageDevice != null);
        extStorageDevice.click();
        extStorageDevice.click();
        sleepInt(1);
        for (int i = 0; i < 10; i++) {
            UiObject2 AAA = phone.findObject(AAAS);
            if (AAA.isSelected()) {
                break;
            } else {
                press_right(1);
            }
        }
        UiObject2 AAA = phone.findObject(AAAS);
        AAA.click();
        sleepInt(1);
        addStep("打开music下的任意音频文件");
        sleepInt(2);
        UiObject2 musicFolder = phone.findObject(By.text("music"));
        verify("没有找到music文件夹", musicFolder != null);
        press_right(1);
        musicFolder.click();
        sleepInt(1);
        UiObject2 musicName = phone.findObject(By.textContains("music1"));
        verify("音频文件不存在", musicName != null);
        clickAndWaitForNewWindow(musicName);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                playMusic(Loop);
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    launchApp(AppName.Filemanager, PkgName.Filemanager);
                    sleepInt(3);
                    UiObject2 storageDevice1 = phone.findObject(storageDeviceS);
                    clickAndWaitForNewWindow(storageDevice1);
                    addStep("打开外接存储设备");
                    sleepInt(1);
                    UiObject2 extStorageDevice1= phone.findObject(extStorageDeviceS);
                    check("没有找到外接存储", extStorageDevice1 != null);
                    extStorageDevice1.click();
                    extStorageDevice1.click();
                    sleepInt(1);
                    for (int i = 0; i < 10; i++) {
                        UiObject2 AAA1 = phone.findObject(AAAS);
                        if (AAA1.isSelected()) {
                            break;
                        } else {
                            press_right(1);
                        }
                    }
                    UiObject2 AAA2 = phone.findObject(AAAS);
                    AAA2.click();
                    sleepInt(1);
                    addStep("打开music下的任意音频文件");
                    sleepInt(2);
                    UiObject2 musicFolder1 = phone.findObject(By.text("music"));
                    check("没有找到music文件夹", musicFolder1 != null);
                    press_right(1);
                    musicFolder1.click();
                    sleepInt(1);
                    UiObject2 musicName1 = phone.findObject(By.textContains("music1"));
                    check("音频文件不存在", musicName1 != null);
                    clickAndWaitForNewWindow(musicName1);
                    playMusic(Loop);
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void playMusic(int Loop){
            sleepInt(5);
            UiObject2 player = phone.findObject(By.pkg("com.stv.music"));
            check("音乐没有播放", player != null);
            addStep("进行暂停、播放操作");
            sleepInt(6);
            press_center(1);
            sleepInt(2);
            press_center(1);
            addStep("按菜单键，进行设置播放模式");
            press_menu(1);
            press_left(2);
            for (int i = 0; i < 3; i++) {
                UiObject2 repeatMode = phone.findObject(By.res("com.stv.music:id/repeat_ib"));
                String currentLoopName =repeatMode.getText();
                System.out.println("当前的播放模式为" + currentLoopName);
                press_center(1);
                sleepInt(3);
                UiObject2 repeatMode1 = phone.findObject(By.res("com.stv.music:id/repeat_ib"));
                String afterLoopName = repeatMode1.getText();
                check("播放模式未切换成功", !currentLoopName.equals(afterLoopName));
            }
            press_back(1);
            sleepInt(5);
            addStep("播放过程中，按下键，在播放列表中，选择任意视频，按确定键");
            press_down(1);
            UiObject2 playList = phone.findObject(By.text("播放列表"));
            check("播放列表不存在", playList != null);
            clickAndWaitForNewWindow(playList);
            press_down(Loop+1);
            press_center(1);
            press_back(1);
            sleepInt(1);
    }

    @Test
    @CaseName("播放本地存储中音频，进行操作")
    public void testPlayMedia() throws UiObjectNotFoundException, RemoteException {
        addStep("进入文件管理器，打开存储设备");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        UiObject2 video = phone.findObject(By.text("视频"));
        UiObject2 videoFile = phone.findObject(By.text("视频文件"));
        UiObject2 picture = phone.findObject(By.text("图片"));
        UiObject2 nullFolder = phone.findObject(By.text("null"));
        UiObject2 music = phone.findObject(By.text("音乐"));
        UiObject2 musicFile = phone.findObject(By.text("音频文件"));
        UiObject2 apk = phone.findObject(By.text("安装包"));
        addStep("进入视频项，播放视频");
        check("视频项不存在", video != null);
        clickAndWaitForNewWindow(video);
        sleepInt(1);
        check("没有成功进入视频", videoFile != null);
        press_down(1);
        UiObject2 video1 = phone.findObject(By.text("video1.mp4"));
        check("没有成功进入视频", video1 != null);
        clickAndWaitForNewWindow(video1);
        sleepInt(10);
        press_back(2);

        addStep("进入图片项，浏览图片");
        check("照片项不存在", picture != null);
        clickAndWaitForNewWindow(picture);
        sleepInt(1);
        check("null文件夹不存在", nullFolder != null);
        clickAndWaitForNewWindow(nullFolder);
        sleepInt(1);
        press_center(1);
        press_right(8);
        press_back(3);

        addStep("进入音乐项,播放音乐");
        check("照片项不存在", music != null);
        clickAndWaitForNewWindow(music);
        sleepInt(1);
        check("没有进入音频文件", musicFile != null);
        press_right(1);
        sleepInt(1);
        press_center(1);
        addStep("播放10首音乐，各10秒");
        for (int i = 0; i < 10; i++) {
            sleepInt(10);
            press_right(1);
        }

        press_back(3);
        addStep("进入安装包项,打开应用");
    }


    @Test
    @CaseName("文件管理器中打开和删除文件/文件夹")
    public void testAddDelFiles() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        sleepInt(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        verify("没有找到存储设备", storageDevice != null);
        clickAndWaitForNewWindow(storageDevice);
        addStep("打开外接存储设备打开AAA文件夹");
        UiObject2 extStorageDevice = waitForObj(extStorageDeviceS);
        verify("没有找到外接存储", extStorageDevice != null);
        extStorageDevice.click();
        extStorageDevice.click();
        UiObject2 AAA = waitForObj(AAAS);
        verify("AAA folder is not found", AAA != null);
        AAA.click();
        AAA.click();
        sleepInt(1);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                AddDelFiles();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    launchApp(AppName.Filemanager, PkgName.Filemanager);
                    sleepInt(3);
                    UiObject2 storageDevice1 = waitForObj(storageDeviceS);
                    verify("存储设备不存在",storageDevice1!=null);
                    clickAndWaitForNewWindow(storageDevice1);
                    addStep("打开外接存储设备打开AAA文件夹");
                    UiObject2 extStorageDevice1 = waitForObj(extStorageDeviceS);
                    check("没有找到外接存储", extStorageDevice != null);
                    extStorageDevice1.click();
                    extStorageDevice1.click();
                    UiObject2 AAA1 = waitForObj(AAAS);
                    check("AAA folder is not found", AAA1 != null);
                    AAA1.click();
                    AAA1.click();
                    sleepInt(1);
                    AddDelFiles();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        addStep("退出文件管理");
        press_back(4);
    }

    public void AddDelFiles() throws UiObjectNotFoundException, RemoteException {
        UiObject2 fileTitle = waitForObj(By.res("com.stv.filemanager:id/text_title").text(Pattern.compile("AAA.*")));
        check("open AAA successful", fileTitle != null);
        addStep("删除文件夹");
        delfile("folder");
        press_back(1);
        sleepInt(1);
        delfile("folder");
        press_back(1);
        sleepInt(1);
        delfile("folder");
        press_back(1);
        sleepInt(1);
        delfile("folder");
        press_back(1);
        sleepInt(1);

        addStep("删除音频文件");
        delfile("music");
        press_back(1);
        sleepInt(1);

        addStep("删除10张图片");
        delfile("zpicture");
        press_back(1);
        sleepInt(1);


        addStep("删除视频");
        delfile("video");
        press_back(1);
        sleepInt(1);
    }

    private final UiWatcher allowWatcher = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 exitWindows = phone.findObject(By.textContains("要允许文件管理访问"));
            UiObject2 allow = phone.findObject(By.text("允许"));
            if (exitWindows != null) {
                clickAndWaitForNewWindow(allow);
                sleepInt(15);
                return true;
            } else {
                return false;
            }
        }
    };
}
