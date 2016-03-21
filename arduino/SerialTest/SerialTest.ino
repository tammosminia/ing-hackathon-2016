#include <Wire.h>
//The library, in combination with arduino IDE 1.6.7 has a bug. 
//When using lcd.print, it prints only the first letter
#include <LiquidCrystal_I2C.h>

//schematic:
//lcd(with I2C) on sda, scl, +, -

LiquidCrystal_I2C lcd(0x27, 16, 2);

void printLcd(int x, int y, String s) {
  Serial.println("printing" + s);
  for (int index = 0; index < s.length(); ++index) {
    lcd.setCursor(x + index, y);
    lcd.print(s[index]);
  }
}

String text = "";

// the setup routine runs once when you press reset:
void setup() {
  // initialize serial communication at 9600 bits per second:
  Serial.begin(9600);
  lcd.init();                      // initialize the lcd 
  lcd.backlight();
//  printLcd(0, 0, "starting serialtest");
}

// the loop routine runs over and over again forever:
void loop() {
  if (Serial.available()) {
    String input = Serial.readString();
    lcd.clear();
    printLcd(0, 0, text);
    printLcd(0, 1, input);
    text = input;
  }

  Serial.println("hallo");

  delay(100);        // delay in between reads for stability
}

