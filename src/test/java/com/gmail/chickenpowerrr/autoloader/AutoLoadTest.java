package com.gmail.chickenpowerrr.autoloader;

import com.gmail.chickenpowerrr.autoloader.classes.AbstractModule;
import com.gmail.chickenpowerrr.autoloader.classes.yetanotherpackage.DatabaseModule;
import com.gmail.chickenpowerrr.autoloader.classes.PeanutModule;
import com.gmail.chickenpowerrr.autoloader.classes.yetanotherpackage.CheeseModule;
import com.gmail.chickenpowerrr.autoloader.combination.WhaleModule;
import com.gmail.chickenpowerrr.autoloader.depending.*;
import com.gmail.chickenpowerrr.autoloader.interfaces.Animal;
import com.gmail.chickenpowerrr.autoloader.interfaces.Cat;
import com.gmail.chickenpowerrr.autoloader.interfaces.anotherone.Dog;
import com.gmail.chickenpowerrr.autoloader.interfaces.anotherone.Mule;
import com.gmail.chickenpowerrr.autoloader.loop.Loop;
import org.junit.Test;

import java.util.Collection;
import java.util.EventListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AutoLoadTest {

    @Test
    public void getApiTest() {
        assertNotNull(AutoLoaderApi.getApi());
    }

    @Test
    public void specificClassAutoLoadTest() {
        Collection<AbstractModule> modules = AutoLoaderApi.getApi().loadClasses(AutoLoadTest.class, "com.gmail.chickenpowerrr.autoloader", AbstractModule.class);
        classAutoLoadTest(modules);
    }

    @Test
    public void receivedClassAutoLoadTest() {
        Collection<AbstractModule> modules = AutoLoaderApi.getApi().loadClasses(AutoLoadTest.class, AbstractModule.class);
        classAutoLoadTest(modules);
    }

    @Test
    public void specificInterfaceAutoLoadTest() {
        Collection<Animal> animals = AutoLoaderApi.getApi().loadClasses(AutoLoadTest.class, "com.gmail.chickenpowerrr.autoloader", Animal.class);
        interfaceAutoLoadTest(animals);
    }

    @Test
    public void receivedInterfaceAutoLoadTest() {
        Collection<Animal> animals = AutoLoaderApi.getApi().loadClasses(AutoLoadTest.class, Animal.class);
        interfaceAutoLoadTest(animals);
    }

    @Test
    public void dependTest() {
        Collection<FamilyMember> familyMembers = AutoLoaderApi.getApi().loadClasses(AutoLoadTest.class, FamilyMember.class);
        assertEquals(5, familyMembers.size());
        assertNotNull(AutoLoaderApi.getApi().getInstance(Child.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(Father.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(GrandFather.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(GrandMother.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(Mother.class));
        assertEquals(5, AutoLoaderApi.getApi().getInstances(FamilyMember.class).size());
    }

    @Test
    public void loopTest() {
        Collection<Loop> loops = AutoLoaderApi.getApi().loadClasses(AutoLoaderApi.class, Loop.class);
        assertEquals(1, loops.size());
    }

    @Test
    public void externalTest() {
        Collection<EventListener> listeners = AutoLoaderApi.getApi().loadClasses(this.getClass(), EventListener.class);
        assertEquals(1, listeners.size());
    }

    private void classAutoLoadTest(Collection<AbstractModule> modules) {
        assertEquals(4, modules.size());
        assertNotNull(AutoLoaderApi.getApi().getInstance(PeanutModule.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(CheeseModule.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(WhaleModule.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(DatabaseModule.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(AbstractModule.class));
    }

    private void interfaceAutoLoadTest(Collection<Animal> animals) {
        assertEquals(4, animals.size());
        assertNotNull(AutoLoaderApi.getApi().getInstance(Mule.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(Cat.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(Dog.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(WhaleModule.class));
        assertNotNull(AutoLoaderApi.getApi().getInstance(Animal.class));
    }
}
