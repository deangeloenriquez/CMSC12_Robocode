package CMSC12_2020_ENRIQUEZ.src;
import robocode.*;
import java.awt.Color;

/**
 * CarBot_Dalisay - a robot by Deangelo M. Enriquez
 */

public class CarBot_Dalisay extends AdvancedRobot
{
	static double galaw;
	static double distansya;
	static String kalaban;

	private Color Kulay(int r, int g, int b) {
		return new Color(r, g, b);
	}

	public void run() {

		setBodyColor(Kulay(40,40,40));
		setGunColor(Kulay(175,12,21));
		setRadarColor(Kulay(13,53,128));
		setScanColor(Color.red);

		galaw = Double.MAX_VALUE;

		setAdjustGunForRobotTurn(true);
		onRobotDeath(null);

		turnRadarRightRadians(Double.MAX_VALUE);

		execute();
	}

	public void onScannedRobot(ScannedRobotEvent e) {

		double kalabanDistansya = e.getDistance();

		if(getDistanceRemaining() == 0){

			setAhead(galaw = -galaw);
			setTurnRightRadians(90);
		}

		if(distansya > kalabanDistansya){

			distansya = kalabanDistansya;
			kalaban = e.getName();

			out.println("Lagot ka tha akin " + kalaban);
		}

		if(kalaban.equals(e.getName())){

			if(getGunHeat() < 1 && kalabanDistansya < 600){

				if(getGunHeat() == getGunTurnRemaining()) {
					setFireBullet(getEnergy() * 15 / kalabanDistansya);
					onRobotDeath(null);
				}

				setTurnRadarLeft(getRadarTurnRemaining());
			}

			kalabanDistansya = e.getBearingRadians() + getHeadingRadians();
			setTurnGunRightRadians(robocode.util.Utils.normalRelativeAngle(kalabanDistansya - getGunHeadingRadians() + (1 - e.getDistance() / 600) * Math.asin(e.getVelocity() / 11) * Math.sin(e.getHeadingRadians() - kalabanDistansya)));
		}

		execute();
	}

	public void onHitWall(HitWallEvent e) {

		out.println("Aray! Ang thaket ng pader!");
		//back(100);
		//setTurnRight(90);
		if(Math.abs(galaw) > 200){
			galaw = 200;
		}
		execute();
	}

	public void onRobotDeath(RobotDeathEvent e){

		distansya = Double.MAX_VALUE;
		execute();
	}

}
