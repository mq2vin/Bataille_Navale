import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MethodeTest {

    @Test
    public void testTousCoules() {

        char[][] plateau1 = {
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'}
        };
        assertTrue(Methodes.tousCoules(plateau1), "Il n'y a pas de navire sur un plateau \"vide\", devrait retourner true.");

        char[][] plateau2 = {
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', 'P','P', 'P', 'P','P', '~', '~', 'T'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', 'T'},
                {'~', 'c', 'c','c', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', 'C', 'C','C', 'C', '~', '~'},
                {'~', '~', 'S','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', 'S','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', 'S','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'}
        };
        assertFalse(Methodes.tousCoules(plateau2), "Aucun navire n'est coulé, devrait retourner false.");

        char[][] plateau3 = {
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','X', 'X', 'X','X', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', 'X', 'X','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', 'C', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', 'C', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', 'C', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','X', 'X', 'X', '~'}
        };
        assertFalse(Methodes.tousCoules(plateau3), "Un navire (non touché) n'est pas coulé, devrait retourner false.");

        char[][] plateau4 = {
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','X', 'X', 'X','X', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', 'X', 'X','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', 'C', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', 'X', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', 'X', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','X', 'X', 'X', '~'}
        };
        assertFalse(Methodes.tousCoules(plateau4), "un navire (touché) n'est pas coulé, devrait retourner false.");

        char[][] plateau5 = {
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','X', 'X', 'X','X', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', 'X', 'X','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', 'X', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', 'X', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', 'X', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', 'X', '~', '~'},
                {'~', '~', '~','~', '~', '~','~', '~', '~', '~'},
                {'~', '~', '~','~', '~', '~','X', 'X', 'X', '~'}
        };
        assertTrue(Methodes.tousCoules(plateau5), "Tous Coulés, devrait retourner true.");
    }

    @Test
    public void testPlacementNavire() {
        char[][] plateau1 = new char[10][10];
        char[][] plateau2 = new char[10][10];
        char[][] plateau3 = new char[10][10];
        char[][] plateau4 = new char[10][10];
        char[][] plateau5 = new char[10][10];
        char[][] plateau6 = new char[10][10];

        Methodes.generationDesPlateau(plateau1,plateau2,plateau3,plateau4,plateau5,plateau6);

        //placement horizontal d'un contre-torpilleur par un joueur
        assertTrue(Methodes.placementNavire(false, plateau1, 3,2, 1, 2, 'h'), "Placement horizontal valide, devrait retourner true.");
        assertEquals('c', plateau1[2][1]);
        assertEquals('c', plateau1[2][2]);
        assertEquals('c', plateau1[2][3]);


        //placement vertical d'un croiseur par un joueur
        assertTrue(Methodes.placementNavire(false, plateau2, 4, 1,2, 0, 'v'), "Placement vertical valide, devrait retourner true.");
        assertEquals('C', plateau2[0][2]);
        assertEquals('C', plateau2[1][2]);
        assertEquals('C', plateau2[2][2]);
        assertEquals('C', plateau2[3][2]);


        //collision (joueur)
        Methodes.placementNavire(false, plateau3, 4, 1,1, 2, 'h');
        assertFalse(Methodes.placementNavire(false, plateau3, 3, 3,1, 2, 'h'), "Collision détectée, devrait retourner false.");

        //placement hors limites (joueur)
        assertFalse(Methodes.placementNavire(false, plateau4, 5,0, 10, 2, 'h'), "Placement hors limites horizontal, devrait retourner false.");

        //placement horizontal d'un sous-marin par l'ia
        assertTrue(Methodes.placementNavire(true, plateau5, 3,3, 1, 2, 'h'), "Placement horizontal valide, devrait retourner true.");
        assertEquals('S', plateau5[2][1]);
        assertEquals('S', plateau5[2][2]);
        assertEquals('S', plateau5[2][3]);


        //placement vertical d'un torpilleur par l'ia
        assertTrue(Methodes.placementNavire(true, plateau6, 2, 4,2, 0, 'v'), "Placement vertical valide, devrait retourner true.");
        assertEquals('T', plateau6[0][2]);
        assertEquals('T', plateau6[1][2]);
    }


}