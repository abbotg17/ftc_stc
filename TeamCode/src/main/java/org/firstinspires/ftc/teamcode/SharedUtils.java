package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by gta on 12/4/16.
 * An attempt at making a class where the method flipper() can be shared.
 */

public class SharedUtils {
    
    public NewHardwareRegister hw;

    public newdrivercontrolled_mod dr = new newdrivercontrolled_mod();
    
    public SharedUtils(NewHardwareRegister inHw) {
        hw = inHw;
    }
    





    public void flipper(double speed, double rotations, double timeoutS)
            throws InterruptedException
    {
        int newTarget;

        // Ensure that the opmode is still active
        if (dr.opModeIsActive())
        {
            // Determine new target position, and pass to motor controller
            //newLeftTarget = hw.launchMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            //newRightTarget = hw.sweeperMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newTarget = hw.launchMotor.getCurrentPosition() + (int)(4*rotations * dr.COUNTS_PER_MOTOR_REV);
            hw.launchMotor.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            hw.launchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            dr.runtime.reset();
            hw.launchMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and the motor is running.
            while (dr.opModeIsActive() &&
                    (dr.runtime.seconds() < timeoutS) &&
                    hw.launchMotor.isBusy())
            {

                // Display data on the driver station.
                dr.telemetry.addData("newTarget", "Running to %7d",newTarget);
                dr.telemetry.addData("getCurrentPosition", "Running at %7d", hw.launchMotor.getCurrentPosition());
                dr.telemetry.update();

                // Allow time for other processes to run.
                dr.idle();
            }

            // Stop all motion;
            hw.launchMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            hw.launchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // optional pause after each move
            dr.sleep(250);
        }
    }
}
