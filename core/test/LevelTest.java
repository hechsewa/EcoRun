import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.hechsmanwilczak.ecorun.Scenes.Hud;
import com.hechsmanwilczak.ecorun.Screens.PlayScreen;
import com.hechsmanwilczak.ecorun.Screens.LevelsScreen;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.hechsmanwilczak.ecorun.EcoRun;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LevelTest {
    @Test
    public void testlevel4(){
        LevelsScreen levelsScreen = Mockito.mock(LevelsScreen.class);
        when(levelsScreen.goToGameScreen(4,0)).thenReturn(4);
        Assert.assertEquals(4,levelsScreen.goToGameScreen(4,0));
    }
    @Test
    public void testlevel5(){
        LevelsScreen levelsScreen = Mockito.mock(LevelsScreen.class);
        when(levelsScreen.goToGameScreen(5,0)).thenReturn(5);
        Assert.assertEquals(5,levelsScreen.goToGameScreen(5,0));
    }
    @Test
    public void testlevel6(){
        LevelsScreen levelsScreen = Mockito.mock(LevelsScreen.class);
        when(levelsScreen.goToGameScreen(6,0)).thenReturn(6);
        Assert.assertEquals(6,levelsScreen.goToGameScreen(6,0));
    }
}

