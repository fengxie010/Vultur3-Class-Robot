# Vultur3-Class-Robot
First place baby
package cghernandez_fxie;

import java.awt.Color;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Vultur3 extends AdvancedRobot {
    boolean movingForward;

    public Vultur3() {
    }

    public void run() {
        this.setColors(Color.black, Color.black, Color.black);
        this.setBulletColor(Color.red);
        this.setAdjustRadarForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);
        this.setAdjustGunForRobotTurn(true);
        this.setAhead(10000.0D);
        this.setTurnRadarLeft(360.0D);
        boolean var1 = true;

        while(true) {
            this.execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double targetDistance = e.getDistance();
        double targetDirection = this.getHeading() + e.getBearing();
        double directionFromGun = Utils.normalRelativeAngleDegrees(targetDirection - this.getGunHeading());
        double directionFromRadar = Utils.normalRelativeAngleDegrees(targetDirection - this.getRadarHeading());
        if (this.movingForward) {
            this.setTurnRight(Utils.normalRelativeAngleDegrees(e.getBearing() + 80.0D));
        } else {
            this.setTurnRight(Utils.normalRelativeAngleDegrees(e.getBearing() + 100.0D));
        }

        if (Math.abs(directionFromGun) <= 5.0D) {
            this.setTurnGunRight(directionFromGun);
            this.setTurnRadarRight(directionFromRadar);
            if (targetDistance > 200.0D && targetDistance <= 500.0D) {
                this.fire(3.0D);
            } else if (targetDistance < 200.0D) {
                this.fire(1.0D);
            }
        } else {
            this.setTurnGunRight(directionFromGun);
            this.setTurnRadarRight(directionFromRadar);
        }

    }

    public void onHitByBullet(HitByBulletEvent e) {
    }

    public void onHitWall(HitWallEvent e) {
        if (this.movingForward) {
            this.setBack(10000.0D);
            this.movingForward = false;
        } else {
            this.setAhead(10000.0D);
            this.movingForward = true;
        }

    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            if (this.movingForward) {
                this.setBack(10000.0D);
                this.movingForward = false;
            } else {
                this.setAhead(10000.0D);
                this.movingForward = true;
            }
        }

    }
}
