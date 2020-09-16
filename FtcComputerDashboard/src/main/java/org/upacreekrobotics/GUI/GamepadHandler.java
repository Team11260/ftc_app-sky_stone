package org.upacreekrobotics.GUI;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GamepadHandler {

    private List<Controller> controllers = null;
    private HashMap<Integer, HashMap<String, Float>> lastValues = new HashMap<>();

    public GamepadHandler() {

        controllers = new ArrayList<>(Arrays.asList(ControllerEnvironment.getDefaultEnvironment().getControllers()));

        List<Controller> removeControllers = new ArrayList<>();

        for(Controller controller : controllers) {
            if(!isValidController(controller.getName())) removeControllers.add(controller);
        }

        controllers.removeAll(removeControllers);
    }

    public synchronized void updateControllers() {
        controllers = new ArrayList(Arrays.asList(createDefaultEnvironment().getControllers()));
        ArrayList<Controller> removeControllers = new ArrayList<>();

        for(Controller controller : controllers) {
            if(!isValidController(controller.getName())) removeControllers.add(controller);
        }

        controllers.removeAll(removeControllers);
    }

    public synchronized List<Controller> getControllers() {
        if(controllers != null) return controllers;

        controllers = new ArrayList(Arrays.asList(createDefaultEnvironment().getControllers()));

        ArrayList<Controller> removeControllers = new ArrayList<>();

        for(Controller controller : controllers) {
            if(!isValidController(controller.getName())) removeControllers.add(controller);
        }

        controllers.removeAll(removeControllers);

        removeControllers.clear();
        removeControllers.addAll(controllers);

        return removeControllers;
    }

    public synchronized String getGamepadState(int gamepad) {
        Controller controller = controllers.get(gamepad);

        String value = "";

        HashMap<String, Float> oldComponents = lastValues.get(gamepad);
        if(oldComponents == null) oldComponents = new HashMap<>();
        HashMap<String, Float> currentComponents = new HashMap<>();

        if(!controller.poll()) return null;

        for (Component component : controller.getComponents()) {
            currentComponents.put(component.getName(), component.getPollData());
            if(controller.getName().contains("Apple")) {
                HashMap<String, Component> identifiers = new HashMap<>();

                for (Component comp : controller.getComponents()) {
                    identifiers.put(comp.getIdentifier().toString().toLowerCase(), comp);
                }

                if(!identifiers.containsKey("middle")) {
                    if (identifiers.get("a").getPollData() == 1.0f) {
                        value += "left_stick_x:-1.0<:-:>";
                    } else if (identifiers.get("d").getPollData() == 1.0f) {
                        value += "left_stick_x:1.0<:-:>";
                    } else {
                        value += "left_stick_x:0.0<:-:>";
                    }

                    if (identifiers.get("w").getPollData() == 1.0f) {
                        value += "left_stick_y:-1.0<:-:>";
                    } else if (identifiers.get("s").getPollData() == 1.0f) {
                        value += "left_stick_y:1.0<:-:>";
                    } else {
                        value += "left_stick_y:0.0<:-:>";
                    }

                    if (identifiers.get("left").getPollData() == 1.0f) {
                        value += "right_stick_x:-1.0<:-:>";
                    } else if (identifiers.get("right").getPollData() == 1.0f) {
                        value += "right_stick_x:1.0<:-:>";
                    } else {
                        value += "right_stick_x:0.0<:-:>";
                    }

                    if (identifiers.get("up").getPollData() == 1.0f) {
                        value += "right_stick_y:-1.0<:-:>";
                    } else if (identifiers.get("down").getPollData() == 1.0f) {
                        value += "right_stick_y:1.0<:-:>";
                    } else {
                        value += "right_stick_y:0.0<:-:>";
                    }
                } else {
                    value += "left_stick_x:" + (identifiers.get("x").getPollData() / 10) + "<:-:>";
                    value += "left_stick_y:" + (identifiers.get("y").getPollData() / 10) + "<:-:>";
                }

                break;
            } else {
                if (component.isAnalog()) {
                    String name = null;

                    switch (component.getIdentifier().toString()) {
                        case "x":
                            name = "left_stick_x";
                            break;
                        case "y":
                            name = "left_stick_y";
                            break;
                        case "rx":
                            name = "right_stick_x";
                            break;
                        case "ry":
                            name = "right_stick_y";
                            break;
                        case "z":
                            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                                if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                                    if (component.getPollData() > 0) {
                                        value += "left_trigger:" + (Math.abs(component.getPollData()) < 0.05 ? 0 : Math.abs(component.getPollData())) + "<:-:>right_trigger:0<:-:>";
                                    } else if (component.getPollData() > -1) {
                                        value += "left_trigger:0<:-:>right_trigger:" + (Math.abs(component.getPollData()) < 0.05 ? 0 : Math.abs(component.getPollData())) + "<:-:>";
                                    } else {
                                        value += "left_trigger:0<:-:>right_trigger:0<:-:>";
                                    }
                                }
                            } else {
                                name = "left_trigger";
                                if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData()))
                                    value += name + ":" + (Math.abs((component.getPollData() + 1) / 2) < 0.05 ? 0 : (component.getPollData() + 1) / 2) + "<:-:>";
                                name = null;
                            }
                            break;
                        case "rz":
                            name = "right_trigger";
                            if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData()))
                                value += name + ":" + (Math.abs((component.getPollData() + 1) / 2) < 0.05 ? 0 : (component.getPollData() + 1) / 2) + "<:-:>";
                            name = null;
                            break;
                    }

                    if (name != null) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData()))
                            value += name + ":" + (Math.abs(component.getPollData()) < 0.05 ? 0 : component.getPollData()) + "<:-:>";
                    }
                } else if (component.getIdentifier().toString().equals("pov")) {
                    if (component.getPollData() == 0.125) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:true<:-:>";
                            value += "dpad_up:true<:-:>";
                            value += "dpad_right:false<:-:>";
                            value += "dpad_down:false<:-:>";
                        }
                    } else if (component.getPollData() == 0.25) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:false<:-:>";
                            value += "dpad_up:true<:-:>";
                            value += "dpad_right:false<:-:>";
                            value += "dpad_down:false<:-:>";
                        }
                    } else if (component.getPollData() == 0.375) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:false<:-:>";
                            value += "dpad_up:true<:-:>";
                            value += "dpad_right:true<:-:>";
                            value += "dpad_down:false<:-:>";
                        }
                    } else if (component.getPollData() == 0.5) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:false<:-:>";
                            value += "dpad_up:false<:-:>";
                            value += "dpad_right:true<:-:>";
                            value += "dpad_down:false<:-:>";
                        }
                    } else if (component.getPollData() == 0.625) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:false<:-:>";
                            value += "dpad_up:false<:-:>";
                            value += "dpad_right:true<:-:>";
                            value += "dpad_down:true<:-:>";
                        }
                    } else if (component.getPollData() == 0.75) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:false<:-:>";
                            value += "dpad_up:false<:-:>";
                            value += "dpad_right:false<:-:>";
                            value += "dpad_down:true<:-:>";
                        }
                    } else if (component.getPollData() == 0.875) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:true<:-:>";
                            value += "dpad_up:false<:-:>";
                            value += "dpad_right:false<:-:>";
                            value += "dpad_down:true<:-:>";
                        }
                    } else if (component.getPollData() == 1.0) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:true<:-:>";
                            value += "dpad_up:false<:-:>";
                            value += "dpad_right:false<:-:>";
                            value += "dpad_down:false<:-:>";
                        }
                    } else {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData())) {
                            value += "dpad_left:false<:-:>";
                            value += "dpad_up:false<:-:>";
                            value += "dpad_right:false<:-:>";
                            value += "dpad_down:false<:-:>";
                        }
                    }
                } else {
                    String name = null;
                    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                        switch (component.getIdentifier().toString()) {
                            case "0":
                                name = "a";
                                break;
                            case "1":
                                name = "b";
                                break;
                            case "2":
                                name = "x";
                                break;
                            case "3":
                                name = "y";
                                break;
                            case "4":
                                name = "left_bumper";
                                break;
                            case "5":
                                name = "right_bumper";
                                break;
                            case "6":
                                name = "back";
                                break;
                            case "7":
                                name = "start";
                                break;
                            case "8":
                                name = "left_stick_button";
                                break;
                            case "9":
                                name = "right_stick_button";
                                break;
                        }
                    } else {
                        switch (component.getIdentifier().toString()) {
                            case "0":
                                name = "a";
                                break;
                            case "1":
                                name = "b";
                                break;
                            case "2":
                                name = "x";
                                break;
                            case "3":
                                name = "y";
                                break;
                            case "4":
                                name = "left_bumper";
                                break;
                            case "5":
                                name = "right_bumper";
                                break;
                            case "6":
                                name = "left_stick_button";
                                break;
                            case "7":
                                name = "right_stick_button";
                                break;
                            case "8":
                                name = "start";
                                break;
                            case "9":
                                name = "back";
                                break;
                            case "10":
                                name = "mode";
                                break;
                            case "11":
                                name = "dpad_up";
                                break;
                            case "12":
                                name = "dpad_down";
                                break;
                            case "13":
                                name = "dpad_left";
                                break;
                            case "14":
                                name = "dpad_right";
                                break;
                        }
                    }

                    if (name != null) {
                        if (!(oldComponents.get(component.getName()) != null && oldComponents.get(component.getName()) == component.getPollData()))
                            value += name + ":" + (component.getPollData() == 1.0f) + "<:-:>";
                    }
                }
            }
        }

        if(value != "") value = value.substring(0, value.length() - 5);

        lastValues.put(gamepad, currentComponents);

        return value;
    }

    public synchronized boolean getStartGamepad1(int gamepad) {
        if(gamepad < 0 || gamepad >= controllers.size()) return false;

        Controller controller = controllers.get(gamepad);

        boolean starting = true;
        int counter = 0;

        controller.poll();

        for (Component component : controller.getComponents()) {
            if(System.getProperty("os.name").toLowerCase().contains("windows")) {
                if (component.getIdentifier().toString().equals("0") || component.getIdentifier().toString().equals("7")) {
                    counter++;
                    if (!(component.getPollData() == 1.0f)) starting = false;
                }
            } else {
                if (component.getIdentifier().toString().equals("0") || component.getIdentifier().toString().equals("8")) {
                    counter++;
                    if (!(component.getPollData() == 1.0f)) starting = false;
                }
            }
        }

        if(counter < 2) starting = false;

        return starting;
    }

    public synchronized boolean getStartGamepad2(int gamepad) {
        if(gamepad < 0 || gamepad >= controllers.size()) return false;

        Controller controller = controllers.get(gamepad);

        boolean starting = true;
        int counter = 0;

        controller.poll();


        for (Component component : controller.getComponents()) {
            if(System.getProperty("os.name").toLowerCase().contains("windows")) {
                if(component.getIdentifier().toString().equals("1") || component.getIdentifier().toString().equals("7")) {
                    counter++;
                    if (!(component.getPollData() == 1.0f)) starting = false;
                }
            } else {
                if(component.getIdentifier().toString().equals("1") || component.getIdentifier().toString().equals("8")) {
                    counter++;
                    if (!(component.getPollData() == 1.0f)) starting = false;
                }
            }
        }

        if(counter < 2) starting = false;

        return starting;
    }

    private static ControllerEnvironment createDefaultEnvironment() {

        try {

            // Find constructor (class is package private, so we can't access it directly)
            Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>)
                    Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

            // Constructor is package private, so we have to deactivate access control checks
            constructor.setAccessible(true);

            // Create object with default constructor
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isValidController(String name) {
        return (name != null && (name.toLowerCase().contains("f310") || name.toLowerCase().contains("xbox") || name.toLowerCase().contains("3d") || name.toLowerCase().contains("keyboard")));
    }
}
