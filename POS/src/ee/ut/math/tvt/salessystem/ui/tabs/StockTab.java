package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionException;

public class StockTab {

	private static final long serialVersionUID = 1L;
	JTextField barCodeField;
    JTextField quantityField;
    JTextField priceField;
    JTextField nameField;
    JTextField description;

  private JButton addItem;
  private SalesSystemModel model;

  public StockTab(SalesSystemModel model) {
    this.model = model;
  }

  // warehouse stock tab - consists of a menu and a table
  public Component draw() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();
    panel.setLayout(gb);

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    panel.add(drawStockMenuPane(), gc);

    gc.weighty = 1.0;
    gc.fill = GridBagConstraints.BOTH;
    panel.add(drawStockMainPane(), gc);
    return panel;
  }
  // Formatting constraints for the dialogPane
  private GridBagConstraints getDialogPaneConstraints() {
      GridBagConstraints gc = new GridBagConstraints();

      gc.anchor = GridBagConstraints.NORTH;
      gc.weightx = 0.2;
      gc.weighty = 0d;
      gc.gridwidth = GridBagConstraints.REMAINDER;
      gc.fill = GridBagConstraints.NONE;

      return gc;
  }

  // warehouse menu
  
  private JButton createAddButton() {
	    JButton addItem = new JButton("Add");
	    addItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	       addNewItem();
	      }
	    });

	    return addItem;
	  }

  private Component drawStockMenuPane() {
    JPanel panel = new JPanel();
    GridBagConstraints gc = new GridBagConstraints();

    panel.setBorder(BorderFactory.createTitledBorder("Product"));
    

    GridBagLayout gb = new GridBagLayout();
    

    panel.setLayout(gb);
    panel.setLayout(new GridLayout(6, 2));
    // Initialize the textfields
    barCodeField = new JTextField();
    quantityField = new JTextField();
    priceField = new JTextField();
    nameField = new JTextField();
    description = new JTextField();

    
    // - bar code
    panel.add(new JLabel("Bar code(id):"));
    panel.add(barCodeField);
    
    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    
    panel.add(new JLabel("Description:"));
    panel.add(description);
    
    // - price
    panel.add(new JLabel("Price:"));
    panel.add(priceField);

    // - amount
    panel.add(new JLabel("Amount:"));
    panel.add(quantityField);  

    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 0;
    

    //addItem = new JButton("Add");
    gc.gridwidth = GridBagConstraints.RELATIVE;
    gc.weightx = 1.0;
    addItem = createAddButton();
    panel.add(addItem, gc);

    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    return panel;
  }

	public void addNewItem() {
		try {


			int quantity = Integer.parseInt(quantityField.getText());
			double price = (double) Math.round(Double.parseDouble(priceField
					.getText()) * 100) / 100;
			int id1 = Integer.parseInt(barCodeField.getText());
			StockItem addedItem = new StockItem(id1, nameField.getText(),
					description.getText(), price, quantity);

			model.getWarehouseTableModel().addItem(addedItem);
			Session session = HibernateUtil.currentSession();
			session.getTransaction().begin();

			for (StockItem x : model.getWarehouseTableModel()
					.getTableRows()) {
				if ((Long.parseLong(barCodeField.getText()) == x.getId())) {
					session.update(addedItem);
					break;

				} else {
					session.save(addedItem);
				}
			}
			JComboBox<String> combo = new JComboBox<String>();
			for (StockItem x : model.getWarehouseTableModel().getTableRows()) {
				combo.addItem(x.getName());
			}
			model.getWarehouseTableModel().fireTableDataChanged();
			session.getTransaction().commit();
			session.close();

		} catch (NullPointerException e) {

			e.getMessage();

		} catch (NumberFormatException e) {

			System.out.println(e.getCause());
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		} catch (SessionException l) {
			l.printStackTrace();
		} catch (NonUniqueObjectException e) {
			e.printStackTrace();
		}
		
			
		}
		
	

  // table of the wareshouse stock
  private Component drawStockMainPane() {
    JPanel panel = new JPanel();

    JTable table = new JTable(model.getWarehouseTableModel());

    JTableHeader header = table.getTableHeader();
    header.setReorderingAllowed(false);

    JScrollPane scrollPane = new JScrollPane(table);

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();
    gc.fill = GridBagConstraints.BOTH;
    gc.weightx = 1.0;
    gc.weighty = 1.0;

    panel.setLayout(gb);
    panel.add(scrollPane, gc);

    panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
    return panel;
  }

}
