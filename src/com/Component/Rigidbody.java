package com.Component;

import com.tomo.Component;
import com.util.Constants;
import com.util.Vector2;

public class Rigidbody extends Component {

    public Vector2 velocity;

    public Rigidbody(Vector2 velocity){
        this.velocity = velocity;
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void update(double dt){
        gameObject.transform.position.y += velocity.y * dt; // Velocity + Acceleration: That's how physics works
        gameObject.transform.position.x += velocity.x * dt;

        velocity.y += Constants.GRAVITY * dt;

        if(Math.abs(velocity.y) > Constants.TERMINAL_VELOCITY) { // Max velocity player can travel while falling
            velocity.y = Math.signum(velocity.y) * Constants.TERMINAL_VELOCITY; // Math.signum returns -1 if function is - and 1 if function is +. Makes sure terminal velocity is in correct direction
        }
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
