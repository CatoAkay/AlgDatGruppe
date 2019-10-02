package Oblig1;////// Løsningsforslag Oblig 1 - 2019 ////////////////////////
import Div.quicksort;
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

        int duplikat = 0;

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

        if(a.length - duplikat < 0)
        {
            return (a.length - duplikat) * -1;
        }

        return a.length - duplikat;

    }

    ///// Oppgave 4 //////////////////////////////////////
    public static void delsortering(int[] a)
    {
        int temp = 0;
        int odd = 0;

        for (int i = 0; i < a.length; i++)
        {
            if ((Math.abs(a[i]) % 2) != 0)
            {
                temp = a[odd];
                a[odd++] = a[i];
                a[i] = temp;
            }
        }
        quicksort.quickSort(a,0,odd - 1);
        quicksort.quickSort(a,odd,a.length-1);
    }

    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a)
    {
        for (int i = 0; i < 1; i++)
        {
            for (int j = a.length - 1; j > 0; j--) {
                char temp = a[j];
                a[j] = a[j - 1];
                a[j-1] = temp;
        }
        }
    }
    
    ///// Oppgave 6 //////////////////////////////////////
    public static void rotasjon(char[] a, int k)
    {
        throw new NotImplementedException();
    }



    ///// Oppgave 7 //////////////////////////////////////
    /// 7a)
    public static String flett(String s, String t) {
        StringBuilder flettet = new StringBuilder();
        int lengde;

        //En bedre måte å gjøre det på
        //int lengde = (s.length() <= t.length()) ? t.length() : s.length();

        if (s.length() < t.length()){
            lengde = t.length();
        }else if (t.length() < s.length()){
            lengde = s.length();
        }else {
            lengde = s.length();
        }

        for (int i = 0; i < lengde; i++){
            if (s.length() > i){
                flettet.append(s.charAt(i));
            }
            if (t.length() > i){
                flettet.append(t.charAt(i));
            }
        }
        return flettet.toString();
    }

    /// 7b)
    public static String flett(String... s) {
        int lengste = 0;
        StringBuilder flettet = new StringBuilder();
        for (String strenger : s) {
            if (strenger.length() > lengste){
                lengste = strenger.length();
            }
        }
        for (int i = 0; i < lengste; i++){
            for (String strenger2 : s) {
                if (strenger2.length() > i){
                    flettet.append(strenger2,i,i+1);
                }
            }
        }
        return flettet.toString();
    }

    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a) {

        int[] indekssortert = new int[a.length];

        if (a.length == 0) return indekssortert;

        int[] b = a.clone();

        Hjelpemetoder.StorteringsMetoder.quickSort(b, 0, b.length - 1);

        int indeksretur = 0;

        for (int i = 0; i < b.length; i++) {
            if (i < b.length - 1)
            {
                if (b[i] != b[i + 1])
                {
                    for (int j = 0; j < a.length; j++)
                    {
                        if (b[i] == a[j])
                        {
                            indekssortert[indeksretur] = j;
                            indeksretur++;
                        }
                    }
                }

            }
            else
            {
                for (int j = 0; j < a.length; j++)
                {
                    if (b[i] == a[j])
                    {
                        indekssortert[indeksretur] = j;
                        indeksretur++;
                    }
                }
            }

        }
        return indekssortert;
    }

    ///// Oppgave 9 //////////////////////////////////////
    public static int[] tredjeMin(int[] a) {
        if (a.length < 3){
            throw new NoSuchElementException("Tabellen må ha 3 eller flere elementer");
        }
        int minst = Integer.MAX_VALUE;
        int nestMinst = Integer.MAX_VALUE;
        int tredjeMinst = Integer.MAX_VALUE;

        int minstIndex = 0;
        int nestMinstIndex = 0;
        int tredjeMinstIndex = 0;

        for (int i = 0; i < a.length; i++){
            if (a[i] < minst){
                tredjeMinstIndex = nestMinstIndex;
                tredjeMinst = nestMinst;

                nestMinstIndex = minstIndex;
                nestMinst = minst;

                minstIndex = i;
                minst = a[i];
            }
            else if (a[i] < nestMinst){
                tredjeMinstIndex = nestMinstIndex;
                tredjeMinst = nestMinst;

                nestMinstIndex = i;
                nestMinst = a[i];
            }
            else if (a[i] < tredjeMinst){
                tredjeMinstIndex = i;
                tredjeMinst = a[i];
            }
        }
        return new int[]{minstIndex, nestMinstIndex, tredjeMinstIndex};
    }
    
    ///// Oppgave 10 //////////////////////////////////////
    public static boolean inneholdt(String a, String b) {
        if (a.length() > b.length()) {
            return false;
        }

        char[] aTab = a.toCharArray();
        char[] bTab = b.toCharArray();
        Arrays.sort(aTab);

        if(aTab.length == 0 && bTab.length == 0) {return true;}

        System.out.println(aTab);
        int teller = 1;
        int inneholder = 0;
        boolean ok = false;
        boolean ok2 = false;

        for (int i = 0; i < a.length(); i++) // Loop gjennom A for å finne ut om vi finner alle bokstavene
        {
            if (i != aTab.length - 1)
            {
                if (aTab[i] != aTab[i + 1]) {
                    for (int j = 0; j < bTab.length; j++)
                    {
                        if (aTab[i] == bTab[j])
                        {
                            inneholder++;
                            if (inneholder == teller)
                            {
                                inneholder = 0;
                                teller = 1;
                                ok = true;
                                break;
                            }
                        }
                    }
                } else {
                    teller++;
                }
            } else {
                for (int j = 0; j < bTab.length; j++)
                {
                    if (aTab[i] == bTab[j])
                    {
                        inneholder++;
                        if (inneholder == teller)
                        {
                            ok2 = true;
                        }
                    }
                }
            }
        }
        if(ok && ok2)
        {
            return true;
        }
        else return false;
    }

}  // Oblig1.Oblig1
