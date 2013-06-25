public class Store{
    private Item[] items;
    private String name;
    private int nItems;
    Store(String name, int nItems){
	this.name = name;
	this.nItems = nItems;
	if (nItems != 5){
	    System.err.println("Store currently only supports 5 hardwired items");
	    System.exit(1);
	}
	items = new Item[5];
	items[0] = new Item("Pinto", 2000., 100);
	items[1] = new Item("Gremlin", 1800., 100);
	items[2] = new Item("Pacer", 1750., 100);
	items[3] = new Item("Dart", 2200., 100);
	items[4] = new Item("Nova", 1450., 100);
    }

    public double getPrice(String itemName){
	Item item = findItem(itemName);
	if (item == null)
	    return(-1.);
	else
	    return(item.price);
    }   

    public String[] getListing(){
	String[] list = new String[nItems];
	for (int i = 0; i < list.length; ++i){
	    list[i] = items[i].toString();
	}
	return(list);
    }

    public synchronized int purchase (String itemName){
	Item item = findItem(itemName);
	if (item.quantity > 0){
	    --item.quantity;
	    return(1);
	}
	else{
	    return(-1);
	}
    }
    
    private Item findItem(String itemName){
	for (int i = 0; i < nItems; ++i){
	    if (items[i].name.equalsIgnoreCase(itemName)){
		return(items[i]);
	    }
	}
	return(null);
    }
}


class Item{
    protected String name;
    protected double price;
    protected int quantity;
    Item(String name, double price, int quantity){
	this.name = name;
	this.price = price;
	this.quantity = quantity;
    }

    public String toString(){
	return(this.name + ";" + this.price + ";" + this.quantity);
    }
}
