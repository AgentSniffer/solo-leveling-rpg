// Dont include this on cascade this is just for testing

class Vehicle {
    String brand = "Ford";
    public void honk() {               
    System.out.println("Tuut, tuut!");
  }
}

class test extends Vehicle {
    private String modelName = "Mustang";

    public static void main(String[] args) {
        test myCar = new test();
        myCar.honk();
        System.out.println(myCar.brand + " " + myCar.modelName);
    }     
}
