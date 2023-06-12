package cn.evole.mods.reset.util;

import cn.evole.mods.reset.Reset;

/**
 * Author cnlimiter
 * CreateTime 2023/6/8 17:08
 * Name Logger
 * Description
 */

public class Logger {

    public static void info(String msg){
        Reset.instance.getLog().info(msg);
    }

    public static void warn(String msg){
        Reset.instance.getLog().warn(msg);
    }

    public static void err(String msg){
        Reset.instance.getLog().error(msg);
    }
}
