package no.oslomet.cs.algdat.Oblig3;
/**
 * Medlemmer:
 * Jakkris Thongma - s197101
 * Bao Duy Nguyen - s169969
 * Cato Hilmi Akay - s326326
 * Amirhan Hadzjaev - s326322
 */

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
  public void hentBladNode(Node<T> node, Deque stack)
  {
    if(node == null) return;

    //Funnet bladnode
    if(node.venstre == null && node.høyre ==null)
    {
      stack.push(node);
    }

    if(node.venstre != null)
    {
      hentBladNode(node.venstre, stack);
    }

    if(node.høyre != null)
    {
      hentBladNode(node.høyre, stack);
    }

  }

  @Override
  public boolean leggInn(T verdi)
  {
    Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

    Node<T> p = rot, q = null;
    int cmp = 0;

    while (p != null)
    {
      q = p;
      cmp = comp.compare(verdi, p.verdi);
      p = cmp < 0 ? p.venstre : p.høyre;
    }

    p = new Node<>(verdi, q);
    if (q == null) {
      rot = p;//
    } else if (cmp < 0) {
      q.venstre = p;

    } else {
      q.høyre = p;//
    }
    endringer++;
    antall++;
    return true;
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

  public int antall(T verdi)
  {
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
    if (verdi == null) return false;

    Node<T> p = rot, q = null;

    while (p != null)
    {
      int cmp = comp.compare(verdi,p.verdi);
      if (cmp < 0) { q = p; p = p.venstre; }
      else if (cmp > 0) { q = p; p = p.høyre; }
      else break;
    }
    if (p == null) return false;


    if (p.venstre == null || p.høyre == null)
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
        rot = b;
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
    else
    {
      Node<T> s = p, r = p.høyre;
      while (r.venstre != null)
      {
        s = r;
        r = r.venstre;
      }

      p.verdi = r.verdi;

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
    endringer++;
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
    endringer++;
  }

  private static <T> Node<T> nesteInorden(Node<T> p)
  {
    Objects.requireNonNull(p);
    if (p.høyre != null) {
      p = p.høyre;
      while (p.venstre != null) {
        p = p.venstre;
      }
    }
    else
    {
      while (p.forelder != null && p == p.forelder.høyre)
      {
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
    Node<T> p = rot;
    for ( ; p.høyre != null; p = p.høyre) stakk.leggInn(p);
    StringJoiner s = new StringJoiner(", ", "[","]");

    while (true)
    {
      s.add(p.verdi.toString());
      if (p.venstre != null)
      {
        for (p = p.venstre; p.høyre != null; p = p.høyre)
        {
          stakk.leggInn(p);
        }
      }
      else if (!stakk.tom())
      {
        p = stakk.taUt();
      }
      else break;
    }
    return s.toString();
  }

  public String høyreGren()
  {
    Deque<Node> stack = new ArrayDeque<>();
    Node p = rot;
    if(tom()) return "[]";

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

  public String[] grener()
  {
    Deque<Node<T>> stack = new ArrayDeque();
    //Hent alle bladnoder rekursiv hjelpemetode
    hentBladNode(rot, stack);
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
    Deque<Node<T>> stack = new ArrayDeque();
    Node <T> p = rot;
    StringJoiner s = new StringJoiner(", ", "[", "]");
    hentBladNode(p, stack);

    while(!stack.isEmpty())
    {
      p = stack.pollLast();
      s.add(p.verdi.toString());
    }
    return s.toString();
  }

  public String postString()
  {
    Deque<Node> stack = new ArrayDeque<>();
    Node prev = null;
    StringJoiner s = new StringJoiner(", ", "[", "]");
    if(rot == null)
      return "[]";

    stack.push(rot);
    while(!stack.isEmpty())
    {
      Node<T> current = stack.peek();

      if(prev == null ||current == prev.venstre ||current == prev.høyre)
      {
        if(current.venstre != null)
          stack.push(current.venstre);
        else if (current.høyre != null)
        {
          stack.push(current.høyre);
        }
        else
        {
          stack.pop();
          s.add(current.verdi.toString());
        }
      }
      else if(prev == current.venstre)
      {
        if(current.høyre != null)
          stack.push(current.høyre);
        else
        {
          stack.pop();
          s.add(current.verdi.toString());
        }
      }
      else if(prev == current.høyre)
      {
        stack.pop();
        s.add(current.verdi.toString());
      }
      prev = current;
    }
    return s.toString();

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
      if(p == null)
      {
        return;
      }

      while(p.venstre != null || p.høyre != null)
      {
        if(p.venstre != null)
        {
          p = p.venstre;
        }
        else
        {
          p = p.høyre;
        }
      }
    }
    
    @Override
    public boolean hasNext()
    {
      return p != null;  // Denne skal ikke endres!
    }
    
    @Override
    public T next()

    {
      if (endringer != iteratorendringer) {
        throw new ConcurrentModificationException("Listen er endret!");
      }

      if (!hasNext()) {
        throw new NoSuchElementException("Tomt eller ingen verdier igjen!");
      }
      q = p;

      removeOK = true;

      while (p != null && ((p.høyre != null || p.venstre != null) || p == q)){
        p = nesteInorden(p);
      }

      return q.verdi;
    }
    
    @Override
    public void remove()
    {
      if(endringer != iteratorendringer)
      {
        throw new ConcurrentModificationException();
      }
      if(!removeOK)
      {
        throw new IllegalStateException();
      }

      if(q == null)
      {
        return;
      }

      removeOK = false;

      if(q.forelder != null)
      {
        Node<T> parent = q.forelder;

        if(parent.høyre == q)
        {
          parent.høyre = null;
        }
        else if(parent.venstre == q)
        {
          parent.venstre = null;
        }
      }
      else
      {
        rot = null;
      }

      endringer ++;
      iteratorendringer++;
      antall--;
    }

  } // BladnodeIterator

} // ObligSBinTre
