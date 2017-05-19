package gui;

import java.util.Random;

public class Car {
    String carplate;
    String name;
    int place;
    int number;
    String email;

    public Car(String plate) {
        carplate = new String(plate);
        name = new String(generateName());
        email = new String(generateemail());
        generatenumber();
    }

    private static String[] Beginning = {"Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk"};
    private static String[] Middle = {"air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak"};
    private static String[] End = {"d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur"};

    private static Random rand = new Random();

    private static String generateName() {

        return Beginning[rand.nextInt(Beginning.length)] + Middle[rand.nextInt(Middle.length)] +
                End[rand.nextInt(End.length)];

    }

    private String generateemail() {
        return name + "." + generateName() + "@gmail.com";
    }

    private void generatenumber() {
        for (int i = 0; i < 8; i++) {
            number = (number + rand.nextInt(9) + 1) * 10;
        }
        number = number + rand.nextInt(9) + 1;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getCarplate() {
        return carplate;
    }

    public String stringify(){
        return String.valueOf(place) + " (rsz: " + carplate + ")";
    }

    public int getPlace() {
        return place;
    }

    public String getEmail() {
        return email;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
