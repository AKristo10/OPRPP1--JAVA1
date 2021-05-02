package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BarChartDemo extends JFrame {

    private BarChart chart;

    public BarChartDemo(BarChart chart) {

        super();
        this.chart = chart;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BarChart");
        setLocation(30, 40);
        setSize(800, 700);
        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.add(new BarChartComponent(chart));
        cp.setBackground(Color.WHITE);
    }

    public static void main(String[] args) {


        BarChart chart = null;
        try {
            List<String> list = Files.readAllLines(Paths.get(args[0]));
            String xString = list.get(0);
            String yString = list.get(1);
            String xyValue = list.get(2);
            List<XYValue> xyl = new LinkedList<>();
            for(int i = 0; i < xyValue.split(" ").length ; i++) {
            	String x =xyValue.split(" ")[i].split(",")[0];
            	String y = xyValue.split(" ")[i].split(",")[1];
            	xyl.add(new XYValue(Integer.parseInt(x), Integer.parseInt(y)));
            }
            int yMin = Integer.parseInt(list.get(3));
            int yMax = Integer.parseInt(list.get(4));
            int space = Integer.parseInt(list.get(5));
            chart = new BarChart(xyl, xString, yString, yMin, yMax, space);
            
        } catch (IOException | NumberFormatException e) {
           System.out.println("Greska prilikom otvaranja datoteke ili pretvorbe formata!");
        }

        final BarChart finalBarCh = chart;

        SwingUtilities.invokeLater(() -> {
            BarChartDemo prozor = new BarChartDemo(finalBarCh);
            prozor.setVisible(true);
        });
    }

    
}