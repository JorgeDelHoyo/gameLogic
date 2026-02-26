package bacteriaLifeLogicTestRefactorTest;

import org.example.bacteriaLifeLogicRefactor.BacteriaLifeLogicRefactor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class BacteriaLifeLogicRefactorTest {

    private static final Logger log = LoggerFactory.getLogger(BacteriaLifeLogicRefactorTest.class);
    private BacteriaLifeLogicRefactor logic;

    @BeforeEach
    public void setUp() {
        logic = new BacteriaLifeLogicRefactor(3);
    }

    @Test
    public void testGenerateInitialGen(){
        int[][] gen = logic.generateInitialGen();

        assertEquals(3,gen.length,"3 filas");
        assertEquals(3,gen[0].length,"3 columnas");

        assertTrue(gen[0][0] == 0 || gen[0][0] == 1,"celda 0,0 debe ser 0 o 1");
        assertTrue(gen[1][1] == 0 || gen[1][1] == 1,"celda 1,1 debe ser 0 o 1");
        assertTrue(gen[2][2] == 0 || gen[2][2] == 1,"celda 2,2 debe ser 0 o 1");
    }

    @Test
    public void testInBounds(){
        int[][] gen = {
                {1,1,0},
                {1,0,0},
                {0,0,1}
        };

        int vecinosCentro = logic.checkNeighbours(gen,1,1);
        assertEquals(4,vecinosCentro,"4 vecinos del centro");

        int vecinosEsquina = logic.checkNeighbours(gen,0,2);
        assertEquals(1,vecinosEsquina,"Debe detectar 1 vecino");
    }

    @Test
    public void testRegla_Nacimiento(){
        int[][] gen = {
                {1,1,0},
                {1,0,0},
                {0,0,0}
        };
        int[][] newGen = logic.generateNewGen(gen);
        assertEquals(1,newGen[1][1],"Nace en el centro");
    }

    @Test
    public void testRegala_MuerteSoledad(){
        int[][] gen = {
                {0,0,0},
                {0,1,1},
                {0,0,0}
        };
        int[][] newGen = logic.generateNewGen(gen);
        assertEquals(0,newGen[1][1],"Debería morir");
    }

    @Test
    public void testRegla_MuerteAsfixia(){
        int[][] gen = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };
        int[][] newGen = logic.generateNewGen(gen);
        assertEquals(0,newGen[1][1],"Muere por asfixia");
    }

    @Test
    public void testRegla_Supervivencia(){
        int[][] gen = {
                {1,0,0},
                {0,1,0},
                {0,0,1}
        };
        int[][] newGen = logic.generateNewGen(gen);
        assertEquals(1, newGen[1][1],"Debe sobrevivir");
    }

    @Test
    public void testCheckStableGen() {
        int[][] gen1 = {{1, 0}, {0, 1}};
        int[][] gen2 = {{1, 0}, {0, 1}};
        int[][] genDistinta = {{0, 0}, {0, 0}};

        assertTrue(logic.checkStableGen(gen1, gen2));
        assertFalse(logic.checkStableGen(gen1, genDistinta));
    }

    @Test
    public void testGetRound(){
        assertEquals(0,logic.getRound(),"Ronda inicial");

        int[][] gen = new int[3][3];
        logic.generateNewGen(gen);

        assertEquals(1,logic.getRound(),"Ronda iniciada");
    }

    @Test
    public void testMaxRoundsLimit(){
        int[][] genOriginal = new int[3][3];

        logic.round = 300;

        int[][] genResultado = logic.generateNewGen(genOriginal);

        assertEquals(genResultado,genResultado,"Debe devolver lo mismo");
    }

}
