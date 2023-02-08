public class Tile {
    private ResourceType resourceType;
    private int value;
    private boolean robber;
    public Tile(ResourceType type, int val){
        resourceType = type;
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

    public ResourceType getTileType() {
        return resourceType;
    }
}
