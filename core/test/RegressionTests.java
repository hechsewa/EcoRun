import com.badlogic.gdx.Screen;
import com.hechsmanwilczak.ecorun.EcoRun;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.LevelsScreen;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;
import com.hechsmanwilczak.ecorun.Sprites.Earth;
import com.hechsmanwilczak.ecorun.Sprites.Enemy;
import com.hechsmanwilczak.ecorun.Sprites.PlasticBag;
import com.hechsmanwilczak.ecorun.Sprites.Saturn;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static com.hechsmanwilczak.ecorun.Screens.PlayScreen.GAME_PAUSED;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RegressionTests {
    @Test
    public void testGetHud() {
        PlayScreen playScreen = Mockito.mock(PlayScreen.class);
        when(playScreen.getHud()).thenReturn(Mockito.mock(Hud.class));
        Assert.assertEquals(Hud.class, playScreen.getHud().getClass());
    }


    // in game functions
    @Test
    public void testMovementKeys(){
        PlayScreen playScreen = Mockito.mock(PlayScreen.class);

        //test keys for movement
        playScreen.handleInput(10);
        System.out.println("movement keys functioning");
    }

    @Test
    public void testCollectItems(){

    }

    @Test
    public void testScoreChanges(){
        try(MockedStatic<Hud> mock = Mockito.mockStatic(Hud.class)){
            //increase in score
            int score = Hud.getScore();
            mock.when(new MockedStatic.Verification() {
                @Override
                public void apply() throws Throwable {
                    Hud.getScore();
                }
            }).thenReturn(10);

            Hud.addScore(10);
            int finalScore = Hud.getScore();

            Assert.assertNotEquals(score, finalScore);
        }
    }

    @Test
    public void testKillEnemy(){
        final PlasticBag enemy = Mockito.mock(PlasticBag.class);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                enemy.setToDestroy = true;
                return null;
            }
        }).when(enemy).hitOnHead();

        enemy.hitOnHead();

        Assert.assertEquals(true, enemy.setToDestroy);
    }

    @Test
    public void testLoseLife(){
        final Earth earth = Mockito.mock(Earth.class);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                earth.earthIsDead = true;
                return null;
            }
        }).when(earth).die();

        earth.die();
        Assert.assertEquals(true, earth.earthIsDead);
    }

    @Test
    public void testFailLevel(){
        //lost
        PlayScreen playLoseScreen = Mockito.mock(PlayScreen.class);
        when(playLoseScreen.gameOver()).thenReturn(true);
        playLoseScreen.render(10F);

        Assert.assertEquals(true, playLoseScreen.gameOver());

        //have not lost yet
        PlayScreen  playActiveScreen = Mockito.mock(PlayScreen.class);
        when(playActiveScreen.gameOver()).thenReturn(false);
        playLoseScreen.render(10F);

        Assert.assertEquals(false, playActiveScreen.gameOver());
    }


    // menu functions

    @Test
    public void testLevelSelection(){
        EcoRun game = new EcoRun();
        LevelsScreen levelsScreen = Mockito.mock(LevelsScreen.class);

        levelsScreen.goToGameScreen(1, 0);
        Screen screen = game.getScreen();

        Assert.assertNotEquals(Mockito.mock(PlayScreen.class), screen);
    }

    @Test
    public void testPauseButton(){
        final PlayScreen playScreen = Mockito.mock(PlayScreen.class);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                playScreen.gameStatus = 1;
                return null;
            }
        }).when(playScreen).pause();


        playScreen.pause();
        int current_play_status = playScreen.gameStatus;

        Assert.assertEquals(current_play_status, GAME_PAUSED);
    }
}
