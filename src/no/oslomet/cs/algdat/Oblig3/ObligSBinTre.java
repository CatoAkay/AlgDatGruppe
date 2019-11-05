package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import com.sun.deploy.security.SelectableSecurityManager;

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
    Objects.requireNonNull(verdi, "Kan ikke være null verdier");

    Node<T> p = rot, q = null;
    int cmp = 0;

    while(p != null)
    {
      q = p;

      cmp = comp.compare(verdi, p.verdi);

      p = cmp < 0 ? p.venstre : p.høyre;

    }

    p = new Node<>(verdi,q);

    if(q == null) rot = p;
    else if(cmp < 0) q.venstre = p;
    else q.høyre = p;

    antall++;
    endringer++;
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
          Node<T> b = p.venstre != null ? p.venstre : p.høyre;
          if (p == rot) rot = b;
          else if (p == q.venstre)
          {
              q.venstre = b;
              if(b != null)
              {
                  b.forelder = q;
              }

          }
          else
          {
              q.høyre = b;
              if (b != null)
              {
                  b.forelder = q;
              }
          }
      }
      else
      {
          Node<T> s = null, r = p.høyre;
          while (r.venstre != null)
          {
              r = r.venstre;
          }

          s = r.forelder;
          p.verdi = r.verdi;

          if (s != p)
          {
              s.venstre = r.høyre;
              r.høyre.forelder = s.venstre;
          }
          else
          {
              s.høyre = r.høyre;
              r.høyre.forelder = s.høyre;
          }
      }

      antall--;
      return true;
  }

    public static void main(String[] args) {
        int[] a = {4,7,2,9,4,10,8,7,4,6,1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);

        System.out.println(tre.fjern(4)); // 3
        System.out.println(tre);
    }
  
  public int fjernAlle(T verdi)
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  @Override
  public int antall()
  {
    return antall;
  }
  
  public int antall(T verdi)
  {
    Node<T> p = rot;
    int antallVerdi = 0;

    while (p != null)
    {
      int cmp = comp.compare(verdi,p.verdi);

      if (cmp < 0) p = p.venstre; //Mindre går derfor mot venstre
      else
      {
        if (cmp == 0) antallVerdi++;
        p = p.høyre; //Større eller lik, går derfor mot høyre
      }
    }
    return antallVerdi;
  }
  
  @Override
  public boolean tom()
  {
    return antall == 0;
  }
  
  @Override
  public void nullstill()
  {
    throw new UnsupportedOperationException("Ikke kodet ennå!");
  }
  
  private static <T> Node<T> nesteInorden(Node<T> p)
  {
    Objects.requireNonNull(p,"Kan ikke være null");

    if(p.høyre != null)
    {
      p = p.høyre;

      while (p.venstre != null) {
        p = p.venstre;
      }
    }
    else
    {
      while(p.forelder != null && p == p.forelder.høyre)
      {
        p = p.forelder;
      }
      p = p.forelder;
    }
    return p;
  }

  @Override
  public String toString()
  {
    if (tom()) return "[]";

    StringBuilder s = new StringBuilder();
    s.append('[');

    Node<T> p = rot;

    while (p.venstre != null) p = p.venstre;
    s.append(p.verdi);
    p = nesteInorden(p);

    for(int i = 0; i < antall-1; i++)
    {
      s.append(',').append(' ').append(p.verdi);
      p = nesteInorden(p);
    }

    s.append(']');
    return s.toString();
  }


  
  public String omvendtString()
  {
    if (tom()) return "[]";

    Stakk<Node<T>> stakk = new TabellStakk<>();
    Node<T> p = rot;   // starter i roten og går til høyre
    for ( ; p.høyre != null; p = p.høyre) stakk.leggInn(p);

    StringJoiner s = new StringJoiner(", ", "[", "]");

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
        p = stakk.taUt();   // p.høyre == null, henter fra stakken
      }
      else break;          // stakken er tom - vi er ferdig
    } // while
    return s.toString();
  }

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
