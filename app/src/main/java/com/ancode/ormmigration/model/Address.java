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
    public long id;

    @Property(nameInDb = "id_person")
    @NotNull
    public int personId;

    @Property(nameInDb = "address")
    @NotNull
    public String address;

@Generated(hash = 903809066)
public Address(long id, int personId, @NotNull String address) {
    this.id = id;
    this.personId = personId;
    this.address = address;
}

@Generated(hash = 388317431)
public Address() {
}

    @Override
    public String toString() {
        return address;
    }

public long getId() {
    return this.id;
}

public void setId(long id) {
    this.id = id;
}

public int getPersonId() {
    return this.personId;
}

public void setPersonId(int personId) {
    this.personId = personId;
}

public String getAddress() {
    return this.address;
}

public void setAddress(String address) {
    this.address = address;
}
}
