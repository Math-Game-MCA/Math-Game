package com.mathgame.math;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

/**
 * 
 * 
 * The adapter class used for moving components such as the JLabels around on the screen.
 * It is used as a mouse adapter and a mouse motion adapter, so to use, set the component's 
 * add mouseListener and mouseMotionListener parameters to the object which has been created from this class.
 * i.e. CompMover mover = new CompMover(MathGame.this);
 * jlabel.addMouseListener(mover);
 * jlabel.addMouseMotionListener(mover); 
 * 
 *
 */
public class CompMover extends MouseInputAdapter
{
	 	Component selectedComponent;
        Point offset;
        boolean dragging;
        boolean moved = false;
        
        static MathGame view;
        //components from the main class
        static JLayeredPane layer;
        JLabel[] cards = new JLabel[11];//card1, card2..opA,S...
    	Rectangle[] cardHomes = new Rectangle[11];//home1, home2...opA,S...
        
    	static JPanel workPanel;
    	static JPanel holdPanel;
        
 
    	/**
    	 * The constructor which is only able to be called using the MathGame class.
    	 * 
    	 * @param view MathGame.this
    	 */
        public CompMover(MathGame view){        
            dragging = false;
            this.view = view;
            setViews();
        }
        
        
        public CompMover(){        
            dragging = false;
           
            //setViews();
        }
        
        private void setViews(){
        	layer = view.layer;
        	cards = view.cards;
        	cardHomes = view.cardHomes;
        	workPanel = view.workPanel;
        	holdPanel = view.holdPanel;
        	
        }
        
        	
        @Override
        public void mousePressed(MouseEvent e)
        {
        	
            selectedComponent = (Component)e.getSource();
            Point tempPoint = selectedComponent.getLocation();
            offset = e.getPoint();
            dragging = true;
            
            //System.out.println(selectedComponent.getParent());
          
            try{       
            	 if(selectedComponent.getParent().equals(workPanel))
	  	           {
	            		 
	  	        	   view.workPanel.remove(selectedComponent);
	  	        	   view.workPanel.revalidate();
	  	        	   view.layer.add(selectedComponent, new Integer(1));
	  	        	   view.layer.revalidate();
	  	        	   layer.repaint();
	  	        	 
	  	        	   //offset = selectedComponent.getLocationOnScreen();
	  	        	   //selectedComponent.setBounds(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, cardHomes[1].getSize().width, cardHomes[1].getSize().height);
	  	        	   //selectedComponent.setLocation(MouseInfo.getPointerInfo().getLocation());
	  	        	   /*System.out.println(MouseInfo.getPointerInfo().getLocation());
	  	        	   System.out.println(selectedComponent.getLocation());
	  	        	   System.out.println(selectedComponent.getLocationOnScreen());
	  	        	   System.out.println(tempPoint);*/
	  	        	   selectedComponent.setLocation(-200, -200);
	  	        	   
	  	        	   
	 	        	  // selectedComponent.setSize(cardHomes[1].getSize().width, cardHomes[1].getSize().height);
	 	     
	  	           }
            	 else if(selectedComponent.getParent().equals(holdPanel))
            	 {
            		 int tempX = selectedComponent.getX();
            		 int tempY = selectedComponent.getLocationOnScreen().y;
            		 view.holdPanel.remove(selectedComponent);
            		 view.holdPanel.revalidate();
            		 view.layer.add(selectedComponent, new Integer(1));
            		 view.layer.revalidate();
            		 layer.repaint();
            		 
            		 selectedComponent.setLocation(tempX, tempY);
            	 }
            /*	 else
            	 {
            		 System.out.println("normal workpanel:"+workPanel);
            		 System.out.println("parent:"+selectedComponent.getParent());
            	 }*/
	        
	           
	          
            } catch(Exception ex){
            	System.out.println("error removing from panel");
            	ex.printStackTrace();
            }
           
           
        	   
          
	            
        }
 
        @Override
        public void mouseReleased(MouseEvent e)
        {
            dragging = false;
            Rectangle box1 = new Rectangle(), box2 = new Rectangle(), box3 = new Rectangle();
            box1.setBounds(selectedComponent.getLocation().x, selectedComponent.getLocation().y, selectedComponent.getWidth(), selectedComponent.getHeight() );
            
            try{
            	box2.setBounds(view.workPanel.getBounds());
            	box3.setBounds(view.holdPanel.getBounds());
            
            } catch(Exception ex){ 
            	System.out.println("Bounds could not be set");
            	
            }
            
        
            if(box1.intersects(box2))
            {
            	layer.remove(selectedComponent);
            	layer.revalidate();
            	view.workPanel.add(selectedComponent);
            	view.workPanel.revalidate();
            	//panel2b.repaint();
            	//selectedComponent.setSize(cardHomes[1].getSize());
            	 
            	view.layer.repaint();
            }
            else if(box1.intersects(box3))
            {
            	layer.remove(selectedComponent);
            	layer.revalidate();
            	view.holdPanel.add(selectedComponent);
            	view.holdPanel.revalidate();
            	view.layer.repaint();
            }
            else 
            {
            	for(int i=0;i<cards.length;i++)
            	{
            		//System.out.println(selectedComponent);
	            		if(selectedComponent.equals(cards[i]))
	            		{
	            		
	            			selectedComponent.setBounds(cardHomes[i]);
		            		break;
	            		}
            	}
            	try{
	            	if(selectedComponent.getName().equals(("Answer")))
	            	{
	            		layer.remove(selectedComponent);
	                	layer.revalidate();
	                	view.holdPanel.add(selectedComponent);
	                	view.holdPanel.revalidate();
	                	view.layer.repaint();
	            	}
            	} catch(Exception ex) {
            		System.err.println("can't get selectedComponent name");
            	}
            		
            	
            	
            }
            
        }
 
        @Override
        public void mouseDragged(MouseEvent e)
        {
        	//System.out.println(e.getLocationOnScreen());
            if(dragging)
            {
                Rectangle r = selectedComponent.getBounds();
            
            	r.x += e.getX() - offset.x;
            	r.y += e.getY() - offset.y;
            	selectedComponent.setBounds(r);
            	
	        	
            	/*if(selectedComponent.getLocationOnScreen() != e.getLocationOnScreen())
 	        	{
 	        		//System.out.println("not");
 	        		selectedComponent.setLocation(MouseInfo.getPointerInfo().getLocation().x-38, MouseInfo.getPointerInfo().getLocation().y-100);
 	        	}*/
            	
            
            	//System.out.println(selectedComponent.getParent());
                
            }
        }

}
