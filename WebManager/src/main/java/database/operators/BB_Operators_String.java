package database.operators;

public class BB_Operators_String {
    public static final byte Set                = 1;
    public static final byte AdditiveInverse    = 2;
    public static final byte LogicalNegation    = 3;
    
    public static final byte BitwiseNOT         = 20;
    public static final byte BitwiseAND         = 21;
    public static final byte BitwiseOR          = 22;
    public static final byte BitwiseXOR         = 23;
    public static final byte BitwiseLeftShift   = 24;
    public static final byte BitwiseRightShift  = 25;
    
    public static final byte Modulo             = 40;
    
    public static final byte Division           = 60;
    
    public static final byte Multiplication     = 80;
    
    public static final byte Add                = 100;
    public static final byte ADD_POSITIVE       = 101;
    public static final byte ADD_NEGATIVE       = 102;
    public static final byte ADD_MIN            = 103;
    public static final byte ADD_MAX            = 104;
    public static final byte ADD_MIN_MAX        = 105;
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private BB_Operators_String() {}
    private static BB_Operators_String instance=null;
    public static final BB_Operators_String gI() {
        if(instance==null) {
            instance = new BB_Operators_String();
        }
        return instance;
    }
}