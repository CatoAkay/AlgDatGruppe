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
        hode = hale = null;
        antall = 0;
    }

    // hjelpemetoder
    private Node<T> finnNode(int indeks)
    {

        int startPunkt = antall/2;
        Node<T> current;
        if(indeks >= startPunkt)
        {
            current = hale;
            for(int i = antall-1; i >=indeks; i--)
            {
                if(i != indeks)
                {
                    current = current.forrige;
                }
                else
                {
                    return current;
                }
            }
        }
        else
        {
            current = hode;
            for(int i = 0; i<=indeks; i++)
            {
                if(i != indeks)
                {
                    current = current.neste;
                }
                else
                {
                    return current;
                }
            }
        }
        return current;
    }

    private void fratilKontroll(int fra, int til)
    {
        if(fra<0) throw new IndexOutOfBoundsException("fra parameter er illegalt " + fra);
        if(til>antall) throw new IndexOutOfBoundsException("til parameter er illegalt " + til);
        if(fra>til) throw new IllegalArgumentException("fra > til");
    }

    public static void main(String[] args)
    {
        String[] s1 = {"Jakk", "Petter", "Jakk", "Kjell"};

        DobbeltLenketListe<String> liste = new DobbeltLenketListe<>(s1);
        System.out.println(liste.toString());

        System.out.println(liste.indeksTil(null));

    }

    //Oppgave 1
    public DobbeltLenketListe(T[] a)
    {
        this();
        Objects.requireNonNull(a, "Ikke tillatt med null-verdier!");

        int i = 0; for (; i < a.length && a[i] == null; i++);

        if (i < a.length)
        {
            Node<T> current = hode = new Node<>(a[i],null, null);
            antall = 1;

            for (i++; i < a.length; i++)
            {
                if (a[i] != null)
                {
                    current = current.neste = new Node<>(a[i], current,null);
                    antall++;
                }
            }
            hale = current;
        }
    }

    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(fra,til);

        T[] array =(T[]) new Object[til-fra];
        int indeks = 0;

        for(int i = fra; i<til; i++)
        {
            array[indeks] = hent(i);
            indeks++;
        }

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>(array);
        return liste;
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
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi);
        indeksKontroll(indeks, true);

        if(indeks == 0)
        {
            hode = new Node<>(verdi, null, hode);
            if (antall == 0)
            {
                hale = hode;
            }
            else
            {
                hode.neste.forrige = hode;
            }
        }
        else if(indeks == antall)
        {
            hale = hale.neste = new Node<>(verdi,hale, null);
        }
        else
        {
            Node<T> prevNode = finnNode(indeks-1);
            Node<T> nextNode = finnNode(indeks);
            Node<T> current = new Node<>(verdi, prevNode,nextNode);
            prevNode.neste = current;
            nextNode.forrige = current;
        }

        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi)
    {
        if(indeksTil(verdi) != -1)
        {
            return true;
        }
        else return false;
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
        for(int i = 0; i<antall; i++)
        {
            T hentet = hent(i);
            if(hentet.equals(verdi))
            return i;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi,"Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, false);

        Node<T> temp = finnNode(indeks);
        T gammelverdi = temp.verdi;

        temp.verdi = nyverdi;
        endringer++;
        return gammelverdi;
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
            Node<T> current = hale;
            s.append(current.verdi);

            current = current.forrige;

            while(current != null)
            {

                s.append(",").append(" ").append(current.verdi);
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


