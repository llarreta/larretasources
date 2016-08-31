package co.com.directv.sdii.model.pojo;



/**
 * MassiveMovementDealer entity. @author MyEclipse Persistence Tools
 */

public class MassiveMovementDealer  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Dealer dealer;
     private MassiveMovement massiveMovement;
     private String movementMessage;


    // Constructors

    /** default constructor */
    public MassiveMovementDealer() {
    }

    
    /** full constructor */
    public MassiveMovementDealer(Dealer dealer, MassiveMovement massiveMovement, String movementMessage) {
        this.dealer = dealer;
        this.massiveMovement = massiveMovement;
        this.movementMessage = movementMessage;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Dealer getDealer() {
        return this.dealer;
    }
    
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public MassiveMovement getMassiveMovement() {
        return this.massiveMovement;
    }
    
    public void setMassiveMovement(MassiveMovement massiveMovement) {
        this.massiveMovement = massiveMovement;
    }

    public String getMovementMessage() {
        return this.movementMessage;
    }
    
    public void setMovementMessage(String movementMessage) {
        this.movementMessage = movementMessage;
    }
   








}