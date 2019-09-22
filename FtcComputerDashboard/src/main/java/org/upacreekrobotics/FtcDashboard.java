package org.upacreekrobotics;

import org.upacreekrobotics.GUI.UI;

public class FtcDashboard {
    public static void main(String[] args) {

        UI gui = new UI();
        while (Thread.currentThread().isAlive()){
            gui.handleIncoming();
        }
        gui.gamepadThread.interrupt();
        gui.dashboardThread.interrupt();
    }
}