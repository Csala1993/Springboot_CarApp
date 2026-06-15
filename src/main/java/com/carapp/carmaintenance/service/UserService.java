package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.MasinaDTO;
import com.carapp.carmaintenance.dto.UserDTO;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.carapp.carmaintenance.dto.AdminUserDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Un utilizator cu acest email există deja!");
        }
        user.setParola(passwordEncoder.encode(user.getParola())); // ADAUGĂ
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit!"));

        user.setNume(userDetails.getNume());
        user.setEmail(userDetails.getEmail());
        if (userDetails.getParola() != null && !userDetails.getParola().isEmpty()) {
            user.setParola(passwordEncoder.encode(userDetails.getParola())); // modifică
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit!"));
        userRepository.delete(user);
    }

    public List<UserDTO> getAllUsersDto() {
        return userRepository.findAll().stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getNume(),
                        u.getEmail(),
                        u.getParola(),
                        u.getMasini().stream()
                                .map(m -> new MasinaDTO(
                                        m.getId(),
                                        m.getMarca(),
                                        m.getModel(),
                                        m.getAn(),
                                        m.getNumarInmatriculare(),
                                        m.getVin(),
                                        m.getKilometraj()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public List<AdminUserDTO> getAllUsersForAdmin() {
        return userRepository.findAll()
                .stream()
                .map(user -> new AdminUserDTO(
                        user.getId(),
                        user.getNume(),
                        user.getEmail(),
                        user.getRole().name()
                ))
                .toList();
    }


}