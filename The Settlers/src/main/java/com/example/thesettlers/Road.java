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

    public Settlement getNextSettlement(Settlement startingSettlement){
        if(startingSettlement == settlementA){
            return settlementB;
        }
        else if(startingSettlement == settlementB){
            return settlementA;
        }
        throw new Exception();

    }
}
