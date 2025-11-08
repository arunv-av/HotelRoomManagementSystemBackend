package com.dailycodework.lakesidehotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    @Lob
    private Blob photo;

    @OneToMany(mappedBy="room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //If one room deleted anything realted to it will be deleted...
    private List<BookedRoom> bookings; // make a container to store bookings...

    public Room() {
        this.bookings = new ArrayList<>();
    }
    // we create constructor to avoid null pointer exception....
    // Because we use booked rooms here....


    public void addBooking(BookedRoom booking){
        if (bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);//two-way relationship established between Room and BookedRoom..
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);// setting random booking confirmation code...
        booking.setBookingConfirmationCode(bookingCode);
    }
}
