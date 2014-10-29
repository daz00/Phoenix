package ee.ut.math.tvt.salessystem.ui.model;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;

public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {

	private static final long serialVersionUID = 1L;


	public HistoryTableModel() {
		//Define new headings
		super(new String[] { "Date", "Time", "Total price" });
	}
	//Add items
	public void addItem(HistoryItem item) {
		rows.add(item);
		fireTableDataChanged();
	}

	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getTotalSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

}
