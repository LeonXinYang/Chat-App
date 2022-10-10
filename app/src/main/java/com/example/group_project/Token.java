package com.example.group_project;

import java.util.Objects;

public class Token {
    //Our token have only three type consisting of a tag
    public enum Type {HASH, CONTENT, BLANK}

    //We get the wrong type when tokenizer get the token with type not among of these three
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    public String toString(){
        return token+","+type;
    }

    //Have not used this method
//    public boolean equal(Object object){
//        if(this == object){ return true;}
//        if(object instanceof Token == false){
//            return false;
//        }
//        else{
//            if(((Token) object).getToken().equals(this.token) && ((Token) object).getType().equals(this.type)){
//                return true;
//            }
//            return false;
//        }
//    }

    public int hashCode() {
        return Objects.hash(token, type);
    }
}
