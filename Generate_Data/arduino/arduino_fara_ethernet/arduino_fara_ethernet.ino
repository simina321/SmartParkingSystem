/*
 Circuit:
 * Ethernet shield attached to pins 10, 11, 12, 13
 * Analog inputs attached to pins A0 through A5 (optional)
 */
#include <SPI.h>
#include <Ethernet.h>
#include <Servo.h>
#include <SoftwareSerial.h>
//ip meu:169,254,158,161
//ip PC:169.254.130.140
IPAddress ip(169,254,130,140);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 0, 0);
// telnet defaults to port 23
EthernetServer server(137);
SoftwareSerial Bluetooth(2, 3); // RX, TX
Servo servo;
#define DEBUG_MODE_ON 1
#define RGB_LED_RED_PIN 5
#define RGB_LED_GREEN_PIN 4
#define RGB_LED_BLUE_PIN 6
#define TRIGGER_PIN 2
#define ECHO_PIN 3
#define SERVO_PIN 9
byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};
String Data; // the data received
int incomingByte = 0; 
long duration, distance;
char thisChar;
boolean alreadyConnected = false; // whether or not the client was connected previously
boolean barrierPresence = false;
boolean openingStateFront = false;
boolean openingStateBack =  false;
boolean backToFront = false;
int ledStateMachine = 1; // 0=inceput,1=conectat ethernet(yellow),2= cod admis(verde),3=poarta(albastru),4=cod respins(rosu)
void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(RGB_LED_RED_PIN, redValue);
  analogWrite(RGB_LED_GREEN_PIN, greenValue);
  analogWrite(RGB_LED_BLUE_PIN, blueValue);
}
void ultrasonicSignal(){
  digitalWrite(TRIGGER_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIGGER_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER_PIN, LOW);
  duration = pulseIn(ECHO_PIN, HIGH);
  distance = (duration/2) / 29.1;
}
void ledStateMachineFunction(){
  switch(ledStateMachine){
    case 1:{
      //1=conectat ethernet(yellow)
      setColor(255, 255, 0);//yellow
      break;
    }
    case 2:{
      //2= cod admis(verde)
      setColor(0, 255, 0); //green
      break;
    }
    case 3:{
      //3=poarta(albastru)
      setColor(0, 0, 255); //blue
      break;
    }
    case 4:{
      //4=cod respins(rosu)
      setColor(255, 0, 0); //red
      break;
    }
  }
}
void barrierCheck(){
  
  if(openingStateFront == true){
      if(distance>2 && distance <=17){
        barrierPresence = true;
        ledStateMachine=3;//blue
      }
      if(barrierPresence == true){
        if(distance >15){
          barrierPresence = false;
          openingStateFront=false;
          delay(2500);
          servo.write(35);
          ledStateMachine=1;//yellow
        }
      }
  }
  //backToFront=false;
  if(openingStateBack == true){
      if(distance>3 && distance <=15){
        backToFront=true;
        barrierPresence = true;
        ledStateMachine=3;//blue
      }
      if(barrierPresence == true){
        if(distance >15 && backToFront==true){
          barrierPresence = false;
          openingStateBack=false;
          backToFront=false;
          delay(2500);
          servo.write(35);
          ledStateMachine=1;//yellow
        }
      }
  }
  
}
void setup() {
  Serial.begin(9600);
  pinMode(TRIGGER_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  servo.attach(SERVO_PIN);
  //Ethernet.begin(mac,ip); // initializare folosind DHCP
  // start listening for clients
  //server.begin();
  // Open serial communications and wait for port to open:
  
  //while (!Serial) {
 //   ; // wait for serial port to connect. Needed for Leonardo only
  //}
  //Serial.print("Chat server address:");
  //Serial.println(Ethernet.localIP());
  //Bluetooth.begin(9600);
  //Serial.begin(9600);
  //Serial.println("Waiting for command...");
  //Bluetooth.println("Send 1 to turn on the LED. Send 0 to turn Off");
  servo.write(35);
}
void loop() {
  ultrasonicSignal();
  if (distance >= 2000 ) {
    Serial.println("Out of range");
  }
  else {
    Serial.print(distance);
    Serial.println(" cm");
  }
  //EthernetClient client = server.available();
  if(DEBUG_MODE_ON){
    thisChar = Serial.read();
      if(thisChar=='D'){
        servo.write(130);
        ledStateMachine=2;//green
        if(distance>3 && distance <=17){
          openingStateFront = true;
          openingStateBack = false;
          Serial.println("Fata");
        }
        else{
          openingStateBack = true;
          openingStateFront = false;
          Serial.println("Spate");
        }
      }
       /*else if(thisChar=='S'){
        servo.write(130);
        openingStateFront = false;
        openingStateBack = true;
        ledStateMachine=3;//blue
        
      }*/
      else if(thisChar=='I'){
        servo.write(35);
        ledStateMachine=4;//red
        
      }
  }
  barrierCheck();
  ledStateMachineFunction();
  delay(250);
  // when the client sends the first byte, say hello:
  /*if (client) {
    if (!alreadyConnected) {
      // clead out the input buffer:
      client.flush();
      Serial.println("We have a new client");
      //client.println("Hello, client!");
      alreadyConnected = true;
      //incomingByte = Serial.read();
      //client.println(5, DEC);
    }

    if (client.available() > 0) {
      client.println(2414, DEC);
      thisChar = client.read();
      if(thisChar=='D'){
        //servo.write(40);
        userID=true;
      }
      else if(thisChar=='I'){
        servo.write(95);
        userID=false;
      }
      server.write(thisChar);
      Serial.write(thisChar);
     }
  }*/
  /*if (Bluetooth.available()){ //wait for data received
    Data=Bluetooth.readString();
      Serial.println(Data);
      Bluetooth.println("Mesaj trimis");
  }*/
  
}



