package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
@SuppressWarnings("unchecked")
public class SalesDomainControllerImpl implements SalesDomainController {
	private Session session = HibernateUtil.currentSession();

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		// XXX - Save purchase
	}

	public void submitCurrentPurchase(List<SoldItem> goods,
			SalesSystemModel model) throws VerificationFailedException {

	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		return session.createQuery("from StockItem").list();
	  }

	public List<SoldItem> getGoods() {
		
		return session.createQuery("from SoldItem").list();
	}
	
	public List<HistoryItem> getSales(){
		return session.createQuery("from HistoryItem").list();
	}
	
	public void save(DisplayableItem entity) {
	    Transaction transaction = session.beginTransaction();
	    if (entity.getId() == null) {
	      session.save(entity);
	    } else {
	      session.update(entity);
	    }
	    transaction.commit();
	  }

	public void endSession() {
		try {
			HibernateUtil.closeSession();
		} catch (SessionException ee) {
			ee.printStackTrace();
		} finally {
			System.exit(0);
		}

	}

}