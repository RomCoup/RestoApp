/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 29 "../../../../../RestoAppPersistence.ump"
// line 156 "../../../../../RestoApp.ump"
public class Waiter implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Waiter Attributes
  private String name;

  //Autounique Attributes
  private int number;

  //Waiter Associations
  private List<Order> order;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Waiter(String aName, RestoApp aRestoApp)
  {
    name = aName;
    number = nextNumber++;
    order = new ArrayList<Order>();
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create waiter due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getNumber()
  {
    return number;
  }

  public Order getOrder(int index)
  {
    Order aOrder = order.get(index);
    return aOrder;
  }

  public List<Order> getOrder()
  {
    List<Order> newOrder = Collections.unmodifiableList(order);
    return newOrder;
  }

  public int numberOfOrder()
  {
    int number = order.size();
    return number;
  }

  public boolean hasOrder()
  {
    boolean has = order.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = order.indexOf(aOrder);
    return index;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public static int minimumNumberOfOrder()
  {
    return 0;
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (order.contains(aOrder)) { return false; }
    Waiter existingWaiter = aOrder.getWaiter();
    if (existingWaiter == null)
    {
      aOrder.setWaiter(this);
    }
    else if (!this.equals(existingWaiter))
    {
      existingWaiter.removeOrder(aOrder);
      addOrder(aOrder);
    }
    else
    {
      order.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    if (order.contains(aOrder))
    {
      order.remove(aOrder);
      aOrder.setWaiter(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrder()) { index = numberOfOrder() - 1; }
      order.remove(aOrder);
      order.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(order.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrder()) { index = numberOfOrder() - 1; }
      order.remove(aOrder);
      order.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }

  public boolean setRestoApp(RestoApp aRestoApp)
  {
    boolean wasSet = false;
    if (aRestoApp == null)
    {
      return wasSet;
    }

    RestoApp existingRestoApp = restoApp;
    restoApp = aRestoApp;
    if (existingRestoApp != null && !existingRestoApp.equals(aRestoApp))
    {
      existingRestoApp.removeWaiter(this);
    }
    restoApp.addWaiter(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while( !order.isEmpty() )
    {
      order.get(0).setWaiter(null);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeWaiter(this);
    }
  }

  // line 34 "../../../../../RestoAppPersistence.ump"
   public static  void reinitializeAutouniqueNumber(List<Waiter> waiters){
    int nextId = 0; 
    for (Waiter waiter : waiters) {
      if (waiter.getNumber() > nextId) {
        nextId = waiter.getNumber();
      }
    }
    nextId++;
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 32 "../../../../../RestoAppPersistence.ump"
  private static final long serialVersionUID = -6819781202254769716L ;

  
}