package Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T>
    {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen


    public DobbeltLenketListe()
    {
        hode= hale = null;
        antall = 0;
    }

    public static void main(String[] args)
    {
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + " " + liste.omvendtString());
        for (int i = 1; i <= 3; i++)
        {
            liste.leggInn(i);
            System.out.println(liste.toString() + " " + liste.omvendtString());
        }
    }

    //Oppgave 1
    public DobbeltLenketListe(T[] a)
    {
        this();  // alle variabelene er nullet
        Objects.requireNonNull(a, "Ikke tillatt med null-verdier!");
        // Finner den første i a som ikke er null
        int i = 0; for (; i < a.length && a[i] == null; i++);

        if (i < a.length)
        {
            Node<T> current = hode = new Node<>(a[i],null, null);  // den første noden
            antall = 1;                                 // vi har minst en node

            for (i++; i < a.length; i++)
            {
                if (a[i] != null)
                {
                    current = current.neste = new Node<>(a[i], current,null);   // en ny node
                    antall++;
                }
            }
            hale = current;
        }
    }

    public Liste<T> subliste(int fra, int til){
        throw new NotImplementedException();
    }

    //Oppgave 1
    @Override
    public int antall()
    {
        return antall;
    }
    //Oppgave 1
    @Override
    public boolean tom()
    {
        return antall()==0;
    }

    //Oppgave 2
    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi);

        if(tom())
        {
            hode = hale = new Node<>(verdi, null, null);
        }
        else
        {
            hale = hale.neste = new Node<>(verdi,hale, null);
        }
        endringer++;
        antall++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T hent(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new NotImplementedException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T fjern(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public void nullstill() {
        throw new NotImplementedException();
    }

    //Oppgave 2
    @Override
    public String toString()
    {
        if (antall == 0) {
            return "[]";
        }

        StringBuilder s = new StringBuilder();
        s.append("[");

        if(!tom()){
            Node<T> current = hode;
            s.append(current.verdi);

            current = current.neste;

            while(current != null){
                s.append(",").append(" ").append(current.verdi);
                current = current.neste;
            }
        }

        s.append("]");

        return s.toString();
    }

    //Oppgave 2
    public String omvendtString()
    {
        if (antall == 0) {
            return "[]";
        }

        StringBuilder s = new StringBuilder();
        s.append("[");


        if(!tom())
        {
            //Første node, starter fra halen. [Kari
            Node<T> current = hale;
            s.append(current.verdi);

            //Loopen
            current = current.forrige;

            //Første kjøring [Kari, Lars
            //Videre må vi sette at loopen går videre i while
            while(current != null)
            {
                //
                s.append(",").append(" ").append(current.verdi);
                //Loop går videre
                current = current.forrige;
            }
            s.append("]");
            return s.toString();
        }


        return s.toString();
    }


    @Override
    public Iterator<T> iterator() {
        throw new NotImplementedException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            throw new NotImplementedException();
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext(){
            throw new NotImplementedException();
        }

        @Override
        public T next(){
            throw new NotImplementedException();
        }

        @Override
        public void remove(){
            throw new NotImplementedException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }

} // class DobbeltLenketListe


