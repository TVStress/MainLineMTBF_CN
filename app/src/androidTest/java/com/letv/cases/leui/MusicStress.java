package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
//import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.Test;

import java.util.regex.Pattern;

public class MusicStress extends LetvTestCase {
    int count=0;



    @Test
    @CaseName("音乐本地播放音频")
    public void testLocalPlayMusic() throws UiObjectNotFoundException, RemoteException {
        addStep("打开媒体中心");
        launchApp(AppName.Music,PkgName.Music);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                LocalPlayMusic();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开媒体中心");
                    launchApp(AppName.Music, PkgName.Music);
//                    enter_PlayMusic();
//                    sleepInt(10);
                    LocalPlayMusic();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void LocalPlayMusic() throws UiObjectNotFoundException, RemoteException {
        UiObject2 online=waitForObj(By.clazz("android.widget.TextView").text("本地音乐"));
        check("未进本地播放音乐",online!=null);
        online.click();
        online.click();
        press_center(1);
        sleepInt(5);
        UiObject2 title1=waitForObj(By.res("com.stv.music:id/common_title").text("是否播放推荐歌曲?"));
        if(title1!=null){
            UiObject2 cannel=waitForObj(By.res("com.stv.music:id/cancel_btn").text("取消"));
            cannel.click();
            cannel.click();
        }
        addStep("音乐播放、暂停、下一首、下一首");
        for(int i=0;i<6;i++){
            UiObject2 pause=waitForObj(By.res("com.stv.music:id/pause"));
            check("播放暂停",pause!=null);
            pause.click();
            sleepInt(5);
            UiObject2 next=waitForObj(By.res("com.stv.music:id/next"));
            check("播放下一首",pause!=null);
            next.click();
            sleepInt(5);
            UiObject2 prev=waitForObj(By.res("com.stv.music:id/prev"));
            check("播放上一首",pause!=null);
            prev.click();
            sleepInt(5);
        }
        addStep("音乐播放列表连续播放");
        press_menu(1);
        UiObject2 play_list1 = waitForObj(By.res("com.stv.music:id/play_list").text("播放列表"));
        play_list1.click();
        UiObject2 play = waitForObj(By.res("com.stv.music:id/title_tv").text("播放列表"));
        check("未进入播放列表", play != null);
        press_up(10);
        press_center(1);
        sleepInt(5);
        press_back(1);
        for(int j=0;j<10;j++) {
            press_menu(1);
            UiObject2 play_list2 = waitForObj(By.res("com.stv.music:id/play_list").text("播放列表"));
            play_list2.click();
            check("未进入播放列表", play != null);
            press_down(j+2);
            press_center(1);
            sleepInt(5);
            press_back(1);
        }
        press_back(1);
        sleepInt(5);
    }




















    @Test
    @CaseName("音乐在线播放音频")
    public void testOnlinePlayMusic() throws UiObjectNotFoundException, RemoteException {
        addStep("打开媒体中心");
//        launchApp(AppName.Music,PkgName.Music);
//        sleepInt(10);
        enter_PlayMusic();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                OnlinePlayMusic();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开媒体中心");
//                    launchApp(AppName.Music, PkgName.Music);
                    enter_PlayMusic();
                    sleepInt(10);
                    OnlinePlayMusic();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }

    }
    public void OnlinePlayMusic() throws UiObjectNotFoundException, RemoteException {
        UiObject2 online=waitForObj(By.clazz("android.widget.LinearLayout").res("com.stv.music:id/onlineButton"));
        check("未进入在线音乐",online!=null);
        online.click();
        press_down(1);
        UiObject2 view=waitForObj(By.res("com.letv.tv:id/channel_headview_view2"));
        check("未进入音乐",view!=null);
        view.click();
        view.click();
        UiObject2 item =waitForObj(By.res("com.letv.tv:id/video_topic_item"));
        check("未进入音乐片源播放",view!=null);
        item.click();
        item.click();
        press_left(1);
        press_center(1);
        for(int i=0;i<5;i++){
            sleepInt(5);
            press_center(1);
            sleepInt(4);

            press_center(1);
            sleepInt(10);
            press_right(2);
            sleepInt(5);
            press_left(2);
        }
        press_back(1);
        sleepInt(2);
        press_back(2);
    }









    @Test
    @CaseName("媒体中心里播放")
    public void testMediaPlayMusic() throws UiObjectNotFoundException, RemoteException {
        addStep("打开媒体中心");
//        launchApp(AppName.Music,PkgName.Music);
//        sleepInt(10);
        enter_PlayMusic();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                PlayMusic();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开媒体中心");
//                    launchApp(AppName.Music, PkgName.Music);
                    enter_PlayMusic();
                    sleepInt(10);
                    PlayMusic();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }

    }
    public void PlayMusic() throws UiObjectNotFoundException, RemoteException {
        UiObject2 online=waitForObj(By.clazz("android.widget.LinearLayout").res("com.stv.music:id/localButton"));
        check("未进本地播放音乐",online!=null);
        online.click();
        online.click();
        press_up(1);
        press_center(1);
        sleepInt(20);


        addStep("暂停，继续播放音频文件循环5次");
        for (int i = 0;i<5;i++) {
            press_center(1);
            sleepInt(5);
            press_center(1);
            sleepInt(5);
        }
        addStep("播放下一首音频文件5次");
        for (int i = 0;i<5;i++) {
            press_right(1);
            sleepInt(5);
        }
        addStep("播放上一首音频文件5次");
        for (int i = 0;i<5;i++) {
            press_left(1);
            sleepInt(5);
        }

        addStep("打开播放列表任意选择5首每首播放10秒");
        for(int i=0;i<5;i++) {
            press_menu(1);
            press_down(1);
            UiObject2 playlist = waitForObj(By.text("播放列表"));
            check(playlist != null);
            press_center(1);
            sleepInt(10);
        }
        press_menu(1);
        press_up(6);
        press_center(1);
        sleepInt(10);

        press_back(1);
        sleepInt(2);
    }


    public void enter_PlayMusic(){
        gotoHomeScreen("应用");
        press_back(3);
        press_down(1);
        UiObject2 allapp=phone.findObject(By.text(Pattern.compile("全部应用")));
        if(allapp!=null){
            allapp.click();
            sleepInt(5);
            press_down(7);
        }
        UiObject2 music=waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("音乐"));
        music.click();
        music.click();
        sleepInt(5);
    }

    @Test
    @CaseName("媒体中心里进入播放各列表音频")
    public void testSearchPlayMusic() throws UiObjectNotFoundException, RemoteException {
        addStep("打开媒体中心");
        launchApp(AppName.Music,PkgName.Music);
        sleepInt(10);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".........loop:" + Loop);
            try {
                SearchPlayMusic();
            }catch (Exception e){
                try {
                    failCount(count++, getIntParams("Loop"), e.getMessage());
                    addStep("打开媒体中心");
                    launchApp(AppName.Music,PkgName.Music);
                    sleepInt(10);
                    SearchPlayMusic();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        press_back(4);
    }
    public void SearchPlayMusic() throws UiObjectNotFoundException, RemoteException {
        addStep("打开播放列表任意选择5首每首播放10秒");
        for(int i=0;i<5;i++) {
            press_menu(1);
            press_down(1);
            UiObject2 playlist = waitForObj(By.text("播放列表"));
            check(playlist != null);
            press_center(1);
            sleepInt(10);
        }
        press_menu(1);
        press_up(6);
        press_center(1);
        sleepInt(10);

//        press_up(1);
//        UiObject2 find;
//        press_center(1);
//        find=waitForObj(By.res("com.tencent.qqmusictv.qing:id/mv_title").text("音乐台"));
//        check("没有进入发现", find != null);
//        find.click();
//        String[] classification={"推荐专辑","精选歌单","排行榜","推荐电台"};
//        for(int i=0;i<classification.length;i++){
//            addStep("进入" + classification[i] + "分类播放歌曲");
//            UiObject2 music=waitForObj(By.text(classification[i]));
//            check("没有进入发现", music != null);
//            music.click();
//            sleepInt(1);
//            for(int k=1;k<5;k++){
//                UiObject2 grid=waitForObj(By.clazz("android.widget.GridView"));
//                check("can't find grid view", grid != null);
//                UiObject2 albumMusic=grid.getChildren().get(k);
//                check("音乐专辑不存在", albumMusic != null);
//                albumMusic.click();
//                albumMusic.click();
//                sleepInt(1);
//                if(i!=3){
//                    UiObject2 all=waitForObj(By.text("播放全部"));
//                    check("没有找到播放全部按钮",all!=null);
//                    all.click();
//                    sleepInt(5);
//                    press_back(1);
//                }
//            }
//        }
//        press_back(1);
    }
}
