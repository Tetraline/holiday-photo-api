package com.example.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public String addImage(Image i) throws IOException {
        imageRepository.save(i);
        return "Successfully Added";
    }

    public Image getImage(String id) {
        return imageRepository.findById(id).get();
    }

    public Stream<Image> getAllImages() {
        return imageRepository.findAll().stream();
    }

    public Stream<Image> getImagesOfHoliday(String holidayId) {
        return imageRepository.findAll().stream().filter(i -> i.getHolidayId().equals(holidayId)).sorted(Comparator.comparing(Image::getDate));
    }
}
