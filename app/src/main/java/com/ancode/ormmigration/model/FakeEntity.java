package com.ancode.ormmigration.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(
        nameInDb = "fake",
        createInDb = false
)
public class FakeEntity {
    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;

@Generated(hash = 104374499)
public FakeEntity(Long id) {
    this.id = id;
}

@Generated(hash = 151978228)
public FakeEntity() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}
}
