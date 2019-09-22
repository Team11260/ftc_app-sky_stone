package org.upacreekrobotics.GUI;

import java.io.Serializable;

public class StorableComponent implements Serializable {

    private String type, name;
    private int x, y, width, height;

    public StorableComponent(String type, String name, int x, int y, int width, int height) {
        this.type = type;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
