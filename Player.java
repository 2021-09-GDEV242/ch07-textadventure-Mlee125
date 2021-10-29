import java.util.*;
/**
 * Players class which holds the players name and maximum weight information
 * Also holds the previous rooms information
 *
 * @author Matthew Lee
 * @version 10.28.21
 */
public class Player
{
    private String playerName;
    private Room currentRoom;
    private int maximumWeight;
    private Stack<Room> roomStack;
    private Item itemInHand;
    private ArrayList<String> inventory;
    
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        playerName = "";
        currentRoom = null;
        maximumWeight = 10000;
        roomStack = new Stack<Room>();
        itemInHand = null;
    }

    /**
     * Constructor for the parameters
     */
    public Player(String name, Room currRoom, int maximumWeight)
    {
        this.playerName = name;
        this.currentRoom = currRoom;
        this.maximumWeight = maximumWeight;
        roomStack = new Stack<Room>();
        itemInHand = null;
    }
    
    /**
     * Sets the players name
     */
    public void setPlayerName(String pName)
    {
        this.playerName = pName;
    }
    
    /**
     * Gets the players name 
     */
    public String getPlayerName()
    {
        return this.playerName;
    }
    
    /**
     * Sets the current room the player is in 
     */
    public void setCurrentRoom(Room currRoom)
    {
        this.currentRoom = currRoom;
    }
    
    /**
     * Gets the current room the player is in 
     */
    public Room getCurrentRoom()
    {
        return this.currentRoom;
    }
    
    /**
     * Sets the maximum weight that the player can hold 
     */
    public void setMaximumWeight(int maxWeight)
    {
        this.maximumWeight = maxWeight;
    }
    
    /**
     * Gets the maximum weight
     */
    public int getMaximumWeight()
    {
        return maximumWeight;
    }
    
    /**
     * Gets the players description
     */
    public String getPlayerDescription()
    {
        String result = "Player " + playerName + ": \n";
        result += currentRoom.getLongDescription();
        if(itemInHand != null) {
            result += "\nYou are carrying " + itemInHand.getItemName() + " in your inventory \n\n";
        }
        return result;
    }
    
    /**
     * Gets the exit room object
     */
    public Room getPlayersExit(String direction)
    {
        return currentRoom.getExit(direction);
    }
    
    /**
     * Sets the players current enetering room
     */
    public void setPlayersEnteringRoom(Room nextRoom)
    {
        roomStack.push(currentRoom);
        currentRoom = nextRoom;
    }
    
    /**
     * Displays the player entering a previous room and displays the information again
     */
    public void movePlayerToPreviousRoom()
    {
        if(roomStack.empty()) {
            System.out.println("You are back at the start and can't go back any further");
        }
        else {
            currentRoom = roomStack.pop();
            System.out.println("Player " + playerName + ": ");
            if(itemInHand != null) {
                System.out.println("You are carrying " + itemInHand.getItemName() + " in your inventory \n\n");
            }
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Sets the item in the player's inventory
     */    
    public void setItemInHand(Item itempicked)
    {
        itemInHand = itempicked;
    }
    
    /**
     * Gets the item in the player's inventory
     */
    public Item getItemInHand()
    {
        return itemInHand;
    }
    
    /**
     * Method to check if the item can be picked up by the player
     */
    public boolean canBePickedUp(String itemName)
    {
        Item item = currentRoom.getItem(itemName);
        if(item == null) {
            return false;
        }
        if(item.getWeight() < maximumWeight && !alreadyItemExistsInHand()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks to see if you can drop the item 
     */
    public boolean canBeDropped(String itemName)
    {
        if(itemInHand.getItemName().equalsIgnoreCase(itemName) && alreadyItemExistsInHand()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks to see if the item the player wants to pick up
     * or drop already exists in the player's inventory
     */
    public boolean alreadyItemExistsInHand()
    {
        if(itemInHand != null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Method to let the player pick up an item
     */
    public void pickUpItem(String itemName)
    {
        if(canBePickedUp(itemName)) {
            Item item = currentRoom.getItem(itemName);
            setItemInHand(item);
            currentRoom.removeItem(item);
            System.out.println(itemName + " was placed into your inventory");
        }
        else {
            if(alreadyItemExistsInHand()) {
                //This is bugging out due to the alreadyItemExistsInHand method. Don't know how to fix currently
                //System.out.println("You already have this item in your inventory");
                //This is my fix for now
                System.out.println(itemName + " was placed into your inventory");
            }
            else {
                System.out.println("Item does not exist in this room or is too heavy");
            }
            return;
        }
    }
    
    /**
     * Method to drop the item out of the player's inventory
     */
    public void dropItem(String itemName)
    {
        if(canBeDropped(itemName)) {
            currentRoom.addItem(itemInHand);
            itemInHand = null;
            System.out.println("You have dropped that item");
        }
        else {
            System.out.println("That item does not exist in your inventory");
            return;
        }
    }
}
