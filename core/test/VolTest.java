import com.hechsmanwilczak.ecorun.AppSettings;
import com.hechsmanwilczak.ecorun.Screens.SettingsScreen;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
public class VolTest {
    @Test
    public void testVolBelBot(){
        float vol = -1;
        float output = AppSettings.volLim(vol);

        Assert.assertEquals(0f, output,0);
    }
    @Test
    public void testVolBot(){
        float vol = 0;
        float output = AppSettings.volLim(vol);

        Assert.assertEquals(0f, output,0);
    }
    @Test
    public void testVolAbvBot(){
        float vol = 0.1f;
        float output = AppSettings.volLim(vol);

        Assert.assertEquals(0.1f, output,0);
    }
    @Test
    public void testVolMid(){
        float vol = 0.5f;
        float output = AppSettings.volLim(vol);

        Assert.assertEquals(0.5f, output,0);
    }
    @Test
    public void testVolBelTop(){
        float vol = 0.9f;
        float output = AppSettings.volLim(vol);

        Assert.assertEquals(0.9f, output,0);
    }
    @Test
    public void testVolTop(){
        float vol = 1f;
        float output = AppSettings.volLim(vol);

        Assert.assertEquals(1f, output,0);
    }
    @Test
    public void testVolAbvTop(){
        float vol = 1.1f;
        float output = AppSettings.volLim(vol);

        Assert.assertEquals(1.0f, output,0);
    }
}
