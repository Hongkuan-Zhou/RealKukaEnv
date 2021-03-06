package Controller;

import com.kuka.roboticsAPI.RoboticsAPIContext;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Tool;

import robot.ExecutionController;
import robot.SunriseConnector;
import utility.SingleInstanceChecker;


public class Controller {
    
	private SmartServoLINMotions app = null;
	public Controller(double [] startPosition) {
		RoboticsAPIContext.useGracefulInitialization(true);

		// check if another robot application is already running
		new SingleInstanceChecker().start();

		// initialization
		app = new SmartServoLINMotions(RoboticsAPIContext.createFromResource(SmartServoLINMotions.class, "RoboticsAPI.config.xml"));
		app.setStartPosition(startPosition);
		app.initialize();
		app.run();
	}
	
	
	public double [] getCurrentJoints() {
		return app.getCurrentJointsPosition();
	}
	
	public double [] getCurrentFrame() {
		return app.getCurrentPosition();
	}
	
	
	public double [] getCurrentFrameVelocity() {
		return app.getCurrentVelocity();
	}
	
	public void setAction(double [] action) {
		app.setAction(action);
	}


	public void step() {
		app.step();
	}
	public void resetInitialPosition() {
		app.resetInitialPosition();
	}
	
	public void dispose() {
		app.dispose();
		System.exit(0);
	}
	public String path() {
		return this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	}
	public static void print(double [] x) {
		for (int i = 0; i < x.length; i++) {
			System.out.print(x[i]+ " ");
		}
		System.out.println();
		
	}
    public static void main (String [] args) {
    	Controller c = new Controller(new double [] {0.0, 0.712, 0.0, -1.26, 0.0, 1.17, 0.0});
    	print(c.getCurrentFrame());
    	c.resetInitialPosition();
    	c.dispose();
    }


}
