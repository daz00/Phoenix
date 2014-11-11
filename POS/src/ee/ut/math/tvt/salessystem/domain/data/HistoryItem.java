package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * History item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "HistoryItem")
public class HistoryItem implements Cloneable, DisplayableItem {
	@Column(name = "sale_date")
	private String date;

	@Column(name = "sale_time")
	private String time;

	@Column(name = "price_total")
	private double price;

	@OneToMany(mappedBy = "historyitem")
	private List<SoldItem> sold;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	
	/**
     * Constructs new  <code>HistoryItem</code>.
     * @param id
     * @param date
     * @param time
     * @param price
     */
	public HistoryItem(){
		
	}
	
    
	    
	    public HistoryItem(List<SoldItem> sold) {
	    	Date dNow = new Date();
	        SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd");
	        date = ft.format(dNow);
	        Date timeNow = new Date();
	        SimpleDateFormat ft2 =  new SimpleDateFormat ("HH:mm:ss");
	        time = ft2.format(timeNow);
	    	
	    	this.sold = sold;
	    	
	    	
	    	
	    }

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public List<SoldItem> getSold() {
			return sold;
		}

		public void setSold(List<SoldItem> sold) {
			this.sold =sold;
		}
		//price
		public String getTotalPrice() {
			Double money = 0.0;
			for (final SoldItem item : sold) {
				money += item.getPrice();
			}
			money = Math.round(money * 100) / 100.0;
			return money.toString();
		}

		@Override
		public Integer getId() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setId(Integer itemId) {
			this.id = itemId;
		}
		public void addGoods(SoldItem item){
			item.setHistoryItem(this);
			sold.add(item);
			
			
		}

}

