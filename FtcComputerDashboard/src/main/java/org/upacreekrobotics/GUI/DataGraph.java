package org.upacreekrobotics.GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

class DataGraph extends JPanel implements ActionListener{
    double[] x;
    double[] y;
    double xMin;
    double xMax;
    double yMin;
    double yMax;
    final int PAD = 100;
    JFrame frame;
    JPanel controlPanel;
    JCheckBox showvaluesBox;
    JCheckBox drawlinesBox;

    public DataGraph(double[] x, double[] y) {
        frame = new JFrame();
        frame.setSize(800,800);
        frame.setMinimumSize(new Dimension(400, 400));

        setBackground(UI.blueColor);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Up-A-Creek FTC Graphing Utility",
                TitledBorder.CENTER,TitledBorder.BELOW_TOP,new Font("Sans",Font.PLAIN,40),Color.DARK_GRAY));
        setLayout(new BorderLayout(10,10));

        initControlPanel();
        add(controlPanel,BorderLayout.SOUTH);

        frame.add(this);
        frame.setVisible(true);
        setData(x, y);
    }

    public void initControlPanel(){
        controlPanel = new JPanel();
        controlPanel.setBackground(UI.blueColor);

        showvaluesBox = new JCheckBox("Show Values");
        showvaluesBox.setSelected(false);
        showvaluesBox.setBackground(UI.blueColor);
        showvaluesBox.setActionCommand("showvalues");
        showvaluesBox.addActionListener(this);
        showvaluesBox.setToolTipText("Show values fpr each data point");

        controlPanel.add(showvaluesBox);

        drawlinesBox = new JCheckBox("Draw Lines");
        drawlinesBox.setSelected(false);
        drawlinesBox.setBackground(UI.blueColor);
        drawlinesBox.setActionCommand("drawlines");
        drawlinesBox.addActionListener(this);
        drawlinesBox.setToolTipText("Draws a line between each point");

        controlPanel.add(drawlinesBox);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        double xScale = (w - 2*PAD)/(xMax - xMin);
        double yScale = (h - 2*PAD)/(yMax - yMin);
        Point2D.Double origin = new Point2D.Double(); // Axes origin.
        Point2D.Double offset = new Point2D.Double(); // Locate data.
        if(xMax < 0) {
            origin.x = w - PAD;
            offset.x = origin.x - xScale*xMax;
        } else if(xMin < 0) {
            origin.x = PAD - xScale*xMin;
            offset.x = origin.x;
        } else {
            origin.x = PAD;
            offset.x = PAD - xScale*xMin;
        }
        if(yMax < 0) {
            origin.y = h - PAD;
            offset.y = origin.y - yScale*yMax;
        } else if(yMin < 0) {
            origin.y = PAD - yScale*yMin;
            offset.y = origin.y;
        } else {
            origin.y = PAD;
            offset.y = PAD - yScale*yMin;
        }
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, origin.y, w-PAD, origin.y));
        // Draw ordinate.
        g2.draw(new Line2D.Double(origin.x, PAD, origin.x, h-PAD));
        g2.setPaint(UI.greyColor);
        // Mark origin.
        g2.fill(new Ellipse2D.Double(origin.x-2, origin.y-2, 4, 4));

        // Plot data.
        double lastX = 0, lastY = 0;
        for(int i = 0; i < x.length; i++) {
            double x1 = offset.x + xScale*x[i];
            double y1 = offset.y + yScale*y[i];
            if(i==0){
                lastX=x1;
                lastY=y1;
            }
            if(drawlinesBox.isSelected()){
                g2.setPaint(Color.lightGray);
                g2.drawLine((int)lastX,(int)lastY,(int)x1,(int)y1);
                lastX=x1;
                lastY=y1;
            }
            g2.setPaint(UI.greyColor);
            g2.fill(new Ellipse2D.Double(x1-2, y1-2, 4, 4));
            if(showvaluesBox.isSelected())g2.drawString("("+(float)x[i]+","+(float)-y[i]+")", (float)x1+3, (float)y1-3);
        }

        // Draw extreme data values.
        g2.setPaint(Color.black);
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        String s = String.format("%.1f", xMin);
        float width;
        double x = offset.x + xScale*xMin;
        g2.drawString(s, (float)x, (float)origin.y+lm.getAscent());
        s = String.format("%.1f", xMax);
        width = (float)font.getStringBounds(s, frc).getWidth();
        x = offset.x + xScale*xMax;
        g2.drawString(s, (float)x-width, (float)origin.y+lm.getAscent());
        s = String.format("%.1f", -yMin);
        width = (float)font.getStringBounds(s, frc).getWidth();
        double y = offset.y + yScale*yMin;
        g2.drawString(s, (float)origin.x+1, (float)y+lm.getAscent());
        s = String.format("%.1f", -yMax);
        width = (float)font.getStringBounds(s, frc).getWidth();
        y = offset.y + yScale*yMax;
        g2.drawString(s, (float)origin.x+1, (float)y);
    }

    public void setData(double[] x, double[] y) {
        if(x.length != y.length) {
            throw new IllegalArgumentException("x and y data arrays " +
                    "must be same length.");
        }
        this.x = x;
        this.y = y;
        for(int i=0;i<y.length;i++){
            this.y[i] = -this.y[i];
        }
        double[] xVals = getExtremeValues(x);
        xMin = xVals[0];
        xMax = xVals[1];
        double[] yVals = getExtremeValues(y);
        yMin = yVals[0];
        yMax = yVals[1];
        repaint();
    }

    private double[] getExtremeValues(double[] d) {
        double min = Double.MAX_VALUE;
        double max = -min;
        for(int i = 0; i < d.length; i++) {
            if(d[i] < min) {
                min = d[i];
            }
            if(d[i] > max) {
                max = d[i];
            }
        }
        return new double[] { min, max };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

        }
        repaint();
    }
}
