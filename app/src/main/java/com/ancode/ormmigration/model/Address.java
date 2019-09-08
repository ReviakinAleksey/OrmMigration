package com.ancode.ormmigration.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(
        nameInDb = "addresses",
        createInDb = false
)
public class Address {
    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    public Long id;

    @Property(nameInDb = "id_person")
    @NotNull
    public Long personId;

    @Property(nameInDb = "address")
    @NotNull
    public String address;

@Generated(hash = 1776160774)
public Address(Long id, @NotNull Long personId, @NotNull String address) {
    this.id = id;
    this.personId = personId;
    this.address = address;
}

@Generated(hash = 388317431)
public Address() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public Long getPersonId() {
    return this.personId;
}

public void setPersonId(Long personId) {
    this.personId = personId;
}

public String getAddress() {
    return this.address;
}

public void setAddress(String address) {
    this.address = address;
}

}
