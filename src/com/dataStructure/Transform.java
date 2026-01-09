package com.dataStructure;

import com.file.Parser;
import com.file.Serialize;
import com.util.Vector2;

public class Transform extends Serialize { // Emulating the unity 2D transform class
    public Vector2 position;
    public Vector2 scale;
    public float rotation;

    public Transform(Vector2 position) {
        this.position = position;
        this.scale = new Vector2(1.0f, 1.0f);
        this.rotation = 0.0f;
    }

    public Transform copy(){
        Transform transform = new Transform(this.position.copy());
        transform.scale = this.scale.copy();
        transform.rotation = this.rotation; // You cant copy instances of classes in java without problems but for basic types, you can just set it equal to
        return transform;
    }

    @Override
    public String toString(){ // Making a toString for debugging purposes
        return "Position: " + position.x + ", " + position.y;
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Transform", tabSize));
        builder.append(beginObjectProperty("Position", tabSize + 1));
        builder.append(position.serialize(tabSize + 2)); // Increasing size by one each time to create indented tabs
        builder.append(closeObjectProperty(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(beginObjectProperty("Scale", tabSize + 1));
        builder.append(scale.serialize(tabSize + 2));
        builder.append(closeObjectProperty(tabSize + 2));
        builder.append(addEnding(true, true));

        builder.append(addFloatProperty("rotation", rotation, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static Transform deserialize(){
        Parser.consumeBeginObjectProperty("Transform");
        Parser.consumeBeginObjectProperty("Position");
        Vector2 position = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');

        Parser.consumeBeginObjectProperty("Scale");
        Vector2 scale = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');
        float rotation = Parser.consumeFloatProperty("rotation");
        Parser.consumeEndObjectProperty();

        Transform t = new Transform(position);
        t.scale = scale;
        t.rotation = rotation;

        return t;
    }
}
