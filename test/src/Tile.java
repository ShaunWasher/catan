public class Tile {
    private TileType tileType;
    private int value;
    private boolean robber;
    public Tile(TileType type, int val){
        tileType = type;
        value = val;
        robber = false;
    }

    public void setRobber(boolean robber) {
        this.robber = robber;
    }

    public boolean getRobber() {
        return robber;
    }

    public int getValue() {
        return value;
    }

    public TileType getTileType() {
        return tileType;
    }
}
