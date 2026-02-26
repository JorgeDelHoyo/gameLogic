package treasureHuntLogicTest;

import org.apache.commons.logging.Log;
import org.example.treasureHuntLogic.TreasureHuntLogic;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TreasureHuntLogicTest {

    private static final Logger log = LoggerFactory.getLogger(TreasureHuntLogicTest.class);

    @Test
    public void testGetterYEstadoInicial(){
        TreasureHuntLogic logic = new TreasureHuntLogic(10,10,3);

        assertEquals(10,logic.getRows());
        assertEquals(10,logic.getCols());
        assertEquals(3,logic.getLives());
        assertNull(logic.getCurrentState(),"Al inicio no está generado");
    }

    @Test
    public void testCheckIfWall(){
        TreasureHuntLogic logic = new TreasureHuntLogic(5,5,3);
        assertTrue(logic.checkIfWall(0,2),"Arriba es pared");
        assertTrue(logic.checkIfWall(4,2),"Abajo es pared");
        assertTrue(logic.checkIfWall(2,0),"Izquierda es pared");
        assertTrue(logic.checkIfWall(2,4),"Derecha es pared");

        assertFalse(logic.checkIfWall(2,2),"Centro no es pared");
    }

    @Test
    public void testGenerateMaze(){
        TreasureHuntLogic logic = new TreasureHuntLogic(5,5,3);
        char[][] maze = logic.generateMaze();

        assertNotNull(maze);
        assertEquals(5, maze.length);
        assertEquals(5,maze[0].length);

        int pRow = logic.playerRow;
        int pCol = logic.playerCol;
        assertEquals('P',maze[pRow][pCol],"El jugador debe estar en sus coordenadas");
    }

    @Test
    public void testGetRandomEmptyPosition(){
        TreasureHuntLogic logic = new TreasureHuntLogic(4,4,3);

        logic.maze = new char[][]{
                {'#', '#', '#', '#'},
                {'#', 'X', 'T', '#'},
                {'#', '#', '.', '#'}, // Única casilla con '.'
                {'#', '#', '#', '#'}
        };

        int[] pos = logic.getRandomEmptyPosition();

        assertEquals(2,pos[0]);
        assertEquals(2,pos[1]);
    }

    // =========================================================
    // TESTS DE MOVIMIENTO (Mini-laberinto trucado a mano)
    // P = Jugador en [1][2]
    // W (Arriba) -> Pared '#'
    // S (Abajo)  -> Vacío '.'
    // A (Izda)   -> Trampa 'X'
    // D (Dcha)   -> Tesoro 'T'
    // =========================================================

    @Test
    public void testMovePlayer_Wall_Up() {
        TreasureHuntLogic logic = new TreasureHuntLogic(4, 5, 2);
        logic.maze = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', 'X', 'P', 'T', '#'},
                {'#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#'}
        };
        logic.playerRow = 1;
        logic.playerCol = 2;

        String result = logic.movePlayer('W');

        assertEquals("", result, "No pasa nada al chocar");
        assertEquals(1, logic.playerRow, "El jugador no se mueve");
    }

    @Test
    public void testMovePlayer_Empty_Down(){
        TreasureHuntLogic logic = new TreasureHuntLogic(4,5,2);
        logic.maze = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', 'X', 'P', 'T', '#'},
                {'#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#'}
        };

        logic.playerRow = 1;
        logic.playerCol = 2;

        String result = logic.movePlayer('S');

        assertEquals("",result);
        assertEquals(2,logic.playerRow,"Debe bajar una fila");
        assertEquals('P',logic.maze[2][2],"Nueva posición de P");
        assertEquals('.',logic.maze[1][2],"Posición antigua limpia");
    }

    @Test
    public void testMovePlayer_Treasure_Right(){
        TreasureHuntLogic logic = new TreasureHuntLogic(4,5,2);
        logic.maze = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', 'X', 'P', 'T', '#'},
                {'#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#'}
        };

        logic.playerRow = 1;
        logic.playerCol = 2;

        String result = logic.movePlayer('D');

        assertEquals("treasure", result);
        assertEquals(3,logic.playerCol,"Debe avanzar una columna");
        assertEquals('P',logic.maze[1][3], "El jugado ocupa la casilla del tesoro");
    }

    @Test
    public void testMovePlayer_TrapSurvive_Left(){
        TreasureHuntLogic logic = new TreasureHuntLogic(4,5,2);
        logic.maze = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', 'X', 'P', 'T', '#'},
                {'#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#'}
        };

        logic.playerRow = 1;
        logic.playerCol = 2;

        String result = logic.movePlayer('A');

        assertEquals("trap", result,"Sobrevive a la trampa");
        assertEquals(1,logic.lives,"Pierde 1 vida");
        assertEquals(1,logic.playerCol,"Avanza a la trampa");
    }

    @Test
    public void testMovePlayer_TrapLoss_Left(){
        TreasureHuntLogic logic = new TreasureHuntLogic(4,5,1);
        logic.maze = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', 'X', 'P', 'T', '#'},
                {'#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#'}
        };
        logic.playerRow = 1;
        logic.playerCol = 2;

        String result = logic.movePlayer('A');

        assertEquals("loss",result,"No quedan vidas");
        assertEquals(0,logic.lives,"Quedan 0 vidas");
    }

}
