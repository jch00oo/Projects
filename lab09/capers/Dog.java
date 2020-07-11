package capers;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/** Represents a dog that can be serialized.
 * @author Sean Dooher
 */
public class Dog implements Serializable { // FIXME

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = new File(Main.CAPERS_FOLDER, ".dogs"); // FIXME

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        _age = age;
        _breed = breed;
        _name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // FIXME
        File dogFile = Utils.join(DOG_FOLDER, name);
        Dog dog3=null;
        try{
            ObjectInputStream dog2= new ObjectInputStream(new FileInputStream(dogFile));
            dog3 = (Dog) dog2.readObject();
            dog2.close();
        }
        catch(IOException|ClassNotFoundException poodle){
            //nothing ever comes here anyway— thanks with help from Marcus!
        }
        return dog3;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        _age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        // FIXME
        File dog = Utils.join(DOG_FOLDER, this._name);
        try {
            ObjectOutputStream dog1 = new ObjectOutputStream(new FileOutputStream(dog));
            dog1.writeObject(this);
            dog1.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
                _name, _breed, _age);
    }

    /** Age of dog. */
    private int _age;
    /** Breed of dog. */
    private String _breed;
    /** Name of dog. */
    private String _name;
}