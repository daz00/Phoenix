package ee.ut.math.tvt.salessystem.domain.controller.impl;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

    private static final Logger log = Logger.getLogger(SalesDomainControllerImpl.class);

    private SalesSystemModel model;

    private Session session = HibernateUtil.currentSession();

    @SuppressWarnings("unchecked")
    public List<StockItem> getAllStockItems() {
        List<StockItem> result =
            session
                .createQuery("from StockItem")
                .list();

        log.info(result.size() + " items loaded from disk");

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Sale> getAllSales() {
        List<Sale> result = session.createQuery("from Sale").list();
        log.info(result.size() + " Sales loaded from disk");

        return result;
    }


    @SuppressWarnings("unchecked")
    public List<Client> getAllClients() {
        List<Client> clients =
            session.createQuery("from Client").list();

        log.info(clients.size() + " clients loaded from disk");

        return clients;
    }

    public Client getClient(long id) {
        return (Client) session.get(Client.class, id);
    }


    private StockItem getStockItem(long id) {
        return (StockItem) session.get(StockItem.class, id);
    }


    public void registerSale(Sale sale) {

        // Begin transaction
        Transaction tx = session.beginTransaction();

        sale.setSellingTime(new Date());


        // Reduce quantities of stockItems in warehouse
        for (SoldItem item : sale.getSoldItems()) {
            // Associate with current sale
            item.setSale(sale);

            StockItem stockItem = getStockItem(item.getStockItem().getId());
            stockItem.setQuantity(stockItem.getQuantity() - item.getQuantity());
            session.save(stockItem);
        }

        session.save(sale);

        // end transaction
        tx.commit();
        model.getPurchaseHistoryTableModel().addRow(sale);
        HibernateUtil.closeSession();
        session=HibernateUtil.currentSession();


    }


    public void createStockItem(StockItem stockItem) {
        // Begin transaction
        Transaction tx = session.beginTransaction();
        session.save(stockItem);
        tx.commit();
        model.getWarehouseTableModel().addRow(stockItem);
        log.info("Added new stockItem : " + stockItem);
    }


    public void cancelCurrentPurchase() {
        log.info("Current purchase canceled");
    }

    public void startNewPurchase() {
        log.info("New purchase started");
    }



    public void setModel(SalesSystemModel model) {
        this.model = model;
    }


    public Sale getSale(Long id) {
        return (Sale) session.get(Sale.class, id);
    }

    @Override
    public void endSession() {
        HibernateUtil.closeSession();
    }

}