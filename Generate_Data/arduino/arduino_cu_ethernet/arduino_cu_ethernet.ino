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
//-----------------------------------------------------
//SoftwareSerial Bluetooth(8, 7); // RX, TX
//-----------------------------------------------------
Servo servo;
#define RGB_LED_RED_PIN 5
#define RGB_LED_GREEN_PIN 4
#define RGB_LED_BLUE_PIN 6
#define TRIGGER_PIN 2
#define ECHO_PIN 3
#define SERVO_PIN 9
#define SERVO_BARRIER_OPEN 130
#define SERVO_BARRIER_CLOSE 35
byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};
String Data; // the data received
long duration, distance;
char thisChar;
boolean alreadyConnected = false; // whether or not the client was connected previously
boolean barrierPresence = false;
boolean openingStateFront = false;
boolean openingStateBack =  false;
boolean backToFront = false;
boolean bluethootMessageRecived = false;
boolean clientConnected = false;
boolean messageRecivedEth = false;
int ledStateMachine = 0; // 0=inceput,1=conectat ethernet(yellow),2= cod admis(verde),3=poarta(albastru),4=cod respins(rosu)
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
      if(distance>=2 && distance <=12){
        barrierPresence = true;
        ledStateMachine=3;//blue
      }
      if(barrierPresence == true){
        if(distance >12){
          barrierPresence = false;
          openingStateFront=false;
          delay(2500);
          servo.write(SERVO_BARRIER_CLOSE);
          ledStateMachine=1;//yellow
        }
      }
  }
  if(openingStateBack == true){
      if(distance>=2 && distance <=12){
        backToFront=true;
        barrierPresence = true;
        ledStateMachine=3;//blue
      }
      if(barrierPresence == true){
        if(distance >12 && backToFront==true){
          barrierPresence = false;
          openingStateBack=false;
          backToFront=false;
          delay(2500);
          servo.write(SERVO_BARRIER_CLOSE);
          ledStateMachine=1;//yellow
        }
      }
  }

  messageRecivedEth = false;
}
void setup() {
  Serial.begin(9600);
  pinMode(TRIGGER_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  servo.attach(SERVO_PIN);
  Ethernet.begin(mac,ip); // initializare folosind DHCP
  // start listening for clients
  server.begin();
  // Open serial communications and wait for port to open:
  
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }
  Serial.print("Chat server address:");
  Serial.println(Ethernet.localIP());
  //Bluetooth.begin(9600);
//  Bluetooth.println("Connected");
  servo.write(SERVO_BARRIER_CLOSE);
}
void loop() {
  ultrasonicSignal();
  if (distance >= 2000 ) {
     Serial.println("Out of range");
  }
  else{
     Serial.print(distance);
     Serial.println(" cm");
  }
  delay(250);
  EthernetClient client = server.available();
  // when the client sends the first byte, say hello:
  if(clientConnected == true && messageRecivedEth==false){
    ledStateMachine = 1;
  }
  if (client) {
    if (!alreadyConnected) {
      // clead out the input buffer:
      client.flush();
      Serial.println("We have a new client");
      alreadyConnected = true;
      clientConnected = true;
    }

    if (client.available() > 0) {
      thisChar = client.read();
      if(thisChar=='D'){
        messageRecivedEth = true;
        servo.write(SERVO_BARRIER_OPEN);
        ledStateMachine=2;//green
        if(distance>2 && distance <=15){
          openingStateFront = true;
          openingStateBack = false;
        }
        else{
          openingStateBack = true;
          openingStateFront = false;
        } 
      }
      else if(thisChar=='I'){
        servo.write(SERVO_BARRIER_CLOSE);
        ledStateMachine=4;//red
      }
      server.write(thisChar);
      Serial.write(thisChar);
     }
     //---------------------------------------------------------
     /*if (Bluetooth.available()){ //wait for data received
          Data=Bluetooth.readString();
          Serial.println(Data);
          Bluetooth.println("Mesaj trimis");
          bluethootMessageRecived = true;
          
      }
      if(bluethootMessageRecived==true){
        client.println(Data);
        bluethootMessageRecived = false;
      }*/
      //---------------------------------------------------------
  }
  barrierCheck();
  ledStateMachineFunction();
  delay(250);
  
  
}



