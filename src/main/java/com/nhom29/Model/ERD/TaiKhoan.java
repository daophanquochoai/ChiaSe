package com.nhom29.Model.ERD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
    @Id
    @Column(name = "username", columnDefinition = "varchar(50)")
    private String username;
    @Column(name = "password", nullable = false, columnDefinition = "varchar(100)")
    private String password;
    @Column(columnDefinition = "BIT")
    private Boolean active;
}
