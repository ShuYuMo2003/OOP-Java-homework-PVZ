package homework;

// Class to hold an array of image paths and current frame ID
class ImagesAndFrameId {
    public String[] imagesPaths;  // Array to store paths of images
    public int frameId;  // Current frame ID

    // Constructor to initialize with an array of image paths
    ImagesAndFrameId(String[] imagesPaths) {
        this.imagesPaths = imagesPaths;
        frameId = 0;  // Initialize frame ID to 0
    }

    // Method to set the next frame ID (circular fashion)
    public void setNextFrame() {
        frameId = (frameId + 1) % imagesPaths.length;  // Increment frame ID and wrap around
    }

    // Method to retrieve the current frame's path
    public String currentFramePath(){
        return imagesPaths[frameId];  // Return the path of the current frame
    }
}

// Main class representing a stage of a game character (Zombine)
public class ZombineStage {
    public String stageName;  // Name of the stage (verbose only)
    private ImagesAndFrameId normalImagesPaths;  // Object holding paths for normal state frames
    private ImagesAndFrameId attackImagesPaths;  // Object holding paths for attack state frames
    public double health;  // Health of the stage

    // Constructor to initialize the stage with its properties
    ZombineStage(String stageName, String[] normalImagesPaths, String[] attackImagesPaths, int health) {
        this.stageName = stageName;  // Initialize stage name
        // Create ImagesAndFrameId objects for normal and attack images with their respective paths
        this.normalImagesPaths = new ImagesAndFrameId(normalImagesPaths);
        this.attackImagesPaths = new ImagesAndFrameId(attackImagesPaths);
        this.health = health;  // Initialize health
    }

    // Method to decrease health by a specified amount
    public void minusHealth(int damage) {
        this.health -= damage;  // Reduce health by the given damage amount
    }

    // Method to check if the stage is dead (health less than or equal to 0)
    public boolean isDie() {
        return health <= 0;  // Return true if health is less than or equal to 0, indicating death
    }

    // Method to apply an attack and reduce health
    public void applyAttack(double damage) {
        // Debugging output if Constants.Debug is true
        if (Constants.Debug) {
            System.err.println("On Stage: `" + stageName + "` get damage: " + damage +
                    " health: " + health + " -> " + (health - damage));
        }
        health -= damage;  // Reduce health by the given damage amount
    }

    // Method to retrieve the path of the current frame for either normal or attack state
    public String getFramePath(boolean isNormal) {
        if (isNormal) {
            normalImagesPaths.setNextFrame();  // Set the next frame for normal state
            return normalImagesPaths.currentFramePath();  // Return the path of the current normal frame
        } else {
            attackImagesPaths.setNextFrame();  // Set the next frame for attack state
            return attackImagesPaths.currentFramePath();  // Return the path of the current attack frame
        }
    }
}
