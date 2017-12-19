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

import org.junit.Test;

public class MusicStress extends LetvTestCase {
    int count=0;

    @Test
    @CaseName("媒体中心里播放音频")
    public void testPlayMusic() throws UiObjectNotFoundException, RemoteException {
        addStep("打开媒体中心");
        launchApp(AppName.Music,IntentConstants.Music);
        sleepInt(10);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                PlayMusic();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开媒体中心");
                    launchApp(AppName.Music,IntentConstants.Music);
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
        addStep("播放音频文件50s");
        press_center(1);
        sleepInt(10);
       addStep("暂停，继续播放音频文件循环5次");
        for (int i = 0;i<5;i++) {
            press_center(1);
            sleepInt(5);
            press_center(1);
            sleepInt(5);
//            phone.pressKeyCode(KEY_MEDIA_PLAY_PAUSE);
//            UiObject2 pause1 = waitForObj(By.res("com.tencent.qqmusictv.qing:id/play_full_screen_paly_btn"));
//            check("music isn't paused", pause1 != null);
//            pause1.click();
//            phone.pressKeyCode(KEY_MEDIA_PLAY_PAUSE);
//            sleepInt(5);
        }
        addStep("播放下一首音频文件5次");
        for (int i = 0;i<5;i++) {
            press_right(1);
            sleepInt(1);
        }
        addStep("播放上一首音频文件5次");
        for (int i = 0;i<5;i++) {
            press_left(1);
            sleepInt(1);
        }
    }

    @Test
    @CaseName("媒体中心里进入播放各列表音频")
    public void testSearchPlayMusic() throws UiObjectNotFoundException, RemoteException {
        addStep("打开媒体中心");
        launchApp(AppName.Music,IntentConstants.Music);
        sleepInt(10);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".........loop:" + Loop);
            try {
                SearchPlayMusic();
            }catch (Exception e){
                try {
                    failCount(count++, getIntParams("Loop"), e.getMessage());
                    addStep("打开媒体中心");
                    launchApp(AppName.Music,IntentConstants.Music);
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
