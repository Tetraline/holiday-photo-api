package com.example.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;

    public String addHoliday(Holiday h) throws IOException {
        holidayRepository.save(h);
        return "Successfully Added";
    }

    public Holiday getHoliday(String id) {
        return holidayRepository.findById(id).get();
    }

    public Stream<Holiday> getAllHolidays() {
        return holidayRepository.findAll().stream();
    }


}
