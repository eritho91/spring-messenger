package se.iths.erikthorell.springmessenger.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email extends Message {
    private String subject;

    @Override
    public String getType(){
        return "email";
    }
}
