package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {

    private Ship aircraftCarrier = new Ship("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    private Ship battleship = new Ship("Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    //private Ship cruiser = new Ship("Cruiser",3, new Coordinate(0,0),new Coordinate(0,0));
    //private Ship destroyer = new Ship("Destroyer",2, new Coordinate(0,0),new Coordinate(0,0));
    private Ship submarine = new Ship("Submarine",2, new Coordinate(0,0),new Coordinate(0,0));

    private Ship computer_aircraftCarrier = new Ship("Computer_AircraftCarrier",5, new Coordinate(2,2),new Coordinate(2,7));
    private Ship computer_battleship = new Ship("Computer_Battleship",4, new Coordinate(2,8),new Coordinate(6,8));
    //private Ship computer_cruiser = new Ship("Computer_Cruiser",3, new Coordinate(4,1),new Coordinate(4,4));
    //private Ship computer_destroyer = new Ship("Computer_Destroyer",2, new Coordinate(7,3),new Coordinate(7,5));
    private Ship computer_submarine = new Ship("Computer_Submarine",2, new Coordinate(9,6),new Coordinate(9,8));
    private Ship computer_clipper = new Ship("Computer_Clipper", 3, new Coordinate(1, 1), new Coordinate(1, 3));
    private Ship computer_dhingy = new Ship("Computer_Dhingy", 1, new Coordinate(10, 10), new Coordinate(10, 10));
    private Ship computer_fisher = new Ship("Computer_Fisher", 2, new Coordinate(7, 1), new Coordinate(7, 2));


    ArrayList<Coordinate> playerHits;
    private ArrayList<Coordinate> playerMisses;
    ArrayList<Coordinate> computerHits;
    private ArrayList<Coordinate> computerMisses;
    ArrayList<Coordinate> computerHitsCivShip;
    ArrayList<Coordinate> computerHitsCIAShip;

    boolean scanResult = false;



    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
    }


    public Ship getShip(String shipName) {
        if (shipName.equalsIgnoreCase("aircraftcarrier")) {
            return aircraftCarrier;
        } if(shipName.equalsIgnoreCase("battleship")) {
            return battleship;
        } if(shipName.equalsIgnoreCase("Cruiser")) {
        return null;
        } if(shipName.equalsIgnoreCase("destroyer")) {
            return null;
        }if(shipName.equalsIgnoreCase("submarine")) {
            return submarine;
        } else {
            return null;
        }
    }

    public BattleshipModel placeShip(String shipName, String row, String col, String orientation) {
        int rowint = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        if(orientation.equals("horizontal")){
            if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+4));
            } if(shipName.equalsIgnoreCase("battleship")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+3));
            } if(shipName.equalsIgnoreCase("Cruiser")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+2));
            } if(shipName.equalsIgnoreCase("destroyer")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+1));
            }if(shipName.equalsIgnoreCase("submarine")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 1));
            }
        }else{
            //vertical
                if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+4,colInt));
                } if(shipName.equalsIgnoreCase("battleship")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+3,colInt));
                } if(shipName.equalsIgnoreCase("Cruiser")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+2,colInt));
                } if(shipName.equalsIgnoreCase("destroyer")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+1,colInt));
                }if(shipName.equalsIgnoreCase("submarine")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 1, colInt));
                }
        }
        return this;
    }

    public void shootAtComputer(int row, int col) {
        Coordinate coor = new Coordinate(row, col);
        if (computer_aircraftCarrier.covers(coor)) {
            computerHits.add(coor);
        } else if (computer_battleship.covers(coor)) {
            computerHitsCIAShip.add(coor);
        } else if (computer_clipper.covers(coor)) {
            computerHitsCivShip.add(coor);
        } else if (computer_dhingy.covers(coor)) {
            computerHitsCivShip.add(coor);
        } else if (computer_fisher.covers(coor)) {
            computerHitsCivShip.add(coor);
        } else if (computer_submarine.covers(coor)){
            computerHitsCIAShip.add(coor);
        } else {
            computerMisses.add(coor);
        }
    }

    public void shootAtPlayer() {
        int max = 10;
        int min = 1;
        Random random = new Random();
        int randRow = random.nextInt(max - min + 1) + min;
        int randCol = random.nextInt(max - min + 1) + min;

        Coordinate coor = new Coordinate(randRow,randCol);
        playerShot(coor);
    }

    void playerShot(Coordinate coor) {
        if(playerMisses.contains(coor)){
            System.out.println("Dupe");
            this.shootAtPlayer();
        }

        if(aircraftCarrier.covers(coor)){
            playerHits.add(coor);

        }else if (battleship.covers(coor)){
            playerHits.add(coor);
        }//else if (cruiser.covers(coor)){
           // playerHits.add(coor);
        //}else if (destroyer.covers(coor)){
          //  playerHits.add(coor);
        else if (submarine.covers(coor)){
            playerHits.add(coor);
        } else {
            playerMisses.add(coor);
        }
    }


    public void scan(int rowInt, int colInt) {
        Coordinate coor = new Coordinate(rowInt, colInt);
        scanResult = false;
        if (computer_aircraftCarrier.scan(coor)) {
            scanResult = true;
<<<<<<< HEAD
        }
        else if (computer_battleship.scan(coor)){    // battleship has stealth
            scanResult = false;
        }else if (computer_cruiser.scan(coor)){
=======
        } else if (computer_battleship.scan(coor)) {
            scanResult = true;
        } else if (computer_clipper.scan(coor)) {
            scanResult = true;
        } else if (computer_dhingy.scan(coor)) {
>>>>>>> 402f23a539b599498cf3390a9629de7e09fcb91a
            scanResult = true;
        } else if (computer_fisher.scan(coor)) {
            scanResult = true;
<<<<<<< HEAD
        }else if (computer_submarine.scan(coor)){   //submarine has stealth
            scanResult = false;
=======
        } else if (computer_submarine.scan(coor)){
            scanResult = true;
>>>>>>> 402f23a539b599498cf3390a9629de7e09fcb91a
        } else {
            scanResult = false;
        }
    }

    public boolean getScanResult() {
        return scanResult;
    }
}
