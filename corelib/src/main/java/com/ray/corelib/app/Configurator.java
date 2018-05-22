package com.ray.corelib.app;

import java.util.WeakHashMap;

public class Configurator {
    //据说WeakHashMap的键值对在不适用时就会回收
    private static final WeakHashMap<String ,Object> IMALL_CONFIGS=new WeakHashMap<>();
    private Configurator(){
        IMALL_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    /**
     * 静态内部类单例
     */
    private static class Holder{
        private static final Configurator INSTANCE=new Configurator();
    }
}
