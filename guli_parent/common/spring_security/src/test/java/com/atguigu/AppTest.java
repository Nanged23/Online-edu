package com.atguigu;
class speakerCar extends Thread{
    @Override
    public void run(){
        for (int i = 0; i < 3; i++) {
            System.out.println("car is "+i);
        }
    }
}
class speakerEle extends Thread{
    @Override
    public void run(){
        for (int i = 0; i < 3; i++) {
            System.out.println("elem is "+i);
        }
    }
}
class AppTest{
    public static void main(String[] args) {
        new speakerCar().start();
        new speakerEle().start();
        for (int i = 0; i < 40; i++) {
            System.out.println("main is "+i);
        }
    }

}