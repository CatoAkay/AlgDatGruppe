package Oblig1;////// Løsningsforslag Oblig 1 - 2019 ////////////////////////

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.NoSuchElementException;


public class Oblig1 {
    private Oblig1() {
    }

    ///// Oppgave 1 //////////////////////////////////////
    public static int maks(int[] a)
    {
        //Diverse feilsjekk
        if( a == null) throw new NullPointerException("Parametertabellen a er null!");
        if(a.length == 0) throw new NoSuchElementException("Ingen verdier i a!");

        for(int i = 1; i<a.length; i++)
        {
            if(a[i-1] > a[i])
            {
                int temp = a[i];
                a[i] = a[i-1];
                a[i-1] =  temp;
            }

        }
        return a[a.length-1];
    }

    public static int ombyttinger(int[] a)
    {
        //Diverse feilsjekk
        if( a == null) throw new NullPointerException("Parametertabellen a er null!");
        if(a.length == 0) throw new NoSuchElementException("Ingen verdier i a!");

        int ombyttinger = 0;
        for(int i = 1; i<a.length; i++)
        {
            if(a[i-1] > a[i])
            {
                int temp = a[i];
                a[i] = a[i-1];
                a[i-1] =  temp;
                ombyttinger++;
            }

        }
        return ombyttinger;
    }

    ///// Oppgave 2 //////////////////////////////////////
    public static int antallUlikeSortert(int[] a)
    {
        int antallUlikeVerdier = 0;

        if(a.length == 0) return antallUlikeVerdier;
        else
        {
            antallUlikeVerdier++;
        }

        for(int i = 1; i<a.length; i++)
            {
                if(a[i-1] > a[i]) throw new IllegalStateException("Tabellen er ikke sortert stigende");

                if(a[i-1] != a[i]) {antallUlikeVerdier++;}
            }

        return antallUlikeVerdier;
    }



    ///// Oppgave 3 //////////////////////////////////////
    public static int antallUlikeUsortert(int[] a)
    {
        int antallUlikeVerdier = 0;
        if(a.length == 0) return antallUlikeVerdier;
        else
        {
            antallUlikeVerdier++;
        }

        int like = 0;
        int start = 0;

        for (int i = start; i<a.length; i++)
        {
            if(a[start] == a[i+1])
            {

            }

        }

    }

    public static void main(String[] args) {
        int a[] = {5,3,7,4,3,5,7};
    }

    ///// Oppgave 4 //////////////////////////////////////
    public static void delsortering(int[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 6 //////////////////////////////////////
    public static void rotasjon(char[] a, int k) {
        throw new NotImplementedException();
    }

    ///// Oppgave 7 //////////////////////////////////////
    /// 7a)
    public static String flett(String s, String t) {
        throw new NotImplementedException();
    }

    /// 7b)
    public static String flett(String... s) {
        throw new NotImplementedException();
    }

    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a) {
        throw new NotImplementedException();
    }


    ///// Oppgave 9 //////////////////////////////////////
    public static int[] tredjeMin(int[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 10 //////////////////////////////////////
    public static int bokstavNr(char bokstav) {
        throw new NotImplementedException();
    }

    public static boolean inneholdt(String a, String b) {
        throw new NotImplementedException();
    }

}  // Oblig1.Oblig1
