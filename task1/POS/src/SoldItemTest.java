import static org.junit.Assert.*;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;


public class SoldItemTest {

	@Test
	public void testGetSum() {
		StockItem stockItem = new StockItem(1, "test", "#Yolo", 33);
		SoldItem soldItem = new SoldItem(stockItem, 10);
		assertTrue(soldItem.getSum() == 330);
	}
	
	@Test
	public void testGetSumWithZeroQuantity() {
		StockItem stockItem = new StockItem(1, "test", "#Yolo", 33);
		SoldItem soldItem = new SoldItem(stockItem, 0);
		assertTrue(soldItem.getSum() == 0);
	}

}
