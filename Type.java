public enum Type {
  ADD,
  SUB,
  MUL,
  DIV,
  POW,
  SQRT,
  SIN,
  COS,
  TAN,
  ASIN,
  ACOS,
  ATAN,
  LEFT_PAR,
  RIGHT_PAR,
  NUM;

  public static Type fromString(String s){
    switch (s){
        case "+":
            return Type.ADD;
        case "-":
            return Type.SUB;
        case "*":
            return Type.MUL;
        case "/":
            return Type.DIV;
        case "^":
            return Type.POW;
        case "sqrt":
            return Type.SQRT;
        case "sin":
            return Type.SIN;
        case "cos":
            return Type.COS;
        case "tan":
            return Type.TAN;
        case "asin":
            return Type.ASIN;
        case "acos":
            return Type.ACOS;
        case "atan":
            return Type.ATAN;
        case "(":
            return Type.LEFT_PAR;
        case ")":
            return Type.RIGHT_PAR;
        default:
            return Type.NUM;
    }
}

  @Override
  public String toString() {
      switch (this.ordinal()){
          case 0:
              return "+";
          case 1:
              return "-";
          case 2:
              return "*";
          case 3:
              return "/";
          case 4:
              return "^";
          case 5:
              return "sqrt";
          case 6:
              return "sin";
          case 7:
              return "cos";
          case 8:
              return "tan";
          case 9:
              return "asin";
          case 10:
              return "acos";
          case 11:
              return "atan";
          case 12:
              return "(";
          case 13:
              return ")";
          case 14:
              return this.name();
          default:
              return "NONE";
      }
  }
}
