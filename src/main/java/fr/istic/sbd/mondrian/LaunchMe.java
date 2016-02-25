package fr.istic.sbd.mondrian;

import java.util.*;

public class LaunchMe 
{ 
    public static void main(String[] args)
    {
        /**
         * on définit le parametere de confidentialité k
         */
        int k = 2;
        /*
         * on definit ici la cardinalite 'n' du jeu de données 
         */
        int n = 4;
        /**
         * on definit ici la borne du premier attribut de notre QID
         * Tableau d'entier
         */
        int[] firstQidValues = new int[]{75001, 75005};
        /**
         * on definit ici la borne du second attribut de notre QID
         * Tableau d'entier
         */
        int[] secondQidValues = new int[]{20, 25};
        /**
         * on définit ici le nombre de valeurs distictes de nos données sensibles qui sont
         * générées aléatoirement
         */
        int randomStringSetLength = 5;
        /**
         * on definit ici le nombre de caracteres de nos données sensibles générées 
         */
        int randomStringLength = 5;
        /**
         * ici génération de notre jeu de données
         * avec ecriture des quasi-identifiants dans le fichier dataset.csv 
         */
        List<Data> data = DataSetHelper.createDataSet(n, firstQidValues, secondQidValues, randomStringSetLength, randomStringLength);
        System.out.println("Affichage de notre jeu de données");
        /**
         * Affichage du jeu de données généré
         */
        DataSetHelper.toString(data);
        /**
         * Appelle de la fonction mondrian
         * avec le jeu de données et le paramètre de confidentialité
         * en paramètre
         */
        MondrianHelper.mondrian(data, k);
        
        System.out.println("Affichage de nos classes d'équivalence");
        /**
         * Affichage des classes d'équivalence
         */
        MondrianHelper.display();
        /**
         * Affichage du nombre de division effectuée
         */
        System.out.println("Nombre divisions effectuées: "+MondrianHelper.getNumberOfDivision());
    }
        
}