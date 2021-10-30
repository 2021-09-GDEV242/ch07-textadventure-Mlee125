import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Matthew Lee
 * @version 10.26.21
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Scanner reader;
    private Player player;
    private ArrayList<String> inventory;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player();
        inventory = new ArrayList<>();
        reader = new Scanner(System.in);
    }
    
    /**
     * Creates the player and the players stats into the game
     */
    private void createPlayer()
    {
        System.out.println("Enter Player Name: ");
        String name = reader.nextLine();
        player.setPlayerName(name);
        createRooms();
        //System.out.println("Enter the maximum weight the player can carry");
        //int weight = reader.nextInt();
        //player.setMaximumWeight(weight);
    }
    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // Each room of the game
        Room outside, entrence, bathroom, book, foodhall, hiking, hidden, computer, coffee, clothing, phone, security, safe, shoe, exit;
        // Items for each room
        Item outsideItems[] = {new Item ("Rope","Rope: could be useful" , 50),
                               new Item ("Bench","Bench: to sit on", 10000),
                               new Item ("Flower","Flower: smells great" , 5) };
                               
        Item bathroomItem[] = { new Item ("Toliet", "Toliet" , 10000),
                                new Item ("Sink", "Sink", 10000)};
                               
        Item bookItem[] = {     new Item ("Book", "Book: A book on mazes", 200)};
                               
        Item foodhallItem[] = {new Item ("Pizza","Pizza: +25 health", 15),
                               new Item ("Soda","Soda: +10hp", 50),
                               new Item ("Apple","Apple: +50hp", 250) };
                               
        Item hikingItem[] = {  new Item ("Jacket","Jacket: A jacket to keep you warm", 250),
                               new Item ("Flashlight", "Flashlight: can light up dark areas", 150) };
                               
        Item hiddenItem[] = { new Item("cookie", "Magic cookie: + 2000 carry weight", 100)};
        
        Item computerItem[] = { new Item ("Laptop", "Laptop: NEW EMAIL! - Hey Paul I left you the keys to the exit in the safe for you", 10000)};
        
        Item coffeeItem[] = { new Item ("Coffee", "Coffee: +10hp", 25),
                              new Item ("Water", "Water: + 30hp", 30)};
                              
        Item clothingItem[] = { new Item ("Shirt", "Shirt", 50),
                                new Item("Pants", "Pants", 50)};
                                
        Item phoneItem[] = { new Item ("Phone", "Phone: use to call for a hint", 50)};
        
        Item securityItem[] = { new Item ("Cameras", "Cameras: Shows the exit somewhere south", 100000)};
        
        Item safeItem[] = { new Item ("Keys", "Keys: You found the keys to the exit!!!", 10)};
        
        Item shoeItem[] = { new Item ("Shoes", "Shoes: New shoes + 50 carry weight", 10)};
        
        
        
                              
                              
        
                                
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        entrence = new Room("You enter the mall and see many stores for you to check out");
        bathroom = new Room("in the bathroom");
        book = new Room("in a book store");
        foodhall = new Room("in the food hall");
        hiking = new Room("in a hiking/adventure store");
        hidden = new Room("You found a hidden room!!!");
        computer = new Room("in a computer store");
        coffee = new Room("in a cafe");
        clothing = new Room("in a clothing store");
        phone = new Room("in a phone store");
        security = new Room("in the security room with a camera feed of the mall");
        safe = new Room("You found a hidden safe!!!");
        shoe = new Room("in a shoe store");
        exit = new Room("You found the exit!!! - insert keys to exit");
        
        // add each item into each room
        outside = addItemsToRoom(outside, outsideItems);
        bathroom = addItemsToRoom(bathroom, bathroomItem);
        book = addItemsToRoom(book, bookItem);
        foodhall = addItemsToRoom(foodhall, foodhallItem);
        hiking = addItemsToRoom(hiking, hikingItem);
        computer = addItemsToRoom(computer, computerItem);
        hidden = addItemsToRoom(hidden, hiddenItem);
        coffee = addItemsToRoom(coffee, coffeeItem);
        clothing = addItemsToRoom(clothing, clothingItem);
        phone = addItemsToRoom(phone, phoneItem);
        security = addItemsToRoom(security, securityItem);
        safe = addItemsToRoom(safe, safeItem);
        shoe = addItemsToRoom(shoe, shoeItem);
        
        // initialise room exits
        outside.setExit("south", entrence);

        entrence.setExit("north", outside);
        entrence.setExit("east", book);
        entrence.setExit("south", foodhall);
        entrence.setExit("west", bathroom);

        bathroom.setExit("east", entrence);
        
        book.setExit("west", entrence);

        foodhall.setExit("north", entrence);
        foodhall.setExit("east", computer);
        foodhall.setExit("south", coffee);
        foodhall.setExit("west", hiking);
        
        hiking.setExit("west", hidden);
        hiking.setExit("east", foodhall);
        
        hidden.setExit("east", hiking);

        computer.setExit("west", foodhall);
        
        coffee.setExit("north", foodhall);
        coffee.setExit("east", security);
        coffee.setExit("south", shoe);
        coffee.setExit("west", clothing);
        
        clothing.setExit("east", coffee);
        clothing.setExit("south", phone);
        
        phone.setExit("north", clothing);
        phone.setExit("east", shoe);
        
        security.setExit("east", safe);
        security.setExit("west", coffee);
        
        safe.setExit("west", security);
        
        shoe.setExit("north", coffee);
        shoe.setExit("west", phone);
        shoe.setExit("south", exit);

        player.setCurrentRoom(outside);// Start the game outside
    }

    /**
     * Method to add each item to a given room
     */
    public Room addItemsToRoom(Room room, Item items[])
    {
        for (int i = 0; i < items.length; i++) {
            room.addItem(items[i]);
        }
        return room;
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        // calls the createPlayer() method
        createPlayer();
        
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getPlayerDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case BACK:
                backRoom();
                break;
                
            case TAKE:
                pickUpItemFromRoom(command);
                break;
                
            case DROP:
                dropItemInHand(command);
                break;
                
            case INVENTORY:
                printItem();
                break;
                
            case EATCOOKIE:
                eatCookie();
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getPlayersExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setPlayersEnteringRoom(nextRoom);
            System.out.println(player.getPlayerDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * "Look" was entered. Print out the description of the room
     * and the exits that are available
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * "Eat" was entered. Player eats food and prints out a text that you have eaten
     */
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry anymore");
    }
    
    /**
     * Goes back to the previous room you were in and prints out the description again
     */
    public void backRoom()
    {
        player.movePlayerToPreviousRoom();
    }
    
    /**
     * A method to take the command take from the player and pick up the item
     */
    public void pickUpItemFromRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("That item does not exist in this room");
            return;
        }
        String itemName = command.getSecondWord();
        player.pickUpItem(itemName);
        inventory.add(itemName);
    }
    
    /**
     * A method to take the command drop from the player and drop that item
     */
    public void dropItemInHand(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("That item does not exist in your inventory");
            return;
        }
        String itemName = command.getSecondWord();
        player.dropItem(itemName);
        inventory.remove(itemName);
    }
    
    /**
     * Prints out the player's inventory
     */
    public void printItem()
    {
        System.out.println("\nItems in your inventory: " + inventory);
    }
    
    public void eatCookie()
    {
        Item cookie = player.getCurrentRoom().getItem("cookie");
        if(cookie != null) {
            player.eatCookie();
            player.getCurrentRoom().removeItem(cookie);
        }
        else {
            System.out.println("No cookie");
        }
    }
    
}
