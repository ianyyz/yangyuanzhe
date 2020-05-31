package byow.lab12;
import byow.Core.Position;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int size = 4;
    //private static final int WIDTH = size * getHeight(size);
    //private static final int HEIGHT = 3*getWidth(size) + 2 * size;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 28733;
    private static final Random RANDOM = new Random(SEED);

    public static class Position{
        public int x;
        public int y;

        public Position(int x,int y){
            this.x = x;
            this.y = y;
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.AVATAR;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.SAND;
            case 5: return Tileset.MOUNTAIN;

            default: return Tileset.TREE;
        }
    }


    // first fill all the grid by empty space
    public static void fillTheGridWithEmptySpace(TETile[][] hexGrid){
        int width = hexGrid.length;
        int height = hexGrid[0].length;
        for(int i = 0; i< width; i++){
            for(int j = 0; j< height; j++){
                hexGrid[i][j] = Tileset.NOTHING;
            }
        }
    }


    public static void fillTheGridWithObj(TETile[][] hexGrid,int size,TETile obj,int startingX,int startingY){
        int h = getHeight(size);
        int w = getWidth(size);
        int offline = 0;
        for(int i = startingX; i < startingX + size; i++){
            for(int j = startingY + (size - ((i -startingX)%size) -1); j < startingY + h-(size - ((i -startingX)%size) -1); j++){
                hexGrid[i][j] = obj;
            }
        }
        for(int i = startingX + size; i < startingX + w - size; i++){
            for(int j = startingY; j < startingY + h; j++){
                hexGrid[i][j] = obj;
            }
            offline += 1;
        }
        for(int i =startingX + w - size; i < startingX + w; i++){
            for(int j = startingY + ((i -startingX)- offline)%size; j < startingY + h - ((i -startingX)- offline)%size; j++){
                hexGrid[i][j] = obj;
            }
        }
    }



    private static int getWidth(int size) {return 2*(size-1)+size;}

    private static int getHeight(int size) {return size*2;}

    public static void addHexagon(TETile[][] hexGrid, int size, Position p,TETile obj){
        fillTheGridWithObj(hexGrid,size,obj,p.y,p.x );
    }

    public static void generateHexagons(TETile[][] hexGrid,int size, List<Position> pList){
        for (Position i: pList){
            addHexagon(hexGrid,size,i,randomTile());
        }

    }


    private static List<Position> getAllPositions(int size,Position startPosition){
        List<Position> result = new ArrayList<>();
        int yGap = getWidth(size) - size + 1;
        int levelOne = startPosition.x + 2*size;
        int num1 = 0;
        while (num1 <3){
            Position p = new Position(levelOne + num1 * getHeight(size),startPosition.y);
            result.add(p);
            num1 += 1;
        }
        int levelTwo = startPosition.x + size;
        int num2 = 0;
        while (num2 < 4){
            Position p = new Position(levelTwo + num2 * getHeight(size),startPosition.y + yGap);
            result.add(p);
            num2 += 1;
        }
        int levelThree = startPosition.x;
        int num3 = 0;
        while (num3 < 5){
            Position p = new Position(levelThree + num3 * getHeight(size),startPosition.y + 2 * yGap);
            result.add(p);
            num3 += 1;
        }
        int levelFour = startPosition.x + size;
        int num4 = 0;
        while (num4 < 4){
            Position p = new Position(levelFour + num4 * getHeight(size),startPosition.y + 3 * yGap);
            result.add(p);
            num4 += 1;
        }
        int levelFive = startPosition.x + 2 * size;
        int num5 = 0;
        while (num5 < 3){
            Position p = new Position(levelFive + num5 * getHeight(size),startPosition.y + 4 * yGap);
            result.add(p);
            num5 += 1;
        }
        return result;
    }

    private static void addARoom(TETile[][] TileGrid,int height, int width, byow.Core.Position position){
        for (int xCoor = position.x; xCoor < position.x + width; xCoor++){
            for (int yCoor = position.y; yCoor < position.y + height; yCoor++){
                if(xCoor == position.x || xCoor == position.x + width-1){
                    TileGrid[xCoor][yCoor] = Tileset.WALL;
                }else{
                    if(yCoor == position.y || yCoor == position.y + height-1){
                        TileGrid[xCoor][yCoor] = Tileset.WALL;
                    }else{
                        TileGrid[xCoor][yCoor] = Tileset.FLOOR;
                    }
                }
            }
        }

    }



    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexTile = new TETile[WIDTH][HEIGHT];
        fillTheGridWithEmptySpace(hexTile);
        addARoom(hexTile,10,8,new byow.Core.Position(10,0));
        //generateHexagons(hexTile,size,getAllPositions(size,new Position(0,10)));



        ter.renderFrame(hexTile);
    }
}
