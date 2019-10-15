/**
 * Medlemmer:
 * Jakkris Thongma - s197101
 * Bao Duy Nguyen - s169969
 * Cato Hilmi Akay - s326326
 * Amirhan Hadzjaev - s326322
 */
////////////////// class DobbeltLenketListe //////////////////////////////
package Oblig2;

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
    private Node<T> hode;
    private Node<T> hale;
    private int antall;
    private int endringer;


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

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall()==0;
    }

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
    public boolean fjern(T verdi)
    {
        if(tom())
        {
            return false;
        }

        Node<T> current = hode; //Hjelpepekere

        while(current != null)
        {
            if(current.verdi.equals(verdi)) break; //Verdien funnet
            current = current.neste;
        }

        if(current == null)return false; //Fant ikke verdi

        if(current == hode && antall != 1)
        {
            Node<T> next;
            next = current.neste;
            hode = next;
            hode.forrige = null;
            current.neste = null;
            antall--;
            endringer++;
            return true;
        }
        else if(current == hode) // Siste node slettes
        {
            hode = hale = null;
            antall--;
            endringer++;
            return true;
        }
        else if (current == hale)
        {
            Node<T> prev;
            prev = current.forrige;
            hale = prev;
            prev.neste = null;

            current.forrige = null;
            antall --;
            endringer++;
            return true;
        }
        else
        {
            Node<T> prev;
            prev = current.forrige;
            Node<T> next;
            next = current.neste;
            prev.neste = next;
            next.forrige = prev;

            antall--;
            endringer++;
            return true;
        }
    }

    @Override
    public T fjern(int indeks){
        indeksKontroll(indeks, false);
        T temp;

        if (indeks == 0) {
            temp = hode.verdi;
            if (antall == 1) {
                hode = null;
                hale = null;
            }else {
                hode = hode.neste;
                hode.forrige = null;
            }
        }else if(indeks == antall-1){
            temp = hale.verdi;
            hale = hale.forrige;
            hale.neste = null;
        } else{
            Node<T> current = finnNode(indeks);
            Node<T> prev = current.forrige;
            Node<T> next = current.neste;
            temp = current.verdi;

            current.verdi = null;
            current.forrige = null;
            current.neste = null;

            prev.neste = next;
            next.forrige = prev;
        }
        endringer++;
        antall--;
        return temp;
    }

    @Override
    public void nullstill()
    {
        /* Manuelt:
        Node<T> current = hode, next = null;
        while(current != null)
        {
            next = current.neste;
            current.forrige = null;
            current.neste = null;
            current.verdi = null;
            current = next;
        }
        hode = hale = null;
        antall = 0;
        */
        while(antall!= 0)
        {
            fjern(0);
        }
    }

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
    public Iterator<T> iterator()
    {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;
            fjernOK = false;
            iteratorendringer = endringer;
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next()
        {
            if(iteratorendringer != endringer) throw new ConcurrentModificationException("Iterator endringer ikke lik endringer");
            if(!hasNext())
            {
                throw new NoSuchElementException("Ingen flere elementer");
            }

            fjernOK = true;
            T denneVerdi = denne.verdi;
            denne = denne.neste;
            return denneVerdi;
        }

        @Override
        public void remove()
        {
            if (endringer != iteratorendringer)
            {
                throw new ConcurrentModificationException("Listen er endret!");
            }
            if(!fjernOK) throw new IllegalStateException();


            fjernOK = false;
            Node<T> prev = null; //Hjelpepekere

            if (hode.neste == denne)//
            {

                hode = hode.neste;
                if(denne != null)
                {
                    prev = denne.forrige;
                    prev.neste = null;
                    denne.forrige = null;

                    antall--;
                    endringer++;
                    iteratorendringer++;
                }
                else
                {
                    hale = null;
                    antall--;
                    endringer++;
                    iteratorendringer++;
                }
            }
            else //Vi har flere noder
            {
                if(denne == null)
                {
                    prev = hale.forrige;
                    prev.neste = null;
                    hale.forrige = null;
                    hale.verdi = null;
                    hale = prev;
                    antall--;
                    endringer++;
                    iteratorendringer++;
                }
                else if(denne == hale)
                {
                    prev = hale.forrige.forrige;
                    prev.neste = hale;
                    hale.forrige = prev;
                    antall--;
                    endringer++;
                    iteratorendringer++;
                }
                else
                {
                    prev = denne.forrige.forrige;
                    prev.neste = denne;
                    denne.forrige = prev;
                    antall--;
                    endringer++;
                    iteratorendringer++;
                }
            }
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        boolean sorted = false;

        while(!sorted)
        {
            sorted = true;

            for(int i = 0; i<liste.antall()-1; i++)
            {
                T hent1 = liste.hent(i);
                T hent2 = liste.hent(i+1);
                if(c.compare(hent1, hent2) > 0) //
                {
                    T temp = hent1;
                    liste.fjern(i);
                    liste.leggInn(i+1, temp);
                    sorted = false;
                }
            }
        }
    }

} // class DobbeltLenketListe


