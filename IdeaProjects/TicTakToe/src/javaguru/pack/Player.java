package javaGuru.pack;

public class Player {

    String name;
    String surName;
    char playerSymbol;

    public Player(String name , String surName , char playerSimbol)
    {
        this.name = name;
        this.surName = surName;
        this.playerSymbol = playerSimbol;
    }

    public String getName()
    {
          return name;
    }

    public String getSurName()
    {
        return surName;
    }

    public char getPlayerSymbol()
    {
        return playerSymbol;
    }

    public void setName(String name)
    {
         this.name = name;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public void setPlayerSymbol(char playerSymbol)
    {
        this.playerSymbol = playerSymbol;
    }

}