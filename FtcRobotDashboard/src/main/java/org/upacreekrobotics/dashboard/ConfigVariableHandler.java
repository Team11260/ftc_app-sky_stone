package org.upacreekrobotics.dashboard;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.upacreekrobotics.classscanner.ClassFilter;
import org.upacreekrobotics.classscanner.ClasspathScanner;
import org.upacreekrobotics.classscanner.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConfigVariableHandler {

    private final Set<String> IGNORED_PACKAGES = new HashSet<>(Arrays.asList(
            "java",
            "android",
            "com.sun",
            "com.vuforia",
            "com.google",
            "kotlin"
    ));

    private final Map<String, Map<String, Field>> configVariables = new HashMap<>();

    public ConfigVariableHandler() {
        ClasspathScanner scanner = new ClasspathScanner(new ClassFilter() {
            @Override
            public boolean shouldProcessClass(String className) {
                for (String packageName : IGNORED_PACKAGES) {
                    if (className.startsWith(packageName)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void processClass(Class klass) {
                if (klass.isAnnotationPresent(Config.class) && !klass.isAnnotationPresent(Disabled.class)) {

                    HashMap<String, Field> klassVariables = new HashMap<>();

                    for (Field field : klass.getDeclaredFields()) {
                        try {
                            if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()) && (field.get(null) instanceof Integer || field.get(null) instanceof Double || field.get(null) instanceof String)) {
                                klassVariables.put(field.getName(), field);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    if (klassVariables.size() > 0) {
                        configVariables.put(klass.getSimpleName(), klassVariables);
                    }
                }
            }
        });
        scanner.scanClasspath();
    }

    public Map<String, Map<String, Field>> getConfigVariables() {
        return configVariables;
    }

    public void setConfigVariable(String className, String fieldName, String value) {
        try {
            Field field = configVariables.get(className).get(fieldName);
            Object currentFieldValue = field.get(null);
            if (currentFieldValue instanceof Integer) {
                field.set(null, Integer.valueOf(value));
            } else if (currentFieldValue instanceof Double) {
                field.set(null, Double.valueOf(value));
            } else {
                field.set(null, String.valueOf(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
