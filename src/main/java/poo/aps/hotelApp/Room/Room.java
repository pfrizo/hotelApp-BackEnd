package poo.aps.hotelApp.Room;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {
    private Long id;
    private String name;
    private String desc;
    private String size;
    private int guests;
    private float dailyValue;
}
