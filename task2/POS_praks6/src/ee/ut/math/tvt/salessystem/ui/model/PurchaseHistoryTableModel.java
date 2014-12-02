package ee.ut.math.tvt.salessystem.ui.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.Sale;

/**
 * Purchase history model.
 */
public class PurchaseHistoryTableModel extends SalesSystemTableModel<Sale> {
	private static final long serialVersionUID = 1L;

	private List<Sale> rows;

	private static DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm:ss");

	public PurchaseHistoryTableModel() {
		super(new String[] { "Id", "Time", "Sum", "Client" });
		this.rows = new ArrayList<Sale>();
	}

	@Override
	protected Object getColumnValue(Sale sale, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return sale.getId();
		case 1:
			return DATE_FORMAT.format(sale.getSellingTime());
		case 2:
			return sale.getSum();
		case 3:
			return sale.getClient();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final Sale sale : rows) {
			buffer.append(sale.getId() + "\t");
			buffer.append(sale.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	@Override
	public List<Sale> getTableRows() {
		return rows;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getColumnValue(rows.get(rowIndex), columnIndex);
	}

	public void populateWithData(List<Sale> sales) {
		rows.clear();
		rows.addAll(sales);
		fireTableDataChanged();
	}

	public Sale getRow(int selectedRow) {
		return rows.get(selectedRow);
	}

	public void addRow(Sale sale) {
		rows.add(sale);
		fireTableDataChanged();

	}

	@Override
	public void clear() {
		rows.clear();
		fireTableDataChanged();

	}
}