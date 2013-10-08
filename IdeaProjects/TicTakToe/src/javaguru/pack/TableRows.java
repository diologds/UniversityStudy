package javaguru.pack;


public class TableRows {

    private char symbol;
    private int cordX , cordY;

    public TableRows(int cordX, int cordY)
    {
        symbol = '*';
        this.cordX = cordX;
        this.cordY = cordY;
    }

    public void setCordX(int cordX)
    {
        this.cordX = cordX;
    }
    public void setCordY(int cordY)
    {
        this.cordY = cordY;
    }

    public void setSymbol(char symbol)
    {
        this.symbol = symbol;
    }


    public int getCordX()
    {
         return cordX;
    }
    public int getCordY()
    {
        return cordY;
    }

    public char getSymbol()
    {
        return symbol;
    }
}
