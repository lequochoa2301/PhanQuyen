package mvc.codejava.entity;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "number_of_rooms")
    private int numberOfRooms; // Số phòng

    @Column(name = "room_type", nullable = false)
    private String roomType; // Loại phòng

    @Column(name = "capacity")
    private int capacity; // Số người tối đa

    @Column(name = "status", nullable = false)
    private String status; // Tình trạng phòng

    @Column(name = "price", nullable = false)
    private Double price; // Giá cả

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo; // Hình ảnh

    public Room() {
    }

    public Room(int numberOfRooms, String roomType, int capacity, String status, Double price, byte[] photo) {
        this.numberOfRooms = numberOfRooms;
        this.roomType = roomType;
        this.capacity = capacity;
        this.status = status;
        this.price = price;
        this.photo = photo;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}