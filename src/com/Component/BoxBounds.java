package com.Component;

import com.file.Parser;
import com.tomo.Component;
import com.tomo.GameObject;
import com.util.Constants;
import com.util.Vector2;

public class BoxBounds extends Bounds{
    public float width, height;
    public float halfWidth, halfHeight;
    public Vector2 center = new Vector2();

    public BoxBounds(float width, float height){
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2.0f; // Calculate the half values before the game runs so we don't waste resources( no point in repeatedly calculating)
        this.halfHeight = height / 2.0f;
        this.type = BoundsType.Box; // IT TOOK ME 4 YEARS TO DEBUG THIS WHY DID I USE AN ENUM
    }

    @Override
    public void start(){
        this.calculateCenter();
    }

    public void calculateCenter(){
        this.center.x =  this.gameObject.transform.position.x + this.halfWidth;
        this.center.y = this.gameObject.transform.position.y + this.halfHeight;
    }

    public void resolveCollision(GameObject player){
        BoxBounds playerBounds = player.getComponent(BoxBounds.class);
        playerBounds.calculateCenter();
        this.calculateCenter();

        float dx = this.center.x - playerBounds.center.x;
        float dy = this.center.y - playerBounds.center.y;

        float combineHalfWidths = playerBounds.halfWidth + this.halfWidth;
        float combineHalfHeights = playerBounds.halfHeight + this.halfHeight;

        float overlapX = combineHalfWidths - Math.abs(dx);
        float overlapY = combineHalfHeights - Math.abs(dy);

        if(overlapX >= overlapY){ // Vert collision
            if(dy > 0){ // If it is on the top
                // Collision on top side of player cube
                player.transform.position.y = gameObject.transform.position.y - playerBounds.getHeight(); // Move the player right on top of the box
                player.getComponent(Rigidbody.class).velocity.y = 0; // Reset velocity because player is colliding
                player.getComponent(Player.class).onGround = true;
            } else {
                // Collision on bottom
                player.getComponent(Player.class).die();
            }
        } else{
            // Collision on left or right
            if(dx < 0 && dy <= Constants.COLLISION_OFFSET){ // If it is a very small collision, bump the player up so the game feels more fair
                player.transform.position.y = gameObject.transform.position.y - playerBounds.getHeight();
                player.getComponent(Rigidbody.class).velocity.y = 0;
                player.getComponent(Player.class).onGround = true;
            } else{
                player.getComponent(Player.class).die();
            }
        }


    }

    @Override
    public Component copy() {
        return new BoxBounds(width, height);
    }

    @Override
    public void update(double dt) {

    }

    public static boolean checkCollision(BoxBounds b1, BoxBounds b2){ // If the distance between the two centers is less than the half width of both boxes, they are colliding.
        b1.calculateCenter(); // Just in case it hasn't been calculated already
        b2.calculateCenter();

        float dx = b2.center.x - b1.center.x;
        float dy = b2.center.y - b1.center.y;

        float combineHalfWidths = b1.halfWidth + b2.halfWidth;
        float combineHalfHeights = b1.halfHeight + b2.halfHeight;

        if(Math.abs(dx) <= combineHalfWidths){
            return Math.abs(dy) <= combineHalfHeights;
        }
        return false;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("BoxBounds", tabSize));
        builder.append(addFloatProperty("Width", this.width, tabSize + 1, true, true));
        builder.append(addFloatProperty("Height", this.height, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static BoxBounds deserialize(){
        float width = Parser.consumeFloatProperty("Width");
        Parser.consume(',');
        float height = Parser.consumeFloatProperty("Height");
        Parser.consumeEndObjectProperty();

        return new BoxBounds(width, height);
    }
}
