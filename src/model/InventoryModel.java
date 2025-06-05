package model;

import java.util.HashMap;

public class InventoryModel {
    public HashMap<String, ItemModel> inventory = new HashMap<String, ItemModel>();
    
    public void addItem(ItemModel item) {
      inventory.put(item.name, item);
    }
    
    public void removeItem(String itemName) {
      inventory.remove(itemName);
    }
}
