public class Token {
    private String expression;
    private Type type;
  
    public Token(String expression, Type type){
      this.expression = expression;
      this.type = type;
    }
  
    @Override
    public String toString(){
      return "[Token:"+ expression+ ", Type:"+ type+"]";
    }
  
    public Type type(){
      return type;
    }
  
    public String expression(){
      return expression;
    }
  }