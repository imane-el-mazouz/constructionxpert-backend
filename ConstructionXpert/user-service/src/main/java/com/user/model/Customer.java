package com.user.model;

import com.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer extends User {
//    private Long id;

    public Customer() {
        this.setRole(Role.CUSTOMER);
    }

}
