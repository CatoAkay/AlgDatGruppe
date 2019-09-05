package Oblig1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Denne klassen kan du bruke til hjelp under utvikling av din oblig.
 * Lag små og enkle test-eksempler for å teste at metoden din fungerer som ønsket.
 */
class Oblig1UnitTest {

    @org.junit.jupiter.api.Test
    void maks() {
        int[] a = {10,3,2,8};
        assertEquals(10, Oblig1.maks(a));
        System.out.println(Arrays.toString(a));
    }

    @org.junit.jupiter.api.Test
    void ombyttinger() {
        int[] a = {1,2,3};
        int[] b = {1,3,2};
        int[] c = {2,1,3};
        int[] d = {2,3,1};
        int[] e = {3,1,2};
        int[] f = {3,2,1};

        System.out.println(Oblig1.ombyttinger(a));
        System.out.println(Oblig1.ombyttinger(b));
        System.out.println(Oblig1.ombyttinger(c));
        System.out.println(Oblig1.ombyttinger(d));
        System.out.println(Oblig1.ombyttinger(e));
        System.out.println(Oblig1.ombyttinger(f));

    }

    @org.junit.jupiter.api.Test
    void antallUlikeSortert() {
        int[] a = {};
        System.out.println(Oblig1.antallUlikeSortert(a));

    }

    @org.junit.jupiter.api.Test
    void antallUlikeUsortert() {
        assertEquals(true, false, "Implementer antallUlikeUsortert og denne testen");
    }

    @org.junit.jupiter.api.Test
    void delsortering() {
        assertEquals(true, false, "Implementer delsortering og denne testen");
    }

    @org.junit.jupiter.api.Test
    void rotasjon() {
        assertEquals(true, false, "Implementer rotasjon og denne testen");
    }

    @org.junit.jupiter.api.Test
    void flett() {
        assertEquals(true, false, "Implementer flett og denne testen");
    }

    @org.junit.jupiter.api.Test
    void indekssortering() {
        assertEquals(true, false, "Implementer indekssortering og denne testen");
    }

    @org.junit.jupiter.api.Test
    void tredjeMin() {
        assertEquals(true, false, "Implementer tredjeMin og denne testen");
    }

    @org.junit.jupiter.api.Test
    void bokstavNr() {
        assertEquals(true, false, "Implementer bokstavNr og denne testen");
    }

    @org.junit.jupiter.api.Test
    void inneholdt() {
        assertEquals(true, false, "Implementer inneholdt og denne testen");
    }
}