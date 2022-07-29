package com.aibles.democar.service.Impl;

import com.aibles.democar.model.Car;
import com.aibles.democar.repository.CarRepository;
import com.aibles.democar.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

public class CarServiceImpl implements CarService {

    public final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public Car createdCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> cars() {
        return carRepository.findAll();
    }

    @Override
    public Car updateCar(int carId, Car carReq) {
        Car result = carRepository.findById(carId)
                .map(car -> {
                    car.setName(carReq.getName());
                    car.setColor(carReq.getColor());
                    return car;
                })
                .map(carRepository::save)
                .orElse(null);
        return result;
    }

    @Override
    public void deleteCar(int carId) {
        carRepository.findById(carId)
                .map(car -> {
                    carRepository.delete(car);
                    return car;
                })
                .orElse(null);
    }
}
