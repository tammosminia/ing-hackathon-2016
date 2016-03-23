#include <AFMotor.h>

AF_DCMotor leftMotor(3);
AF_DCMotor rightMotor(4);

void setup() {
    Serial.begin(9600);
    
}

void loop() {
  if (Serial.available()) {
    String input = Serial.readString();
    if (input == "langzaam") {
      leftMotor.setSpeed(100);
      rightMotor.setSpeed(100);
      leftMotor.run(FORWARD);
      rightMotor.run(FORWARD);
    } else if (input == "snel") {
      leftMotor.setSpeed(255);
      rightMotor.setSpeed(255);
      leftMotor.run(FORWARD);
      rightMotor.run(FORWARD);
    } else if (input == "stop") {
      leftMotor.setSpeed(0);
      rightMotor.setSpeed(0);
      leftMotor.run(BRAKE);
      rightMotor.run(BRAKE);
    } else if (input == "links") {
      leftMotor.setSpeed(255);
      rightMotor.setSpeed(255);
      leftMotor.run(FORWARD);
      rightMotor.run(BACKWARD);
    } else if (input == "rechts") {
      leftMotor.setSpeed(255);
      rightMotor.setSpeed(255);
      leftMotor.run(BACKWARD);
      rightMotor.run(FORWARD);
    }
  }

}
