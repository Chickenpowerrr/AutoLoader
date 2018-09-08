package com.gmail.chickenpowerrr.autoloader.depending;

import com.gmail.chickenpowerrr.autoloader.Depend;

@Depend(depends = {GrandMother.class, GrandFather.class})
public class Mother implements Parent {

}
