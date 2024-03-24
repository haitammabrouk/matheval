import java.util.List;
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;

class Main {
  public static void main(String[] args) throws IOException{
    System.out.println("Please input a mathematical expression : ");
    while(true){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
        String name = reader.readLine(); 
        Scanner sc = new Scanner(name);
        List<Token> scanExp = sc.scan();
        Parser parser = new Parser(scanExp);
        List<Token> parsed = parser.parse();
        System.out.println(sc.eval(parsed));
    }
  }
}