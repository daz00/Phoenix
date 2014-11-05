package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab extends JPanel {
	private SalesSystemModel model;
	private static final long serialVersionUID = 1L;

    public HistoryTab(SalesSystemModel model) {
        this.model = model;
      }
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
    private Component drawStockMenuPane() {
        JPanel panel = new JPanel();

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();

        panel.setLayout(gb);

        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.weightx = 0;


        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
      }
    
    public void aken(JTable table, int rowindex){
    	PurchaseInfoTableModel purchase = new PurchaseInfoTableModel();
    	JTable infoTable = new JTable(purchase);
    	JFrame window = new JFrame();
  	  	window.setTitle("Purchase information");
  	  	JPanel panel = new JPanel();
  	  	

        HistoryItem cur = model.getHistoryTableModel().getTableRows().get(rowindex);
    	for(SoldItem i : cur.getSold()) {
    		purchase.addItem(i);
    	}

  	  
  	  	window.setLayout(new GridLayout(2,2));

        panel.setBorder(BorderFactory.createTitledBorder("Detailed purchase information"));
          
         panel.add(infoTable);
         window.setSize(400, 300);
         window.add(panel);
         
          window.setVisible(true);
          window.setLocationRelativeTo(null);
          window.pack();
    	}

                
     
    private Component drawStockMainPane() {
        final JPanel panel = new JPanel();
        final JTable table = new JTable(model.getHistoryTableModel());
        int rida = 0;
        
       
    
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                	table.clearSelection();
                }

                final int rowindex = table.getSelectedRow();
                if (rowindex < 0)
                    return;
                else {
                	 JPopupMenu popupMenu = new JPopupMenu();
                     JMenuItem info = new JMenuItem("Info");
                     info.addActionListener(new ActionListener() {

                         @Override
                         public void actionPerformed(ActionEvent e) {
                        	 System.out.print(rowindex);
                             aken(table,rowindex);
                         }
                     });
                	 popupMenu.add(info);
                     table.setComponentPopupMenu(popupMenu);
                     table.clearSelection();
                	
                }
            }
        });
    

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

        panel.setBorder(BorderFactory.createTitledBorder("Purchase history"));
        return panel;
      }

}