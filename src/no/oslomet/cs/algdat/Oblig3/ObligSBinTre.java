package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T> {
  private static final class Node<T>   // en indre nodeklasse
  {
    private T verdi;                   // nodens verdi
    private Node<T> venstre, høyre;    // venstre og høyre barn
    private Node<T> forelder;          // forelder

    // konstruktør
    private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
      this.verdi = verdi;
      venstre = v;
      høyre = h;
      this.forelder = forelder;
    }

    private Node(T verdi, Node<T> forelder)  // konstruktør
    {
      this(verdi, null, null, forelder);
    }


    @Override
    public String toString() {
      return "" + verdi;
    }

  } // class Node

  private Node<T> rot;                            // peker til rotnoden
  private int antall;                             // antall noder
  private int endringer;                          // antall endringer

  private final Comparator<? super T> comp;       // komparator

  public ObligSBinTre(Comparator<? super T> c)    // konstruktør
  {
    rot = null;
    antall = 0;
    comp = c;
  }

  @Override
  public boolean leggInn(T verdi) {
    Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

    Node<T> p = rot, q = null;               // p starter i roten
    int cmp = 0;                             // hjelpevariabel

    while (p != null)       // fortsetter til p er ute av treet
    {
      q = p;                                 // q er forelder til p
      cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
      p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
    }

    // p er nå null, dvs. ute av treet, q er den siste vi passerte

    // oppretter en ny node

    p = new Node<>(verdi, q);
    if (q == null) {
      rot = p;// p blir rotnode
    } else if (cmp < 0) {
      q.venstre = p;         // venstre barn til q

    } else {
      q.høyre = p;// høyre barn til q
    }

    antall++;                                // én verdi mer i treet
    return true;                             // vellykket innlegging
  }

  @Override
  public boolean inneholder(T verdi) {
    if (verdi == null) return false;

    Node<T> p = rot;

    while (p != null) {
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0) p = p.venstre;
      else if (cmp > 0) p = p.høyre;
      else return true;
    }

    return false;
  }



  public int antall(T verdi) {
    Node<T> p = rot;
    int forekomst = 0;

    while (p != null) {
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0) p = p.venstre;
      else {
        if (cmp == 0) forekomst++;
        p = p.høyre;
      }
    }
    return forekomst;
  }


  @Override
  public boolean fjern(T verdi) {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }

  public int fjernAlle(T verdi) {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }

  @Override
  public int antall() {
    return antall;
  }


  @Override
  public boolean tom() {
    return antall == 0;
  }

  @Override
  public void nullstill() {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }


  private static <T> Node<T> nesteInorden(Node<T> p) {
    Objects.requireNonNull(p);
    if (p.høyre != null) {
      p = p.høyre;
      while (p.venstre != null) {
        p = p.venstre;
      }
    } else {

      while (p.forelder != null && p == p.forelder.høyre) {
        p = p.forelder;
      }

      p = p.forelder;
    }

    return p;
  }

    /*
    Case 1: Node has right subtree
    Go deep to leftmost node in right subtree
    Find min in right subtree
    case 2: no right subtree
    if child left: go to parent
    if child right: go to parent.parent


  */

  /*
  Inorder:
  1: Visit left-subtree
  2: Visit root
  3: Visit right subtree
   */

  @Override
  public String toString() {
    if (antall == 0) {
      return "[]";
    }
    Node p = rot;

    while (p.venstre != null) {
      p = p.venstre;
    }

    StringBuilder s = new StringBuilder("[").append(p.verdi);
    p = nesteInorden(p);

    for (int i = 0; i < antall - 1; i++) {
      s.append(", ").append(p.verdi);
      p = nesteInorden(p);
    }
    s.append("]");
    return s.toString();
  }

  public static void main(String[] args)
  {
    int[] a = {4,2,1,3,5};
    ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
    for (int verdi : a) tre.leggInn(verdi);
    System.out.println(tre.omvendtString()); // [10, 9, 8, 7, 7, 6, 4, 4, 4, 2, 1]
  }



  public String omvendtString()
  {
    if(tom()) return "[]";
    Stakk<Node<T>> stakk = new TabellStakk<>();
    Node<T> p = rot;   // starter i roten og går til høyre
    for ( ; p.høyre != null; p = p.høyre) stakk.leggInn(p);
    StringJoiner s = new StringJoiner(", ", "[","]");

    while (true)
    {
      s.add(p.verdi.toString());
      if (p.venstre != null)          // til høyre i venstre subtre
      {
        for (p = p.venstre; p.høyre != null; p = p.høyre)
        {
          stakk.leggInn(p);
        }
      }
      else if (!stakk.tom())
      {
        p = stakk.taUt();// p.høyre == null, henter fra stakken
      }
      else break;          // stakken er tom - vi er ferdig
    } // while
    return s.toString();
  }

  /*
  Traversere treet i omvendt inorden iterativt.
  Skal bruke en hjelpestakk
  skal ikke bruke rekursjon

   */
  
  public String høyreGren()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String lengstGren()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String[] grener()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String bladnodeverdier()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  public String postString()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }


  @Override
  public Iterator<T> iterator()
  {
    return new BladnodeIterator();
  }
  
  private class BladnodeIterator implements Iterator<T>
  {
    private Node<T> p = rot, q = null;
    private boolean removeOK = false;
    private int iteratorendringer = endringer;
    
    private BladnodeIterator()  // konstruktør
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public boolean hasNext()
    {
      return p != null;  // Denne skal ikke endres!
    }
    
    @Override
    public T next()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    
    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

  } // BladnodeIterator

} // ObligSBinTre