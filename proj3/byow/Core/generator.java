package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.HexWorld;
import edu.princeton.cs.algs4.In;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.io.PipedOutputStream;
import java.util.*;

public class generator {
    TETile[][] hexGRid;
    Deque<Room> rooms;
    Map<Position,Room> roomMap;
    //Map<,Room> NodeToRoom = new HashMap<>();  // key is the Node
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;



    private static final long SEED = 28733;
    private static final Random RANDOM = new Random(SEED);

    public generator() {
        hexGRid = new TETile[WIDTH][HEIGHT];
        rooms = new ArrayDeque<>();
        roomMap = new HashMap<>();
        /*
        fillTheGridWithEmptySpace(hexGRid);
        List<Integer> widthAndHeight = getRandomRoomVal();
        Position startPos = new Position((int) RandomUtils.uniform(RANDOM, (double) WIDTH/4,(double) 3/4*WIDTH),
                (int) RandomUtils.uniform(RANDOM, (double) HEIGHT/4,(double) 3/4*HEIGHT));
        Room startRoom = new Room(startPos,widthAndHeight.get(0),widthAndHeight.get(1));
        addARoom(hexGRid,widthAndHeight.get(1),widthAndHeight.get(0),startPos);
        roomMap.put(startPos,startRoom);
        rooms.add(startRoom);
        while (!rooms.isEmpty()){
            Room a = rooms.poll();
            for (int i = 0; i < RandomUtils.uniform(RANDOM,1,5);i++){
                int direction = getRandomDirection();
                if(!thereIsExitOnEdge(hexGRid,a,direction)){
                    int length = getRandomLength();
                    Hallway hallway = addHallwaysToRoom(hexGRid,length,a,direction);
                    List<Integer> wAndH = getRandomRoomVal();
                    Room newRoom = connectARoomToHallway(hexGRid,wAndH.get(1),wAndH.get(0),hallway,hallway.direction);
                    roomMap.put(newRoom.position,newRoom);
                    rooms.add(newRoom);
                }
            }

        }

         */





    }

    private int getRandomLength(){
        int length;
        length = RandomUtils.uniform(RANDOM,1,10);
        return length;
    }
    private int getRandomDirection(){
        int direction;
        direction = RandomUtils.uniform(RANDOM,1,5);
        return direction;
    }
    private List<Integer> getRandomRoomVal(){
        List<Integer> result = new ArrayList<>();
        result.add(RandomUtils.uniform(RANDOM,3,10));
        result.add(RandomUtils.uniform(RANDOM,3,10));
        return result;
    }

    private static class Hallway{
        private Position entrance;
        private Position exit;
        private int direction;
        private int length;

        public Hallway(Position position, int length, int direction){
            this.entrance = position;
            this.length = length;
            this.direction = direction;
            if (direction == UP){
                this.exit = new Position(entrance.x,entrance.y+length);
            } else if (direction == DOWN){
                this.exit = new Position(entrance.x,entrance.y-length);
            } else if (direction == LEFT){
                this.exit = new Position(entrance.x - length,entrance.y);
            }else{
                this.exit = new Position(entrance.x + length,entrance.y);
            }
        }

        public Position getEntrance(){return entrance;}
        public Position getExit(){return exit;}
        public int getLength(){return length;}
        public int getDirection(){return direction;}

    }

    private static class Room{

        private Position position;
        private int Width;
        private int Height;
        private List<Room> neighbors;
        private Room parentRoom;

        public Room(Position p, int width, int height){
            position = p;
            Width = width;
            Height = height;
            neighbors = new ArrayList<>();

        }
        public int getWidth(){return Width;}
        public int getHeight(){return Height;}
        public Position getPosition(){return position;}
        public void addNeighbor(Room room){
            neighbors.add(room);
        }

    }

    private Room connectARoomToHallway(TETile[][] TileGrid, int height, int width, Hallway hallway,int direction){
        int index;
        Room room;
        int x = hallway.exit.x;
        int y = hallway.exit.y;
        if (direction == UP || direction == DOWN){
            index = RandomUtils.uniform(RANDOM,1,width-1);
        }else{
            index = RandomUtils.uniform(RANDOM,1,height-1);
        }
        if (direction == UP){
            Position oneOverExit = new Position(x - index,y + 1);
            addARoom(TileGrid,height,width,oneOverExit);
            TileGrid[hallway.exit.x][hallway.exit.y + 1] = Tileset.FLOOR;
            room = new Room(oneOverExit,width,height);
        }else if (direction == DOWN){
            Position oneOverExit = new Position(x - index,y-height);
            addARoom(TileGrid,height,width,oneOverExit);
            TileGrid[hallway.exit.x][hallway.exit.y - 1] = Tileset.FLOOR;
            room = new Room(oneOverExit,width,height);
        }else if (direction == LEFT){
            Position oneOverExit = new Position(x - width,y-index);
            addARoom(TileGrid,height,width,oneOverExit);
            TileGrid[hallway.exit.x -1 ][hallway.exit.y ] = Tileset.FLOOR;
            room = new Room(oneOverExit,width,height);
        }else{
            Position oneOverExit = new Position(x + 1,y-index);
            addARoom(TileGrid,height,width,oneOverExit);
            TileGrid[hallway.exit.x + 1][hallway.exit.y ] = Tileset.FLOOR;
            room = new Room(oneOverExit,width,height);

        }
        return room;
    }

    private boolean validRoomPosition(TETile[][] TileGrid, Hallway hallway) {
        return true;
    }


    private void fillTheGridWithEmptySpace(TETile[][] TileGrid){
        int width = TileGrid.length;
        int height = TileGrid[0].length;
        for(int i = 0; i< width; i++){
            for(int j = 0; j< height; j++){
                TileGrid[i][j] = Tileset.NOTHING;
            }
        }
    }

    private List<Integer> wAndHifRoomHitWall(TETile[][] TileGrid,int height, int width, Position position) {
        List<Integer> result = new ArrayList<>();
        int trueHeight = 0;
        int trueWidth = 0;
        boolean widthHitWall = false;
        for (int xCoor = position.x; xCoor < position.x + width; xCoor++) {
            for (int yCoor = position.y; yCoor < position.y + height; yCoor++) {
                if (!TileGrid[xCoor + 1][yCoor].equals(Tileset.NOTHING)) {
                    widthHitWall = true;
                    break;
                }
            }
            if(!widthHitWall) {
                trueWidth += 1;
            }else {
                break;
            }
        }
        boolean heightHitWall = false;
        for (int yCoor = position.y; yCoor < position.y + height; yCoor++) {
            for (int xCoor = position.x; xCoor < position.x + width; xCoor++) {
                if (!TileGrid[xCoor][yCoor + 1].equals(Tileset.NOTHING)) {
                    heightHitWall = true;
                    break;
                }
            }
            if(!heightHitWall) {
                trueHeight += 1;
            }else {
                break;
            }
        }

        result.add(trueWidth);
        result.add(trueHeight);
        if ( widthHitWall || heightHitWall){
            result.add(1);
        }else{
            result.add(-1);
        }
        return result;
    }

    private Room addARoom(TETile[][] TileGrid,int height, int width, Position position){
        if(!TileGrid[position.x][position.y].equals(Tileset.NOTHING)){
            return null;
        }else{
            Room room;
            List <Integer> wAndH = wAndHifRoomHitWall(TileGrid,height,width,position);
            int newHeight = wAndH.get(1);
            int newWidth = wAndH.get(0);
            for (int xCoor = position.x; xCoor < position.x + newWidth; xCoor++){
                for (int yCoor = position.y; yCoor < position.y + newHeight; yCoor++) {
                    if(xCoor == position.x || xCoor == position.x + newWidth - 1){
                        TileGrid[xCoor][yCoor] = Tileset.WALL;
                    }else{
                        if(yCoor == position.y || yCoor == position.y + newHeight - 1){
                            TileGrid[xCoor][yCoor] = Tileset.WALL;
                        }else{
                            TileGrid[xCoor][yCoor] = Tileset.FLOOR;
                        }
                    }
                }
            }
            if (wAndH.get(2) < 0){
                room = new Room(position,newWidth,newHeight);
            }else{
                room = null;
            }
            return room;
        }
    }

    private boolean ifHallwaysHitEdge(TETile[][] TileGrid,int length, Room room, int direction){
        if (direction == UP){
            if(length < HEIGHT - room.position.y - room.getHeight() - 2){
                return false;
            }else{
                return true;
            }
        }else if(direction == DOWN){
            if(length < room.position.y - 2){
                return false;
            }else{
                return true;
            }
        }else if(direction == LEFT){
            if(length < room.position.x - 2){
                return false;
            }else{
                return true;
            }
        }else{
            if(length < WIDTH - room.position.x -room.getWidth() - 2){
                return false;
            }else{
                return true;
            }
        }

    }

    private boolean ifEmptySpace(TETile[][] TileGrid,int xCoor ,int yCoor){
        if (TileGrid[xCoor][yCoor].equals(Tileset.NOTHING)){
            return true;
        }else{
            return false;
        }
    }

    private Hallway addHallwayOnUpSide(TETile[][] TileGrid,int length, Room room,int direction){
        int index;
        int trueLength = 0;
        boolean hitWall = false;
        Hallway hallway;
        index = RandomUtils.uniform(RANDOM,1,room.getWidth()-1);

        if(!ifHallwaysHitEdge(TileGrid,length,room,direction)){
            TileGrid[room.position.x+index][room.position.y + room.getHeight()-1] = Tileset.FLOOR;
            for (int yCoor = room.position.y + room.getHeight(); yCoor < room.position.y + room.getHeight() + length; yCoor++){
                if(!ifEmptySpace(hexGRid,room.position.x + index,yCoor) || !ifEmptySpace(hexGRid,room.position.x + index -1,yCoor)
                        || !ifEmptySpace(hexGRid,room.position.x + index+1,yCoor)){
                    TileGrid[room.position.x + index][yCoor - 1] = Tileset.WALL;
                    hitWall = true;
                    break;
                }else{
                    TileGrid[room.position.x + index][yCoor] = Tileset.FLOOR;
                    TileGrid[room.position.x + index - 1][yCoor] = Tileset.WALL;
                    TileGrid[room.position.x + index + 1][yCoor] = Tileset.WALL;
                    trueLength += 1;
                }
            }
            if(hitWall){
                hallway = null;
            }else{
                hallway = new Hallway(new Position(room.position.x + index,room.position.y + room.getHeight()-1),trueLength,direction);
            }
        }else{
            TileGrid[room.position.x+index][room.position.y + room.getHeight()-1] = Tileset.FLOOR;
            for (int yCoor = room.position.y + room.getHeight(); yCoor < HEIGHT; yCoor++){
                if(!ifEmptySpace(hexGRid,room.position.x + index,yCoor) || !ifEmptySpace(hexGRid,room.position.x + index -1,yCoor)
                        || !ifEmptySpace(hexGRid,room.position.x + index+1,yCoor)){
                    TileGrid[room.position.x + index][yCoor - 1] = Tileset.WALL;
                    hitWall = true;
                    break;
                }else{
                    TileGrid[room.position.x + index][yCoor] = Tileset.FLOOR;
                    TileGrid[room.position.x + index - 1][yCoor] = Tileset.WALL;
                    TileGrid[room.position.x + index + 1][yCoor] = Tileset.WALL;
                }
            }
            if(!hitWall){
                TileGrid[room.position.x + index][HEIGHT-1] = Tileset.WALL;
            }
            hallway = null;
        }
        return  hallway;

    }

    private Hallway addHallwayOnDownSide(TETile[][] TileGrid,int length, Room room,int direction){
        int index;
        int trueLength = 0;
        boolean hitWall = false;
        Hallway hallway;
        index = RandomUtils.uniform(RANDOM,1,room.getWidth()-1);

        if(!ifHallwaysHitEdge(TileGrid,length,room,direction)){
            TileGrid[room.position.x+index][room.position.y] = Tileset.FLOOR;
            for (int yCoor = room.position.y - 1; yCoor > room.position.y - length -1; yCoor--){
                if(!ifEmptySpace(hexGRid,room.position.x + index,yCoor) || !ifEmptySpace(hexGRid,room.position.x + index -1,yCoor)
                        || !ifEmptySpace(hexGRid,room.position.x + index+1,yCoor)){
                    TileGrid[room.position.x + index][yCoor + 1] = Tileset.WALL;
                    hitWall = true;
                    break;
                }else{
                    TileGrid[room.position.x + index][yCoor] = Tileset.FLOOR;
                    TileGrid[room.position.x + index - 1][yCoor] = Tileset.WALL;
                    TileGrid[room.position.x + index + 1][yCoor] = Tileset.WALL;
                    trueLength += 1;
                }
            }
            if (hitWall){
                hallway = null;
            }else{
                hallway = new Hallway(new Position(room.position.x + index,room.position.y),trueLength,direction);
            }
        }else{
            TileGrid[room.position.x+index][room.position.y] = Tileset.FLOOR;
            for (int yCoor = room.position.y -1; yCoor >= 0; yCoor--){
                if(!ifEmptySpace(hexGRid,room.position.x + index,yCoor) || !ifEmptySpace(hexGRid,room.position.x + index -1,yCoor)
                        || !ifEmptySpace(hexGRid,room.position.x + index+1,yCoor)){
                    TileGrid[room.position.x + index][yCoor + 1] = Tileset.WALL;
                    hitWall = true;
                    break;
                }else{
                    TileGrid[room.position.x + index][yCoor] = Tileset.FLOOR;
                    TileGrid[room.position.x + index - 1][yCoor] = Tileset.WALL;
                    TileGrid[room.position.x + index + 1][yCoor] = Tileset.WALL;
                }
            }
            if(!hitWall){
                TileGrid[room.position.x + index][0] = Tileset.WALL;
            }
            hallway = null;
        }
        return  hallway;

    }

    private Hallway addHallwayOnLeftSide(TETile[][] TileGrid,int length, Room room,int direction){
            int index;
            int trueLength = 0;
            boolean hitWall = false;
            Hallway hallway;
            index = RandomUtils.uniform(RANDOM,1,room.getHeight()-1);

            if(!ifHallwaysHitEdge(TileGrid,length,room,direction)){
                TileGrid[room.position.x][room.position.y + index] = Tileset.FLOOR;
                for (int xCoor = room.position.x - 1; xCoor > room.position.x - length -1; xCoor--){
                    if(!ifEmptySpace(hexGRid,xCoor,room.position.y + index) || !ifEmptySpace(hexGRid,xCoor,room.position.y + index - 1)
                            || !ifEmptySpace(hexGRid,xCoor,room.position.y + index + 1)){
                        TileGrid[xCoor + 1][room.position.y + index] = Tileset.WALL;
                        hitWall = true;
                        break;
                    }else{
                        TileGrid[xCoor][room.position.y + index] = Tileset.FLOOR;
                        TileGrid[xCoor][room.position.y + index - 1] = Tileset.WALL;
                        TileGrid[xCoor][room.position.y + index + 1] = Tileset.WALL;
                        trueLength += 1;
                    }
                }
                if (hitWall){
                    hallway = null;
                }else{
                    hallway = new Hallway(new Position(room.position.x,room.position.y + index),trueLength,direction);
                }
            }else{
                TileGrid[room.position.x][room.position.y + index] = Tileset.FLOOR;
                for (int xCoor = room.position.x; xCoor >= 0; xCoor--){
                    if(!ifEmptySpace(hexGRid,xCoor,room.position.y + index) || !ifEmptySpace(hexGRid,xCoor,room.position.y + index - 1)
                            || !ifEmptySpace(hexGRid,xCoor,room.position.y + index + 1)){
                        TileGrid[xCoor + 1][room.position.y + index] = Tileset.WALL;
                        hitWall = true;
                        break;
                    }else{
                        TileGrid[xCoor][room.position.y + index] = Tileset.FLOOR;
                        TileGrid[xCoor][room.position.y + index - 1] = Tileset.WALL;
                        TileGrid[xCoor][room.position.y + index + 1] = Tileset.WALL;
                    }
                }
                if(!hitWall){
                    TileGrid[0][room.position.y + index] = Tileset.FLOOR;
                }
                hallway = null;
            }
            return  hallway;
    }

    private Hallway addHallwayOnRightSide(TETile[][] TileGrid,int length, Room room,int direction){
        int index;
        int trueLength = 0;
        boolean hitWall = false;
        Hallway hallway;
        index = RandomUtils.uniform(RANDOM,1,room.getHeight()-1);

        if(!ifHallwaysHitEdge(TileGrid,length,room,direction)){
            TileGrid[room.position.x + room.getWidth() - 1][room.position.y + index] = Tileset.FLOOR;
            for (int xCoor = room.position.x + room.getWidth(); xCoor < room.position.x+ room.getWidth() + length; xCoor++){
                if(!ifEmptySpace(hexGRid,xCoor,room.position.y + index) || !ifEmptySpace(hexGRid,xCoor,room.position.y + index - 1)
                        || !ifEmptySpace(hexGRid,xCoor,room.position.y + index + 1)){
                    TileGrid[xCoor - 1][room.position.y + index] = Tileset.WALL;
                    hitWall = true;
                    break;
                }else{
                    TileGrid[xCoor][room.position.y + index] = Tileset.FLOOR;
                    TileGrid[xCoor][room.position.y + index - 1] = Tileset.WALL;
                    TileGrid[xCoor][room.position.y + index + 1] = Tileset.WALL;
                    trueLength += 1;
                }
            }
            if (hitWall){
                hallway = null;
            }else{
                hallway = new Hallway(new Position(room.position.x + room.getWidth() - 1,room.position.y + index),length,direction);
            }
        }else{
            TileGrid[room.position.x + room.getWidth() - 1][room.position.y + index] = Tileset.FLOOR;
            for (int xCoor = room.position.x + room.getWidth() - 1; xCoor < WIDTH; xCoor++){
                if(!ifEmptySpace(hexGRid,xCoor,room.position.y + index) || !ifEmptySpace(hexGRid,xCoor,room.position.y + index - 1)
                        || !ifEmptySpace(hexGRid,xCoor,room.position.y + index + 1)){
                    TileGrid[xCoor - 1][room.position.y + index] = Tileset.WALL;
                    hitWall = true;
                    break;
                }else{
                    TileGrid[xCoor][room.position.y + index] = Tileset.FLOOR;
                    TileGrid[xCoor][room.position.y + index - 1] = Tileset.WALL;
                    TileGrid[xCoor][room.position.y + index + 1] = Tileset.WALL;
                }
            }
            if(!hitWall){
                TileGrid[WIDTH-1][room.position.y + index] = Tileset.FLOOR;
            }
            hallway = null;
        }
        return  hallway;
    }

    private Hallway addHallwaysToRoom(TETile[][] TileGrid,int length, Room room, int direction){
        Hallway hallway;

        if (direction == UP){
            hallway = addHallwayOnUpSide(TileGrid,length,room,direction);
        }else if (direction == DOWN){
            hallway = addHallwayOnDownSide(TileGrid,length,room,direction);
        }else if (direction == LEFT){
            hallway = addHallwayOnLeftSide(TileGrid,length,room,direction);
        }else{
            hallway = addHallwayOnRightSide(TileGrid,length,room,direction);
        }
        return hallway;
    }

    private boolean thereIsExitOnEdge(TETile[][] TileGrid,Room room,int direction){
        if (direction == UP){
            for (int xCoor = room.position.x; xCoor < room.position.x + room.getWidth(); xCoor++){
                if (TileGrid[xCoor][room.position.y + room.getHeight() - 1] != Tileset.WALL){
                    return true;
                }
            }
            return false;
        }else if (direction == DOWN){
            for (int xCoor = room.position.x; xCoor < room.position.x + room.getWidth(); xCoor++){
                if (TileGrid[xCoor][room.position.y] != Tileset.WALL){
                    return true;
                }
            }
            return false;
        }else if (direction == LEFT){
            for (int yCoor = room.position.y; yCoor < room.position.y + room.getHeight(); yCoor++){
                if (TileGrid[room.position.x][yCoor] != Tileset.WALL) {
                    return true;
                }
            }
            return false;
        }else{
            for (int yCoor = room.position.y; yCoor < room.position.y + room.getHeight(); yCoor++){
                if (TileGrid[room.position.x + room.getWidth() -1][yCoor] != Tileset.WALL){
                    return true;
                }
            }
            return false;
        }

    }


    //private boolean detectWall(TETile[][] TileGrid){}


    // use a tree to store the position of room and extend the room according to the depth search

    public static void main(String[] args){

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        generator generator = new generator();
        generator.fillTheGridWithEmptySpace(generator.hexGRid);
        Position p = new Position(30,10);
        generator.addARoom(generator.hexGRid,10,7,p);
        Room a = new Room(p,7,10);
        Hallway hallway1 = generator.addHallwaysToRoom(generator.hexGRid,9,a,UP);
        Hallway hallway2 = generator.addHallwaysToRoom(generator.hexGRid,5,a,RIGHT);
        Room b = generator.connectARoomToHallway(generator.hexGRid,6,10,hallway2,hallway2.direction);
        Hallway hallway3 = generator.addHallwaysToRoom(generator.hexGRid,5,b,DOWN);
        Room c = generator.connectARoomToHallway(generator.hexGRid,3,5,hallway3,hallway3.direction);
        Hallway hallway4 = generator.addHallwaysToRoom(generator.hexGRid,3,b,UP);
        Room d = generator.connectARoomToHallway(generator.hexGRid,3,6,hallway4,hallway4.direction);
        Hallway hallway5 = generator.addHallwaysToRoom(generator.hexGRid,4,d,LEFT);





        ter.renderFrame(generator.hexGRid);
    }




}
