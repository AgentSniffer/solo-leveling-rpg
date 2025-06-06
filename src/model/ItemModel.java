package model;

public class ItemModel {

    public String name, description, type;
    public int value;

    public ItemModel(String newName, String newDescription, String newType, int newValue) {
        name = newName;
        description = newDescription;
        type = newType;
        value = newValue;
    }
}
