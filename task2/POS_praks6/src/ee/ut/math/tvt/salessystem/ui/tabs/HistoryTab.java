package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

    private SalesSystemModel model;

    private PurchaseInfoTableModel historyDetailsTableModel;

    private SalesDomainController domainController;
    
    public HistoryTab(SalesSystemModel model, SalesDomainController domainController) {
        this.model = model;
        this.domainController = domainController;
    }

    /**
     * The main entry-point method. Creates the tab.
     */
    public Component draw() {
        JPanel panel = new JPanel();

        GridBagConstraints gc = getGbConstraints();
        GridBagLayout gb = new GridBagLayout();

        panel.setLayout(gb);
        panel.add(drawHistoryGeneralTable(), gc);
        panel.add(drawHistoryDetailsTable(), gc);

        return panel;
    }
    
    public void refresh(){
        List <Sale> sales = domainController.getAllSales();
        model.getPurchaseHistoryTableModel().populateWithData(sales);
}


    private Component drawHistoryGeneralTable() {

        JTable table = new JTable(model.getPurchaseHistoryTableModel());
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);

        ListSelectionModel rowSM = table.getSelectionModel();

        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                // Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    int selectedRow = lsm.getMinSelectionIndex();
                    Sale sale = model.getPurchaseHistoryTableModel().getRow(selectedRow);
                    historyDetailsTableModel.showSale(sale);
                }
            }
        });

        // Wrap it inside a panel
        JPanel panel = createWrapperPanel("Sales history");
        panel.add(scrollPane, getGbConstraints());

        return panel;
    }


    private Component drawHistoryDetailsTable() {

        // Create the table
        historyDetailsTableModel = PurchaseInfoTableModel.getEmptyTable();
        JTable table = new JTable(historyDetailsTableModel);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        // Wrap it inside a panel
        JPanel panel = createWrapperPanel("Details of the selected sale");
        panel.add(scrollPane, getGbConstraints());

        return panel;
    }


    private JPanel createWrapperPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        return panel;
    }


    private GridBagConstraints getGbConstraints() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        return gc;
    }

}