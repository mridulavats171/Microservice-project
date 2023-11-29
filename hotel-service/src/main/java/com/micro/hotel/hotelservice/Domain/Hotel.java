package com.micro.hotel.hotelservice.Domain;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.micro.hotel.hotelservice.City;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionIdType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    private String id;

    @Pattern(regexp = "^[a-zA-Z]{1,20}$", message = "Name must only contain lowercase and uppercase alphabets")
    private String name;
    private String location;
    @NotEmpty
    private String about;

    @City
    private String city;
}