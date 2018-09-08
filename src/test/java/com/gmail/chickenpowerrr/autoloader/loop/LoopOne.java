package com.gmail.chickenpowerrr.autoloader.loop;

import com.gmail.chickenpowerrr.autoloader.Depend;

@Depend(depends = {SomeNotLoopingLoop.class, LoopTwo.class})
public class LoopOne implements Loop {

}
