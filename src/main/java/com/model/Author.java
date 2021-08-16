package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Table
public class Author {

    @Id
    private String id;

    @Length(max = 50 , message = "Max de 50 caracteres")
    @NotEmpty(message = "Campo nome é obrigatório")
    @Column
    private String name;

    @Length(max = 50 , message = "Max de 50 caracteres")
    @NotEmpty(message = "Campo endereço é obrigatório")
    @Column
    private String address;

    @Column @NotNull @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

    public Author() {
    }

    public Author(String id, String name, String address, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.birthdate = birthdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}