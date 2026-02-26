package hangmanLogicTest;

import org.example.hangmanLogic.HangmanLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Clase test org.example.hangmanLogic
 */
public class HangmanLogicTest {

    private static final Logger log = LoggerFactory.getLogger(HangmanLogicTest.class);
    private HangmanLogic logic;

    @BeforeEach
    public void setUp() {
        logic = new HangmanLogic();
    }

    @Test
    public void testSetAndGetSecret(){
        logic.setSecret("secret");

        assertEquals("secret",logic.getSecret(),"La palabra secreta debería ser secret");
    }

    @Test
    public void testInitialWordState(){
        logic.setSecret("GATO");

        boolean acierto = logic.guessLetter("A");

        assertTrue(acierto,"Debería devolver true");
        assertEquals("_ A _ _ ",logic.getCurrentWordState());
    }

    @Test
    public void testGuessLetterMultipleOccurrences(){
        logic.setSecret("PATATA");
        boolean acierto = logic.guessLetter("A");

        assertTrue(acierto);
        assertEquals("_ A _ A _ A ",logic.getCurrentWordState());
    }

    @Test
    public void testSequentialGuesses(){
        logic.setSecret("SOL");

        logic.guessLetter("S");
        assertEquals("S _ _ ",logic.getCurrentWordState());

        logic.guessLetter("L");
        assertEquals("S _ L ",logic.getCurrentWordState());

        logic.guessLetter("O");
        assertEquals("S O L ",logic.getCurrentWordState());
    }
}
