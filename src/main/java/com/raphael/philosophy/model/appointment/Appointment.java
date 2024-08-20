package com.raphael.philosophy.model.appointment;

import com.raphael.philosophy.model.appointment.enums.AppointmentStatus;
import com.raphael.philosophy.model.Audit;
import com.raphael.philosophy.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "appointments")
public class Appointment extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Short id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private User client;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private User coach;

    @NotNull
    private LocalDateTime agendaDateTime;

    @NotNull
    private AppointmentStatus appointmentStatus;

}
