/*Names: Ishaan Aggarwal, Luke Blaydes
* Project Phase 2.1: parser for expressions
*/

public class Token {
  private String token;
  private String type;
  public Token(String tok, String type){
    token = tok;
    this.type = type;
  }
  public Token(){
    token = "N/A";
    type = "N/A";
  }
  public String getType() {return type;}
  public String getToken(){
    return token;
  }
  public void setType(String type) { 
    this.type = type;
  
  }
  public void setToken(String token) {this.token = token;}
  public String toString() {
    if(type != "IDENTIFIER" && type != "SYMBOL" && type != "IDENTIFIER" && type != "KEYWORD"){
      return getType();
    }
    else
	  return getType() + " " + getToken();
  }
}