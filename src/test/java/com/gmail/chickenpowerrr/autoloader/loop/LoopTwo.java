package com.gmail.chickenpowerrr.autoloader.loop;

import com.gmail.chickenpowerrr.autoloader.Depend;

@Depend(depends = {SomeNotLoopingLoop.class, LoopOne.class})
public class LoopTwo implements Loop {

}
