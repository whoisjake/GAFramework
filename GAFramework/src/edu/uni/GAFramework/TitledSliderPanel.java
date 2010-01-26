//***********************************************
// file:   TitledSliderPanel.java
// author: Ryan Dixon
// date:   April 13, 2002
// notes:  An augmented slider implementation
//         wrapped in a JPanel.
//***********************************************

package edu.uni.GAFramework;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

public class TitledSliderPanel extends JPanel
{
    GridBagConstraints  c;
    String  	   	title;
    DecimalSlider 	slider;
    JTextField          sliderValue;
    
    public TitledSliderPanel(int orientation, int min, int max, int initial)
    {
        super();
        
        // Initialize slider information
        slider = new DecimalSlider(orientation, min, max, initial);
        
        // Construct Panel Layout with Componenets
        createPanelLayout(orientation);
    }
    
    public TitledSliderPanel(String title, int orin, int min, int max, 
                                int init, String[] labels)
    {
        super();
        
        slider = new DecimalSlider(orin, min, max, init);
        this.title = title;
                    
        // If labels are provided, assign the labels to the slider
        if ( labels != null )
        {
            slider.setLabelTable(labels);
            slider.setPaintLabels(true);
        }
        
        createPanelLayout(orin);
    }
    
    public TitledSliderPanel(String title, int orientation, double min, double max,
                                double init, double ratio, String[] labels)
    {
        super();
        
        this.title  = title;
        slider      = new DecimalSlider(orientation, min, max, init, ratio);
    
        if ( labels != null )
        {
            slider.setLabelTable(labels);
            slider.setPaintLabels(true);
        }
        
        createPanelLayout(orientation);
    }
    
    // Public Slider Delegation Methods
    public void setLabelTable(String[] labels)
    {
        slider.setLabelTable(labels);
    }
    
    public void setPaintLabels(boolean paintLabels)
    {
        slider.setPaintLabels(paintLabels);
    }
    
    public double getValue()
    {
        return slider.getDecimalValue();
    }
    
    
    // Helper Methods
    private void createPanelLayout(int orientation)
    {
        // Create BevelBorder with appropriate title
        if ( title != null )
        {
            // Create a title bevel border
            setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createBevelBorder(1),
                        title, TitledBorder.LEFT, 
                        TitledBorder.TOP));
        }
        else
        {
            // Create untitled bevel border
            setBorder(BorderFactory.createBevelBorder(1));
        }

        // Setup appropriate panel layout
        setLayout(new GridBagLayout());
        
        sliderValue = new JTextField(Double.toString(slider.getDecimalValue()), 5);
        sliderValue.setEditable(false);
        sliderValue.setText(Double.toString(slider.getDecimalValue()));
        
        c = new GridBagConstraints();
        
        boolean orient = (orientation == JSlider.HORIZONTAL);
        
        c.gridx = orient ? GridBagConstraints.RELATIVE : 0;
        c.gridy = orient ? 0 : GridBagConstraints.RELATIVE;
        c.gridwidth  =  1;
        c.gridheight = 1;
        c.weightx = orient ? 1.0 : 1.0;
        c.weighty = orient ? 1.0 : 1.0;
        c.anchor  = GridBagConstraints.WEST;
        c.fill    = (orientation == JSlider.HORIZONTAL) ? GridBagConstraints.HORIZONTAL 
                                                        : GridBagConstraints.VERTICAL;
                                                        
        add(slider, c);
        
        c.weightx = orient ? 0.0 : 1.0;
        c.weighty = orient ? 1.0 : 0.0;
        add(sliderValue, c);
        
        
        // Create Slider Listener
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt){
                if ( Double.toString(slider.getDecimalValue()).length() < 6 )
                    sliderValue.setText(Double.toString(slider.getDecimalValue()));
                else
                    sliderValue.setText( Double.toString(slider.getDecimalValue()).substring(0,4) );
            }
        });
    }

    // Private inner-classes
    private class DecimalSlider extends JSlider
    {
        Hashtable tableLabels;
        double ratio;
        int    max;
        int    min;
        int    initial;
        
        
        public DecimalSlider(int orientation, double min, double max, 
                                double initial, double ratio)
        {
            super(orientation, (int) (min/ratio), (int) (max/ratio), (int) (initial/ratio));
            
            this.max     = (int) (max/ratio);
            this.min     = (int) (min/ratio);
            this.initial = (int) (initial/ratio);
            this.ratio   = ratio;
        }
        
        public DecimalSlider(int orientation, int min, int max, int initial)
        {
            super(orientation, min, max, initial);
            
            this.max = max;
            this.min = min;
            this.initial = initial;
            this.ratio = 1.0;
        }
        
        // Overridden Functionality
        public void setLabelTable(String[] labels)
        {
            // Configure appropriate labels
            tableLabels = new Hashtable();
            Font labelFont = new Font(null,Font.PLAIN,10);
    
            JLabel[] sliderLabels = new JLabel[labels.length];
            
            // Equally distribute the given labels across the slider
            for (int i = 0; i < labels.length; i++)
            {
                JLabel  tmpLabel    = new JLabel(labels[i]);
                Integer tmpPosition = new Integer( max / (labels.length - 1) * i );
                
                tmpLabel.setFont(labelFont);
                tableLabels.put( tmpPosition, tmpLabel);
            }
                
            this.setLabelTable(tableLabels);
        }
        
        public double getDecimalValue()
        {
            return ((double)this.getValue()) * ratio;
        }
        
        public void setDecimalValue(double newValue)
        {
            this.setValue( (int) (newValue / ratio) );
        }
    
    }
}
