package ru.lexbatalin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement
@XmlType(propOrder = {"name", "password", "isAdmin"})
public class User {

    public User(String name) {
        this.name = name;
    }

    @Size(min = 4, message = "{name.size.error}")
    private String name;

    @Size(min = 3, max = 15, message = "{password.size.error}")
    private String password;

    private Boolean isAdmin;
}
