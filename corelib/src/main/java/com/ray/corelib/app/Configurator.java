package com.ray.corelib.app;

import java.util.HashMap;
import java.util.WeakHashMap;

public class Configurator {
    //据说WeakHashMap的键值对在不适用时就会回收
    private static final WeakHashMap<String, Object> IMALL_CONFIGS = new WeakHashMap<>();

    private Configurator() {
        //刚刚开始配置未完成，
        // 返回名字的字符串
        IMALL_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 静态内部类单例
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    final WeakHashMap<String,Object> getIMallConfigs(){
        return IMALL_CONFIGS;
    }


    public final void configure() {
        //配置完成
        IMALL_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }


    public final Configurator withApiHost(String host){
        IMALL_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady= (boolean) IMALL_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady){
            throw new RuntimeException("");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) IMALL_CONFIGS.get(key.name());
    }
}
