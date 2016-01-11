package com.teamhenry.game.globals;

/**
 * Created by Ender on 1/10/2016.
 */

//Class is used to hold a global variable of any type
public class Global<Type>
{
    //Creates a variable of the type that the class was given
    Type var;

    public void Global(Type var)
    {
        this.var = var;
    }

    //Sets the global variable to the given variable type (could cause problems if given wrong class type)
    public void setVar(Type var) { System.out.println(this.var); this.var = var; }

    //Gets the global variable
    public Type getVar() { return var; }
}
