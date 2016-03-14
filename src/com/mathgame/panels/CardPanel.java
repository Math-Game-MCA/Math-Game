package com.mathgame.panels;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.mathgame.cards.*;
import com.mathgame.math.Calculate;
import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;



/**
 * The CardPanel class represents the panel at the top of the game screen that holds the cards that players start out with
 */
public class CardPanel extends JPanel{

    
	private static final long serialVersionUID = -3726881692277688183L;
	
	public static final int NUM_OF_CARDS = 6;//there are 6 cards
	private NumberCard cards[];
	private NumberCard ans;
	private static final String IMAGE_FILE = "/images/CardBar.png";
	
	private ImageIcon background;
	
	private ValidationBox vboxes[];
	private ValidationBox v_ans;
	
	private JLayeredPane masterLayer;
	
	private Calculate calc;
	public ArrayList<String> values;
	private ArrayList<Boolean> cardExists;
	
	//TODO use for offline play?
	/*private InputStream cardValueInput;
	private XSSFWorkbook cardValueWorkbook;
	private static final String CARD_VALUE_FILE = "values.xlsx";
	private XSSFSheet currentSheet;
	private int rowCount;
	private int currentRowNumber;
	private XSSFRow currentRow;*/
	
	private TypeManager typeManager;
		
	public CardPanel()	{
		this.typeManager = MathGame.getTypeManager();
	}
	
	/**
	 * Initializes a card panel
	 * @param masterLayer - The JLayeredPane that contains the CardPanel
	 */
	public void init () {
		this.setBounds(0, 0, 750, 150);
		setLayout(null);
		this.masterLayer = MathGame.getMasterPane();
		cardExists = new ArrayList<Boolean>();
		for (int i = 0; i < 6; i++)	{
			cardExists.add(true);
		}
		
		background = new ImageIcon(CardPanel.class.getResource(IMAGE_FILE));
		
		values = new ArrayList<String>();
		
		cards = new NumberCard[6];
		vboxes = new ValidationBox[6];
		
		ans = new NumberCard(0);
		ans.setBounds(650, 15, 80, 100);
		this.add(ans);
		v_ans = new ValidationBox(ans);
		v_ans.setBounds(650, 115, 80, 20);
		this.add(v_ans);
		
		for(int i = 0; i < NUM_OF_CARDS; i++)	{
			cards[i] = new NumberCard(i);
			cards[i].setNumberTag(i);
			cards[i].setBounds(20 + 90 * i, 15, 80, 100);
			values.add(cards[i].getStrValue());
			vboxes[i] = new ValidationBox(cards[i]);
			vboxes[i].setBounds(20 + 90 * i, 115, 80, 20);
			this.add(cards[i]);
			this.add(vboxes[i]);
		}
		
		calc = new Calculate();
		
		typeManager.init(this);
	}
	
	/**
	 * Change whether the card with the given index exists
	 * @param index - The card's index
	 * @param exists - Whether the card exists or not
	 */
	public void changeCardExistence(int index, Boolean exists)	{
		cardExists.set(index, exists);
	}
	
	/**
	 * @param index - The card's index
	 * @return Whether the given card exists or not
	 */
	public Boolean getCardExistence(int index)	{
		return cardExists.get(index);
	}
	
	/**
	 * Restores a card deleted during calculations (but you must ensure the card exists first!)
	 * @param cardValue - The card's value (as a string)
	 */
	public void restoreCard(String cardValue) {
		System.out.println("cardValue passed " + cardValue);
		for (int i = 0; i < NUM_OF_CARDS; i++) {
			System.out.println("values from reset " + values.get(i));
			if(cardValue.equals(values.get(i)) && !cardExists.get(i))	{
				System.out.println("reset "+i);
				cards[i].setBounds(20 + 90 * i, 15, 80, 120);
				masterLayer.add(cards[i], new Integer(1));
				cardExists.set(i, true);
				break;
			}
		}
	}
	
	/**
	 * Resets all validation boxes
	 */
	public void resetValidationBoxes(){
		v_ans.reset();
		for(int i = 0; i < NUM_OF_CARDS; i++)
			vboxes[i].reset();
	}
	
	/**
	 * Sets the visibility of cards to false, meaning all cards are hidden
	 */
	public void hideCards()	{
		ans.setVisible(false);
		v_ans.setVisible(false);
		for(int i = 0; i < NUM_OF_CARDS; i++)	{
			cards[i].setVisible(false);
			vboxes[i].setVisible(false);
		}
	}
	
	/**
	 * Sets visibility of cards to true, meaning all cards are visible
	 */
	public void showCards()	{
		ans.setVisible(true);
		v_ans.setVisible(true);
		for(int i = 0; i < NUM_OF_CARDS; i++)	{
			cards[i].setVisible(true);
			vboxes[i].setVisible(true);
		}
	}

	/**
	 * @return the cards
	 */
	public NumberCard[] getCards() {
		return cards;
	}

	/**
	 * @return the ans
	 */
	public NumberCard getAns() {
		return ans;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, CardPanel.this);
	}
}
