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
        System.out.println("Enter the maximum weight the player can carry");
        int weight = reader.nextInt();
        player.setMaximumWeight(weight);
    }
    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // Each room of the game
        Room outside, theater, pub, lab, office;
        // Items for each room
        Item outsideItems[] = {new Item ("Rope","Rope: could be useful" , 50),
                               new Item ("Bench","Bench: to sit on", 10000),
                               new Item ("Flower","Flower: smells great" , 5) };
                               
        Item theaterItem[] = { new Item ("Projector", "Projector: to display videos" , 1500),
                               new Item ("Popcorn:","Popcorn: +20hp" , 100),
                               new Item ("Wallet","Wallet: contains $20", 150) };
                               
        Item pubItem[] = {     new Item ("Beer","Beer: -5 health", 15),
                               new Item ("Soda","Soda: +10hp", 50),
                               new Item ("Apple","Apple: +50hp", 250) };
                               
        Item labItem[] = {     new Item ("Laptop","Laptop: email - Hello Matt I left you a special apple in my office for you to try", 1000), 
                               new Item ("Lighter","Lighter: could light up a room" , 100),
                               new Item ("Strange mushroom","Strang mushroom: -100hp", 5) };
                               
        Item officeItem[] = {  new Item ("Keys","Keys: can unlock certain doors", 250),
                               new Item ("Golden apple","Golden apple: +1000 carry capacity", 15),
                               new Item ("Flashlight", "Flashlight: can light up dark areas", 150) };
                                
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // add each item into each room
        outside = addItemsToRoom(outside, outsideItems);
        theater = addItemsToRoom(theater, theaterItem);
        pub = addItemsToRoom(pub, pubItem);
        lab = addItemsToRoom(lab, labItem);
        office = addItemsToRoom(office, officeItem);
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

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
                
            case EAT:
                eat();
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
    
    
    
}
