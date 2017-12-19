package com.letv.cases.leui;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import org.junit.Test;
import java.util.regex.Pattern;

public class MessageStress extends LetvTestCase {
    int count=0;
    @Test
    @CaseName("消息压力测试")
    public void testMessage()throws UiObjectNotFoundException,RemoteException {
        addStep("进入消息中心");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("........Loop"+Loop);
            launchApp(AppName.Message, IntentConstants.Message);
            addStep("消息中心标签切换");
            for(int i=0; i<3;i++){
                UiObject2 messageA=phone.findObject(By.text(Pattern.compile("消息.*")));
                check("未进消息框",messageA!=null);
                press_down(3);
                sleep(30);
                press_up(3);
                sleep(30);
            }
            addStep("进入时间标签");
            UiObject2 time = phone.findObject(By.text(Pattern.compile("时间")));
            check("未进入时间", time != null);
            time.click();
            time.click();
            press_right(1);
            press_left(1);
            sleepInt(1);
            press_down(1);
            sleepInt(1);
            addStep("进入类型标签");
            UiObject2 type = phone.findObject(By.text(Pattern.compile("类型")));
            check("未进入时间", type != null);
            type.click();
            type.click();
            press_right(1);
            press_left(1);
            sleepInt(1);
            press_down(1);
            sleepInt(1);
            addStep("进入已读/未读标签");
            UiObject2 message = phone.findObject(By.text(Pattern.compile("已读/未读.*")));
            check("未进入时间", message != null);
            message.click();
            message.click();
            press_right(1);
            press_left(1);
            sleepInt(1);
            press_back(1);
        }
    }
}
