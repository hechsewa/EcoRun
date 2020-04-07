package com.hechsmanwilczak.ecorun.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;
import com.hechsmanwilczak.ecorun.Sprites.Bin;
import com.hechsmanwilczak.ecorun.Sprites.Items.Leaf;
import com.hechsmanwilczak.ecorun.Sprites.Items.Trash;
import com.hechsmanwilczak.ecorun.Sprites.PlasticBag;
import com.hechsmanwilczak.ecorun.Sprites.Portal;
import com.hechsmanwilczak.ecorun.Sprites.Water;


public class B2WorldCreator {
    private Array<PlasticBag> plasticBagArray;
    private Array<Leaf> leavesArray;
    private Array<Trash> trashArray;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //ground layer
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / EcoRun.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / EcoRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / EcoRun.PPM, rectangle.getHeight() / 2 / EcoRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //bins layer
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Bin(screen, rectangle);
        }

        //portal layer
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Portal(screen, rectangle);
        }

        //plastic bags
        plasticBagArray = new Array<PlasticBag>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            plasticBagArray.add(new PlasticBag(screen, rectangle.getX() / EcoRun.PPM, rectangle.getY() / EcoRun.PPM));
        }

        //water
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Water(screen, rectangle);
        }

        //leaves
        leavesArray = new Array<Leaf>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            leavesArray.add(new Leaf(screen, rectangle.getX() / EcoRun.PPM, rectangle.getY()/ EcoRun.PPM));
        }

        //trash
        trashArray = new Array<Trash>();
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            String name = object.getName();
            Integer category = 3;
            if (name.equals("Plastic"))
                category = 0;
            else if (name.equals("Metal"))
                category = 1;
            else if (name.equals("Paper"))
                category = 2;
            trashArray.add(new Trash(screen, rectangle.getX() / EcoRun.PPM, rectangle.getY()/ EcoRun.PPM, category));
        }
    }

    public Array<PlasticBag> getPlasticBagArray() {
        return plasticBagArray;
    }
    public Array<Leaf> getLeavesArray() { return leavesArray; }
    public Array<Trash> getTrashArray() {return trashArray;}
}
