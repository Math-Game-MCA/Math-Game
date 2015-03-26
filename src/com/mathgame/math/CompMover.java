package com.mathgame.math;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.panels.CardPanel;

/** 
 * The CompMover class is the adapter class used for moving components such as the JLabels around on
 * the screen. It is used as a mouse adapter and a mouse motion adapter, so to
 * use, set the component's add mouseListener and mouseMotionListener parameters
 * to the object which has been created from this class.
 * <p>
 * For example:<br/>
 * CompMover mover = new CompMover();<br/>
 * item.addMouseListener(mover);<br/>
 * item.addMouseMotionListener(mover);<br/>
 * <p>
 * Note: This class should only be instantiated in the MathGame class
 */
public class CompMover extends MouseInputAdapter {
	Component selectedComponent;
	Point offset;
	boolean draggingCard;
	boolean moved;

	JLabel[] cards = new JLabel[12]; // card1, card2..opA,S...
	Rectangle[] cardHomes = new Rectangle[12]; // home1, home2...opA,S...
	
	/**
	 * The constructor (which should only be called in the MathGame class)
	 */
	public CompMover() {
		draggingCard = false;
		moved = false;

		cards = MathGame.getCards();
		cardHomes = MathGame.getCardHomes();
	}

	/**
	 * If a card is selected, it can be dragged
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed event");
		selectedComponent = (Component) (e.getSource());
		//System.out.println("parent: " + selectedComponent.getParent());
		// Point tempPoint = selectedComponent.getLocation();
		offset = e.getPoint();
		Point realLoc = e.getLocationOnScreen();
		
		System.out.println("location on screen: " + realLoc);
		
		Rectangle r = selectedComponent.getBounds();
		System.out.println("original bounds: " + r);
		
		
		draggingCard = true;

		try {
			if (selectedComponent.getParent().equals(MathGame.getWorkspacePanel())) {
				r = SwingUtilities.convertRectangle(MathGame.getWorkspacePanel(), r, MathGame.getMasterPane());
				System.out.println("new bounds: " + r);
				
				MathGame.getWorkspacePanel().remove(selectedComponent);
				MathGame.getWorkspacePanel().revalidate();
				MathGame.getMasterPane().add(selectedComponent, new Integer(1));
				MathGame.getMasterPane().revalidate();
				MathGame.getMasterPane().repaint();

				// offset = selectedComponent.getLocationOnScreen();
				// selectedComponent.setBounds(MouseInfo.getPointerInfo().getLocation().x,
				// MouseInfo.getPointerInfo().getLocation().y,
				// cardHomes[1].getSize().width, cardHomes[1].getSize().height);
				// selectedComponent.setLocation(MouseInfo.getPointerInfo().getLocation());
				
				/*
				System.out.println(MouseInfo.getPointerInfo().getLocation());
				System.out.println(selectedComponent.getLocation());
				System.out.println(selectedComponent.getLocationOnScreen());
				System.out.println(tempPoint);
				*/
				
				//selectedComponent.setLocation(300, 400);
				//selectedComponent.setLocation(realLoc.x, realLoc.y);
				//selectedComponent.setLocation(offset.x, offset.y);
				selectedComponent.setBounds(r);

				// selectedComponent.setSize(cardHomes[1].getSize().width,
				// cardHomes[1].getSize().height);

			} else if (selectedComponent.getParent().equals(MathGame.getHoldPanel())) {
				r = SwingUtilities.convertRectangle(MathGame.getHoldPanel(), r, MathGame.getMasterPane());
				System.out.println("new bounds: " + r);
				
			
				int tempX = selectedComponent.getX();
				int tempY = selectedComponent.getLocationOnScreen().y;
				MathGame.getHoldPanel().remove(selectedComponent);
				MathGame.getHoldPanel().revalidate();
				MathGame.getMasterPane().add(selectedComponent, new Integer(1));
				MathGame.getMasterPane().revalidate();
				MathGame.getMasterPane().repaint();

				//selectedComponent.setLocation(tempX, tempY);
				selectedComponent.setBounds(r);
			}
			/*
			else { System.out.println("normal workpanel:"+workPanel);
			System.out.println("parent:"+selectedComponent.getParent()); }
			*/

		} catch (Exception ex) {
			System.out.println("error removing from panel");
			ex.printStackTrace();
		}

	}

	/**
	 * Handles the cases when any card is released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouse released event");
		draggingCard = false;
		Rectangle box1 = new Rectangle();
		Rectangle box2 = new Rectangle();
		Rectangle box3 = new Rectangle();
		
		box1.setBounds(selectedComponent.getLocation().x, selectedComponent.getLocation().y,
				selectedComponent.getWidth(), selectedComponent.getHeight());

		try {
			box2.setBounds(MathGame.getWorkspacePanel().getBounds());
			box3.setBounds(MathGame.getHoldPanel().getBounds());
		} catch (Exception ex) {
			System.out.println("Bounds could not be set");
		}

		if (box1.intersects(box2)) {
			// The selectedComponent is above the WorkspacePanel
			if (selectedComponent instanceof NumberCard) {				
				NumberCard temp = (NumberCard)selectedComponent;
				if (temp.getHome() == "home") {
					// Meaning the card originated from cardPanel
					for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
						if(temp.getNumberTag() == MathGame.getCardPanel().getCards()[i].getNumberTag()){
							MathGame.getCardPanel().changeCardExistence(i, false);
							break;
						}
					}
				}
				
				if (MathGame.getWorkspacePanel().getComponentCount() == 1	&&
						MathGame.getWorkspacePanel().getComponent(0) instanceof OperationCard) {
					// Force card to be placed BEFORE operator
					OperationCard tempOpCard = (OperationCard)(MathGame.getWorkspacePanel().getComponent(0));
					MathGame.getWorkspacePanel().remove(0); // Temporarily take out operation card
					MathGame.getMasterPane().remove(selectedComponent);
					MathGame.getMasterPane().revalidate();
					MathGame.getWorkspacePanel().add(selectedComponent);// Put numbercard
					MathGame.getWorkspacePanel().add(tempOpCard);// Put back operation AFTER numbercard
					MathGame.getWorkspacePanel().revalidate();
					MathGame.getMasterPane().repaint();
				} else {
					MathGame.getMasterPane().remove(selectedComponent);
					MathGame.getMasterPane().revalidate();
					MathGame.getWorkspacePanel().add(selectedComponent);
					MathGame.getWorkspacePanel().revalidate();
					MathGame.getMasterPane().repaint();
				}
				
			} else if (selectedComponent instanceof OperationCard) {
				// Now attempt to put an operation card in between if necessary
				if (MathGame.getWorkspacePanel().getComponentCount() == 0) {
					// Nothing in work panel; do not put operation
					restoreCard();
				} else if (MathGame.getWorkspacePanel().getComponentCount() == 1) {
					// There is presumably one NumberCard in there
					MathGame.getMasterPane().remove(selectedComponent);
					MathGame.getMasterPane().revalidate();
					MathGame.getWorkspacePanel().add(selectedComponent);
					MathGame.getWorkspacePanel().revalidate();
					MathGame.getMasterPane().repaint();
				} else if (MathGame.getWorkspacePanel().getComponentCount() == 2) {
					// Check if its two NumberCards or a NumberCard & OperationCard
					if (MathGame.getWorkspacePanel().getComponent(0) instanceof NumberCard
							&& MathGame.getWorkspacePanel().getComponent(1) instanceof NumberCard) {
						OperationCard temp = (OperationCard) selectedComponent;
						MathGame.getMasterPane().remove(selectedComponent);
						MathGame.getMasterPane().revalidate();
						NumberCard tempNumCard = (NumberCard)(MathGame.getWorkspacePanel().getComponent(1));
						MathGame.getWorkspacePanel().remove(1); // Remove the second number card;
						MathGame.getWorkspacePanel().revalidate();
						MathGame.getWorkspacePanel().add(temp);
						MathGame.getWorkspacePanel().revalidate();
						MathGame.getWorkspacePanel().add(tempNumCard);
						MathGame.getWorkspacePanel().revalidate();
						MathGame.getMasterPane().repaint();
					} else {
						restoreCard();
					}
				} else {
					// There are three cards in the middle
					restoreCard();
				}
			}
			// selectedComponent.setSize(cardHomes[1].getSize());
			
		} else if (box1.intersects(box3)) {
			// The selectedComponent is above the HoldPanel
			if (selectedComponent instanceof NumberCard) {
				NumberCard temp = (NumberCard) selectedComponent;
				if (temp.getHome() == "home") {
					// Meaning it originated from cardPanel
					for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
						if(temp.getNumberTag() == MathGame.getCardPanel().getCards()[i].getNumberTag()){
							MathGame.getCardPanel().changeCardExistence(i, false);
							break;
						}
					}
				}
				MathGame.getMasterPane().remove(selectedComponent);
				MathGame.getMasterPane().revalidate();
				MathGame.getHoldPanel().add(selectedComponent);
				MathGame.getHoldPanel().revalidate();
				MathGame.getMasterPane().repaint();
			} else if (selectedComponent instanceof OperationCard) {
				// Don't put operations card in hold
				restoreCard();
			}
			
		} else {
			// The selectedComponent is above neither the WorkspacePanel nor the HoldPanel
			restoreCard();
			try {
				if (selectedComponent.getName().equals(("Answer"))) {
					MathGame.getMasterPane().remove(selectedComponent);
					MathGame.getMasterPane().revalidate();
					MathGame.getHoldPanel().add(selectedComponent);
					MathGame.getHoldPanel().revalidate();
					MathGame.getMasterPane().repaint();
				}
			} catch (Exception ex) {
				System.err.println("selectedComponent is unnamed");
			}
		}

	}

	/**
	 * Restores the card to its original position if not placed in a panel
	 */
	private void restoreCard() {
		for (int i = 0; i < cards.length; i++) {
			// System.out.println(selectedComponent);
			if (selectedComponent.equals(cards[i])) {
				selectedComponent.setBounds(cardHomes[i]);
				return;
			}
		}
	}

	/**
	 * Handles the dragging of selected components
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("mouse dragged event");
		// System.out.println(e.getLocationOnScreen());
		if (draggingCard) {
			Rectangle r = selectedComponent.getBounds();

			r.x += e.getX() - offset.x;
			r.y += e.getY() - offset.y;
			selectedComponent.setBounds(r);

			/*
			if(selectedComponent.getLocationOnScreen() !=
			e.getLocationOnScreen()) { //System.out.println("not");
			selectedComponent.setLocation(MouseInfo.getPointerInfo().getLocation().x-38,
			MouseInfo.getPointerInfo().getLocation().y-100); }
			*/

			// System.out.println(selectedComponent.getParent());z	
		}
	}

}
