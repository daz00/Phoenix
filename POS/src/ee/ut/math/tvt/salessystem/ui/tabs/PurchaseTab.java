package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private final SalesDomainController domainController;

	private JButton newPurchase;

	private JButton confirmPurchase;

	private JButton cancelPurchase;

	private PurchaseItemPanel purchasePane;

	private SalesSystemModel model;

	private JButton submitPayment;

	private JButton cancelPayment;

	private JFrame window;



	public PurchaseTab(SalesDomainController controller,
			SalesSystemModel model)
	{
		this.domainController = controller;
		this.model = model;
	}


	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog and
	 * shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model);
		panel.add(purchasePane, getConstraintsForPurchasePanel());

		return panel;
	}




	// The purchase menu. Contains buttons "New purchase", "confirm", "Cancel".
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		confirmPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined above
		panel.add(newPurchase, gc);
		panel.add(confirmPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}


	// Creates the button "New purchase"
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}


	// Creates the "Cancel" button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	//Save order to History tab:
	public void saveOrder() {
		model.getHistoryTableModel().addItem(new HistoryItem(
				model.getCurrentPurchaseTableModel().getTableRows()))
				;
	}
	// Add all the prices together 
	public double getTotalSum() {
		double sum = 0;
		int row = model.getCurrentPurchaseTableModel().getRowCount();
		int column = model.getCurrentPurchaseTableModel().getColumnCount() - 1;
		for (int i = 0; i < row; i++) {
			double money = (double) model.getCurrentPurchaseTableModel()
					.getValueAt(i, column);
			sum += money;

		}
		return sum;

	}
	public void confirmWin(){
		window = new JFrame();
		setTitle("Purchase information");
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		final JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		window.setLayout(new GridLayout(4, 6));
		panel.setBorder(BorderFactory.createTitledBorder("Purchase information"));
		panel2.setBorder(BorderFactory.createTitledBorder("Payment"));
		panel3.setBorder(BorderFactory.createTitledBorder("Change"));
		panel4.setBorder(BorderFactory.createTitledBorder("Buttons"));

		// - amount
		final double sum = getTotalSum();
		panel.add(new JLabel("Total amount to be paid: " + sum));

		// - bar code
		panel2.add(new JLabel("Enter the amount paid and press ENTER: "));
		final JTextField input = new JTextField(20);
		panel2.add(input);

		/** not working change code
		 * 
		 * actionPerformed(evt,doubleinput);
       double doublechange = doubleinput-sum;
        panel4.add(new JLabel("Change: " + doublechange));
		 **/


		final JLabel changelabel = new JLabel("");
		panel3.add(changelabel);

		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				log.info("amount paid is: " + input.getText());
				double doublechange = Double.parseDouble(input.getText()) - sum;
				if (doublechange >= 0) {
					log.info("change is: " + doublechange);
					changelabel.setText("Change: " + doublechange);
				}
				else {
					log.error("Not enough paid");
					input.setText("");
					changelabel.setText("");
				}
				//System.out.println("The entered text is: " + input.getText());
			}
		});

		panel4.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		submitPayment = createNewSubmitButton();
		cancelPayment = createNewCancelButton();
		
			
		// Add the buttons to the panel
		panel4.add(submitPayment, gc);
		panel4.add(cancelPayment, gc);


		// Fill in the window and its details
		window.setSize(400, 300);
		window.add(panel);
		window.add(panel2);
		window.add(panel3);
		window.add(panel4);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		pack();  
	}


	//The submit/cancel menu. Contains buttons "Submit", "Cancel".
	private Component getSubmitMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		submitPayment = createNewSubmitButton();
		cancelPayment = createNewCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined above
		panel.add(submitPayment, gc);
		panel.add(cancelPayment, gc);

		return panel;
	}

	//Creates the button "Submit"
	private JButton createNewSubmitButton() {
		JButton b = new JButton("Submit");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitPaymentButtonClicked();
			}
		});

		return b;
	}


	//Creates the button "Cancel"
	private JButton createNewCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPaymentButtonClicked();
			}
		});
		b.setEnabled(true);

		return b;
	}


	/**  Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPaymentButtonClicked() {
		log.info("Sale cancelled");
		try {
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
		window.dispose();
	}


	/** Event handler for the <code>confirm purchase</code> event. */
	protected void submitPaymentButtonClicked() {
		
		log.info("Sale complete");
		try {
			log.debug("Items bought:\n" + model.getCurrentPurchaseTableModel());
			domainController.submitCurrentPurchase(
					model.getCurrentPurchaseTableModel().getTableRows()
					);
			HistoryItem z = new HistoryItem(
					model.getCurrentPurchaseTableModel().getTableRows());
			
			Session session = HibernateUtil.currentSession();
			session.getTransaction().begin();
			for (SoldItem x : model.getCurrentPurchaseTableModel().getTableRows()){
				x.setHistoryItem(z);
				System.out.print(x.getName());
				session.save(x);
				
				
			}
			session.save(z);
			session.getTransaction().commit();
			
				
			
			
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
		saveOrder();
		endSale();
		model.getCurrentPurchaseTableModel().clear();
		window.dispose();
		
	}






	/**also not working
	 * public double actionPerformed(ActionEvent evt, double doubleinput) {
	 *
	    String textinput = input.getText();
	    return doubleinput = Double.parseDouble(textinput);
  }**/

	/* === Event handlers for the menu buttons
	 *     (get executed when the buttons are clicked)
	 */


	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}


	/**  Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Purchase cancelled");
		try {
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}




	/** Event handler for the <code>confirm purchase</code> event. */
	protected void confirmPurchaseButtonClicked() {
		log.info("Basket complete");
		try {
			log.debug("Contents of the current basket:\n" + model.getCurrentPurchaseTableModel());
			domainController.submitCurrentPurchase(
					model.getCurrentPurchaseTableModel().getTableRows()
					);
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
		confirmWin(); 

	}
	/* === Helper methods that bring the whole purchase-tab to a certain state
	 *     when called.
	 */

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();

		purchasePane.setEnabled(true);
		confirmPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();

		cancelPurchase.setEnabled(false);
		confirmPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}




	/* === Next methods just create the layout constraints objects that control the
	 *     the layout of different elements in the purchase tab. These definitions are
	 *     brought out here to separate contents from layout, and keep the methods
	 *     that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}


	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}


	// The constraints that control the layout of the buttons in the purchase menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}

}