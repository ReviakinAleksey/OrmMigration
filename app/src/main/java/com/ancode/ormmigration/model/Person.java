package com.ancode.ormmigration.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(
        nameInDb = "persons",
        createInDb = false
)
public class Person {
    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    public long id;

    @Property(nameInDb = "name")
    @NotNull
    public String name;

@Generated(hash = 296726366)
public Person(long id, @NotNull String name) {
    this.id = id;
    this.name = name;
}

@Generated(hash = 1024547259)
public Person() {
}

    @Override
    public String toString() {
        return name;
    }

public long getId() {
    return this.id;
}

public void setId(long id) {
    this.id = id;
}

public String getName() {
    return this.name;
}

public void setName(String name) {
    this.name = name;
}
}
