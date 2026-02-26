package bacteriaLifeLogicTest;

import org.example.bacteriaLifeLogic.BacteriaLifeLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class BacteriaLifeLogicTest {

    private static final Logger log = LoggerFactory.getLogger(BacteriaLifeLogicTest.class);
    private BacteriaLifeLogic logic;

    @BeforeEach
    public void setUp() {
        logic = new BacteriaLifeLogic(3);
    }

    @Test
    public void testGenerateInitialGen(){
        int[][] gen = logic.generateInitialGen();

        assertEquals(3, gen.length,"3 filas");
        assertEquals(3,gen[0].length,"3 columnas");

        assertTrue(gen[0][0] == 0 || gen[0][0] == 1, "La primera celda debe ser 0 o 1");
        assertTrue(gen[1][1] == 0 || gen[1][1] == 1, "El centro debe ser 0 o 1");
        assertTrue(gen[2][2] == 0 || gen[2][2] == 1, "La esquina infeior debe ser o 1");
    }

    @Test
    public void testInBounds(){
        int[][] gen = new int[3][3];

        assertTrue(BacteriaLifeLogic.inBounds(gen,1,1));
        assertTrue(BacteriaLifeLogic.inBounds(gen,0,0));

        assertFalse(BacteriaLifeLogic.inBounds(gen,-1,-1));
        assertFalse(BacteriaLifeLogic.inBounds(gen,3,3));
    }

    @Test
    public void testCheckNeighbours(){
        int[][] gen = {
                {1,1,0},
                {1,0,0},
                {0,0,1}
        };

        int vecinosCentro = BacteriaLifeLogic.checkNeighbours(gen,1,1);
        assertEquals(4,vecinosCentro, "El centro debe detectar 4 vecinos");

        int vecinosEsquina = BacteriaLifeLogic.checkNeighbours(gen,0,2);
        assertEquals(1,vecinosEsquina,"La esquina debe detectar 1 vecino");
    }

    @Test
    public void testRegla_nacimiento() {
        int[][] gen = {
                {1,1,0},
                {1,0,0},
                {0,0,0}
        };
        int[][] newGen = logic.generateNewGen(gen);

        assertEquals(1, newGen[1][1], "Debería nacer una bacteria en el centro");
    }

    @Test
    public void testRegla_MuerteSoledad(){
        int[][] gen = {
                {0,0,0},
                {0,1,1},
                {0,0,0}
        };
        int[][] newGen = logic.generateNewGen(gen);

        assertEquals(0,newGen[1][1],"Muere por solo 1 vecino");
    }

    @Test
    public void testRegla_MuerteAsfixia(){
        int[][] gen = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };
        int[][] newGen = logic.generateNewGen(gen);

        assertEquals(0,newGen[1][1],"Debería morir 4 vecinos");
    }

    @Test
    public void testRegla_Supervivencia(){
        int[][] gen = {
                {1,0,0},
                {0,1,0},
                {0,0,1}
        };
        int[][] newGen = logic.generateNewGen(gen);

        assertEquals(1, newGen[1][1],"Debería sobrevivir, 2 vecinos");
    }

    @Test
    public void testCheckStableGen(){
        int[][] gen1 = {{1,0},{0,1}};
        int[][] gen2 = {{1,0},{0,1}};

        int[][] genDistinta = {{0,0},{0,0}};

        assertTrue(BacteriaLifeLogic.checkStableGen(gen1, gen2), "Deben ser consideradas estables (idénticas)");
        assertFalse(BacteriaLifeLogic.checkStableGen(gen1, genDistinta), "No deben ser estables si cambian");
    }

    @Test
    public void testGetRound(){
        assertEquals(0, logic.getRound(), "La ronda inicial es 0");

        int[][] gen = new int[3][3];
        logic.generateNewGen(gen);

        assertEquals(1, logic.getRound(),"La ronda se ha incrementado");
    }
}
