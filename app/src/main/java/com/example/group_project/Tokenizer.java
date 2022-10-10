package com.example.group_project;

public class Tokenizer {
    private String buffer;
    private Token lastToken;
    private Token currentToken;

    //Constructor
    public Tokenizer(String postContent){
        buffer = postContent;
        lastToken = null;
        currentToken = null;
        //next();
    }

    //Call next token
    public void next(){
        if(buffer == null){
            currentToken = null;
            return;
        }
        String first = buffer.substring(0,1);
        if(first.equals(" ")){
            lastToken = currentToken;
            currentToken = new Token(" ", Token.Type.BLANK);
            buffer = buffer.substring(1);
            return;
        }
        if(first.equals("#")){
            lastToken = currentToken;
            currentToken = new Token("#", Token.Type.HASH);
            buffer = buffer.substring(1);
            return;
        }
        else{
            int i = 1;
            String next = buffer.substring(0, 1);
            String store = "";
            while((next.equals(" ") == false && next.equals("#") == false)){
                store += next;
                if(buffer.length() > 1) {
                    next = buffer.substring(i, i + 1);
                    buffer = buffer.substring(i);
                }
                else{
                    buffer = buffer.substring(i);
                    break;
                }
            }
            lastToken = currentToken;
            currentToken = new Token(store, Token.Type.CONTENT);
            return;
        }
    }

    public boolean hasNext(){
        if(buffer.equals("")){
            return false;
        }
        Tokenizer next = new Tokenizer(this.getBuffer());
        //System.out.println(next.getBuffer());
        next.next();
        if(next.currentToken != null){
            return true;
        }
        else{
            return false;
        }
    }

    public String getBuffer(){
        return buffer;
    }

    public Token getLastToken() {
        return lastToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }
}
