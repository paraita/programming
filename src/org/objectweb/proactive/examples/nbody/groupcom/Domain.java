package org.objectweb.proactive.examples.nbody.groupcom;


import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.objectweb.proactive.ProActive;
import org.objectweb.proactive.core.group.Group;
import org.objectweb.proactive.core.group.ProActiveGroup;
import org.objectweb.proactive.examples.nbody.common.Displayer;
import org.objectweb.proactive.examples.nbody.common.Rectangle;

/**
 * Domains encapsulate one Planet, do their calculations, communicates with a Group, and synchronized by a master.
 */
public class Domain implements Serializable{
    
    private int identification;					// unique domain identifier
    private Domain neighbours;					// The Group containing all the other Domains
    private String hostName = "unknown";		// to display on which host we're running
    
    private Maestro maestro;					// the master for synchronization
    private Displayer display;					// If we want some graphical interface
    
    Planet info;								// the body information
    private Planet [] values; 					// list of all the bodies sent by the other domains
    private int nbvalues, nbReceived=0;			// iteration related variables, counting the "pings"
    
    /**
     * Required by ProActive Active Objects
     */
    public Domain (){}
    
    /**
     * Constructor
     * @param i the unique identifier
     * @param r the boundaries containing the Planet at the begining of the simulation
     */
    public Domain (Integer i, Rectangle r) {
        identification = i.intValue();
        info = new Planet(r);
        try { this.hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {  e.printStackTrace();  }
    }
    
    /**
     * Sets some execution-time related variables. 
     * @param domainGroup all the other Domains.
     * @param dp The Displayer used to show on screen the movement of the objects.
     * @param master Maestro used to synchronize the computations. 
     */
    public void init(Domain domainGroup, Displayer dp, Maestro master) {
        this.display=dp;
        this.maestro = master;
        this.neighbours = domainGroup;
        Group g = ProActiveGroup.getGroup(neighbours);
        g.remove(ProActive.getStubOnThis()); 				// no need to send information to self
        this.nbvalues = g.size();							// number of expected values to receive.
        this.values = new Planet [nbvalues + 1] ; 			// leave empty slot for self
    }
    
    /**
     * Reset iteration-related variables
     *
     */
    public void clearValues(){
        this.nbReceived = 0 ;
    } 
    
    /**
     * Move the Planet contained, applying the force computed. 
     */
    public void moveBody() {
        // System.out.println("Domain " + identification + " starting mvt computation");
        Force force = new Force();  
        for (int i = 0 ; i < values.length ; i++) {
            force.add(info, values[i]); // adds the interaction of the distant body 
        }
        this.info.moveWithForce(force);
        clearValues();
    }
    
    
    /**
     * Called by a distant Domain, this method adds the inf contribution to the force applied on the local Planet
     * @param inf the distant Planet which adds its contribution.
     * @param id the identifier of this distant body.
     */
    public void setValue(Planet inf, int id) {
        this.values [id] = inf;
        this.nbReceived ++ ;
        if (this.nbReceived > this.nbvalues)  // This is a bad sign!
            throw new RuntimeException("Domain " + identification + " received too many answers");
        if (this.nbReceived == this.nbvalues) {
            this.maestro.notifyFinished();
            moveBody();
        }
    }
    
    /**
     * Triggers the emission of the local Planet to all the other Domains.
     */
    public void sendValueToNeighbours() {
        this.neighbours.setValue(info,identification);
        if (this.display == null) {// if no display, only the first Domain outputs message to say recompute is going on
            if (this.identification==0) 
                System.out.println("Compute movement.");
        }
        else 
            this.display.drawBody((int)this.info.x, (int)this.info.y, (int)this.info.vx, (int)this.info.vy, 
                    (int)this.info.mass, (int)this.info.diameter, this.identification, this.hostName);
    }
    
    /**
     * Method called when the object is redeployed on a new Node (Fault recovery, or migration).
     */
    private void readObject(java.io.ObjectInputStream in) 
    throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        try {
            this.hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName="unknown";
            e.printStackTrace();
        }
        
    }
}
