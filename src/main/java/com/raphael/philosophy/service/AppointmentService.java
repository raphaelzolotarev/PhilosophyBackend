package com.raphael.philosophy.service;

import com.raphael.philosophy.model.appointment.Appointment;
import com.raphael.philosophy.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository repo;

    public List<Appointment> getAllAppointments() {
        return repo.findAll();
    }

    public Optional<Appointment> getAppointmentById(Short id) {
        return repo.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        return repo.save(appointment);
    }

    public Optional<Appointment> updateAppointment(Short id, Appointment appointment) {
        if (repo.existsById(id)) {
            appointment.setId(id);
            return Optional.of(repo.save(appointment));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteAppointment(Short id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
