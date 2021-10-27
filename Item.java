
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

    /**
     * Constructor for objects of class Item
     */
    public Item()
    {
        // initialise instance variables
        itemDescription = "";
        itemWeight = 0;
    }

    /**
     * Sets the instance variables with parameter values
     */
    public Item(String description, int weight)
    {
        itemDescription = description;
        itemWeight = weight;
    }
    
    public String getItemDescription()
    {
        String itemString = "\tItem name: " + this.itemDescription + "\n";
        itemString += "\tItem Weight: " + this.itemWeight;
        return itemString;
    }
    
    /**
     * Returns the description and weight of the item found in the room the player enters
     */
    /*
    public String getItemDescription()
    {
        String itemString = "Item Description: ";
        itemString += this.itemDescription + "\n";
        itemString += "Item Weight: " + this.itemWeight;
        return itemString;
    }*/
}
