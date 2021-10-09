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

    @Size(min = 4, message = "Имя должно быть больше 4 знаков")
    private String name;

    @Size(min = 5, max = 10, message = "Пароль должен быть от 5 до 10 знаков")
    private String password;

    private Boolean isAdmin;
}
