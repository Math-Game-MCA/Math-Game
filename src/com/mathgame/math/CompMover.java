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
        
        MathGame view;
        //components from the main class
        JLayeredPane layer;
        JPanel panel2a;
        JPanel panel2b;
        JLabel[] cards = new JLabel[11];//card1, card2..opA,S...
    	Rectangle[] cardHomes = new Rectangle[11];//home1, home2...opA,S...
        
        
 
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
        	panel2a = view.panel2a;
        	panel2b = view.panel2b;
        	cards = view.cards;
        	cardHomes = view.cardHomes;
        }
        
        	
        @Override
        public void mousePressed(MouseEvent e)
        {
            selectedComponent = (Component)e.getSource();
            offset = e.getPoint();
            dragging = true;
            
            
           // System.out.println(selectedComponent.getParent());
            //have to reset the text if the card was on another place card (add1,2...)

            try{           
	           if(selectedComponent.getParent().equals(view.panel2a))
	           {
	        	   panel2a.remove(selectedComponent);
	        	   panel2a.revalidate();
	        	   layer.add(selectedComponent, new Integer(1));
	        	   layer.revalidate();
	        	   selectedComponent.setBounds(cardHomes[1]);
	        	   selectedComponent.setSize(cardHomes[1].getSize());
	        	   selectedComponent.setLocation(MouseInfo.getPointerInfo().getLocation());
	           }
	           
	           if(selectedComponent.getParent().equals(panel2b))
	           {
	        	   panel2b.remove(selectedComponent);
	        	   panel2b.revalidate();
	        	   layer.add(selectedComponent, new Integer(1));
	        	  // repaint();
	        	   selectedComponent.setSize(cardHomes[1].getSize());
	        	   selectedComponent.setLocation(MouseInfo.getPointerInfo().getLocation());
	           }
            } catch(Exception ex){//do nothing, just to prevent errors
            }
           
           
        	   
          
	            
        }
 
        @Override
        public void mouseReleased(MouseEvent e)
        {
            dragging = false;
            Rectangle box1 = new Rectangle(), box2 = new Rectangle(), box3 = new Rectangle();
            box1.setBounds(selectedComponent.getLocationOnScreen().x, selectedComponent.getLocationOnScreen().y, selectedComponent.getWidth(), selectedComponent.getHeight() );
            
            try{
            box2.setBounds(panel2a.getLocationOnScreen().x, panel2a.getLocationOnScreen().y, panel2a.getWidth(), panel2a.getHeight());
            box3.setBounds(panel2b.getLocationOnScreen().x, panel2b.getLocationOnScreen().y, panel2b.getWidth(), panel2b.getHeight());
            } catch(Exception ex){ //do nothing
            }
            
            if(box1.intersects(box2) )
            {
            	panel2a.add(selectedComponent);
   
            	panel2a.revalidate();
            	selectedComponent.setSize(cards[1].getSize());
            	//panel2a.repaint();
            	layer.repaint();
            	
            }
            else if(box1.intersects(box3))
            {
            	panel2b.add(selectedComponent);
            	panel2b.revalidate();
            	//panel2b.repaint();
            	layer.repaint();
            }
            else 
            {
            	for(int i=0;i<cards.length;i++)
	            		if(selectedComponent.equals(cards[i]))
	            		{
		            		selectedComponent.setLocation(cardHomes[i].getLocation());
		            		break;
	            		}
            	
            }
            
            
            
            
        }
 
        @Override
        public void mouseDragged(MouseEvent e)
        {
            if(dragging)
            {
                Rectangle r = selectedComponent.getBounds();
            
            	r.x += e.getX() - offset.x;
            	r.y += e.getY() - offset.y;
            	selectedComponent.setBounds(r);
            
                
            }
        }

}
