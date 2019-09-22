package org.upacreekrobotics.GUI;

import net.java.games.input.Controller;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class UI extends JFrame implements ActionListener {

    /**
     * main panel "panel" object
     */
    private JPanel panel;

    /**
     * "controlPanel" objects
     */
    private JPanel controlPanel;

    private JButton logreaderButton;
    private JButton smartdashboardButton;
    private JButton clearButton;
    private JButton saveButton;
    private JCheckBox autoscrollBox;
    private JCheckBox dateBox;
    private JCheckBox timeBox;
    private JCheckBox opmodetimeBox;
    private JCheckBox variableeditorBox;
    private JPanel connected;

    /**
     * "robotPanel" objects
     */
    private JPanel robotPanel;
    private JTextField batteryField;
    private JTextField phoneBatteryField;
    private JPanel opModePanel;
    private JScrollPane opModePane;
    private int opModeCount = 0;
    private ButtonGroup opModes;
    private boolean gotOpModes = false;
    private long lastOpModesRequest = 0;
    private int gotOpModesCount = 0;
    private ArrayList<JRadioButton> opModeButtons = new ArrayList();
    private JButton initButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton restartButton;
    private JTextField gamepad1Label;
    private JComboBox<String> gamepad1Menu;
    private JTextField gamepad2Label;
    private JComboBox<String> gamepad2Menu;
    private JButton gamepadUpdateButton;
    private JScrollPane infoScrollPane;
    private JTextPane infoTextPane;
    private PrintStream infoOut;

    /**
     * "centerPanel" objects
     */
    private JPanel centerPanel;
    private JPanel doublePanel;
    private JPanel inputPanel;
    private JTextField inputField;
    private JTextField displayField;
    private JButton submitButton;
    private JPanel variablePanel;
    private JComboBox<Object> variableclassBox;
    private JComboBox<Object> variablenameBox;
    private JTextField variableField;
    private JButton variablesubmitButton;
    private JTextPane pane;
    private PrintStream out;
    private JScrollPane scrollPane;
    private JTextPane telemetryDocument;
    private PrintStream telemOut;

    private HashMap<String, HashMap<String, Object>> variables;

    /**
     * other objects
     */
    private Date date;
    private OpModeSelector OpModes;
    private OpModeState currentOpModeState;
    public Thread dashboardThread;
    public GamepadThread gamepadThread;
    public Data data;

    /**
     * Background color of the UI
     */
    public static Color blueColor = new Color(36, 146, 208);
    public static Color greyColor = Color.DARK_GRAY;

    /**
     * JCheckbox variables
     */
    private boolean autoscroll = true;
    private boolean showdate = false;
    private boolean showtime = false;
    private boolean showopmodetime = true;

    /**
     * Smart Dashboard variables
     */
    private List<DashboardComponent> smartdashboardComponents = Collections.synchronizedList(new ArrayList<>());

    private GamepadHandler gamepadHandler;

    /**
     * Constructor calls main init "initUI
     */
    public UI() {
        System.setProperty("net.java.games.input.librarypath", ClassLoader.getSystemClassLoader().getResource("jinput-dx8_64.dll").getPath().replace("/jinput-dx8_64.dll", ""));

        UIManager UI = new UIManager();
        UI.put("OptionPane.background", blueColor);
        UI.put("Panel.background", blueColor);

        initUI();
    }

    /**
     * "initUI" calls init for all the parts of the UI
     */
    private void initUI() {

        setVisible(false);

        variables = new HashMap<>();

        date = new Date();
        OpModes = new OpModeSelector();
        currentOpModeState = OpModeState.NONE;

        gamepadHandler = new GamepadHandler();

        gamepadThread = new GamepadThread();
        gamepadThread.start();

        dashboardThread = new Thread(new DataHandler());
        dashboardThread.start();

        initPanel();

        initControlPanel();
        panel.add(controlPanel, BorderLayout.NORTH);

        initRobotPanel();
        panel.add(robotPanel, BorderLayout.EAST);

        initCenterPanel();
        panel.add(centerPanel);

        setVisible(true);
    }

    /**
     * "initPanel" init's the main panel
     */
    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.setBackground(blueColor);

        add(panel);

        setTitle("Up-A-Creek FTC Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setMinimumSize(new Dimension(600, 400));

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * "initControlPanel" init's the control panel,
     * includes save and clear buttons,
     * autoscroll and date checkboxs, and connected indicator
     */
    private void initControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Up-A-Creek FTC Robot Dashboard",
                TitledBorder.CENTER, TitledBorder.BELOW_TOP, new Font("Sans", Font.PLAIN, 40), Color.DARK_GRAY));
        controlPanel.setBackground(blueColor);

        logreaderButton = new JButton("Log Reader");
        logreaderButton.setActionCommand("logreader");
        logreaderButton.setToolTipText("Opens Robot Log Reader");
        logreaderButton.addActionListener(this);

        controlPanel.add(logreaderButton);

        smartdashboardButton = new JButton("Smart Dashboard");
        smartdashboardButton.setActionCommand("smartdashboard");
        smartdashboardButton.setToolTipText("Opens Smart Dashboard");
        smartdashboardButton.addActionListener(this);

        controlPanel.add(smartdashboardButton);

        saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.setToolTipText("Saves console output to file");
        saveButton.addActionListener(this);

        controlPanel.add(saveButton);

        clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        clearButton.setToolTipText("Clears console");
        clearButton.addActionListener(this);

        controlPanel.add(clearButton);

        autoscrollBox = new JCheckBox("Autoscroll");
        autoscrollBox.setBackground(blueColor);
        autoscrollBox.setActionCommand("autoscroll");
        autoscrollBox.addActionListener(this);
        autoscrollBox.setSelected(true);

        controlPanel.add(autoscrollBox);

        dateBox = new JCheckBox("Date");
        dateBox.setBackground(blueColor);
        dateBox.setActionCommand("date");
        dateBox.addActionListener(this);
        dateBox.setSelected(false);

        controlPanel.add(dateBox);

        timeBox = new JCheckBox("Time");
        timeBox.setBackground(blueColor);
        timeBox.setActionCommand("time");
        timeBox.addActionListener(this);
        timeBox.setSelected(false);

        controlPanel.add(timeBox);

        opmodetimeBox = new JCheckBox("OpMode Time");
        opmodetimeBox.setBackground(blueColor);
        opmodetimeBox.setActionCommand("opmodetime");
        opmodetimeBox.addActionListener(this);
        opmodetimeBox.setSelected(true);

        controlPanel.add(opmodetimeBox);

        variableeditorBox = new JCheckBox("Variable Editor");
        variableeditorBox.setBackground(blueColor);
        variableeditorBox.setActionCommand("variableeditor");
        variableeditorBox.addActionListener(this);
        variableeditorBox.setSelected(false);

        controlPanel.add(variableeditorBox);

        JTextPane text = new JTextPane();
        text.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        text.setText("Connected:");
        text.setEditable(false);
        text.setBackground(blueColor);

        controlPanel.add(text);

        connected = new JPanel();
        connected.setPreferredSize(new Dimension(20, 20));
        connected.setBackground(Color.red);

        controlPanel.add(connected);
    }

    /**
     * "initRobotPanel" init's the robot panel,
     * includes phone and robot battery indicators,
     * init, run, stop, and reset buttons,
     * and opMode selector
     */
    private void initRobotPanel() {
        robotPanel = new JPanel();
        robotPanel.setLayout(new BoxLayout(robotPanel, BoxLayout.PAGE_AXIS));
        robotPanel.setBackground(blueColor);

        batteryField = new JTextField();
        batteryField.setEditable(false);
        batteryField.setMaximumSize(new Dimension(350, 30));
        batteryField.setHorizontalAlignment(SwingConstants.CENTER);
        batteryField.setBackground(Color.red);

        robotPanel.add(batteryField);

        phoneBatteryField = new JTextField();
        phoneBatteryField.setEditable(false);
        phoneBatteryField.setMaximumSize(new Dimension(350, 30));
        phoneBatteryField.setHorizontalAlignment(SwingConstants.CENTER);
        phoneBatteryField.setBackground(Color.red);

        robotPanel.add(phoneBatteryField);

        initButton = new JButton("Init");
        initButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        initButton.setActionCommand("init");
        initButton.setToolTipText("Inits robot code");
        initButton.addActionListener(this);

        robotPanel.add(initButton);

        runButton = new JButton("Run");
        runButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        runButton.setActionCommand("run");
        runButton.setToolTipText("Runs robot code");
        runButton.addActionListener(this);

        robotPanel.add(runButton);

        stopButton = new JButton("Stop");
        stopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        stopButton.setActionCommand("stop");
        stopButton.setToolTipText("Stops robot code");
        stopButton.addActionListener(this);

        robotPanel.add(stopButton);

        restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setActionCommand("restart");
        restartButton.setToolTipText("Restarts the robot");
        restartButton.addActionListener(this);
        restartButton.setEnabled(false);

        robotPanel.add(restartButton);

        ArrayList<String> choices = new ArrayList<>();

        choices.add("1 - Default");

        int n = 2;

        for (Controller controller : gamepadHandler.getControllers()) {
            choices.add(n + " - " + controller.getName().replace("Wired ", "").replace("Apple Internal Keyboard / Trackpad", "Keyboard"));
            n++;
        }

        gamepad1Label = new JTextField("Gamepad 1:");
        gamepad1Label.setBackground(blueColor);
        gamepad1Label.setEditable(false);
        gamepad1Label.setBorder(BorderFactory.createEmptyBorder());
        gamepad1Label.setMaximumSize(new Dimension(500, 50));

        robotPanel.add(gamepad1Label);

        gamepad1Menu = new JComboBox(choices.toArray());
        gamepad1Menu.setMaximumSize(new Dimension(500, 50));
        gamepad1Menu.setActionCommand("gamepad1");
        gamepad1Menu.addActionListener(gamepadThread);

        robotPanel.add(gamepad1Menu);

        gamepad2Label = new JTextField("Gamepad 2:");
        gamepad2Label.setBackground(blueColor);
        gamepad2Label.setEditable(false);
        gamepad2Label.setBorder(BorderFactory.createEmptyBorder());
        gamepad2Label.setMaximumSize(new Dimension(500, 50));

        robotPanel.add(gamepad2Label);

        gamepad2Menu = new JComboBox(choices.toArray());
        gamepad2Menu.setMaximumSize(new Dimension(500, 50));
        gamepad2Menu.setActionCommand("gamepad2");
        gamepad2Menu.addActionListener(gamepadThread);

        robotPanel.add(gamepad2Menu);

        gamepadUpdateButton = new JButton("Refresh Gamepads");
        gamepadUpdateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamepadUpdateButton.setToolTipText("Updates available gamepad list");
        gamepadUpdateButton.setActionCommand("refresh");
        gamepadUpdateButton.addActionListener(gamepadThread);

        robotPanel.add(gamepadUpdateButton);

        opModes = new ButtonGroup();

        opModePanel = new JPanel();
        opModePanel.setLayout(new BoxLayout(opModePanel, BoxLayout.PAGE_AXIS));
        opModePanel.setBackground(blueColor);

        for (int i = 0; i < 100; i++) {
            JRadioButton modeButton = new JRadioButton("ZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
            opModeButtons.add(modeButton);
            modeButton.setBackground(blueColor);
            modeButton.setVisible(false);
            opModePanel.add(modeButton);
        }

        opModePane = new JScrollPane(opModePanel);
        opModePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        opModePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        opModePane.setAutoscrolls(true);
        opModePane.setPreferredSize(new Dimension(200, 100));

        robotPanel.add(opModePane);

        infoTextPane = new JTextPane();
        infoTextPane.setEditable(false);
        infoTextPane.setBackground(blueColor);

        infoOut = new PrintStream(new DocumentOutputStream(infoTextPane.getDocument()));

        infoScrollPane = new JScrollPane(infoTextPane);
        infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.setAutoscrolls(false);
        infoScrollPane.setPreferredSize(new Dimension(200, 100));

        robotPanel.add(infoScrollPane);
    }

    /**
     * "initCenterPanel" init's the center panel,
     * includes input and display textfields,
     * submit button,
     * and telemetry textpane
     */
    private void initCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(blueColor);

        doublePanel = new JPanel();
        doublePanel.setBackground(blueColor);

        inputPanel = new JPanel();
        inputPanel.setBackground(blueColor);

        displayField = new JTextField();
        displayField.setBackground(blueColor);
        displayField.setPreferredSize(new Dimension(400, 30));
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        displayField.setBorder(BorderFactory.createEmptyBorder());
        displayField.setEditable(false);
        displayField.setText("Input:");
        displayField.setVisible(false);

        inputPanel.add(displayField);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(100, 30));
        inputField.setVisible(false);

        inputPanel.add(inputField);

        submitButton = new JButton("Submit");
        submitButton.setEnabled(false);
        submitButton.setActionCommand("submit");
        submitButton.addActionListener(this);
        submitButton.setVisible(false);

        inputPanel.add(submitButton);

        doublePanel.add(inputPanel);

        variablePanel = new JPanel();
        variablePanel.setBackground(blueColor);

        variableclassBox = new JComboBox<>();
        variableclassBox.setActionCommand("variableclass");
        variableclassBox.addActionListener(this);
        variableclassBox.setVisible(false);

        variablePanel.add(variableclassBox);

        variablenameBox = new JComboBox<>();
        variablenameBox.setActionCommand("variablename");
        variablenameBox.addActionListener(this);
        variablenameBox.setVisible(false);

        variablePanel.add(variablenameBox);

        variableField = new JTextField();
        variableField.setPreferredSize(new Dimension(200, 30));
        variableField.setVisible(false);
        variableField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actionPerformed(new ActionEvent(variableField, 0, "variablefield"));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actionPerformed(new ActionEvent(variableField, 0, "variablefield"));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actionPerformed(new ActionEvent(variableField, 0, "variablefield"));
            }
        });

        variablePanel.add(variableField);

        variablesubmitButton = new JButton("Update");
        variablesubmitButton.setActionCommand("variablesubmit");
        variablesubmitButton.addActionListener(this);
        variablesubmitButton.setEnabled(false);
        variablesubmitButton.setVisible(false);

        variablePanel.add(variablesubmitButton);

        doublePanel.add(variablePanel);

        centerPanel.add(doublePanel, BorderLayout.NORTH);

        pane = new JTextPane();
        pane.setEditable(false);

        out = new PrintStream(new DocumentOutputStream(pane.getDocument()));

        scrollPane = new JScrollPane(pane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setAutoscrolls(true);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        centerPanel.add(scrollPane);

        telemetryDocument = new JTextPane();
        telemOut = new PrintStream(new DocumentOutputStream(telemetryDocument.getDocument()));
    }

    /**
     * "handleIncoming" responds to incoming socket messages
     */
    public void handleIncoming() {
        if (OpModes.getSelectedOpMode() != null) {
            switch (currentOpModeState) {
                case NONE:
                    initButton.setEnabled(true);
                    runButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    break;
                case INIT:
                    initButton.setEnabled(false);
                    runButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    break;
                case RUNNING:
                    initButton.setEnabled(false);
                    runButton.setEnabled(false);
                    stopButton.setEnabled(true);
                    break;
                case STOPPED:
                    initButton.setEnabled(false);
                    runButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    currentOpModeState = OpModeState.NONE;
                    break;
            }
        } else {
            initButton.setEnabled(false);
            runButton.setEnabled(false);
            stopButton.setEnabled(false);
        }

        if (data != null && data.isReady()) {
            Message incoming = data.read();
            if (incoming != null) {
                switch (incoming.getType()) {
                    case TELEMETRY:
                        print(incoming.getText());
                        break;
                    case LOG:
                        log(incoming.getText());
                        break;
                    case INFO:
                        info(incoming.getText());
                        break;
                    case OP_MODES:
                        addOpMode(incoming.getText());
                        break;
                    case VARIABLE:
                        addVariables(incoming.getText());
                        break;
                    case SELECT_OP_MODE:
                        for (JRadioButton button : opModeButtons)
                            if (button.getText().equals(incoming.getText())) {
                                OpModes.setOpMode(incoming.getText());
                                button.setSelected(true);
                                break;
                            }
                        break;
                    case ROBOT_STATUS:
                        if (incoming.getText().equals("INIT")) currentOpModeState = OpModeState.INIT;
                        if (incoming.getText().equals("RUNNING")) currentOpModeState = OpModeState.RUNNING;
                        if (incoming.getText().equals("STOPPED")) {
                            if (submitButton.isVisible()) {
                                submitButton.setVisible(false);
                                inputField.setVisible(false);
                                displayField.setVisible(false);
                                submitButton.setEnabled(false);
                                data.write(new Message(MessageType.RETURN_VALUE, ""));
                                inputField.setText("");
                                displayField.setText("Input:");
                            }

                            currentOpModeState = OpModeState.STOPPED;
                        }
                        break;
                    case GET_VALUE:
                        getRootPane().setDefaultButton(submitButton);
                        displayField.setText(incoming.getText() + ":");
                        submitButton.setEnabled(true);
                        displayField.setVisible(true);
                        inputField.setVisible(true);
                        submitButton.setVisible(true);
                        inputField.requestFocus();
                        break;
                    case BATTERY:
                        double votage = Double.valueOf(incoming.getText());
                        batteryField.setText("Robot Battery: " + incoming.getText() + "V");
                        if (votage >= 13) batteryField.setBackground(Color.GREEN);
                        else if (votage >= 12) batteryField.setBackground(Color.YELLOW);
                        else if (votage >= 11) batteryField.setBackground(Color.ORANGE);
                        else batteryField.setBackground(Color.red);
                        break;
                    case PHONE_BATTERY:
                        double percentage = Double.valueOf(incoming.getText());
                        phoneBatteryField.setText("Phone Battery: " + String.format("%.0f", Double.valueOf(incoming.getText())) + "%");
                        if (percentage >= 80) phoneBatteryField.setBackground(Color.GREEN);
                        else if (percentage >= 50) phoneBatteryField.setBackground(Color.YELLOW);
                        else if (percentage >= 20) phoneBatteryField.setBackground(Color.ORANGE);
                        else phoneBatteryField.setBackground(Color.red);
                        break;
                    case RESTART_ROBOT:
                        restartButton.setEnabled(true);
                        break;
                    case SMARTDASHBOARD_PUT: {
                        String[] parts = incoming.getText().split("<&#%#&>");

                        smartdashboardswitch:
                        switch (parts[0]) {
                            case "VALUE": {
                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            ((JTextField) component.getComponent()).setText(parts[1] + ": " + parts[2]);
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                JTextField text = new JTextField(parts[1] + ": " + parts[2]);
                                text.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                                text.setBounds(0, 0, 100, 50);
                                text.setBackground(blueColor);
                                text.setHorizontalAlignment(JTextField.CENTER);
                                text.setEditable(false);
                                text.setHighlighter(null);

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], text));
                                break;
                            }
                            case "BOOLEAN": {
                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            if (Boolean.valueOf(parts[2]))
                                                component.getComponent().setBackground(Color.green);
                                            else component.getComponent().setBackground(Color.red);
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                BooleanField display = new BooleanField(parts[1]);
                                display.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                                display.setBounds(0, 0, 100, 50);
                                if (Boolean.valueOf(parts[2])) display.setBackground(Color.green);
                                else display.setBackground(Color.red);
                                display.setHorizontalAlignment(JTextField.CENTER);
                                display.setEditable(false);
                                display.setHighlighter(null);

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], display));
                                break;
                            }
                            case "BUTTON": {
                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            ((JButton) component.getComponent()).setText(parts[1]);
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                JButton button = new JButton(parts[1]);
                                button.setBounds(0, 0, 100, 50);
                                button.setBackground(blueColor);
                                button.setHorizontalAlignment(JTextField.CENTER);

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], button));
                                break;
                            }
                            case "INPUT": {
                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                JPanel panel = new JPanel();
                                panel.setLayout(new BorderLayout(8, 8));
                                panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
                                panel.setBackground(blueColor);
                                panel.setBounds(0, 0, 100, 50);

                                JTextField inputDisplay = new JTextField(parts[1] + ":");
                                inputDisplay.setBorder(BorderFactory.createEmptyBorder());
                                inputDisplay.setBackground(blueColor);
                                inputDisplay.setHorizontalAlignment(JTextField.RIGHT);
                                inputDisplay.setEditable(false);
                                inputDisplay.setHighlighter(null);

                                panel.add(inputDisplay, BorderLayout.WEST);

                                JTextField input = new JTextField();

                                panel.add(input, BorderLayout.CENTER);

                                JPanel emptyPanel = new JPanel();
                                emptyPanel.setBackground(blueColor);

                                panel.add(emptyPanel, BorderLayout.NORTH);

                                emptyPanel = new JPanel();
                                emptyPanel.setBackground(blueColor);

                                panel.add(emptyPanel, BorderLayout.SOUTH);

                                panel.setVisible(true);

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], panel));
                                break;
                            }
                            case "SLIDER": {
                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            ((SliderPanel) component.getComponent()).setRange(Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                SliderPanel slider = new SliderPanel(parts[1], Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], slider));
                                break;
                            }
                            case "GRAPH": {

                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            ((GraphPanel) component.getComponent()).addDataPoint(parts[2], Double.valueOf(parts[3]), Double.valueOf(parts[4]));
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                GraphPanel graph = new GraphPanel(parts[1]);

                                graph.addDataPoint(parts[2], Double.valueOf(parts[3]), Double.valueOf(parts[4]));

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], graph));
                                break;
                            }
                            case "GRAPH_POINT": {

                                parts[0] = "GRAPH";

                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            ((GraphPanel) component.getComponent()).addPoint(parts[2], Double.valueOf(parts[3]), Double.valueOf(parts[4]));
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                GraphPanel graph = new GraphPanel(parts[1]);

                                graph.addDataPoint(parts[2], Double.valueOf(parts[3]), Double.valueOf(parts[4]));

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], graph));
                                break;
                            }
                            case "GRAPH_CIRCLE": {
                                parts[0] = "GRAPH";

                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            ((GraphPanel) component.getComponent()).addCircle(parts[2], Double.valueOf(parts[3]), Double.valueOf(parts[4]), Double.valueOf(parts[5]));
                                            break smartdashboardswitch;
                                        }
                                    }
                                }

                                GraphPanel graph = new GraphPanel(parts[1]);

                                graph.addCircle(parts[2], Double.valueOf(parts[3]), Double.valueOf(parts[4]), Double.valueOf(parts[5]));

                                smartdashboardComponents.add(new DashboardComponent(parts[0], parts[1], graph));
                                break;
                            }
                            case "GRAPH_CLEAR": {
                                parts[0] = "GRAPH";

                                synchronized (smartdashboardComponents) {
                                    for (DashboardComponent component : smartdashboardComponents) {
                                        if (component.getType().equals(parts[0]) && component.getName().equals(parts[1])) {
                                            ((GraphPanel) component.getComponent()).clearSeries(parts[2]);
                                            break smartdashboardswitch;
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case SMARTDASHBOARD_GET: {
                        String[] parts = incoming.getText().split("<&#%#&>");

                        smartdashboardswitch:
                        switch (parts[1]) {
                            case "VALUE":
                                for (DashboardComponent component : smartdashboardComponents) {
                                    if (component.getType().equals(parts[1]) && component.getName().equals(parts[2])) {
                                        data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + ((JTextField) component.getComponent()).getText().split(": ")[1]));
                                        break smartdashboardswitch;
                                    }
                                }
                                data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + null));
                                break;
                            case "BOOLEAN":
                                for (DashboardComponent component : smartdashboardComponents) {
                                    if (component.getType().equals(parts[1]) && component.getName().equals(parts[2])) {
                                        if (component.getComponent().getBackground().equals(Color.green))
                                            data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>true"));
                                        else
                                            data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>false"));
                                        break smartdashboardswitch;
                                    }
                                }
                                data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + null));
                                break;
                            case "BUTTON":
                                for (DashboardComponent component : smartdashboardComponents) {
                                    if (component.getType().equals(parts[1]) && component.getName().equals(parts[2])) {
                                        data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + ((JButton) component.getComponent()).getModel().isPressed()));
                                        break smartdashboardswitch;
                                    }
                                }
                                data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + null));
                                break;
                            case "INPUT":
                                for (DashboardComponent component : smartdashboardComponents) {
                                    if (component.getType().equals(parts[1]) && component.getName().equals(parts[2])) {
                                        if (((JTextField) (((JPanel) component.getComponent()).getComponent(1))).getText().equals(""))
                                            data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + null));
                                        else
                                            data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + ((JTextField) (((JPanel) component.getComponent()).getComponent(1))).getText()));
                                        break smartdashboardswitch;
                                    }
                                }
                                data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + null));
                                break;
                            case "SLIDER":
                                for (DashboardComponent component : smartdashboardComponents) {
                                    if (component.getType().equals(parts[1]) && component.getName().equals(parts[2])) {
                                        data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + ((SliderPanel) component.getComponent()).getSlider().getValue()));
                                        break smartdashboardswitch;
                                    }
                                }
                                data.write(new Message(MessageType.SMARTDASHBOARD_GET, parts[0] + "<&#%#&>" + null));
                                break;
                        }
                        break;
                    }
                    case HAND_SHAKE:
                        break;
                    default:
                        System.err.println("Unknown Command:" + incoming.getType() + " : " + incoming.getText());
                        break;
                }
            }
        }
    }

    /**
     * "logPrint" displays text to the telemetry textpane, called by "handleIncoming"
     */
    public void print(String text) {
        try {
            if (text != null) {
                //System.out.println(text);
                String[] parts = null;
                String message = "";
                try {
                    boolean addColon = false;

                    parts = text.split("`");

                    if (showdate) {
                        message += parts[0];
                        addColon = true;
                    }
                    if (showtime) {
                        message += parts[0].split(" ", 2)[1];
                        addColon = true;
                    }
                    if (showopmodetime) {
                        if (addColon) message += " ";
                        if (!parts[1].equals(" ")) {
                            message += parts[1];
                            addColon = true;
                        }
                    }
                    if (addColon) message += ": ";
                    message += parts[2];

                    out.println(message);

                    telemOut.println(parts[0] + "%&&%&&%" + parts[1] + "%&&%&&%" + parts[2]);

                    if (autoscroll) pane.setCaretPosition(pane.getDocument().getLength());
                } catch (ArrayIndexOutOfBoundsException e) {
                    if (autoscroll) pane.setCaretPosition(pane.getDocument().getLength());
                }
            }
        } catch (NullPointerException e) {
        }
    }

    /**
     * "log" saves data and writes to file when save button is pressed, called by "handleIncoming"
     */
    public void log(String text) {
        try {
            if (text != null) {
                try {
                    String[] parts = text.split("`", 2);
                    telemOut.println(parts[0] + ": " + parts[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    telemOut.println(text);
                }
            }
        } catch (NullPointerException e) {
        }
    }

    public void info(String text) {
        infoTextPane.setText(text.replace("!#@$", "\n"));
    }

    /**
     * "writeFile" writes all stored data to a new file in Documents/FTC/FTC RobotLogs/NewLogs/ folder
     */
    public void writeFile() {
        File file;
        int i = 0;

        String[] selections = {"Cancel", "Enter"};

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
        fileChooser.setFileFilter(filter);
        fileChooser.showSaveDialog(this);

        if (fileChooser.getSelectedFile() == null) return;

        Path path = Paths.get(fileChooser.getSelectedFile().getPath().replace(fileChooser.getSelectedFile().getName(), ""));
        String name = fileChooser.getSelectedFile().getName();

        while (true) {
            if (i == 0) file = new File(path.toString(), "FTC Robot Log " + name + " [" + date.getDate() + "].txt");
            else file = new File(path.toString(), "FTC Robot Log " + name + " [" + date.getDate() + "](" + i + ").txt");
            if (!file.exists()) {
                break;
            }
            i++;
        }

        try {
            Files.createFile(file.toPath());
            Files.write(file.toPath(), telemetryDocument.getDocument().getText(0, telemetryDocument.getDocument().getLength()).getBytes());
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("File write error");
        } catch (BadLocationException e) {
            System.err.println("File path not found");
        }
    }

    /**
     * "addOpModes" adds opMode "name" to opMode selector and "opModeButtons" ArrayList, called by "handleIncoming"
     */
    public void addOpMode(String name) {
        gotOpModes = true;
        gotOpModesCount = 0;
        for (JRadioButton button : opModeButtons) if (button.getText().equals(name)) return;
        opModes.add(opModeButtons.get(opModeCount));
        opModeButtons.get(opModeCount).setText(name);
        opModeButtons.get(opModeCount).setActionCommand(name);
        opModeButtons.get(opModeCount).addActionListener(OpModes);
        opModeButtons.get(opModeCount).setVisible(true);

        ArrayList<String> sortedButtons = new ArrayList<>();

        for (JRadioButton button : opModeButtons) {
            sortedButtons.add(button.getText());
        }

        sortedButtons.sort(String::compareTo);

        for (int i = 0; i < sortedButtons.size(); i++) {
            opModeButtons.get(i).setText(sortedButtons.get(i));
            opModeButtons.get(i).setActionCommand(sortedButtons.get(i));
        }

        opModeCount++;
    }

    public void addVariables(String line) {
        HashMap<String, Object> klassVariables = new HashMap<>();

        String[] parts = line.split("<&#%#&>");

        for(String part:parts) {
            if(part.contains(">#&%&#<")) {
                String[] p = part.split(">#&%&#<");
                switch (p[1]) {
                    case "int":
                        klassVariables.put(p[0], Integer.valueOf(p[2]));
                        break;
                    case "double":
                        klassVariables.put(p[0], Double.valueOf(p[2]));
                        break;
                    case "class java.lang.String":
                        klassVariables.put(p[0], p[2]);
                        break;
                }
            }
        }

        variables.put(parts[0], klassVariables);

        ArrayList<String> choices = new ArrayList<>(variables.keySet());
        Collections.sort(choices);
        variableclassBox.setModel(new DefaultComboBoxModel<>(choices.toArray()));

        choices = new ArrayList<>(variables.get(variableclassBox.getSelectedItem()).keySet());
        Collections.sort(choices);
        variablenameBox.setModel(new DefaultComboBoxModel<>(choices.toArray()));

        variableField.setText(variables.get(String.valueOf(variableclassBox.getSelectedItem())).get(variablenameBox.getSelectedItem()).toString());
    }

    /**
     * "actionPerformed" is called whenever an action occurs on the UI
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "logreader":
                new LogReaderHandler();
                break;
            case "smartdashboard":
                new SmartDashboard();
                break;
            case "clear":
                pane.setText("");
                telemetryDocument.setText("");
                infoTextPane.setText("");
                break;
            case "autoscroll":
                autoscroll = !autoscroll;
                break;
            case "save":
                writeFile();
                break;
            case "date":
                if (dateBox.isSelected() && timeBox.isSelected()) timeBox.setSelected(false);
                showtime = timeBox.isSelected();
                showdate = dateBox.isSelected();
                break;
            case "time":
                if (timeBox.isSelected() && dateBox.isSelected()) dateBox.setSelected(false);
                showdate = dateBox.isSelected();
                showtime = timeBox.isSelected();
                break;
            case "opmodetime":
                if (timeBox.isSelected() && dateBox.isSelected()) dateBox.setSelected(false);
                showopmodetime = opmodetimeBox.isSelected();
                break;
            case "variableeditor":
                if(variableeditorBox.isSelected()) getRootPane().setDefaultButton(variablesubmitButton);
                variableclassBox.setVisible(variableeditorBox.isSelected());
                variablenameBox.setVisible(variableeditorBox.isSelected());
                variableField.setVisible(variableeditorBox.isSelected());
                variablesubmitButton.setVisible(variableeditorBox.isSelected());
                break;
            case "init":
                data.write(new Message(MessageType.SELECT_OP_MODE, OpModes.getSelectedOpMode()));
                data.write(new Message(MessageType.INIT_OP_MODE, "Hi"));
                currentOpModeState = OpModeState.INIT;
                break;
            case "run":
                data.write(new Message(MessageType.RUN_OP_MODE, "Hi"));
                currentOpModeState = OpModeState.RUNNING;
                break;
            case "stop":
                infoTextPane.setText("");
                if (submitButton.isEnabled()) {
                    submitButton.setVisible(false);
                    inputField.setVisible(false);
                    displayField.setVisible(false);
                    submitButton.setEnabled(false);
                    data.write(new Message(MessageType.RETURN_VALUE, "0"));
                    inputField.setText("");
                    displayField.setText("Input:");
                }
                data.write(new Message(MessageType.STOP_OP_MODE, "Hi"));
                currentOpModeState = OpModeState.STOPPED;
                break;
            case "restart":
                infoTextPane.setText("");
                restartButton.setEnabled(false);
                if (submitButton.isEnabled()) {
                    submitButton.setVisible(false);
                    inputField.setVisible(false);
                    displayField.setVisible(false);
                    submitButton.setEnabled(false);
                    data.write(new Message(MessageType.RETURN_VALUE, "0").getMessage());
                    inputField.setText("");
                    displayField.setText("Input:");
                }
                OpModes.clearSelected();
                opModes.clearSelection();
                currentOpModeState = OpModeState.NONE;
                initButton.setEnabled(false);
                runButton.setEnabled(false);
                stopButton.setEnabled(false);
                data.write(new Message(MessageType.RESTART_ROBOT, "Hi"));
                break;
            case "submit":
                submitButton.setVisible(false);
                inputField.setVisible(false);
                displayField.setVisible(false);
                submitButton.setEnabled(false);
                data.write(new Message(MessageType.RETURN_VALUE, inputField.getText()).getMessage());
                inputField.setText("");
                displayField.setText("Input:");
                break;
            case "variableclass":
                ArrayList<String> choices = new ArrayList<>(variables.get(variableclassBox.getSelectedItem()).keySet());
                Collections.sort(choices);
                variablenameBox.setModel(new DefaultComboBoxModel<>(choices.toArray()));

                variableField.setText(variables.get(variableclassBox.getSelectedItem()).get(variablenameBox.getSelectedItem()).toString());
                break;
            case "variablename":
                variableField.setText(variables.get(variableclassBox.getSelectedItem()).get(variablenameBox.getSelectedItem()).toString());
                break;
            case "variablesubmit":
                data.write(new Message(MessageType.VARIABLE, variableclassBox.getSelectedItem() + "<&#%#&>" + variablenameBox.getSelectedItem() + ">#&%&#<" + variableField.getText()));
                if(variables.get(variableclassBox.getSelectedItem()).get(variablenameBox.getSelectedItem()) instanceof Integer) {
                    variables.get(variableclassBox.getSelectedItem()).put(variablenameBox.getSelectedItem().toString(), Integer.valueOf(variableField.getText()));
                } else if (variables.get(variableclassBox.getSelectedItem()).get(variablenameBox.getSelectedItem()) instanceof Double) {
                    variables.get(variableclassBox.getSelectedItem()).put(variablenameBox.getSelectedItem().toString(), Double.valueOf(variableField.getText()));
                } else {
                    variables.get(variableclassBox.getSelectedItem()).put(variablenameBox.getSelectedItem().toString(), variableField.getText());
                }

                variableField.setText(variables.get(variableclassBox.getSelectedItem()).get(variablenameBox.getSelectedItem()).toString());
                break;
            case "variablefield":
                try {
                    if(variables.get(variableclassBox.getSelectedItem()).get(variablenameBox.getSelectedItem()) instanceof Integer) {
                        Integer.valueOf(variableField.getText());
                    } else if (variables.get(variableclassBox.getSelectedItem()).get(variablenameBox.getSelectedItem()) instanceof Double) {
                        Double.valueOf(variableField.getText());
                    }
                    variablesubmitButton.setEnabled(true);
                } catch (Exception ee) {
                    variablesubmitButton.setEnabled(false);
                }
                break;
        }
    }

    /**
     * "DataHandler" creates and maintains a socket connection with the robot controller
     */
    public class DataHandler implements Runnable {

        public boolean running = true;

        DataHandler() {

        }

        public void run() {
            data = new Data();
            while (running) {

                if (!gotOpModes && data != null && System.currentTimeMillis() - lastOpModesRequest > 1500) {
                    data.write(new Message(MessageType.GET_OP_MODES, "Hi"));
                    lastOpModesRequest = System.currentTimeMillis();
                    if (gotOpModesCount >= 3) {
                        data = new Data();
                        gotOpModes = false;
                        gotOpModesCount = 0;
                    }
                    gotOpModesCount++;
                }
                if (connected != null) {
                    if (data.isConnected()) {
                        if (connected.getBackground() == Color.red) {
                            connected.setBackground(Color.GREEN);
                            data.write(new Message(MessageType.GET_OP_MODES, "Hi"));
                            if (restartButton != null) restartButton.setEnabled(true);
                        }
                    } else {
                        if (connected.getBackground() == Color.GREEN && isVisible()) {
                            infoTextPane.setText("");
                            if (submitButton.isEnabled()) {
                                submitButton.setVisible(false);
                                inputField.setVisible(false);
                                displayField.setVisible(false);
                                submitButton.setEnabled(false);
                                data.write(new Message(MessageType.RETURN_VALUE, inputField.getText()).getMessage());
                                inputField.setText("");
                                displayField.setText("Input:");
                            }
                            phoneBatteryField.setBackground(Color.red);
                            phoneBatteryField.setText("");
                            batteryField.setBackground(Color.red);
                            batteryField.setText("");
                            OpModes.clearSelected();
                            connected.setBackground(Color.red);
                            initButton.setEnabled(false);
                            runButton.setEnabled(false);
                            stopButton.setEnabled(false);
                            restartButton.setEnabled(false);
                            for (JRadioButton button : opModeButtons) {
                                button.setText("ZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
                            }
                            opModes.clearSelection();
                            opModeCount = 0;
                            currentOpModeState = OpModeState.NONE;
                            variables = new HashMap<>();
                            variableclassBox.setModel(new DefaultComboBoxModel<>());
                            variablenameBox.setModel(new DefaultComboBoxModel<>());
                            variableField.setText("");
                            for (JRadioButton button : opModeButtons) {
                                button.setVisible(false);
                            }
                        }
                        data = new Data();
                        gotOpModes = false;
                        continue;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }

        public void end() {
            running = false;
        }
    }

    public class GamepadThread implements Runnable, ActionListener {

        private Thread thread;
        private int gamepad1 = -1;
        private int gamepad2 = -1;

        public GamepadThread() {
            thread = new Thread(this);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (gamepad1 != -1) {
                    String state = gamepadHandler.getGamepadState(gamepad1);
                    if (state == null) {
                        ActionEvent e = new ActionEvent(this, 0, "refresh");
                        actionPerformed(e);
                    } else if (data != null && !state.equals("")) {
                        data.write(new Message(MessageType.GAMEPAD_1_SET, state));
                    }
                }
                if (gamepad2 != -1) {
                    String state = gamepadHandler.getGamepadState(gamepad2);
                    if (state == null) {
                        ActionEvent e = new ActionEvent(this, 0, "refresh");
                        actionPerformed(e);
                    }
                    if (data != null && !state.equals("")) {
                        data.write(new Message(MessageType.GAMEPAD_2_SET, state));
                    }
                }

                for (Controller controller : gamepadHandler.getControllers()) {
                    if (gamepad1Menu != null && gamepadHandler.getStartGamepad1(gamepadHandler.getControllers().indexOf(controller))) {
                        gamepad1Menu.setSelectedIndex(gamepadHandler.getControllers().indexOf(controller) + 1);
                        if (gamepad1Menu.getSelectedIndex() == gamepad2Menu.getSelectedIndex()) {
                            gamepad2Menu.setSelectedIndex(0);
                            if (data != null) data.write(new Message(MessageType.GAMEPAD_2_SET, "default"));
                        }
                    }
                    if (gamepad2Menu != null && gamepadHandler.getStartGamepad2(gamepadHandler.getControllers().indexOf(controller))) {
                        gamepad2Menu.setSelectedIndex(gamepadHandler.getControllers().indexOf(controller) + 1);
                        if (gamepad1Menu.getSelectedIndex() == gamepad2Menu.getSelectedIndex()) {
                            gamepad1Menu.setSelectedIndex(0);
                            if (data != null) data.write(new Message(MessageType.GAMEPAD_1_SET, "default"));
                        }
                    }
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }

        public void start() {
            thread.start();
        }

        public void interrupt() {
            thread.interrupt();
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals("gamepad1")) {
                gamepad1 = gamepad1Menu.getSelectedIndex() - 1;

                if (gamepad1Menu.getSelectedIndex() != 0 && gamepad1Menu.getSelectedIndex() == gamepad2Menu.getSelectedIndex()) {
                    gamepad2Menu.setSelectedIndex(0);
                    if (data != null) data.write(new Message(MessageType.GAMEPAD_2_SET, "default"));
                }

                if (gamepad1 == -1) {
                    if (data != null) data.write(new Message(MessageType.GAMEPAD_1_SET, "default"));
                }
            } else if (e.getActionCommand().equals("gamepad2")) {
                gamepad2 = gamepad2Menu.getSelectedIndex() - 1;

                if (gamepad2Menu.getSelectedIndex() != 0 && gamepad1Menu.getSelectedIndex() == gamepad2Menu.getSelectedIndex()) {
                    gamepad1Menu.setSelectedIndex(0);
                    if (data != null) data.write(new Message(MessageType.GAMEPAD_1_SET, "default"));
                }

                if (gamepad2 == -1) {
                    if (data != null) data.write(new Message(MessageType.GAMEPAD_2_SET, "default"));
                }
            } else if (e.getActionCommand().equals("refresh")) {
                gamepadHandler.updateControllers();

                ArrayList<String> choices = new ArrayList<>();

                choices.add("1 - Default");

                int n = 2;

                for (Controller controller : gamepadHandler.getControllers()) {
                    choices.add(n + " - " + controller.getName().replace("Wired ", "").replace("Apple Internal Keyboard / Trackpad", "Keyboard"));
                    n++;
                }

                DefaultComboBoxModel comboBoxModel1 = new DefaultComboBoxModel(choices.toArray());
                DefaultComboBoxModel comboBoxModel2 = new DefaultComboBoxModel(choices.toArray());

                gamepad1 = -1;
                gamepad2 = -1;

                if (gamepad1Menu != null) {
                    gamepad1Menu.setModel(comboBoxModel1);
                    gamepad1Menu.setSelectedIndex(0);
                }
                if (gamepad2Menu != null) {
                    gamepad2Menu.setModel(comboBoxModel2);
                    gamepad2Menu.setSelectedIndex(0);
                }
            }
        }
    }

    public class LogReaderHandler extends JFrame implements Runnable, ActionListener {

        private JPanel logPanel;
        private JPanel logControlPanel;
        private JTextPane logPane;
        private PrintStream logOut;
        private JScrollPane logScrollPane;
        private File currentFile;
        private ArrayList<String> logText = new ArrayList<>();

        /**
         * Controls
         */
        private JButton logNewfileButton;
        private JButton logGraphButton;
        private JCheckBox logShowdateBox;
        private JCheckBox logShowtimeBox;
        private JCheckBox logOpmodetimeBox;
        private JTextField logDisplaysearchField;
        private JTextField logSearchField;
        private JTextField logSearchoccurrenceField;
        private JCheckBox logMatchcaseBox;
        private JCheckBox logShowmatchingBox;
        private int logHighlightNumber = -1;
        private int logTotalHighlightNumber = 0;

        public LogReaderHandler() {
            Thread logThread = new Thread(this);
            logThread.start();
        }

        @Override
        public void run() {
            initLogReader();

            while (this.isShowing()) {
            }

            this.setVisible(false);
        }

        private synchronized void initLogReader() {
            setTitle("Up-A-Creek FTC Log Reader");
            setLocationRelativeTo(null);
            setMinimumSize(new Dimension(600, 400));
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            logPanel = new JPanel();
            logPanel.setLayout(new BorderLayout(10, 10));
            logPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15),
                    "Up-A-Creek FTC Robot Log Reader", TitledBorder.CENTER, TitledBorder.BELOW_TOP,
                    new Font("Sans", Font.PLAIN, 40), Color.DARK_GRAY));
            logPanel.setBackground(blueColor);
            logPanel.setVisible(true);

            initLogControlPanel();

            initLogDisplayPanel();

            addKeyListener(keyboard);

            add(logPanel);

            setVisible(true);

            try {
                logText.clear();
                for (String line : telemetryDocument.getDocument().getText(0, telemetryDocument.getDocument().getLength()).split("\n")) {
                    logText.add(line);
                }
                logPrint();
            } catch (BadLocationException e) {
            }
        }

        private synchronized void initLogControlPanel() {
            logControlPanel = new JPanel();
            logControlPanel.setLayout(new BoxLayout(logControlPanel, BoxLayout.PAGE_AXIS));
            logControlPanel.setBackground(blueColor);

            logNewfileButton = new JButton("Load File");
            logNewfileButton.setToolTipText("Load a new file");
            logNewfileButton.addActionListener(this);
            logNewfileButton.setActionCommand("newfile");
            logNewfileButton.setVisible(true);

            logControlPanel.add(logNewfileButton);

            logGraphButton = new JButton("Graph Data");
            logGraphButton.setToolTipText("Create a graph of data");
            logGraphButton.addActionListener(this);
            logGraphButton.setActionCommand("graph");
            logGraphButton.setVisible(true);

            logControlPanel.add(logGraphButton);

            logShowdateBox = new JCheckBox("Show Date");
            logShowdateBox.setBackground(blueColor);
            logShowdateBox.setToolTipText("Show date for each line");
            logShowdateBox.addActionListener(this);
            logShowdateBox.setActionCommand("showdate");
            logShowdateBox.setVisible(true);

            logControlPanel.add(logShowdateBox);

            logShowtimeBox = new JCheckBox("Show Time");
            logShowtimeBox.setBackground(blueColor);
            logShowtimeBox.setToolTipText("Show time for each line");
            logShowtimeBox.addActionListener(this);
            logShowtimeBox.setActionCommand("showtime");
            logShowtimeBox.setVisible(true);

            logControlPanel.add(logShowtimeBox);

            logOpmodetimeBox = new JCheckBox("Show OpMode Time");
            logOpmodetimeBox.setBackground(blueColor);
            logOpmodetimeBox.setToolTipText("Show OpMode time for each line");
            logOpmodetimeBox.addActionListener(this);
            logOpmodetimeBox.setActionCommand("showopmodetime");
            logOpmodetimeBox.setVisible(true);

            logControlPanel.add(logOpmodetimeBox);

            logDisplaysearchField = new JTextField();
            logDisplaysearchField.setMaximumSize(new Dimension(400, 30));
            logDisplaysearchField.setBorder(BorderFactory.createEmptyBorder());
            logDisplaysearchField.setBackground(blueColor);
            logDisplaysearchField.setHorizontalAlignment(SwingConstants.CENTER);
            logDisplaysearchField.setText("Search:");
            logDisplaysearchField.setEditable(false);

            logControlPanel.add(logDisplaysearchField);

            logSearchField = new JTextField();
            logSearchField.setMaximumSize(new Dimension(400, 30));
            logSearchField.setHorizontalAlignment(SwingConstants.CENTER);
            logSearchField.setEditable(true);
            logSearchField.setToolTipText("Search Log");
            logSearchField.addActionListener(this);
            logSearchField.setActionCommand("search");
            logSearchField.setText("");
            logSearchField.addKeyListener(keyboard);

            logControlPanel.add(logSearchField);

            logSearchoccurrenceField = new JTextField();
            logSearchoccurrenceField.setMaximumSize(new Dimension(400, 30));
            logSearchoccurrenceField.setBorder(BorderFactory.createEmptyBorder());
            logSearchoccurrenceField.setBackground(blueColor);
            logSearchoccurrenceField.setHorizontalAlignment(SwingConstants.CENTER);
            logSearchoccurrenceField.setText("");
            logSearchoccurrenceField.setEditable(false);

            logControlPanel.add(logSearchoccurrenceField);

            logMatchcaseBox = new JCheckBox("Match Case");
            logMatchcaseBox.setBackground(blueColor);
            logMatchcaseBox.setToolTipText("Match case in search");
            logMatchcaseBox.addActionListener(this);
            logMatchcaseBox.setActionCommand("matchcase");
            logMatchcaseBox.setVisible(true);
            logMatchcaseBox.addKeyListener(keyboard);

            logControlPanel.add(logMatchcaseBox);

            logShowmatchingBox = new JCheckBox("Show Matching");
            logShowmatchingBox.setBackground(blueColor);
            logShowmatchingBox.setToolTipText("Only show lines that match search");
            logShowmatchingBox.addActionListener(this);
            logShowmatchingBox.setActionCommand("showmatching");
            logShowmatchingBox.setVisible(true);
            logShowmatchingBox.addKeyListener(keyboard);

            logControlPanel.add(logShowmatchingBox);

            logPanel.add(logControlPanel, BorderLayout.EAST);
        }

        private synchronized void initLogDisplayPanel() {
            logPane = new JTextPane();
            logPane.setEditable(false);

            logPane.addKeyListener(keyboard);

            logOut = new PrintStream(new DocumentOutputStream(logPane.getDocument()));

            logScrollPane = new JScrollPane(logPane);
            logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            logScrollPane.setAutoscrolls(true);
            logScrollPane.setPreferredSize(new Dimension(500, 300));

            logPanel.add(logScrollPane);
        }

        private synchronized void logPrint() {
            logPane.setText("");
            for (String line : logText) {
                try {
                    String[] parts = line.split("%&&%&&%");
                    String message = "";
                    if (logShowdateBox.isSelected()) message += parts[0];
                    if (logShowtimeBox.isSelected()) message += parts[0].split(" ")[1];
                    if (logOpmodetimeBox.isSelected()) message += (message != "") ? ": " + parts[1] : parts[1];
                    if (message != "") message += ": ";
                    message += parts[2];
                    if (logShowmatchingBox.isSelected()) {
                        if (logMatchcaseBox.isSelected()) {
                            if (!message.contains(logSearchField.getText())) continue;
                        } else {
                            if (!message.toLowerCase().contains(logSearchField.getText().toLowerCase())) continue;
                        }
                    }
                    logOut.println(message);
                } catch (ArrayIndexOutOfBoundsException eee) {
                }
            }
            updateHighlights();
        }

        private synchronized void startHighlight(boolean down) {
            Highlighter highlighter = logPane.getHighlighter();
            highlighter.removeAllHighlights();
            try {
                if (logSearchField.getText().length() > 0) {
                    Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    int p0 = 0;
                    int p1 = 0;
                    int h = 0;
                    if (logMatchcaseBox.isSelected()) {
                        while (logPane.getText().indexOf(logSearchField.getText(), p1) != -1) {
                            p0 = logPane.getText().indexOf(logSearchField.getText(), p1);
                            p1 = p0 + logSearchField.getText().length();
                            highlighter.addHighlight(p0, p1, painter);
                            h++;
                        }
                    } else {
                        while (logPane.getText().toLowerCase().indexOf(logSearchField.getText().toLowerCase(), p1) != -1) {
                            p0 = logPane.getText().toLowerCase().indexOf(logSearchField.getText().toLowerCase(), p1);
                            p1 = p0 + logSearchField.getText().length();
                            highlighter.addHighlight(p0, p1, painter);
                            h++;
                        }
                    }
                    logTotalHighlightNumber = h;
                    Highlighter.Highlight[] highlights = highlighter.getHighlights();
                    for (h = 0; h < highlights.length; h++) {
                        if (highlights[h].getStartOffset() > logPane.getCaretPosition()) break;
                    }
                    if (h == highlights.length) {
                        logHighlightNumber = h;
                    } else {
                        if (!down) logHighlightNumber = h - 1;
                        else logHighlightNumber = h;
                    }
                }
                if (logTotalHighlightNumber == 0) logSearchoccurrenceField.setText("1 Occurrence");
                else logSearchoccurrenceField.setText((logTotalHighlightNumber + 1) + " Occurrences");
                if ((logTotalHighlightNumber + 1) == 0) logSearchField.setBackground(Color.RED);
                else logSearchField.setBackground(Color.GREEN);
            } catch (BadLocationException e) {
            }
        }

        private synchronized void updateHighlights() {
            String logText = logPane.getText(), searchText = logSearchField.getText();
            Highlighter highlighter = logPane.getHighlighter();
            highlighter.removeAllHighlights();
            try {
                if (searchText.length() > 0) {
                    int h = 0;
                    Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    Highlighter.HighlightPainter sPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
                    int p0 = 0;
                    int p1 = 0;
                    if (logMatchcaseBox.isSelected()) {
                        while (logText.indexOf(searchText, p1) != -1) {
                            p0 = logText.indexOf(searchText, p1);
                            p1 = p0 + searchText.length();
                            if (h == logHighlightNumber) {
                                highlighter.addHighlight(p0, p1, sPainter);
                                logPane.setCaretPosition(p0);
                            } else highlighter.addHighlight(p0, p1, painter);
                            h++;
                        }
                    } else {
                        while (logText.toLowerCase().indexOf(searchText.toLowerCase(), p1) != -1) {
                            p0 = logText.toLowerCase().indexOf(searchText.toLowerCase(), p1);
                            p1 = p0 + searchText.length();
                            if (h == logHighlightNumber) {
                                highlighter.addHighlight(p0, p1, sPainter);
                                logPane.setCaretPosition(p0);
                            } else highlighter.addHighlight(p0, p1, painter);
                            h++;
                        }
                    }
                    logTotalHighlightNumber = h - 1;
                    if (logTotalHighlightNumber == 0) logSearchoccurrenceField.setText("1 Occurrence");
                    else logSearchoccurrenceField.setText((logTotalHighlightNumber + 1) + " Occurrences");
                    if ((logTotalHighlightNumber + 1) == 0) logSearchField.setBackground(Color.RED);
                    else logSearchField.setBackground(Color.GREEN);
                } else {
                    logSearchoccurrenceField.setText("");
                    logSearchField.setBackground(Color.WHITE);
                    logHighlightNumber = -1;
                }
            } catch (BadLocationException e) {
            }
        }

        private void graph() {
            String xStream = (String) JOptionPane.showInputDialog(this, "Enter X data stream", "Graph",
                    JOptionPane.PLAIN_MESSAGE, null, null, "Time since start");
            if (xStream == null) return;

            String yStream = (String) JOptionPane.showInputDialog(this, "Enter Y data stream", "Graph",
                    JOptionPane.PLAIN_MESSAGE, null, null, "");
            if (yStream == null) return;

            ArrayList<Double> x = new ArrayList<>(), y = new ArrayList<>();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));

                while (reader.ready()) {
                    String line = reader.readLine();
                    if (line == "") continue;
                    if (line.contains(xStream + ":") && line.contains(yStream + ":")) {
                        line = line.replace("%&&%&&%", " ");
                        String[] parts = line.split(xStream + ": ");
                        x.add(Double.valueOf(parts[1].split(" ")[0]));
                        parts = line.split(yStream + ": ");
                        y.add(Double.valueOf(parts[1].split(" ")[0]));
                    }
                }
                double[] newX = new double[x.size()], newY = new double[y.size()];
                for (int i = 0; i < x.size(); i++) {
                    newX[i] = x.get(i);
                    newY[i] = y.get(i);
                }
                new DataGraph(newX, newY);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please ensure proper X/Y data streams", "Error",
                        JOptionPane.ERROR_MESSAGE, null);
                return;
            }
        }

        @Override
        public synchronized void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "newfile":
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
                    fileChooser.setFileFilter(filter);
                    fileChooser.showOpenDialog(this);
                    if (fileChooser.getSelectedFile() == null) return;
                    currentFile = fileChooser.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                        logPane.setText("");
                        logText.clear();
                        while (reader.ready()) {
                            String line = reader.readLine();
                            if (line == "") continue;
                            logText.add(line);
                        }
                    } catch (FileNotFoundException ee) {
                        System.err.println("File not found");
                    } catch (IOException ee) {
                        System.err.println("File read error");
                    } catch (NullPointerException ee) {
                        System.err.println("No file selected");
                    }
                    break;
                case "showdate":
                    if (logShowdateBox.isSelected()) logShowtimeBox.setSelected(false);
                    break;
                case "showtime":
                    if (logShowtimeBox.isSelected()) logShowdateBox.setSelected(false);
                    break;
                case "search":
                    updateHighlights();
                    return;
                case "graph":
                    graph();
                    return;
                case "matchcase":
                    updateHighlights();
                    return;
                default:
                    break;
            }
            logPrint();
        }

        KeyListener keyboard = new KeyListener() {
            @Override
            public synchronized void keyTyped(KeyEvent e) {

            }

            @Override
            public synchronized void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 16 || e.getKeyCode() == 37 || e.getKeyCode() == 39) return;
                if (logHighlightNumber == -1) {
                    if (e.getKeyCode() == 40) startHighlight(true);
                    else startHighlight(false);
                    return;
                }
                switch (e.getKeyCode()) {
                    case 38:
                        logHighlightNumber--;
                        logSearchField.setCaretPosition(logSearchField.getText().length());
                        break;
                    case 40:
                        logHighlightNumber++;
                        logSearchField.setCaretPosition(logSearchField.getText().length());
                        break;
                    default:
                        return;
                }
                if (logHighlightNumber < 0) logHighlightNumber = logTotalHighlightNumber;
                if (logHighlightNumber > logTotalHighlightNumber) logHighlightNumber = 0;
                updateHighlights();
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 16:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                        return;
                    default:
                        break;
                }
                if (logShowmatchingBox.isSelected()) logPrint();
                else updateHighlights();
            }
        };
    }

    public class SmartDashboard extends JFrame implements ActionListener, MouseListener, Runnable {

        private JPanel smartdashboardPanel;
        private JPanel smartdashboardControlPanel;
        private JPanel smartdashboardComponentPanel;

        private JButton smartdashboardSaveButton;
        private JButton smartdashboardLoadButton;

        private ComponentMover componentMover;
        private ComponentResizer componentResizer;

        private JPanel smartdashboardConnected;

        private ArrayList lastComponents = new ArrayList();

        public SmartDashboard() {

            smartdashboardPanel = new JPanel();
            smartdashboardPanel.setLayout(new BorderLayout(10, 10));
            smartdashboardPanel.setBackground(blueColor);

            initSmartdashboardContolPanel();

            smartdashboardPanel.add(smartdashboardControlPanel, BorderLayout.NORTH);

            smartdashboardComponentPanel = new JPanel();
            smartdashboardComponentPanel.setBackground(blueColor);
            smartdashboardComponentPanel.setLayout(null);

            smartdashboardPanel.add(smartdashboardComponentPanel, BorderLayout.CENTER);

            add(smartdashboardPanel);

            componentMover = new ComponentMover();
            componentMover.setDragInsets(new Insets(8, 8, 8, 8));

            componentResizer = new ComponentResizer();
            componentResizer.setMinimumSize(new Dimension(40, 20));
            componentResizer.setDragInsets(new Insets(8, 8, 8, 8));

            setTitle("Up-A-Creek FTC Smart Dashboard");
            setBackground(blueColor);
            getContentPane().setBackground(blueColor);
            setExtendedState(MAXIMIZED_BOTH);
            setVisible(true);

            new Thread(this).start();
        }

        private synchronized void initSmartdashboardContolPanel() {
            smartdashboardControlPanel = new JPanel();
            smartdashboardControlPanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "Up-A-Creek FTC Smart Dashboard",
                    TitledBorder.CENTER, TitledBorder.BELOW_TOP, new Font("Sans", Font.PLAIN, 40), Color.DARK_GRAY));
            smartdashboardControlPanel.setBackground(blueColor);

            smartdashboardSaveButton = new JButton("Save Layout");
            smartdashboardSaveButton.setActionCommand("save");
            smartdashboardSaveButton.addActionListener(this);

            smartdashboardControlPanel.add(smartdashboardSaveButton);

            smartdashboardLoadButton = new JButton("Load Layout");
            smartdashboardLoadButton.setActionCommand("load");
            smartdashboardLoadButton.addActionListener(this);

            smartdashboardControlPanel.add(smartdashboardLoadButton);

            JTextPane text = new JTextPane();
            text.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
            text.setText("Connected:");
            text.setEditable(false);
            text.setBackground(blueColor);

            smartdashboardControlPanel.add(text);

            smartdashboardConnected = new JPanel();
            smartdashboardConnected.setPreferredSize(new Dimension(20, 20));
            smartdashboardConnected.setBackground(Color.red);

            smartdashboardControlPanel.add(smartdashboardConnected);
        }

        public void smartdashboardWriteFile() {
            String[] selections = {"Cancel", "Enter"};

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
            fileChooser.setFileFilter(filter);
            fileChooser.showSaveDialog(this);

            if (fileChooser.getSelectedFile() == null) return;

            File file = fileChooser.getSelectedFile();

            try {

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));

                synchronized (smartdashboardComponents) {

                    ArrayList<StorableComponent> storableComponents = new ArrayList<>();

                    for (DashboardComponent comp : smartdashboardComponents) {
                        Component component = comp.getComponent();

                        storableComponents.add(new StorableComponent(comp.getType(), comp.getName(), component.getX(), component.getY(), component.getWidth(), component.getHeight()));
                    }

                    objectOutputStream.writeObject(storableComponents);
                }

            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("File write error");
            }
        }

        public void smartdashboardReadFile() {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
            fileChooser.setFileFilter(filter);
            fileChooser.showOpenDialog(this);
            if (fileChooser.getSelectedFile() == null) return;
            File file = fileChooser.getSelectedFile();

            try {
                ObjectInputStream objectOutputStream = new ObjectInputStream(new FileInputStream(file));

                synchronized (smartdashboardComponents) {

                    smartdashboardComponents = Collections.synchronizedList(new ArrayList<>());

                    ArrayList<StorableComponent> storableComponents = (ArrayList<StorableComponent>) objectOutputStream.readObject();

                    for (StorableComponent component : storableComponents) {
                        switch (component.getType()) {
                            case "VALUE":
                                JTextField text = new JTextField(component.getName() + ": ");
                                text.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                                text.setBounds(component.getX(), component.getY(), component.getWidth(), component.getHeight());
                                text.setBackground(blueColor);
                                text.setHorizontalAlignment(JTextField.CENTER);
                                text.setEditable(false);
                                text.setHighlighter(null);

                                smartdashboardComponents.add(new DashboardComponent(component.getType(), component.getName(), text));
                                break;
                            case "BOOLEAN":
                                BooleanField display = new BooleanField(component.getName());
                                display.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                                display.setBounds(component.getX(), component.getY(), component.getWidth(), component.getHeight());
                                display.setBackground(Color.red);
                                display.setHorizontalAlignment(JTextField.CENTER);
                                display.setEditable(false);
                                display.setHighlighter(null);

                                smartdashboardComponents.add(new DashboardComponent(component.getType(), component.getName(), display));
                                break;
                            case "BUTTON":
                                JButton button = new JButton(component.getName());
                                button.setBounds(component.getX(), component.getY(), component.getWidth(), component.getHeight());
                                button.setBackground(blueColor);
                                button.setHorizontalAlignment(JTextField.CENTER);

                                smartdashboardComponents.add(new DashboardComponent(component.getType(), component.getType(), button));
                                break;
                            case "INPUT":
                                JPanel panel = new JPanel();
                                panel.setLayout(new BorderLayout(8, 8));
                                panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
                                panel.setBackground(blueColor);
                                panel.setBounds(component.getX(), component.getY(), component.getWidth(), component.getHeight());

                                JTextField inputDisplay = new JTextField(component.getName() + ":");
                                inputDisplay.setBorder(BorderFactory.createEmptyBorder());
                                inputDisplay.setBackground(blueColor);
                                inputDisplay.setHorizontalAlignment(JTextField.RIGHT);
                                inputDisplay.setEditable(false);
                                inputDisplay.setHighlighter(null);

                                panel.add(inputDisplay, BorderLayout.WEST);

                                JTextField input = new JTextField();

                                panel.add(input, BorderLayout.CENTER);

                                JPanel emptyPanel = new JPanel();
                                emptyPanel.setBackground(blueColor);

                                panel.add(emptyPanel, BorderLayout.NORTH);

                                emptyPanel = new JPanel();
                                emptyPanel.setBackground(blueColor);

                                panel.add(emptyPanel, BorderLayout.SOUTH);

                                panel.setVisible(true);

                                smartdashboardComponents.add(new DashboardComponent(component.getType(), component.getName(), panel));
                                break;
                            case "SLIDER":
                                SliderPanel slider = new SliderPanel(component.getName(), 0, 1, component.getX(), component.getY(), component.getWidth(), component.getHeight());

                                smartdashboardComponents.add(new DashboardComponent(component.getType(), component.getName(), slider));
                                break;

                            case "GRAPH":
                                GraphPanel graph = new GraphPanel(component.getName(), component.getX(), component.getY(), component.getWidth(), component.getHeight());

                                smartdashboardComponents.add(new DashboardComponent(component.getType(), component.getName(), graph));
                                break;
                        }
                    }

                    lastComponents = new ArrayList();
                    smartdashboardComponentPanel.removeAll();
                }

            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            } catch (IOException e) {
                System.err.println("File read error");
            } catch (ClassNotFoundException e) {
                System.err.println("File read error");
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "save":
                    smartdashboardWriteFile();
                    break;
                case "load":
                    smartdashboardReadFile();
                    break;
            }
        }

        @Override
        public void run() {

            while (true) {
                synchronized (smartdashboardComponents) {
                    for (DashboardComponent component : smartdashboardComponents) {
                        if (!lastComponents.contains(component)) {

                            component.getComponent().addMouseListener(this);

                            componentMover.registerComponent(component.getComponent());
                            componentResizer.registerComponent(component.getComponent());
                            smartdashboardComponentPanel.add(component.getComponent());

                            lastComponents.add(component);

                            component.getComponent().setVisible(false);
                            component.getComponent().setVisible(true);
                        }
                    }
                }

                repaint();

                smartdashboardConnected.setBackground(connected.getBackground());
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            Component component = e.getComponent();
            synchronized (smartdashboardComponents) {

                DashboardComponent comp = null;

                for (DashboardComponent c : smartdashboardComponents) {
                    if (c.getComponent().equals(component)) {
                        comp = c;
                    }
                }

                smartdashboardComponents.remove(comp);
                smartdashboardComponents.add(0, comp);
                smartdashboardComponentPanel.remove(comp.getComponent());
                smartdashboardComponentPanel.add(comp.getComponent(), 0);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class SliderPanel extends JPanel implements ActionListener, ChangeListener, MouseListener {

        private JTextField text;
        private JTextField display;
        private JSlider slider;

        public SliderPanel(String name, int min, int max) {
            setLayout(new BorderLayout(20, 20));
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            setBackground(blueColor);
            setBounds(0, 0, 200, 100);

            text = new JTextField(name);
            text.setBorder(BorderFactory.createEmptyBorder());
            text.setBackground(blueColor);
            text.setHorizontalAlignment(JTextField.CENTER);
            text.setEditable(false);
            text.setHighlighter(null);

            add(text, BorderLayout.NORTH);

            slider = new JSlider(min, max);
            slider.setBackground(blueColor);
            slider.addChangeListener(this);
            slider.addMouseListener(this);

            add(slider, BorderLayout.CENTER);

            display = new JTextField(String.valueOf(slider.getValue()));
            display.setHorizontalAlignment(JTextField.RIGHT);
            display.addActionListener(this);

            add(display, BorderLayout.WEST);

            setVisible(false);
            setVisible(true);
        }

        public SliderPanel(String name, int min, int max, int x, int y, int width, int height) {
            setLayout(new BorderLayout(20, 20));
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            setBackground(blueColor);
            setBounds(x, y, width, height);

            text = new JTextField(name);
            text.setBorder(BorderFactory.createEmptyBorder());
            text.setBackground(blueColor);
            text.setHorizontalAlignment(JTextField.CENTER);
            text.setEditable(false);
            text.setHighlighter(null);

            add(text, BorderLayout.NORTH);

            slider = new JSlider(min, max);
            slider.setBackground(blueColor);
            slider.addChangeListener(this);
            slider.addMouseListener(this);

            add(slider, BorderLayout.CENTER);

            display = new JTextField(String.valueOf(slider.getValue()));
            display.setHorizontalAlignment(JTextField.RIGHT);
            display.addActionListener(this);

            add(display, BorderLayout.WEST);

            setVisible(false);
            setVisible(true);
        }

        public JSlider getSlider() {
            return slider;
        }

        public void setRange(int min, int max) {
            slider.setMinimum(min);
            slider.setMaximum(max);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            slider.setValue(Integer.valueOf(display.getText()));
            display.setText(String.valueOf(slider.getValue()));
            setVisible(false);
            setVisible(true);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            display.setText(String.valueOf(slider.getValue()));
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setVisible(false);
            setVisible(true);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class BooleanField extends JTextField {

        public BooleanField() {
            super();
        }

        public BooleanField(String string) {
            super(string);
        }
    }

    public class GraphPanel extends JPanel {

        private String name;
        private JFreeChart chart;
        private HashMap<String, XYSeries> seriesHashMap;
        private XYSeriesCollection data;
        private JPanel seriesPanel;

        public GraphPanel(String name) {

            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            setBackground(blueColor);
            setBounds(0, 0, 400, 200);

            seriesHashMap = new HashMap<>();

            data = new XYSeriesCollection();

            this.name = name;

            chart = ChartFactory.createScatterPlot(name, "X", "Y", data);
            chart.setBackgroundPaint(blueColor);
            chart.getTitle().setPaint(greyColor);
            chart.getLegend().setBackgroundPaint(blueColor);
            chart.getLegend().setItemPaint(greyColor);
            chart.getLegend().setFrame(new BlockBorder(blueColor));
            ((XYPlot) chart.getPlot()).setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

            ChartPanel panel = new ChartPanel(chart);

            add(panel, BorderLayout.CENTER);

            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener((e) -> {
                data.removeAllSeries();
                seriesHashMap.clear();
                seriesPanel.removeAll();
            });

            add(clearButton, BorderLayout.SOUTH);

            seriesPanel = new JPanel();
            seriesPanel.setBackground(blueColor);
            seriesPanel.setLayout(new BoxLayout(seriesPanel, BoxLayout.Y_AXIS));

            add(seriesPanel, BorderLayout.EAST);

            setVisible(false);
            setVisible(true);
        }

        public GraphPanel(String name, int x, int y, int width, int height) {

            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            setBackground(blueColor);
            setBounds(x, y, width, height);

            seriesHashMap = new HashMap<>();

            data = new XYSeriesCollection();

            this.name = name;

            chart = ChartFactory.createScatterPlot(name, "X", "Y", data);
            chart.setBackgroundPaint(blueColor);
            chart.getTitle().setPaint(greyColor);
            chart.getLegend().setBackgroundPaint(blueColor);
            chart.getLegend().setItemPaint(greyColor);
            chart.getLegend().setFrame(new BlockBorder(blueColor));

            ChartPanel panel = new ChartPanel(chart);

            add(panel, BorderLayout.CENTER);

            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener((e) -> {
                data.removeAllSeries();
                seriesHashMap.clear();
                seriesPanel.removeAll();
            });

            add(clearButton, BorderLayout.SOUTH);

            seriesPanel = new JPanel();
            seriesPanel.setBackground(blueColor);
            seriesPanel.setLayout(new BoxLayout(seriesPanel, BoxLayout.Y_AXIS));

            add(seriesPanel, BorderLayout.EAST);

            setVisible(false);
            setVisible(true);
        }

        public void addDataPoint(String series, double x, double y) {
            if (!seriesHashMap.containsKey(series)) {
                seriesHashMap.put(series, new XYSeries(series));
                seriesHashMap.get(series).add(new XYDataItem(x, y));
                data.addSeries(seriesHashMap.get(series));

                JCheckBox checkBox = new JCheckBox(series);
                checkBox.setSelected(true);
                checkBox.setBackground(blueColor);
                checkBox.addActionListener((e) -> {
                    if (checkBox.isSelected()) {
                        data.addSeries(seriesHashMap.get(series));
                    } else {
                        data.removeSeries(seriesHashMap.get(series));
                    }
                });

                seriesPanel.add(checkBox);
            }

            seriesHashMap.get(series).add(new XYDataItem(x, y));
        }

        public void addPoint(String series, double x, double y) {
            if (!seriesHashMap.containsKey(series)) {
                seriesHashMap.put(series, new XYSeries(series));
                data.addSeries(seriesHashMap.get(series));

                JCheckBox checkBox = new JCheckBox(series);
                checkBox.setSelected(true);
                checkBox.setBackground(blueColor);
                checkBox.addActionListener((e) -> {
                    if (checkBox.isSelected()) {
                        data.addSeries(seriesHashMap.get(series));
                    } else {
                        data.removeSeries(seriesHashMap.get(series));
                    }
                });

                seriesPanel.add(checkBox);
            }

            seriesHashMap.get(series).clear();
            seriesHashMap.get(series).add(new XYDataItem(x, y));
        }

        public void addCircle(String series, double r, double x, double y) {
            if (!seriesHashMap.containsKey(series)) {
                seriesHashMap.put(series, new XYSeries(series));
                data.addSeries(seriesHashMap.get(series));

                JCheckBox checkBox = new JCheckBox(series);
                checkBox.setSelected(true);
                checkBox.setBackground(blueColor);
                checkBox.addActionListener((e) -> {
                    if (checkBox.isSelected()) {
                        data.addSeries(seriesHashMap.get(series));
                    } else {
                        data.removeSeries(seriesHashMap.get(series));
                    }
                });

                seriesPanel.add(checkBox);
            }

            for (double i = 0; i < 360; i++) {
                seriesHashMap.get(series).add(new XYDataItem((Math.cos(Math.toRadians(i)) * r + x), (Math.sin(Math.toRadians(i)) * r + y)));
            }
        }

        public void clearSeries(String series) {
            if (seriesHashMap.containsKey(series)) seriesHashMap.get(series).clear();
        }

        public void clear() {
            data.removeAllSeries();
            seriesHashMap.clear();
            seriesPanel.removeAll();
        }
    }
}