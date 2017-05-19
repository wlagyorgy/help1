import gui.View;
import jason.asSyntax.*;

import jason.environment.*;

import java.util.Hashtable;
import java.util.logging.*;

public class mainEnv extends Environment {
    int free_spaces = 100;
    private Logger logger = Logger.getLogger("ier_hf.mas2j." + mainEnv.class.getName());
    View display = new View(this, free_spaces);
    int WeirdthingPlace;

    @Override
    public void init(String[] args) {
        super.init(args);
        display.SetFreespaces(free_spaces);
    }

    @Override
    public boolean executeAction(String agName, Structure action) {

        if (action.getFunctor().equals("let_in")) {
            addPercept(Literal.parseLiteral("new_car"));

        } else if (action.getFunctor().equals("let_out")) {
            addPercept(Literal.parseLiteral("delete_car"));

        } else if (action.getFunctor().equals("let_in_asap")) {
            addPercept(Literal.parseLiteral("accident"));

        } else if (action.getFunctor().equals("open_gates")) {
            logger.info("Free to move for the emergency");
            clearAllPercepts();

        } else if (action.getFunctor().equals("get_car_info")) {
            logger.info("Problem at " + String.valueOf(display.getWeirdthingplace()));
            addPercept(Literal.parseLiteral("ask_info"));

        } else if (action.getFunctor().equals("send_info")) {
            logger.info("Send car info");
            display.getCarbyplace(display.getWeirdthingplace());
            addPercept(Literal.parseLiteral("info"));

        } else if (action.getFunctor().equals("send_alarm")) {
            Hashtable<String, String> problem = display.getCarinfo();
            logger.info("Notify: " + problem.get("name") + " Call: " + problem.get("number") + " Send email: " +
            problem.get("email") + " For: " + problem.get("thing"));
            clearAllPercepts();

        } else if (action.getFunctor().equals("add_new_car")) {
            if (free_spaces > 0) {
                free_spaces--;
                display.AddActualCarplate();
                display.SetFreespaces(free_spaces);
                logger.info("Car added");
            } else {
                logger.info("No more free space");
            }
            clearAllPercepts();

        } else if (action.getFunctor().equals("free_car")) {
            if (display.isValidCarplateInput()) {
                free_spaces++;
                logger.info("Valid car plate");
                display.RemoveCarplate();
                display.SetFreespaces(free_spaces);
            } else {
                logger.info("Invalid car plate");
            }
            clearAllPercepts();

        } else if (action.getFunctor().equals("notify_all_flood")) {
            logger.info("Notify all");
            for (int i = 0; i < display.sizeofcars(); i++) {
                logger.info("Send email to: " + display.getcaremail(i) + " and send SMS to: " + display.getcarnumber(i));
            }
            clearAllPercepts();

        } else if (action.getFunctor().equals("notify_all_fire")) {
            logger.info("Notify all");
            for (int i = 0; i < display.sizeofcars(); i++) {
                logger.info("Send email to: " + display.getcaremail(i) + " and send SMS to: " + display.getcarnumber(i));
            }
            clearAllPercepts();

        } else {
            logger.info("executing: " + action + ", but not implemented!");
            return false;
        }
        return true;
    }

    @Override
    public void stop() {
        super.stop();
    }
}


