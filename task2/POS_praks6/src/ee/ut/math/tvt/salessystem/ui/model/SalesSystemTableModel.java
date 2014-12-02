package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;


/**
 * Generic table model implementation suitable for extending.
 */
public abstract class SalesSystemTableModel<T extends DisplayableItem> extends
        AbstractTableModel {

    private static final long serialVersionUID = 1L;

    protected final String[] headers;

    public SalesSystemTableModel(final String[] headers) {
        this.headers = headers;
    }

    /**
     * @param item
     *            item describing selected row
     * @param columnIndex
     *            selected column index
     * @return value displayed in column with specified index
     */
    //Abstract method; rows removed 
    protected abstract Object getColumnValue(T item, int columnIndex);

    public int getColumnCount() {
        return headers.length;
    }
    
    public int getRowCount() {
        return getTableRows().size();
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return headers[columnIndex];
    }
    
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return getColumnValue(getTableRows().get(rowIndex), columnIndex);
    }


    public abstract List<T> getTableRows();

    abstract public void clear();
    
    abstract public void addRow(T row);
    
    abstract public T getRow(int index);

    
}