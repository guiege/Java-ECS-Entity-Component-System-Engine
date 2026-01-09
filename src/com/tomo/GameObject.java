package com.tomo;

import com.dataStructure.Transform;
import com.file.Parser;
import com.file.Serialize;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameObject extends Serialize {
    private List<Component> components; // Making a list of components in the scene and updating them together, one of the main principles of an entity-component system
    private String name;
    public Transform transform;
    private boolean isSerializable = true;
    public int zIndex;

    public boolean isUi = false;

    public GameObject(String name, Transform transform, int zIndex){
        this.name = name;
        this.transform = transform;
        this.components = new ArrayList<>();
        this.zIndex = zIndex;
    }

    // Class<> means a generic class. Using the generified version of a class allows you to use various logic operators on it
    public <T extends Component> T getComponent(Class<T> componentClass) { // Returns the first component of the class passed in(had to dig through a lot of stack overflow to figure this out) Basically, its creating a temporary class that extends component and checking if the classes in the list of components are components.
        for(Component c: components) {
            if(componentClass.isAssignableFrom(c.getClass())) {// Basically what this is saying is if these have the same class(even if it is abstract) return it
                try {
                    return componentClass.cast(c); // Casting the class type to the type of component c in the list of components
                } catch (ClassCastException e ) { // The ide put in this catch for me but from my understanding it just ends the program if you try to cast between two incompatible types
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
        return null; // If we don't find the component, return null
    }

    public void addComponent(Component c){
        components.add(c);
        c.gameObject = this; // Instantiates game object in all components
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        for(Component c : components){
            if(componentClass.isAssignableFrom(c.getClass())) {
                components.remove(c);
                return;
            }
        }
    }

    public List<Component> getAllComponents(){
        return components;
    }

    public GameObject copy(){
        GameObject newGameObject = new GameObject("Generated", transform.copy(), this.zIndex);
        for(Component c : components){
            Component copy = c.copy();
            if(c.copy() != null){
                newGameObject.addComponent(copy);
            }
        }
        return newGameObject;
    }

    public void update(double dt){
        for(Component c : components){
            c.update(dt);
        }
    }

    public void setNonserializable(){
        isSerializable = false;
    }

    public void draw(Graphics2D g2){
        for(Component c : components){
            c.draw(g2); // Draws all game objects if they have something to draw
        }
    }

    @Override
    public String serialize(int tabSize) {
        if(!isSerializable) return "";

        StringBuilder builder = new StringBuilder(); // Fancy way to concat strings. Easier to use than making your own methods
        // Game Object
        builder.append(beginObjectProperty("GameObject", tabSize));

        //Transform
        builder.append(transform.serialize(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(addStringProperty("Name", name, tabSize + 1, true, true));

        //Name
        if (components.size() > 0) {
            builder.append(addIntProperty("ZIndex", zIndex, tabSize + 1, true, true));
            builder.append(beginObjectProperty("Components", tabSize + 1));

        } else {
            builder.append(addIntProperty("ZIndex", zIndex, tabSize + 1, true, false));
        }

        int i = 0;
        for (Component c : components) {
            String str = c.serialize(tabSize + 2);
            if (str.compareTo("") != 0) {
                builder.append(str);
                if (i != components.size() - 1) {
                    builder.append(addEnding(true, true));
                } else {
                    builder.append(addEnding(true, false));
                }
            }
            i++;
        }

        if (components.size() > 0) {
            builder.append(closeObjectProperty(tabSize + 1));
        }



        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static GameObject deserialize(){
        Parser.consumeBeginObjectProperty("GameObject");

        Transform transform = Transform.deserialize();
        Parser.consume(',');
        String name = Parser.consumeStringProperty("Name");
        Parser.consume(',');
        int zIndex = Parser.consumeIntProperty("ZIndex");

        GameObject go = new GameObject(name, transform, zIndex);

        if(Parser.peek() == ','){
            Parser.consume(',');
            Parser.consumeBeginObjectProperty("Components");
            go.addComponent(Parser.parseComponent());

            while(Parser.peek() == ','){
                Parser.consume(',');
                go.addComponent(Parser.parseComponent());
            }
            Parser.consumeEndObjectProperty();
        }
        Parser.consumeEndObjectProperty();

        return go;
    }

    public void setUi(boolean val){
        this.isUi = val;
    }
}
