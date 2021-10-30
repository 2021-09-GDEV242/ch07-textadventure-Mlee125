
/**
 * Item class used to get the description and weight of an item object
 *
 * @author Matthew Lee
 * @version 10.25.21
 */
public class Item
{
    // instance variables
    private String itemDescription;
    private int itemWeight;
    private String name;
    

    /**
     * Constructor for objects of class Item
     */
    public Item()
    {
        // initialise instance variables
        name = "";
        itemDescription = "";
        itemWeight = 0;
    }
    
    /**
     * A method to get the item name
     */
    public String getItemName()
    {
        return name;
    }
    
    /**
     * A method to get the items weight
     */
    public int getWeight()
    {
        return itemWeight;
    }
    
    /**
     * Sets the instance variables with parameter values
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        itemDescription = description;
        itemWeight = weight;
    }
    
    /**
     * Returns the name, description and weight of the item found in the room the player enters
     */
    public String getItemDescription()
    {
        String itemString = "\tName: " + this.name;    
        itemString = "\n\tItem:" + this.itemDescription + "\n";
        itemString += "\tItem Weight: " + this.itemWeight + "\n";
        return itemString;
    }
}
