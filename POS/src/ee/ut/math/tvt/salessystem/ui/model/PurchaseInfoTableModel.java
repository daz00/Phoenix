package ee.ut.math.tvt.salessystem.ui.model;

import java.util.NoSuchElementException;

import javax.swing.JComboBox;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final SoldItem item) {
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).getStockItem().getId() == item.getStockItem()
					.getId()) {
				SoldItem existingItem = rows.get(i);
				existingItem.setQuantity(existingItem.getQuantity()
						+ item.getQuantity());
				fireTableDataChanged(existingItem);
				return;
			}
		}

		rows.add(item);
		fireTableDataChanged(item);
	}

	public void fireTableDataChanged(SoldItem item) {
		log.debug("Added " + item.getName() + " quantity of "
				+ item.getQuantity());
		fireTableDataChanged();
	}

	public JComboBox<String> setItems(JComboBox<String> combo, StockItem item) {

		combo.addItem(item.getName());
		log.debug("Added " + item.getName() + "to ComboBox ");
		return combo;
	}

	public double getSum() {
		double summa = 0.0;
		for (SoldItem soldItem : getTableRows()) {
			summa += soldItem.getSum();
		}
		return summa;
	}

}
