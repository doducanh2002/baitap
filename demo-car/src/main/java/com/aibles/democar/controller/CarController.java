package com.aibles.democar.controller;

import com.aibles.democar.model.Car;
import com.aibles.democar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Car> createdCar(@RequestBody Car carReq){
        Car car = carService.createdCar(carReq);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Car>> carList(){
        List<Car> carList = carService.cars();
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @PutMapping("/{car_id}")
    public ResponseEntity<Car> updateCar(@PathVariable("car_id") int carId,
                                         @RequestBody Car carReq){
        final Car updatedCar = carService.updateCar(carId, carReq);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{car_id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("car_id") int carId){
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
