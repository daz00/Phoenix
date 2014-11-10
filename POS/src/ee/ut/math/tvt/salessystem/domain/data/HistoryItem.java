package ee.ut.math.tvt.salessystem.domain.data;

import java.text.Format;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	private double sum;
    
	    
	    public HistoryItem(List<SoldItem> sold) {
	    	Date dNow = new Date();
	        SimpleDateFormat ft =  new SimpleDateFormat ("dd.MM.yyyy");
	        date = ft.format(dNow);
	        Date timeNow = new Date();
	        SimpleDateFormat ft2 =  new SimpleDateFormat ("HH:mm:ss");
	        time = ft2.format(timeNow);
	       
	        double sum = 0;
	   
	    	
	    	for(int i = 0; i < sold.size(); i++) {
	    		sum += sold.get(i).getPrice();
	    	}
	    	
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

		public double getSum() {
			return sum;
		}

		public void setSum(double sum) {
			this.sum = sum;
		}

		public List<SoldItem> getSold() {
			return sold;
		}

		public void setSold(List<SoldItem> sold) {
			this.sold =sold;
		}
		//Sum
		public String getTotalSum() {
			Double money = 0.0;
			for (final SoldItem item : sold) {
				money += item.getSum();
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

