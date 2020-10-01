package org.upacreekrobotics.dashboard;

import java.util.ArrayList;
import java.util.List;

public class RobotRestartHandler {

    private final List<RestartRequestListener> fRestartRequestListeners;
    private final List<RestartCompleteListener> fRestartCompleteListeners;

    public RobotRestartHandler() {
        fRestartRequestListeners = new ArrayList<>();
        fRestartCompleteListeners = new ArrayList<>();
    }

    public void requestRestart() {
        for(RestartRequestListener listener : fRestartRequestListeners) {
            Dashboard.getExecutor().execute(listener::requestRestart);
        }
    }

    public void restartComplete() {
        for(RestartCompleteListener listener : fRestartCompleteListeners) {
            Dashboard.getExecutor().execute(listener::restartCompleted);
        }
    }
    
    public void registerRestartRequestListener(RestartRequestListener restartRequestListener) {
        fRestartRequestListeners.add(restartRequestListener);
    }

    public void unregisterRestartRequestListener(RestartRequestListener restartRequestListener) {
        fRestartRequestListeners.remove(restartRequestListener);
    }

    public void registerRestartCompleteListener(RestartCompleteListener restartCompleteListener) {
        fRestartCompleteListeners.add(restartCompleteListener);
    }
    
    public void unregisterRestartCompleteListener(RestartCompleteListener restartCompleteListener) {
        fRestartCompleteListeners.remove(restartCompleteListener);
    }

    public interface RestartRequestListener {

        void requestRestart();
    }
    
    public interface RestartCompleteListener {
        
        void restartCompleted();
    }
}
