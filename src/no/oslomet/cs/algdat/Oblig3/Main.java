package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator. naturalOrder ());
        char [] verdier = "IATBHJCRSOFELKGDMPQN" .toCharArray();
        for ( char c : verdier) tre.leggInn(c);
        System. out .println(tre.høyreGren() + " " + tre.lengstGren());
    }

}
