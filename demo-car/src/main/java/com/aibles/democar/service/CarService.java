package com.aibles.democar.service;

import com.aibles.democar.model.Car;

import java.util.List;

public interface CarService {

    Car createdCar(Car car);
    List<Car> cars();
    Car updateCar(int id, Car car);
    void deleteCar(int id);
}
