package com.miracleshed.common.helper;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Looper;

import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * @author zhengyx
 * @des  https://github.com/JeremyLiao/LiveEventBus
 *       基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP
 * @example
 *         1。订阅变化
 *             LiveEventBusHelper.listenChange(this, "a", Integer.class, new Observer<Integer>() {
 *                     @Override
 *                     public void onChanged(@Nullable Integer s) {
 *                          ToastUtil.toast("new value is "+s);
 *                     }
 *                 });
 *         2。发布变化
 *        Integer value = i++;
 *                 LiveEventBusHelper.postChange("a", value);
 *        3。如果是区别某种行为并且取不同数据的话，则使用postIntent,和listenInten比如
 *             3。1  订阅
                    LiveEventBusHelper.listenIntent(this, new Observer<Intent>() {
                        @Override
                        public void onChanged(Intent intent) {
                            String action = intent.getAction();
                            if (Objects.equals("aaa",action)) {
                                 ToastUtil.toast("from aaa");
                            } else if (Objects.equals("bbb",action)) {
                                 int intValue = intent.getIntExtra("intkey", 0);
                                 ToastUtil.toast("from bbb get int extra value"+intValue);
                             }
                        }
                    });
 *             3。2  发布
 *                 Intent intent = new Intent("bbb");
 *                 intent.putExtra("intkey",1111);
 *                 LiveEventBusHelper.postIntent(intent);
 * @date 2019/3/6
 **/
public class LiveEventBusHelper {

    private static String KEY_INTENT = "key_intent";
    /**
     * 发送通知变化
     * @param key
     * @param value
     */
    public static void postChange(String key, Object value) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            LiveEventBus.get().with(key).setValue(value);
        } else {
            LiveEventBus.get().with(key).postValue(value);
        }
    }

    /**
     * 监听变化做处理
     * @param owner
     * @param key
     * @param valueTypeClass
     * @param observer
     */
    public static void listenChange(LifecycleOwner owner, String key, Class valueTypeClass, Observer observer) {
        LiveEventBus.get()
                .with(key, valueTypeClass)
                .observe(owner, observer);
    }

    /**
     * 发送通知变化
     * @param value
     */
    public static void postIntent(Intent value) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            LiveEventBus.get().with(KEY_INTENT).setValue(value);
        } else {
            LiveEventBus.get().with(KEY_INTENT).postValue(value);
        }
    }

    /**
     * 监听变化做处理
     * @param owner
     * @param observer
     */
    public static void listenIntent(LifecycleOwner owner, Observer observer) {
        LiveEventBus.get()
                .with(KEY_INTENT, Intent.class)
                .observe(owner, observer);
    }



}
