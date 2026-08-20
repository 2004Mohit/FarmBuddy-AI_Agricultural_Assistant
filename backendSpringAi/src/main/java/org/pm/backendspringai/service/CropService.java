package org.pm.backendspringai.service;

import org.pm.backendspringai.entity.Crop;
import org.pm.backendspringai.entity.Farm;
import org.pm.backendspringai.entity.User;
import org.pm.backendspringai.repository.CropDiagnosisRepository;
import org.pm.backendspringai.repository.CropRepository;
import org.pm.backendspringai.repository.FarmRepository;
import org.pm.backendspringai.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CropService {

    private final CropRepository cropRepository;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    public CropService(CropRepository cropRepository, FarmRepository farmRepository, UserRepository userRepository) {
        this.cropRepository = cropRepository;
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
    }

    public Crop createCrop(
            String email,
            UUID farmId,
            String name,
            String variety,
            java.time.LocalDate sowingDate,
            java.time.LocalDate expectedHarvestDate,
            Double area) {

        User user = getUserByEmail(email);

        Farm farm = getUserFarm(farmId, user);

        Crop crop = new Crop();
        crop.setFarm(farm);
        crop.setName(name);
        crop.setVariety(variety);
        crop.setSowingDate(sowingDate);
        crop.setExpectedHarvestDate(expectedHarvestDate);
        crop.setArea(area);

        return cropRepository.save(crop);
    }

    public List<Crop> getFarmCrops(
            String email,
            UUID farmId) {

        User user = getUserByEmail(email);

        Farm farm = getUserFarm(farmId, user);

        return cropRepository.findAllByFarm(farm);
    }

    public Crop getCrop(
            String email,
            UUID farmId,
            UUID cropId) {

        User user = getUserByEmail(email);

        Farm farm = getUserFarm(farmId, user);

        return cropRepository.findByIdAndFarm(cropId, farm)
                .orElseThrow(() ->
                        new RuntimeException("Crop not found"));
    }

    public Crop updateCrop(
            String email,
            UUID farmId,
            UUID cropId,
            String name,
            String variety,
            java.time.LocalDate sowingDate,
            java.time.LocalDate expectedHarvestDate,
            Double area,
            org.pm.backendspringai.entity.CropStatus status) {

        User user = getUserByEmail(email);

        Farm farm = getUserFarm(farmId, user);

        Crop crop = cropRepository.findByIdAndFarm(cropId, farm)
                .orElseThrow(() ->
                        new RuntimeException("Crop not found"));

        crop.setName(name);
        crop.setVariety(variety);
        crop.setSowingDate(sowingDate);
        crop.setExpectedHarvestDate(expectedHarvestDate);
        crop.setArea(area);
        crop.setStatus(status);

        return cropRepository.save(crop);
    }

    public void deleteCrop(
            String email,
            UUID farmId,
            UUID cropId) {

        User user = getUserByEmail(email);

        Farm farm = getUserFarm(farmId, user);

        Crop crop = cropRepository.findByIdAndFarm(cropId, farm)
                .orElseThrow(() ->
                        new RuntimeException("Crop not found"));

        cropRepository.delete(crop);
    }

    private Farm getUserFarm(UUID farmId, User user) {

        return farmRepository.findByIdAndUser(farmId, user)
                .orElseThrow(() -> new RuntimeException("Farm not Found"));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Email : " + email));
    }

}
