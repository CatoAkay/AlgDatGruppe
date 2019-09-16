package Oblig1;////// Løsningsforslag Oblig 1 - 2019 ////////////////////////

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.NoSuchElementException;


public class Oblig1 {
    private Oblig1() {
    }

    ///// Oppgave 1 //////////////////////////////////////
    public static int maks(int[] a) {

        if(a == null) throw new NullPointerException("Tabellen er Null");
        if(a.length < 1)
        {
            throw new java.util.NoSuchElementException("Tabellen er tom!");
        }
        for (int i= 1; i < a.length; i++)
        {
           while(a[i-1] > a[i])
            {
                int temp = a[i];
                a[i] = a[i-1];
                a[i-1] = temp;
            }
        }
        return a[a.length-1];
    }

    public static int ombyttinger(int[] a)
    {
        if(a == null) throw new NullPointerException("Tabellen er Null");
        if(a.length < 1)
        {
            throw new java.util.NoSuchElementException("Tabellen er tom!");
        }

        int ombyttninger = 0;
        for (int i= 1; i < a.length; i++)
        {
            if(a[i-1] > a[i])
            {
                int temp = a[i];
                a[i] = a[i-1];
                a[i-1] = temp;
                ombyttninger++;
            }
        }
        return ombyttninger;
    }


    ///// Oppgave 2 //////////////////////////////////////
    public static int antallUlikeSortert(int[] a)
    {
        int ulike = 1;
        if(a.length == 0)
        {
            return 0;
        }
        for(int i = 1; i < a.length; i++)
        {
            if(a[i-1]>a[i])
            {
                throw new IllegalStateException("Tabellen er ikke sortert!");
            }
            if(a[i-1] != a[i])
            {
                ulike++;
            }
        }
        return ulike;
    }



    ///// Oppgave 3 //////////////////////////////////////
    public static int antallUlikeUsortert(int[] a) {

        if(a.length == 0)
        {
            return 0;
        }

        int duplikat = 1;
        int lengde = a.length;

        for(int i = 0; i < a.length; i++)
        {
            for( int l = i + 1; l < a.length; l++)
            {
                if(a[l] == a[i])
                {
                    duplikat++;
                }
            }
        }

        return lengde-duplikat;
    }
    public static void main (String[]args)
    {
        //Oppgaver fra 1.1.2
        int[] tabell = new int[]{1,2,4,5,3,3};

        System.out.println(antallUlikeUsortert(tabell));

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
