package com.hechsmanwilczak.ecorun.Tools;

import com.badlogic.gdx.utils.Array;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;
import com.hechsmanwilczak.ecorun.Sprites.Items.Leaf;
import com.hechsmanwilczak.ecorun.Sprites.Items.Trash;
import com.hechsmanwilczak.ecorun.Sprites.PlasticBag;

public abstract class B2WorldCreatorEmpty {
    private Array<PlasticBag> plasticBagArray;
    private Array<Leaf> leavesArray;
    private Array<Trash> trashArray;

    public B2WorldCreatorEmpty(PlayScreen screen){
    }

    public Array<PlasticBag> getPlasticBagArray() {
        return plasticBagArray;
    }
    public Array<Leaf> getLeavesArray() { return leavesArray; }
    public Array<Trash> getTrashArray() {return trashArray;}
}
