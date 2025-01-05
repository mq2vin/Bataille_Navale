import java.util.Scanner;
public class Methodes {

    public static Scanner scanner = new Scanner(System.in);
    public static String couleurRougeVif = "\u001b[91m";
    public static String couleurRouge = "\u001b[31m";
    public static String couleurMarron = "\u001b[33m";
    public static String couleurVert = "\u001b[32m";
    public static String couleurBleu = "\u001b[94m";
    public static String reset = "\u001b[0m";


     // Initialise plusieurs grilles de jeu (plateaux) avec le caractère ~ représentant une case vide
    public static void generationDesPlateau(char[][] plateaujoueur1, char[][] plateaujoueur2, char[][] plateauTirJoueur1, char[][] plateauIA, char[][] plateauTirJoueur2, char[][] plateaTirIA) {
        for (int ligne = 0; ligne < plateaujoueur1.length; ligne++) {
            for (int col = 0; col < plateaujoueur1[ligne].length; col++) {
                plateaujoueur1[ligne][col] = '~';
                plateaujoueur2[ligne][col] = '~';
                plateauIA[ligne][col] = '~';
                plateauTirJoueur1[ligne][col] = '~';
                plateauTirJoueur2[ligne][col] = '~';
                plateaTirIA[ligne][col] = '~';
            }
        }
    }


     // Affiche une grille de jeu avec des couleurs ANSI en fonction des symboles présents (eau, navires, tirs)
    public static void afficherPlateau(char[][] plateau){
        int compteur =1;
        System.out.println("    "+"A  "+"B  "+"C  "+"D  "+"E  "+"F  "+"G  "+"H  "+"I  "+"J");
        for (int i = 0; i < plateau.length; i++) {
            System.out.printf("%2d  ",compteur);
            compteur++;
            for (int j = 0; j < plateau[i].length; j++) {
                switch (plateau[i][j]) {
                    case '~':System.out.print(couleurBleu + "~" + "  ");break;
                    case 'P':System.out.print(couleurMarron + "P" + "  ");break;
                    case 'C':System.out.print(couleurMarron + "C" + "  ");break;
                    case 'c':System.out.print(couleurMarron + "c" + "  ");break;
                    case 'S':System.out.print(couleurMarron + "S" + "  ");break;
                    case 'T':System.out.print(couleurMarron + "T" + "  ");break;
                    case 'X':System.out.print(couleurRouge + "X" + "  ");break;
                    case 'O':System.out.print(couleurVert + "O" + "  ");break;
                    default:System.out.print(plateau[i][j] + "  ");
                }
            }
            System.out.print(reset);
            System.out.println();
        }
        System.out.println();
    }


     // Gère l'initialisation des pseudos et la phase de placement des navires sur les grilles de jeu en mode joueur contre joueur ou joueur contre IA
    public static void preparationPartie(boolean ia){
        if (ia){
            System.out.println("\nEntrez votre pseudo :");
            System.out.print(couleurMarron);
            GestionBatailleNaval.pseudoJoueur1 = scanner.nextLine();
            System.out.println(reset);

            sautDelignes();

            afficherPlateau(GestionBatailleNaval.plateaujoueur1);
            placementNavireJoueur(GestionBatailleNaval.pseudoJoueur1, GestionBatailleNaval.plateaujoueur1);

            placementNaviresIA(GestionBatailleNaval.plateauIA);
            sautDelignes();
        }
        else {
            System.out.println("\nJoueur 1 entrez votre pseudo :");
            System.out.print(couleurMarron);
            GestionBatailleNaval.pseudoJoueur1 = scanner.nextLine();
            System.out.println(reset);

            do {
                System.out.println("Joueur 2 entrez votre pseudo :");
                System.out.print(couleurMarron);
                GestionBatailleNaval.pseudoJoueur2 = scanner.nextLine();
                System.out.println(reset);
            }while (GestionBatailleNaval.pseudoJoueur1.equals(GestionBatailleNaval.pseudoJoueur2));


            sautDelignes();

            afficherPlateau(GestionBatailleNaval.plateaujoueur1);
            placementNavireJoueur(GestionBatailleNaval.pseudoJoueur1, GestionBatailleNaval.plateaujoueur1);

            sautDelignes();

            System.out.println("À TOI " + GestionBatailleNaval.pseudoJoueur2.toUpperCase() + " !!\n");

            afficherPlateau(GestionBatailleNaval.plateaujoueur2);
            placementNavireJoueur(GestionBatailleNaval.pseudoJoueur2, GestionBatailleNaval.plateaujoueur2);

            sautDelignes();
        }
    }


     // Permet à un joueur de placer ses navires sur une grille en spécifiant leurs positions et orientations
    public static void placementNavireJoueur(String joueur, char [][]plateau){
        boolean estplacer, ia = false;
        int[] tailleNavire = {5, 4, 3, 3, 2};
        int compteur=0, ligne, colonne;
        String typeNavire, position;
        char direction;

        for (int i=0; i< tailleNavire.length;i++) {

            switch (tailleNavire[i]){
                case 5:typeNavire= couleurMarron + "le Porte-avion (P)" + reset; break;
                case 4:typeNavire= couleurMarron + "le Croiseur (C)" + reset; break;
                case 3:
                    if (compteur==2){
                        typeNavire= couleurMarron + "le Contre-torpilleur (c)" + reset; break;
                    }
                    else {
                        typeNavire= couleurMarron + "le Sous-marin (S)" + reset; break;
                    }
                case 2:typeNavire= couleurMarron + "le torpilleur (T)" + reset; break;
                default:typeNavire= couleurMarron + "le navire" + reset;
            }

            do{
                do {
                    System.out.println(joueur + ", place " + typeNavire + " de taille " + tailleNavire[i] + " (format : A1) :");
                    position = scanner.next().toUpperCase();
                } while (saisiCoordoneeIncorrect(position));
                do {
                    System.out.println("sens ? (h/v)");
                    direction = scanner.next().toLowerCase().charAt(0);
                }while (direction != 'v' && direction != 'h');

                ligne = Integer.parseInt(position.substring(1)) - 1;
                colonne = position.charAt(0) - 'A';

                estplacer = placementNavire(ia,plateau, tailleNavire[i],compteur, colonne, ligne, direction);
            }while (!estplacer);

            compteur++;
            sautDelignes();
            afficherPlateau(plateau);
        }
    }


     // Vérifie si une saisie de coordonnées par un joueur est valide
    public static boolean saisiCoordoneeIncorrect(String position){
        if(position.length() > 3 || position.length() < 2){
            return true;
        }
        else if (position.length()==3){
            if ( position.charAt(1) != '1' || position.charAt(2) != '0'){
                return true;
            }
        }
        // cas où l'utillisateur s'amuserait a mettre autre chose que les dix premières de l'alphabet (exemple: mettre un R a la place d'un A
        if (position.charAt(0) - 'A' > 9){
            return true;
        }

        if (position.charAt(1) == '0'){
            return true;
        }

        int compteur = 0;
        char [] nombre = {'0','1','2','3','4','5','6','7','8','9'};
        for (int i = 0; i < nombre.length; i++) {
            if (position.charAt(1) == nombre[i]){
                compteur++;
            }
            // cas où l'utillisateur s'amuserait à mettre que des chiffres
            if (position.charAt(0) == nombre[i]){
                return true;
            }
        }

        if (compteur != 1){
            return true;
        }

        return false;
    }


     // Place aléatoirement les navires de l'IA sur une grille
    public static void placementNaviresIA(char[][] plateau) {
        boolean ia = true, estplacer;
        int[] tailleNavire = {5, 4, 3, 3, 2}; // Longueurs des bateaux
        int compteur=0, ligne, colonne;
        char direction;
        double sens;

        for (int i=0; i< tailleNavire.length;i++) {

                sens = Math.random();
                if (sens < 0.5){
                    direction = 'h';
                }
                else {
                    direction = 'v';
                }

                do{
                    ligne = (int)(Math.random() * 10);
                    colonne = (int)(Math.random() * 10);
                    estplacer = placementNavire(ia,plateau, tailleNavire[i],compteur, colonne, ligne, direction);
                }while (!estplacer);

                compteur++;

        }
    }


     // Place un navire sur une grille après validation des contraintes (collision, hors-limite)
    public static boolean placementNavire(boolean ia, char[][] plateau, int taille, int compteurs, int colonne, int ligne, char direction){

        if ( ((plateau.length < ligne + taille) && (direction=='v')) || ((plateau[0].length < colonne + taille) && (direction == 'h'))  ){
            if (!ia){
                System.out.println("Placement invalide!");
            }
            return false;
        }
        else {
            // Vérification des collisions
            for (int i = 0; i < taille; i++) {
                if ((direction == 'h' && plateau[ligne][colonne + i] != '~') || (direction == 'v' && plateau[ligne + i][colonne] != '~')) {
                    if(!ia){
                        System.out.println("Collision détectée! Une case est déjà occupée.");
                    }
                    return false;
                }
            }

            //placement porte avion
            if (taille==5){
                if (direction == 'h') {
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne][colonne + i] = 'P';
                    }
                }
                else{
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne+i][colonne] = 'P';
                    }

                }
            }
            else if (taille==4) {
                if (direction == 'h') {
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne][colonne + i] = 'C';
                    }
                }
                else{
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne+i][colonne] = 'C';
                    }

                }
            }
            else if (taille==3 && compteurs==2) {
                if (direction == 'h') {
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne][colonne + i] = 'c';
                    }
                }
                else{
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne+i][colonne] = 'c';
                    }

                }
            }
            else if (taille==3 && compteurs==3) {
                if (direction == 'h') {
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne][colonne + i] = 'S';
                    }
                }
                else{
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne+i][colonne] = 'S';
                    }

                }
            }
            else {
                if (direction == 'h') {
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne][colonne + i] = 'T';
                    }
                }
                else{
                    for (int i = 0; i < taille; i++) {
                        plateau[ligne+i][colonne] = 'T';
                    }

                }
            }

        }
        return true;
    }


     // Alternance des tours entre les joueurs (ou IA) jusqu'à ce qu'une victoire soit déclarée
    public static void gestionTour(boolean ia, char[][] plateauJoueur1,char[][] plateauJoueur2,char[][] plateauTirJoueur1,char[][] plateauTirJoueur2){
        int i = 1;
        boolean toucher;

        if (ia){
            while (!tousCoules(plateauJoueur1) && !tousCoules(plateauJoueur2)){
                if (i % 2 != 0){
                    System.out.println("\nA TOI DE JOUER " + GestionBatailleNaval.pseudoJoueur1 + ": \n");
                    afficherPlateau(plateauTirJoueur2);
                    affichageDesNaviresCoules(plateauJoueur2);
                    toucher= gestionTir(plateauJoueur2, plateauTirJoueur2);

                    if (toucher){
                        System.out.println(GestionBatailleNaval.pseudoJoueur1 + " a touché un navire !!");
                    }
                    else {
                        System.out.println("À l'eau !! ");
                    }
                    affichageDesNaviresCoules(plateauJoueur2);
                }
                else {
                    System.out.println("IA :");
                    toucher = gestionTirIa(plateauJoueur1, plateauTirJoueur1);
                    afficherPlateau(plateauTirJoueur1);
                    if (toucher){
                        System.out.println("L'IA a touché un navire !!");
                    }
                    else {
                        System.out.println("L'IA a tiré dans l'eau !! ");
                    }
                    affichageDesNaviresCoules(plateauJoueur1);
                }

                if (tousCoules(plateauJoueur2)){
                    sautDelignes();
                    System.out.println("\nVICTOIRE de " + GestionBatailleNaval.pseudoJoueur1 + " en " + i + " tours !!\n");
                    GestionBatailleNaval.finDePartie();
                }
                else if (tousCoules(plateauJoueur1)) {
                    sautDelignes();
                    System.out.println("\nVICTOIRE de l'IA en " + i + " tours !!\n");
                    GestionBatailleNaval.finDePartie();
                }
                i++;
            }
        }
        else {
            while(!tousCoules(plateauJoueur1) && !tousCoules(plateauJoueur2)){
                if (i % 2 != 0){
                    System.out.println("\nTOUR DE " + GestionBatailleNaval.pseudoJoueur1 + ": \n");
                    afficherPlateau(plateauTirJoueur2);
                    toucher = gestionTir(plateauJoueur2, plateauTirJoueur2);
                    afficherPlateau(plateauTirJoueur2);
                    if (toucher){
                        System.out.println(GestionBatailleNaval.pseudoJoueur1 + " a touché un navire !!");
                    }
                    else {
                        System.out.println("A l'eau !! ");
                    }
                    affichageDesNaviresCoules(plateauJoueur2);
                }
                else {
                    System.out.println("\nTOUR DE  " + GestionBatailleNaval.pseudoJoueur2 + ": \n");
                    afficherPlateau(plateauTirJoueur1);
                    toucher = gestionTir(plateauJoueur1,plateauTirJoueur1);
                    afficherPlateau(plateauTirJoueur1);
                    if (toucher){
                        System.out.println(GestionBatailleNaval.pseudoJoueur2 + " a touché un navire !!");
                    }
                    else {
                        System.out.println("A l'eau !! \n");
                    }
                    affichageDesNaviresCoules(plateauJoueur1);
                }

                if (tousCoules(plateauJoueur2)){
                    sautDelignes();
                    System.out.println("\nVICTOIRE de " + GestionBatailleNaval.pseudoJoueur1 + " en " + i + " tours !!\n");
                    GestionBatailleNaval.finDePartie();
                }
                else if (tousCoules(plateauJoueur1)) {
                    sautDelignes();
                    System.out.println("\nVICTOIRE de " + GestionBatailleNaval.pseudoJoueur2 + " en " + i + " tours !!\n");
                    GestionBatailleNaval.finDePartie();
                }
                i++;
            }
        }

    }


     // Gère un tir effectué par un joueur, en mettant à jour les plateaux et en vérifiant les impacts
    public static boolean gestionTir(char[][] plateau, char[][] plateauTir){
        String position;
        do {
            System.out.println("Entrez tir (format : A1): ");
            System.out.print(couleurRougeVif);
            position = scanner.next().toUpperCase();
            System.out.println(reset);
        }while (saisiCoordoneeIncorrect(position));


        int ligne = Integer.parseInt(position.substring(1)) - 1; // converti "A1" en indice : position.substring(1) permet d'avoir "1" puis Integer.parseInt() convertit
        int colonne = position.charAt(0) - 'A';

        while (plateau[ligne][colonne] == 'X'){
            System.out.println("Tir invalide vous avez déjà tiré ici !!  ");
            System.out.println("Entrez les coordonées pour le tir (format : A1): ");
            System.out.print(couleurRougeVif);
            position = scanner.next().toUpperCase();
            System.out.println(reset);
            ligne = Integer.parseInt(position.substring(1)) - 1; // converti "A1" en indice : position.substring(1) permet d'avoir "1" puis Integer.parseInt() convertit
            colonne = position.charAt(0) - 'A';
        }

        if (plateau[ligne][colonne] == 'P' || plateau[ligne][colonne] == 'C' || plateau[ligne][colonne] == 'c' || plateau[ligne][colonne] == 'S' || plateau[ligne][colonne] == 'T'){
            plateauTir[ligne][colonne] = 'X';
            plateau[ligne][colonne] = 'X';
            return true;
        }
        else {
            plateauTir[ligne][colonne] = 'O';
            plateau[ligne][colonne] = 'O';
            return false;
        }

    }


     // logique de tir de l'IA, qui vise des cases stratégiquement ou aléatoirement
    public static boolean gestionTirIa(char[][] plateau, char[][] plateauTir) {
        int ligne = 0, colonne = 0;
        boolean cibleTrouvee = false;

        // Recherche des zones proches aux 'X' pour continuer à viser un navire
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'X') {
                    // Vérifier les cases adjacentes
                    //au dessus
                    if (estTirable(plateau, i + 1, j)) {
                        ligne = i + 1;
                        colonne = j;
                        cibleTrouvee = true;
                        break;
                    }
                    //en dessous
                    else if (estTirable(plateau, i - 1, j)) {
                        ligne = i - 1;
                        colonne = j;
                        cibleTrouvee = true;
                        break;
                    }
                    //a droite
                    else if (estTirable(plateau, i, j + 1)) {
                        ligne = i;
                        colonne = j + 1;
                        cibleTrouvee = true;
                        break;
                    }
                    //a gauche
                    else if (estTirable(plateau, i, j - 1)) {
                        ligne = i;
                        colonne = j - 1;
                        cibleTrouvee = true;
                        break;
                    }
                }
            }
            if (cibleTrouvee) {
                break;
            }
        }

        // Si aucune cible proche n'est trouvée, tirer aléatoirement
        if (!cibleTrouvee) {
            do {
                ligne = (int) (Math.random() * 10);
                colonne = (int) (Math.random() * 10);
            } while (plateau[ligne][colonne] == 'X' || plateau[ligne][colonne] == 'O');
        }

        // Effectuer le tir
        if (plateau[ligne][colonne] == 'P' || plateau[ligne][colonne] == 'C' || plateau[ligne][colonne] == 'c' || plateau[ligne][colonne] == 'S' || plateau[ligne][colonne] == 'T') {
            plateauTir[ligne][colonne] = 'X';
            plateau[ligne][colonne] = 'X';
            return true;
        } else {
            plateauTir[ligne][colonne] = 'O';
            plateau[ligne][colonne] = 'O';
            return false;
        }
    }


     // Vérifie si une case est disponible pour un tir
    public static boolean estTirable(char[][] plateau, int ligne, int colonne) {
        if (ligne >= 0 && ligne < plateau.length && colonne >= 0 && colonne < plateau[0].length && plateau[ligne][colonne] != 'X' && plateau[ligne][colonne] != 'O'){
            return true;
        }
        return false;
    }


     // Ajoute des lignes vides pour améliorer la lisibilité de la console
    public static void affichageDesNaviresCoules(char [][]plateau){
        boolean PAcoules = true, CroiseurCoules = true, CTorpilleurCoules = true, torpilleurCoules = true, sMarinCoules = true;

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'P') {
                    PAcoules = false;
                    break;
                }
            }
            if (!PAcoules) {
                break;
            }
        }

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'C') {
                    CroiseurCoules = false;
                    break;
                }
            }
            if (!CroiseurCoules) {
                break;
            }
        }

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'c') {
                    CTorpilleurCoules = false;
                    break;
                }
            }
            if (!CTorpilleurCoules) {
                break;
            }
        }

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'S') {
                    sMarinCoules = false;
                    break;
                }
            }
            if (!sMarinCoules) {
                break;
            }
        }

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'T') {
                    torpilleurCoules = false;
                    break;
                }
            }
            if (!torpilleurCoules) {
                break;
            }
        }

        if (PAcoules) {
            System.out.println("\nLe Porte avion a été coulé.");
        }

        if (CroiseurCoules) {
            System.out.println("\nLe Croiseur a été coulé.");
        }

        if (sMarinCoules) {
            System.out.println("\nLe sous-marin a été coulé.");
        }

        if (CTorpilleurCoules) {
            System.out.println("\nLe Contre-Torpilleur a été coulé.");
        }

        if (torpilleurCoules) {
            System.out.println("\nLe Torpilleur a été coulé.");
        }
    }


     // Vérifie si tous les navires d'une grille ont été coulés
    public static boolean tousCoules(char[][] plateau){
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'P' || plateau[i][j] == 'C' || plateau[i][j] == 'c' || plateau[i] [j] == 'S' || plateau[i][j] == 't' || plateau[i][j] == 'T'){
                    return false;
                }
            }
        }
        return true;
    }


     // Affiche les noms des navires coulés sur le plateau
    public static void sautDelignes(){
        for (int i = 0; i < 200; i++) {
            System.out.println();
        }
    }

}