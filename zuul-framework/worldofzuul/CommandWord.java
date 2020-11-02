package worldofzuul;

public enum CommandWord
{
    GO("go"), QUIT("quit"), HELP("help"), COLLECT("collect"), UNKNOWN("?"),
    GET("get"), INVENTORY("inventory");
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
