package Oblig2;

import java.util.Iterator;

public class main {

    public static void main(String[] args) {
        DobbeltLenketListe liste = new DobbeltLenketListe();
        liste.leggInn("Cato");
        liste.leggInn("Fredrik");
        liste.leggInn("Bjørnar");
        Iterator<DobbeltLenketListe> iterator = liste.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
