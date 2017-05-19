package gui;

import jason.asSyntax.Literal;
import jason.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;


public class View extends JFrame {
    JPanel uphalf;
    JButton carinbtn;
    JButton caroutbtn;
    JLabel carplatel;
    JTextField carplatetf;
    JLabel freespacel;
    JLabel textfreespacel;
    JPanel downhalf;
    JLabel spacel;
    JComboBox carplatecb;
    JComboBox happeningcb;
    JButton notifbtn;
    JButton emergencybtn;
    JButton floodbtn;
    JButton firebtn;
    DefaultComboBoxModel model;
    ArrayList<Car> cars;
    int[] places;
    String[] problems = {"lopas", "muszaki"};
    Environment environment;

    int weirdthingplace;
    String weirdthing;
    Car problem;

    public View(Environment env, int free) {
        initComponents();
        environment = env;
        places = new int[free];
    }

    public void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //we need this
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inituphalf();
        initdownhalf();
        add(uphalf);
        add(downhalf);
        pack();
        setVisible(true);
    }

    private class CarinbtnActLis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (carplatetf.getText().length() > 0) {
                environment.addPercept(Literal.parseLiteral("car_in"));
            }
        }
    }

    private class CaroutbtnActLis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (carplatetf.getText().length() > 0) {
                environment.addPercept(Literal.parseLiteral("car_out"));
            }
        }
    }

    private class NotifActLis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String car = (String) carplatecb.getSelectedItem();
            String prob = (String) happeningcb.getSelectedItem();
            if ((car != null) && (prob != null)) {
                weirdthingplace = Integer.parseInt(car.split(" ")[0]);
                weirdthing = prob;
                environment.addPercept(Literal.parseLiteral("weird_thing"));
            }
        }
    }

    public int getWeirdthingplace() {
        return weirdthingplace;
    }

    public void getCarbyplace(int place){
        for(int i = 0; i < cars.size(); i++){
            if(cars.get(i).getPlace() == place){
                problem = cars.get(i);
            }
        }
    }

    public Hashtable<String, String> getCarinfo(){
        Hashtable<String,String> result = new Hashtable<>();
        result.put("name", problem.getName());
        result.put("email", problem.getEmail());
        result.put("number", String.valueOf(problem.getNumber()));
        result.put("thing", weirdthing);
        return result;
    }

    private class EmergencyActLis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            environment.addPercept(Literal.parseLiteral("emergency"));
        }
    }

    private class FloodActLis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            environment.addPercept(Literal.parseLiteral("flood"));
        }
    }

    private class FireActLis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            environment.addPercept(Literal.parseLiteral("fire"));
        }
    }

    public void AddActualCarplate() {
        AddCarplate(carplatetf.getText());
    }

    public void AddCarplate(String car) {
        Car temp = new Car(car);
        temp.setPlace(getfreespace());
        cars.add(temp);
        refreshplatelist();
    }

    private void refreshplatelist() {
        model = new DefaultComboBoxModel(carplatesgen().toArray(new String[]{}));
        carplatecb.setModel(model);
    }

    public boolean isValidCarplateInput() {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < cars.size(); i++) {
            temp.add(cars.get(i).getCarplate());
        }
        return temp.contains(carplatetf.getText());
    }

    public void RemoveCarplate() {
        String car = carplatetf.getText();
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < cars.size(); i++) {
            temp.add(cars.get(i).getCarplate());
        }
        int index = temp.indexOf(car);
        places[cars.get(index).getPlace()] = 0;
        cars.remove(index);
        refreshplatelist();
    }

    private ArrayList<String> carplatesgen() {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < cars.size(); i++) {
            temp.add(cars.get(i).stringify());
        }
        return temp;
    }

    private int getfreespace() {
        int i = 0;
        while (places[i] != 0) {
            i++;
        }
        places[i] = 1;
        return i;
    }

    public int sizeofcars(){
        return cars.size();
    }

    public String getcaremail(int index){
        Car temp = cars.get(index);
        String result = new String(temp.getEmail());
        return result;
    }

    public String getcarnumber(int index){
        Car temp = cars.get(index);
        String result = new String(String.valueOf(temp.getNumber()));
        return result;
    }

    public void SetFreespaces(int spaces) {
        textfreespacel.setText(String.valueOf(spaces));
    }

    private void initdownhalf() {
        downhalf = new JPanel();
        spacel = new JLabel("Hely:");
        cars = new ArrayList<Car>();
        model = new DefaultComboBoxModel(carplatesgen().toArray(new String[]{}));
        carplatecb = new JComboBox();
        happeningcb = new JComboBox(problems);
        notifbtn = new JButton("Ertesites");
        emergencybtn = new JButton("Veszhelyezet");
        floodbtn = new JButton("Magas viz");
        firebtn = new JButton("Tuz");

        notifbtn.addActionListener(new NotifActLis());
        emergencybtn.addActionListener(new EmergencyActLis());
        floodbtn.addActionListener(new FloodActLis());
        firebtn.addActionListener(new FireActLis());

        downhalf.setLayout(new FlowLayout());
        downhalf.add(spacel);
        downhalf.add(carplatecb);
        downhalf.add(happeningcb);
        downhalf.add(notifbtn);
        downhalf.add(emergencybtn);
        downhalf.add(floodbtn);
        downhalf.add(firebtn);
    }

    private void inituphalf() {
        uphalf = new JPanel();
        carinbtn = new JButton("Auto be");
        caroutbtn = new JButton("Auto ki");
        carplatel = new JLabel("Rendszam:");
        carplatetf = new JTextField();
        carplatetf.setColumns(10);
        freespacel = new JLabel("Szabad helyek szama: ");
        textfreespacel = new JLabel();
        textfreespacel.setText("     ");

        carinbtn.addActionListener(new CarinbtnActLis());
        caroutbtn.addActionListener(new CaroutbtnActLis());

        uphalf.setLayout(new FlowLayout());
        uphalf.add(carinbtn);
        uphalf.add(caroutbtn);
        uphalf.add(carplatel);
        uphalf.add(carplatetf);
        uphalf.add(freespacel);
        uphalf.add(textfreespacel);
    }
}
