#include <AFMotor.h>

AF_DCMotor Motor1(3);
AF_DCMotor Motor2(4);

void setup()
{
}

void loop()
{
    Motor1.setSpeed(255);
    Motor2.setSpeed(255);
    Motor1.run(FORWARD);
    Motor2.run(FORWARD); 
    delay(1000);
    Motor1.run(BACKWARD);
    Motor2.run(BACKWARD);
    delay(1000);
    Motor1.run(FORWARD);
    Motor2.run(BACKWARD);
    delay(1000);
    Motor1.run(BACKWARD);
    Motor2.run(FORWARD);
    delay(1000);
    Motor1.setSpeed(0);
    Motor2.setSpeed(0);
    Motor1.run(BRAKE);
    Motor2.run(BRAKE);
    delay(1000);
}
