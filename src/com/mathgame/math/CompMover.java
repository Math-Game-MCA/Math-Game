package com.mathgame.math;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/** 
 * The CompMover class is the adapter class used for moving components such as the JLabels around on
 * the screen. It is used as a mouse adapter and a mouse motion adapter, so to
 * use, set the component's add mouseListener and mouseMotionListener parameters
 * to the object which has been created from this class.
 * <p>
 * For example:
 * CompMover mover = new CompMover(MathGame.this);
 * jlabel.addMouseListener(mover);
 * jlabel.addMouseMotionListener(mover);
 * <p>
 * Note: This class should only be instantiated in the MathGame class
 */
public class CompMover extends MouseInputAdapter {
	Component selectedComponent;
	Point offset;
	boolean draggingCard;
	boolean moved;

	static MathGame mathGame; // Components from the main class

	JLabel[] cards = new JLabel[11]; // card1, card2..opA,S...
	Rectangle[] cardHomes = new Rectangle[11]; // home1, home2...opA,S...

	/**
	 * The constructor (which should only be called in the MathGame class)
	 * @param mathGame - The MathGame object (It is recommended to pass "MathGame.this" as an argument)
	 */
	public CompMover(MathGame mathGame) {
		draggingCard = false;
		moved = false;
		CompMover.mathGame = mathGame;

		cards = mathGame.getCards();
		cardHomes = mathGame.getCardHomes();
	}

	/**
	 * Initializes an instance of CompMover (without passing in a MathGame object)
	 */
	public CompMover() {
		draggingCard = false;
		// setViews();
	}

	/**
	 * If a card is selected, it can be dragged
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		selectedComponent = (Component) (e.getSource());
		// System.out.println(selectedComponent.getParent());
		// Point tempPoint = selectedComponent.getLocation();
		offset = e.getPoint();
		draggingCard = true;

		try {
			if (selectedComponent.getParent().equals(mathGame.getWorkspacePanel())) {

				mathGame.getWorkspacePanel().remove(selectedComponent);
				mathGame.getWorkspacePanel().revalidate();
				mathGame.getMasterPane().add(selectedComponent, new Integer(1));
				mathGame.getMasterPane().revalidate();
				mathGame.getMasterPane().repaint();

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
				selectedComponent.setLocation(-200, -200);

				// selectedComponent.setSize(cardHomes[1].getSize().width,
				// cardHomes[1].getSize().height);

			} else if (selectedComponent.getParent().equals(mathGame.getHoldPanel())) {
				int tempX = selectedComponent.getX();
				int tempY = selectedComponent.getLocationOnScreen().y;
				mathGame.getHoldPanel().remove(selectedComponent);
				mathGame.getHoldPanel().revalidate();
				mathGame.getMasterPane().add(selectedComponent, new Integer(1));
				mathGame.getMasterPane().revalidate();
				mathGame.getMasterPane().repaint();

				selectedComponent.setLocation(tempX, tempY);
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
		draggingCard = false;
		Rectangle box1 = new Rectangle();
		Rectangle box2 = new Rectangle();
		Rectangle box3 = new Rectangle();
		
		box1.setBounds(selectedComponent.getLocation().x, selectedComponent.getLocation().y,
				selectedComponent.getWidth(), selectedComponent.getHeight());

		try {
			box2.setBounds(mathGame.getWorkspacePanel().getBounds());
			box3.setBounds(mathGame.getHoldPanel().getBounds());
		} catch (Exception ex) {
			System.out.println("Bounds could not be set");
		}

		if (box1.intersects(box2)) {
			// The selectedComponent is above the WorkspacePanel
			if (selectedComponent instanceof NumberCard) {				
				NumberCard temp = (NumberCard)selectedComponent;
				if (temp.getHome() == "home") {
					// Meaning the card originated from cardPanel
					if (temp.getNumberTag() == mathGame.getCardPanel().card1.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(0, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card2.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(1, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card3.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(2, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card4.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(3, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card5.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(4, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card6.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(5, false);
					}
				}
				
				if (mathGame.getWorkspacePanel().getComponentCount() == 1	&&
						mathGame.getWorkspacePanel().getComponent(0) instanceof OperationCard) {
					// Force card to be placed BEFORE operator
					OperationCard tempOpCard = (OperationCard)(mathGame.getWorkspacePanel().getComponent(0));
					mathGame.getWorkspacePanel().remove(0); // Temporarily take out operation card
					mathGame.getMasterPane().remove(selectedComponent);
					mathGame.getMasterPane().revalidate();
					mathGame.getWorkspacePanel().add(selectedComponent);// Put numbercard
					mathGame.getWorkspacePanel().add(tempOpCard);// Put back operation AFTER numbercard
					mathGame.getWorkspacePanel().revalidate();
					mathGame.getMasterPane().repaint();
				} else {
					mathGame.getMasterPane().remove(selectedComponent);
					mathGame.getMasterPane().revalidate();
					mathGame.getWorkspacePanel().add(selectedComponent);
					mathGame.getWorkspacePanel().revalidate();
					mathGame.getMasterPane().repaint();
				}
				
			} else if (selectedComponent instanceof OperationCard) {
				// Now attempt to put an operation card in between if necessary
				if (mathGame.getWorkspacePanel().getComponentCount() == 0) {
					// Nothing in work panel; do not put operation
					restoreCard();
				} else if (mathGame.getWorkspacePanel().getComponentCount() == 1) {
					// There is presumably one NumberCard in there
					mathGame.getMasterPane().remove(selectedComponent);
					mathGame.getMasterPane().revalidate();
					mathGame.getWorkspacePanel().add(selectedComponent);
					mathGame.getWorkspacePanel().revalidate();
					mathGame.getMasterPane().repaint();
				} else if (mathGame.getWorkspacePanel().getComponentCount() == 2) {
					// Check if its two NumberCards or a NumberCard & OperationCard
					if (mathGame.getWorkspacePanel().getComponent(0) instanceof NumberCard
							&& mathGame.getWorkspacePanel().getComponent(1) instanceof NumberCard) {
						OperationCard temp = (OperationCard) selectedComponent;
						mathGame.getMasterPane().remove(selectedComponent);
						mathGame.getMasterPane().revalidate();
						NumberCard tempNumCard = (NumberCard)(mathGame.getWorkspacePanel().getComponent(1));
						mathGame.getWorkspacePanel().remove(1); // Remove the second number card;
						mathGame.getWorkspacePanel().revalidate();
						mathGame.getWorkspacePanel().add(temp);
						mathGame.getWorkspacePanel().revalidate();
						mathGame.getWorkspacePanel().add(tempNumCard);
						mathGame.getWorkspacePanel().revalidate();
						mathGame.getMasterPane().repaint();
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
					if (temp.getNumberTag() == mathGame.getCardPanel().card1.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(0, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card2.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(1, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card3.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(2, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card4.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(3, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card5.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(4, false);
					} else if (temp.getNumberTag() == mathGame.getCardPanel().card6.getNumberTag()) {
						mathGame.getCardPanel().changeCardExistence(5, false);
					}
				}
				mathGame.getMasterPane().remove(selectedComponent);
				mathGame.getMasterPane().revalidate();
				mathGame.getHoldPanel().add(selectedComponent);
				mathGame.getHoldPanel().revalidate();
				mathGame.getMasterPane().repaint();
			} else if (selectedComponent instanceof OperationCard) {
				// Don't put operations card in hold
				restoreCard();
			}
			
		} else {
			// The selectedComponent is above neither the WorkspacePanel nor the HoldPanel
			restoreCard();
			try {
				if (selectedComponent.getName().equals(("Answer"))) {
					mathGame.getMasterPane().remove(selectedComponent);
					mathGame.getMasterPane().revalidate();
					mathGame.getHoldPanel().add(selectedComponent);
					mathGame.getHoldPanel().revalidate();
					mathGame.getMasterPane().repaint();
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
