/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 81 "../../../../../RestoAppPersistence.ump"
// line 111 "../../../../../RestoApp.ump"
public class OrderItem implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OrderItem Attributes
  private int quantity;

  //OrderItem Associations
  private PricedMenuItem pricedMenuItem;
  private List<Seat> seats;
  private Order order;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OrderItem(int aQuantity, PricedMenuItem aPricedMenuItem, Order aOrder, Seat... allSeats)
  {
    quantity = aQuantity;
    boolean didAddPricedMenuItem = setPricedMenuItem(aPricedMenuItem);
    if (!didAddPricedMenuItem)
    {
      throw new RuntimeException("Unable to create orderItem due to pricedMenuItem");
    }
    seats = new ArrayList<Seat>();
    boolean didAddSeats = setSeats(allSeats);
    if (!didAddSeats)
    {
      throw new RuntimeException("Unable to create OrderItem, must have at least 1 seats");
    }
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create orderItem due to order");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public PricedMenuItem getPricedMenuItem()
  {
    return pricedMenuItem;
  }

  public Seat getSeat(int index)
  {
    Seat aSeat = seats.get(index);
    return aSeat;
  }

  /**
   * only from order.tables.seats
   */
  public List<Seat> getSeats()
  {
    List<Seat> newSeats = Collections.unmodifiableList(seats);
    return newSeats;
  }

  public int numberOfSeats()
  {
    int number = seats.size();
    return number;
  }

  public boolean hasSeats()
  {
    boolean has = seats.size() > 0;
    return has;
  }

  public int indexOfSeat(Seat aSeat)
  {
    int index = seats.indexOf(aSeat);
    return index;
  }

  public Order getOrder()
  {
    return order;
  }

  public boolean setPricedMenuItem(PricedMenuItem aPricedMenuItem)
  {
    boolean wasSet = false;
    if (aPricedMenuItem == null)
    {
      return wasSet;
    }

    PricedMenuItem existingPricedMenuItem = pricedMenuItem;
    pricedMenuItem = aPricedMenuItem;
    if (existingPricedMenuItem != null && !existingPricedMenuItem.equals(aPricedMenuItem))
    {
      existingPricedMenuItem.removeOrderItem(this);
    }
    pricedMenuItem.addOrderItem(this);
    wasSet = true;
    return wasSet;
  }

  public boolean isNumberOfSeatsValid()
  {
    boolean isValid = numberOfSeats() >= minimumNumberOfSeats();
    return isValid;
  }

  public static int minimumNumberOfSeats()
  {
    return 1;
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    seats.add(aSeat);
    if (aSeat.indexOfOrderItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aSeat.addOrderItem(this);
      if (!wasAdded)
      {
        seats.remove(aSeat);
      }
    }
    return wasAdded;
  }

  public boolean removeSeat(Seat aSeat)
  {
    boolean wasRemoved = false;
    if (!seats.contains(aSeat))
    {
      return wasRemoved;
    }

    if (numberOfSeats() <= minimumNumberOfSeats())
    {
      return wasRemoved;
    }

    int oldIndex = seats.indexOf(aSeat);
    seats.remove(oldIndex);
    if (aSeat.indexOfOrderItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aSeat.removeOrderItem(this);
      if (!wasRemoved)
      {
        seats.add(oldIndex,aSeat);
      }
    }
    return wasRemoved;
  }

  public boolean setSeats(Seat... newSeats)
  {
    boolean wasSet = false;
    ArrayList<Seat> verifiedSeats = new ArrayList<Seat>();
    for (Seat aSeat : newSeats)
    {
      if (verifiedSeats.contains(aSeat))
      {
        continue;
      }
      verifiedSeats.add(aSeat);
    }

    if (verifiedSeats.size() != newSeats.length || verifiedSeats.size() < minimumNumberOfSeats())
    {
      return wasSet;
    }

    ArrayList<Seat> oldSeats = new ArrayList<Seat>(seats);
    seats.clear();
    for (Seat aNewSeat : verifiedSeats)
    {
      seats.add(aNewSeat);
      if (oldSeats.contains(aNewSeat))
      {
        oldSeats.remove(aNewSeat);
      }
      else
      {
        aNewSeat.addOrderItem(this);
      }
    }

    for (Seat anOldSeat : oldSeats)
    {
      anOldSeat.removeOrderItem(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean addSeatAt(Seat aSeat, int index)
  {  
    boolean wasAdded = false;
    if(addSeat(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSeatAt(Seat aSeat, int index)
  {
    boolean wasAdded = false;
    if(seats.contains(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSeatAt(aSeat, index);
    }
    return wasAdded;
  }

  public boolean setOrder(Order aOrder)
  {
    boolean wasSet = false;
    if (aOrder == null)
    {
      return wasSet;
    }

    Order existingOrder = order;
    order = aOrder;
    if (existingOrder != null && !existingOrder.equals(aOrder))
    {
      existingOrder.removeOrderItem(this);
    }
    order.addOrderItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    PricedMenuItem placeholderPricedMenuItem = pricedMenuItem;
    this.pricedMenuItem = null;
    if(placeholderPricedMenuItem != null)
    {
      placeholderPricedMenuItem.removeOrderItem(this);
    }
    ArrayList<Seat> copyOfSeats = new ArrayList<Seat>(seats);
    seats.clear();
    for(Seat aSeat : copyOfSeats)
    {
      aSeat.removeOrderItem(this);
    }
    Order placeholderOrder = order;
    this.order = null;
    if(placeholderOrder != null)
    {
      placeholderOrder.removeOrderItem(this);
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 84 "../../../../../RestoAppPersistence.ump"
  private static final long serialVersionUID = 2045406856025012133L ;

// line 116 "../../../../../RestoApp.ump"
  @Override
   public String toString () 
  {
    //String result = this.getQuantity() + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  $ " + String.valueOf(getPricedMenuItem().getPrice())+ "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t " + getPricedMenuItem().getMenuItem().getName() ;
    String result = String.format("%-4s \t\t\t\t %-"+ (20- (getPricedMenuItem().getMenuItem().getName().length()))+"s             \t\t  %5s", this.getQuantity(),getPricedMenuItem().getMenuItem().getName() ,String.valueOf(getPricedMenuItem().getPrice()));
    List<Seat> seats = this.getSeats();
    if (seats.size() > 1) {
		result = result + " (shared with: ";
		for(Seat seat : seats) {
	    	result = result + String.valueOf(seat.getNumber()) + ", ";
	    }
		result = result.substring(0, result.length() - 2);
		result = result + ")";
	}
    return result;
  }

  
}