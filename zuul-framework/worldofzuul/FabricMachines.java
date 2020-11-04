package worldofzuul;

public class FabricMachines extends Item {
    private boolean hasWater;
    private boolean hasPlant;
    private boolean hasChemical;
    public boolean readyToProduce;
    private String hempRecipe;

    public FabricMachines(String hempRecipe)
    {
        this.hempRecipe = hempRecipe;
    }

    public String setHempRecipe()
    {
        return hempRecipe;
    }


    public boolean readyToProduce() {return readyToProduce;}



    //hasPlant skal have korrekt state som requirement
//    public void setReadyToProduce() {
//        if (hasWater && hasPlant && hasChemical) {
//            this.readyToProduce = true;
//        } else if (hasWater && hasPlant && !hasChemical) {
//            System.out.println("You need chemicals");
//        } else if (hasWater && !hasPlant && hasChemical) {
//            System.out.println("You need a plant");
//        } else if (!hasWater && hasPlant && hasChemical) {
//            System.out.println("You need water");
//        } else if (hasWater && !hasPlant && !hasChemical) {
//            System.out.println("You need chemicals and a plant");
//        } else if (!hasWater && hasPlant && !hasChemical) {
//            System.out.println("You need chemicals and water");
//        } else if (!hasWater && !hasPlant && hasChemical) {
//            System.out.println("You need water and a plant");
//        } else {
//            System.out.println("You need a plant, water and chemicals. Try again :)))");
//        }
//    }
}
