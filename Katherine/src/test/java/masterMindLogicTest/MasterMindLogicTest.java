package masterMindLogicTest;

import org.example.masterMindLogic.MasterMindLogic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MasterMindLogicTest {

    private static final Logger log = LoggerFactory.getLogger(MasterMindLogicTest.class);
    @Spy private MasterMindLogic logic;

    @Test
    public void testGenerateSecret(){
        Color[] secreto = logic.generateSecret(4);

        assertNotNull(secreto);
        assertEquals(4,secreto.length);
    }

    @Test
    public void testShowSecret(){
        Color[] fixedSecret = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW};

        doReturn(fixedSecret).when(logic).generateSecret(4);

        logic.init(4);

        assertEquals("RGBY",logic.showSecret());
    }

    @Test
    public void testCheckGuess_TodoAciertos(){
        Color[] fixedSecret = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW};
        doReturn(fixedSecret).when(logic).generateSecret(4);
        logic.init(4);

        Color[] guess = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW};
        MasterMindLogic.Result result = logic.checkGuess(guess);

        assertEquals(4,result.blacks,"4 aciertos plenos");
        assertEquals(0,result.whites,"0 acierto parcial");
    }

    @Test
    public void testCheckGuess_TodoDesordenado(){
        Color[] fixedSecret = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW};
        doReturn(fixedSecret).when(logic).generateSecret(4);
        logic.init(4);

        Color[] guess = {Color.YELLOW,Color.BLUE,Color.GREEN,Color.RED};
        MasterMindLogic.Result result = logic.checkGuess(guess);

        assertEquals(0,result.blacks,"Ninguno en su sitio");
        assertEquals(4,result.whites,"Todas desordenadas");
    }

    @Test
    public void testConstructorConParametros(){
        Color[] palette = {Color.BLUE,Color.BLUE};
        String[] labels = {"B","B"};

        MasterMindLogic customLogic = new MasterMindLogic(palette,3,labels);

        String result = customLogic.showSecret();

        assertNotNull(result);
    }



}
