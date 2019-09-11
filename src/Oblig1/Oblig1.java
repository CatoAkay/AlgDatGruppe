package Oblig1;////// Løsningsforslag Oblig 1 - 2019 ////////////////////////

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.NoSuchElementException;
import Hjelpemetoder.Sortering;


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
        throw new NotImplementedException();
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

    public static void main(String[] args)
    {
        int a[] = {6,10,16,11,7,12,3,9,8,5};
        int [] sortert = indekssortering(a);
        System.out.println(Arrays.toString(sortert));

    }


    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a)
    {

        int [] indekssortert = new int[a.length];
        if(a.length == 0) return indekssortert;

        int [] b = a.clone();

        Sortering.quickSort(b,0,b.length-1);

        int indeksretur = 0;

        for(int i = 0; i<b.length; i++)
        {
            for(int j = 0; j<a.length; j++)
            {
                if (b[i] == a[j])
                {
                    indekssortert[indeksretur] = j;
                    indeksretur++;
                }
            }
        }

        return indekssortert;

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
