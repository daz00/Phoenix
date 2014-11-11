package ee.ut.math.tvt.salessystem.domain.data;
import javax.persistence.*;



@Entity
@Table(name = "SoldItem")
/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
public class SoldItem implements Cloneable, DisplayableItem {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "stockitem_id")
	private StockItem stockItem;


	private String name;
              
    @ManyToOne
    @JoinColumn (name="historyitem_id")
    private HistoryItem historyitem;

	@Column(name = "quantity")
	private Integer quantity;

	private double price;
    
    public SoldItem(StockItem stockItem, int quantity) {
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
        
    }
    
    
   public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    public HistoryItem getHistoryItem() {
		return historyitem;
	}

	public void setHistoryItem(HistoryItem newHistoryItem) {
		this.historyitem = newHistoryItem;
		this.historyitem=newHistoryItem;
	}
    
}
