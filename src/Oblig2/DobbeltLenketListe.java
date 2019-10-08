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



public class DobbeltLenketListe<T> implements Liste<T>
{

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

    private void fratilKontroll(int lengde, int fra, int til)
    {
        if(fra < 0)
        {
            throw new IndexOutOfBoundsException("Fra kan ikke være negativ!");
        }

        if(til>lengde)
        {
            throw new IndexOutOfBoundsException("Til kan ikke være større en antall noder");
        }

        if(fra > til)
        {
            throw new IllegalArgumentException("Fra kan ikke være større en Til!");
        }

    }

    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall, fra, til);

        T[] a = (T[]) new  Object[til-fra];
        int indeks = 0;

        for(int i = fra; i < til; i++){
            a[indeks] = hent(i);
            indeks++;
        }

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>(a);
        return liste;
    }



    @Override
    public int antall()
    {
        return antall;
    }

    private Node<T> finnNode(int indeks)
    {
        Node<T> current = hode;

        if (indeks<antall/2)
        {
            for (int i = 0; i < indeks; i++) current = current.neste;
        }
            current = hale;
            for (int i = antall-1; i > indeks; i--) current = current.forrige;

            return current;

    }

    @Override
    public boolean tom()
    {
        return (antall == 0);
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Verdi kan ikke være Null");

        if(antall == 0)
        {
            hode = hale =  new Node<T>(verdi, null, null);
            antall++;

            return true;
        }
        else
        {
            hale = hale.neste =  new Node<T>(verdi, hale, null);
            antall++;
            endringer++;

            return true;
        }
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "Verdi kan ikke være null!");
        indeksKontroll(indeks, true);

        if (indeks == 0)
        {
            hode = new Node<>(verdi, null,hode);

            if (antall == 0)
            {
                hale = hode;

            }
            else
            {
                hode.neste.forrige = hode;
            }
        }
        else if (indeks == antall)
        {
            hale = hale.neste = new Node<>(verdi, hale,null);
        }
        else
        {
            Node<T> prev = finnNode(indeks-1);
            Node<T> neste = finnNode(indeks);
            Node<T> current = new Node<T>(verdi,prev,neste);
            prev.neste = current;
            neste.forrige = current;
        }
        endringer++;
        antall++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {
        if(verdi == null) return -1;

        Node<T> a = hode;

        for(int i = 0; i < antall; i++)
        {
            if (a.verdi.equals(verdi)) return i;
            a = a.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi, "Ny verdi kan ikke være null!");
        indeksKontroll(indeks, false);

        Node<T> currentindeks = finnNode(indeks);
        T gammelVerdi = currentindeks.verdi;

        currentindeks.verdi = nyverdi;
        endringer++;
        return gammelVerdi;

    }

    @Override
    public boolean fjern(T verdi)
    {
        if(antall == 0)
        {
            return false;
        }

        Node<T> current = hode;
        Node<T> next;
        Node<T> prev;

        while(current != null)
        {
            if(current.verdi.equals(verdi)) break;
            current = current.neste;
        }

        if(current == null)return false;

        if(current == hode && antall != 1)
        {
            next = current.neste;
            hode = next;
            hode.forrige = null;
            current.verdi = null;
            current.neste = null;
            antall--;
            endringer++;
            return true;
        }
        else if(current == hode)
        {
            hode = hale = null;
            antall--;
            endringer++;
            return true;
        }
        else if (current == hale)
        {
            prev = current.forrige;
            hale = prev;
            prev.neste = null;
            current.verdi = null;
            current.forrige = null;
            antall --;
            endringer++;
            return true;
        }
        else
        {
            prev = current.forrige;
            next = current.neste;
            prev.neste = next;
            next.forrige = prev;
            current.verdi = null;
            antall--;
            endringer++;
            return true;
        }
    }


    @Override
    public T fjern(int indeks)
    {

        indeksKontroll(indeks, false);

        T current;

        if (indeks == 0)
        {
            current = hode.verdi;

            if (antall == 1)
            {
                hode = null;
                hale = null;
            }
            else
            {
                hode = hode.neste;
                hode.forrige = null;
            }
        }
        else if(indeks == antall-1)
        {
            current = hale.verdi;
            hale = hale.forrige;
            hale.neste = null;
        }
        else
        {
            Node<T> q = finnNode(indeks);
            Node<T> p = q.forrige;
            Node<T> r = q.neste;
            current = q.verdi;

            q.verdi = null;
            q.forrige = null;
            q.neste = null;

            p.neste = r;
            r.forrige = p;

        }

        endringer++;
        antall--;
        return current;
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
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + " " + liste.omvendtString());
        for (int i = 1; i <= 3; i++)
        {
            liste.leggInn(i);
            System.out.println(liste.toString() + " " + liste.omvendtString());
        }


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


