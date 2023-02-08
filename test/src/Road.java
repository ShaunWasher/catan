public class Road {
    private Player owner;
    private Settlement settlementA;
    private Settlement settlementB;
    public Road(Settlement setA, Settlement setB){
        owner = null;
        settlementA = setA;
        settlementB = setB;
    }

    public Player getOwner() {
        return owner;
    }

    public Settlement getSettlementA() {
        return settlementA;
    }

    public Settlement getSettlementB() {
        return settlementB;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
