package com.teamhenry.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamhenry.game.globals.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Ender on 1/3/2016.
 */

//Used to manager the various states (basic stack of game states)
public class GameStateManager
{
    //Stack of game states
    private Stack<State> states;
    //Map of global variables, referenced using identifiers
    private static HashMap<String, Global> globals = new HashMap<String, Global>();

    public GameStateManager() { states = new Stack<State>(); }

    public void push(State state) { states.push(state); }

    public void pop() { states.pop().dispose(); }

    //Pops the last game state then immediately pushes the new one (over comes run time of double function call)
    public void set(State state)
    {
        states.pop().dispose();
        states.push(state);
    }

    //Adds a global variable to the HashMap
    public<Type> void addGlobal(String id, Type obj)
    {
        //Creates temp global variable
        Global global = new Global<Type>();
        global.setVar(obj);
        //Puts the global variable in the HashMap
        globals.put(id, global);
    }

    //Returns the global variable with the specified id
    public Global getGlobal(String id) { return globals.get(id); }

    //Updates the game states on the top of the stack
    public void update(float dt) { states.peek().update(dt); }

    //Renders the game state on the top of the stack
    public void render(SpriteBatch sb) { states.peek().render(sb); }
}
