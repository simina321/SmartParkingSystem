/*
 Circuit:
 * Ethernet shield attached to pins 10, 11, 12, 13
 * Analog inputs attached to pins A0 through A5 (optional)
 */
#include <SPI.h>
#define RGB_LED_RED_PIN 5
#define RGB_LED_GREEN_PIN 4
#define RGB_LED_BLUE_PIN 6

#define TRIGGER_PIN3 12
#define ECHO_PIN3 11

#define TRIGGER_PIN2 2
#define ECHO_PIN2 3 

#define TRIGGER_PIN1 9
#define ECHO_PIN1 10

long duration1, distance1;
long duration3, distance2;
long duration2, distance3;
char thisChar;
char parkingSpace[3];
boolean parkSpace1[5]={false,false,false,false,false};
boolean parkSpace2[5]={false,false,false,false,false};
boolean parkSpace3[5]={false,false,false,false,false};
int ledStateMachine = 0; // 0=inceput,1=conectat ethernet(yellow),2= cod admis(verde),3=poarta(albastru),4=cod respins(rosu)
void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(RGB_LED_RED_PIN, redValue);
  analogWrite(RGB_LED_GREEN_PIN, greenValue);
  analogWrite(RGB_LED_BLUE_PIN, blueValue);
}
void ultrasonicSignal1(){
  digitalWrite(TRIGGER_PIN1, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIGGER_PIN1, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER_PIN1, LOW);
  duration1 = pulseIn(ECHO_PIN1, HIGH);
  distance1 = (duration1/2) / 29.1;
}

void ultrasonicSignal2(){
  digitalWrite(TRIGGER_PIN2, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIGGER_PIN2, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER_PIN2, LOW);
  duration2 = pulseIn(ECHO_PIN2, HIGH);
  distance2 = (duration2/2) / 29.1;
}

void ultrasonicSignal3(){
  digitalWrite(TRIGGER_PIN3, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIGGER_PIN3, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER_PIN3, LOW);
  duration3 = pulseIn(ECHO_PIN3, HIGH);
  distance3 = (duration3/2) / 29.1;
}


void ledStateMachineFunction(){
  switch(ledStateMachine){
    case 0:{
      //2= cod admis(verde)
      setColor(0, 255, 0); //green
      break;
    }
    case 1:{
      //1=conectat ethernet(yellow)
      setColor(0, 0, 255);//yellow
      break;
    }
    case 2:{
      //1=conectat ethernet(yellow)
      setColor(255, 255, 0);//yellow
      break;
    }
    case 3:{
      //4=cod respins(rosu)
      setColor(255, 0, 0); //red
      break;
    }
  }
}

void setup() {
  Serial.begin(9600);
  pinMode(TRIGGER_PIN1, OUTPUT);
  pinMode(ECHO_PIN1, INPUT);

  pinMode(TRIGGER_PIN2, OUTPUT);
  pinMode(ECHO_PIN2, INPUT);

  pinMode(TRIGGER_PIN3, OUTPUT);
  pinMode(ECHO_PIN3, INPUT);
}
int contor1=0;
int contor2=0;
int contor3=0;
int nrParkOcupate=0;
void loop() {
  ultrasonicSignal1();
  ultrasonicSignal2();
  ultrasonicSignal3();
  
  if(distance1>3 && distance1 <=17){
    //Serial.println("A");
     parkSpace1[contor1]=true;
     contor1++;
     if(contor1==5){
      int parkSpaceTrue=0;
      int parkSpaceFalse=0;
      for(int i=0;i<5;i++){
        if(parkSpace1[i]==true){
          parkSpaceTrue++;
        }
        else{
          parkSpaceFalse++;
        }
      }
      if(parkSpaceTrue>parkSpaceFalse){
        //Serial.print("A");
        contor1=0;
        parkingSpace[0]='A';
      }
      else{
        //Serial.print("F");
        contor1=0;
        parkingSpace[0]='F';
      }
     }
  }
  else{
    //Serial.println("F");
    parkSpace1[contor1]=false;
    contor1++;
    if(contor1==5){
      int parkSpaceTrue=0;
      int parkSpaceFalse=0;
      for(int i=0;i<5;i++){
        if(parkSpace1[i]==true){
          parkSpaceTrue++;
        }
        else{
          parkSpaceFalse++;
        }
      }
      if(parkSpaceTrue>parkSpaceFalse){
        //Serial.print("A");
        contor1=0;
        parkingSpace[0]='A';
      }
      else{
        //Serial.print("F");
        contor1=0;
        parkingSpace[0]='F';
      }
     }
  }

  if(distance2>3 && distance2 <=17){
    //Serial.println("A");
     parkSpace2[contor2]=true;
     contor2++;
     if(contor2==5){
      int parkSpaceTrue=0;
      int parkSpaceFalse=0;
      for(int i=0;i<5;i++){
        if(parkSpace2[i]==true){
          parkSpaceTrue++;
        }
        else{
          parkSpaceFalse++;
        }
      }
      if(parkSpaceTrue>parkSpaceFalse){
        //Serial.print("A");
        contor2=0;
        parkingSpace[1]='A';
      }
      else{
        //Serial.print("F");
        contor2=0;
        parkingSpace[1]='F';
      }
     }
  }
  else{
    //Serial.println("F");
    parkSpace2[contor2]=false;
    contor2++;
    if(contor2==5){
      int parkSpaceTrue=0;
      int parkSpaceFalse=0;
      for(int i=0;i<5;i++){
        if(parkSpace2[i]==true){
          parkSpaceTrue++;
        }
        else{
          parkSpaceFalse++;
        }
      }
      if(parkSpaceTrue>parkSpaceFalse){
        //Serial.print("A");
        contor2=0;
        parkingSpace[1]='A';
      }
      else{
        //Serial.print("F");
        contor2=0;
        parkingSpace[1]='F';
      }
     }
  }

  if(distance3>3 && distance3 <=17){
    //Serial.println("A");
     parkSpace3[contor3]=true;
     contor3++;
     if(contor3==5){
      int parkSpaceTrue=0;
      int parkSpaceFalse=0;
      for(int i=0;i<5;i++){
        if(parkSpace3[i]==true){
          parkSpaceTrue++;
        }
        else{
          parkSpaceFalse++;
        }
      }
      if(parkSpaceTrue>parkSpaceFalse){
        //Serial.print("A");
        contor3=0;
        parkingSpace[2]='A';
      }
      else{
        //Serial.print("F");
        contor3=0;
        parkingSpace[2]='F';
      }
     }
  }
  else{
    //Serial.println("F");
    parkSpace3[contor3]=false;
    contor3++;
    if(contor3==5){
      int parkSpaceTrue=0;
      int parkSpaceFalse=0;
      for(int i=0;i<5;i++){
        if(parkSpace3[i]==true){
          parkSpaceTrue++;
        }
        else{
          parkSpaceFalse++;
        }
      }
      if(parkSpaceTrue>parkSpaceFalse){
        //Serial.print("A");
        contor3=0;
        parkingSpace[2]='A';
      }
      else{
        //Serial.print("F");
        contor3=0;
        parkingSpace[2]='F';
      }
     }
  }
  Serial.print(parkingSpace[0]);
  Serial.print(parkingSpace[1]);
  Serial.print(parkingSpace[2]);
  //Serial.println(distance3);
  nrParkOcupate=0;
  for(int j=0;j<3;j++){
    if(parkingSpace[j]=='A'){
      nrParkOcupate++;
    }
  }
  
  switch(nrParkOcupate){
    case 0:{
      ledStateMachine=0;
      break;
    }
    case 1:{
      ledStateMachine=1;
      break;
    }
    case 2:{
      ledStateMachine=2;
      break;
    }
    case 3:{
      ledStateMachine=3;
      break;
    }
  }
  //Serial.println(ledStateMachine);
  ledStateMachineFunction();
  delay(1000);
}



