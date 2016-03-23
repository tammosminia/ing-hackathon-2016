
int firstPin = 3;
int lastPin = 11;

void setup() {
  Serial.begin(9600);
  for(int t=firstPin; t<=lastPin; t++) {
    pinMode(t, INPUT);  
  }
}

int currentPin() {
  for(int t=firstPin; t<=lastPin; t++) {
    int state = digitalRead(t);  
    if (state == 1) {
      return t;
    }
  }  
  return 0;
}

int lowestPin = lastPin;
int previousPin = lastPin;

void loop() {
  int current = currentPin();
  if (previousPin != current) {
    Serial.println(current);
    previousPin = current;  
  }
  
  if (current == 0) {
    
  } else if (current < lowestPin) {
    lowestPin = current;
  } else if((current == lastPin) && (lowestPin < lastPin)) {
    Serial.print("coin found. lowest pin=");
    Serial.print(lowestPin);
    Serial.println("");
    lowestPin = lastPin;
  }
}

