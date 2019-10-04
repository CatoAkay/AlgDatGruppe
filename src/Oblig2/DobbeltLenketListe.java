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
    private static final class Node<T> {
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
        hode = null;
        hale = null;
        antall = 0;
    }

    public DobbeltLenketListe(T[] a)
    {
        this();

        Objects.requireNonNull(a, "Tabellen a er Null");

        int i = 0; for (; i < a.length && a[i] == null; i++);


        if(i < a.length)
        {
            Node<T> current = hode = new Node<T>(a[i], null, null);
            antall++;

            for (i++; i < a.length; i++)
            {
                if(a[i] != null)
                {
                    current = current.neste = new Node<T>(a[i], current,null);
                    antall++;
                }
            }
            hale = current;

        }

    }


    public Liste<T> subliste(int fra, int til){
        throw new NotImplementedException();
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return (antall == 0);
    }

    @Override
    public boolean leggInn(T verdi) {
        throw new NotImplementedException();
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

    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder();

        string.append('[');

        if (antall != 0)
        {
            Node<T> current = hode;
            string.append(current.verdi);

            current = current.neste;

            while (current != null)
            {
                string.append(',').append(' ').append(current.verdi);
                current = current.neste;
            }

        }
        string.append(']');
        return string.toString();
    }



    public String omvendtString() {

        if(antall == 0)
            return "[]";

        StringBuilder string = new StringBuilder();

        string.append('[');

        if (antall != 0)
        {

            Node<T> current = hale;
            string.append(current.verdi);

            current = current.forrige;

            while (current != null)
            {
                string.append(',').append(' ').append(current.verdi);
                current = current.forrige;
            }

        }
        string.append(']');
        return string.toString();

    }

    public static void main(String[]args)
    {
        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};

        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);

        System.out.println(l1.toString());
        System.out.println(l2.toString());
        System.out.println(l3.toString());

        System.out.println(l3.omvendtString());
        System.out.println(l2.omvendtString());
        System.out.println(l1.omvendtString());

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


