import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;


public class StockTableModelTest {

	@Test
	public void testValidateNameUniqueness() {
		StockTableModel stockTableModel = new StockTableModel();
		StockItem stockItem1 = new StockItem(1, "test1", "#Yolo", 33);
		StockItem stockItem2 = new StockItem(2, "test2", "#Swag", 22);
		StockItem stockItem3 = new StockItem(3, "test3", "#YMCMB", 11);
		stockTableModel.addItem(stockItem1);
		stockTableModel.addItem(stockItem2);
		stockTableModel.addItem(stockItem3);
		int numberOfRows = stockTableModel.getRowCount();
		List<StockItem> stockItemList = stockTableModel.getTableRows();
		boolean exists = false;
		for (int i = 0; i < numberOfRows; i++) {
		StockItem curStockItem = stockItemList.get(i);
		for (int j = i + 1; j < numberOfRows; j++) {
		if (curStockItem.getName().equals(stockItemList.get(j).getName())) {
		exists = true;
			}
		}
		}
		assertFalse(exists);
		}
	
	
	@Test
	public void testHasEnoughInStock(){
		StockTableModel stockTableModel = new StockTableModel();
		StockItem stockItem1 = new StockItem(1, "test1", "#Yolo", 33, 22);
		stockTableModel.addItem(stockItem1);
		int quantity = (int)stockTableModel.getValueAt(0, 3);
		assertTrue(quantity > 20);
	}
		
	
	@Test
	public void testGetItemByIdWhenItemExists(){
		StockTableModel stockTableModel = new StockTableModel();
		StockItem stockItem1 = new StockItem(1, "test1", "#Yolo", 33);
		stockTableModel.addItem(stockItem1);
		assertNotNull(stockTableModel.getItemById(1));
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException(){
		StockTableModel stockTableModel = new StockTableModel();
		StockItem stockItem1 = new StockItem(1, "test1", "#Yolo", 33);
		stockTableModel.addItem(stockItem1);
		stockTableModel.getItemById(2);
		
		
	}	
}