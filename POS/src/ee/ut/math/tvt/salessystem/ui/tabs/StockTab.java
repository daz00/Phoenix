package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.table.JTableHeader;


public class StockTab {

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
  private Component drawStockMenuPane() {
    JPanel panel = new JPanel();
    GridBagConstraints gc = new GridBagConstraints();

    panel.setBorder(BorderFactory.createTitledBorder("Product"));
    

    GridBagLayout gb = new GridBagLayout();
    

    panel.setLayout(gb);
    panel.setLayout(new GridLayout(6, 2));
    // Initialize the textfields
    final JTextField barCodeField = new JTextField();
    final JTextField quantityField = new JTextField();
    final JTextField priceField = new JTextField();
    final JTextField nameField = new JTextField();
    final JTextField description = new JTextField();

    
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
    
    //TODO Järgnev kood tuleks kuidagi ümber muuta või õigesse kohta vahele lükata
    /*addItem.addActionListener(new ActionListener() {
   	 
        public void actionPerformed(ActionEvent e)
        {
            StockItem addedItem = new StockItem(Long.parseLong(barCodeField.getText()),
            		nameField.getText(),
            		description.getText(),
            		Double.parseDouble(quantityField.getText()),
            		Integer.getInteger(priceField.getText()));
            System.out.println(addedItem.getName());
        }
    });*/

    addItem = new JButton("Add");
    gc.gridwidth = GridBagConstraints.RELATIVE;
    gc.weightx = 1.0;
    panel.add(addItem, gc);

    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    return panel;
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
