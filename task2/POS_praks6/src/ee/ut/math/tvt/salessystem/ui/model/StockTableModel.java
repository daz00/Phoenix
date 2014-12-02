package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);
	
	private List<StockItem> rows;

	public StockTableModel() {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
		this.rows=new ArrayList<StockItem>();
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	public StockItem getItemById(long id) {
        for (StockItem item : rows){
                if(item.getId()==id){
                        return item;
                }
        }
        throw new NoSuchElementException();
}

	
	
	public boolean hasEnoughInStock(StockItem item, int quantity) {
	    for(StockItem i : this.rows) {
	        if (i.getId().equals(item.getId())) {
	            return (i.getQuantity() >= quantity);
	        }
	    }
	    return false;
	}
	
	public boolean validateNameUniqueness(String newName) {
	    for (StockItem item : rows) {
	        log.debug(" === Comparing: " + newName + " vs. " + item.getName());
	        
	        if (newName.equals(item.getName())) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	@Override
	public List<StockItem> getTableRows() {
		return rows;
	}

	public void populateWithData(List<StockItem> stockItems) {
		rows.clear();
        rows.addAll(stockItems);
        fireTableDataChanged();
		
	}


	public void addRow(StockItem stockItem) {
        rows.add(stockItem);
        fireTableDataChanged();
        
}

	@Override
	public void clear() {
		rows.clear();
        fireTableDataChanged();
		
	}

	@Override
	public StockItem getRow(int index) {
		return getTableRows().get(index);
	}

}