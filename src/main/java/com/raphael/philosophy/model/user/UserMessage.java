package com.raphael.philosophy.model.user;

import com.raphael.philosophy.model.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_messages")
public class UserMessage extends Message {
    @ManyToOne
    @JoinColumn(name = "id_receiver", referencedColumnName = "id")
    private User idReceiver;
}
