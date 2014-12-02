import static org.junit.Assert.*;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;


public class SaleTest {

	@Test
	public void testAddSoldItem() {
		StockItem stockItem = new StockItem();
		SoldItem soldItem = new SoldItem(stockItem, 10);
		PurchaseInfoTableModel purchaseInfoTableModel = new PurchaseInfoTableModel();
		purchaseInfoTableModel.addItem(soldItem);
		assertTrue(purchaseInfoTableModel.getRowCount() == 1);
	}
	
	@Test
	public void testGetSumWithNoItems() {
		PurchaseInfoTableModel purchaseInfoTableModel = new PurchaseInfoTableModel();
		assertTrue(purchaseInfoTableModel.getSum() == 0);
	}
	
	@Test
	public void testGetSumWithOneItem() {
		StockItem stockItem = new StockItem(1, "test", "#Yolo", 33);
		SoldItem soldItem = new SoldItem(stockItem, 10);
		PurchaseInfoTableModel purchaseInfoTableModel = new PurchaseInfoTableModel();
		purchaseInfoTableModel.addItem(soldItem);
		assertTrue(purchaseInfoTableModel.getSum() == 330);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {
		StockItem stockItem1 = new StockItem(1, "test1", "#Yolo", 33);
		StockItem stockItem2 = new StockItem(2, "test2", "#Swag", 22);
		SoldItem soldItem1 = new SoldItem(stockItem1, 10);
		SoldItem soldItem2 = new SoldItem(stockItem2, 10);
		PurchaseInfoTableModel purchaseInfoTableModel = new PurchaseInfoTableModel();
		purchaseInfoTableModel.addItem(soldItem1);
		purchaseInfoTableModel.addItem(soldItem2);
		assertTrue(purchaseInfoTableModel.getSum() == 550);
		
	}

}
