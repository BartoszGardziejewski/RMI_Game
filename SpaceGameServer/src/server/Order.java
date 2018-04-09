package server;

import java.security.PrivateKey;

public interface Order {

    boolean checkOrder(Player player);
    int getValue();
    String getElement();

}
