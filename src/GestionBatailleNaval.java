import java.util.Scanner;
public class GestionBatailleNaval {
    public static Scanner scanner=new Scanner(System.in);

    public static char[][] plateaujoueur1 = new char[10][10];
    public static char[][] plateaujoueur2 = new char[10][10];
    public static char[][] plateauIA = new char[10][10];

    public static char[][] plateauTirJoueur1 = new char[10][10];
    public static char[][] plateauTirJoueur2 = new char[10][10];
    public static char[][] plateauTirIA = new char[10][10];


    public static String pseudoJoueur1 = "";
    public static String pseudoJoueur2 = "";
    public static boolean ia;

    /*
     * Gère le déroulement complet d'une partie de bataille navale.
     * - Génère les plateaux pour les joueurs et leurs tirs.
     * - Affiche le menu principal pour démarrer la partie.
     * - Lance la gestion des tours entre les deux joueurs jusqu'à la fin de la partie.
     */
    public static void gestionBataille(){

        Methodes.generationDesPlateau(plateaujoueur1,plateaujoueur2,plateauIA, plateauTirJoueur1, plateauTirJoueur2,plateauTirIA);
        menu();
        if (ia){
            Methodes.gestionTour(true, plateaujoueur1, plateauIA, plateauTirJoueur1, plateauTirIA);
        }
        else{
            Methodes.gestionTour(false, plateaujoueur1, plateaujoueur2, plateauTirJoueur1, plateauTirJoueur2);
        }
    }

    /*
     * Affiche le menu principal du jeu de bataille navale.
     * - Options proposées :
     *   1. Démarrer une nouvelle partie.
     *   2. Afficher les règles du jeu.
     *   3. Quitter le jeu.
     * - Gère la saisie utilisateur et exécute l'option sélectionnée.
     */
    public static void menu(){
        String marron = "\u001B[33m"; // Couleur marron
        String reset = "\u001B[0m";

        String texte = """
             ______       _        _   _        _   _                  _     \s
             | ___ \\     | |      (_) | |      | \\ | |                | |    \s
             | |_/ / __ _| |_ __ _ _| | | ___  |  \\| | __ ___   ____ _| | ___\s
             | ___ \\/ _` | __/ _` | | | |/ _ \\ | . ` |/ _` \\ \\ / / _` | |/ _ \\
             | |_/ / (_| | || (_| | | | |  __/ | |\\  | (_| |\\ V / (_| | |  __/
             \\____/ \\__,_|\\__\\__,_|_|_|_|\\___| \\_| \\_/\\__,_| \\_/ \\__,_|_|\\___| \s
       \s""";

        System.out.println(marron + texte + reset);
        System.out.println();
        System.out.println("""
                1. Démarer une partie joueur VS joueur
                2. Démarer une partie joueur VS IA
                3. Redécouvrir les règles
                4. Quitter
                """);
        System.out.println("Entrez votre choix");
        System.out.print("==>  ");
        String choix = scanner.nextLine();
        while (!choix.equals("1") && !choix.equals("2") && !choix.equals("3") && !choix.equals("4")) {
            System.out.println("\nChoix non valide réessayer");
            System.out.print("==>  ");
            choix = scanner.nextLine();
        }

        switch (choix) {
            case "1": ia = false; Methodes.preparationPartie(false); break;
            case "2": ia = true; Methodes.preparationPartie(true); break;
            case "3": reglesDuJeu(); break;
        }
    }


     // Affiche les règles du jeu de bataille navale
     public static void reglesDuJeu() {
         System.out.println("""
            \nRègles du Jeu : Bataille Navale
            
            \tObjectif du Jeu
            
            Le but de la bataille navale est de couler tous les navires de l'adversaire en devinant leur position sur sa grille.
            Le premier joueur (ou l'IA) à couler tous les navires ennemis remporte la partie.
            
            Mise en Place
            
            \tPlateaux de jeu :
            
            Chaque joueur (ou l'IA) a une grille (par exemple, 10x10) où placer ses navires.
            Les colonnes sont identifiées par des lettres (A à J) et les lignes par des chiffres (1 à 10).
            
            \tPlacement des navires :
            
            Chaque joueur dispose de plusieurs navires de tailles différentes :
            
            \t\t° Porte-avions (5 cases)
            \t\t° Croiseur (4 cases)
            \t\t° Contre-torpilleur (3 cases)
            \t\t° Sous-marin (3 cases)
            \t\t° Torpilleur (2 cases)
            
            Les navires doivent être placés horizontalement ou verticalement (jamais en diagonale).
            Les navires ne peuvent pas se chevaucher ni dépasser les bords de la grille.
            
            \tGrille vide pour référence :
            
                A  B  C  D  E  F  G  H  I  J
             1  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             2  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             3  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             4  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             5  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             6  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             7  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             8  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
             9  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
            10  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~ \s
            
            Avant de placer leurs navires, les joueurs peuvent consulter une grille vide pour planifier leur stratégie.
            
            Déroulement du Jeu
            
            \t\tTour par Tour :
            
            Chaque joueur joue à tour de rôle.
            
            Modes de Jeu :
            1. Joueur vs Joueur : Deux joueurs humains s'affrontent.
            2. Joueur vs IA : Un joueur affronte une Intelligence Artificielle.
            
            À son tour, un joueur :
            Devine une position sur la grille ennemie (exemple : B5).
            
            Résultats possibles :
            
            \t\t° Touché : Un navire ennemi occupe cette case.
            \t\t° À l'eau : Cette case est vide.
            \t\t° Coulé : Si toutes les cases d’un navire sont touchées, ce navire est détruit.
            
            \tMode Joueur vs IA :
            L'IA placera ses navires automatiquement de manière aléatoire.
            À chaque tour, elle sélectionnera une case à attaquer selon une stratégie ou aléatoirement.
            
            Chaque joueur voit sa propre grille avec ses navires et les attaques ennemies.
            La grille de l’adversaire montre seulement les attaques réussies (Touché ou À l'eau).
            
            Conditions de Victoire
            
            Le jeu se termine dès qu'un joueur (ou l'IA) a coulé tous les navires de son adversaire.
            Ce joueur est déclaré vainqueur.
            
            Règles Supplémentaires
            
            \t\tValidité des tirs :
            
            Un joueur ne peut pas tirer deux fois sur la même case.
            Si cela se produit, il est averti, et il doit choisir une nouvelle position.
            
            Stratégie de placement :
            
            Les navires doivent être placés de manière stratégique pour éviter de révéler des schémas évidents à l’adversaire.
            
            Après chaque tour, la grille ennemie mise à jour est affichée avec les résultats des attaques (X pour touché, O pour à l'eau).
           
            Le jeu continue jusqu’à ce que tous les navires d’un joueur soient coulés.
            
            """);
         finDePartie();
     }


     // Affiche les options pour terminer ou continuer la partie après un jeu.
    public static void finDePartie(){
        System.out.println("""
                1. Retour au menu
                2. Quitter

                """);
        System.out.println("Entrez votre choix");
        System.out.print("==>  ");
        String choix = scanner.nextLine();
        while (!choix.equals("1") && !choix.equals("2")) {
            System.out.println("\nChoix non valide réessayer");
            System.out.print("==>  ");
            choix = scanner.nextLine();
        }
        if (choix.equals("1")) {
            Methodes.sautDelignes();
            GestionBatailleNaval.gestionBataille();
        }
    }
}
