package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T>
{
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

  //hjelpemetoder
  public void getLeafNodes(Node<T> node, Deque stack)
  {
    if(node == null) return;

    //Funnet bladnode
    if(node.venstre == null && node.høyre ==null)
    {
      stack.push(node);
    }

    if(node.venstre != null)
    {
      getLeafNodes(node.venstre, stack);
    }

    if(node.høyre != null)
    {
      getLeafNodes(node.høyre, stack);
    }

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
  public boolean fjern(T verdi)
  {
    if (verdi == null) return false;  // treet har ingen nullverdier

    Node<T> p = rot, q = null;   // q skal være forelder til p

    while (p != null)            // leter etter verdi
    {
      int cmp = comp.compare(verdi,p.verdi);      // sammenligner
      if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
      else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
      else break;    // den søkte verdien ligger i p
    }
    if (p == null) return false;   // finner ikke verdi

    //Funnet eller ikke funnet verdien: Gjort

    //Slett en bladnode eller en node med nøyaktig 1 barn

    if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
    {
      Node<T> b;
      if(p.venstre != null)
      {
        b = p.venstre;
      }
      else
      {
        b = p.høyre;
      }

      if (p == rot)
      {
        rot = b; //Sletter vi rot?
      }
      else if (p == q.venstre)
      {
        if(b!= null)
        {
          q.venstre = b;
          b.forelder = q;
        }
        else
        {
          q.venstre = null;
        }
      }
      else
      {
        if(b!= null)
        {
          q.høyre = b;
          b.forelder = q;
        }
        else
        {
          q.høyre = null;
        }
      }
    }
    else  // Tilfelle 3)
    {
      Node<T> s = p, r = p.høyre;   // finner neste i inorden
      while (r.venstre != null)
      {
        s = r;    // s er forelder til r
        r = r.venstre;
      }

      p.verdi = r.verdi;   // kopierer verdien i r til p

      if (s != p)
      {
        if(r.høyre != null)
        {
          s.venstre = r.høyre;
        }
        else
        {
          s.venstre = null;
          r.forelder = null;
          r = null;
        }
      }
      else
      {
        r = r.høyre;
        s.høyre = r;
        r.forelder = s;
      }
    }
    antall--;   // det er nå én node mindre i treet
    return true;
  }

  public int fjernAlle(T verdi)
  {
    int antallFjernet = 0;
    while (fjern(verdi)){
      antallFjernet++;
    }
    return antallFjernet;
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
  public void nullstill()
  {
    Node <T> p = rot;
    if(tom()) return;
    while(p.venstre != null)
    {
      p = p.venstre;
    }

    while(antall != 0)
    {
      Node <T> neste = nesteInorden(p);
      fjern(p.verdi);
      p = neste;
    }
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



  public String høyreGren()
  {
    Deque<Node> stack = new ArrayDeque<>();
    Node p = rot;
    if(tom()) return "[]";

    //Finn største bladnode
    while (p != null || !stack.isEmpty())
    {

      while (p.høyre != null) {
        stack.push(p);
        p = p.høyre;
      }

      if (p.venstre == null && p.høyre == null){
        break;
      }
      stack.push(p);
      p = p.venstre;
    }
    stack.push(p);
    StringJoiner s = new StringJoiner(", ", "[","]");

    while(!stack.isEmpty())
    {
      p = stack.pollLast();
      s.add(p.verdi.toString());
    }
    return s.toString();
  }
  
  public String lengstGren()
  {
    if (antall == 0)
    {
      return "[]";
    }

    String [] strings = grener();
    String lengsteString = strings[strings.length-1];
    int lengste = 0;

    if (strings.length == 1)
    {
      return lengsteString;
    }
    else
    {
      for(String s : strings)
      {
        String [] antallNoder = s.split(",");
        int lengde = antallNoder.length;
        if(lengde > lengste)
        {
          lengste = lengde;
          lengsteString = s;
        }
      }
    }
    return lengsteString;
  }

  public static void main(String[] args)
  {
    ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());
    char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
    for (char c : verdier) tre.leggInn(c);

    tre.grener();
    String[] s = tre.grener();


    System.out.println(tre.lengstGren());

  }



  public String[] grener()
  {
    Deque<Node<T>> stack = new ArrayDeque();
    //Hent alle bladnoder rekursiv hjelpemetode
    getLeafNodes(rot, stack);
    int j = 0;
    String [] strings = new String[stack.size()];

    while(!stack.isEmpty())
    {
      StringJoiner s = new StringJoiner(", ", "[","]");
      Deque<Node<T>> hjelpestack = new ArrayDeque();
      Node p = stack.pollLast();
      hjelpestack.add(p);

      while(p.forelder != null)
      {
        p = p.forelder;
        hjelpestack.add(p);
      }

      while(!hjelpestack.isEmpty())
      {
        p = hjelpestack.pollLast();
        s.add(p.verdi.toString());
      }
      strings[j] = s.toString();
      j++;
    }
    return strings;
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
