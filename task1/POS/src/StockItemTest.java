import static org.junit.Assert.*;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;


public class StockItemTest {

	@Test
	public void testClone() {
		StockItem stockItem = new StockItem(2, "testName", "testDesc", 3);
		StockItem clonedStockItem = (StockItem) stockItem.clone();
		assertTrue(clonedStockItem.getBarCode() == 2);
	}
	
	@Test
	public void testGetColumn() {
		StockItem stockItem1 = new StockItem(1, "test1", "#Yolo", 33);
		assertTrue(stockItem1.getColumn(0).equals(1));
	}

}
