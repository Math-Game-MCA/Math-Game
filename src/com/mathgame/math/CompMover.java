package com.mathgame.math;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
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
	boolean dragging;
	boolean moved;

	static MathGame view; // Components from the main class
	static JLayeredPane layer;
	JLabel[] cards = new JLabel[11]; // card1, card2..opA,S...
	Rectangle[] cardHomes = new Rectangle[11]; // home1, home2...opA,S...

	static JPanel workPanel;
	static JPanel holdPanel;

	/**
	 * The constructor (which should only be called in the MathGame class)
	 * @param view - The MathGame object. It is recommended to pass "MathGame.this" as an argument
	 */
	public CompMover(MathGame view) {
		dragging = false;
		moved = false;
		this.view = view;
		setViews();
	}

	/**
	 * Initializes an instance of CompMover (without passing in a MathGame object)
	 */
	public CompMover() {
		dragging = false;
		// setViews();
	}

	/**
	 * Sets the view of the panels
	 */
	private void setViews() {
		layer = view.layer;
		cards = view.cards;
		cardHomes = view.cardHomes;
		workPanel = view.workPanel;
		holdPanel = view.holdPanel;

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
		dragging = true;

		try {
			if (selectedComponent.getParent().equals(workPanel)) {

				view.workPanel.remove(selectedComponent);
				view.workPanel.revalidate();
				view.layer.add(selectedComponent, new Integer(1));
				view.layer.revalidate();
				layer.repaint();

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

			} else if (selectedComponent.getParent().equals(holdPanel)) {
				int tempX = selectedComponent.getX();
				int tempY = selectedComponent.getLocationOnScreen().y;
				view.holdPanel.remove(selectedComponent);
				view.holdPanel.revalidate();
				view.layer.add(selectedComponent, new Integer(1));
				view.layer.revalidate();
				layer.repaint();

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
		dragging = false;
		Rectangle box1 = new Rectangle();
		Rectangle box2 = new Rectangle();
		Rectangle box3 = new Rectangle();
		
		box1.setBounds(selectedComponent.getLocation().x, selectedComponent.getLocation().y,
				selectedComponent.getWidth(), selectedComponent.getHeight());

		try {
			box2.setBounds(view.workPanel.getBounds());
			box3.setBounds(view.holdPanel.getBounds());

		} catch (Exception ex) {
			System.out.println("Bounds could not be set");

		}

		if (box1.intersects(box2)) {
			if (selectedComponent instanceof NumberCard) {				
				NumberCard temp = (NumberCard) selectedComponent;
				if (temp.getHome() == "home") {
					// Meaning the card originated from cardPanel
					if (temp.getNumberTag() == view.cardPanel.card1
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(0, false);
					} else if (temp.getNumberTag() == view.cardPanel.card2
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(1, false);
					} else if (temp.getNumberTag() == view.cardPanel.card3
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(2, false);
					} else if (temp.getNumberTag() == view.cardPanel.card4
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(3, false);
					} else if (temp.getNumberTag() == view.cardPanel.card5
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(4, false);
					} else if (temp.getNumberTag() == view.cardPanel.card6
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(5, false);
					}
				}
				
				if (view.workPanel.getComponentCount() == 1	&&
						view.workPanel.getComponent(0) instanceof OperationCard) {
					// Force card to be placed BEFORE operator
					OperationCard tempOpCard = (OperationCard)(view.workPanel.getComponent(0));
					view.workPanel.remove(0); // Temporarily take out operation card
					layer.remove(selectedComponent);
					layer.revalidate();
					view.workPanel.add(selectedComponent);// Put numbercard
					view.workPanel.add(tempOpCard);// Put back operation AFTER numbercard
					view.workPanel.revalidate();
					view.layer.repaint();
				} else {
					layer.remove(selectedComponent);
					layer.revalidate();
					view.workPanel.add(selectedComponent);
					view.workPanel.revalidate();
					view.layer.repaint();
				}
				
			} else if (selectedComponent instanceof OperationCard) {
				// Now attempt to put an operation card in between if necessary
				if (view.workPanel.getComponentCount() == 0) {
					// Nothing in work panel; do not put operatio 
					restoreCard();
				} else if (view.workPanel.getComponentCount() == 1) {
					// There is presumably one NumberCard in there
					layer.remove(selectedComponent);
					layer.revalidate();
					view.workPanel.add(selectedComponent);
					view.workPanel.revalidate();
					view.layer.repaint();
				} else if (view.workPanel.getComponentCount() == 2) {
					// Check if its two NumberCards or a NumberCard & OperationCard
					if (view.workPanel.getComponent(0) instanceof NumberCard
							&& view.workPanel.getComponent(1) instanceof NumberCard) {
						OperationCard temp = (OperationCard) selectedComponent;
						layer.remove(selectedComponent);
						layer.revalidate();
						NumberCard tempNumCard = (NumberCard)(view.workPanel.getComponent(1));
						view.workPanel.remove(1); // Remove the second number card;
						view.workPanel.revalidate();
						view.workPanel.add(temp);
						view.workPanel.revalidate();
						view.workPanel.add(tempNumCard);
						view.workPanel.revalidate();
						view.layer.repaint();
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
			if (selectedComponent instanceof NumberCard) {
				NumberCard temp = (NumberCard) selectedComponent;
				if (temp.getHome() == "home") {
					// Meaning it originated from cardPanel
					if (temp.getNumberTag() == view.cardPanel.card1
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(0, false);
					} else if (temp.getNumberTag() == view.cardPanel.card2
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(1, false);
					} else if (temp.getNumberTag() == view.cardPanel.card3
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(2, false);
					} else if (temp.getNumberTag() == view.cardPanel.card4
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(3, false);
					} else if (temp.getNumberTag() == view.cardPanel.card5
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(4, false);
					} else if (temp.getNumberTag() == view.cardPanel.card6
							.getNumberTag()) {
						view.cardPanel.changeCardExistence(5, false);
					}
				}
				layer.remove(selectedComponent);
				layer.revalidate();
				view.holdPanel.add(selectedComponent);
				view.holdPanel.revalidate();
				view.layer.repaint();
			} else if (selectedComponent instanceof OperationCard) {
				// Don't put operations card in hold
				restoreCard();
			}
			
		} else {
			restoreCard();
			try {
				if (selectedComponent.getName().equals(("Answer"))) {
					layer.remove(selectedComponent);
					layer.revalidate();
					view.holdPanel.add(selectedComponent);
					view.holdPanel.revalidate();
					view.layer.repaint();
				}
			} catch (Exception ex) {
				System.err.println("can't get selectedComponent name");
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
		if (dragging) {
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
