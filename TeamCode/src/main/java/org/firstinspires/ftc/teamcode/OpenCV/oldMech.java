package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "oldMech", group = "hugo")

public class oldMech extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();

    //Creating variable motor
    private DcMotor leftFrontMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor rightFrontMotor = null;
    private DcMotor rightBackMotor = null;


    @Override

    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        leftFrontMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        leftBackMotor = hardwareMap.get(DcMotor.class, "backLeft");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "frontRight");
        rightBackMotor = hardwareMap.get(DcMotor.class, "backRight");


        // ########################################################################################
        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to drive forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);

        //private ElapsedTime runtime = new ElapsedTime();
        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double horizontal = gamepad1.left_stick_x;  // Note: pushing stick forward gives negative value
            double pivot = gamepad1.right_stick_x;
            //double close = gamepad1.right_trigger;
            boolean open = gamepad1.right_bumper;
            boolean down = gamepad1.dpad_down;
            boolean up = gamepad1.dpad_up;
            double vertical = gamepad1.left_stick_y;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.

            double leftFrontPower = (-pivot+(vertical-horizontal));
            double rightFrontPower = (pivot+vertical +horizontal);
            double leftBackPower = (-pivot+vertical+horizontal);
            double rightBackPower = (pivot+(vertical -horizontal));
            boolean clawPower =  open;
            //claw
            //direction reverse
            //claw.setDirection(Servo.Direction.REVERSE);

//            telemetry.addData("location",claw.getPosition());
//            if(open)
//            {
//                claw.setPosition(0.35);
//                //claw.setPosition(10) was last
//                telemetry.addData("in",clawPower);
//            }
//            else
//            {
//
//                claw.setPosition(0.070);
//            }
            //slide
//            double slidePower = 0;
//            if(down)
//            {
//                slidePower += 1;
//
//            }
//            else if(up)
//            {
//                slidePower -=1;
//            }
//            else
//            {
//                slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//                slide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            }


            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.

            /*
            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            */

            // Send calculated power to wheels
            leftFrontMotor.setPower(leftFrontPower);
            rightFrontMotor.setPower(rightFrontPower);
            leftBackMotor.setPower(leftBackPower);
            rightBackMotor.setPower(rightBackPower);
//            slide.setPower(slidePower);
//            slide2.setPower(slidePower);
            //claw.setPower(turnLeftPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.update();
            idle();
        }
    }
}

