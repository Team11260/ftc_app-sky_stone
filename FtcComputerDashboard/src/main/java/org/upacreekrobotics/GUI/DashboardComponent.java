package org.upacreekrobotics.GUI;

import java.awt.Component;

public class DashboardComponent {

    private String type, name;
    private Component component;

    public DashboardComponent(String type, String name, Component component) {
        this.type = type;
        this.name = name;
        this.component = component;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Component getComponent() {
        return component;
    }

    public String toString() {
        return type + " " + name + " " + component;
    }
}

