package homework;

class ImagesAndFrameId {
    public String[] imagesPaths;
    public int frameId;
    ImagesAndFrameId(String[] imagesPaths) {
        this.imagesPaths = imagesPaths;
        frameId = 0;
    }
    public void setNextFrame() {
        frameId = (frameId + 1) % imagesPaths.length;
    }
    public String currentFramePath(){
        return imagesPaths[frameId];
    }
}

public class ZombineStage {
    // `stageName` for verbox only.
    public String stageName;
    private ImagesAndFrameId normalImagesPaths;
    private ImagesAndFrameId attackImagesPaths;
    public double health;
    ZombineStage(String stageName, String[] normalImagesPaths, String[] attackImagesPaths, int health) {
        this.stageName = stageName;
        this.normalImagesPaths = new ImagesAndFrameId(normalImagesPaths);
        this.attackImagesPaths = new ImagesAndFrameId(attackImagesPaths);
        this.health = health;
    }
    public void minusHealth(int damage) { this.health -= damage; }
    public boolean isDie() { return health <= 0; }
    public void applyAttack(double damage) {
        if(Constants.Debug)
            System.err.println("On Stage: `" + stageName + "` get damage: " + damage + " health: " + health + " -> " + (health - damage));
        health -= damage;
    }
    public String getFramePath(boolean isNormal) {
        if (isNormal) {
            normalImagesPaths.setNextFrame();
            return normalImagesPaths.currentFramePath();
        } else {
            attackImagesPaths.setNextFrame();
            return attackImagesPaths.currentFramePath();
        }
    }


}
