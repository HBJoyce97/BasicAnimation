package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Comparator;

/**
 * Created by hj167 on 31/10/2017.
 */

public class BasicAnimation extends ApplicationAdapter {
    static final Color BACKGROUND_COLOUR = new Color(0F, 0F, 0F, 1F);
    static final float SCENE_WIDTH = 12.0F;
    static final float SCENE_HEIGHT = 7.0F;
    static final float FRAME_DURATION = 1.0F / 30.0F;

    OrthographicCamera camera;
    Viewport view;
    SpriteBatch batch;
    TextureAtlas atlas;

    Animation threeringAnim;
    float animationTime;

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion>
    {

        @Override public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2)
        {
            return region1.name.compareTo(region2.name); // Returns an integer
        }
    }

    @Override
    public void create()
    {
        camera = new OrthographicCamera();
        view = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

        batch = new SpriteBatch();
        animationTime = 0.0F;

        atlas = new TextureAtlas((Gdx.files.internal("gfx/ring_assets.atlas"))); // Creates the atlas

        // Load animations
        Array<TextureAtlas.AtlasRegion> ringRegions = new Array<TextureAtlas.AtlasRegion>(atlas.getRegions()); // Creates the array and returns all regions
        ringRegions.sort(new RegionComparator()); // Sorts the integers in order
        threeringAnim = new Animation(FRAME_DURATION, ringRegions, Animation.PlayMode.LOOP); // Creates an animation object targeting the array and loops the animation

        // Position the camera
        camera.position.set(SCENE_WIDTH * 0.5F, SCENE_HEIGHT * 0.5F, 0.0F);
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(BACKGROUND_COLOUR.r, BACKGROUND_COLOUR.g, BACKGROUND_COLOUR.b, BACKGROUND_COLOUR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        animationTime += Gdx.graphics.getDeltaTime(); // Animation time each frame
        batch.begin();
        TextureRegion ringFrame = (TextureRegion) threeringAnim.getKeyFrame(animationTime); // Get the appropriate frame for that time (animationTime)
        batch.draw(ringFrame, (SCENE_WIDTH * 100) / 2, (SCENE_HEIGHT * 100) / 2);// Draw the texture region
        batch.end();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        atlas.dispose();
    }
}
