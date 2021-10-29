import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Matthew Lee
 * @version 10.26.21
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private Item roomItem;
    private ArrayList<Item> roomItems;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        // array list for all the items in the game
        roomItems = new ArrayList<Item>();
    }
    
    /**
     * Gets the arrayList of the items added into each room
     */
    public ArrayList<Item> getRoomItems()
    {
        return roomItems;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    
    /**
     * Gets the item if the item object is present in the room
     */
    public Item getItem(String itemName)
    {
        for (int i = 0; i < roomItems.size(); i++){
            if(roomItems.get(i).getItemName().equalsIgnoreCase(itemName)) {
                return roomItems.get(i);
            }
        }
        return null;
    }
    
    /**
     * Removes the item from the player
     */
    public void removeItem(Item item)
    {
        for(int i = 0; i < roomItems.size(); i++) {
            if(roomItems.get(i) == item) {
                roomItems.remove(item);
                break;
            }
        }
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getItemsInRoom() + getExitString();
    }
    
    /**
     * Returns all of the items in the current room the player is in
     */
    public String getItemsInRoom()
    {
        String returnItems = "Items in the room are: \n";
        for(Item item : roomItems) {
            returnItems += item.getItemDescription() + "\n";
        }
        return returnItems;
    }
    
    /**
     * Adds items to a room
     */
    public void addItem(Item item)
    {
        roomItems.add(item);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "\n" + "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

