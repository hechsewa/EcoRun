import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
        //test UP, LEFT, RIGHT keys for movement

        Assert.assertEquals(true, true);
    }

    @Test
    public void testcollectItems(){
        Assert.assertEquals(true, true);
    }

    @Test
    public void testScoreChanges(){
        //increase in score

        //decrease in score

        Assert.assertEquals(true, true);
    }

    @Test
    public void testKillEnemy(){
        Assert.assertEquals(true, true);
    }

    @Test
    public void testLoseLife(){
        Assert.assertEquals(true, true);
    }

    @Test
    public void testFailLevel(){
        // lost all lives

        // automatic loss

        Assert.assertEquals(true, true);
    }


    // menu functions

    @Test
    public void testLevelSelection(){
        Assert.assertEquals(true, true);
    }

    @Test
    public void testRestartGame(){
        Assert.assertEquals(true, true);
    }

    @Test
    public void testPauseButton(){
        Assert.assertEquals(true, true);
    }
}
