package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */

public class BattleshipModel {

    private Ship aircraftCarrier = new Ship("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    private Ship battleship = new StealthShip("Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    private Ship submarine = new StealthShip("Submarine",2, new Coordinate(0,0),new Coordinate(0,0));
    private Ship clipper = new CivilianShip("Clipper", 3, new Coordinate(0, 0), new Coordinate(0, 0));
    private Ship dhingy = new CivilianShip("Dhingy", 1, new Coordinate(0, 0), new Coordinate(0, 0));
    private Ship fisher = new CivilianShip("Fisher", 2, new Coordinate(0, 0), new Coordinate(0, 0));


    private Ship computer_aircraftCarrier = new Ship("Computer_AircraftCarrier",5, new Coordinate(2,2),new Coordinate(2,7));
    private Ship computer_battleship = new StealthShip("Computer_Battleship",4, new Coordinate(2,8),new Coordinate(6,8));
    private Ship computer_submarine = new StealthShip("Computer_Submarine",2, new Coordinate(9,6),new Coordinate(9,8));
    private Ship computer_clipper = new CivilianShip("Computer_Clipper", 3, new Coordinate(1, 1), new Coordinate(1, 3));
    private Ship computer_dhingy = new CivilianShip("Computer_Dhingy", 1, new Coordinate(10, 10), new Coordinate(10, 10));
    private Ship computer_fisher = new CivilianShip("Computer_Fisher", 2, new Coordinate(7, 1), new Coordinate(7, 2));


    ArrayList<ShotData> playerHits;
    ArrayList<ShotData> playerMisses;
    ArrayList<ShotData> computerHits;
    ArrayList<ShotData> computerMisses;

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
        } if(shipName.equalsIgnoreCase("dhingy")) {
            return dhingy;
        } if(shipName.equalsIgnoreCase("clipper")) {
            return clipper;
        } if(shipName.equalsIgnoreCase("fisher")){
            return fisher;
        } if(shipName.equalsIgnoreCase("submarine")) {
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
            } if(shipName.equalsIgnoreCase("dhingy")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt));
            } if(shipName.equalsIgnoreCase("clipper")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2));
            } if(shipName.equalsIgnoreCase("fisher")){
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 1));
            }if(shipName.equalsIgnoreCase("submarine")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 1));
            }
        }else{
            //vertical
                if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+4,colInt));
                } if(shipName.equalsIgnoreCase("battleship")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+3,colInt));
                } if(shipName.equalsIgnoreCase("dhingy")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt));
                } if(shipName.equalsIgnoreCase("clipper")) {
                this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt));
                } if(shipName.equalsIgnoreCase("fisher")){
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 1, colInt));
                }if(shipName.equalsIgnoreCase("submarine")) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 1, colInt));
                }
        }
        return this;
    }

    public void addHittoList(Ship x, Coordinate coor, ArrayList<ShotData> z){

        ShotData temp = new ShotData (coor, x.type);
        z.add(temp);
    }


    public void shootAtComputer(int row, int col) {
        Coordinate coor = new Coordinate(row, col);
        Ship temp;

        if (computer_aircraftCarrier.covers(coor)) {
            temp = computer_aircraftCarrier;
        } else if (computer_battleship.covers(coor)) {
            temp = computer_battleship;
        } else if (computer_clipper.covers(coor)) {
            temp = computer_clipper;
        } else if (computer_dhingy.covers(coor)) {
            temp = computer_dhingy;
        } else if (computer_fisher.covers(coor)) {
            temp = computer_fisher;
        } else if (computer_submarine.covers(coor)){
            temp = computer_submarine;
        } else {
            computerMisses.add(new ShotData (coor, "default"));
            return;
        }
        addHittoList(temp, coor, computerHits);
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
        ShotData search = new ShotData(coor, "default");
        if(playerMisses.contains(search)){
            System.out.println("Dupe");
            this.shootAtPlayer();
        }
        Ship temp;

        if(aircraftCarrier.covers(coor)){
            temp = aircraftCarrier;
        }else if (battleship.covers(coor)){
            temp = battleship;
        }else if (dhingy.covers(coor)){
            temp = dhingy;
        }else if (clipper.covers(coor)) {
            temp = clipper;
        }else if (fisher.covers(coor)){
            temp = fisher;
        }else if (submarine.covers(coor)){
            temp = submarine;
        }else {
            playerMisses.add(new ShotData (coor, "default"));
            return;
        }
        addHittoList(temp, coor, playerHits);
    }

    public void scan(int rowInt, int colInt) {
        Coordinate coor = new Coordinate(rowInt, colInt);
        scanResult = false;
        if (computer_aircraftCarrier.scan(coor)) {
            scanResult = true;
        } else if (computer_battleship.scan(coor)){
            scanResult = true;
	    } else if (computer_clipper.scan(coor)) {
            scanResult = true;
        } else if (computer_dhingy.scan(coor)) {
            scanResult = true;
        } else if (computer_fisher.scan(coor)) {
            scanResult = true;
        } else {
            scanResult = false;
        }
    }

    public boolean getScanResult() {
        return scanResult;
    }
}
