package Oblig1;////// Løsningsforslag Oblig 1 - 2019 ////////////////////////

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.NoSuchElementException;


public class Oblig1 {
    private Oblig1() {
    }

    ///// Oppgave 1 //////////////////////////////////////

    /**
     * Det blir flest ombyttinger hvis tabellen er i synkende rekkefølge, da vil det alltid være ombyttinger for hver iterasjon
     * Det er færrest/ingen hvis tabellen er i stigende rekkefølge
     *
     */
    public static int maks(int[] a) {
        if(a.length <= 0)
            throw new NoSuchElementException("Tabellen er tom");
        for(int i = 1; i < a.length; i++) {
            if(a[i-1] > a[i]) {
                int temp = a[i];
                a[i] = a[i-1];
                a[i-1] = temp;
            }
        }
        return a[a.length - 1];
    }

    public static int ombyttinger(int[] a) {
        int ombyttinger = 0;
        if(a.length <= 0)
            throw new NoSuchElementException("Tabellen er tom");
        for(int i = 1; i < a.length; i++) {
            if(a[i-1] > a[i]) {
                int temp = a[i];
                a[i] = a[i-1];
                a[i-1] = temp;
                ombyttinger++;
            }
        }
        return ombyttinger;
    }

    ///// Oppgave 2 //////////////////////////////////////
    public static int antallUlikeSortert(int[] a) {
        if(a.length == 0)
            return 0;


        int antall = 1;
        for(int i = 1; i < a.length; i++)
        {
            if(a[i - 1] > a[i])
                throw new IllegalStateException("Tabellen er ikke sortert");

            if(a[i - 1] != a[i]) {
                antall++;
            }
        }
        return antall;
    }


    ///// Oppgave 3 //////////////////////////////////////
    public static int antallUlikeUsortert(int[] a) {
        if(a.length == 0)
            return 0;

        int antLike = 0;

        for(int i = 0; i < a.length; i++)
        {
            for(int j = i + 1; j < a.length; j++)
            {
                if(a[j] == a[i])
                    antLike++;
            }
        }

        if(a.length - antLike < 0)
            return -1 * (a.length - antLike);

        return a.length - antLike;

    }

    ///// Oppgave 4 //////////////////////////////////////
    public static void delsortering(int[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a) {

        if(a.length <= 0)
            return;

        char tempLast = a[a.length - 1];

        for(int i = a.length - 1; i > 0; i--) {
            a[i] = a[i - 1];
        }
        a[0] = tempLast;
    }

    ///// Oppgave 6 //////////////////////////////////////
    public static void rotasjon(char[] a, int k) {
        if(a.length <= 0)
            return;

        /*
            vi har bare metode for å rotere til høyre, men å rotere -1, dvs
            å rotere til venstre en gang er som å rotere til høyre til enden plus 1.
            -1 = 9 rotasjoner slik at den gjør en ekstra runde og faller på riktig plass
         */
        if(k < 0)
        {
            k = -k % a.length;
            k = a.length - k;
        }

        int n = a.length;
        int steg = k % n;
        reverser(a, 0, n-1);
        reverser(a, 0, steg-1);
        reverser(a, steg, n-1);
    }

    // algoritme for å rottere til høyre
    public static void reverser(char[] c, int v, int h){
        char[] temp = c.clone();
        for (int i = v; i <= h; i++){
            c[i] = temp[h - i + v];
        }
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
