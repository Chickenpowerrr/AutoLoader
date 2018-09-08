package com.gmail.chickenpowerrr.autoloader.depending;

import com.gmail.chickenpowerrr.autoloader.Depend;

import static org.junit.Assert.assertNotNull;

@Depend(depends = {Father.class, Mother.class})
public class Child implements FamilyMember {

    public Child(Parent parent) {
        assertNotNull(parent);
    }
}
