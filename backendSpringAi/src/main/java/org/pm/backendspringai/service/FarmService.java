package org.pm.backendspringai.service;

import org.pm.backendspringai.entity.Farm;
import org.pm.backendspringai.entity.User;
import org.pm.backendspringai.repository.FarmRepository;
import org.pm.backendspringai.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    public FarmService(FarmRepository farmRepository, UserRepository userRepository) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
    }

    public Farm createFarm(String email, String name, String location, Double area) {

        User user = getUserByEmail(email);

        Farm farm = new Farm();

        farm.setUser(user);
        farm.setName(name);
        farm.setLocation(location);
        farm.setArea(area);

        return farmRepository.save(farm);
    }

    public List<Farm> getMyFarms(String email) {

        User user = getUserByEmail(email);

        return farmRepository.findAllByUser(user);
    }

    public Farm getMyFarm(String email, UUID farmId) {
        User user = getUserByEmail(email);

        return farmRepository.findByIdAndUser(farmId, user)
                .orElseThrow(() ->
                        new RuntimeException("Farm not found"));
    }

    public Farm updateFarm(String email, UUID farmId, String name, String location, Double area) {

        User user = getUserByEmail(email);

        Farm farm = farmRepository.findByIdAndUser(farmId, user)
                .orElseThrow(() ->
                        new RuntimeException("Farm not found"));

        farm.setName(name);
        farm.setLocation(location);
        farm.setArea(area);

        return farmRepository.save(farm);
    }

    public void deleteFarm(String email, UUID farmId) {

        User user = getUserByEmail(email);

        Farm farm = farmRepository.findByIdAndUser(farmId, user)
                .orElseThrow(() -> new RuntimeException("Farm not Found"));

        farmRepository.delete(farm);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email : " + email
                ));
    }
}
