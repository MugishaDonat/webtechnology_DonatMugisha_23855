package model;

import javax.persistence.*;
import org.hibernate.annotations.Type;
import java.util.UUID;

@Entity
@Table(name = "Room")
public class Room {
    
    @Id
    @GeneratedValue
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @Column(name = "room_id", columnDefinition = "uuid")
    private UUID room_id;

    @Column(name = "room_code")
    private String room_code;

    // Getters and Setters
    public UUID getRoom_id() {
        return room_id;
    }

    public void setRoom_id(UUID room_id) {
        this.room_id = room_id;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }
}
