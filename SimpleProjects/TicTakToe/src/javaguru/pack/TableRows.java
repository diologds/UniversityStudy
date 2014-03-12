package javaGuru.pack;


public class TableRows {

    private char symbol;
    private int middleCordX , middleCordY;
    private int firstCorrectionPointCordX ,  firstCorrectionPointCordY;
    private int secondCorrectionPointCordX ,  secondCorrectionPointCordY;

    public TableRows(int cordX, int cordY , int firstCorrectionPointCordX ,int firstCorrectionPointCordY , int secondCorrectionPointCordX , int secondCorrectionPointCordY )
    {
        symbol = '*';
        this.middleCordX = cordX;
        this.middleCordY = cordY;
        this.firstCorrectionPointCordX = firstCorrectionPointCordX;
        this.firstCorrectionPointCordY = firstCorrectionPointCordY;
        this.secondCorrectionPointCordX = secondCorrectionPointCordX;
        this.secondCorrectionPointCordY = secondCorrectionPointCordY;
    }

    public void setCordX(int cordX)
    {
        this.middleCordX = cordX;
    }
    public void setCordY(int cordY)
    {
        this.middleCordY = cordY;
    }
    
    public void setfirstCorrectionPointCordX(int firstCorrectionPointCordX)
    {
        this.firstCorrectionPointCordX = firstCorrectionPointCordX;
    }
    public void setfirstCorrectionPointCordY(int firstCorrectionPointCordY)
    {
        this.firstCorrectionPointCordY = firstCorrectionPointCordY;
    }
    
    public void setsecondCorrectionPointCordX(int secondCorrectionPointCordX)
    {
        this.secondCorrectionPointCordX = secondCorrectionPointCordX;
    }
    public void setsecondCorrectionPointCordY(int secondCorrectionPointCordY)
    {
        this.secondCorrectionPointCordY = secondCorrectionPointCordY;
    }

    public void setSymbol(char symbol)
    {
        this.symbol = symbol;
    }


    public int getCordX()
    {
         return middleCordX;
    }
    public int getCordY()
    {
        return middleCordY;
    }

    public int getfirstCorrectionPointCordX()
    {
    	return firstCorrectionPointCordX;
    }
    public int getfirstCorrectionPointCordY()
    {
    	return firstCorrectionPointCordY;
    }
    
    public int getsecondCorrectionPointCordX()
    {
        return secondCorrectionPointCordX;
    }
    public int getsecondCorrectionPointCordY()
    {
    	return secondCorrectionPointCordY;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
}