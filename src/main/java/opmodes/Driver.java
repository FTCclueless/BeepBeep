package opmodes;

import BeepBeep.Autonomous;
import BeepBeep.FieldType;
import BeepBeep.Simulation;
import BeepBeep.Drive;
import org.reflections.Reflections;

import java.io.IOException;
import java.util.Set;

public class Driver {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("sun.java2d.opengl", "true");
        // Convert the set to a class so it can be easily used
        Set<Class<?>> opmodesSet = new Reflections(Driver.class).getTypesAnnotatedWith(Autonomous.class);
        Simulation s = new Simulation(
            FieldType.POWERPLAY,
            800,
            opmodesSet.toArray(new Class<?>[opmodesSet.size()]),
            new Drive(0, 0, 0, 0.05, 0.01, 17.375, 15.94488)
        );
        s.start();
    }
}