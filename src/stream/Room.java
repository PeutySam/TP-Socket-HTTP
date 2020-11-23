package stream;

import java.util.Set;

public class Room {
    private long id;
    private Set<User> users;

    public Room(long id){
        this.id=id;
    }
}
