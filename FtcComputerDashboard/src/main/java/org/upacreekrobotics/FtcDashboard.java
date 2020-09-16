package org.upacreekrobotics;

import org.upacreekrobotics.GUI.UI;

import java.lang.reflect.Field;

public class FtcDashboard {
    public static void main(String[] args) {
        //setDllLibraryPath("lib/");
//        //System.out.println("Library: " + System.getProperty("java.library.path"));
//        System.loadLibrary("jinput-osx");

        UI gui = new UI();
        while (Thread.currentThread().isAlive()){
            gui.handleIncoming();
        }
        gui.gamepadThread.interrupt();
        gui.dashboardThread.interrupt();
    }

    public static void setDllLibraryPath(String resourceStr) {
        try {
            System.setProperty("java.library.path", resourceStr);
            //System.setProperty("java.library.path", "/lib/x64");//for example

            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);//next time path is accessed, the new path will be imported
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}