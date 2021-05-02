package hr.fer.zemris.java.gui.charts;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;
/**
 * Razred koji predstavlja stupcasti dijagram/histogram.
 * @author Andrea
 *
 */

public class BarChartComponent extends JComponent {

   
	private static final long serialVersionUID = 1L;
	private static final int INTERSPACE = 25;
    BarChart chart;
    FontMetrics fm;
    int yMin;
    int yMax;
    String xString;
    String yString;
    int interval;
    int listSize;

    /**
     * Konstruktor koji prima instancu Barchart.
     * @param chart instanca Barchart
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
        yMin = chart.getyMin();
        yMax = chart.getyMax();
        xString = chart.getxDescription();
        yString = chart.getyDescription();
        interval = chart.getYspace();
        listSize = chart.getList().size();
    }


    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        fm = g2.getFontMetrics();
       
        
        int xAxis =  getInsets().left + 2 * INTERSPACE + fm.getHeight() ;
        int yAxis = getHeight() - (getInsets().bottom + 2*INTERSPACE + 2 * fm.getHeight()) ;
        int endX = getWidth() - getInsets().right - INTERSPACE;
        int endY = getInsets().top + INTERSPACE;
        
        
        setCoordinateSystem(g2, xAxis, endX, yAxis, endY);
        setValues(g2, xAxis, endX, yAxis, endY);
        setStreakes(g2, xAxis, endX, yAxis, endY);
        setGrid(g2, xAxis, endX, yAxis, endY);
        drawColumn(g2, xAxis, endX, yAxis, endY);
       
    }
    /**
     * Metoda koja crta koordinatne osi, strelice na koordinatnim osoba i opis koordinatne ose
     * @param g2 Graphics2D
     * @param xAxis pocetak x-osi
     * @param endX kraj x-osi
     * @param yAxis pocetak y-osi
     * @param endY kraj y-osi
     */
    public void setCoordinateSystem(Graphics2D g2, int xAxis, int endX, int yAxis, int endY) {
    	//nacrtaj osi
    	int arrowlength = 5;
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(xAxis, yAxis, endX, yAxis);
        g2.drawLine(xAxis , yAxis, xAxis , endY);
        g2.fillPolygon(
    			new int[] {
    				endX + 2*arrowlength,
    				endX ,
    				endX
    			},
    			new int[] {
    				getSize().height - xAxis ,
    				getSize().height - xAxis - 10,
    				getSize().height - xAxis + 10
    			}, 3
    		);
        
        g2.fillPolygon(
    			 new int[] {
        				xAxis - 10 ,
        				xAxis ,
        				xAxis + 10
        			},
    			 new int[]{
    				endY ,
    				endY -2*arrowlength,
    				endY
    			}
    			, 3
    		);
        
        g2.drawString(xString, xAxis + (endX - xAxis)/2 - fm.stringWidth(xString)/2 , getSize().height - getInsets().bottom - INTERSPACE);    

        AffineTransform defAt = g2.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2.setTransform(at);
        g2.drawString(yString, - INTERSPACE + (endY - yAxis)/2 -fm.stringWidth(yString) , INTERSPACE + getInsets().left);
        g2.setTransform(defAt);  
        
    }
    
    /**
     * Metoda koja postavlja numericke vrijednosti koordinatnih osi.
     * @param g2 Graphics2D
     * @param xAxis pocetak x-osi
     * @param endX kraj x-osi
     * @param yAxis pocetak y-osi
     * @param endY kraj y-osi
     */
    public void setValues(Graphics2D g2, int xAxis, int endX, int yAxis, int endY) {
    	g2.setColor(Color.BLACK);
    	int startPosition = yAxis + fm.getHeight()/3;
         for(int i = yMin ; i<= yMax; i+= interval) {
         	g2.drawString(String.valueOf(i), xAxis - INTERSPACE - fm.stringWidth(String.valueOf(i)), startPosition);
         	startPosition += (endY - yAxis) / ((yMax-yMin)/interval);
         }
         
         startPosition = xAxis + ((endX - xAxis)/5)/2 - fm.stringWidth(String.valueOf(1));
         for(int i = 0; i<listSize; i++) {
         	g2.drawString(String.valueOf(i+1), startPosition , yAxis + INTERSPACE);
         	startPosition += ((endX - xAxis)/5);
         }
    }
    
    /**
     * Metoda koja crta tocke osi x i y
     * @param g2 Graphics2D
     * @param xAxis pocetak x-osi
     * @param endX kraj x-osi
     * @param yAxis pocetak y-osi
     * @param endY kraj y-osi
     */
    public void setStreakes(Graphics2D g2, int xAxis, int endX, int yAxis, int endY) {
    	g2.setColor(Color.GRAY);
    	int startPosition = xAxis;
        for(int i = 0; i < listSize+1 ; i++) {
        	
        	g2.drawLine(startPosition ,yAxis, startPosition, yAxis+5);
        	startPosition += (endX - xAxis)/5;
        	
        }
        g2.setColor(Color.GRAY);
        startPosition = yAxis;
        for(int i = yMin; i <= yMax ; i += interval) {
        	g2.drawLine(xAxis, startPosition, xAxis-5, startPosition);
        	startPosition += (endY - yAxis) / ((yMax-yMin)/interval);
        } 
    }
    
    /**
     * Metoda koja postavlja resetke
     * @param g2 Graphics2D
     * @param xAxis pocetak x-osi
     * @param endX kraj x-osi
     * @param yAxis pocetak y-osi
     * @param endY kraj y-osi
     */
    public void setGrid(Graphics2D g2, int xAxis, int endX, int yAxis, int endY) {
    	  g2.setColor(new Color(0xF5AA89));
          int startPosition = xAxis + (endX - xAxis)/5;
          for(int i = 0; i<listSize; i++) {
          	g2.drawLine(startPosition, yAxis, startPosition, endY);
          	startPosition +=  (endX - xAxis)/5;
          }
          g2.setColor(new Color(0xF5AA89));
          startPosition = yAxis + (endY- yAxis) / ((yMax-yMin)/interval);
          for(int i = yMin; i <= yMax; i+= interval) {
          	g2.drawLine(xAxis, startPosition, endX, startPosition);
          	startPosition += (endY - yAxis) / ((yMax-yMin)/interval);
          }  	
    }
    
    /**
     * Metoda koja crta crta stupce histograma.
     * @param g2 Graphics2D
     * @param xAxis pocetak x-osi
     * @param endX kraj x-osi
     * @param yAxis pocetak y-osi
     * @param endY kraj y-osi
     */
  
    public void drawColumn(Graphics2D g2, int xAxis, int endX, int yAxis, int endY){
    	 int hRow = (yAxis - endY) / ((yMax-yMin)/interval);
         int startPosition = xAxis;
         g2.setColor(new Color(0xF1784B));
         for(int i = 0; i < listSize ; i++) {
         	int yPosition = yAxis - (chart.getList().get(i).getY() -yMin) * hRow / interval;
         	System.out.println(startPosition + " " + yPosition );
         	g2.fillRect(startPosition, yPosition, ((endX - xAxis)/5)-1, yAxis - yPosition );
         	startPosition += ((endX - xAxis)/5);
         }
    }
}