package se.iths.erikthorell.springmessenger.messaging;

import se.iths.erikthorell.springmessenger.model.Message;

public interface Messenger {
    void send(Message message);
}
