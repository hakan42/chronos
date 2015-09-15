## chronos - Χρόνος - greek god of time

a LED clock controller with REST API

## What? Why? And Wherefore?

My wall clock died. And, instead of buying a new one like any sensible person would do,
I started creating my own LED clock. And being the computer geek that I am, I wanted it
to be controllable by my home automation system.

###### TODO: insert picture here


## Hardware

The clock consists mainly of:

* A [Raspberry Pi](https://www.raspberrypi.org/), bought from [Amazon](http://www.amazon.de/gp/product/B00T3BX0L8)
* A ring with 60 LEDs on it, bought from [watterott electronics](http://www.watterott.com/de/WS2812-RGB-Ring-60-Black-Edition)
* A [FadeCandy](http://www.misc.name/fadecandy/) LED controller, bought from [Amazon](http://www.amazon.de/gp/product/B00K02HWIG)

## Software

* A [Spring Boot](http://projects.spring.io/spring-boot/) based Java application
* The native controller for the FadeCandy
* A dockerified boot image for the Pi, thanks to the guys at [Hypriot](http://blog.hypriot.com/)

## Links

* https://en.wikipedia.org/wiki/Chronos
* Maven generated site, version [0.0.1-SNAPSHOT](/site/0.0.1-SNAPSHOT)

