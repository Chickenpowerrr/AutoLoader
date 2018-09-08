package com.gmail.chickenpowerrr.autoloader.dontload;

import com.gmail.chickenpowerrr.autoloader.Skip;
import com.gmail.chickenpowerrr.autoloader.interfaces.Animal;

@Skip(reason = "I'm disabled because my owner wanted to test this")
public class DisabledPet implements Animal {

    public DisabledPet() {
        throw new IllegalStateException();
    }
}
