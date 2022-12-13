import com.hechsmanwilczak.ecorun.Scenes.Hud;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CharacterSelectionTests {
    @Test
    public void testDefaultEarth(){
        //earth's character code is 0
        Assert.assertEquals(0, Hud.getTexture());
    }

    @Test
    public void testSelectSaturn(){
        //saturn's character code is 1
        try(MockedStatic<Hud> mock = Mockito.mockStatic(Hud.class)){
            mock.when(new MockedStatic.Verification() {
                @Override
                public void apply() throws Throwable {
                    Hud.getTexture();
                }
            }).thenReturn(1);

            Assert.assertEquals(1, Hud.getTexture());
        }
    }

    @Test
    public void testSelectGraySpot(){
        //grayspot's character code is 2
        try(MockedStatic<Hud> mock = Mockito.mockStatic(Hud.class)){
            mock.when(new MockedStatic.Verification() {
                @Override
                public void apply() throws Throwable {
                    Hud.getTexture();
                }
            }).thenReturn(2);

            Assert.assertEquals(2, Hud.getTexture());
        }
    }


}
