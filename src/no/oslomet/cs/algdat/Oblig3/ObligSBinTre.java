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
    private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
    {
      this.verdi = verdi;
      venstre = v; høyre = h;
      this.forelder = forelder;
    }

    private Node(T verdi, Node<T> forelder)  // konstruktør
    {
      this(verdi, null, null, forelder);
    }

    @Override
    public String toString(){ return "" + verdi;}

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
  public boolean leggInn(T verdi)
  {
    Objects.requireNonNull(verdi, "Ulovelig med nullverdier");

    Node<T> p = rot;
    Node<T> q = null;
    int cmp = 0;

    while (p != null){
      q = p;
      cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0){
        p = p.venstre;
      } else{
        p = p.høyre;
      }
    }

    p = new Node<>(verdi, q);
    if (q == null){
      rot = p;
    }else if (cmp < 0){
      q.venstre = p;
    }else {
      q.høyre = p;
    }

    antall++;
    return true;

  }
  
  @Override
  public boolean inneholder(T verdi)
  {
    if (verdi == null) return false;

    Node<T> p = rot;

    while (p != null)
    {
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0) p = p.venstre;
      else if (cmp > 0) p = p.høyre;
      else return true;
    }

    return false;
  }
  
  @Override
  public boolean fjern(T verdi)
  {
    if (verdi == null){
      return false;
    }

    Node<T> p = rot;
    Node<T> q = null;

    while (p != null){
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0){
        q = p;
        p = p.venstre;
      }else if (cmp > 0){
        q = p;
        p = p.høyre;
      }else {
        break;
      }
    }
    if (p == null){
      return false;
    }

    if (p.venstre == null || p.høyre == null){
      Node<T> b;
      if (p.venstre != null){
        b = p.venstre;
      }else {
        b = p.høyre;
      }

      if (p == rot){
        rot = b;
      }else if(p == q.venstre){
        if (b != null){
          q.venstre = b;
          b.forelder = q;
        }else {
          q.venstre = null;
        }
      }else {
        if (b != null){
          q.høyre = b;
          b.forelder = q;
        }else {
          q.høyre = null;
        }
      }
    }else {
       Node<T> s = p;
       Node<T> r = p.høyre;
       while (r.venstre != null){
         s = r;
         r = r.venstre;
       }

       p.verdi = r.verdi;

       if (s != p){
         if (r.høyre != null){
           s.venstre = r.høyre;
         }else {
           s.venstre = null;
           r.forelder = null;
           r = null;
         }
       }else {
         r = r.høyre;
         s.høyre = r;
         r.forelder = s;
       }
    }
    antall--;
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
  public int antall()
  {
    return antall;
  }
  
  public int antall(T verdi) {
    Node<T> p = rot;
    int forekomst = 0;

    while (p != null){
      int cmp = comp.compare(verdi, p.verdi);
      if (cmp < 0){
        p = p.venstre;
      }else {
        if (cmp == 0){
          forekomst++;
        }
        p = p.høyre;
      }
    }
    return forekomst;
  }
  
  @Override
  public boolean tom()
  {
    return antall == 0;
  }
  
  @Override
  public void nullstill()
  {
    Node<T> p = rot;
    if (tom()){
      return;
    }
    while (p.venstre != null){
      p = p.venstre;
    }
    while (antall != 0){
      Node<T> neste = nesteInorden(p);
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
    }else {
      while (p.forelder != null && p == p.forelder.høyre){
        p = p.forelder;
      }
      p = p.forelder;
    }
    return p;
  }
  
  @Override
  public String toString()
  {
    if (antall == 0){
      return "[]";
    }
    Node p = rot;

    while (p.venstre != null){
      p = p.venstre;
    }

    StringBuilder s = new StringBuilder("[").append(p.verdi);
    p = nesteInorden(p);

    for (int i = 0; i < antall-1; i++){
      s.append(", ").append(p.verdi);
      p = nesteInorden(p);
    }
    s.append("]");
    return s.toString();
  }
  
  public String omvendtString()
  {
    if (tom()){
      return "[]";
    }
    Stakk<Node<T>> stakk = new TabellStakk<>();
    Node<T> p = rot;
    for (; p.høyre != null; p = p.høyre){
      stakk.leggInn(p);
    }
    StringJoiner s = new StringJoiner(", ", "[","]");

    while (true){
      s.add(p.verdi.toString());
      if (p.venstre != null){
        for (p = p.venstre; p.høyre != null; p = p.høyre){
          stakk.leggInn(p);
        }
      }else if (!stakk.tom()){
        p = stakk.taUt();
      }
      else break;
    }
    return s.toString();
  }
  
  public String høyreGren() {
    Deque<Node> deque = new ArrayDeque<>();
    Node p = rot;
    while (p != null || !deque.isEmpty()) {
      while (p !=  null) {
        deque.push(p);
        p = p.høyre;
      }
      p = deque.pop();
      if (p.venstre == null && p.høyre == null){
        break;
      }
      p = p.venstre;

    }

    Deque<Node> fStack = new ArrayDeque<>();

    while (p != null){
      fStack.push(p);
      p = p.forelder;
    }

    StringJoiner stringJoiner = new StringJoiner(", ", "[","]");
    while (!fStack.isEmpty()){
      p = fStack.pop();
      stringJoiner.add(p.verdi.toString());
    }
    return stringJoiner.toString();
  }
  
  public String lengstGren()
  {
    if (antall == 0){
      return "[]";
    }
    String [] stringArray = grener();
    String lengsteString = stringArray[stringArray.length-1];
    int lengstLengde = 0;
    if (stringArray.length == 1){
      return lengsteString;
    }else {
      for (String teller : lengsteString.split(",")){
        lengstLengde++;
      }
    }

    for (int i = stringArray.length-2; i >= 0; i--){
      String s = stringArray[i];
      int sLengde = 0;
      for (String tell : s.split(",")){
        sLengde++;
      }
      if (sLengde >= lengstLengde){
        lengstLengde = sLengde;
        lengsteString = s;
      }
    }
    return lengsteString;
  }
  
  public String[] grener()
  {
    Deque<Node> deque = new ArrayDeque<>();
    Deque<Node> bladDeque = new ArrayDeque<>();
    Deque<Node> bladDeque2 = new ArrayDeque<>();
    Node p = rot;
    int i = 0;
    int j = 0;

    while (p != null || !deque.isEmpty()){
      while (p !=  null){
        if (p.venstre == null && p.høyre == null){
          bladDeque.push(p);
          i++;
        }
        deque.push(p);
        p = p.venstre;
      }
      p = deque.pop();
      p = p.høyre;
    }

    String[] stringArray = new String[i];

    while (!bladDeque.isEmpty()){
      p = bladDeque.pop();
      while (p != null){
        bladDeque2.push(p);
        p = p.forelder;
      }

      StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
      while (!bladDeque2.isEmpty()){
        p = bladDeque2.pop();
        stringJoiner.add(p.verdi.toString());
      }


      stringArray[i-1] = stringJoiner.toString();
      i--;
    }
    return stringArray;
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
