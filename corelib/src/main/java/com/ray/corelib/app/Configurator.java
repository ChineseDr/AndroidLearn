package com.ray.corelib.app;

import java.util.WeakHashMap;

public class Configurator {
    //据说WeakHashMap的键值对在不适用时就会回收
    private static final WeakHashMap<String ,Object> IMALL_CONFIGS=new WeakHashMap<>();
}
