package exercises;

import robot.ExecutionController;
import robot.SunriseConnector;
import utility.DataHandler;
import utility.FileLogger;
import utility.SingleInstanceChecker;
import utility.FileLogger.Fields;

import java.util.Vector;

import com.kuka.roboticsAPI.RoboticsAPIContext;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.motionModel.BasicMotions;
import com.kuka.roboticsAPI.motionModel.MotionBatch;
import com.kuka.roboticsAPI.motionModel.RobotMotion;

public class Task_03_ElbowConfiguration extends RoboticsAPIApplication {
	
	public Task_03_ElbowConfiguration(RoboticsAPIContext context){
		super(context); 
	}

	public static void main(String[] args) {
		RoboticsAPIContext.useGracefulInitialization(true);

		// check if another robot application is already running
		new SingleInstanceChecker().start();
		
		// initialization
		Task_03_ElbowConfiguration app = new Task_03_ElbowConfiguration(RoboticsAPIContext.createFromResource(Task_04_JointAndTaskSpace.class, "RoboticsAPI.config.xml")); 
		SunriseConnector.initialize(app);

		app.run();
		
		ExecutionController.waitForAllMotionsFinished();
	}

	LBR robot;
	Tool tool;

	@Override
	public void run() {
		robot = SunriseConnector.getRobot();
		tool = SunriseConnector.getTool();
		System.out.println("Starting " + this.getClass().getSimpleName());

		double blendingRadiusCart = 10;
		double jointVelocity = 0.2;

		
		
		/** ----- create a motion batch with PTP movements to joint positions ----- */
		/** add Cartesian blending with 10 mm and set joint velocity to 0.2  */
		 Frame F0 = DataHandler.loadFrame("F2");
		
		 Vector<RobotMotion<?>> motionVector = new Vector<RobotMotion<?>>();
		 motionVector.add(BasicMotions.lin(F0));
		 RobotMotion<?>[] motionArray = motionVector.toArray(new RobotMotion<?>[motionVector.size()]);
		 MotionBatch mb = new MotionBatch(motionArray);
		 JointPosition J0 = DataHandler.loadJointPos("J0");
		

		/** use a sync move to the first (joint-)position, then start logging and execute the motion batch */
		tool.move(BasicMotions.ptp(J0));
		FileLogger.startLogging("log_PTP_jp", Fields.TIME, Fields.TRANSLATIONAL_DISTANCE, Fields.ROTATIONAL_DISTANCE,
				Fields.JOINT_SPACE_DISTANCE);
		tool.move(mb);
		FileLogger.stopLogging();
		 
		
		

		/** ----- create a motion batch with PTP movements to frames ----- */
		/** add Cartesian blending with 10 mm and set joint velocity to 0.2 */
		
		
		
		
		

		/** ----- create a motion batch with LIN movements to frames ----- */
		/** add Cartesian blending with 10 mm and set joint velocity to 0.2 */
		
		
		
		

		/** wait until movements are finished [if there are async movements] */
		ExecutionController.waitForAllMotionsFinished();
		System.exit(0);
	}
	

}