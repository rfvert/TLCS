package by.bsuir.tlcs.applet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;
import by.bsuir.tlcs.main.Runner;

/**
 * Class for creation car on the 13th lane. Lane for pedestrians. Connecting to
 * the behavior in graphics aspect car == pedestrian. date: April, 2012
 * 
 * @version 1.0
 * @see Car
 * @see Observer
 * @see Runner
 * @see Applet
 * 
 */
public class CarNumberThirteen extends Applet implements Car, Observer {

	private boolean arrivedDistance = true;
	private boolean move;
	private static final long serialVersionUID = 1L;
	private int coordX = 438;
	private int coordY = 459;
	private Image picture, firstCar;
	private Lane curentLane;

	/**
	 * Constructor for class
	 * 
	 * @param currentLane
	 */
	public CarNumberThirteen(Lane currentLane) {
		this.curentLane = currentLane;
		currentLane.getTrafficLight().addObserver(this);
		init(Runner.getImages());
		move = true;
	}

	/**
	 * Method from interface Observer.
	 * 
	 * @see Observer
	 */
	public void update(Observable obs, Object arg) {
		move(true);
	}

	public int getCoordX() {
		return coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setArrivedDistance(boolean choice) {
		this.arrivedDistance = choice;
	}

	public void move(boolean choice) {
		this.move = choice;
	}

	public void init(List<Image> images) {
		this.picture = images.get(0);
		this.firstCar = images.get(7);
	}

	public void draw(Graphics g) {

		if (move) {

			Graphics2D g2 = (Graphics2D) g;

			if ((Runner.getListOfCars()[13].size() >= 2)
					& (Runner.getListOfCars()[13].indexOf(this) != 0)) {
				if (((Runner.getListOfCars()[13].get(Runner.getListOfCars()[13]
						.indexOf(this) - 1)).getCoordY() + 40) > coordY) {
					Runner.getListOfCars()[13].get(
							Runner.getListOfCars()[13].indexOf(this))
							.setArrivedDistance(false);
				} else {
					Runner.getListOfCars()[13].get(
							Runner.getListOfCars()[13].indexOf(this))
							.setArrivedDistance(true);
				}
			}

			if (arrivedDistance) {

				if (coordY == 378) {
					curentLane.getCarDetector().push();
				}

				if (coordY == 340) {
					if (curentLane.getTrafficLight().getColor() == TrafficLightColor.RED
							|| curentLane.getTrafficLight().getColor() == TrafficLightColor.AMBER) {
						this.move(false);
					}
				}

				g2.drawImage(firstCar, coordX, coordY, this);
				g2.clipRect(coordX, coordY + 20, 20, 1);
				paint(g2);

				coordY -= 1;

				if (coordY == -21) {
					curentLane.getTrafficLight().deleteObserver(this);
					Runner.getListOfCars()[13]
							.remove(Runner.getListOfCars()[13].indexOf(this));
				}
			}
		}
	}

	public void paint(Graphics g) {
		g.drawImage(picture, 0, 0, this);
	}
}