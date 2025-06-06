package model;

import java.util.HashMap;

public class InventoryModel {

    public HashMap<String, ItemModel> items = new HashMap<String, ItemModel>();

    public void addItem(ItemModel item) {
        items.put(item.name, item);
    }

    public void removeItem(String name) {
        items.remove(name);
    }
}
